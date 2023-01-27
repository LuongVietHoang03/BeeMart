package beemart.fpoly.beemart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DAO.HoaDonChiTietDAO;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.HoaDonChiTiet;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.Order;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.ViBeePay.NapTien;

public class BottomSheetDialogXacNhanHoaDon extends BottomSheetDialogFragment {
    private static final String KEY_PUT = "order";
    private static final int MAX = 99999;
    private static final int MIN = 10000;
    private Order mOrder;
    private HoaDonDAO hoaDonDAO;
    private TextView tvTienDonHang,tvNameandSodienThoai,tvDiaChi,tvSanPham,tvPhuongThucThanhToan,tvThanhToanTaiQuay;
    private Button btnXacNhanDonHang,btnHuyNhanDonHang;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private GioHangDAO2 gioHangDAO2;
    private ArrayList<GioHang> listGioHang;
    private ArrayList<HoaDon> listHoaDon;
    private KhachHangDAO khachHangDAO;
    private KhachHang khachHang;
    private LinearLayout linerBottomSheet;
    private Dialog dialog;
    private LinearLayout dialogPros;
    private SanPhamDAO sanPhamDAO;
    private SanPham objSanPham;
    public static BottomSheetDialogXacNhanHoaDon newInstance(Order order){
        BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogXacNhanHoaDon();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PUT,order);
        bottomSheetDialogFragment.setArguments(bundle);
        return (BottomSheetDialogXacNhanHoaDon) bottomSheetDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle  = getArguments();
        if(bundle!= null){
            mOrder = (Order) bundle.get(KEY_PUT);

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_bheet_thong_bao,null);
        bottomSheetDialog.setContentView(view);
        initView(view);
        setDataOrder();
        hoaDonDAO = new HoaDonDAO(getContext());
        btnHuyNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        khachHangDAO = new KhachHangDAO(getContext());
        btnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrder.getMaKH() != 0 && mOrder.getPhuongThucThanhToan() == 1 && mOrder.getMaNV() != 0){
                    khachHang = khachHangDAO.getID(mOrder.getMaKH()+"");
                    if(mOrder.getTongTien() > khachHang.getViTien()){

                        toastThongBao(getContext(),R.layout.custom_snackbar_error2,R.id.linerError,"Số dư ví trong tài khoản khách hàng không đủ");
                        return;
                    }
                }



                if(mOrder.getMaKH() != 0 && mOrder.getPhuongThucThanhToan() == 1){
                    khachHang = khachHangDAO.getID(mOrder.getMaKH()+"");
                    if(mOrder.getTongTien() > khachHang.getViTien()){
                        diaLogKhongDuTien(getContext(),Gravity.CENTER);
                        return;
                    }
                    khachHang.setViTien(khachHang.getViTien() - mOrder.getTongTien());
                    int kq = khachHangDAO.update(khachHang);
                    if(kq > 0){
                        Log.d("zzzzzz", "Thanh toán thành công ");
                    }else{
                        Log.d("zzzzzz", "Thanh toán thất bại");
                    }
                    
                    
                }
                xuatHoaDon();
            }
        });
        return bottomSheetDialog;
    }

    private void xuatHoaDon() {
        String date = sdf.format(new Date());
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER",Context.MODE_PRIVATE);
        String quyen = (preferences.getString("quyen",""));
        HoaDon obj = new HoaDon();
        Random random = new Random();
        int numberRandom;
        do{
            numberRandom = random.nextInt(MAX - MIN + 1) + MIN;
        }while (!(hoaDonDAO.checkMaHoaDon(numberRandom+"") < 0));
        obj.setMaHoaDon(numberRandom);
        if(mOrder.getMaKH() != 0){
            obj.setMaKH(mOrder.getMaKH());

        }
        if(mOrder.getMaNV() != 0){
            obj.setMaNV(mOrder.getMaNV());
        }
        obj.setNgayMua(date);
        obj.setTongTien(mOrder.getTongTien());
        if(quyen.equals("nhanvien")){
            obj.setTrangThai(4);
        }else{
            obj.setTrangThai(1);
        }
        obj.setMaCuaHang(2);
        long kq = hoaDonDAO.insert(obj);
        if(kq > 0){
            insertHoaDonChiTiet(numberRandom);
            if(mOrder.getMaKH() != 0){
                khachHangDAO = new KhachHangDAO(getContext());
                khachHang = khachHangDAO.getID(mOrder.getMaKH()+"");
                khachHang.setDiemThuong( (khachHang.getDiemThuong() + khachHang.TinhDiemThuong(mOrder.getTongTien())) - mOrder.getDiemThuong());
                int updateDiem = khachHangDAO.update(khachHang);
                if(updateDiem >0){
                    Log.d("zzzzz", "Thêm điểm thưởng thành công: ");
                }else{
                    Log.d("zzzzz", "thêm điểm thưởng thất bại: ");
                }
            }

        }else{
            Log.d("zzzzzz", "Thanh toán thất bại ");
        }
    }

    private void insertHoaDonChiTiet(int maHoaDon) {
        gioHangDAO2 = new GioHangDAO2(getContext());
        sanPhamDAO = new SanPhamDAO(getContext());
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getContext());
        listGioHang = gioHangDAO2.getListCart(mOrder.getMaOrder()+"",mOrder.getMaQuyen());//check lại
        for(GioHang obj:listGioHang){
            HoaDonChiTiet objHoaDonChiTiet = new HoaDonChiTiet();
            objHoaDonChiTiet.setMaHoaDon(maHoaDon);
            objHoaDonChiTiet.setMaSP(obj.getMaSP());
            objHoaDonChiTiet.setDonGia(obj.getGiaSP());
            objHoaDonChiTiet.setSoLuong(obj.getSoLuong());
            objSanPham = sanPhamDAO.getID(objHoaDonChiTiet.getMaSP()+"");
            if(objSanPham != null){
                objSanPham.setSoLuong(objSanPham.getSoLuong() - objHoaDonChiTiet.getSoLuong());
                int kq = sanPhamDAO.update(objSanPham);
                if(kq > 0){
                    Log.d("zzzzzz", "Sửa số lượng thành công ");
                }else{
                    Log.d("zzzzzz", "Sửa số lượng thất bại ");
                }
            }
            long kq = hoaDonChiTietDAO.insert(objHoaDonChiTiet);
            if(kq >0){
                Log.d("zzzzzz", "thêm vào hóa đơn chi tiết thành công: ");
                gioHangDAO2.deleteGioHang(mOrder.getMaOrder()+"",mOrder.getMaQuyen());

            }else{
                Log.d("zzzzzz", "thêm vào hóa đơn chi tiết thất bại ");
            }
        }
        dialogLoading(getContext(),Gravity.CENTER);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(),TabLayoutActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        },1400);



    }
    @Override
    public void onDetach() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        super.onDetach();
    }

    private void toastThongBao(Context context,int idLayout,int idViewGroup ,String s){
        Toast toast  = new Toast(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(idLayout,(ViewGroup) getActivity().findViewById(idViewGroup));
        TextView tvError = view.findViewById(R.id.tvError);
        tvError.setText(s);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER ,0,20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(dialogPros, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    public void snackBarMain(int layout,String s) {
        Snackbar snackbar = Snackbar.make(linerBottomSheet, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
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
        dialogPros = dialog.findViewById(R.id.dialogPros);
        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Thanh toán thành công vui lòng chờ !");
        dialog.show();
    }
    private void diaLogKhongDuTien(Context context,int gravity){
        dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_khong_du_tien);

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
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        Button btnNapTien = dialog.findViewById(R.id.btnNapTien);
        btnHuy.setOnClickListener(v -> dialog.dismiss());
        btnNapTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, NapTien.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void setDataOrder() {
        if(mOrder == null){
            return;
        }
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER",Context.MODE_PRIVATE);
        String quyen = (preferences.getString("quyen",""));
        tvTienDonHang.setText(mOrder.getTongTien()+" VND");
        tvSanPham.setText(mOrder.getListProduct());
        tvDiaChi.setText(mOrder.getDiaChi() == null ? "Địa chỉ: không có" : "Địa chỉ : "+ mOrder.getDiaChi());
        if(quyen.equals("nhanvien")){
            tvThanhToanTaiQuay.setVisibility(View.VISIBLE);
        }else{
            tvThanhToanTaiQuay.setVisibility(View.INVISIBLE);
        }
        if(mOrder.getTenKhachHang() ==null && mOrder.getSoDienThoai()==null && mOrder.getDiaChi()==null){
            tvNameandSodienThoai.setText("Khách tại của hàng");
        }else{
            tvNameandSodienThoai.setText(mOrder.getTenKhachHang() + " - 0" + mOrder.getSoDienThoai());
        }
        if(mOrder.getPhuongThucThanhToan() == 1){
            tvPhuongThucThanhToan.setText("Ví BeePay");
        }else if(mOrder.getPhuongThucThanhToan() == 2){
            tvPhuongThucThanhToan.setText("Thanh toán khi nhận hàng");
        }

    }

    private void initView(View view){
        tvTienDonHang = view.findViewById(R.id.tvTienDonHang);
        tvSanPham = view.findViewById(R.id.tvSanPham);
        tvPhuongThucThanhToan = view.findViewById(R.id.tvPhuongThucThanhToan);
        tvNameandSodienThoai = view.findViewById(R.id.tvNameandSodienThoai);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        btnXacNhanDonHang = view.findViewById(R.id.btnXacNhanDonHang);
        btnHuyNhanDonHang = view.findViewById(R.id.btnHuyNhanDonHang);
        tvThanhToanTaiQuay = view.findViewById(R.id.tvThanhToanTaiQuay);
        linerBottomSheet = view.findViewById(R.id.linerBottomSheet);
    }
}
