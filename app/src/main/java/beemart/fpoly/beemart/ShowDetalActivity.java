package beemart.fpoly.beemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.SanPhamAdapterHorizontal;
import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.SanPham;

public class ShowDetalActivity extends AppCompatActivity {
    private TextView tvTenSanPhamShowDetal, tvGiaSanPhamShowDetal, tvSoLuongShowDetal, tvNoiDungShowDetal;
    private ImageView imgAnhSanPham, imgMinus, imgPlus;
    private Button btnThemVaoGio;
    private SanPham objSanPham;
    private ArrayList<SanPham> list;
    private SanPhamDAO sanPhamDAO;
    private ConstraintLayout showDetal;
    int numberOrder = 1,tienSanPham = 0;
    SanPhamAdapterHorizontal sanPhamAdapterHorizontal;
    Toolbar toolbar;
    private KhachHang khachHang;
    private GioHangDAO2 gioHangDAO2;
    private NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detal);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        toolbar = (Toolbar) findViewById(R.id.idToolBarGioHang);
        //gioHangDAO = new GioHangDAO(this);
        sanPhamDAO = new SanPhamDAO(this);
        gioHangDAO2 = new GioHangDAO2(this);
        list = (ArrayList<SanPham>) sanPhamDAO.getAll();


        // set acctionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa đơn");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        thamChieu();
        getBundle();
    }


    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(showDetal, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        int valuePosition = bundle.getInt("position");
        objSanPham = list.get(valuePosition);
        if(objSanPham.getAnhSP() == null){
            Picasso.get().load(objSanPham.getLinkAnhSP()).into(imgAnhSanPham);
        }else if(objSanPham.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(objSanPham.getAnhSP(),0,objSanPham.getAnhSP().length);
            imgAnhSanPham.setImageBitmap(bitmap);
        }
        tvTenSanPhamShowDetal.setText(objSanPham.getTenSP());
        tvGiaSanPhamShowDetal.setText(objSanPham.getGiamGia() + " VND");
        tvNoiDungShowDetal.setText(
                "Tên sản phẩm : " +objSanPham.getTenSP()
                + "\n" + "Giá : " +objSanPham.getGiamGia() + "\n"
                + "Số lượng trong kho: " + objSanPham.getSoLuong()+"\n"
                + "Ngày sản xuất : "+ objSanPham.getNgaySanXuat()
                +"\n"+"Hạn sử dụng : "+objSanPham.getHanSuDung());
        tvSoLuongShowDetal.setText(numberOrder + "");
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder< objSanPham.getSoLuong()){
                    numberOrder = numberOrder + 1;
                }
                tvSoLuongShowDetal.setText(numberOrder + "");

            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                tvSoLuongShowDetal.setText(numberOrder + "");
            }
        });
        SharedPreferences preferences = getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        String quyen = (preferences.getString("quyen",""));
        if(quyen.equals("khachhang")){
            KhachHangDAO khachHangDAO = new KhachHangDAO(this);
            khachHang = khachHangDAO.getUserName(userName);
        }else if(quyen.equals("nhanvien")){
            NhanVienDAO nhanVienDAO = new NhanVienDAO(this);
            nhanVien = nhanVienDAO.getUserName(userName);
        }
        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang objGioHang = new GioHang();
                if(quyen.equals("khachhang")){
                    if(gioHangDAO2.checkValue(objSanPham.getMaSP() +"",khachHang.getMaKH()+"") > 0){
                        snackBar(R.layout.custom_snackbar_error2,"Sản phẩm đã có trong giỏ hàng");
                        return;
                    }
                }else if(quyen.equals("nhanvien")){
                    if(gioHangDAO2.checkValueNhanVien(objSanPham.getMaSP() +"",nhanVien.getMaNV()+"") > 0){
                        snackBar(R.layout.custom_snackbar_error2,"Sản phẩm đã có trong giỏ hàng");
                        return;
                    }
                }
                if(objSanPham.getSoLuong() == 0){
                    snackBar(R.layout.custom_snackbar_error2,"Sản phẩm đã hết hàng ");
                    return;
                }
                objGioHang.setMaSP(objSanPham.getMaSP());
                objGioHang.setTenSP(objSanPham.getTenSP());
                if(quyen.equals("khachhang")){
                    objGioHang.setMaKH(khachHang.getMaKH());
                }else if(quyen.equals("nhanvien")){
                    objGioHang.setMaNV(nhanVien.getMaNV());
                }
                if(objSanPham.getAnhSP() == null){
                    objGioHang.setLinkAnhSP(objSanPham.getLinkAnhSP());
                }
                if(objSanPham.getLinkAnhSP() == null){
                    objGioHang.setAnhSP(objSanPham.getAnhSP());
                }
                objGioHang.setSoLuong(numberOrder);
                objGioHang.setGiaSP(objSanPham.getGiamGia());
                long kq = gioHangDAO2.insertGioHang(objGioHang);
                if(kq >0){
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Thêm vào giỏ hành thành công");

                }else{
                    snackBar(R.layout.custom_snackbar_error2,"Thêm thất bại");
                }

            }
        });

    }

    private void thamChieu() {
        tvTenSanPhamShowDetal = findViewById(R.id.tvTenSanPhamShowDetal);
        tvGiaSanPhamShowDetal = findViewById(R.id.tvGiaSanPhamShowDetal);
        tvSoLuongShowDetal = findViewById(R.id.tvSoLuongShowDetal);
        tvNoiDungShowDetal = findViewById(R.id.tvNoiDungShowDetal);
        imgAnhSanPham = findViewById(R.id.imgAnhSanPham);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        btnThemVaoGio = findViewById(R.id.btnThemVaoGioHang);
        showDetal = findViewById(R.id.showDetal);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}