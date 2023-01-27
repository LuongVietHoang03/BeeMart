package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.Fragment.FragmentKhachHang;
import beemart.fpoly.beemart.R;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<KhachHang> list;
    KhachHangDAO khachHangDAO;
    private FragmentKhachHang fragmentKhachHang;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> list, KhachHangDAO khachHangDAO, FragmentKhachHang fragmentKhachHang) {
        this.context = context;
        this.list = list;
        this.khachHangDAO = khachHangDAO;
        this.fragmentKhachHang = fragmentKhachHang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view =inflater.inflate(R.layout.custom_list_thanh_vien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang thanhVien = list.get(position);
        if (thanhVien==null){
            return;
        }else {
            holder.tvTenKH.setText(""+list.get(position).getTenKH());
            holder.tvSoDienThoai.setText("0"+list.get(position).getSoDienThoai());
            holder.tvDiemThuong.setText(list.get(position).getDiemThuong() == 0 ? "0" : thanhVien.getDiemThuong() +"");

        }
        holder.imSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentKhachHang.showDialogSuaKhachHang(position,list,context,Gravity.CENTER,thanhVien, KhachHangAdapter.this);
            }
        });

        holder.imXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentKhachHang.showDialogXoaKhachHang(position,list,context,Gravity.CENTER,thanhVien, KhachHangAdapter.this);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvTenKH,tvSoDienThoai,tvDiemThuong,tvAnhKH;
        ImageView imSua,imXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKH = itemView.findViewById(R.id.tvTenThanhVien);
            tvSoDienThoai = itemView.findViewById(R.id.tvSoDienThoai);
            tvDiemThuong = itemView.findViewById(R.id.tvDiemThuong);
            imSua = itemView.findViewById(R.id.imSua);
            imXoa= itemView.findViewById(R.id.imXoa);
        }
    }





}
