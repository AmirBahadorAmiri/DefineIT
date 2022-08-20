/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 6:00 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.DEFINEit.R;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.packager.Packager;
import ir.DEFINEit.tools.tapsell_ads.AdManager;
import ir.DEFINEit.tools.user_info.User;
import ir.DEFINEit.view.activity.SettingsActivity;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.model.TapsellPlusAdModel;

@SuppressLint("IntentReset")
public class MeFragment extends Fragment {

    AppCompatButton developer_button, support_app;
    AppCompatImageButton settings;
    FloatingActionButton other_apps, developer_gmail, telegram_channel;
    AppCompatTextView versionInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        start();
    }

    private void findViews(View view) {
        settings = view.findViewById(R.id.settings);
        other_apps = view.findViewById(R.id.other_apps);
        developer_gmail = view.findViewById(R.id.developer_gmail);
        telegram_channel = view.findViewById(R.id.telegram_channel);
        versionInfo = view.findViewById(R.id.versionInfo);
        developer_button = view.findViewById(R.id.developer_button);
        support_app = view.findViewById(R.id.support_app);
    }

    private void start() {
        settings.setOnClickListener(n -> startActivity(new Intent(requireActivity(), SettingsActivity.class)));
        String version = "نگارش " + Packager.getVersionName(requireContext());
        versionInfo.setText(version);
        developer_gmail.setOnClickListener(view -> bugDialog());
        support_app.setOnClickListener(v -> AdManager.requestAd(requireActivity(), new DefaultListener() {
            @Override
            public void onSuccess() {
                AdManager.showAd(requireActivity(), new AdShowListener() {
                    @Override
                    public void onRewarded(TapsellPlusAdModel tapsellPlusAdModel) {
                        User.setBuyTime(System.currentTimeMillis());
                        Toast.makeText(requireContext(), "با تشکر از شما :)", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "شما میتوانید به مدت 30 دقیقه از سرویس مترجم استفاده نمایید", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "متاسفانه درخواست شما با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
            }
        }));
        developer_button.setOnClickListener(view -> startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://myket.ir/developer/dev-55536/apps?lang=fa")), getString(R.string.choose_app))));
        other_apps.setOnClickListener(view -> startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://myket.ir/developer/dev-55536/apps?lang=fa")), getString(R.string.choose_app))));
        telegram_channel.setOnClickListener(view -> startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/AmirBahadorAmiri")), getString(R.string.choose_app))));
    }

    private void bugDialog() {
        DialogManager.showMsgDialog(requireContext(), true, "گزارش مشکل", "مشکل خود را بنویسید …", new DefaultListener() {
            @Override
            public void onSuccess(String str) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("email"));
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_TEXT, str);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_gmail)});
                startActivity(Intent.createChooser(intent, "انتخاب برنامه مورد نظر ..."));
            }
        });
    }

}
