package beemart.fpoly.beemart;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import beemart.fpoly.beemart.Fragment.FragmentChoDuyet;
import beemart.fpoly.beemart.Fragment.FragmentDaDuyet;

public class TabLayoutActivity3 extends AppCompatActivity {

    private TabLayout tabLayout3;
    private ViewPager viewPager3;
    private Toolbar idToolBarQuanLyBeePay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout2);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tabLayout3 = findViewById(R.id.tabLayout2);

        idToolBarQuanLyBeePay = findViewById(R.id.idToolBarVi);
        setSupportActionBar(idToolBarQuanLyBeePay);
        getSupportActionBar().setTitle("Quản lý ví BeePay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idToolBarQuanLyBeePay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout3.addTab(tabLayout3.newTab().setText("Chờ duyệt"));
        tabLayout3.addTab(tabLayout3.newTab().setText("Đã duyệt"));

        ReplaceScreen(new FragmentChoDuyet(), 1);
        tabLayout3.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ReplaceScreen(new FragmentChoDuyet(), 1);
                        break;
                    case 1:
                        ReplaceScreen(new FragmentDaDuyet(), 1);
                        break;
                    default:
                        ReplaceScreen(new FragmentChoDuyet(), 1);
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