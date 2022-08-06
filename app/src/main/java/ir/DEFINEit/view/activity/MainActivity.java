/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:58 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.BaselineLayout;

import java.util.ArrayList;
import java.util.List;

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
    private DrawerLayout mainDrawerLayout;
    private View main_view_profile;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private TranslateFragment translateFragment;
    private MeFragment meFragment;

    ListView activity_main_drawer_list_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        start();

    }

    private void start() {

//        if (User.userIsAvailable()) {
//            getTokenInformation();
//        } else {
//            accountInfo.setText("کاربر مهمان");
//        }

        MyListAdapter adapter = new MyListAdapter(this, getDrawerMenu());
        activity_main_drawer_list_view.setAdapter(adapter);

        main_bottom_nav.setOnNavigationItemSelectedListener(item -> {
            loadFragments(item.getItemId());
            return true;
        });

        main_view_profile.setOnClickListener(view -> {
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

//    private void getTokenInformation() {
//        WebApi.getUserInfo(User.getUserToken(), User.getUserPassword(), new DefaultListener() {
//            @Override
//            public void onSuccess(Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
//                        if (obj.getBoolean("status")) {
//                            User.setUserId(obj.getInt("user_id"));
//                            User.setUserStatus(obj.getInt("user_status"));
//                            User.setUserNameFamily(obj.getString("user_namefamily"));
//                            User.setUserEmail(obj.getString("user_email"));
//                            User.setUserNumber(obj.getString("user_number"));
//                        } else {
//                            DialogManager.showDialog(MainActivity.this,true,"مشکلی برای حساب شما پیش آمده است","فهمیدم","",null);
//                            User.removeAccount();
//                        }
//                    } catch (IOException | JSONException ignored) {
//                    } finally {
//                        String str = User.getUserId() + " - " + User.getUserNameFamily() + "\n" + User.getUserEmail() + " - " + User.getUserNumber();
//                        accountInfo.setText(str);
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Throwable throwable) {
//                String str = User.getUserId() + " - " + User.getUserNameFamily() + "\n" + User.getUserEmail() + " - " + User.getUserNumber();
//                accountInfo.setText(str);
//            }
//        });
//    }

    private void findView() {
        mainDrawerLayout = findViewById(R.id.mainDrawerLayout);
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
        activity_main_drawer_list_view = findViewById(R.id.activity_main_drawer_list_view);
        AppCompatTextView accountInfo = findViewById(R.id.accountInfo);
        main_view_profile = findViewById(R.id.main_view_profile);
    }

    public class MyListAdapter extends BaseAdapter {

        private List<String> adapterItems;
        private Context context;

        public MyListAdapter(Context context, List<String> adapterItems) {
            this.context = context;
            this.adapterItems = adapterItems;
        }

        @Override
        public int getCount() {
            return adapterItems.size();
        }

        @Override
        public Object getItem(int position) {
            return adapterItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder")
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            ((TextView) view).setText(adapterItems.get(position));

            view.setOnClickListener(v -> {
                openDrawer(false);
                if (position == 0) {
                    main_bottom_nav.setSelectedItemId(R.id.main_page);
                } else if (position == 1) {
                    main_bottom_nav.setSelectedItemId(R.id.search_page);
                } else if (position == 2) {
                    main_bottom_nav.setSelectedItemId(R.id.translate_page);
                } else if (position == 3) {
                    bugDialog();
                } else if (position == 4) {
                    main_bottom_nav.setSelectedItemId(R.id.me_page);
                } else if (position == 5) {
                    exitDialog();
                }
            });

            return view;
        }
    }

    private void bugDialog() {
        DialogManager.showMsgDialog(this, true, "گزارش مشکل", "مشکل خود را بنویسید …", new DefaultListener() {
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
        } /*else if (id == R.id.learn_page) {

        }*/ else if (id == R.id.me_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_FrameLayout, getUserFragment()).commit();
        }
    }

    public List<String> getDrawerMenu() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_page));
        list.add(getString(R.string.search_page));
        list.add(getString(R.string.translate_page));
        list.add("گزارش باگ");
        list.add("درباره ما");
        list.add("خروج");
        return list;
    }

    public void openDrawer(boolean isOpen) {
        if (isOpen) {
            mainDrawerLayout.openDrawer(Gravity.RIGHT);
        } else {
            mainDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(Gravity.RIGHT))
            mainDrawerLayout.closeDrawer(Gravity.RIGHT);
        else {
            if ((doubleBackeMillis + 1000) >= System.currentTimeMillis()) {
                exitDialog();
            } else {
                Toast.makeText(this, "برای خروج 2 مرتبه دکمه بازگشت را بزنید", Toast.LENGTH_SHORT).show();
                doubleBackeMillis = System.currentTimeMillis();
            }
        }
    }

    private void exitDialog() {
        DialogManager.showDialog(this, true, "آیا میخواهید خارج شوید ؟", new DefaultListener() {
            @Override
            public void onSuccess() {
                finish();
            }
        });
    }

}
