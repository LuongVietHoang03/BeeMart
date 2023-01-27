package beemart.fpoly.beemart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.Adapter.CartListAdapter;
import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.CountCart;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.Fragment.FragmentDoanhSoNhanVien;
import beemart.fpoly.beemart.Fragment.FragmentDoiMatKhau;
import beemart.fpoly.beemart.Fragment.FragmentLoaiSanPham;
import beemart.fpoly.beemart.Fragment.FragmentManHinhChinh;
import beemart.fpoly.beemart.Fragment.FragmentQuanLyNhanVien;
import beemart.fpoly.beemart.Fragment.FragmentSanPham;
import beemart.fpoly.beemart.Fragment.FragmentKhachHang;
import beemart.fpoly.beemart.Fragment.FragmentThongKe;
import beemart.fpoly.beemart.Fragment.FragmentThongTinCaNhan;
import beemart.fpoly.beemart.Fragment.FragmentVi;
import beemart.fpoly.beemart.GioHang.GioHangActivity;
import beemart.fpoly.beemart.ThongBao.HomeThongBaoActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int IDSANPHAM = 10;
    private static final int IDHOADON = 11;
    private static final int IDLIENHE = 12;
    private static final int IDCANHAN = 13;
    private static final int IDVI = 14;
    DrawerLayout drawerLayout;
    Toolbar toolbar, toolbar2;
    Boolean check = true;
    NavigationView navigationView;
    FrameLayout contentView;
    TextView tvNavUserName;
    NhanVienDAO nhanVienDAO;
    List<NhanVien> listNhanVien;
    NhanVien objNhanVien;
    CircleImageView imgAvatar;
    private SearchView searchView;
    static final float END_SCALE = 0.7f;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    private KhachHangDAO khachHangDAO;
    private KhachHang objkhachHang;
    private int countCart,quyenGioHang;
    private GioHangDAO2 gioHangDAO2;
    private ArrayList<CountCart> listCountCarts;
    private int maGioHang,soLuongThongBao;
    private CartListAdapter cartListAdapter;
    private TextView tvCartCount, tvRingCount;
    private ArrayList<GioHang> listGioHang;
    private HoaDonDAO hoaDonDAO;
    private ArrayList<HoaDon> listHoaDon;
    private ViTienDAO viTienDAO;
    private ArrayList<ViTien> listViTien;
    String quyen;
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_man_hinh_chinh);
        drawerLayout = findViewById(R.id.idDrawerLayout);
        navigationView = findViewById(R.id.idNavView);
        toolbar = findViewById(R.id.idToolBar);
        contentView = findViewById(R.id.id_framelayout);
        nhanVienDAO = new NhanVienDAO(this);
        khachHangDAO = new KhachHangDAO(this);


        // loginGoogle

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String name = account.getDisplayName();
            String mail = account.getEmail();
            Log.d("zzzzz", " name google " + name);
            Log.d("zzzzz", " mail google " + mail);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        // chọn phần từ đầu tiên sét màu icon
        ReplaceScreen(FragmentManHinhChinh.newInstance(), 0);
        navigationView.getMenu().getItem(0).setChecked(true);
        ;

//        set icon kính lup


        toolbar.setTitle("Màn hình chính");

        // biến nhận dữ liệu từ login
        SharedPreferences preferences = getSharedPreferences("NAMEUSER", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String username = extra.getString("User_name");
            quyen = extra.getString("quyen");
            snackBar(R.layout.custom_snackbar_check_mark);
            View headerView = navigationView.getHeaderView(0);
            tvNavUserName = headerView.findViewById(R.id.tvNavUserName);
            imgAvatar = headerView.findViewById(R.id.imgAvatar);
            tvNavUserName.setText(username);
            if (quyen.equalsIgnoreCase("nhanvien")) {
                objNhanVien = nhanVienDAO.getUserName(username);
                if (objNhanVien.getTenNV() != null) {
                    tvNavUserName.setText(objNhanVien.getTenNV());
                    //set ảnh cho nhân viên
                    edit.putString("nameUser", objNhanVien.getTenNV());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(objNhanVien.getAnhNV(), 0, objNhanVien.getAnhNV().length);
                    imgAvatar.setImageBitmap(bitmap);
                    changeItemNav(1);
                    maGioHang = objNhanVien.getMaNV();
                    quyenGioHang = 2;

                }
            } else if (quyen.equalsIgnoreCase("khachhang")) {
                objkhachHang = khachHangDAO.getUserName(username);
                if (objkhachHang.getTenKH() != null) {
                    tvNavUserName.setText(objkhachHang.getTenKH());
                    edit.putString("nameUser", objkhachHang.getTenKH());
                    maGioHang = objkhachHang.getMaKH();
                    quyenGioHang = 1;
                }
                changeItemNav(2);
            } else {
                edit.putString("nameUser", username);
                navigationView.getMenu().findItem(R.id.menuDoiMatKhau).setVisible(false);
            }
            edit.putString("userNameAcount", username);
            edit.putString("quyen", quyen);
            edit.commit();
        }
