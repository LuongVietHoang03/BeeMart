package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.Fragment.FragmentManHinhChinh;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.ShowDetalActivity;

public class SanPhamAdapterHorizontal extends RecyclerView.Adapter<SanPhamAdapterHorizontal.SanPhamViewHolder> {

    private ArrayList<SanPham> list;
    private Context context;
    private FragmentManHinhChinh fragmentManHinhChinh;

    public SanPhamAdapterHorizontal(ArrayList<SanPham> list, Context context, FragmentManHinhChinh fragmentManHinhChinh) {
        this.list = list;
        this.context = context;
        this.fragmentManHinhChinh = fragmentManHinhChinh;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_man_hinh_chinh, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
         SanPham obj = list.get(position);
        if (obj == null) {
            return;
        }

        holder.tvTenSanPhamManHinhChinh.setSelected(true);
        holder.tvGiaSanPhamManHinhChinh.setText(obj.getGiamGia() +" VND");
        holder.tvTenSanPhamManHinhChinh.setText(obj.getTenSP());
        if(obj.getAnhSP() == null){
            Picasso.get().load(obj.getLinkAnhSP()).into(holder.imgSanPham);
        }else if(obj.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(obj.getAnhSP(),0,obj.getAnhSP().length);
            holder.imgSanPham.setImageBitmap(bitmap);
        }
        SharedPreferences preferences = context.getSharedPreferences("NAMEUSER",Context.MODE_PRIVATE);
        String quyen = (preferences.getString("quyen",""));
        if((quyen.equals("admin"))){
            holder.imgCartManHinhChinh.setVisibility(View.INVISIBLE);
        }
        holder.imgCartManHinhChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetalActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
                fragmentManHinhChinh.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham, imgCartManHinhChinh;
        TextView tvTenSanPhamManHinhChinh, tvGiaSanPhamManHinhChinh;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            imgCartManHinhChinh = itemView.findViewById(R.id.imgCartManHinhChinh);
            tvTenSanPhamManHinhChinh = itemView.findViewById(R.id.tvTenSanPhamManHinhChinh);
            tvGiaSanPhamManHinhChinh = itemView.findViewById(R.id.tvGiaSanPhamManHinhChinh);

        }
    }
}
