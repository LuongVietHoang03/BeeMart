package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.NhanVien;

public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien obj){
        ContentValues values=new ContentValues();

        values.put("anhNV", obj.getAnhNV());
        values.put("tenNV",obj.getTenNV());
        values.put("userName", obj.getUserName());
        values.put("passWord", obj.getPassWord());


        return db.insert("NhanVien", null,values);
    }

    public int updatePass(NhanVien obj){
        ContentValues values=new ContentValues();

        values.put("anhNV", obj.getAnhNV());
        values.put("tenNV",obj.getTenNV());
        values.put("userName", obj.getUserName());
        values.put("passWord", obj.getPassWord());

        return db.update("NhanVien", values,"maNV=?", new String[]{obj.getMaNV()+""});

    }
    public int delete(String id){
        return db.delete("NhanVien", "maNV=?",new String[]{id});
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    public List<NhanVien> getData(String sql, String...selectionArgs){
        List<NhanVien> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql, selectionArgs);
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                NhanVien obj=new NhanVien();
                obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
                obj.setAnhNV(c.getBlob(c.getColumnIndex("anhNV")));
                obj.setTenNV(c.getString(c.getColumnIndex("tenNV")));
                obj.setUserName(c.getString(c.getColumnIndex("userName")));
                obj.setPassWord(c.getString(c.getColumnIndex("passWord")));
                list.add(obj);
                c.moveToNext();
            }
        }
        return list;
    }

    //get tat ca data
    public List<NhanVien> getAll(){
        String sql="SELECT * FROM NhanVien";
        return getData(sql);
    }

    //get data theo id
    public NhanVien getID(String id){
        String sql="SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list =getData(sql,id);
        return list.get(0);
    }
    public NhanVien getUserName(String id){
        String sql="SELECT * FROM NhanVien WHERE userName=?";
        List<NhanVien> list =getData(sql,id);
        return list.get(0);
    }


    //check login
    public int checkLogin(String id,String password){
        String sql="SELECT * FROM NhanVien WHERE userName=? AND passWord=?";
        List<NhanVien> list=getData(sql,id,password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
    public int checkUser(String id){
        String sql="SELECT * FROM NhanVien WHERE userName=?";
        List<NhanVien> list=getData(sql,id);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
