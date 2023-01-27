package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.NhanVien;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", obj.getMaHoaDon());
        values.put("maNV", obj.getMaNV());
        values.put("maKH", obj.getMaKH());
        values.put("ngayMua", obj.getNgayMua());
        values.put("tongTien", obj.getTongTien());
        values.put("trangThai", obj.getTrangThai());
        values.put("maCuaHang", obj.getMaCuaHang());
        return db.insert("HoaDon", null, values);
    }

    public int update(HoaDon obj) {
        ContentValues values = new ContentValues();
        values.put("maNV", obj.getMaNV());
        values.put("maKH", obj.getMaKH());
        values.put("ngayMua", obj.getNgayMua());
        values.put("tongTien", obj.getTongTien());
        values.put("trangThai", obj.getTrangThai());
        values.put("maCuaHang", obj.getMaCuaHang());
        return db.update("HoaDon", values, "maHoaDon=?", new String[]{String.valueOf(obj.getMaHoaDon())});
    }

    public int delete(HoaDon obj){
        return db.delete("HoaDon", "maHoaDon=?",new String[]{String.valueOf(obj.getMaHoaDon())});
    }

    //get data nhieu tham so

    @SuppressLint("Range")
    public List<HoaDon> getData(String sql, String... selectionArgs) {
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                HoaDon obj = new HoaDon();
                obj.setMaHoaDon(Integer.parseInt(c.getString(c.getColumnIndex("maHoaDon"))));
                obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
                obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
                obj.setMaCuaHang(Integer.parseInt(c.getString(c.getColumnIndex("maCuaHang"))));
                obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangThai"))));
                obj.setTongTien(Double.parseDouble(c.getString(c.getColumnIndex("tongTien"))));
                obj.setNgayMua(c.getString(c.getColumnIndex("ngayMua")));
                list.add(obj);
                c.moveToNext();
            }
        }

        return list;
    }

    // get tat ca data
    public List<HoaDon> getAll() {
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }
    public List<HoaDon> getAllMaNV(String maNV){
        String sql="SELECT * FROM HoaDon WHERE maNV=?";
        List<HoaDon> list=getData(sql,maNV);
        return list;
    }
    public List<HoaDon> getAllKH(String maKH) {
        String sql = "SELECT * FROM HoaDon WHERE maKH=?";
        List<HoaDon> list = getData(sql, maKH);
        return list;
    }
    public List<HoaDon> getAllThongBao() {
        String sql = "SELECT * FROM HoaDon WHERE trangThai=1";
        List<HoaDon> list = getData(sql);
        return list;
    }
    //get tat ca id
    public HoaDon getID(String id) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }

    public int checkMaHoaDon(String id) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
        List<HoaDon> list = getData(sql, id);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
