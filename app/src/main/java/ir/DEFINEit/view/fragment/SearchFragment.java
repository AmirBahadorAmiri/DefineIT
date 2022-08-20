/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:59 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.adapter.WordSearchAdapter;
import ir.DEFINEit.model.WordSearchModel;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.view.activity.SettingsActivity;
import ir.DEFINEit.view.activity.WordHistoryActivity;
import ir.DEFINEit.view.activity.WordTaggedActivity;

public class SearchFragment extends Fragment {

    private final Pattern ENGLISH_CHARACTER = Pattern.compile("[a-zA-Z]");

    private AppCompatImageButton historyLogo, wordTagged, send_speak_clearLogo, searchSettings;
    private AppCompatTextView search_not_found;
    private AppCompatEditText search_editText;
    private RecyclerView search_recyclerView;
    private WordSearchAdapter wordSearchAdapter;

    @SuppressWarnings("rawtypes")
    private Observable typingObservable;
    private Disposable typingDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        start();
    }

    private void start() {

        DBM.getDB(requireContext());

        wordTagged.setOnClickListener(n -> startActivity(new Intent(getActivity(), WordTaggedActivity.class)));
        historyLogo.setOnClickListener(n -> startActivity(new Intent(getActivity(), WordHistoryActivity.class)));
        search_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        wordSearchAdapter = new WordSearchAdapter(getActivity());
        search_recyclerView.setAdapter(wordSearchAdapter);

        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String str = Objects.requireNonNull(search_editText.getText()).toString();
                search_recyclerView.setVisibility(View.INVISIBLE);
                if (typingDisposable != null && !typingDisposable.isDisposed())
                    typingDisposable.dispose();
                typingObservable = Observable.timer(1500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                search_not_found.setVisibility(View.INVISIBLE);
                if (str.isEmpty()) {
                    send_speak_clearLogo.setImageResource(R.drawable.ic_round_mic);
                } else {
                    send_speak_clearLogo.setImageResource(R.drawable.ic_round_clear);

                    //noinspection unchecked
                    typingDisposable = typingObservable.subscribe(onNext -> {

                        if (isENGLISH_CHARACTER(str)) {
                            //noinspection ResultOfMethodCallIgnored
                            DBM.getDB(requireContext()).getWordDao().readEnglishWord(str)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(newData -> {
                                        if (newData != null && !newData.isEmpty()) {
                                            addData(newData, false);
                                        } else {
                                            search_recyclerView.setVisibility(View.INVISIBLE);
                                            search_not_found.setVisibility(View.VISIBLE);
                                        }
                                    }, e -> Log.d("TAG", "err : " + e.getMessage()));
                        } else {
                            //noinspection ResultOfMethodCallIgnored
                            DBM.getDB(requireContext()).getWordDao().readPersianWord(str)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(newData -> {
                                        if (newData != null && !newData.isEmpty()) {
                                            addData(newData, true);
                                        } else {
                                            search_recyclerView.setVisibility(View.INVISIBLE);
                                            search_not_found.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    });
                }
            }
        });

        searchSettings.setOnClickListener(n -> startActivity(new Intent(requireActivity(), SettingsActivity.class)));

        send_speak_clearLogo.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(search_editText.getText()).toString())) {
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                startActivityForResult(voiceIntent, 1002);
            } else {
                search_editText.setText("");
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void addData(List<WordSearchModel> newData, boolean isPersian) {
        wordSearchAdapter.setPersian(isPersian);
        wordSearchAdapter.loadData(newData);
        wordSearchAdapter.notifyDataSetChanged();
        search_recyclerView.scrollToPosition(0);
        search_recyclerView.setVisibility(View.VISIBLE);
    }

    private boolean isENGLISH_CHARACTER(String str) {
        return ENGLISH_CHARACTER.matcher(str).find();
    }

    @Override
    public void onResume() {
        super.onResume();
        search_editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (typingDisposable != null && !typingDisposable.isDisposed())
            typingDisposable.dispose();
    }

    private void findViews(View view) {
        search_recyclerView = view.findViewById(R.id.search_recyclerView);
        search_editText = view.findViewById(R.id.search_editText);
        send_speak_clearLogo = view.findViewById(R.id.send_speak_clearLogo);
        historyLogo = view.findViewById(R.id.historyLogo);
        wordTagged = view.findViewById(R.id.wordTagged);
        searchSettings = view.findViewById(R.id.settings);
        search_not_found = view.findViewById(R.id.search_not_found);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1002) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> spokenSearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (spokenSearch != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (Objects.requireNonNull(search_editText.getText()).toString().isEmpty()) {
                        stringBuilder.append(search_editText.getText());
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(spokenSearch.get(0));
                    search_editText.setText(stringBuilder.toString());
                    search_editText.setSelection(search_editText.length());
                }
            }
        }
    }

}
