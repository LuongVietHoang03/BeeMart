package beemart.fpoly.beemart.Adapter;

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

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.Fragment.FragmentQuanLyNhanVien;
import beemart.fpoly.beemart.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterNhanVien extends RecyclerView.Adapter<AdapterNhanVien.ViewHolderNhanVien> {
    private ArrayList<NhanVien> list;
    private ArrayList<NhanVien> oldList;
    private NhanVienDAO nhanVienDAO;
    FragmentQuanLyNhanVien fragmentQuanLyNhanVien;

    public AdapterNhanVien(ArrayList<NhanVien> list, NhanVienDAO nhanVienDAO, FragmentQuanLyNhanVien fragmentQuanLyNhanVien) {
        this.list = list;
        this.nhanVienDAO = nhanVienDAO;
        this.fragmentQuanLyNhanVien = fragmentQuanLyNhanVien;
        this.oldList = list;
    }

    @NonNull
    @Override
    public ViewHolderNhanVien onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false);
        return new ViewHolderNhanVien(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNhanVien holder, int position) {
        final NhanVien objNhanVien = list.get(position);
        if(objNhanVien == null){
            return;

        }
        holder.tvTenNhanVien.setText(objNhanVien.getTenNV());
        holder.iconDelete.setOnClickListener(v -> fragmentQuanLyNhanVien.cusTomDiaLogDelete(v.getContext(), nhanVienDAO,Gravity.CENTER,list ,position,this));
        Bitmap bitmap = BitmapFactory.decodeByteArray(objNhanVien.getAnhNV(),0,objNhanVien.getAnhNV().length);
        holder.imageAvataNhanVien.setImageBitmap(bitmap);
    }
    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }
    public class ViewHolderNhanVien extends RecyclerView.ViewHolder{
        private TextView tvTenNhanVien;
        ImageView iconEdit,iconDelete;
        private CircleImageView imageAvataNhanVien;
        public ViewHolderNhanVien(@NonNull View itemView) {
            super(itemView);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            imageAvataNhanVien = itemView.findViewById(R.id.imageAvataNhanVien);
            iconDelete = itemView.findViewById(R.id.iconDelete);
        }
    }
}
