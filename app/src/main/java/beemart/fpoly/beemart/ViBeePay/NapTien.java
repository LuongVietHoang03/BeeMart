package beemart.fpoly.beemart.ViBeePay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import beemart.fpoly.beemart.Adapter.LichSuNapAdapter;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;

public class NapTien extends AppCompatActivity {
    Toolbar toolbar;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    private ViTienDAO viTienDAO;
    TextView tvSoDuVi;
    private ConstraintLayout fragXacNhan;
    EditText edNhapSoTien;
    ChoXacNhan acChoXacNhan;
    private static final int MAX = 99999;
    private static final int MIN = 10000;
    Button btn50, btn100, btn200, btn500, btn1000, btn2000, btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        anhXa();
        khachHangDAO = new KhachHangDAO(this);
        toolbar = findViewById(R.id.idToolBar);
        acChoXacNhan = new ChoXacNhan();

        fragXacNhan = findViewById(R.id.napTien);

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

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edNhapSoTien.getText().toString().trim().isEmpty()) {
                    snackBar(R.layout.custom_snackbar_error2,"Vui lòng nhập số tiền");
                    return;
                }
                napTien();
                showDiaLog(NapTien.this, Gravity.CENTER);


            }
        });

        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("50000");
            }
        });
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("100000");
            }
        });
        btn200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("200000");
            }
        });
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("500000");
            }
        });
        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("1000000");
            }
        });
        btn2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapSoTien.setText("2000000");
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void anhXa() {
        tvSoDuVi = findViewById(R.id.tvSoDuVi);
        edNhapSoTien = findViewById(R.id.edNhapSoTien);
        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);
        btn1000 = findViewById(R.id.btn1000);
        btn2000 = findViewById(R.id.btn2000);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }

    private void napTien() {
        SharedPreferences preferences = getSharedPreferences("NAMEUSER", MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount", ""));
        khachHangDAO = new KhachHangDAO(this);
        objKhachHang = khachHangDAO.getUserName(userName);
        Log.d("aaa", "Khach Hang: " + objKhachHang.getTenKH());
        Log.d("mk", "mat  khau la: " + objKhachHang.getPasswordKH());




    }

    public void showDiaLog(Context context, int gravity) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_xac_nhan_nap_tien);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        EditText edMatKhau = dialog.findViewById(R.id.edMatKhau);
        Button btnThem = dialog.findViewById(R.id.btnLuu);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhau = edMatKhau.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy '-'HH:mm:ss");
                viTienDAO = new ViTienDAO(context);

                String currentDateAndTime = sdf.format(new Date());
                Random random = new Random();
                int numberRandom;
                do {
                    numberRandom = random.nextInt(MAX - MIN + 1) + MIN;
                } while (!(viTienDAO.checkMaGiaoDich(numberRandom + "") < 0));
                if (!objKhachHang.getPasswordKH().equals(matKhau)) {
                    snackBar(R.layout.custom_snackbar_error2, "Mật khẩu cũ sai");
                    return;
                }
                ViTien objViTien = new ViTien();
                objViTien.setMaKH(objKhachHang.getMaKH());
                objViTien.setThoiGian(currentDateAndTime);
                objViTien.setTienNap(Double.parseDouble(edNhapSoTien.getText().toString().trim()));
                objViTien.setMaGiaoDich(numberRandom);
                objViTien.setTrangThai(1);
                long kq = viTienDAO.insert(objViTien);
                if (kq > 0) {
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Xác nhận thành công");
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ChoXacNhan.class);
                    intent.putExtra("obj", objViTien);
                    context.startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();

                } else {
                    snackBar(R.layout.custom_snackbar_error2, "Xác nhận không thành công");

                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void snackBar(int layout, String s) {
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