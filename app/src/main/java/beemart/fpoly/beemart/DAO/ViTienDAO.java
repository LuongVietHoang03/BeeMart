package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.ViTien;

public class ViTienDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

    public ViTienDAO(Context context){
        this.context=context;
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(ViTien obj){
        ContentValues values=new ContentValues();
        values.put("maGiaoDich",obj.getMaGiaoDich());
        values.put("maKH", obj.getMaKH());
        values.put("trangThaiXN",obj.getTrangThai());
        values.put("thoiGian",obj.getThoiGian());
        values.put("tienNap",obj.getTienNap());

        return db.insert("ViTien", null,values);
    }

    public int update(ViTien obj){
        ContentValues values=new ContentValues();
        values.put("maGiaoDich",obj.getMaGiaoDich());
        values.put("maKH", obj.getMaKH());
        values.put("trangThaiXN",obj.getTrangThai());
        values.put("thoiGian",obj.getThoiGian());
        values.put("tienNap",obj.getTienNap());
        return db.update("ViTien", values,"maGiaoDich=?", new String[]{String.valueOf(obj.getMaGiaoDich())});

    }
    public int delete(String id){
        return db.delete("ViTien", "maGiaoDich=?",new String[]{id});
    }

    //get tat ca data
    public List<ViTien> getAll(){
        String sql="SELECT * FROM ViTien";
        return getData(sql);
    }
    public List<ViTien> getAllAdmin(){
        String sql="SELECT * FROM ViTien WHERE trangThaiXN=1";
        return getData(sql);
    }
    public List<ViTien> getAllKH(String maKH){
        String sql="SELECT * FROM ViTien WHERE maKH=?";
        return getData(sql,maKH);
    }

    //get data theo id
    public ViTien getID(String id){
        String sql="SELECT * FROM ViTien WHERE maGiaoDich=?";
        List<ViTien> list =getData(sql,id);
        return list.get(0);
    }

    public int checkMaGiaoDich(String id){
        String sql="SELECT * FROM ViTien WHERE maGiaoDich=?";
        List<ViTien> list=getData(sql,id);
        if (list.size() == 0)
            return -1;
        return 1;
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private List<ViTien> getData(String sql, String...selectionArgs){
        List<ViTien> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql, selectionArgs);
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                ViTien obj=new ViTien();
                obj.setMaGiaoDich(Integer.parseInt(c.getString(c.getColumnIndex("maGiaoDich"))));
                obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
                obj.setTienNap(Integer.parseInt(c.getString(c.getColumnIndex("tienNap"))));
                obj.setThoiGian(c.getString(c.getColumnIndex("thoiGian")));
                obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangThaiXN"))));
                list.add(obj);
                c.moveToNext();
            }
        }
        return list;
    }
}
