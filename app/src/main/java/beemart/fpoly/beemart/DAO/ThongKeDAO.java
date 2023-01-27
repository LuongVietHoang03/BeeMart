package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.ThongKe;
import beemart.fpoly.beemart.DTO.ThongKeDoanhThu;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        DBHelper dpHelper = new DBHelper(context);
        db = dpHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public int getTop (String tuNgay, String denNgay){
        String sqlTop = "SELECT SUM(tongTien) as doanhThu from HoaDon WHERE ngayMua BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlTop,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try{
                int doanhTHu = (Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
                list.add(doanhTHu);
            }catch (Exception e){
                e.printStackTrace();
                list.add(0);
            }

        }
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<ThongKeDoanhThu> getTop10(){
        String sql = "SELECT maSP,sum(soLuong) as soLuong FROM ChiTietDonHang GROUP BY maSP ORDER BY soLuong DESC LIMIT 10";
        List<ThongKeDoanhThu> list = new ArrayList<ThongKeDoanhThu>();
        Cursor c = db.rawQuery(sql,null);
        while (c.moveToNext()){
            ThongKeDoanhThu obj = new ThongKeDoanhThu();
            obj.setMaSP((Integer.parseInt(c.getString(c.getColumnIndex("maSP")))));
            obj.setSoLuong((Integer.parseInt(c.getString(c.getColumnIndex("soLuong")))));
            list.add(obj);
        }
        return list;
    }
}
