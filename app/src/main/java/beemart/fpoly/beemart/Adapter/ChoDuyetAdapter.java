package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.Fragment.FragmentChoDuyet;
import beemart.fpoly.beemart.R;

public class ChoDuyetAdapter extends RecyclerView.Adapter<ChoDuyetAdapter.ViewHolder>{
    private ArrayList<ViTien> list;
    private Context context;
    private FragmentChoDuyet fragmentChoDuyet;
    ViTienDAO viTienDAO;
     TextView tvMaGD,tvMaKH,tvTenKH,tvThoiGian,tvSoTien,tvTrangThai;
    private Button btnOK,btnBack,btnThatBai;

    public ChoDuyetAdapter(ArrayList<ViTien> list, Context context, ViTienDAO viTienDAO) {
        this.list = list;
        this.context = context;
        this.viTienDAO = viTienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.custom_list_cho_duyet,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViTien objViTien = list.get(position);
        if (objViTien==null){
            return;
        }else {
//                    ViTienDAO viTienDAO = new ViTienDAO(context);
            holder.tvMaGD.setText("Mã giao dịch:"+objViTien.getMaGiaoDich()+"");
            holder.tvTrangThai.setText(objViTien.getTrangThai()+"");
            holder.tvThoiGian.setText(objViTien.getThoiGian());
            holder.tvSoTien.setText(objViTien.getTienNap()+" VND");
            if (objViTien.getTrangThai()==2){
                holder.tvTrangThai.setTextColor(Color.GREEN);
                holder.tvTrangThai.setText("Thành công");
                holder.itemView.setSelected(false);
            }else if(objViTien.getTrangThai()==1){
                holder.tvTrangThai.setText("Chờ xác nhận");
                holder.tvTrangThai.setTextColor(Color.MAGENTA);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDiaLog(context,Gravity.CENTER,position);
                    }
                });

            }else if(objViTien.getTrangThai() == 3){
                holder.tvTrangThai.setText("Thất bại");
                holder.tvTrangThai.setTextColor(Color.RED);
            }


        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaGD,tvTrangThai,tvThoiGian,tvSoTien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaGD=itemView.findViewById(R.id.tvMaGD);
            tvTrangThai=itemView.findViewById(R.id.tvTrangThai);
            tvThoiGian=itemView.findViewById(R.id.tvThoiGian);
            tvSoTien= itemView.findViewById(R.id.tvSoTien);


        }

    }
    private void showDiaLog(Context context, int gravity,int i){
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_cho_duyet);


        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        tvMaGD =dialog.findViewById(R.id.tvMaGD);
        tvMaKH = dialog.findViewById(R.id.tvMaKH);
        tvThoiGian= dialog.findViewById(R.id.tvThoiGian);
        tvSoTien= dialog.findViewById(R.id.tvSoTien);
        tvTenKH = dialog.findViewById(R.id.tvTenKH);
        btnThatBai = dialog.findViewById(R.id.btnThatBai);
        tvTrangThai= dialog.findViewById(R.id.tvTrangThai);
        btnOK= dialog.findViewById(R.id.btnOK);
        btnBack= dialog.findViewById(R.id.btnBack);
        ViTien viTien = list.get(i);
        tvMaGD.setText(viTien.getMaGiaoDich()+"");
        tvSoTien.setText(viTien.getTienNap()+"");
        tvThoiGian.setText(viTien.getThoiGian());
        tvMaKH.setText(viTien.getMaKH()+"");
       if ( viTien.getTrangThai()==1){
            tvTrangThai.setText("Chờ xác nhận");
            tvTrangThai.setTextColor(Color.MAGENTA);
        }else if (viTien.getTrangThai()==2){
         tvTrangThai.setTextColor(Color.GREEN);
        tvTrangThai.setText("Thành công");
       }

        KhachHangDAO khachHangDAO= new KhachHangDAO(context);
       KhachHang khachHang = khachHangDAO.getID(viTien.getMaKH()+"");
       tvTenKH.setText(khachHang.getTenKH());
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViTienDAO viTienDAO = new ViTienDAO(context);
                ViTien viTien = list.get(i);
                viTien.setTrangThai(2);
                viTienDAO.update(viTien);
                KhachHang khachHang = khachHangDAO.getID(viTien.getMaKH()+"");
                if (khachHang!=null){
                    khachHang.setViTien(khachHang.getViTien()+viTien.getTienNap());
                    khachHangDAO.update(khachHang);
                }
                list.remove(i);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnThatBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViTienDAO viTienDAO = new ViTienDAO(context);
                ViTien viTien = list.get(i);
                viTien.setTrangThai(3);
                viTienDAO.update(viTien);
                list.remove(i);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
