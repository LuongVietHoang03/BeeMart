package beemart.fpoly.beemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import beemart.fpoly.beemart.Fragment.FragmentChoXacNhan;
import beemart.fpoly.beemart.Fragment.FragmentDaXacNhan;
import beemart.fpoly.beemart.Fragment.FragmentDangGiao;
import beemart.fpoly.beemart.Fragment.FragmentGiaoXong;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar idToolBarHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tabLayout = findViewById(R.id.tabLayout);
        idToolBarHoaDon = findViewById(R.id.idToolBarHoaDon);
        setSupportActionBar(idToolBarHoaDon);

        SharedPreferences preferences = getSharedPreferences("NAMEUSER", MODE_PRIVATE);
        String quyen = (preferences.getString("quyen", ""));
        if (quyen.equals("khachhang")) {
            getSupportActionBar().setTitle("Đơn mua");
        } else {
            getSupportActionBar().setTitle("Quản lý hóa đơn");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idToolBarHoaDon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã xác nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang giao"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao"));
        ReplaceScreen(new FragmentChoXacNhan(), 1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ReplaceScreen(new FragmentChoXacNhan(), 1);
                        break;
                    case 1:
                        ReplaceScreen(new FragmentDaXacNhan(), 1);
                        break;
                    case 2:
                        ReplaceScreen(new FragmentDangGiao(), 1);
                        break;
                    case 3:
                        ReplaceScreen(new FragmentGiaoXong(), 1);
                        break;
                    default:
                        ReplaceScreen(new FragmentChoXacNhan(), 1);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void ReplaceScreen(Fragment fragment, int flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag == 0) {
            transaction.add(R.id.id_framelayout, fragment);
            transaction.commit();
        } else if (flag == 1) {
            transaction.replace(R.id.id_framelayout, fragment);
            transaction.commit();
        }
    }
}