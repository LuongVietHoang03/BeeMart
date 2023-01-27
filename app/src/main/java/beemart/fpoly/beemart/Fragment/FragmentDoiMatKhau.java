package beemart.fpoly.beemart.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDoiMatKhau#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDoiMatKhau extends Fragment {
    EditText edOldPassWordDiaLog, edNewPassWordDialog, edRePassWordDialog;
    Button btnDoiMatKhauNhanVien;
    RelativeLayout fragDoiMatKhau;
    NhanVienDAO nhanVienDAO;
    NhanVien objNhanVien;
    private KhachHangDAO khachHangDAO;
    private KhachHang khachHang;
    public FragmentDoiMatKhau() {

    }


    public static FragmentDoiMatKhau newInstance() {
        FragmentDoiMatKhau fragment = new FragmentDoiMatKhau();
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
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edOldPassWordDiaLog = view.findViewById(R.id.edOldPassWordDiaLog);
        edNewPassWordDialog = view.findViewById(R.id.edNewPassWordDialog);
        edRePassWordDialog = view.findViewById(R.id.edRePassWordDialog);
        fragDoiMatKhau = view.findViewById(R.id.fragDoiMatKhau);
        btnDoiMatKhauNhanVien =view.findViewById(R.id.btnDoiMatKhauNhanVien);
        nhanVienDAO = new NhanVienDAO(getContext());
        khachHangDAO = new KhachHangDAO(getContext());
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        String quyen = (preferences.getString("quyen",""));
        Bundle extra = getActivity().getIntent().getExtras();
        if(quyen.equals("nhanvien")){
            if (extra != null) {
                String username = extra.getString("User_name");
                btnDoiMatKhauNhanVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkValidate() > 0){
                            objNhanVien =  nhanVienDAO.getUserName(username);
                            String oldPass = edOldPassWordDiaLog.getText().toString().trim();
                            String newPass = edNewPassWordDialog.getText().toString().trim();
                            String reNewPass = edRePassWordDialog.getText().toString().trim();

                            if(!objNhanVien.getPassWord().equals(oldPass)){
                                snackBar(R.layout.custom_snackbar_error2,"Mật khẩu cũ sai");
                                return;
                            }else if( !(newPass.equals(reNewPass) && reNewPass.equals(newPass) )){
                                snackBar(R.layout.custom_snackbar_error2,"Mật khẩu không trùng khớp");
                                return;
                            }
                            objNhanVien.setPassWord(newPass);
                            if(nhanVienDAO.updatePass(objNhanVien) > 0){
                                snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Đổi thành công");
                                edOldPassWordDiaLog.setText("");
                                edNewPassWordDialog.setText("");
                                edRePassWordDialog.setText("");
                            }else{
                                snackBar(R.layout.custom_snackbar_error2,"Đổi thất bại");
                            }
                        }
                    }
                });
            }
        }else if(quyen.equals("khachhang")){
            if (extra != null) {
                String username = extra.getString("User_name");
                btnDoiMatKhauNhanVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkValidate() >0){
                            khachHang = khachHangDAO.getUserName(username);
                            String oldPass = edOldPassWordDiaLog.getText().toString().trim();
                            String newPass = edNewPassWordDialog.getText().toString().trim();
                            String reNewPass = edRePassWordDialog.getText().toString().trim();
                            if(!khachHang.getPasswordKH().equals(oldPass)){
                                snackBar(R.layout.custom_snackbar_error2,"Mật khẩu cũ sai");
                                return;
                            }else if( !(newPass.equals(reNewPass) && reNewPass.equals(newPass) )){
                                snackBar(R.layout.custom_snackbar_error2,"Mật khẩu không trùng khớp");
                                return;
                            }
                            khachHang.setPasswordKH(newPass);
                            if(khachHangDAO.update(khachHang) > 0){
                                snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Đổi thành công");
                                edOldPassWordDiaLog.setText("");
                                edNewPassWordDialog.setText("");
                                edRePassWordDialog.setText("");
                            }else{
                                snackBar(R.layout.custom_snackbar_error2,"Đổi thất bại");
                            }
                        }
                    }
                });
            }
        }






    }

    public int checkValidate() {
        int check = 1;
        if (edOldPassWordDiaLog.getText().toString().trim().length() == 0 ||
                edNewPassWordDialog.getText().toString().trim().length() == 0 ||
                edRePassWordDialog.getText().toString().trim().length() == 0
        ) {
            snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
             check = -1;
        }
        return check;
    }

    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(fragDoiMatKhau, "", Snackbar.LENGTH_LONG);
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