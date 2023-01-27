package beemart.fpoly.beemart.DangKyTaiKhoanKhachHang;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;

public class XacThucTaiKhoan extends AppCompatActivity {
    private EditText edPassQuenMatKhau;
    private EditText edRePassQuenMatKhau,edUserNameLienKet;
    private Button btnXacNhan;
    private Toolbar idToolBar;
    private KhachHang khachHang;
    private LinearLayout linerMatKhauMoi;
    private KhachHangDAO khachHangDAO;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_mat_khau_moi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        setSupportActionBar(idToolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        khachHangDAO = new KhachHangDAO(this);
        idToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            khachHang = (KhachHang) bundle.getSerializable("objKhachHang");

            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passWored =  edPassQuenMatKhau.getText().toString().trim();
                    String rePass =  edRePassQuenMatKhau.getText().toString().trim();
                    String username =  edUserNameLienKet.getText().toString().trim();
                    if(passWored.isEmpty() ||rePass.isEmpty() || username.isEmpty()){
                        snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
                        return;
                    }
                    if(username.length() < 7){
                        snackBar(R.layout.custom_snackbar_error2,"Tài khoản phải lớn hơn 7 kí tự");
                        return;
                    }
                    if(!(passWored.length() > 7 && rePass.length() >7)){
                        snackBar(R.layout.custom_snackbar_error2,"Mật khẩu phải lớn hơn 7 kí tự");
                        return;
                    }
                    if( !(passWored.equals(rePass) && rePass.equals(passWored) )){
                        snackBar(R.layout.custom_snackbar_error2,"Mật khẩu không trùng khớp");
                        return;
                    }
                    khachHang.setUserNameKH(username);
                    khachHang.setPasswordKH(passWored);
                    int kq = khachHangDAO.update(khachHang);
                    if(kq >0){
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Kiên kết tài khoản thành công");
                        dialogLoading(XacThucTaiKhoan.this, Gravity.CENTER);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        }, 1400);
                    }else {
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Thất bại");

                    }
                }
            });
        }
    }
    public void dialogLoading(Context context, int gravity){
        dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressBarLoading);
        int colorCodeDark = Color.parseColor("#FC630D");
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        dialog.show();
    }
    private void initView(){
        edPassQuenMatKhau = findViewById(R.id.edPassQuenMatKhau);
        edRePassQuenMatKhau = findViewById(R.id.edRePassQuenMatKhau);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        idToolBar = findViewById(R.id.idToolBar);
        linerMatKhauMoi = findViewById(R.id.linerMatKhauMoi);
        edUserNameLienKet = findViewById(R.id.edUserNameLienKet);
    }
    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(linerMatKhauMoi, "", Snackbar.LENGTH_LONG);
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