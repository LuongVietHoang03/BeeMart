package beemart.fpoly.beemart.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.KhachHangAdapter;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentKhachHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKhachHang extends Fragment {
    RecyclerView rclKhachHang;
    FloatingActionButton flbKhachHang;
    KhachHangDAO khachHangDAO;
    ConstraintLayout fragMember;
    TextView tvThongBaoTrong;

    public FragmentKhachHang() {
        // Required empty public constructor
    }

   
    public static FragmentKhachHang newInstance() {
        FragmentKhachHang fragment = new FragmentKhachHang();
      
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
        return inflater.inflate(R.layout.fragment_mem_ber, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclKhachHang =view.findViewById(R.id.rcvThanhVien);
        flbKhachHang= view.findViewById(R.id.fabAddNewThanhVien);
        flbKhachHang.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fragMember = view.findViewById(R.id.fragMember);
        tvThongBaoTrong = view.findViewById(R.id.tvThongBaoTrong);
        khachHangDAO = new KhachHangDAO(getContext());
        loadData();
        flbKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemKhachHang(getContext(),Gravity.CENTER);
            }
        });
    }

    private void loadData(){
        ArrayList<KhachHang> list = (ArrayList<KhachHang>) khachHangDAO.getAll();
        if(list.size() == 0){
            tvThongBaoTrong.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
            rclKhachHang.setLayoutManager(linearLayoutManager);
            KhachHangAdapter khachHangAdapter=new KhachHangAdapter(getContext(),list,khachHangDAO,this);
            rclKhachHang.setAdapter(khachHangAdapter);
            tvThongBaoTrong.setVisibility(View.INVISIBLE);
        }



    }
    public void showDialogXoaKhachHang(int idThanhVien, ArrayList<KhachHang> list, Context context, int gravity, KhachHang khachHang, KhachHangAdapter khachHangAdapter) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);

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
        TextView edKhachHang=dialog.findViewById(R.id.tvDialogDeleteName);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);
        KhachHang objThanhVien = list.get(idThanhVien);

        edKhachHang.setText(""+khachHang.getTenKH());

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int kq= khachHangDAO.delete(khachHang.getMaKH()+"");
                if (kq>0){
                    list.clear();
                    list.addAll(khachHangDAO.getAll());
                    khachHangAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong,"Xóa thành công");

                }else {
                    snackBarThanhCong(R.layout.custom_snackbar_error_that_bai,"Xóa thất bại");

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
        tvTitleDialog.setText("Thêm khách hàng");
        EditText edTenKH=dialog.findViewById(R.id.edTenThanhVien);
        EditText edSoDienThoai=dialog.findViewById(R.id.edSoDienThoai);



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
                    snackBar(R.layout.custom_snackbar_error2,"Không được để trống");
                    return;
                }

                try {
                    checkSoDienThoai = Double.parseDouble(soDienThoai);

                }catch (NumberFormatException e){
                    snackBar(R.layout.custom_snackbar_error2,"Số điện thoại phải là số ");
                    e.printStackTrace();
                    return;
                }
                if(!(soDienThoai.length() >= 10 && soDienThoai.length() <=12)){
                    snackBar(R.layout.custom_snackbar_error2,"Số điện thoại độ dài khoảng 10-12 ký tự");
                    return;
                }
                try {
                   check =  Long.parseLong(soDienThoai);

                }catch (NumberFormatException e){
                    snackBar(R.layout.custom_snackbar_error2,"Không thể parse");
                    e.printStackTrace();
                    return;
                }



                KhachHang khachHang= new KhachHang();
                khachHang.setTenKH(tenKH);
                khachHang.setSoDienThoai(check);
                long kq =khachHangDAO.insert(khachHang);

                if (kq>0){
                    snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong,"Thêm thành công");
                    edTenKH.setText("");
                    edSoDienThoai.setText("");
                    loadData();
                    dialog.dismiss();

                }else {
                    snackBarThanhCong(R.layout.custom_snackbar_error_that_bai,"Thêm thất bại");
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
    public void snackBarThanhCong(int layout,String s) {
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
    public void showDialogSuaKhachHang(int idThanhVien, ArrayList<KhachHang> list, Context context, int gravity, KhachHang thanhVien, KhachHangAdapter thanhVienAdapter) {
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
        EditText edTenThanhVien=dialog.findViewById(R.id.edTenThanhVien);
        EditText edSoDienThoai = dialog.findViewById(R.id.edSoDienThoai);

        Button btnDongY = dialog.findViewById(R.id.btnLuu);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);
        KhachHang objThanhVien = list.get(idThanhVien);

        edTenThanhVien.setText(""+thanhVien.getTenKH());
        edSoDienThoai.setText("0"+thanhVien.getSoDienThoai());


        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thanhVien.getTenKH().equals(edTenThanhVien.getText().toString())
                        &&thanhVien.getSoDienThoai()==(Long.parseLong(edSoDienThoai.getText().toString()))
                ) {
                    snackBar(R.layout.custom_snackbar_error2,"Bạn chưa sửa gì cả , sửa thất bại");
                    dialog.dismiss();
                } else {

                    String tenThanhVien= edTenThanhVien.getText().toString().trim();
                    String soDienThoai =edSoDienThoai.getText().toString().trim();
                    Double checkSoDienThoai = null;
                    Long check = null;
                    if(tenThanhVien.length() == 0 ||soDienThoai.length() == 0){
                        snackBar(R.layout.custom_snackbar_error2,"Không được để trống");
                        return;
                    }
                    try {
                        checkSoDienThoai = Double.parseDouble(soDienThoai);

                    }catch (NumberFormatException e){
                        snackBar(R.layout.custom_snackbar_error2,"Số điện thoại phải là số ");
                        e.printStackTrace();
                        return;
                    }
                    if(!(soDienThoai.length() >= 10 && soDienThoai.length() <=12)){
                        snackBar(R.layout.custom_snackbar_error2,"Số điện thoại độ dài khoảng 10-12 ký tự");
                        return;
                    }
                    try {
                        check =  Long.parseLong(soDienThoai);

                    }catch (NumberFormatException e){
                        snackBar(R.layout.custom_snackbar_error2,"Không thể parse");
                        e.printStackTrace();
                        return;
                    }

                    khachHangDAO = new KhachHangDAO(context);
                    thanhVien.setTenKH(edTenThanhVien.getText().toString());
                    thanhVien.setSoDienThoai(Long.parseLong(edSoDienThoai.getText().toString()));

                    int kq = khachHangDAO.update(thanhVien);
                    if (kq > 0) {
                        list.clear();
                        list.addAll(khachHangDAO.getAll());
                        thanhVienAdapter.notifyDataSetChanged();
                        edTenThanhVien.setText("");
                        edSoDienThoai.setText("");
                        dialog.dismiss();
                        snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong,"Sủa thành công");
                    } else {
                        snackBarThanhCong(R.layout.custom_snackbar_error_that_bai,"Sửa thất bại");
                    }
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
}