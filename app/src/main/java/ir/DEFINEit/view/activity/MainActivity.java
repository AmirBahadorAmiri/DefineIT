/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:58 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.BaselineLayout;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.view.fragment.HomeFragment;
import ir.DEFINEit.view.fragment.MeFragment;
import ir.DEFINEit.view.fragment.SearchFragment;
import ir.DEFINEit.view.fragment.TranslateFragment;

public class MainActivity extends AppCompatActivity {

    private long doubleBackeMillis = 0;

    public BottomNavigationView main_bottom_nav;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private TranslateFragment translateFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        start();

    }

    private void start() {

        main_bottom_nav.setOnNavigationItemSelectedListener(item -> {
            loadFragments(item.getItemId());
            return true;
        });

        main_bottom_nav.setSelectedItemId(R.id.main_page);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Dana-Medium.ttf");
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) main_bottom_nav.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            ((TextView) ((BaselineLayout) itemView.getChildAt(1)).getChildAt(0)).setTypeface(typeface, Typeface.BOLD);
            ((TextView) ((BaselineLayout) itemView.getChildAt(1)).getChildAt(1)).setTypeface(typeface, Typeface.BOLD);
        }

    }

    private void findView() {
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
    }

    public HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            homeFragment.setArguments(new Bundle());
        }
        return homeFragment;
    }

    public SearchFragment getSearchFragment() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            searchFragment.setArguments(new Bundle());
        }
        return searchFragment;
    }

    public TranslateFragment getTranslateFragment() {
        if (translateFragment == null) {
            translateFragment = new TranslateFragment();
            translateFragment.setArguments(new Bundle());
        }
        return translateFragment;
    }

    public MeFragment getUserFragment() {
        if (meFragment == null) {
            meFragment = new MeFragment();
            meFragment.setArguments(new Bundle());
        }
        return meFragment;
    }

    public void loadFragments(int id) {
        if (id == R.id.main_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FrameLayout, getHomeFragment()).commit();
        } else if (id == R.id.search_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FrameLayout, getSearchFragment()).commit();
        } else if (id == R.id.translate_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FrameLayout, getTranslateFragment()).commit();
        } else if (id == R.id.me_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FrameLayout, getUserFragment()).commit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if ((doubleBackeMillis + 1000) >= System.currentTimeMillis()) {
            exitDialog();
        } else {
            Toast.makeText(this, "برای خروج 2 مرتبه دکمه بازگشت را بزنید", Toast.LENGTH_SHORT).show();
            doubleBackeMillis = System.currentTimeMillis();
        }
    }

    private void exitDialog() {
        DialogManager.showAcceptableQuizDialog(this, true, "آیا میخواهید خارج شوید ؟", new DefaultListener() {
            @Override
            public void onSuccess(Object obj) {
                finish();
            }
        });
    }

}
