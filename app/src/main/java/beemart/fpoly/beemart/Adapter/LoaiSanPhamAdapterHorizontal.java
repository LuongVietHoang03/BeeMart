package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.R;


public class LoaiSanPhamAdapterHorizontal extends RecyclerView.Adapter<LoaiSanPhamAdapterHorizontal.LoaiSanPhamViewHolder> {
    private ArrayList<LoaiSanPham> list;
    private Context context;

    public LoaiSanPhamAdapterHorizontal(ArrayList<LoaiSanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_san_pham_holizo, parent, false);
        return new LoaiSanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamViewHolder holder, int position) {
        final LoaiSanPham obj = list.get(position);
        if(obj == null){
            return;
        }
        holder.tvTenLoaiSanPham.setText(obj.getTenLoaiSP());
    }

    @Override
    public int getItemCount() {
        if(list!= null){
            return list.size();
        }
        return 0;
    }

    public class LoaiSanPhamViewHolder extends RecyclerView.ViewHolder {
         TextView tvTenLoaiSanPham;
        public LoaiSanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiSanPham = itemView.findViewById(R.id.tvTenLoaiSanPhamHorizontal);
        }
    }

}
