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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.LoaiSanPhamAdapter;
import beemart.fpoly.beemart.DAO.LoaiSanPhamDAO;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLoaiSanPham#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLoaiSanPham extends Fragment {

  private   ConstraintLayout fragLoaiSanPham;
  private   RecyclerView rcl_loaiSanPham;
  private   FloatingActionButton flb_themLoaiSP;
  private   LoaiSanPhamDAO loaiSanPhamDAO;
  private   TextView tvThongBaoTrong;
    public FragmentLoaiSanPham() {
        // Required empty public constructor
    }

    public static FragmentLoaiSanPham newInstance() {
        FragmentLoaiSanPham fragment = new FragmentLoaiSanPham();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loai_san_pham, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_loaiSanPham = view.findViewById(R.id.rcvLoaiSanPham);
        flb_themLoaiSP=view.findViewById(R.id.flbLoaiSanPham);
        flb_themLoaiSP.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        loaiSanPhamDAO= new LoaiSanPhamDAO(getContext());
        fragLoaiSanPham = view.findViewById(R.id.fragLoaiSanPham);
        tvThongBaoTrong = view.findViewById(R.id.tvThongBaoTrong);
        loadData();
        flb_themLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemLoaiSP(getContext(),Gravity.CENTER);

            }
        });



    }
    private void loadData(){
        ArrayList<LoaiSanPham> list = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        if(list.size() == 0){
            tvThongBaoTrong.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
            rcl_loaiSanPham.setLayoutManager(linearLayoutManager);
            LoaiSanPhamAdapter loaiSanPhamAdapter=new LoaiSanPhamAdapter(getContext(),list,loaiSanPhamDAO,FragmentLoaiSanPham.this);
            rcl_loaiSanPham.setAdapter(loaiSanPhamAdapter);
            tvThongBaoTrong.setVisibility(View.INVISIBLE);
        }



    }
    public void showDialogThemLoaiSP(Context context, int gravity) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_sua_loai_san_pham);


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
        TextView tvTitleDialog = dialog.findViewById(R.id.tvLoaiSanPham);
        tvTitleDialog.setText("Thêm loại sản phẩm");
        EditText edLoaiSanPham=dialog.findViewById(R.id.edTenLoaiSanPham);
        Button btnThem = dialog.findViewById(R.id.btnLuu);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai= edLoaiSanPham.getText().toString().trim();
                LoaiSanPham loaiSanPham = new LoaiSanPham();
                loaiSanPham.setTenLoaiSP(tenLoai);
                if(tenLoai.length() == 0){
                    snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
                    return;
                }
                long kq =loaiSanPhamDAO.insert(loaiSanPham);

                if (kq>0){
                    snackBarThanhCong(R.layout.custom_snackbar_check_mark_thanh_cong);
                    edLoaiSanPham.setText("");
                    loadData();
                    dialog.dismiss();

                }else {
                    snackBar(R.layout.custom_snackbar_error2,"Thêm thất bại");
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

        Snackbar snackbar = Snackbar.make(fragLoaiSanPham, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    public void snackBarThanhCong(int layout) {
        Snackbar snackbar = Snackbar.make(fragLoaiSanPham, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    public void showDialogXoaLoaiSP(int idLoaiSanPham,ArrayList<LoaiSanPham> list,Context context, int gravity,LoaiSanPham loaiSanPham,LoaiSanPhamAdapter loaiSanPhamAdapter) {
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
        TextView edLoaiSanPham=dialog.findViewById(R.id.tvDialogDeleteName);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);
        LoaiSanPham objLoaiSanPham = list.get(idLoaiSanPham);

        edLoaiSanPham.setText(""+loaiSanPham.getTenLoaiSP());

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int kq= loaiSanPhamDAO.delete(loaiSanPham.getMaLoai()+"");
                if (kq>0){
                    list.clear();
                    list.addAll(loaiSanPhamDAO.getAll());
                    loaiSanPhamAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Xóa thành công");

                }else {
                    snackBar(R.layout.custom_snackbar_error_that_bai,"Xóa thất bại");

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

    public void showDialogSuaLoaiSP(int idLoaiSanPham,ArrayList<LoaiSanPham> list,Context context, int gravity,LoaiSanPham loaiSanPham,LoaiSanPhamAdapter loaiSanPhamAdapter) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_sua_loai_san_pham);

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
        EditText edLoaiSanPham=dialog.findViewById(R.id.edTenLoaiSanPham);
        Button btnDongY = dialog.findViewById(R.id.btnLuu);
        Button btnHuy =dialog.findViewById(R.id.btnHuy);
        LoaiSanPham objLoaiSanPham = list.get(idLoaiSanPham);
        edLoaiSanPham.setText(""+loaiSanPham.getTenLoaiSP());
        FragmentLoaiSanPham fragmentLoaiSanPham = new FragmentLoaiSanPham();
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiSanPham =edLoaiSanPham.getText().toString().trim();
                if (loaiSanPham.getTenLoaiSP().equals(edLoaiSanPham.getText().toString()) ) {
                    snackBar(R.layout.custom_snackbar_error2,"Bạn chưa sửa gì cả , sửa thất bại");
                    dialog.dismiss();
                } else {
                    if(tenLoaiSanPham.length() == 0 ){
                        snackBar(R.layout.custom_snackbar_error2,"Không được để trống");
                        return;
                    }
                    loaiSanPhamDAO = new LoaiSanPhamDAO(context);
                    loaiSanPham.setTenLoaiSP(edLoaiSanPham.getText().toString());
                    int kq = loaiSanPhamDAO.update(loaiSanPham);
                    if (kq > 0) {
                        list.clear();
                        list.addAll(loaiSanPhamDAO.getAll());
                        loaiSanPhamAdapter.notifyDataSetChanged();
                        edLoaiSanPham.setText("");
                        dialog.dismiss();
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Sủa thành công");
                    } else {
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong,"Sủa thất bại");
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