//        try {
//            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
//            field.setAccessible(true);
//            field.set(null, 100 * 1024 * 1024); //100MB
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                e.printStackTrace();
//            }
//        }

        String quyen = (preferences.getString("quyen", ""));
        if (!(quyen.equals("admin"))) {
            toolbar = findViewById(R.id.idToolBar);
            toolbar.inflateMenu(R.menu.menu_toolbar);
            Menu menu = toolbar.getMenu();
            MenuItem menuItem = menu.findItem(R.id.itemCart);
            MenuItem menuThongBao = menu.findItem(R.id.itemRing);
            View actionView = menuItem.getActionView();
            View actionView2 = menuThongBao.getActionView();
            tvCartCount = actionView.findViewById(R.id.tvCartCount);
            tvRingCount = actionView2.findViewById(R.id.tvRingCount);
            actionView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ManHinhChinh.this, HomeThongBaoActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ManHinhChinh.this, GioHangActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

        }else{
            toolbar = findViewById(R.id.idToolBar);
            toolbar.inflateMenu(R.menu.menu_toolbar);
            Menu menu = toolbar.getMenu();
            MenuItem menuItem = menu.findItem(R.id.itemCart);
            MenuItem menuThongBao = menu.findItem(R.id.itemRing);
            View actionView = menuItem.getActionView();
            View actionView2 = menuThongBao.getActionView();
            tvCartCount = actionView.findViewById(R.id.tvCartCount);
            tvRingCount = actionView2.findViewById(R.id.tvRingCount);
            menu.getItem(1).setVisible(false);
            actionView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ManHinhChinh.this, HomeThongBaoActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!(quyen.equals("admin"))){
            gioHangDAO2 = new GioHangDAO2(ManHinhChinh.this);
            listCountCarts = (ArrayList<CountCart>) gioHangDAO2.getCountCart(maGioHang + "", quyenGioHang);
            if (listCountCarts.get(0).getSoLuong() == 0) {
                tvCartCount.setText(0 + "");
            } else {
                tvCartCount.setText(listCountCarts.get(0).getSoLuong() + "");
            }
        }
        hoaDonDAO = new HoaDonDAO(this);
        viTienDAO = new ViTienDAO(this);
        if(quyen.equals("admin")){
            listHoaDon = (ArrayList<HoaDon>) hoaDonDAO.getAllThongBao();
            listViTien = (ArrayList<ViTien>) viTienDAO.getAllAdmin();
            soLuongThongBao = listHoaDon.size() + listViTien.size();
        }else if(quyen.equals("nhanvien")){
            listHoaDon = (ArrayList<HoaDon>) hoaDonDAO.getAllThongBao();
            soLuongThongBao = listHoaDon.size();
        }else if(quyen.equals("khachhang")){
            listHoaDon = (ArrayList<HoaDon>) hoaDonDAO.getAllKH(maGioHang+"");
            listViTien = (ArrayList<ViTien>) viTienDAO.getAllKH(maGioHang+"");
            soLuongThongBao = listHoaDon.size() + listViTien.size();
        }
        tvRingCount.setText(soLuongThongBao+"");
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @SuppressLint("ResourceType")
    public void changeItemNav(int id) {
        if (id == 1) {
            navigationView.getMenu().findItem(R.id.menuThemNhanVien).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuThongKe).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuLoaiSanPham).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuQuanLiBeePay).setVisible(false);
        } else if (id == 2) {
            navigationView.getMenu().findItem(R.id.menuQuanLiBeePay).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuThemNhanVien).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuThongKe).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuLoaiSanPham).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuSanPham).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuMember).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuDoanhSoNhanVien).setVisible(false);
            navigationView.getMenu().findItem(R.id.menuHoaDon).setVisible(false);
            navigationView.getMenu().add(R.id.menu1, IDSANPHAM, 15, "Sản phẩm").setIcon(R.drawable.icon_san_pham);
            navigationView.getMenu().add(R.id.menu1, IDHOADON, 15, "Đơn mua").setIcon(R.drawable.icon_hoadon);
            navigationView.getMenu().add(R.id.menu1, IDCANHAN, 20, "Thông tin cá nhân").setIcon(R.drawable.icon_username);
            navigationView.getMenu().add(R.id.menu1, IDVI, 20, "Ví BeePay").setIcon(R.drawable.icon_vi);


        }else if( id == 3){

        }

    }


    // snackBar để thông báo
    public void snackBar(int layout) {
        Snackbar snackbar = Snackbar.make(contentView, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setStatusBarColor(this, R.color.purple_200, R.color.white, true);
    }

    public static void setStatusBarColor(Activity A, int color, int color2, boolean darkIcon) {
        if (A != null && !A.isFinishing()) {

            Window window = A.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (darkIcon) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(0);
            }

            window.setStatusBarColor(ContextCompat.getColor(A, color));
            window.setNavigationBarColor(ContextCompat.getColor(A, color2));

        }
    }

    //nav để thay đổi layout
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int check = 6;

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (id == R.id.menuManHinhChinh) {
            ReplaceScreen(FragmentManHinhChinh.newInstance(), 0);
            changeBackgroundItem(check, item, "Màn hình chính");

        } else if (id == R.id.menuLoaiSanPham) {
            ReplaceScreen(FragmentLoaiSanPham.newInstance(), 1);
            changeBackgroundItem(check, item, "Loại sản phẩm");


        } else if (id == R.id.menuSanPham) {
            ReplaceScreen(FragmentSanPham.newInstance(), 1);
            changeBackgroundItem(check, item, "Sản phẩm");

        } else if (id == R.id.menuHoaDon) {
            startActivity(new Intent(ManHinhChinh.this, TabLayoutActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            changeBackgroundItem(check, item, "");
        } else if (id == R.id.menuQuanLiBeePay) {
            startActivity(new Intent(ManHinhChinh.this, TabLayoutActivity3.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            changeBackgroundItem(check, item, "");
        } else if (id == R.id.menuMember) {
            ReplaceScreen(FragmentKhachHang.newInstance(), 1);
            changeBackgroundItem(check, item, "Quản lý khách hàng");

        } else if (id == R.id.menuDoanhSoNhanVien) {
            ReplaceScreen(FragmentDoanhSoNhanVien.newInstance(), 1);
            changeBackgroundItem(check, item, "Doanh thu nhân viên");

        } else if (id == R.id.menuDangXuat) {
            Intent intent = new Intent(ManHinhChinh.this, MainActivity.class);
            SharedPreferences preferences = getSharedPreferences("NAMEUSER", MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    intent.putExtra("log_out", 0);
                    edit.clear();
                    edit.commit();
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

        } else if (id == R.id.menuThemNhanVien) {
            ReplaceScreen(FragmentQuanLyNhanVien.newInstance(), 1);
            changeBackgroundItem(check, item, "Quản lý nhân viên");
        } else if (id == R.id.menuDoiMatKhau) {
            ReplaceScreen(FragmentDoiMatKhau.newInstance(), 1);
            changeBackgroundItem(check, item, "Đổi mật khẩu");

        } else if (id == R.id.menuThongKe) {
            ReplaceScreen(FragmentThongKe.newInstance(), 1);
            changeBackgroundItem(check, item, "Thống kê");
        } else if (id == IDHOADON) {
//            ReplaceScreen(FragmentHoaDon.newInstance(), 1);
            //
            startActivity(new Intent(ManHinhChinh.this, TabLayoutActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            changeBackgroundItem(check, item, "");
        } else if (id == IDVI) {
            startActivity(new Intent(ManHinhChinh.this, TabLayoutActivity2.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            changeBackgroundItem(check, item, "");

        } else if (id == IDCANHAN) {
            ReplaceScreen(FragmentThongTinCaNhan.newInstance(), 1);
            changeBackgroundItem(check, item, "Thông tin cá nhân");
        } else if (id == IDSANPHAM) {
            ReplaceScreen(FragmentSanPham.newInstance(), 1);
            changeBackgroundItem(check, item, "Sản phẩm");
        }
        drawerLayout.closeDrawer(navigationView);

        return true;

    }

    public void changeBackgroundItem(int check, MenuItem item, String title) {
        toolbar.setTitle(title);
//        for (int i = 0; i < check; i++) {
//            int color = Color.parseColor("#FA9769");
//            navigationView.getMenu().getItem(i).getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//
//
//        }
//        item.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    // tránh sự kiên back ra màn hình chính
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();//thoát ứng dụng

        }
    }

    public void ReplaceScreen(Fragment fragment, int flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag == 0) {
            transaction.add(R.id.id_framelayout, fragment);
            transaction.commit();
        } else if (flag == 1) {
            transaction.add(new FragmentManHinhChinh(), "FragmentManHinhChinh");
            transaction.addToBackStack("FragmentManHinhChinh");
            transaction.replace(R.id.id_framelayout, fragment);
            transaction.commit();
        }
    }


    // xóa scrollbar của nav
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


}