package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.DoanhSoNhanVien;
import beemart.fpoly.beemart.DTO.NhanVien;


public class DoanhSoNhanVienDAO {
    private SQLiteDatabase db;
    private Context context;

    public DoanhSoNhanVienDAO(Context context){
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    @SuppressLint("Range")
    public List<DoanhSoNhanVien> getDoanhSo(String maNV){
        String sql = "SELECT maHoaDon, count(maHoaDon) as soLuong, SUM(tongTien) as tongDoanhThu, maNV FROM HoaDon WHERE maNV=?";
        List<DoanhSoNhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, new String[]{maNV});
        NhanVienDAO nhanVienDAO=new NhanVienDAO(context);
        while (c.moveToNext()){
            DoanhSoNhanVien obj = new DoanhSoNhanVien();
            try{
                obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
                obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
                obj.setTongDoanhThu(Integer.parseInt(c.getString(c.getColumnIndex("tongDoanhThu"))));
            }catch (NumberFormatException e){
                obj.setMaNV(Integer.parseInt(maNV));
                obj.setTongDoanhThu(0);
                obj.setSoLuong(0);
                list.add(obj);
            }

            list.add(obj);

        }
        return list;
    }
}

