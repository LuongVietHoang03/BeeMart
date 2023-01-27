package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.TabLayoutActivity2;
import beemart.fpoly.beemart.TabLayoutActivity3;


public class AdapterThongBaoNapTienAdmin extends RecyclerView.Adapter<AdapterThongBaoNapTienAdmin.ViewHolderDonHang> {
    private ArrayList<ViTien> list;
    private Context context;


    public AdapterThongBaoNapTienAdmin(ArrayList<ViTien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDonHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao_nap_tien_admin, parent, false);
        return new ViewHolderDonHang(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDonHang holder, int position) {
        final ViTien objThongBao = list.get(position);
        if(objThongBao == null){
            return;

        }
        DecimalFormat df = new DecimalFormat("0");
        holder.tvMaDonHang.setText(objThongBao.getMaGiaoDich()+"");
        holder.tvTongTienHoaDon.setText(df.format(objThongBao.getTienNap())+" đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TabLayoutActivity3.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }




    public class ViewHolderDonHang extends RecyclerView.ViewHolder{
        private TextView tvMaDonHang,tvTinhTrangDonHang,tvTongTienHoaDon;
        public ViewHolderDonHang(@NonNull View itemView) {
            super(itemView);
            tvMaDonHang = itemView.findViewById(R.id.tvMaDonHang);
            tvTongTienHoaDon = itemView.findViewById(R.id.tvTongTienHoaDon);

        }
    }



}
