package beemart.fpoly.beemart.GioHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.CartListAdapter;
import beemart.fpoly.beemart.Adapter.SanPhamAdapter;
import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.Interface.ChangeNumberItemCartList;
import beemart.fpoly.beemart.R;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView rclHoaHon;
    private TextView tvThanhTienMN ,tvThueVATMN,tvPhiKhacMN,tvTongTienMN,tvThongBaoKhongcogi,tvThongBaoKhongcogi2;
    private Button btnThanhToan;
    private ImageView imgNotThing;
    private SanPhamAdapter sanPhamAdapter;
    private CartListAdapter cartListAdapter;
    private double tongTien = 0;
    private GioHangDAO2 gioHangDAO2;
    private Toolbar toolbar;
    private LinearLayout linerGioHang;
    private ScrollView scrollViewGioHang;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    private NhanVien objNhanVien;
    private NhanVienDAO nhanVienDAO;
    private ArrayList<beemart.fpoly.beemart.DTO.GioHang> listGioHang;
    private double tax;
    private int maQuyen,quyenNguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        anhXaView();
        SharedPreferences preferences = getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        String quyen = (preferences.getString("quyen",""));
        nhanVienDAO = new NhanVienDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        if(quyen.equals("nhanvien")){
            objNhanVien = nhanVienDAO.getUserName(userName);
            maQuyen = objNhanVien.getMaNV();
            quyenNguoiDung = 2;
        }else if(quyen.equals("khachhang")){
            objKhachHang = khachHangDAO.getUserName(userName);
            maQuyen = objKhachHang.getMaKH();
            quyenNguoiDung = 1;
        }
        gioHangDAO2 = new GioHangDAO2(this);
        listGioHang = (ArrayList<beemart.fpoly.beemart.DTO.GioHang>) gioHangDAO2.getAll(maQuyen+"",quyenNguoiDung);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initList();
        tinhTien();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), XacNhanThongTinGioHang.class);
                intent.putExtra("totalMoney",tongTien);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rclHoaHon.setLayoutManager(linearLayoutManager);

        cartListAdapter = new CartListAdapter(listGioHang, this, new ChangeNumberItemCartList() {
            @Override
            public void changed() {
                tinhTien();
            }
        });


        rclHoaHon.setAdapter(cartListAdapter);

    }
    private void tinhTien(){
        double thue = 0.02;
        double phidichvu = 2000;
        tax = Math.round((gioHangDAO2.getTolalFee(maQuyen+"",quyenNguoiDung)*thue)*100)/100;
        double total = Math.round(((gioHangDAO2.getTolalFee(maQuyen+"",quyenNguoiDung)+tax)*100)/100);
        double itemTotal = Math.round(gioHangDAO2.getTolalFee(maQuyen+"",quyenNguoiDung)*100)/100;
        if(itemTotal > 0){
            tvThueVATMN.setText(tax +" VND");
            tvTongTienMN.setText(total+phidichvu +" VND");
            tvPhiKhacMN.setText(phidichvu + " VND");
            tvThanhTienMN.setText((itemTotal) + " VND");
            imgNotThing.setVisibility(View.INVISIBLE);
            tvThongBaoKhongcogi.setVisibility(View.INVISIBLE);
            tvThongBaoKhongcogi2.setVisibility(View.INVISIBLE);
        }else{
            tvThongBaoKhongcogi.setVisibility(View.VISIBLE);
            tvThongBaoKhongcogi2.setVisibility(View.VISIBLE);
            imgNotThing.setVisibility(View.VISIBLE);
            linerGioHang.setVisibility(View.INVISIBLE);

        }
    }


    public void anhXaView(){
        rclHoaHon = findViewById(R.id.rclHoaHon);
        tvThanhTienMN = findViewById(R.id.tvThanhTienMN);
        tvThueVATMN = findViewById(R.id.tvThueVATMN);
        tvPhiKhacMN = findViewById(R.id.tvPhiKhacMN);
        tvTongTienMN = findViewById(R.id.tvTongTienMN);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        toolbar = findViewById(R.id.idToolBar);
        imgNotThing = findViewById(R.id.imgNotThing);
        tvThongBaoKhongcogi = findViewById(R.id.tvThongBaoKhongcogi);
        tvThongBaoKhongcogi2 = findViewById(R.id.tvThongBaoKhongcogi2);
        scrollViewGioHang = findViewById(R.id.scrollViewGioHang);
        linerGioHang = findViewById(R.id.linerGioHang);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}