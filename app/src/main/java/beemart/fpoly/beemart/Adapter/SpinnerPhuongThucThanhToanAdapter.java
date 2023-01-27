package beemart.fpoly.beemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.PhuongThucThanhToan;
import beemart.fpoly.beemart.R;

public class SpinnerPhuongThucThanhToanAdapter extends ArrayAdapter<PhuongThucThanhToan> {
    private Context context;
    private ArrayList<PhuongThucThanhToan> lists;
    TextView tvVi;
    ImageView imgBieuTuong;


    public SpinnerPhuongThucThanhToanAdapter(@NonNull Context context, ArrayList<PhuongThucThanhToan> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_list_phuong_thuc_thanh_toan, null);
        }
        final PhuongThucThanhToan obj = lists.get(position);
        if (obj != null) {
            tvVi = v.findViewById(R.id.tvVi);
            imgBieuTuong= v.findViewById(R.id.imgBieuTuong);
            tvVi.setText(obj.getTitle());
            imgBieuTuong.setImageResource(obj.getImg());
        }
        return v;
    }

    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_list_phuong_thuc_thanh_toan, null);
        }
        final PhuongThucThanhToan obj = lists.get(position);
        if (obj != null) {
            tvVi = v.findViewById(R.id.tvVi);
            imgBieuTuong= v.findViewById(R.id.imgBieuTuong);
            tvVi.setText(obj.getTitle());
            imgBieuTuong.setImageResource(obj.getImg());
        }
        return v;
    }
}
