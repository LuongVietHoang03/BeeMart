package beemart.fpoly.beemart.GioHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.Adapter.CartListComfirmAdapter;
import beemart.fpoly.beemart.Adapter.SpinnerPhuongThucThanhToanAdapter;
import beemart.fpoly.beemart.BottomSheetDialogXacNhanHoaDon;
import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.Order;
import beemart.fpoly.beemart.DTO.PhuongThucThanhToan;
import beemart.fpoly.beemart.R;

public class XacNhanThongTinGioHang extends AppCompatActivity {
    private TextView tvKhachHang,tvSoDienThoai,tvDiaChi,tvDiemThuongHoaDon;
    private RecyclerView recyclerViewChiTietHoaDon;
    private Button btnXacNhan,btnXacNhanDiemThuong;
    private ArrayList<GioHang> list;
    private CartListComfirmAdapter cartListAdapter;
    private GioHangDAO2 gioHangDAO2;
    private Toolbar toolbar;
    private double tax;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    private List<PhuongThucThanhToan> listThanhToan;
    private SpinnerPhuongThucThanhToanAdapter spinnerPhuongThucThanhToanAdapter;
    private Spinner spinnerThanhToan;
    private EditText edDiemThuongHoaDon;
    private String tenKh,quyen,diachi;
    private int maGioHang,maNV,maKH ;
    private Long soDienThoai;
    private String titlePhuongThucThanhToan,userNameKhachHang = null;
    private ConstraintLayout constraintManHinhXacNhan;
    private int maQuyen;
    private LinearLayout linearLayoutDiaChi,linerDialog;
    private NhanVien objNhanVien;
    private NhanVienDAO nhanVienDAO;
    private double diemThuongAll =0;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private int diemThuongSend;
    private int maPhuongThucThanhToan;
    private Boolean checkDiaChi = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_thong_tin_gio_hang);
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
        khachHangDAO = new KhachHangDAO(this);
        nhanVienDAO = new NhanVienDAO(this);
        thongTinHoaDon();
        thongTinNhanHang();
        gioHangDAO2 = new GioHangDAO2(this);
        list = (ArrayList<GioHang>) gioHangDAO2.getListCart(maGioHang+"",maQuyen);
        initList();
        tinhTien();
        Log.d("zzzz", "Tong tien" + tinhTien());

        loadSpinner();
        if(maQuyen == 2){
            thongTinKhachHangOffline();
        }else if(maQuyen == 1 && diachi == null){
            tvDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogThemDiaChi(XacNhanThongTinGioHang.this,Gravity.CENTER,objKhachHang);
                }
            });
        }

        btnXacNhanDiemThuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diemThuongAll == 0){
                    snackBar(R.layout.custom_snackbar_error2,"??i???m th?????ng hi???n t???i 0 ??i???m");
                    return;
                }
                if(edDiemThuongHoaDon.getText().toString().isEmpty()){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a nh???p ??i???m th?????ng");
                    return;
                }
                if(Double.parseDouble(edDiemThuongHoaDon.getText().toString()) >= diemThuongAll){
                    snackBar(R.layout.custom_snackbar_error2,"??i???m th?????ng c???a b???n kh??ng ????? ");
                    return;
                }
                cusTomXacNhan(XacNhanThongTinGioHang.this,Gravity.CENTER,edDiemThuongHoaDon.getText().toString());

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diachi == null && maNV == 0){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a c???t nh???t ?????a ch??? nh???n h??ng");
                    return;
                }
                if(edDiemThuongHoaDon.isEnabled() == true && !(edDiemThuongHoaDon.getText().toString().isEmpty())){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a x??c nh???n ??i???m th?????ng");
                    return;
                }
                if(edDiemThuongHoaDon.getText().toString().isEmpty()){
                    diemThuongSend = 0;
                }else{
                    diemThuongSend = Integer.parseInt(edDiemThuongHoaDon.getText().toString());

                }
                if(maKH == 0 && maPhuongThucThanhToan == 1){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a li??n k???t v??");
                    return;
                }
                if(maKH != 0&&maPhuongThucThanhToan == 1 && userNameKhachHang == null ){
                    snackBar(R.layout.custom_snackbar_error2,"Kh??ch h??ng ch??a ????ng k?? v?? BeeBay");
                    return;
                }
                
                openBottomSheetFragment();

            }
        });

    }
    private double setDiemThuong(double diemThuong){
        tvDiemThuongHoaDon.setText("??i???m th?????ng hi???n c??: "+ df.format(diemThuong) +" ??i???m");
        return diemThuong;
    }

    private void thongTinKhachHangOffline(){
        tvKhachHang.setText("Kh??ch h??ng:ch??a c?? d??? li???u");
        tvSoDienThoai.setText("S??? ??i???n tho???i:ch??a c?? d??? li???u");
        tvDiaChi.setText("?????a ch??? : "+"ch??a c?? d??? li???u");
        KhachHangDAO khachHangDAO = new KhachHangDAO(this);
        linearLayoutDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCheckKhachHang(XacNhanThongTinGioHang.this,Gravity.CENTER);



            }
        });
    }
    private void dialogThemDiaChi(Context context,int gravity,KhachHang objKhachHang){
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_them_dia_chi);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        EditText edDiaChi = dialog.findViewById(R.id.edDiaChi);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diaChi = edDiaChi.getText().toString().trim();
                if(diaChi.isEmpty()){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a thay ?????i g?? c???");
                    dialog.dismiss();
                    return;
                }
                objKhachHang.setDiaChi(diaChi);
                int kq = khachHangDAO.update(objKhachHang);
                if(kq > 0){
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"C???t nh???t th??nh c??ng");
                    diachi = diaChi;
                    tvDiaChi.setText("?????a ch???:" +diaChi);
                }else{
                    snackBar(R.layout.custom_snackbar_error2,"C???t nh???n ?????a ch??? th???t b???i");
                }
                dialog.dismiss();
            }

        });
        btnHuy.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    private void dialogCheckKhachHang(Context context,int gravity) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_check_so_dien_thoai_khach_hang);

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
        EditText edSoDienThoai = dialog.findViewById(R.id.edSoDienThoai);
        Button btnCheck = dialog.findViewById(R.id.btnCheck);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        TextView tvThemKhachHang = dialog.findViewById(R.id.tvThemKhachHang);
        tvThemKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemKhachHang(XacNhanThongTinGioHang.this,Gravity.CENTER);
            }
        });
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        linerDialog = dialog.findViewById(R.id.linerDialog);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int colorText = Color.parseColor("#FFFBC1");
                KhachHang objKhachHangCheckSoDienThoai = new KhachHang();
                String soDienThoaiDialog = edSoDienThoai.getText().toString();
                if(soDienThoaiDialog.isEmpty()){
                    snackBar(R.layout.custom_snackbar_error2,"B???n ch??a nh???p s??? ??i???n tho???i");
                    return;
                }
                try {
                    objKhachHangCheckSoDienThoai = khachHangDAO.getSoDienThoai(Long.parseLong(soDienThoaiDialog)+"");
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    snackBar(R.layout.custom_snackbar_error2,"S??? ??i???n tho???i kh??ng t???n t???i trong h??? th???ng");
                    return;
                }
                if(objKhachHangCheckSoDienThoai != null){
                    btnCheck.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.boder_button_check));
                    btnCheck.setTextColor(colorText);
                    btnCheck.setText("OK");
                    btnHuy.setText("Tho??t");
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    tvKhachHang.setText("Kh??ch h??ng: "+objKhachHangCheckSoDienThoai.getTenKH());
                    tvSoDienThoai.setText("S??? ??i???n tho???i: 0"+objKhachHangCheckSoDienThoai.getSoDienThoai()+"");
                    tvDiaChi.setText(objKhachHangCheckSoDienThoai.getDiaChi() == null ? "?????a ch??? :" + "Ch??a th??m":"?????a ch??? :" +  objKhachHangCheckSoDienThoai.getDiaChi());
                    tvDiemThuongHoaDon.setText("??i???m th?????ng hi???n c??: " +objKhachHangCheckSoDienThoai.getDiemThuong() + " ??i???m");
                    snackBarDialog(R.layout.custom_snackbar_check_mark_thanh_cong,"Kh??ch h??ng t???n t???i trong h??? th???ng");
                    diemThuongAll = objKhachHangCheckSoDienThoai.getDiemThuong();
                    tenKh = objKhachHangCheckSoDienThoai.getTenKH();
                    soDienThoai = objKhachHangCheckSoDienThoai.getSoDienThoai();
                    diachi = objKhachHangCheckSoDienThoai.getDiaChi();
                    maKH = objKhachHangCheckSoDienThoai.getMaKH();
                    if(objKhachHangCheckSoDienThoai.getUserNameKH() != null){
                        userNameKhachHang = objKhachHangCheckSoDienThoai.getUserNameKH();
                    }

                    linearLayoutDiaChi.setEnabled(false);

                }
                checkDiaChi = false;

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                checkDiaChi = true;
            }

        });

        dialog.show();


    }


    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(constraintManHinhXacNhan, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    public void snackBarDialog(int layout,String s) {
        Snackbar snackbar = Snackbar.make(linerDialog, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    private void openBottomSheetFragment() {
        Order order = new Order(list,tenKh,soDienThoai,diachi,tongTienSauKhiGiam(),maPhuongThucThanhToan,maKH,maNV,maGioHang,maQuyen,diemThuongSend);
        BottomSheetDialogXacNhanHoaDon bottomSheetDialogXacNhanHoaDon = BottomSheetDialogXacNhanHoaDon.newInstance(order);
        bottomSheetDialogXacNhanHoaDon.show(getSupportFragmentManager(),bottomSheetDialogXacNhanHoaDon.getTag());
        bottomSheetDialogXacNhanHoaDon.setCancelable(false);
    }

    public void cusTomXacNhan(Context context, int gravity,String diemThuong) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_diem_thuong);

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
        TextView tvDiemThuong = dialog.findViewById(R.id.tvDiemThuong);

        tvDiemThuong.setText(diemThuong+" ??i???m th?????ng");
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setDiemThuong(diemThuongAll-Double.parseDouble(diemThuong));

            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tongTienSauKhiGiam();
                Log.d("zzzz", "Tong tien sau khi giam" + tongTienSauKhiGiam());
                btnXacNhanDiemThuong.setEnabled(false);
                edDiemThuongHoaDon.setEnabled(false);
                setDiemThuong(diemThuongAll-Double.parseDouble(diemThuong));
                dialog.dismiss();
            }


        });
        dialog.show();
    }
    private void loadSpinner() {
        listThanhToan = new ArrayList<>();
        listThanhToan.add(new PhuongThucThanhToan(1,"V?? Beepay",R.drawable.icon_vi));
        if(maQuyen == 2){
            listThanhToan.add(new PhuongThucThanhToan(2,"Thanh to??n t???i qu???y",R.drawable.icon_cod));
        }else{
            listThanhToan.add(new PhuongThucThanhToan(2,"Thanh to??n khi nh???n h??ng",R.drawable.icon_cod));
        }

        spinnerPhuongThucThanhToanAdapter = new SpinnerPhuongThucThanhToanAdapter(this, (ArrayList<PhuongThucThanhToan>) listThanhToan);
        spinnerThanhToan.setAdapter(spinnerPhuongThucThanhToanAdapter);
        spinnerThanhToan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titlePhuongThucThanhToan = listThanhToan.get(position).getTitle();
                maPhuongThucThanhToan = listThanhToan.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }





    private void thongTinNhanHang() {
        SharedPreferences preferences = getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        quyen = (preferences.getString("quyen",""));
        if(quyen.equals("nhanvien")){
            maQuyen = 2;
            objNhanVien = nhanVienDAO.getUserName(userName);
            if(objNhanVien != null){
                maGioHang = objNhanVien.getMaNV();

            }
        }else if(quyen.equals("khachhang")){
            maQuyen = 1;
            objKhachHang = khachHangDAO.getUserName(userName);
            if(objKhachHang != null){
                tvKhachHang.setText("Kh??ch h??ng : " +objKhachHang.getTenKH());
                tenKh = objKhachHang.getTenKH();
                tvSoDienThoai.setText("S??? ??i???n tho???i : 0" +objKhachHang.getSoDienThoai() +"");
                soDienThoai = objKhachHang.getSoDienThoai();
                maGioHang = objKhachHang.getMaKH();
                tvDiaChi.setText(objKhachHang.getDiaChi() == null? "?????a ch??? :" + "Ch??a th??m --> Click v??o ????? th??m":"?????a ch??? :" + objKhachHang.getDiaChi());
                setDiemThuong(objKhachHang.getDiemThuong());
                diemThuongAll = objKhachHang.getDiemThuong();
                diachi = objKhachHang.getDiaChi();
                userNameKhachHang = objKhachHang.getUserNameKH();
            }
        }



    }

    private double tinhTien(){
        double TongTien;
        double thue = 0.02;
        double phidichvu = 2000;
        tax = Math.round((gioHangDAO2.getTolalFee(maGioHang+"",maQuyen)*thue)*100)/100;
        double total = Math.round(((gioHangDAO2.getTolalFee(maGioHang+"",maQuyen)+tax)*100)/100);
        btnXacNhan.setText(total+phidichvu +" VND");
        TongTien = total+phidichvu;

        return TongTien;

    }
    private Double tongTienSauKhiGiam(){

        Double finalDiemThuong = Double.valueOf(0);
        String diemThuong = edDiemThuongHoaDon.getText().toString();
        if(diemThuong.isEmpty()){
            diemThuong = String.valueOf(0);
        }
        finalDiemThuong = tinhTien()-Double.parseDouble(diemThuong);
        btnXacNhan.setText(finalDiemThuong  +" VND");
        return  finalDiemThuong;
    }
    public void anhXaView(){
        tvKhachHang = findViewById(R.id.tvKhachHang);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        recyclerViewChiTietHoaDon = findViewById(R.id.recyclerViewChiTietHoaDon);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        toolbar = findViewById(R.id.idToolBar);
        spinnerThanhToan = findViewById(R.id.spinerPhuongThucThanhToan);
        edDiemThuongHoaDon = findViewById(R.id.edDiemThuongHoaDon);
        btnXacNhanDiemThuong = findViewById(R.id.btnXacNhanDiemThuong);
        constraintManHinhXacNhan = findViewById(R.id.constraintManHinhXacNhan);
        linearLayoutDiaChi = findViewById(R.id.linearLayoutDiaChi);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvDiemThuongHoaDon = findViewById(R.id.tvDiemThuongHoaDon);
    }
    public void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewChiTietHoaDon.setLayoutManager(linearLayoutManager);
        cartListAdapter = new CartListComfirmAdapter(list);
        recyclerViewChiTietHoaDon.setAdapter(cartListAdapter);
        RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerViewChiTietHoaDon.addItemDecoration(itemDecoration);

    }
    private void thongTinHoaDon(){
        SharedPreferences preferences = getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        String quyen = (preferences.getString("quyen",""));
        if(quyen.equals("nhanvien")){
            objNhanVien = nhanVienDAO.getUserName(userName);
            if(objNhanVien != null){
                maNV = objNhanVien.getMaNV();
            }
        }else if(quyen.equals("khachhang")){
            objKhachHang = khachHangDAO.getUserName(userName);
            if(objKhachHang != null){
                maKH = objKhachHang.getMaKH();

            }
        }
    }
    public void showDialogThemKhachHang(Context context, int gravity) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_sua_thanh_vien);


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
        TextView tvTitleDialog = dialog.findViewById(R.id.tvThanhVien);
        tvTitleDialog.setText("Th??m kh??ch h??ng");
        EditText edTenKH=dialog.findViewById(R.id.edTenThanhVien);
        EditText edSoDienThoai=dialog.findViewById(R.id.edSoDienThoai);


        KhachHangDAO khachHangDAOThem = new KhachHangDAO(context);
        Button btnThem = dialog.findViewById(R.id.btnLuu);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenKH= edTenKH.getText().toString().trim();
                String soDienThoai =edSoDienThoai.getText().toString().trim();
                Double checkSoDienThoai = null;
                Long check = null;
                if(tenKH.length() == 0 ||soDienThoai.length() == 0){
                    snackBar(R.layout.custom_snackbar_error2,"Kh??ng ???????c ????? tr???ng");
                    return;
                }

                try {
                    checkSoDienThoai = Double.parseDouble(soDienThoai);

                }catch (NumberFormatException e){
                    snackBar(R.layout.custom_snackbar_error2,"S??? ??i???n tho???i ph???i l?? s??? ");
                    e.printStackTrace();
                    return;
                }
                if(!(soDienThoai.length() >= 10 && soDienThoai.length() <=12)){
                    snackBar(R.layout.custom_snackbar_error2,"S??? ??i???n tho???i ????? d??i kho???ng 10-12 k?? t???");
                    return;
                }
                try {
                    check =  Long.parseLong(soDienThoai);

                }catch (NumberFormatException e){
                    snackBar(R.layout.custom_snackbar_error2,"Kh??ng th??? parse");
                    e.printStackTrace();
                    return;
                }



                KhachHang khachHang= new KhachHang();
                khachHang.setTenKH(tenKH);
                khachHang.setSoDienThoai(check);
                long kq =khachHangDAOThem.insert(khachHang);

                if (kq>0){
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Th??m th??nh c??ng");
                    edTenKH.setText("");
                    edSoDienThoai.setText("");
                    dialog.dismiss();

                }else {
                    snackBar(R.layout.custom_snackbar_error_that_bai,"Th??m th???t b???i");
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}