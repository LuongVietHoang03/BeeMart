package beemart.fpoly.beemart.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.HoaDonAdapter;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;

public class FragmentDaXacNhan extends Fragment {
    private RecyclerView recyclerDaXacNhan;
    private HoaDonDAO hoaDonDAO;
    private  HoaDonAdapter hoaDonAdapter;
    public FragmentDaXacNhan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hoaDonDAO = new HoaDonDAO(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        hoaDonDAO = new HoaDonDAO(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_da_xac_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerDaXacNhan = view.findViewById(R.id.recyclerDaXacNhan);

        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);

        String quyen = (preferences.getString("quyen", ""));
        if (quyen.equals("khachhang")) {
            String userName = (preferences.getString("userNameAcount", ""));
            KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
            KhachHang obj = khachHangDAO.getUserName(userName);
            if (obj != null) {
                loadData(2,obj.getMaKH()+"");
            }
        }else{
            loadData(1,null);
        }

    }

    private void loadData(int id, String maKH) {
        ArrayList<HoaDon> listDXN = null;
        if (id == 1) {
            ArrayList<HoaDon> list = (ArrayList<HoaDon>) hoaDonDAO.getAll();
            listDXN = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTrangThai() == 2) {
                    listDXN.add(list.get(i));
                }
            }
        } else if (id == 2) {
            ArrayList<HoaDon> list = (ArrayList<HoaDon>) hoaDonDAO.getAllKH(maKH);
            listDXN = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTrangThai() == 2) {
                    listDXN.add(list.get(i));
                }
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerDaXacNhan.setLayoutManager(linearLayoutManager);
        hoaDonAdapter = new HoaDonAdapter(getContext(), listDXN, hoaDonDAO);
        recyclerDaXacNhan.setAdapter(hoaDonAdapter);
    }
}