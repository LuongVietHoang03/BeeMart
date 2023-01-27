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

import beemart.fpoly.beemart.Fragment.FragmentLichSuNap;
import beemart.fpoly.beemart.Fragment.FragmentVi;

public class TabLayoutActivity2 extends AppCompatActivity {

    private TabLayout tabLayout2;
    private ViewPager viewPager2;
    private Toolbar idToolBarVi;
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
        tabLayout2 = findViewById(R.id.tabLayout2);

        idToolBarVi = findViewById(R.id.idToolBarVi);
        setSupportActionBar(idToolBarVi);
        getSupportActionBar().setTitle("Ví beepay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idToolBarVi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout2.addTab(tabLayout2.newTab().setText("Nạp tiền"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Lịch sử nạp"));

        ReplaceScreen(new FragmentVi(), 1);
        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ReplaceScreen(new FragmentVi(), 1);
                        break;
                    case 1:
                        ReplaceScreen(new FragmentLichSuNap(), 1);
                        break;
                    default:
                        ReplaceScreen(new FragmentVi(), 1);
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