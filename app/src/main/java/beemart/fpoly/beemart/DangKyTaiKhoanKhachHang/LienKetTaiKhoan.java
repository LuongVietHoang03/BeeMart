package beemart.fpoly.beemart.DangKyTaiKhoanKhachHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;

public class LienKetTaiKhoan extends AppCompatActivity {
    private Toolbar idToolBarLienKetTaiKhoan;
    private EditText edSoDienThoaiLienKet;
    private Button btnXacNhanLienKet;
    private KhachHangDAO khachHangDAO;
    private RelativeLayout linerLienKet;
    private LinearLayout linerProgressBar;
    private ProgressBar progressBarDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_ket_tai_khoan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        khachHangDAO = new KhachHangDAO(this);
        idToolBarLienKetTaiKhoan = findViewById(R.id.idToolBarLienKetTaiKhoan);
        edSoDienThoaiLienKet = findViewById(R.id.edSoDienThoaiLienKet);
        linerLienKet = findViewById(R.id.linerLienKet);
        btnXacNhanLienKet = findViewById(R.id.btnXacNhanLienKet);
        linerProgressBar = findViewById(R.id.linerProgressBar);
        progressBarDangKy = findViewById(R.id.progressBarDangKy);
        linerProgressBar.setVisibility(View.INVISIBLE);
        int colorCodeDark = Color.parseColor("#FC630D");
        progressBarDangKy.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        setSupportActionBar(idToolBarLienKetTaiKhoan);
        getSupportActionBar().setTitle("Liên kết tài khoản");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        idToolBarLienKetTaiKhoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnXacNhanLienKet.setOnClickListener(v -> checkValue());
    }

    private void checkValue() {
        String soDienThoai = edSoDienThoaiLienKet.getText().toString().trim();
        if(soDienThoai.isEmpty()){
            snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
            return;
        }
        if (!(soDienThoai.length() >= 10 && soDienThoai.length() <= 12)) {
            snackBar(R.layout.custom_snackbar_error2, "Số điện thoại độ dài khoảng 10-12 ký tự");
            return;
        }
        if(khachHangDAO.checkSoDienThoai(soDienThoai) <0){
            snackBar(R.layout.custom_snackbar_error2,"Số điện thoại không tồn tại trong hệ thống");
            return;
        }
        KhachHang objKhachHang = khachHangDAO.getSoDienThoai(soDienThoai);
        if(!(objKhachHang.getUserNameKH() == null)){
            snackBar(R.layout.custom_snackbar_error2,"Số điện thoại đã đăng kí tài khoản trong hệ thống");
            return;
        }
        linerProgressBar.setVisibility(View.VISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+84"+soDienThoai,60,
                    TimeUnit.SECONDS,
                    LienKetTaiKhoan.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(LienKetTaiKhoan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            Intent intent = new Intent(LienKetTaiKhoan.this, VerifiOTPLienKetTaiKhoan.class);
                            intent.putExtra("sdt",soDienThoai);
                            intent.putExtra("otp",s);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
            );
    }
    public void snackBar(int layout, String s) {
        Snackbar snackbar = Snackbar.make(linerLienKet, "", Snackbar.LENGTH_LONG);
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