/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:55 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.adapter.ConversationAdapter;
import ir.DEFINEit.model.ConversationModel;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.tapsell_ads.AdManager;
import ir.DEFINEit.tools.user_info.User;
import ir.DEFINEit.tools.web_api.WebApi;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.model.TapsellPlusAdModel;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    private AppCompatButton translate_from, translate_to;
    private AppCompatImageView conversation_mic_send;
    private AppCompatImageButton translate_reverse_language, clearLogo;
    private RecyclerView conversation_recyclerView;
    private ConversationAdapter conversationAdapter;
    private AppCompatEditText conversation_editText;
    List<ConversationModel> conversationList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        findViews();
        start();
    }

    private void findViews() {
        clearLogo = findViewById(R.id.clearLogo);
        translate_from = findViewById(R.id.translate_from);
        translate_to = findViewById(R.id.translate_to);
        translate_reverse_language = findViewById(R.id.translate_reverse_language);
        conversation_recyclerView = findViewById(R.id.conversation_recyclerView);
        conversation_mic_send = findViewById(R.id.conversation_mic_send);
        conversation_editText = findViewById(R.id.conversation_editText);
    }

    private void start() {
        getLanguages();

        clearLogo.setOnClickListener(n -> conversation_editText.setText(""));

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        conversation_recyclerView.setLayoutManager(layoutManager);
        conversation_recyclerView.setHasFixedSize(true);
        conversationAdapter = new ConversationAdapter(this, conversationList);
        conversation_recyclerView.setAdapter(conversationAdapter);

        conversation_mic_send.setOnClickListener(n -> {

            if (Objects.requireNonNull(conversation_editText.getText()).toString().equals("")) {

                Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageManager.getFromLangaugeCode());
                startActivityForResult(voiceIntent, 1002);

            } else {
                translate();
            }

        });

        translate_to.setOnClickListener(n -> {
            Intent intent = new Intent(this, ChangeLanguageActivity.class);
            intent.putExtra("isFrom", false);
            startActivityForResult(intent, 1003);
        });
        translate_from.setOnClickListener(n -> {
            Intent intent = new Intent(this, ChangeLanguageActivity.class);
            intent.putExtra("isFrom", true);
            startActivityForResult(intent, 1003);
        });

        conversation_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Objects.requireNonNull(conversation_editText.getText()).toString().isEmpty()) {
                    clearLogo.setVisibility(View.GONE);
                    conversation_mic_send.setImageResource(R.drawable.ic_round_mic);
                } else {
                    clearLogo.setVisibility(View.VISIBLE);
                    conversation_mic_send.setImageResource(R.drawable.ic_round_send);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void translate() {
        if (!Objects.requireNonNull(conversation_editText.getText()).toString().isEmpty()) {
            if ((System.currentTimeMillis() - User.getBuyTime()) <= (1800 * 1000)) {
                googleTranslate();
            } else {
                DialogManager.showDialog(this, true, "برای استفاده از این بخش باید تبلیغات را تماشا کنید، آیا مایل هستید؟", new DefaultListener() {
                    @Override
                    public void onSuccess() {
                        AdManager.requestAd(ConversationActivity.this, new DefaultListener() {
                            @Override
                            public void onSuccess() {
                                AdManager.showAd(ConversationActivity.this, new AdShowListener() {
                                    @Override
                                    public void onRewarded(TapsellPlusAdModel tapsellPlusAdModel) {
                                        User.setBuyTime(System.currentTimeMillis());
                                        Toast.makeText(ConversationActivity.this, "شما میتوانید به مدت 30 دقیقه از سرویس مترجم استفاده نمایید", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(ConversationActivity.this, "متاسفانه درخواست شما با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
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
    }

    private void googleTranslate() {
        ConversationModel model = new ConversationModel();
        model.setText(Objects.requireNonNull(conversation_editText.getText()).toString());
        conversation_editText.setText("");
        WebApi.translateText(model.getText(), new DefaultListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {

                        String result = response.body().string();

                        JSONArray object = new JSONArray(result);
                        JSONArray object_0 = object.getJSONArray(0);
                        JSONArray object_1 = object_0.getJSONArray(0);
                        StringBuilder sb = new StringBuilder();

                        sb.append(object_1.getString(0));
                        if (!object_0.isNull(1)) {
                            if (!object_0.getJSONArray(1).isNull(0)) {
                                sb.append(object_0.getJSONArray(1).getString(0));
                            }
                        }

                        model.setTranslate(sb.toString());
                        conversationList.add(model);
                        conversationAdapter.notifyItemInserted(conversationList.size() - 1);
                        conversation_recyclerView.scrollToPosition(conversationList.size() - 1);
                    }
                } catch (IOException | JSONException e) {
                    Toast.makeText(ConversationActivity.this, "متاسفانه مشکلی در برقراری ارتباط پیش آمد", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(ConversationActivity.this, "متاسفانه مشکلی در برقراری ارتباط پیش آمد", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void getLanguages() {
        translate_from.setText(LanguageManager.getFromLangauge());
        translate_to.setText(LanguageManager.getToLangauge());
    }

    @Override
    public void onResume() {
        super.onResume();
        getLanguages();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> spokenSearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (spokenSearch != null) {
                    String grabString = spokenSearch.get(0);
                    conversation_editText.setText(grabString);
                    translate();
                }
            }
        } else if (requestCode == 1003 && resultCode == 1003) {
            getLanguages();
        }
    }
}
