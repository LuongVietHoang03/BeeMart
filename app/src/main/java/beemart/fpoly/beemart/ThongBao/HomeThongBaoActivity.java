package beemart.fpoly.beemart.ThongBao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import beemart.fpoly.beemart.Adapter.ViewPagerAdapterThongBao;
import beemart.fpoly.beemart.R;

public class HomeThongBaoActivity extends AppCompatActivity {
    private Toolbar idToolBarThongBao;
    private TabLayout tabLayoutThongBao;
    private ViewPager viewPagerThongBao;
    private ViewPagerAdapterThongBao viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_thong_bao);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        setSupportActionBar(idToolBarThongBao);
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        idToolBarThongBao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPagerAdapter = new ViewPagerAdapterThongBao(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerThongBao.setAdapter(viewPagerAdapter);
        tabLayoutThongBao.setupWithViewPager(viewPagerThongBao);
    }

    private void initView() {
        idToolBarThongBao = findViewById(R.id.idToolBarThongBao);
        tabLayoutThongBao = findViewById(R.id.tabLayoutThongBao);
        viewPagerThongBao = findViewById(R.id.viewPagerThongBao);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPagerAdapter = new ViewPagerAdapterThongBao(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerThongBao.setAdapter(viewPagerAdapter);
        tabLayoutThongBao.setupWithViewPager(viewPagerThongBao);
    }
}