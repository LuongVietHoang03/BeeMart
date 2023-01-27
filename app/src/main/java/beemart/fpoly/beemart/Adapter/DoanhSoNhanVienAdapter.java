package beemart.fpoly.beemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.DoanhSoNhanVien;
import beemart.fpoly.beemart.DTO.DoanhThuNhanVien;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DoanhThuNhanVienActivity;
import beemart.fpoly.beemart.Fragment.FragmentDoanhSoNhanVien;
import beemart.fpoly.beemart.Fragment.FragmentKhachHang;
import beemart.fpoly.beemart.R;

public class DoanhSoNhanVienAdapter extends RecyclerView.Adapter<DoanhSoNhanVienAdapter.ViewHolder>  {

    private ArrayList<DoanhSoNhanVien> list;
    private Context context;
    private FragmentDoanhSoNhanVien fragmentDoanhSoNhanVien;
    public DoanhSoNhanVienAdapter(ArrayList<DoanhSoNhanVien> list, Context context,FragmentDoanhSoNhanVien fragmentDoanhSoNhanVien) {
        this.list = list;
        this.context = context;
        this.fragmentDoanhSoNhanVien = fragmentDoanhSoNhanVien;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view =inflater.inflate(R.layout.custom_list_doanh_thu_nhan_vien_admin,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DoanhSoNhanVien objDoanhSo = list.get(position);
        if (objDoanhSo==null){
            return;
        }
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        NhanVien nhanVien = nhanVienDAO.getID(objDoanhSo.getMaNV()+"");
        holder.tvTenNhanVien.setText(nhanVien.getTenNV()+"");
        holder.tvTongsohoadon.setText(objDoanhSo.getSoLuong()+"");
        holder.tvTongdoanhthu.setText(objDoanhSo.getTongDoanhThu()+""+"VNĐ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DoanhThuNhanVienActivity.class);
                intent.putExtra("maNV",objDoanhSo.getMaNV()+"");
                context.startActivity(intent);
                fragmentDoanhSoNhanVien.getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

        private TextView tvTenNhanVien, tvTongsohoadon, tvTongdoanhthu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvTongsohoadon = itemView.findViewById(R.id.tvTongsohoadon);
            tvTongdoanhthu = itemView.findViewById(R.id.tvTongdoanhthu);





        }
    }







}

