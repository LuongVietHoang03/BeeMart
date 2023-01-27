package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.DoanhSoNhanVien;
import beemart.fpoly.beemart.DTO.DoanhThuNhanVien;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.R;

public class DoanhThuNhanVienAdapter extends RecyclerView.Adapter<DoanhThuNhanVienAdapter.ViewHolder>  {

    private ArrayList<HoaDon> list;
    private Context context;

    public DoanhThuNhanVienAdapter(ArrayList<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view =inflater.inflate(R.layout.custom_list_doanh_thu_nhan_vien,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HoaDon objDoanhSo = list.get(position);
        if (objDoanhSo==null){
            return;
        }

        if(objDoanhSo.getMaKH() == 0){
            holder.tvtenKH.setText("Khách tại của hàng");
        }else{
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            KhachHang khachHang = khachHangDAO.getID(objDoanhSo.getMaKH()+"");
            holder.tvtenKH.setText(khachHang.getTenKH());
        }
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        NhanVien nhanVien = nhanVienDAO.getID(objDoanhSo.getMaNV()+"");
        holder.tvtenNV.setText(nhanVien.getTenNV()+"");
        holder.tvTongtienhoadon.setText(objDoanhSo.getTongTien()+""+"VNĐ");


    }

    @Override
    public int getItemCount() {

        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private TextView tvtenKH, tvtenNV, tvTongtienhoadon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtenKH = itemView.findViewById(R.id.tvtenKH);
            tvtenNV = itemView.findViewById(R.id.tvtenNV);
            tvTongtienhoadon = itemView.findViewById(R.id.tvTongtienhoadon);



        }
    }







}

