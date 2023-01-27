package beemart.fpoly.beemart.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.HoaDonAdapter;
import beemart.fpoly.beemart.DAO.HoaDonDAO;
import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.HoaDonChiTietActivity;
import beemart.fpoly.beemart.R;


public class FragmentChoXacNhan extends Fragment {
    private RecyclerView recyclerChoXacNhan;
    private HoaDonDAO hoaDonDAO;

    public FragmentChoXacNhan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerChoXacNhan = view.findViewById(R.id.recyclerChoXacNhan);
        hoaDonDAO = new HoaDonDAO(getContext());
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
                if (list.get(i).getTrangThai() == 1) {
                    listDXN.add(list.get(i));
                }
            }
        } else if (id == 2) {
            ArrayList<HoaDon> list = (ArrayList<HoaDon>) hoaDonDAO.getAllKH(maKH);
            listDXN = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTrangThai() == 1) {
                    listDXN.add(list.get(i));
                }
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerChoXacNhan.setLayoutManager(linearLayoutManager);
        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), listDXN, hoaDonDAO);
        recyclerChoXacNhan.setAdapter(hoaDonAdapter);
    }

}
