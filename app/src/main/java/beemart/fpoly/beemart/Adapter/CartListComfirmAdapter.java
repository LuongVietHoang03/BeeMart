package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.GioHangDAO2;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.Interface.ChangeNumberItemCartList;
import beemart.fpoly.beemart.R;

public class CartListComfirmAdapter extends RecyclerView.Adapter<CartListComfirmAdapter.CartListViewHolder> {
    private ArrayList<GioHang> list;


    public CartListComfirmAdapter(ArrayList<GioHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xac_nhan_hoa_don, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        final GioHang obj = list.get(position);
        if(obj == null){
            return;
        }
        if(obj.getAnhSP() == null){
            Picasso.get().load(obj.getLinkAnhSP()).into(holder.imgSanPham);
        }else if(obj.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(obj.getAnhSP(),0,obj.getAnhSP().length);
            holder.imgSanPham.setImageBitmap(bitmap);
        }
        holder.tvTenSP.setText("Sản phẩm: " +obj.getTenSP());
        holder.tvSoLuong.setText("Số lượng: " +obj.getSoLuong()+"");
        holder.tvThanhTien.setText("Thành tiền: " +(Math.round(obj.getSoLuong()*obj.getGiaSP()*100)/100) +" VND");

    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSanPham;

        private TextView tvTenSP,tvSoLuong,tvThanhTien;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);


        }
    }
}
