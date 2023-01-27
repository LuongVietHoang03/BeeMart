package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DAO.HoaDonChiTietDAO;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.HoaDonChiTiet;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.R;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.ViewHolder> {
    private Context context;
    private List<HoaDonChiTiet> list;

    public HoaDonChiTietAdapter(Context context, List<HoaDonChiTiet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_hoa_don_chi_tiet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonChiTiet hoaDonChiTiet = list.get(position);
        if (hoaDonChiTiet == null) {
            return;
        } else {
            holder.tvMaHoaDon.setText(""+hoaDonChiTiet.getMaHoaDon());
            SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
            SanPham sanPham = sanPhamDAO.getID(hoaDonChiTiet.getMaSP()+"");
            if (sanPham!=null){
                holder.tvTenSanPham.setText(""+sanPham.getTenSP());
                if(sanPham.getAnhSP() == null){
                    Picasso.get().load(sanPham.getLinkAnhSP()).into(holder.imgAvataSanPham);
                }else if(sanPham.getLinkAnhSP() == null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getAnhSP(),0,sanPham.getAnhSP().length);
                    holder.imgAvataSanPham.setImageBitmap(bitmap);
                }
            }
            holder.tvSoLuong.setText(""+hoaDonChiTiet.getSoLuong());
            holder.tvTongTien.setText(hoaDonChiTiet.getDonGia() +" VND");
        }
    }

    @Override
    public int getItemCount() {
        if (list.size()!=0){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaHoaDon;
        private TextView tvTenSanPham;
        private TextView tvSoLuong;
        private TextView tvTongTien;
        private ImageView imgAvataSanPham;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            imgAvataSanPham = itemView.findViewById(R.id.imgAvataSanPham);
        }
    }
}
