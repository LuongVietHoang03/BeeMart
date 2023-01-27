package beemart.fpoly.beemart.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import beemart.fpoly.beemart.Adapter.AdapterTop10;
import beemart.fpoly.beemart.DAO.ThongKeDAO;
import beemart.fpoly.beemart.DTO.ThongKe;
import beemart.fpoly.beemart.DTO.ThongKeDoanhThu;
import beemart.fpoly.beemart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThongKe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThongKe extends Fragment {
    private BarChart barChar;
    private ThongKeDAO thongKeDao;
    private ArrayList<ThongKeDoanhThu> list;
    int mYear, mMonth, mDay;
    private EditText edTuNgay,edDenNgay;
    private Button btnTuNgay,btnDenNgay;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    private Button btnXacNhanThongKe;
    private TextView tvDoanhThu;
    private LinearLayout linerThongKe;
    private AdapterTop10 adapterTop10;
    private RecyclerView recyclerView;
    public FragmentThongKe() {
        // Required empty public constructor
    }

    public static FragmentThongKe newInstance() {
        FragmentThongKe fragment = new FragmentThongKe();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        thongKeDao = new ThongKeDAO(getContext());


        list = (ArrayList<ThongKeDoanhThu>) thongKeDao.getTop10();

        adapterTop10 = new AdapterTop10(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterTop10);



        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, mDateNSX, mYear, mMonth, mDay);
                d.show();

            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, mDateHSD, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnXacNhanThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTuNgay.getText().toString().trim();
                String denNgay = edDenNgay.getText().toString().trim();
                if(tuNgay.isEmpty() || denNgay.isEmpty()){
                    snackBar(R.layout.custom_snackbar_error2,"Không được bỏ trống");
                    return;
                }
                tvDoanhThu.setText(thongKeDao.getTop(tuNgay,denNgay) + " VND");
            }
        });

    }
    public void snackBar(int layout,String s) {

        Snackbar snackbar = Snackbar.make(linerThongKe, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
    private void initView(View view) {
        linerThongKe = view.findViewById(R.id.linerThongKe);
        edTuNgay = view.findViewById(R.id.edTuNgay);
        edDenNgay = view.findViewById(R.id.edDenNgay);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnXacNhanThongKe = view.findViewById(R.id.btnXacNhanThongKe);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        recyclerView = view.findViewById(R.id.recyclierViewSanPham);
    }
    DatePickerDialog.OnDateSetListener mDateNSX=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            GregorianCalendar c=new GregorianCalendar(mYear,mMonth,mDay);
            edTuNgay.setText(sdf.format(c.getTime()));

        }
    };

    DatePickerDialog.OnDateSetListener mDateHSD=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            GregorianCalendar c=new GregorianCalendar(mYear,mMonth,mDay);
            edDenNgay.setText(sdf.format(c.getTime()));

        }
    };
}