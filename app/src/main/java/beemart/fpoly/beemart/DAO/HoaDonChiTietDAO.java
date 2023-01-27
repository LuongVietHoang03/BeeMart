package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.HoaDonChiTiet;
import beemart.fpoly.beemart.DTO.SanPham;


public class HoaDonChiTietDAO {
    private SQLiteDatabase db;
    private Context context;

    public HoaDonChiTietDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HoaDonChiTiet obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maHoaDon", obj.getMaHoaDon());
        contentValues.put("maSP", obj.getMaSP());
        contentValues.put("donGia", obj.getDonGia());
        contentValues.put("soLuong", obj.getSoLuong());
        return db.insert("ChiTietDonHang", null, contentValues);
    }

    public int update(HoaDonChiTiet obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maHoaDon", obj.getMaHoaDon());
        contentValues.put("maSP", obj.getMaSP());
        contentValues.put("donGia", obj.getDonGia());
        contentValues.put("soLuong", obj.getSoLuong());
        return db.update("ChiTietDonHang", contentValues, "maHoaDon = ?", new String[]{String.valueOf(obj.getMaHoaDon())});
    }

    public int delete(String id) {
        return db.delete("ChiTietDonHang", "maHoaDon = ?", new String[]{id});
    }

    public HoaDonChiTiet getID(String id) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        List<HoaDonChiTiet> list = getData(sql, id);
        return list.get(0);
    }

    public List<HoaDonChiTiet> getAll() {
        String sql = "SELECT * FROM ChiTietDonHang";
        return getData(sql);
    }

    @SuppressLint("Range")
    public List<HoaDonChiTiet> getData(String sql, String... selectionArgs) {
        ArrayList<HoaDonChiTiet> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HoaDonChiTiet hoaDonChiTietDAO = new HoaDonChiTiet();
                hoaDonChiTietDAO.setMaHoaDon(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maHoaDon"))));
                hoaDonChiTietDAO.setMaSP(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSP"))));
                hoaDonChiTietDAO.setDonGia(Double.parseDouble(cursor.getString(cursor.getColumnIndex("donGia"))));
                hoaDonChiTietDAO.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
                list.add(hoaDonChiTietDAO);
                cursor.moveToNext();
            }
        }
        return list;
    }

}
