/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 6:00 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.DEFINEit.R;
import ir.DEFINEit.tools.packager.Packager;
import ir.DEFINEit.view.activity.MainActivity;
import ir.DEFINEit.view.activity.SettingsActivity;

public class MeFragment extends Fragment {

    AppCompatImageButton drawerLogo, settings;
    FloatingActionButton other_apps, developer_gmail, telegram_channel;
    AppCompatTextView versionInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_me_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        start();
    }

    private void findViews(View view) {
        drawerLogo = view.findViewById(R.id.drawerLogo);
        settings = view.findViewById(R.id.settings);
        other_apps = view.findViewById(R.id.other_apps);
        developer_gmail = view.findViewById(R.id.developer_gmail);
        telegram_channel = view.findViewById(R.id.telegram_channel);
        versionInfo = view.findViewById(R.id.versionInfo);
    }

    private void start() {
        drawerLogo.setOnClickListener(n -> ((MainActivity) requireActivity()).openDrawer(true));
        settings.setOnClickListener(n -> startActivity(new Intent(requireActivity(), SettingsActivity.class)));
        String version = "نگارش " + Packager.getVersionName(requireContext());
        versionInfo.setText(version);
        developer_gmail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("email"));
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_gmail)});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            startActivity(Intent.createChooser(intent, "انتخاب برنامه مورد نظر ..."));
        });
        other_apps.setOnClickListener(view -> startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://myket.ir/developer/dev-55536/apps?lang=fa")), "انتخاب برنامه مورد نظر ...")));
        telegram_channel.setOnClickListener(view -> startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/AmirBahadorAmiri")), "انتخاب برنامه مورد نظر ...")));
    }

}
