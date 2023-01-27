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
import beemart.fpoly.beemart.DTO.SanPham;

public class SanPhamDAO {
    private SQLiteDatabase db;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

    public SanPhamDAO(Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(SanPham obj){
        ContentValues values=new ContentValues();
        values.put("tenSP", obj.getTenSP());
        values.put("anhSP",obj.getAnhSP());
        values.put("linkAnhSP",obj.getLinkAnhSP());
        values.put("tenSP",obj.getTenSP());
        values.put("giaSP", obj.getGiaSP());
        values.put("giamGia",obj.getGiamGia());
        values.put("soLuong", obj.getSoLuong());
        values.put("maLoai", obj.getMaLoai());
        values.put("ngaySanXuat",obj.getNgaySanXuat());
        values.put("hanSuDung",obj.getHanSuDung());

        return db.insert("SanPham", null,values);
    }

    public int update(SanPham obj){
        ContentValues values=new ContentValues();
        values.put("tenSP", obj.getTenSP());
        values.put("anhSP",obj.getAnhSP());
        values.put("linkAnhSP",obj.getLinkAnhSP());
        values.put("tenSP",obj.getTenSP());
        values.put("giaSP", obj.getGiaSP());
        values.put("giamGia",obj.getGiamGia());
        values.put("soLuong", obj.getSoLuong());
        values.put("maLoai", obj.getMaLoai());
        values.put("ngaySanXuat",obj.getNgaySanXuat());
        values.put("hanSuDung",obj.getHanSuDung());
        return db.update("SanPham", values,"maSP=?", new String[]{String.valueOf(obj.getMaSP())});

    }
    public int delete(String id){
        return db.delete("SanPham", "maSP=?",new String[]{id});
    }

    //get tat ca data
    public List<SanPham> getAll(){
        String sql="SELECT * FROM SanPham";
        return getData(sql);
    }
    public List<SanPham> getTenSanPham(String tenSP){
        String sql="SELECT * FROM SanPham WHERE linkAnhSP=?";
        return getData(sql,tenSP);
    }

    //get data theo id
    public SanPham getID(String id){
        String sql="SELECT * FROM SanPham WHERE maSP=?";
        List<SanPham> list =getData(sql,id);
        return list.get(0);
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private List<SanPham> getData(String sql, String...selectionArgs){

        List<SanPham> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql, selectionArgs);
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                SanPham obj=new SanPham();
                obj.setMaSP(Integer.parseInt(c.getString(c.getColumnIndex("maSP"))));
                obj.setAnhSP(c.getBlob(c.getColumnIndex("anhSP")));
                obj.setTenSP(c.getString(c.getColumnIndex("tenSP")));
                obj.setLinkAnhSP(c.getString(c.getColumnIndex("linkAnhSP")));
                obj.setGiaSP(Integer.parseInt(c.getString(c.getColumnIndex("giaSP"))));
                obj.setGiamGia(Integer.parseInt(c.getString(c.getColumnIndex("giamGia"))));
                obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
                obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
                obj.setNgaySanXuat(c.getString(c.getColumnIndex("ngaySanXuat")));
                obj.setHanSuDung(c.getString(c.getColumnIndex("hanSuDung")));
                list.add(obj);
                c.moveToNext();
            }
        }
        return list;
    }

    @SuppressLint("Range")
    public List<SanPham> getIDSX(String id){
        String sql="SELECT * FROM SanPham WHERE maLoai=?";
        List<SanPham> list =new ArrayList<>();
        Cursor c=db.rawQuery(sql,new String[]{id});
        while (c.moveToNext()){
            SanPham obj=new SanPham();
            obj.setMaSP(Integer.parseInt(c.getString(c.getColumnIndex("maSP"))));
            obj.setAnhSP(c.getBlob(c.getColumnIndex("anhSP")));
            obj.setTenSP(c.getString(c.getColumnIndex("tenSP")));
            obj.setLinkAnhSP(c.getString(c.getColumnIndex("linkAnhSP")));
            obj.setGiaSP(Integer.parseInt(c.getString(c.getColumnIndex("giaSP"))));
            obj.setGiamGia(Integer.parseInt(c.getString(c.getColumnIndex("giamGia"))));
            obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setNgaySanXuat(c.getString(c.getColumnIndex("ngaySanXuat")));
            obj.setHanSuDung(c.getString(c.getColumnIndex("hanSuDung")));
            list.add(obj);
        }
        return list;
    }
}
