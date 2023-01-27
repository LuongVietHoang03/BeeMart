package hoanglvph27356.fpoly.beemart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

import hoanglvph27356.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentManHinhChinh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManHinhChinh extends Fragment {
    private ImageSlider imageSlider;

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
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.anh_test1, "Ảnh 1",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.anh_test2,"Ảnh 2", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.anh_test3, "ảnh 3", ScaleTypes.CENTER_CROP));
        imageSlider.startSliding(3000);
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


    }
}