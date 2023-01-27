package beemart.fpoly.beemart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import beemart.fpoly.beemart.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHoaDon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHoaDon extends Fragment {



    public FragmentHoaDon() {
        // Required empty public constructor
    }


    public static FragmentHoaDon newInstance() {
        FragmentHoaDon fragment = new FragmentHoaDon();

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
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}