package beemart.fpoly.beemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.DoanhThuNhanVienAdapter;
import beemart.fpoly.beemart.Adapter.KhachHangAdapter;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.HoaDon;

public class DoanhThuNhanVienActivity extends AppCompatActivity {
    private Toolbar idToolBar;
    private HoaDonDAO hoaDonDAO;
    private ArrayList<HoaDon> list;
    private DoanhThuNhanVienAdapter doanhThuNhanVienAdapter;
    private ConstraintLayout frmDoanhThuNhanVien;
    private RecyclerView recyclerViewDoanhThuNhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_nhan_vien);
        frmDoanhThuNhanVien = findViewById(R.id.frmDoanhThuNhanVien);
        recyclerViewDoanhThuNhanVien = findViewById(R.id.recyclerViewDoanhThuNhanVien);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        idToolBar = findViewById(R.id.idToolBar);
        setSupportActionBar(idToolBar);
        getSupportActionBar().setTitle("Danh sách hóa đơn");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        hoaDonDAO =new HoaDonDAO(this);
        Bundle extra = getIntent().getExtras();
        if(extra != null
        ){
            String maNV = extra.getString("maNV");
            list = (ArrayList<HoaDon>) hoaDonDAO.getAllMaNV(maNV);
            DoanhThuNhanVienAdapter doanhThuNhanVienAdapter = new DoanhThuNhanVienAdapter(list,DoanhThuNhanVienActivity.this );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewDoanhThuNhanVien.setLayoutManager(linearLayoutManager);
            recyclerViewDoanhThuNhanVien.setAdapter(doanhThuNhanVienAdapter);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}