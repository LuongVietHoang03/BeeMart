package beemart.fpoly.beemart.Fragment;

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

import java.util.ArrayList;

import beemart.fpoly.beemart.Adapter.ChoDuyetAdapter;
import beemart.fpoly.beemart.DAO.ViTienDAO;
import beemart.fpoly.beemart.DTO.ViTien;
import beemart.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDaDuyet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDaDuyet extends Fragment {
    RecyclerView rclDaDuyet;
    private ViTienDAO viTienDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDaDuyet() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDaDuyet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDaDuyet newInstance(String param1, String param2) {
        FragmentDaDuyet fragment = new FragmentDaDuyet();
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
        return inflater.inflate(R.layout.fragment_da_duyet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclDaDuyet= view.findViewById(R.id.rcvDaDuyet);
        viTienDAO= new ViTienDAO(getContext());
        loadData();

    }
    private void loadData(){
        ArrayList<ViTien> list = (ArrayList<ViTien>) viTienDAO.getAll();
        if(list.size() == 0){
        }else{
            ArrayList<ViTien> listChoDuyet = new ArrayList<>();
            for (int i = 0; i<list.size(); i++){
                if (list.get(i).getTrangThai() == 2 ||list.get(i).getTrangThai() == 3){
                    listChoDuyet.add(list.get(i));
                }
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            rclDaDuyet.setLayoutManager(linearLayoutManager);
            ChoDuyetAdapter choDuyetAdapter=new ChoDuyetAdapter(listChoDuyet,getContext(),viTienDAO);
            RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            rclDaDuyet.addItemDecoration(itemDecoration);
            rclDaDuyet.setAdapter(choDuyetAdapter);
        }
    }

}