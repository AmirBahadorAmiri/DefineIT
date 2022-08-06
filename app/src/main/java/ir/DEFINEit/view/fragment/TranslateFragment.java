/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:59 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.model.SentenceModel;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.sound_volume.VolumeManager;
import ir.DEFINEit.tools.tapsell_ads.AdManager;
import ir.DEFINEit.tools.tts.TTsSingle;
import ir.DEFINEit.tools.user_info.User;
import ir.DEFINEit.tools.web_api.WebApi;
import ir.DEFINEit.view.activity.ChangeLanguageActivity;
import ir.DEFINEit.view.activity.ConversationActivity;
import ir.DEFINEit.view.activity.MainActivity;
import ir.DEFINEit.view.activity.TranslateHistoryActivity;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.model.TapsellPlusAdModel;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class TranslateFragment extends Fragment {

    private AppCompatEditText translated_editText;
    private AppCompatTextView translated_result;
    private AppCompatImageButton conversationLogo, mic_logo, clearLogo, drawerLogo, send_speakLogo, historyLogo, translate_reverse_language, copy_text, share_text;
    private AppCompatButton translate_from, translate_to;
    private ScrollView translated_scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_translate_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        start();
    }

    private void findViews(View view) {

        translated_editText = view.findViewById(R.id.translated_editText);
        translated_result = view.findViewById(R.id.translated_result);
        translated_scrollView = view.findViewById(R.id.translated_scrollView);
        drawerLogo = view.findViewById(R.id.drawerLogo);
        send_speakLogo = view.findViewById(R.id.send_speakLogo);
        historyLogo = view.findViewById(R.id.historyLogo);
        translate_from = view.findViewById(R.id.translate_from);
        translate_to = view.findViewById(R.id.translate_to);
        translate_reverse_language = view.findViewById(R.id.translate_reverse_language);
        copy_text = view.findViewById(R.id.copy_text);
        share_text = view.findViewById(R.id.share_text);
        conversationLogo = view.findViewById(R.id.conversationLogo);
        clearLogo = view.findViewById(R.id.clearLogo);
        mic_logo = view.findViewById(R.id.mic_logo);
    }


    private void start() {

        getLanguages();

        historyLogo.setOnClickListener(n -> startActivity(new Intent(getActivity(), TranslateHistoryActivity.class)));
        drawerLogo.setOnClickListener(n -> ((MainActivity) requireActivity()).openDrawer(true));

        mic_logo.setOnClickListener(v -> {

            if (VolumeManager.getVolume(requireContext()) == 0) {
                Snackbar.make(v, "صدای سیستم قطع است", Snackbar.LENGTH_LONG)
                        .setAction("افزایش صدا", n -> VolumeManager.setVolume(requireContext(), VolumeManager.getManager(requireContext()).getStreamMaxVolume(AudioManager.STREAM_MUSIC))).setTextColor(getResources().getColor(R.color.white)).show();
            } else {

                TTsSingle.initialize(requireContext(), new DefaultListener() {
                    @Override
                    public void onSuccess() {
                        if (TTsSingle.isSupportLanguage(requireContext(), LanguageManager.getToLangaugeCode())) {
                            TTsSingle.speak(translated_result.getText().toString());
                        } else {
                            Toast.makeText(requireContext(), "بسته نرم صوتی این زبان نصب نشده است", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                if (TTsSingle.initialize(requireContext())) {
//                    TTsSingle.speak(translated_result.getText().toString());
//                } else {
//                    Toast.makeText(requireActivity(), "زبان ورودی پشتیبانی نمیشود", Toast.LENGTH_SHORT).show();
//                    /*
//                     *
//                     *
//                     * edit this page
//                     *
//                     *
//                     *
//                     * */
//                }
            }


        });

        translate_reverse_language.setOnClickListener(n -> translate_reverse_language.animate()
                .rotation(-180)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LanguageManager.reverceLanguage();
                        getLanguages();
                        translate();
                        translate_reverse_language.animate()
                                .rotation(0)
                                .setDuration(500)
                                .setListener(null)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).start());

        conversationLogo.setOnClickListener(n -> startActivity(new Intent(requireActivity(), ConversationActivity.class)));

        translate_to.setOnClickListener(n -> {
            Intent intent = new Intent(requireActivity(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", false);
            startActivityForResult(intent, 1003);
        });
        translate_from.setOnClickListener(n -> {
            Intent intent = new Intent(requireActivity(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", true);
            startActivityForResult(intent, 1003);
        });

        send_speakLogo.setOnClickListener(n -> {
            if (Objects.requireNonNull(translated_editText.getText()).toString().isEmpty()) {
                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageManager.getFromLangaugeCode());
                startActivityForResult(voiceIntent, 1002);
            } else {
                translate();
            }
        });

        copy_text.setOnClickListener(n -> {
            CopyHelper.insert(translated_result.getText().toString());
            Toast.makeText(requireActivity(), "متن کپی شد", Toast.LENGTH_SHORT).show();
        });

        share_text.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, translated_result.getText().toString());
            requireContext().startActivity(intent);

        });

        clearLogo.setOnClickListener(n -> translated_editText.setText(""));

        translated_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                translated_scrollView.setVisibility(View.INVISIBLE);
                copy_text.setVisibility(View.INVISIBLE);
                share_text.setVisibility(View.INVISIBLE);
                mic_logo.setVisibility(View.INVISIBLE);
                if (Objects.requireNonNull(translated_editText.getText()).toString().isEmpty()) {
                    clearLogo.setVisibility(View.GONE);
                    send_speakLogo.setImageResource(R.drawable.ic_round_mic);
                } else {
                    clearLogo.setVisibility(View.VISIBLE);
                    send_speakLogo.setImageResource(R.drawable.ic_round_send);
                }
            }
        });

    }

    private void translate() {
//        if (NetworktManager.isVpnConnected() ) {
//            Snackbar.make(send_speakLogo, "لطفا vpn خود را غیرفعال نمایید", Snackbar.LENGTH_LONG)
//                    .setTextColor(getResources().getColor(R.color.white)).show();
//        } else {
//                if ( User.getUserStatus() == StaticDatas.USER_STATUS_GOLDEN) {
//                    googleTranslate(text);
//                } else {

        if (!Objects.requireNonNull(translated_editText.getText()).toString().isEmpty()) {
            if ((System.currentTimeMillis() - User.getBuyTime()) <= (1800 * 1000)) {
                googleTranslate();
            } else {
                DialogManager.showDialog(requireContext(), true, "برای استفاده از این بخش باید تبلیغات را تماشا کنید، آیا مایل هستید؟", new DefaultListener() {
                    @Override
                    public void onSuccess() {
                        AdManager.requestAd(requireActivity(), new DefaultListener() {
                            @Override
                            public void onSuccess() {
                                AdManager.showAd(requireActivity(), new AdShowListener() {
                                    @Override
                                    public void onRewarded(TapsellPlusAdModel tapsellPlusAdModel) {
                                        User.setBuyTime(System.currentTimeMillis());
                                        Toast.makeText(requireContext(), "شما میتوانید به مدت 30 دقیقه از سرویس مترجم استفاده نمایید", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(requireContext(), "متاسفانه درخواست شما با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        });


                        /*
                         *
                         * show ad in this comment
                         *
                         *
                         * */

                    }
                });
            }
        }

//            }
//        }
    }

    private void googleTranslate() {
        WebApi.translateText(Objects.requireNonNull(translated_editText.getText()).toString(), new DefaultListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {

                        String result = response.body().string();
                        JSONArray object = new JSONArray(result);
                        JSONArray object_0 = object.getJSONArray(0);
                        JSONArray object_1 = object_0.getJSONArray(0);
                        StringBuilder sb = new StringBuilder();

                        translated_scrollView.setVisibility(View.VISIBLE);
                        copy_text.setVisibility(View.VISIBLE);
                        share_text.setVisibility(View.VISIBLE);
                        mic_logo.setVisibility(View.VISIBLE);

                        sb.append(object_1.getString(0));
                        if (!object_0.isNull(1)) {
                            if (!object_0.getJSONArray(1).isNull(0)) {
                                sb.append(object_0.getJSONArray(1).getString(0));
                            }
                        }

                        translated_result.setText(sb.toString());
//
                        DBM.getDB(requireActivity()).getSentenceDao()
                                .insert(new SentenceModel(translated_editText.getText().toString(), sb.toString(), new Date()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();

                    }
                } catch (IOException | JSONException e) {
                    translated_scrollView.setVisibility(View.INVISIBLE);
                    copy_text.setVisibility(View.INVISIBLE);
                    share_text.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "متاسفانه مشکلی در برقراری ارتباط پیش آمد", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                translated_scrollView.setVisibility(View.INVISIBLE);
                copy_text.setVisibility(View.INVISIBLE);
                share_text.setVisibility(View.INVISIBLE);
                Toast.makeText(requireContext(), "متاسفانه مشکلی در برقراری ارتباط پیش آمد", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getLanguages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1002) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                ArrayList<String> spokenSearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (spokenSearch != null) {
                    String grabString = spokenSearch.get(0);
                    if (Objects.requireNonNull(translated_editText.getText()).toString().equals(""))
                        translated_editText.setText(grabString);
                    else {
                        String str = translated_editText.getText() + " " + grabString;
                        translated_editText.setText(str);
                    }
                    translated_editText.setSelection(translated_editText.length());
                }
            }
        } else if (requestCode == 1003 && resultCode == 1003) {
            getLanguages();
            translate();
        }
    }

    private void getLanguages() {
        translate_from.setText(LanguageManager.getFromLangauge());
        translate_to.setText(LanguageManager.getToLangauge());
    }

}
