package beemart.fpoly.beemart.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import beemart.fpoly.beemart.Adapter.LoaiSanPhamAdapter;
import beemart.fpoly.beemart.Adapter.LoaiSanPhamSpinnerAdapter;
import beemart.fpoly.beemart.Adapter.SanPhamAdapter;
import beemart.fpoly.beemart.Adapter.SanPhamAdapterKhachHangNhanVien;
import beemart.fpoly.beemart.Adapter.SapXepSpinnerSanPhamAdapter;
import beemart.fpoly.beemart.DAO.LoaiSanPhamDAO;
import beemart.fpoly.beemart.DAO.SanPhamDAO;
import beemart.fpoly.beemart.DTO.LoaiSanPham;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.R;
import beemart.fpoly.beemart.ScanQr;
import de.hdodenhof.circleimageview.CircleImageView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSanPham#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSanPham extends Fragment {
    FloatingActionButton fabAddNew;
    CircleImageView avataSanPham;
    EditText edTenSP, edGiaSP, edGiamGia, edSoLuong, edNSX, edHSD;
    Button btnThem, btnHuy, btnNSX, btnHSD, btnDongY, btnUpload;
    Bitmap bitmapSql = null;
    SanPhamDAO sanPhamDAO;
    Dialog dialog;
    MotionLayout frmSanPham;
    Spinner spLoaiSP,spSXTG,spSXTheoLoai;
    LoaiSanPhamDAO loaiSanPhamDAO;
    RecyclerView recyclerViewSanPham;
    Boolean check = true;
    int maLoai, maLoaiCheckSpiner = 0;
    SanPham item = new SanPham();
    LinearLayout linerThongBao;
    ArrayList<SanPham> list;
    LoaiSanPhamSpinnerAdapter loaiSanPhamSpinnerAdapter;
    ArrayList<LoaiSanPham> listLoaiSP;
    int positionSP;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int mYear, mMonth, mDay;
    SanPhamAdapter adapterSanPham;
    ConstraintLayout fragSanPham;
    TextView tvThongBaoTrong;
    String linkAnhSp;
    byte[] byteImage;
    private SearchView searchView;
    String quyen;
    SharedPreferences preferences;
    SapXepSpinnerSanPhamAdapter sapXepSpinnerSanPhamAdapter;
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
        tvThongBaoTrong = view.findViewById(R.id.tvThongBaoTrong);
        fabAddNew = view.findViewById(R.id.flbSanPham);
        //set màu cho nút
        fabAddNew.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        sanPhamDAO = new SanPhamDAO(getActivity());
        recyclerViewSanPham = view.findViewById(R.id.rcvSanPham);
        fragSanPham = view.findViewById(R.id.fragSanPham);
        spSXTG=view.findViewById(R.id.spnSXTangGiam);
        spSXTheoLoai=view.findViewById(R.id.spnSXLoai);
        preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        quyen = (preferences.getString("quyen", ""));
        if (!quyen.equals("admin")) {
            fabAddNew.setVisibility(View.INVISIBLE);
        } else {
            fabAddNew.setVisibility(View.VISIBLE);
        }
        loadData();
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemLoaiSP(getContext(), Gravity.CENTER);
            }
        });

        loaiSanPhamDAO=new LoaiSanPhamDAO(getContext());
        listLoaiSP=new ArrayList<>();
        listLoaiSP=(ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        sapXepSpinnerSanPhamAdapter=new SapXepSpinnerSanPhamAdapter(getContext(),listLoaiSP);
        spSXTheoLoai.setAdapter(sapXepSpinnerSanPhamAdapter);
        spSXTheoLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPhamDAO sanPhamDAO2=new SanPhamDAO(getContext());
                maLoai=listLoaiSP.get(position).getMaLoai();
                ArrayList<SanPham> listsx = (ArrayList<SanPham>) sanPhamDAO2.getIDSX(String.valueOf(maLoai));
                if(quyen.equals("admin")){
                    SanPhamAdapter sanPhamAdaptersx=new SanPhamAdapter(getContext(),listsx,sanPhamDAO2,FragmentSanPham.this);
                    recyclerViewSanPham.setAdapter(sanPhamAdaptersx);
                }else{
                    SanPhamAdapterKhachHangNhanVien sanPhamAdapter=new SanPhamAdapterKhachHangNhanVien(getContext(),listsx,sanPhamDAO,FragmentSanPham.this);
                    recyclerViewSanPham.setAdapter(sanPhamAdapter);
                }
                list = listsx;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                maLoaiCheckSpiner = 1;
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.planets_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSXTG.setAdapter(arrayAdapter);
        spSXTG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    loadData();
                }else if (position==1){
                    LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
                    recyclerViewSanPham.setLayoutManager(linearLayoutManager);
                    if(quyen.equals("admin")){
                        SanPhamAdapter sanPhamAdapter=new SanPhamAdapter(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list,(o1, o2) -> {
                            if (o1.getGiamGia() > o2.getGiamGia()) {
                                return 1;
                            }else if(o1.getGiamGia() < o2.getGiamGia()){
                                return -1;
                            }else{
                                return 0;
                            }
                        });
                        sanPhamAdapter.notifyDataSetChanged();
                        Log.d("zzz","tang dan" );
                    }else{
                        SanPhamAdapterKhachHangNhanVien sanPhamAdapter=new SanPhamAdapterKhachHangNhanVien(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list,(o1, o2) -> {
                            if (o1.getGiamGia() > o2.getGiamGia()) {
                                return 1;
                            }else if(o1.getGiamGia() < o2.getGiamGia()){
                                return -1;
                            }else{
                                return 0;
                            }
                        });
                        sanPhamAdapter.notifyDataSetChanged();
                        Log.d("zzz","tang dan" );
                    }

                }else if(position==2){
                    if(quyen.equals("admin")){
                        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
                        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
                        SanPhamAdapter sanPhamAdapter=new SanPhamAdapter(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list,(o1, o2) -> {
                            if(o1.getGiamGia() < o2.getGiamGia()) {
                                return 1;
                            }else if(o1.getGiamGia() > o2.getGiamGia()){
                                return -1;
                            }else{
                                return 0;
                            }
                        });
                        sanPhamAdapter.notifyDataSetChanged();
                        Log.d("zzz","giam dan");
                    }else{
                        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
                        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
                        SanPhamAdapterKhachHangNhanVien sanPhamAdapter=new SanPhamAdapterKhachHangNhanVien(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list,(o1, o2) -> {
                            if(o1.getGiamGia() < o2.getGiamGia()) {
                                return 1;
                            }else if(o1.getGiamGia() > o2.getGiamGia()){
                                return -1;
                            }else{
                                return 0;
                            }
                        });
                        sanPhamAdapter.notifyDataSetChanged();
                        Log.d("zzz","giam dan");
                    }


                }else if(position==3){
                    if (quyen.equals("admin")){
                        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
                        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
                        SanPhamAdapter sanPhamAdapter=new SanPhamAdapter(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list, Comparator.comparing(SanPham::getNgaySanXuat).reversed());
                    }else{
                        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
                        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
                        SanPhamAdapterKhachHangNhanVien sanPhamAdapter=new SanPhamAdapterKhachHangNhanVien(getContext(),list,sanPhamDAO,FragmentSanPham.this);
                        recyclerViewSanPham.setAdapter(sanPhamAdapter);
                        Collections.sort(list, Comparator.comparing(SanPham::getNgaySanXuat).reversed());
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadData() {
        preferences = getActivity().getSharedPreferences("NAMEUSER", Context.MODE_PRIVATE);
        quyen = (preferences.getString("quyen", ""));
        list = (ArrayList<SanPham>) sanPhamDAO.getAll();
        if (list.size() == 0) {
            tvThongBaoTrong.setVisibility(View.VISIBLE);
        } else if (quyen.equals("khachhang") || quyen.equals("nhanvien")) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewSanPham.setLayoutManager(linearLayoutManager);
            SanPhamAdapterKhachHangNhanVien sanPhamAdapter = new SanPhamAdapterKhachHangNhanVien(getContext(), list, sanPhamDAO, FragmentSanPham.this);
            recyclerViewSanPham.setAdapter(sanPhamAdapter);
            tvThongBaoTrong.setVisibility(View.INVISIBLE);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewSanPham.setLayoutManager(linearLayoutManager);
            SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(getContext(), list, sanPhamDAO, FragmentSanPham.this);
            recyclerViewSanPham.setAdapter(sanPhamAdapter);
            tvThongBaoTrong.setVisibility(View.INVISIBLE);
        }

    }

    public void showDialogXoaLoaiSP(int idSanPham, ArrayList<SanPham> list, Context context, int gravity, SanPham sanPham, SanPhamAdapter sanPhamAdapter) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_delete);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        TextView edLoaiSanPham = dialog.findViewById(R.id.tvDialogDeleteName);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        SanPham objSanPham = list.get(idSanPham);

        edLoaiSanPham.setText("" + sanPham.getTenSP());

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int kq = sanPhamDAO.delete(sanPham.getMaSP() + "");
                if (kq > 0) {
                    list.clear();
                    list.addAll(sanPhamDAO.getAll());
                    sanPhamAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Xóa thành công");

                } else {
                    snackBar(R.layout.custom_snackbar_error_that_bai, "Xóa thất bại");

                }


            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public void showDialogThemLoaiSP(Context context, int gravity) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_san_pham);


        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        TextView tvTitleDialog = dialog.findViewById(R.id.tvThemSP);
        tvTitleDialog.setText("Thêm sản phẩm");
        final IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        Button btnQuetMaQr = dialog.findViewById(R.id.btnQuetMaQr);
        btnQuetMaQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValueQr();
            }
        });

        edTenSP = dialog.findViewById(R.id.edTenSP);
        spLoaiSP = dialog.findViewById(R.id.spnLoaiSP);
        edGiaSP = dialog.findViewById(R.id.edGiaSP);
        edGiamGia = dialog.findViewById(R.id.edGiamGia);
        avataSanPham = dialog.findViewById(R.id.edAnhSP);
        edSoLuong = dialog.findViewById(R.id.edSoLuong);
        edNSX = dialog.findViewById(R.id.edNgaySanXuat);
        edHSD = dialog.findViewById(R.id.edHanSuDung);
        btnUpload = dialog.findViewById(R.id.btnUploadImage);
        btnNSX = dialog.findViewById(R.id.btnNgaySanXuat);
        btnHSD = dialog.findViewById(R.id.btnHanSuDung);
        btnThem = dialog.findViewById(R.id.btnLuu);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        linerThongBao = dialog.findViewById(R.id.linerThongBao);
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        listLoaiSP = new ArrayList<>();
        listLoaiSP = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        loaiSanPhamSpinnerAdapter = new LoaiSanPhamSpinnerAdapter(context, listLoaiSP);
        spLoaiSP.setAdapter(loaiSanPhamSpinnerAdapter);

        spLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoaiSP.get(position).getMaLoai();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                maLoaiCheckSpiner = 1;
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        btnNSX.setOnClickListener(new View.OnClickListener() {
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

        btnHSD.setOnClickListener(new View.OnClickListener() {
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

        btnUpload = dialog.findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapSql == null && linkAnhSp == null) {
                    snackBarDialog(R.layout.custom_snackbar_error2, "Bạn chưa chọn ảnh");
                    return;
                }
                if (bitmapSql != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmapSql.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byteImage = byteArrayOutputStream.toByteArray();
                }
                String tenSP = edTenSP.getText().toString().trim();
                String giaSP = edGiaSP.getText().toString().trim();
                String giamGia = edGiamGia.getText().toString().trim();
                String soLuong = edSoLuong.getText().toString().trim();
                String NSX = edNSX.getText().toString().trim();
                String HSD = edHSD.getText().toString().trim();
                if (tenSP.length() == 0 ||
                        giaSP.length() == 0 ||
                        giamGia.length() == 0 ||
                        soLuong.length() == 0 ||
                        NSX.length() == 0 ||
                        HSD.length() == 0) {
                    snackBarDialog(R.layout.custom_snackbar_error2, "Không được để trống");
                    return;
                } else if (listLoaiSP.size() == 0) {
                    snackBarDialog(R.layout.custom_snackbar_error2, "Bạn chưa chọn Spinner");
                    return;
                } else if (!(Integer.parseInt(giamGia) >= 0 && Integer.parseInt(giamGia) <= 100)) {
                    snackBarDialog(R.layout.custom_snackbar_error2, "Giảm giá từ 0 - 100 %");
                    return;
                }


                SanPham sanPham = new SanPham();
                sanPham.setTenSP(tenSP);
                sanPham.setGiaSP(Integer.parseInt(giaSP));
                if (bitmapSql == null) {
                    sanPham.setLinkAnhSP(linkAnhSp);
                }
                if (linkAnhSp == null) {
                    sanPham.setAnhSP(byteImage);
                }
                sanPham.setMaLoai(maLoai);
                sanPham.setGiamGia(sanPham.giaTien(Integer.parseInt(giamGia), Integer.parseInt(giaSP)));
                sanPham.setSoLuong(Integer.parseInt(soLuong));
                sanPham.setNgaySanXuat(NSX);
                sanPham.setHanSuDung(HSD);
                long kq = sanPhamDAO.insert(sanPham);
                if (kq > 0) {
                    snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Thêm thành công");
                    edTenSP.setText("");
                    edGiaSP.setText("");
                    edGiamGia.setText("");
                    edSoLuong.setText("");
                    edNSX.setText("");
                    edHSD.setText("");
                    loadData();
                    bitmapSql = null;
                    linkAnhSp = null;
                    dialog.dismiss();

                } else {
                    snackBar(R.layout.custom_snackbar_error2, "Thêm thất bại");
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void checkValueQr() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Hello");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(ScanQr.class);
        launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            try {
                JSONObject jsonObject = new JSONObject(result.getContents());
                edTenSP.setText(jsonObject.getString("tenSP"));
                ;
                edGiaSP.setText(jsonObject.getString("giaSP"));
                ;
                edNSX.setText(jsonObject.getString("ngaySanXuat"));
                ;
                edHSD.setText(jsonObject.getString("hanSuDung"));
                ;
                linkAnhSp = jsonObject.getString("anhSP");
                Picasso.get().load(linkAnhSp).into(avataSanPham);
                btnUpload.setEnabled(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    });


    DatePickerDialog.OnDateSetListener mDateNSX = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edNSX.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateHSD = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edHSD.setText(sdf.format(c.getTime()));
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                avataSanPham.setImageBitmap(bitmap);
                bitmapSql = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void snackBar(int layout, String s) {
        Snackbar snackbar = Snackbar.make(fragSanPham, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }

    public void snackBarDialog(int layout, String s) {
        Snackbar snackbar = Snackbar.make(linerThongBao, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(layout, null);
        TextView tvError = custom.findViewById(R.id.tvError);
        tvError.setText(s);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(25, 25, 25, 25);
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }


    public void showDialogSuaLoaiSP(int idSanPham, ArrayList<SanPham> list, Context context, int gravity, SanPham sanPham, SanPhamAdapter sanPhamAdapter, LoaiSanPham loaiSanPham) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_san_pham);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        TextView tvThemSP = dialog.findViewById(R.id.tvThemSP);
        tvThemSP.setText("Sửa sản phẩm");
        edTenSP = dialog.findViewById(R.id.edTenSP);
        spLoaiSP = dialog.findViewById(R.id.spnLoaiSP);
        edGiaSP = dialog.findViewById(R.id.edGiaSP);
        edGiamGia = dialog.findViewById(R.id.edGiamGia);
        avataSanPham = dialog.findViewById(R.id.edAnhSP);
        Button btnQuetMaQr = dialog.findViewById(R.id.btnQuetMaQr);
        btnQuetMaQr.setVisibility(View.INVISIBLE);
        edSoLuong = dialog.findViewById(R.id.edSoLuong);
        edNSX = dialog.findViewById(R.id.edNgaySanXuat);
        edHSD = dialog.findViewById(R.id.edHanSuDung);
        btnUpload = dialog.findViewById(R.id.btnUploadImage);
        btnNSX = dialog.findViewById(R.id.btnNgaySanXuat);
        btnHSD = dialog.findViewById(R.id.btnHanSuDung);
        btnDongY = dialog.findViewById(R.id.btnLuu);
        linerThongBao = dialog.findViewById(R.id.linerThongBao);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        SanPham objSanPham = list.get(idSanPham);
        edTenSP.setText(sanPham.getTenSP());
        edGiaSP.setText(sanPham.getGiaSP() + "");
        edGiamGia.setText(sanPham.phanTramGiamGiaSanPham(sanPham.getGiamGia(), sanPham.getGiaSP()) + "");
        edSoLuong.setText(sanPham.getSoLuong() + "");
        edNSX.setText(sanPham.getNgaySanXuat());
        edHSD.setText(sanPham.getHanSuDung());
        Bitmap bitmap;
        if (sanPham.getLinkAnhSP() == null) {
            bitmap = BitmapFactory.decodeByteArray(objSanPham.getAnhSP(), 0, objSanPham.getAnhSP().length);
            avataSanPham.setImageBitmap(bitmap);
        }
        if (sanPham.getAnhSP() == null) {
            Picasso.get().load(sanPham.getLinkAnhSP()).into(avataSanPham);
        }


        spLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoaiSP.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        listLoaiSP = new ArrayList<>();
        listLoaiSP = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        loaiSanPhamSpinnerAdapter = new LoaiSanPhamSpinnerAdapter(context, listLoaiSP);
        spLoaiSP.setAdapter(loaiSanPhamSpinnerAdapter);
        if (loaiSanPham == null) {
            Log.d("zzzz2", " list : " + "không có dữ liệu");

        } else {
            Log.d("zzzz2", " list : " + loaiSanPham.getTenLoaiSP());
            spLoaiSP.setSelection(list.indexOf(loaiSanPham.getMaLoai()));
        }

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


                String tenSP = edTenSP.getText().toString().trim();
                String giaSP = edGiaSP.getText().toString().trim();
                String giamGia = edGiamGia.getText().toString().trim();
                String soLuong = edSoLuong.getText().toString().trim();
                String NSX = edNSX.getText().toString().trim();
                String HSD = edHSD.getText().toString().trim();

                if (false) {
                    snackBar(R.layout.custom_snackbar_error2, "Bạn chưa sửa gì cả , sửa thất bại");
                    dialog.dismiss();
                } else {
                    if (tenSP.length() == 0 ||
                            giaSP.length() == 0 ||
                            giamGia.length() == 0 ||
                            soLuong.length() == 0 ||
                            NSX.length() == 0 ||
                            HSD.length() == 0) {
                        snackBarDialog(R.layout.custom_snackbar_error2, "Không được để trống");
                        return;
                    } else if (listLoaiSP.size() == 0) {
                        snackBarDialog(R.layout.custom_snackbar_error2, "Bạn chưa chọn Spinner");
                        return;
                    } else if (!(Integer.parseInt(giamGia) >= 0 && Integer.parseInt(giamGia) <= 100)) {
                        snackBarDialog(R.layout.custom_snackbar_error2, "Giảm giá từ 0 - 100 %");
                        return;
                    }
                    sanPhamDAO = new SanPhamDAO(context);
                    if (bitmapSql == null) {
                        sanPham.setLinkAnhSP(sanPham.getLinkAnhSP());
                    }
                    if (bitmapSql != null) {
                        bitmapSql.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                        byte[] byteImage = byteArrayOutputStream.toByteArray();
                        sanPham.setLinkAnhSP(null);
                        sanPham.setAnhSP(byteImage);
                    }

                    sanPham.setTenSP(edTenSP.getText().toString());
                    sanPham.setGiaSP(Integer.parseInt(edGiaSP.getText().toString()));
                    sanPham.setGiamGia(sanPham.giaTien(Integer.parseInt(giamGia), Integer.parseInt(giaSP)));
                    sanPham.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
                    sanPham.setNgaySanXuat(edNSX.getText().toString());
                    sanPham.setHanSuDung(edHSD.getText().toString());
                    int kq = sanPhamDAO.update(sanPham);
                    if (kq > 0) {
                        list.clear();
                        list.addAll(sanPhamDAO.getAll());
                        sanPhamAdapter.notifyDataSetChanged();
                        edTenSP.setText("");
                        edGiaSP.setText("");
                        edGiamGia.setText("");
                        bitmapSql = null;
                        edSoLuong.setText("");
                        edNSX.setText("");
                        edHSD.setText("");
                        dialog.dismiss();
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Sủa thành công");
                    } else {
                        snackBar(R.layout.custom_snackbar_check_mark_thanh_cong, "Sủa thất bại");
                    }
                }
            }
        });

        btnNSX.setOnClickListener(new View.OnClickListener() {
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

        btnHSD.setOnClickListener(new View.OnClickListener() {
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

        btnUpload = dialog.findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void diaLogThongTinChiTiet(Context context, int gravity, SanPham obj) {
        Dialog dialog = new Dialog(context, R.style.PauseDialogAnimation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dia_log_thong_tin_chi_tiet_san_pham);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.shadowDialog)));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        final LoaiSanPham loaiSanPham = loaiSanPhamDAO.getID(obj.getMaLoai() + "");
        TextView tvTenSanPhamChiTiet = dialog.findViewById(R.id.tvTenSanPhamChiTiet);
        TextView tvLoaiSanPhamChiTiet = dialog.findViewById(R.id.tvLoaiSanPhamChiTiet);
        TextView tvNgaySanXuatChiTiet = dialog.findViewById(R.id.tvNgaySanXuatChiTiet);
        TextView tvHanSuDungChiTiet = dialog.findViewById(R.id.tvHanSuDungChiTiet);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        tvTenSanPhamChiTiet.setText(obj.getTenSP());
        tvLoaiSanPhamChiTiet.setText(loaiSanPham.getTenLoaiSP());
        tvNgaySanXuatChiTiet.setText(obj.getNgaySanXuat());
        tvHanSuDungChiTiet.setText(obj.getHanSuDung());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}