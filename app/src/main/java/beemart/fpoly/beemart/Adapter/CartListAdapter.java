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
import beemart.fpoly.beemart.Interface.ChangeCountItemCartList;
import beemart.fpoly.beemart.Interface.ChangeNumberItemCartList;
import beemart.fpoly.beemart.R;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    private ArrayList<GioHang> list;
    private GioHangDAO2 gioHangDAO2;
    private ChangeNumberItemCartList changeNumberItemCartList;
    private Context context;

    public CartListAdapter(ArrayList<GioHang> list, Context context, ChangeNumberItemCartList changeNumberItemCartList) {
        this.list = list;
        this.gioHangDAO2 = new GioHangDAO2(context);
        this.changeNumberItemCartList = changeNumberItemCartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_hoa_don, parent, false);
        return new CartListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        final GioHang obj = list.get(position);
        if(obj == null){
            return;
        }


        if(obj.getAnhSP() == null){
            Picasso.get().load(obj.getLinkAnhSP()).into(holder.imageAvataSanPham);
        }else if(obj.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(obj.getAnhSP(),0,obj.getAnhSP().length);
            holder.imageAvataSanPham.setImageBitmap(bitmap);
        }
        holder.tenSanPham.setText(obj.getTenSP());
        holder.tvGiaGoc.setText(obj.getGiaSP()+" VND");
        holder.tvSoLuong.setText(obj.getSoLuong()+"");
        holder.tvGiaTong.setText((Math.round(obj.getSoLuong()*obj.getGiaSP()*100)/100) +" VND");
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangDAO2.plusNumber(list, position,context,holder.itemView.getRootView(), new ChangeNumberItemCartList() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemCartList.changed();
                    }
                });
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioHangDAO2.minusNumber(list, position,holder.itemView.getContext(), new ChangeNumberItemCartList() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemCartList.changed();
                    }
                });
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

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageAvataSanPham,btnPlus;
        private ImageButton btnMinus;
        private TextView tenSanPham,tvSoLuong,tvGiaGoc,tvGiaTong;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAvataSanPham = itemView.findViewById(R.id.imageAvataSanPham);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tenSanPham = itemView.findViewById(R.id.tenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGoc);
            tvGiaTong = itemView.findViewById(R.id.tvGiaTong);

        }
    }
}
