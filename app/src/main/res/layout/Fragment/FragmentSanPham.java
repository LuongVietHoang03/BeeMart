package hoanglvph27356.fpoly.beemart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hoanglvph27356.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSanPham#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSanPham extends Fragment {

    public FragmentSanPham() {
        // Required empty public constructor
    }


    public static FragmentSanPham newInstance() {
        FragmentSanPham fragment = new FragmentSanPham();

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
        return inflater.inflate(R.layout.fragment_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}