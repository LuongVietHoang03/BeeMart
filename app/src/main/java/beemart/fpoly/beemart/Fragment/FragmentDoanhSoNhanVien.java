package beemart.fpoly.beemart.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.DoanhSoNhanVienAdapter;
import beemart.fpoly.beemart.DAO.DoanhSoNhanVienDAO;
import beemart.fpoly.beemart.DAO.NhanVienDAO;
import beemart.fpoly.beemart.DTO.DoanhSoNhanVien;
import beemart.fpoly.beemart.DTO.DoanhThuNhanVien;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.MainActivity;
import beemart.fpoly.beemart.R;


public class FragmentDoanhSoNhanVien extends Fragment {
    private ConstraintLayout frmDoanhSoNhanVien;
    private DoanhSoNhanVienDAO doanhSoNhanVienDAO;
    private ArrayList<DoanhSoNhanVien> list, listAdmin;
    private DoanhSoNhanVienAdapter doanhSoNhanVienAdapter;
    private RecyclerView recyclerViewDoanhSoNhanVien;
    private ListView lvDoanhSoNhanVien;
    private TextView tvTenNhanVien;
    private ArrayList<NhanVien> listNhanVien;
    private NhanVienDAO nhanVienDAO;


    public FragmentDoanhSoNhanVien() {

    }


    public static FragmentDoanhSoNhanVien newInstance() {
        FragmentDoanhSoNhanVien fragment = new FragmentDoanhSoNhanVien();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_doanh_so_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER", MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount", ""));
        String quyen = (preferences.getString("quyen", ""));
        frmDoanhSoNhanVien = view.findViewById(R.id.frmDoanhSoNhanVien);
        doanhSoNhanVienDAO = new DoanhSoNhanVienDAO(getContext());
        recyclerViewDoanhSoNhanVien = view.findViewById(R.id.recyclerViewDoanhSoNhanVien);
        if (quyen.equals("admin")) {
            nhanVienDAO = new NhanVienDAO(getContext());
            listNhanVien = (ArrayList<NhanVien>) nhanVienDAO.getAll();
            listAdmin = new ArrayList<>();
            if(!(listNhanVien.size() == 0)){
                for (int i = 0; i < listNhanVien.size(); i++) {
                    int maNV = listNhanVien.get(i).getMaNV();
                    list = (ArrayList<DoanhSoNhanVien>) doanhSoNhanVienDAO.getDoanhSo(maNV+"");
                    listAdmin.add(list.get(0));
                }
            }
            doanhSoNhanVienAdapter = new DoanhSoNhanVienAdapter(listAdmin, getContext(), FragmentDoanhSoNhanVien.this);
        } else {
            NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
            NhanVien obj = nhanVienDAO.getUserName(userName);
            if (obj != null) {
                list = (ArrayList<DoanhSoNhanVien>) doanhSoNhanVienDAO.getDoanhSo(obj.getMaNV() + "");
                Log.d("ZZZZ", "listDoanhSo: " + list.size());
                doanhSoNhanVienAdapter = new DoanhSoNhanVienAdapter(list, getContext(), FragmentDoanhSoNhanVien.this);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewDoanhSoNhanVien.setLayoutManager(linearLayoutManager);
        recyclerViewDoanhSoNhanVien.setAdapter(doanhSoNhanVienAdapter);

    }


}