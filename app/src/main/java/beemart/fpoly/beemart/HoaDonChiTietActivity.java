package beemart.fpoly.beemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.Adapter.HoaDonAdapter;
import beemart.fpoly.beemart.Adapter.HoaDonChiTietAdapter;
import beemart.fpoly.beemart.DAO.HoaDonChiTietDAO;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.HoaDonChiTiet;
import beemart.fpoly.beemart.DTO.KhachHang;

public class  HoaDonChiTietActivity extends AppCompatActivity {
    private RecyclerView rcyHoaDonChiTiet;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    private Toolbar idToolBarLienKetTaiKhoan;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    private HoaDonDAO hoaDonDAO;
    private HoaDon hoaDon;
    private TextView tvKhachHang,tvSoDienThoai,tvDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        rcyHoaDonChiTiet = findViewById(R.id.rcyHoaDonChiTiet);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getApplicationContext());

        idToolBarLienKetTaiKhoan =findViewById(R.id.idToolBarLienKetTaiKhoan);
        tvKhachHang =findViewById(R.id.tvKhachHang);
        tvSoDienThoai =findViewById(R.id.tvSoDienThoai);
        tvDiaChi =findViewById(R.id.tvDiaChi);
        hoaDonDAO = new HoaDonDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        setSupportActionBar(idToolBarLienKetTaiKhoan);
        getSupportActionBar().setTitle("Chi Tiết Hoá Đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        idToolBarLienKetTaiKhoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        int maHoaDon = 0;
        if (bundle != null) {
            maHoaDon = bundle.getInt("maHoaDon");
            hoaDon  = hoaDonDAO.getID(maHoaDon+"");
            if(hoaDon.getMaKH() == 0){
                tvDiaChi.setText("Địa chỉ :Không có");
                tvSoDienThoai.setText("Số điện thoại:Không có");
                tvKhachHang.setText("Tên khách hàng:khách tại quầy");
            }else{
                objKhachHang = khachHangDAO.getID(hoaDon.getMaKH()+"");
                tvDiaChi.setText(objKhachHang.getDiaChi() ==  null ? "Địa chỉ:Không có":"Địa chỉ:"+objKhachHang.getDiaChi());
                tvSoDienThoai.setText("Số điện thoại:0"+objKhachHang.getSoDienThoai()+"");
                tvKhachHang.setText("Tên khách hàng:"+objKhachHang.getTenKH());

            }
        }
        List<HoaDonChiTiet> list = hoaDonChiTietDAO.getAll();
        List<HoaDonChiTiet> listXuatHD = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaHoaDon() == maHoaDon){
                listXuatHD.add(list.get(i));
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyHoaDonChiTiet.setLayoutManager(linearLayoutManager);
        HoaDonChiTietAdapter hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getApplicationContext(), listXuatHD);
        rcyHoaDonChiTiet.setAdapter(hoaDonChiTietAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}