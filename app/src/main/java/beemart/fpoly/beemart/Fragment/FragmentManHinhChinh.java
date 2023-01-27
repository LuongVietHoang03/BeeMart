package beemart.fpoly.beemart.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.LoaiSanPhamAdapterHorizontal;
import beemart.fpoly.beemart.Adapter.SanPhamAdapterHorizontal;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.LoaiSanPhamDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.SearchFillterItem;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManHinhChinh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManHinhChinh extends Fragment {
    private ImageSlider imageSlider;
    private FloatingActionButton fabSearh,fabCart;
    private RecyclerView rvLoaiSanPham,rvSanPham;
    private LoaiSanPhamDAO loaiSanPhamDAO;
    private ArrayList<LoaiSanPham> list;
    private LoaiSanPhamAdapterHorizontal loaiSanPhamAdapterHorizontal;
    private TextView tvNameUser;
    private NhanVienDAO nhanVienDAO;
    private NhanVien objNhanVien;
    private KhachHangDAO khachHangDAO;
    private KhachHang objkhachHang;
    private SanPhamDAO sanPhamDAO;
    private SanPham objSanPham;
    private ArrayList<SanPham> listSanPham;
    private TextView tvSeeAll,tvThongBaoTrongLoaiSanPham,tvThongBaoTrongSanPham;
    private SanPhamAdapterHorizontal sanPhamAdapterHorizontal;
    public FragmentManHinhChinh() {
        // Required empty public constructor
    }

    public static FragmentManHinhChinh newInstance() {
        FragmentManHinhChinh fragment = new FragmentManHinhChinh();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_man_hinh_chinh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSlider = view.findViewById(R.id.imageSlider);
        fabSearh = view.findViewById(R.id.fabSearh);
        tvNameUser = view.findViewById(R.id.tvNameUser);
        rvLoaiSanPham = view.findViewById(R.id.rvLoaiSanPham);
        rvSanPham = view.findViewById(R.id.rvSanPham);
        tvSeeAll = view.findViewById(R.id.tvSeeAll);
        tvThongBaoTrongLoaiSanPham = view.findViewById(R.id.tvThongBaoTrongLoaiSanPham);
        tvThongBaoTrongSanPham = view.findViewById(R.id.tvThongBaoTrongSanPham);
        loaiSanPhamDAO = new LoaiSanPhamDAO(getContext());
        sanPhamDAO = new SanPhamDAO(getContext());
        list = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        listSanPham = (ArrayList<SanPham>) sanPhamDAO.getAll();
        loaiSanPhamAdapterHorizontal = new LoaiSanPhamAdapterHorizontal(list,getActivity());
        nhanVienDAO = new NhanVienDAO(getContext());
        khachHangDAO = new KhachHangDAO(getContext());
        //set Text tên tài khoản
        tvNameUser.setSelected(true);
        sanPhamAdapterHorizontal = new SanPhamAdapterHorizontal(listSanPham,getContext(),this);
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String quyen = (preferences.getString("quyen", ""));
        tvNameUser.setText(preferences.getString("nameUser",""));
        if(!(quyen.equals("admin"))){
            tvSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SearchFillterItem.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            fabSearh.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            fabSearh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SearchFillterItem.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }else{
            fabSearh.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            tvSeeAll.setVisibility(View.INVISIBLE);
            fabSearh.setVisibility(View.INVISIBLE);
        }



        if(list.size() == 0 ){
            tvThongBaoTrongLoaiSanPham.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            rvLoaiSanPham.setLayoutManager(linearLayoutManager);
            rvLoaiSanPham.setAdapter(loaiSanPhamAdapterHorizontal);
            tvThongBaoTrongLoaiSanPham.setVisibility(View.INVISIBLE);
        }
        if(listSanPham.size()==0){
            tvThongBaoTrongSanPham.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            rvSanPham.setLayoutManager(linearLayoutManager2);
            rvSanPham.setAdapter(sanPhamAdapterHorizontal);
            tvThongBaoTrongSanPham.setVisibility(View.INVISIBLE);
        }
        //set slide show
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://cafebiz.cafebizcdn.vn/zoom/700_438/2015/cocacola-1442439070176-crop-1442456236110.jpg" ,ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://afamilycdn.com/150157425591193600/2021/7/31/photo-1627719786349-1627719786522810871989.jpg", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://phunuhiendai.vn/wp-content/uploads/2014/07/Comfort-image001_Insert_Text.jpg", ScaleTypes.CENTER_CROP));

        imageSlider.startSliding(5000);
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


        //set rcv loai san pham



    }
}