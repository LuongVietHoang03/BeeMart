package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.DoanhSoNhanVien;
import beemart.fpoly.beemart.DTO.DoanhThuNhanVien;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;


public class DoanhThuNhanVienDAO {
    private SQLiteDatabase db;
    private Context context;

    public DoanhThuNhanVienDAO(Context context){
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    @SuppressLint("Range")
    public List<DoanhThuNhanVien> getDoanhSo(){
        String sql = "SELECT SUM(tongTien) as tongTien FROM HoaDon ";
        List<DoanhThuNhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        NhanVienDAO nhanVienDAO=new NhanVienDAO(context);
        KhachHangDAO khachHangDAO=new KhachHangDAO(context);
        while (c.moveToNext()){
            DoanhThuNhanVien obj = new DoanhThuNhanVien();
            NhanVien nhanVien= nhanVienDAO.getID(c.getString(c.getColumnIndex("maNV")));
            obj.setTenNV(nhanVien.getTenNV());
            KhachHang khachHang=khachHangDAO.getID(c.getString(c.getColumnIndex("maKH")));
            obj.setTenKH(khachHang.getTenKH());
            obj.setTongTien(Integer.parseInt(c.getString(c.getColumnIndex("tongTien"))));
            list.add(obj);

        }
        return list;
    }
}


