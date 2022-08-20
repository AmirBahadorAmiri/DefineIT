/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;

import com.pushpole.sdk.PushPole;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.packager.Packager;
import ir.DEFINEit.tools.permission_manager.PermisionListener;
import ir.DEFINEit.tools.permission_manager.PermissionManager;
import ir.DEFINEit.tools.user_info.User;

public class SplashActivity extends AppCompatActivity {

    String[] perms = new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (User.isDarkTheme())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        PermissionManager.onCheckPermissions(this, perms, new PermisionListener() {
            @Override
            public void onChecked(List<String> permissionGranted, List<String> permissionDenied) {
                if (!permissionDenied.isEmpty()) {
                    DialogManager.showDialog(SplashActivity.this, false, "برای عملکرد بهتر برنامه دسترسی‌های مورد نیاز را بدهید", new DefaultListener() {
                        @Override
                        public void onSuccess() {
                            PermissionManager.onRequestPermissions(SplashActivity.this, perms, 1004);
                        }

                        @Override
                        public void onFailure() {
                            start();
                        }
                    });
                } else {
                    start();
                }
            }
        });

    }

//    private void loginAppVersion() {
//
//        WebApi.loginAppVersion(this, new DefaultListener() {
//            @Override
//            public void onSuccess(Response<ResponseBody> response) {
//                if (response.body() != null) {
//                    try {
//                        JSONObject json = new JSONObject(response.body().string());
//                        if (json.getBoolean("status")) {
//                            next();
//                        } else {
//
//                            DialogManager.showDialog(SplashActivity.this, false, "اپلیکیشن نیازمند بروزرسانی میباشد‍‍‍‍, آیا مایل به بروزرسانی هستید ؟", "آره", "نچ", new DefaultListener() {
//                                @Override
//                                public void onSuccess() {
//                                    Packager.openInPlayStore(SplashActivity.this);
//                                    finish();
//                                }
//
//                                @Override
//                                public void onFailure() {
//                                    Toast.makeText(SplashActivity.this, "برای ادامه حتما باید اپلیکیشن را بروزرسانی کنید", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            });
//
//                        }
//                    } catch (JSONException | IOException e) {
//                        next();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                next();
//            }
//        });
//
//    }

//    public void next() {
//        if (User.userIsAvailable())
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        else if (User.getUserSkip())
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        else startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
//        finish();
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1004) {
            start();
        }
    }

    private void start() {

        PushPole.initialize(this, true);

        String version = "نگارش " + Packager.getVersionName(this);
        ((AppCompatTextView) findViewById(R.id.txt_version)).setText(version);

        //noinspection ResultOfMethodCallIgnored
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext -> {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

//                    if (NetworktManager.isVpnConnected()) {
//                        Snackbar.make(findViewById(R.id.txt_version), "لطفا vpn خود را غیرفعال نمایید", Snackbar.LENGTH_LONG)
//                                .setTextColor(getResources().getColor(R.color.white)).show();
//                    } else {
//                        loginAppVersion();
//                    }
                }, onError -> Log.d("TAG", "onCreate: ", onError));

    }
}
