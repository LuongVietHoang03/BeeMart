package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;


public class AdapterThongBaoNapTien extends RecyclerView.Adapter<AdapterThongBaoNapTien.ViewHolderDonHang> {
    private ArrayList<ViTien> list;
    private Context context;


    public AdapterThongBaoNapTien(ArrayList<ViTien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDonHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao_nap_tien, parent, false);
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
        int tinhTrang = objThongBao.getTrangThai();
        if(tinhTrang == 2){
            holder.tvTinhTrangDonHang.setText("Thành công");
        }else if(tinhTrang == 3){
            holder.tvTinhTrangDonHang.setText("Thất bại");
        }else if(tinhTrang == 1){
            holder.tvTinhTrangDonHang.setText("Chờ xác nhận");
        }
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
            tvTinhTrangDonHang = itemView.findViewById(R.id.tvTinhTrangDonHang);
            tvTongTienHoaDon = itemView.findViewById(R.id.tvTongTienHoaDon);

        }
    }



}
