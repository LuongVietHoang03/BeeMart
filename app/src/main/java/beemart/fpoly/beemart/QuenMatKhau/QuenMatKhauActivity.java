package beemart.fpoly.beemart.QuenMatKhau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DangKyTaiKhoanKhachHang.DangKyTaiKhoan;
import beemart.fpoly.beemart.DangKyTaiKhoanKhachHang.VerifiOTPDangKy;
import beemart.fpoly.beemart.R;

public class QuenMatKhauActivity extends AppCompatActivity {
    private Button btnXacNhan;
    private EditText edUserNameQuenMatKhau;
    private KhachHangDAO khachHangDAO;
    private KhachHang khachHang;
    private ConstraintLayout quenMatKhau;
    private Toolbar idToolBar;
    private ProgressBar progressBarQuyenMatKhau;
    private LinearLayout linerProgressBarQuenMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        linerProgressBarQuenMatKhau = findViewById(R.id.linerProgressBarQuenMatKhau);
        progressBarQuyenMatKhau = findViewById(R.id.progressBarQuyenMatKhau);
        linerProgressBarQuenMatKhau.setVisibility(View.INVISIBLE);
        int colorCodeDark = Color.parseColor("#FC630D");
        progressBarQuyenMatKhau.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edUserNameQuenMatKhau = findViewById(R.id.edUserNameQuenMatKhau);
        quenMatKhau = findViewById(R.id.quenMatKhau);
        idToolBar = findViewById(R.id.idToolBar);
        khachHangDAO = new KhachHangDAO(this);

        setSupportActionBar(idToolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });
    }

    private void checkUser() {
        String userName = edUserNameQuenMatKhau.getText().toString().trim();
        if(userName.isEmpty()){
            snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
            return;
        }
        if(khachHangDAO.checkUserName(userName) < 0){
            snackBar(R.layout.custom_snackbar_error2,"Tài khoản không tồn tại trong hệ thống");
            return;
        }
        khachHang = khachHangDAO.getUserName(userName);
        linerProgressBarQuenMatKhau.setVisibility(View.VISIBLE);
        if(khachHang != null){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+84"+khachHang.getSoDienThoai(),60,
                    TimeUnit.SECONDS,
                    QuenMatKhauActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            snackBar(R.layout.custom_snackbar_error2,"Bạn bị giới hạn OTP");
                            linerProgressBarQuenMatKhau.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);


                            Intent intent = new Intent(QuenMatKhauActivity.this,VerifiOTPQuenMatKhau.class);
                            intent.putExtra("objKhachHang",khachHang);
                            intent.putExtra("otp",s);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
            );
        }



    }
    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(quenMatKhau, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}