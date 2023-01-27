package beemart.fpoly.beemart.ViBeePay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;

public class ChoXacNhan extends AppCompatActivity {
    private Button btnDong;
    private TextView tvThoiGian,tvMaDG,tvTrangThaiXN;
    private Toolbar toolbar;
    private ViTienDAO viTienDAO;
    private ViTien objViTien;

    private ConstraintLayout fragXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_xac_nhan);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        viTienDAO=new ViTienDAO(this);
        btnDong= findViewById(R.id.btnDong);
        tvThoiGian= findViewById(R.id.tvThoiGian);
        tvMaDG = findViewById(R.id.tvMaGD);
        tvTrangThaiXN=findViewById(R.id.tvTrangThaiXacNhan);
        fragXacNhan=findViewById(R.id.fragXacNhan);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            objViTien= (ViTien) bundle.getSerializable("obj");
            tvMaDG.setText(objViTien.getMaGiaoDich()+"");
            tvThoiGian.setText(objViTien.getThoiGian());
            tvTrangThaiXN.setText(objViTien.getTrangThai()+"");
            if (objViTien.getTrangThai()==1){
                tvTrangThaiXN.setText("Chờ xác nhận");
            }
        }



        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(fragXacNhan, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
}