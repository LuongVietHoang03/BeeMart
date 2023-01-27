package beemart.fpoly.beemart.ThongBao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.AdapterThongBaoNapTien;
import beemart.fpoly.beemart.Adapter.AdapterThongBaoNapTienAdmin;
import beemart.fpoly.beemart.Adapter.LichSuNapAdapter;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.ViBeePay.NapTien;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NapTienFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NapTienFragment extends Fragment {
    private KhachHangDAO khachHangDAO;
    private ArrayList<ViTien> list;
    private ViTienDAO viTienDAO;
    private RecyclerView recyclerViewThongBaoNapTien;
    private KhachHang khachHang;
    private AdapterThongBaoNapTien adapterThongBaoNapTien;
    private TextView tvThongBaoNapTien;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NapTienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NapTienFragment newInstance(String param1, String param2) {
        NapTienFragment fragment = new NapTienFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NapTienFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_nap_tien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewThongBaoNapTien = view.findViewById(R.id.recyclerViewThongBaoNapTien);
        tvThongBaoNapTien = view.findViewById(R.id.tvThongBaoNapTien);
        khachHangDAO = new KhachHangDAO(getContext());
        viTienDAO = new ViTienDAO(getContext());
        tvThongBaoNapTien.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        String quyen = (preferences.getString("quyen",""));
        if(quyen.equals("khachhang")){
            khachHang = khachHangDAO.getUserName(userName);
            if(khachHang != null){
                list = (ArrayList<ViTien>) viTienDAO.getAllKH(khachHang.getMaKH()+"");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerViewThongBaoNapTien.setLayoutManager(linearLayoutManager);
                AdapterThongBaoNapTien adapterThongBaoNapTien=new AdapterThongBaoNapTien(list,getContext());
                RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                recyclerViewThongBaoNapTien.addItemDecoration(itemDecoration);
                recyclerViewThongBaoNapTien.setAdapter(adapterThongBaoNapTien);
                tvThongBaoNapTien.setVisibility(View.INVISIBLE);
            }
        }else if(quyen.equals("nhanvien")){
            tvThongBaoNapTien.setVisibility(View.VISIBLE);
        }else if(quyen.equals("admin")){
            list = (ArrayList<ViTien>) viTienDAO.getAllAdmin();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerViewThongBaoNapTien.setLayoutManager(linearLayoutManager);
            AdapterThongBaoNapTienAdmin adapterThongBaoNapTien=new AdapterThongBaoNapTienAdmin(list,getContext());
            RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerViewThongBaoNapTien.addItemDecoration(itemDecoration);
            recyclerViewThongBaoNapTien.setAdapter(adapterThongBaoNapTien);
            tvThongBaoNapTien.setVisibility(View.INVISIBLE);
        }
       
    }
}