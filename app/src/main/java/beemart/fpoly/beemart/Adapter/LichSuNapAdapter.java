package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.Fragment.FragmentLichSuNap;
import beemart.fpoly.beemart.R;

public class LichSuNapAdapter extends RecyclerView.Adapter<LichSuNapAdapter.ViewHolder>{
    private ArrayList<ViTien> list;
    private Context context;
    private FragmentLichSuNap fragmentLichSuNap;
    ViTienDAO viTienDAO;
    ViTien viTien;
    NumberFormat formatter = new DecimalFormat("#,###");

    private static final DecimalFormat df = new DecimalFormat("0");
    public LichSuNapAdapter(ArrayList<ViTien> list, Context context, ViTienDAO viTienDAO) {
        this.list = list;
        this.context = context;
        this.viTienDAO = viTienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.custom_list_lich_su_nap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         ViTien objViTien = list.get(position);
        if (objViTien==null){
            return;
        }else {
            //        ViTienDAO viTienDAO = new ViTienDAO(context);
            holder.tvMaGD.setText(objViTien.getMaGiaoDich()+"");
            holder.tvTrangThai.setText(objViTien.getTrangThai()+"");
            holder.tvThoiGian.setText(objViTien.getThoiGian());
            holder.tvSoTien.setText(df.format(objViTien.getTienNap())+" VND");
            Log.d("abc", "magd: "+objViTien.getMaGiaoDich());
            if(objViTien.getTrangThai() == 1){
                holder.tvTrangThai.setText("Chờ xác nhận");
                holder.tvTrangThai.setTextColor(Color.MAGENTA);
            }else if(objViTien.getTrangThai() == 2){
                holder.tvTrangThai.setText("Thành công");
                holder.tvTrangThai.setTextColor(Color.GREEN);
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
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);

            tvThoiGian=itemView.findViewById(R.id.tvThoiGian);
            tvSoTien= itemView.findViewById(R.id.tvSoTien);
        }
    }
}
