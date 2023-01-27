package beemart.fpoly.beemart;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import beemart.fpoly.beemart.Broadcast.BroadCastInternet;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DangKyTaiKhoanKhachHang.DangKyTaiKhoan;
import beemart.fpoly.beemart.QuenMatKhau.QuenMatKhauActivity;

public class MainActivity extends AppCompatActivity {
    private static final int RESQUET_CODE = 123;
    private Boolean check = true;
    private ImageView imgEye,imgLogo,imgLogoGoogle,imgLoginFacebook;
    private EditText edPassWord,edUserName;
    private Button btnDangNhap;
    private CheckBox cbNhoMatKhau;
    private NhanVienDAO nhanVienDAO;
    private MotionLayout loginMain;
    private TextView tvDangKyTaiKhoan,tvQuenMatKhau;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private KhachHangDAO khachHangDAO;
    private BroadCastInternet broadCastInternet;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        broadCastInternet = new BroadCastInternet();
        imgEye = findViewById(R.id.imgEye);
        edPassWord = findViewById(R.id.edPassWord);
        imgLogo = findViewById(R.id.imgLogo);
        btnDangNhap = findViewById(R.id.btnLogin);
        edUserName = findViewById(R.id.edUserName);
        loginMain = findViewById(R.id.loginMain);
        cbNhoMatKhau = findViewById(R.id.cbNhoMatKhau);
        tvDangKyTaiKhoan = findViewById(R.id.tvDangKyTaiKhoan);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuenMatKhauActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //mở dữ liệu
        nhanVienDAO = new NhanVienDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        //đăng nhập bằng google
        // đăng nhập bằng facebook



        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUserName.setText(preferences.getString("USERNAME",""));
        edPassWord.setText(preferences.getString("PASSWORD",""));
        cbNhoMatKhau.setChecked(preferences.getBoolean("REMEMBER",false));
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_logo);
        imgLogo.setAnimation(animation);
        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == true){
                    imgEye.setImageResource(R.drawable.eye);
                    edPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    check = false;
                }else{
                    imgEye.setImageResource(R.drawable.eye_hide);
                    edPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    check = true;
                }
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();


            }
        });
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            edUserName.setText("");
            edPassWord.setText("");
            cbNhoMatKhau.setChecked(false);
            rememberUser(null,null,false);
        }

        tvDangKyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKyTaiKhoan();
            }
        });
    }

    private void dangKyTaiKhoan() {
        startActivity(new Intent(MainActivity.this, DangKyTaiKhoan.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void loginFacebook() {
        Intent intent = new Intent(MainActivity.this,FacebookAuthActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void loginGoogle() {
        Intent intent  = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RESQUET_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUET_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                manHinhChinh();
            } catch (ApiException e) {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }

    private void manHinhChinh() {
        finish();
        startActivity(new Intent(getApplicationContext(),ManHinhChinh.class));
    }

    private void checkLogin() {
        String check_user = edUserName.getText().toString();
        String check_passs = edPassWord.getText().toString();
        if( check_user.isEmpty()||check_passs.isEmpty()){
            snackBar(R.layout.custom_snackbar_error2);
        }else{
            if(nhanVienDAO.checkLogin(check_user,check_passs)>0 ){
                rememberUser(check_user,check_passs,cbNhoMatKhau.isChecked());
                Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
                intent.putExtra("User_name",check_user);
                intent.putExtra("quyen","nhanvien");
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if(check_passs.equals("admin") && check_user.equals("admin")){
                rememberUser(check_user,check_passs,cbNhoMatKhau.isChecked());
                Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
                intent.putExtra("User_name",check_user);
                intent.putExtra("quyen","admin");
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }else if(khachHangDAO.checkLogin(check_user,check_passs)>0 ){
                rememberUser(check_user,check_passs,cbNhoMatKhau.isChecked());
                Intent intent = new Intent(MainActivity.this,ManHinhChinh.class);
                intent.putExtra("User_name",check_user);
                intent.putExtra("quyen","khachhang");
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            else  {
                snackBar(R.layout.snackbar_custom_error);
            }
        }
    }
    public void snackBar(int layout){
        Snackbar snackbar = Snackbar.make(loginMain,"",Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout,null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout  = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25,25 ,25 ,25 );
        snackbarLayout.addView(custom,0);
        snackbar.show();
    }
    public void rememberUser(String u,String pass,Boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        if(!status){
            edit.clear();
        }else{
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",pass);
            edit.putBoolean("REMEMBER",status);
        }
        edit.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadCastInternet,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadCastInternet);
    }
}