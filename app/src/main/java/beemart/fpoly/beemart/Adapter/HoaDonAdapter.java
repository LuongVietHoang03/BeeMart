package beemart.fpoly.beemart.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.HoaDonChiTietActivity;
import beemart.fpoly.beemart.R;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDon> list;
    private HoaDonDAO hoaDonDAO;
//    FragmentManager manager;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list, HoaDonDAO hoaDonDAO) {
        this.context = context;
        this.list = list;
        this.hoaDonDAO = hoaDonDAO;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_hoa_don_final, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SharedPreferences preferences = context.getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        String quyen = (preferences.getString("quyen", ""));
        HoaDon hoaDon = list.get(position);
        if (hoaDon == null) {
            return;
        } else {
            if(quyen.equals("khachhang")){
                holder.imgChiTiet.setVisibility(View.INVISIBLE);
            }else {
                holder.imgChiTiet.setVisibility(View.VISIBLE);
            }
            if(hoaDon.getMaKH() != 0){
                KhachHangDAO khachHangDAO = new KhachHangDAO(context);
                KhachHang khachHang = khachHangDAO.getID(hoaDon.getMaKH() + "");
                if (khachHang != null) {
                    holder.tvTenKhachHang.setText(khachHang.getTenKH());
                }
            }else{
                holder.tvTenKhachHang.setText("Khách hàng tại quầy");
            }
            holder.tvMaHoaDon.setText(hoaDon.getMaHoaDon()+"");
            holder.tvTongTien.setText(hoaDon.getTongTien() + " VND");
            int tinhTrang = hoaDon.getTrangThai();
            if (tinhTrang == 1) {
                holder.tvTinhTrang.setText("Chờ xác nhận");
                holder.tvTinhTrang.setTextColor(Color.RED);
                holder.imgChiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogXacNhanDonHang(context, Gravity.CENTER, position);
                    }
                });
            } else if (tinhTrang == 2) {
                holder.tvTinhTrang.setText("Đã xác nhận");
                holder.tvTinhTrang.setTextColor(Color.GREEN);
                holder.imgChiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogXacNhanDonHang(context, Gravity.CENTER, position);
                    }
                });
            } else if (tinhTrang == 3) {
                holder.tvTinhTrang.setText("Đang giao");
                holder.tvTinhTrang.setTextColor(Color.MAGENTA);
                holder.imgChiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogXacNhanDonHang(context, Gravity.CENTER, position);
                    }
                });
            } else if (tinhTrang == 4) {
                holder.tvTinhTrang.setText("Đã giao");
                holder.tvTinhTrang.setTextColor(Color.CYAN);
            }
            holder.tvNgayMua.setText(hoaDon.getNgayMua());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HoaDonChiTietActivity.class);
                    intent.putExtra("maHoaDon", hoaDon.getMaHoaDon());
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenKhachHang;
        private TextView tvTongTien;
        private TextView tvTinhTrang;
        private ImageView imgChiTiet;
        private TextView tvMaHoaDon;
        private TextView tvNgayMua;
        private ImageView layoutHoaDonChiTiet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKhachHang = itemView.findViewById(R.id.tvTenKhachHang);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            imgChiTiet = itemView.findViewById(R.id.imgChiTiet);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            layoutHoaDonChiTiet = itemView.findViewById(R.id.layoutHoaDonChiTiet);
        }
    }

    private void dialogXacNhanDonHang(Context context, int gravity, int i) {
        Button btnXacNhan;
        Button btnHuy;
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_xac_nhan_don);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnHuy = dialog.findViewById(R.id.btnHuy);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                HoaDon hoaDon = list.get(i);
                if (hoaDon.getTrangThai() == 1) {
                    hoaDon.setTrangThai(2);
                    hoaDonDAO.update(hoaDon);
                    list.remove(i);
                } else if (hoaDon.getTrangThai() == 2) {
                    hoaDon.setTrangThai(3);
                    hoaDonDAO.update(hoaDon);
                    list.remove(i);
                } else if (hoaDon.getTrangThai() == 3) {
                    hoaDon.setTrangThai(4);
                    hoaDonDAO.update(hoaDon);
                    list.remove(i);
                } else if (hoaDon.getTrangThai() == 4) {
                    hoaDonDAO.delete(hoaDon);
                    list.remove(i);
                }
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
