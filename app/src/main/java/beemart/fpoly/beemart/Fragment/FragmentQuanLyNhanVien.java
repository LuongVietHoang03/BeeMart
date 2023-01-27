package beemart.fpoly.beemart.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.AdapterNhanVien;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuanLyNhanVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQuanLyNhanVien extends Fragment {
    FloatingActionButton fabAddNew;
    CircleImageView avataNhanVien;
    EditText edTenNhanVien,edUserName,edPassWord;
    Button btnAddNewNhanVien,btnHuy;
    Bitmap bitmapSql = null;
    NhanVienDAO nhanVienDAO;
    ConstraintLayout frmQuanLyThanhVien;
    RecyclerView recyclerView;
    Boolean check = true;
    ArrayList<NhanVien> list;
    AdapterNhanVien adapterNhanVien;
    private TextView tvThongBaoTrong;
    private  SearchView searchView;
    public FragmentQuanLyNhanVien() {
        // Required empty public constructor
    }


    public static FragmentQuanLyNhanVien newInstance() {
        FragmentQuanLyNhanVien fragment = new FragmentQuanLyNhanVien();

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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_quan_ly_nhan_vien, container, false);
    }

    @SuppressLint("WrongThread")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAddNew = view.findViewById(R.id.fabAddNewNhanVien);
        //set màu cho nút
        fabAddNew.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        nhanVienDAO = new NhanVienDAO(getContext());
        tvThongBaoTrong = view.findViewById(R.id.tvThongBaoTrong);

        frmQuanLyThanhVien = view.findViewById(R.id.frmQuanLyThanhVien);
        recyclerView = view.findViewById(R.id.rcvNhanVien);



        list = (ArrayList<NhanVien>) nhanVienDAO.getAll();
        adapterNhanVien = new AdapterNhanVien(list, nhanVienDAO, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterNhanVien);



        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusTomDiaLogAddNewNhanVien(getContext(),nhanVienDAO,Gravity.CENTER,list,adapterNhanVien);
            }
        });
    }


    // tra lại ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                avataNhanVien.setImageBitmap(bitmap);
                bitmapSql = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    public void snackBar(int layout,String s) {
        Snackbar snackbar = Snackbar.make(frmQuanLyThanhVien, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    public void cusTomDiaLogDelete(Context context, NhanVienDAO nhanVienDAO, int gravity, ArrayList<NhanVien> list, int idNhanVien,AdapterNhanVien adapterNhanVien) {
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
        TextView tvDialogDeleteName = dialog.findViewById(R.id.tvDialogDeleteName);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        NhanVien objNhanVien = list.get(idNhanVien);
        tvDialogDeleteName.setText(objNhanVien.getTenNV());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = nhanVienDAO.delete(objNhanVien.getMaNV()+"");
                if(kq > 0){
                    list.remove(idNhanVien);
                    adapterNhanVien.notifyDataSetChanged();
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Xóa thành công");
                }else{
                    snackBar(R.layout.custom_snackbar_error2,"Xóa thất bại");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void cusTomDiaLogAddNewNhanVien(Context context, NhanVienDAO nhanVienDAO, int gravity, ArrayList<NhanVien> list,AdapterNhanVien adapterNhanVien) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_them_nhan_vien);

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
        edTenNhanVien = dialog.findViewById(R.id.edTenNhanVienDiaLog);
        edPassWord = dialog.findViewById(R.id.edPassWordDialog);
        edUserName = dialog.findViewById(R.id.edUserNameDialog);
        avataNhanVien = dialog.findViewById(R.id.imgAvataNhanVien);
        btnAddNewNhanVien = dialog.findViewById(R.id.btnAddNewNhanVien);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        btnAddNewNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // covert ảnh vào csdl

                if(bitmapSql == null){
                    snackBar(R.layout.custom_snackbar_error2,"Bạn chưa chọn ảnh ");
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapSql.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                byte[] bytesImage = byteArrayOutputStream.toByteArray();
                String tenNhanVien = edTenNhanVien.getText().toString().trim();
                String edUserNameNhanVien = edUserName.getText().toString().trim();
                String passWordNhanVien = edPassWord.getText().toString().trim();
                if(tenNhanVien.length() == 0||
                        edUserNameNhanVien.length() == 0||
                        passWordNhanVien.length() == 0
                ){
                    snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống ");
                    return;
                }
                if(khachHangDAO.checkUserName(edUserNameNhanVien) >0){
                    snackBar(R.layout.custom_snackbar_error2,"Tên tài khoản đã tồn tại trong hệ thống ");
                    return;
                }
                if(!(passWordNhanVien.length() > 7)){
                    snackBar(R.layout.custom_snackbar_error2,"Mật khẩu phải lớn hơn 7 kí tự");
                    return;
                }
                if(nhanVienDAO.checkUser(edUserNameNhanVien) > 0){
                    snackBar(R.layout.custom_snackbar_error2,"Tên tài khoản đã tồn tại trong hệ thống ");
                    return;
                }

                NhanVien objNhanVien = new NhanVien();
                objNhanVien.setTenNV(edTenNhanVien.getText().toString().trim());
                objNhanVien.setUserName(edUserName.getText().toString().trim());
                objNhanVien.setAnhNV(bytesImage);
                objNhanVien.setPassWord(edPassWord.getText().toString().trim());
                long kq = nhanVienDAO.insert(objNhanVien);
                if(kq  > 0){
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Thêm thành công");
                    list.clear();
                    list.addAll(nhanVienDAO.getAll());
                    adapterNhanVien.notifyDataSetChanged();
                    edPassWord.setText("");
                    edTenNhanVien.setText("");
                    edUserName.setText("");
                    bitmapSql = null;
                }else{
                    snackBar(R.layout.custom_snackbar_error2,"Thêm thất bại");
                }
                dialog.dismiss();

            }
        });
        Button btnUpload = dialog.findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,6);
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