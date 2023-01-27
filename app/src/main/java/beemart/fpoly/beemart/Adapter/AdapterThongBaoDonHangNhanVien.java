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

import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.TabLayoutActivity;
import beemart.fpoly.beemart.ThongBao.DonHangFragment;


public class AdapterThongBaoDonHangNhanVien extends RecyclerView.Adapter<AdapterThongBaoDonHangNhanVien.ViewHolderDonHang> {
    private ArrayList<HoaDon> list;
    private Context context;
    private DonHangFragment donHangFragment;


    public AdapterThongBaoDonHangNhanVien(ArrayList<HoaDon> list, Context context, DonHangFragment donHangFragment) {
        this.list = list;
        this.context = context;
        this.donHangFragment = donHangFragment;
    }

    @NonNull
    @Override
    public ViewHolderDonHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao_don_hang_nhan_vien, parent, false);
        return new ViewHolderDonHang(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDonHang holder, int position) {
        final HoaDon objThongBao = list.get(position);
        if(objThongBao == null){
            return;

        }
        DecimalFormat df = new DecimalFormat("0");
        holder.tvMaDonHang.setText(objThongBao.getMaHoaDon()+"");
        holder.tvTongTienHoaDon.setText(df.format(objThongBao.getTongTien())+" đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TabLayoutActivity.class));
                donHangFragment.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
        private TextView tvMaDonHang,tvTongTienHoaDon;
        public ViewHolderDonHang(@NonNull View itemView) {
            super(itemView);
            tvMaDonHang = itemView.findViewById(R.id.tvMaDonHang);
            tvTongTienHoaDon = itemView.findViewById(R.id.tvTongTienHoaDon);

        }
    }



}
