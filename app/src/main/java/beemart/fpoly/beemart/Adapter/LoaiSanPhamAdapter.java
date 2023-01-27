package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.LoaiSanPhamDAO;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.Fragment.FragmentLoaiSanPham;
import beemart.fpoly.beemart.R;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSanPham> list;
    LoaiSanPhamDAO loaiSanPhamDAO;
    LoaiSanPham loaiSanPham;
    private FragmentLoaiSanPham fragmentLoaiSanPham;

    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> list, LoaiSanPhamDAO loaiSanPhamDAO, FragmentLoaiSanPham fragmentLoaiSanPham) {
        this.context = context;
        this.list = list;
        this.loaiSanPhamDAO = loaiSanPhamDAO;
        this.fragmentLoaiSanPham = fragmentLoaiSanPham;
    }

    public LoaiSanPhamAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view =inflater.inflate(R.layout.custom_list_loai_san_pham,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        LoaiSanPham loaiSanPham = list.get(position);
        if (loaiSanPham == null) {
            return;
        } else {

            holder.tvLoaiSP.setText("" + list.get(position).getTenLoaiSP());
        }

        //sua
        holder.imSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             fragmentLoaiSanPham.showDialogSuaLoaiSP(position,list,context,Gravity.CENTER,loaiSanPham,LoaiSanPhamAdapter.this);
            }
        });
        //xoa
        holder.imXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fragmentLoaiSanPham.showDialogXoaLoaiSP(position,list,context,Gravity.CENTER,loaiSanPham,LoaiSanPhamAdapter.this);
            }
        });






    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLoaiSP;
        ImageView imSua,imXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoaiSP = itemView.findViewById(R.id.tvTenLoaiSanPham);
            imSua = itemView.findViewById(R.id.imSua);
            imXoa= itemView.findViewById(R.id.imXoa);
        }
    }

    ///sua







    //xoa

    public void showDialogXoaLoaiSP(int idLoaiSanPham,ArrayList<LoaiSanPham> list,Context context, int gravity,LoaiSanPham loaiSanPham) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Xoa That Bai", Toast.LENGTH_SHORT).show();

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
    private void loadData(){
        list.clear();
        list= (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        notifyDataSetChanged();
    }
}
