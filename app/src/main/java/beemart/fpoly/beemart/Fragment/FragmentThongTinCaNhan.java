package beemart.fpoly.beemart.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.atomic.LongAdder;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThongTinCaNhan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThongTinCaNhan extends Fragment {

    private EditText edSDTKH, edEmailKH, edDiaChiKH, edDiemThuongKH;
    private TextView tvTenKH, tvDiaChi2KH;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    private boolean check = true;
    private FrameLayout fragMember;
    private ArrayList<KhachHang> list = new ArrayList<>();
    private Button btn_editIn4, btn_saveIn4;


    public FragmentThongTinCaNhan() {
        // Required empty public constructor
    }


    public static FragmentThongTinCaNhan newInstance() {
        FragmentThongTinCaNhan fragment = new FragmentThongTinCaNhan();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        khachHangDAO = new KhachHangDAO(getContext());
        tvTenKH = view.findViewById(R.id.tenKhachHang);
        fragMember = view.findViewById(R.id.fragIn4KH);
        tvDiaChi2KH = view.findViewById(R.id.diaChi2);
        edEmailKH = view.findViewById(R.id.edEmailKH);
        edDiaChiKH = view.findViewById(R.id.edDiaChiKH);
        edDiemThuongKH = view.findViewById(R.id.edDiemThuongKH);
        edSDTKH = view.findViewById(R.id.edSDTKH);
        btn_editIn4 = view.findViewById(R.id.btnSua3);
        objKhachHang=new KhachHang();


        edEmailKH.setEnabled(false);
        edDiaChiKH.setEnabled(false);
        edDiemThuongKH.setEnabled(false);
        edSDTKH.setEnabled(false);
        thongTinKhachHang();


        btn_editIn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == true) {
                    edEmailKH.setEnabled(true);
                    edDiaChiKH.setEnabled(true);
                    edEmailKH.setText(objKhachHang.geteMail() == null ? "":objKhachHang.geteMail());
                    edDiaChiKH.setText(objKhachHang.getDiaChi() == null ? "":objKhachHang.getDiaChi());
                    btn_editIn4.setText("Lưu");
                    check = false;
                } else {
                    edEmailKH.setEnabled(false);
                    edDiaChiKH.setEnabled(false);
                    edSDTKH.setEnabled(false);
                    String email = edEmailKH.getText().toString().trim();
                    String soDienThoai = edSDTKH.getText().toString().trim();
                    String diachi = edDiaChiKH.getText().toString().trim();
                    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    Double checkSoDienThoai = null;
                    Long checkk = null;
                    if (email.length() == 0 || soDienThoai.length() == 0 || diachi.length() == 0) {
                        snackBar(R.layout.custom_snackbar_error2, "Không được để trống");
                        edEmailKH.setEnabled(true);
                        edDiaChiKH.setEnabled(true);
                        return;
                    }

                    if (email.matches(emailPattern)) {
                        snackBar(R.layout.custom_snackbar_error2, "Đúng định dạng email");
                    }else{
                        snackBar(R.layout.custom_snackbar_error2, " Không đúng định dạng email");
                        edEmailKH.setEnabled(true);
                        edDiaChiKH.setEnabled(true);
                        return;
                    }
                    objKhachHang.seteMail(edEmailKH.getText().toString());
                    objKhachHang.setDiaChi(edDiaChiKH.getText().toString());

                    int kq = khachHangDAO.update(objKhachHang);
                    if (kq > 0) {
                        list.clear();
                        list.addAll(khachHangDAO.getAll());
                        Log.d("zzz", "Sửa thành công");
                        snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong,"Sửa thành công");
                    } else {
                        Log.d("zzz", "Sửa thất bại");
                        snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong,"Sửa thất bại");
                    }
                    btn_editIn4.setText("Sửa");
                    check =true;
                }

            }
        });
    }

    private void thongTinKhachHang() {
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount", ""));
        String quyen = (preferences.getString("quyen", ""));
        objKhachHang = khachHangDAO.getUserName(userName);
        if (objKhachHang != null) {
            tvTenKH.setText(objKhachHang.getTenKH());
            edEmailKH.setText(objKhachHang.geteMail() == null ? "Chưa có dữ liệu" : objKhachHang.geteMail());
            edSDTKH.setText("0"+objKhachHang.getSoDienThoai() + "");
            edDiaChiKH.setText(objKhachHang.getDiaChi() == null ? "Chưa có dữ liệu" : objKhachHang.getDiaChi());
            edDiemThuongKH.setText(objKhachHang.getDiemThuong() + "");
        }
    }

    public void snackBarThanhCong(int layout, String s) {
        Snackbar snackbar = Snackbar.make(fragMember, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();

    }

    public void snackBar(int layout,String s) {

        Snackbar snackbar = Snackbar.make(fragMember, "", Snackbar.LENGTH_LONG);
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