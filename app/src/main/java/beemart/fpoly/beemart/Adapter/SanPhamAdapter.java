package beemart.fpoly.beemart.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.LoaiSanPhamDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.Fragment.FragmentSanPham;
import beemart.fpoly.beemart.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolderSanPham>{
    private ArrayList<SanPham> list;
    Context context;
    private ArrayList<SanPham> oldList;
    private SanPhamDAO sanphamDAO;
    FragmentSanPham fragmentSanPham;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");


    public SanPhamAdapter(Context context, ArrayList<SanPham> list, SanPhamDAO sanPhamDAO, FragmentSanPham fragmentSanPham) {
        this.context = context;
        this.list = list;
        this.sanphamDAO = sanPhamDAO;
        this.fragmentSanPham = fragmentSanPham;

    }

    @NonNull
    @Override
    public ViewHolderSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_san_pham, parent, false);
        return new ViewHolderSanPham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSanPham holder, @SuppressLint("RecyclerView") int position) {
        final SanPham objSanPham = list.get(position);
        if(objSanPham == null){
            return;

        }
        LoaiSanPhamDAO loaiSanPhamDAO=new LoaiSanPhamDAO(context);
        final LoaiSanPham loaiSanPham = loaiSanPhamDAO.getID(objSanPham.getMaLoai()+"");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,-70,30,30);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMargins(0,0,0,0);
        holder.tvTenSP.setText(objSanPham.getTenSP());
        holder.tvGiaSP.setText(objSanPham.getGiaSP()+" đ");
        holder.tvTenSP.setSelected(true);
        holder.tvGiamGia.setText(objSanPham.getGiamGia()+" đ");
        holder.tvSoluong.setText(objSanPham.getSoLuong()+"");
        if(objSanPham.getGiaSP() == objSanPham.getGiamGia()){
            holder.tvGiaKhuyenMaiTitle.setText("Giá: ");
            holder.tvGiaSP.setVisibility(View.INVISIBLE);
            holder.tvGiaGocTitle.setVisibility(View.INVISIBLE);
            holder.tvGiamGia.setText(objSanPham.getGiamGia()+" đ");
            holder.linerGia.setLayoutParams(params);
        }else {
            holder.tvGiaSP.setVisibility(View.VISIBLE);
            holder.tvGiaGocTitle.setVisibility(View.VISIBLE);
            holder.linerGia.setLayoutParams(params2);
        }
        holder.imgChamThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSanPham.diaLogThongTinChiTiet(context,Gravity.CENTER,objSanPham);
            }
        });
        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSanPham.showDialogXoaLoaiSP(position,list,context,Gravity.CENTER,objSanPham,SanPhamAdapter.this);
            }
        });

        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              fragmentSanPham.showDialogSuaLoaiSP(position,list,context,Gravity.CENTER,objSanPham,SanPhamAdapter.this,loaiSanPham);
            }
        });
        if(objSanPham.getAnhSP() == null){
            Picasso.get().load(objSanPham.getLinkAnhSP()).into(holder.imageAvataSanpham);
        }else if(objSanPham.getLinkAnhSP() == null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(objSanPham.getAnhSP(),0,objSanPham.getAnhSP().length);
            holder.imageAvataSanpham.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolderSanPham extends RecyclerView.ViewHolder{
        private TextView tvTenSP,tvLoaiSP,tvGiaSP,tvGiamGia,tvSoluong,tvNSX,tvHSD,tvGiaKhuyenMaiTitle,tvGiaGocTitle;
        ImageView iconEdit,iconDelete;
        private ImageView imageAvataSanpham,imgChamThan;
        private LinearLayout linerGia;

        public ViewHolderSanPham(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTensanpham);

            tvGiaSP=itemView.findViewById(R.id.tvGiaSP);
            tvGiamGia=itemView.findViewById(R.id.tvGiamGiaSP);
            tvSoluong=itemView.findViewById(R.id.tvSoLuong);
//            tvNSX=itemView.findViewById(R.id.tvNSX);
//            tvHSD=itemView.findViewById(R.id.tvHSD);
            imageAvataSanpham = itemView.findViewById(R.id.imageAvataSanPham);
            iconEdit=itemView.findViewById(R.id.iconUpdate);
            iconDelete = itemView.findViewById(R.id.iconDelete);
            imgChamThan = itemView.findViewById(R.id.imgChamThan);
            tvGiaKhuyenMaiTitle = itemView.findViewById(R.id.tvGiaKhuyenMaiTitle);
            tvGiaGocTitle = itemView.findViewById(R.id.tvGiaGocTitle);
            linerGia = itemView.findViewById(R.id.linerGia);
        }
    }

}
