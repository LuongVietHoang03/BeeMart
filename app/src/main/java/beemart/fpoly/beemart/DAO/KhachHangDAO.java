package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;

public class KhachHangDAO {
    private SQLiteDatabase db;

    public KhachHangDAO(Context context){
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(KhachHang obj){
        ContentValues values=new ContentValues();
        values.put("tenKH", obj.getTenKH());
        values.put("anhKH",obj.getAnhKH());
        values.put("soDienThoai",obj.getSoDienThoai());
        values.put("diemThuong", obj.getDiemThuong());
        values.put("userNameKH",obj.getUserNameKH());
        values.put("passWordKH",obj.getPasswordKH());
        values.put("diaChi",obj.getDiaChi());
        values.put("eMail",obj.geteMail());
        values.put("viTien",obj.getViTien());
        return db.insert("KhachHang", null,values);
    }

    public int update(KhachHang obj){
        ContentValues values=new ContentValues();
        values.put("tenKH", obj.getTenKH());
        values.put("anhKH",obj.getAnhKH());
        values.put("soDienThoai",obj.getSoDienThoai());
        values.put("diemThuong", obj.getDiemThuong());
        values.put("userNameKH",obj.getUserNameKH());
        values.put("passWordKH",obj.getPasswordKH());
        values.put("diaChi",obj.getDiaChi());
        values.put("eMail",obj.geteMail());
        values.put("viTien",obj.getViTien());
        return db.update("KhachHang", values,"maKH=?", new String[]{String.valueOf(obj.getMaKH())});

    }
    public int delete(String id){
        return db.delete("KhachHang", "maKH=?",new String[]{id});
    }

    //get tat ca data
    public List<KhachHang> getAll(){
        String sql="SELECT * FROM KhachHang";
        return getData(sql);
    }

    //get data theo id
    public KhachHang getID(String id){
        String sql="SELECT * FROM KhachHang WHERE maKH=?";
        List<KhachHang> list =getData(sql,id);
        return list.get(0);
    }
    public KhachHang getUserName(String id){
        String sql="SELECT * FROM KhachHang WHERE userNameKH=?";
        List<KhachHang> list =getData(sql,id);
        return list.get(0);
    }
    public KhachHang getSoDienThoai(String soDienThoai){
        String sql="SELECT * FROM KhachHang WHERE soDienThoai=?";
        List<KhachHang> list =getData(sql,soDienThoai);
        return list.get(0);
    }
    //get data nhieu tham so
    @SuppressLint("Range")
    public List<KhachHang> getData(String sql, String...selectionArgs){

        List<KhachHang> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql, selectionArgs);
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                KhachHang obj=new KhachHang();
                obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
                obj.setTenKH(c.getString(c.getColumnIndex("tenKH")));
                obj.setAnhKH(c.getBlob(c.getColumnIndex("anhKH")));
                obj.setSoDienThoai(Long.parseLong(c.getString(c.getColumnIndex("soDienThoai"))));
                obj.setDiemThuong(Double.parseDouble(c.getString(c.getColumnIndex("diemThuong"))));
                obj.setUserNameKH(c.getString(c.getColumnIndex("userNameKH")));
                obj.setPasswordKH(c.getString(c.getColumnIndex("passWordKH")));
                obj.setDiaChi(c.getString(c.getColumnIndex("diaChi")));
                obj.seteMail(c.getString(c.getColumnIndex("eMail")));
                obj.setViTien(Integer.parseInt(c.getString(c.getColumnIndex("viTien"))));
                list.add(obj);
                c.moveToNext();
            }
        }

        return list;
    }
    public int checkLogin(String id,String password){
        String sql="SELECT * FROM KhachHang WHERE userNameKH=? AND passWordKH=?";
        List<KhachHang> list=getData(sql,id,password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
    public int checkSoDienThoai(String soDienThoai){
        String sql="SELECT * FROM KhachHang WHERE soDienThoai=?";
        List<KhachHang> list=getData(sql,soDienThoai);
        if (list.size() == 0)
            return -1;
        return 1;
    }
    public int checkUserName(String userName){
        String sql="SELECT * FROM KhachHang WHERE userNameKH=?";
        List<KhachHang> list=getData(sql,userName);
        if (list.size() == 0)
            return -1;
        return 1;
    }

}
