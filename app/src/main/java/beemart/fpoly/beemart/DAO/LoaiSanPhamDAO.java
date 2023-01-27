package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.LoaiSanPham;

public class LoaiSanPhamDAO {
    private SQLiteDatabase db;

    public LoaiSanPhamDAO(Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSanPham obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiSP", obj.getTenLoaiSP());

        return db.insert("LoaiSanPham", null,values);
    }

    public int update(LoaiSanPham obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiSP", obj.getTenLoaiSP());
        return db.update("LoaiSanPham", values,"maLoai=?", new String[]{String.valueOf(obj.getMaLoai())});

    }
    public int delete(String id){
        return db.delete("LoaiSanPham", "maLoai=?",new String[]{id});
    }

    //get tat ca data
    public List<LoaiSanPham> getAll(){
        String sql="SELECT * FROM LoaiSanPham";
        return getData(sql);
    }

    //get data theo id
    public LoaiSanPham getID(String id){
        String sql="SELECT * FROM LoaiSanPham WHERE maLoai=?";
        List<LoaiSanPham> list =getData(sql,id);
        if(!(list.size() == 0)){
            return list.get(0);
        }
        return null ;
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    public List<LoaiSanPham> getData(String sql, String...selectionArgs){

        List<LoaiSanPham> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql, selectionArgs);
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                LoaiSanPham obj=new LoaiSanPham();
                obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
                obj.setTenLoaiSP(c.getString(c.getColumnIndex("tenLoaiSP")));
                list.add(obj);
                c.moveToNext();
            }
        }

        return list;
    }
}
