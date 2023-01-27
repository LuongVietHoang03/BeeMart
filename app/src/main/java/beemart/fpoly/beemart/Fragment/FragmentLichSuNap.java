package beemart.fpoly.beemart.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.LichSuNapAdapter;
import beemart.fpoly.beemart.Adapter.LoaiSanPhamAdapter;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLichSuNap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLichSuNap extends Fragment {

    private RecyclerView rclLichSuNap;
    private ViTienDAO viTienDAO;
    private KhachHangDAO khachHangDAO;
    private ConstraintLayout fragLichSuNap;
    private KhachHang khachHang;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLichSuNap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLichSuNap.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLichSuNap newInstance(String param1, String param2) {
        FragmentLichSuNap fragment = new FragmentLichSuNap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_nap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclLichSuNap = view.findViewById(R.id.rclLichSuNap);
        viTienDAO = new ViTienDAO(getContext());
        khachHangDAO= new KhachHangDAO(getContext());
        fragLichSuNap = view.findViewById(R.id.fragLichSuNap);
        khachHangDAO = new KhachHangDAO(getContext());
        viTienDAO = new ViTienDAO(getContext());
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        khachHang = khachHangDAO.getUserName(userName);
        loadData();
    }
    private void loadData(){
        ArrayList<ViTien> list = (ArrayList<ViTien>) viTienDAO.getAllKH(khachHang.getMaKH()+"");
        if(list.size() == 0){
//            tvThongBaoTrong.setVisibility(View.VISIBLE);
        }else{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            rclLichSuNap.setLayoutManager(linearLayoutManager);
            LichSuNapAdapter lichSuNapAdapter=new LichSuNapAdapter(list,getContext(),viTienDAO);

            RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            rclLichSuNap.addItemDecoration(itemDecoration);

            rclLichSuNap.setAdapter(lichSuNapAdapter);

        }



    }
}