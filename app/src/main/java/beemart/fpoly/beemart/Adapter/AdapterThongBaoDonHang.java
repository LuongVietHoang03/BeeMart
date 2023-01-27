package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.ThongBaoDonHang;
import beemart.fpoly.beemart.Fragment.FragmentQuanLyNhanVien;
import beemart.fpoly.beemart.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterThongBaoDonHang extends RecyclerView.Adapter<AdapterThongBaoDonHang.ViewHolderDonHang> {
    private ArrayList<HoaDon> list;
    private Context context;


    public AdapterThongBaoDonHang(ArrayList<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDonHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao_don_hang, parent, false);
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
        int tinhTrang = objThongBao.getTrangThai();
        if(tinhTrang == 1){
            holder.tvTinhTrangDonHang.setText("chờ xác nhận");
        }else if(tinhTrang == 2){
            holder.tvTinhTrangDonHang.setText("đã xác nhận");
        }else if(tinhTrang == 3){
            holder.tvTinhTrangDonHang.setText("đang giao");
        }else if(tinhTrang == 4){
            holder.tvTinhTrangDonHang.setText("đã giao");
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
