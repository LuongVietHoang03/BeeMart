package beemart.fpoly.beemart.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import beemart.fpoly.beemart.DAO.KhachHangDAO;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.ViBeePay.NapTien;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentVi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVi extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    Button btnNapTien;
    TextView tvSoDuVi;
    ImageView imAnHien;
    private KhachHangDAO khachHangDAO;
    private KhachHang objKhachHang;
    Boolean check = true;
    private static final DecimalFormat df = new DecimalFormat("0");

    public FragmentVi() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentChoXacNhan.
     */

    public static FragmentVi newInstance(String param1, String param2) {
        FragmentVi fragment = new FragmentVi();
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
        return inflater.inflate(R.layout.fragment_vi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("NAMEUSER",MODE_PRIVATE);
        String userName = (preferences.getString("userNameAcount",""));
        khachHangDAO = new KhachHangDAO(getContext());
        objKhachHang = khachHangDAO.getUserName(userName);
        btnNapTien= view.findViewById(R.id.btnNapTien);
        tvSoDuVi = view.findViewById(R.id.tvSoDuVi);
        imAnHien = view.findViewById(R.id.imAnHien);

        btnNapTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NapTien.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        imAnHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == true){
                    imAnHien.setImageResource(R.drawable.eye);
                    tvSoDuVi.setText(df.format(objKhachHang.getViTien())+"");
                    check = false;
                }else{
                    imAnHien.setImageResource(R.drawable.eye_hide);
                    tvSoDuVi.setText("*******");
                    check = true;
                }
            }
        });
    }
}