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
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.DTO.ThongKeDoanhThu;
import beemart.fpoly.beemart.Interface.ChangeNumberItemCartList;
import beemart.fpoly.beemart.R;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.CartListViewHolder> {
    private ArrayList<ThongKeDoanhThu> list;
    private Context context;

    public AdapterTop10(ArrayList<ThongKeDoanhThu> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_top_10, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        final ThongKeDoanhThu obj = list.get(position);
        if(obj == null){
            return;
        }
        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        SanPham sanPham = sanPhamDAO.getID(obj.getMaSP()+"");
        if(sanPham.getAnhSP() == null){
            Picasso.get().load(sanPham.getLinkAnhSP()).into(holder.imageAvataSanPham);
        }else if(sanPham.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getAnhSP(),0,sanPham.getAnhSP().length);
            holder.imageAvataSanPham.setImageBitmap(bitmap);
        }
        holder.tenSanPham.setText("Tên sản phẩm: "+sanPham.getTenSP());
        holder.tvSoLuong.setText("Số lượng bán ra: "+obj.getSoLuong()+"");

    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageAvataSanPham;
        private TextView tenSanPham,tvSoLuong;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvataSanPham = itemView.findViewById(R.id.imageAvataSanPham);
            tenSanPham = itemView.findViewById(R.id.tenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);

        }
    }
}
