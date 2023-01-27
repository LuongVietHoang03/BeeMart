package beemart.fpoly.beemart.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import beemart.fpoly.beemart.DBHelper.DBHelper;
import beemart.fpoly.beemart.DTO.CountCart;
import beemart.fpoly.beemart.DTO.GioHang;
import beemart.fpoly.beemart.DTO.HoaDon;
import beemart.fpoly.beemart.DTO.KhachHang;
import beemart.fpoly.beemart.DTO.NhanVien;
import beemart.fpoly.beemart.DTO.SanPham;
import beemart.fpoly.beemart.Interface.ChangeNumberItemCartList;
import beemart.fpoly.beemart.R;

public class GioHangDAO2 {
    private SQLiteDatabase db;


    public GioHangDAO2(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertGioHang(beemart.fpoly.beemart.DTO.GioHang obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GioHang.COL_MASP, obj.getMaSP());
        contentValues.put(GioHang.COL_ANHSP, obj.getAnhSP());
        contentValues.put(GioHang.COL_TENSP, obj.getTenSP());
        contentValues.put(GioHang.COL_SOLUONG, obj.getSoLuong());
        contentValues.put(GioHang.COL_GIASP, obj.getGiaSP());
        contentValues.put(GioHang.COL_MAKH, obj.getMaKH());
        contentValues.put(GioHang.COL_MANV, obj.getMaNV());
        contentValues.put(GioHang.COL_LINKANHSP,obj.getLinkAnhSP());
        long res = db.insert(GioHang.TB_NAME, null, contentValues);
        return res;
    }

    public int updateSoLuong(beemart.fpoly.beemart.DTO.GioHang obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GioHang.COL_MASP, obj.getMaSP());
        contentValues.put(GioHang.COL_ANHSP, obj.getAnhSP());
        contentValues.put(GioHang.COL_TENSP, obj.getTenSP());
        contentValues.put(GioHang.COL_SOLUONG, obj.getSoLuong());
        contentValues.put(GioHang.COL_GIASP, obj.getGiaSP());
        contentValues.put(GioHang.COL_MAKH, obj.getMaKH());
        contentValues.put(GioHang.COL_MANV, obj.getMaNV());
        contentValues.put(GioHang.COL_LINKANHSP,obj.getLinkAnhSP());
        return db.update(GioHang.TB_NAME, contentValues, "maSP=?", new String[]{String.valueOf(obj.getMaSP())});
    }

    @SuppressLint("Range")
    public List<GioHang> getData(String sql, String... selectionArgs) {
        List<GioHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            GioHang obj = new GioHang();
            obj.setMaSP(Integer.parseInt(c.getString(c.getColumnIndex(GioHang.COL_MASP))));
            obj.setAnhSP(c.getBlob(c.getColumnIndex(GioHang.COL_ANHSP)));
            obj.setTenSP(c.getString(c.getColumnIndex(GioHang.COL_TENSP)));
            obj.setLinkAnhSP(c.getString(c.getColumnIndex(GioHang.COL_LINKANHSP)));
            obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex(GioHang.COL_SOLUONG))));
            obj.setGiaSP(Integer.parseInt(c.getString(c.getColumnIndex(GioHang.COL_GIASP))));
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex(GioHang.COL_MAKH))));
            obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex(GioHang.COL_MANV))));
            list.add(obj);
        }
        return list;
    }

    public int checkValue(String id, String maKH) {
        String sql = "SELECT * FROM GioHang WHERE maSP=? AND maKH=?";
        List<GioHang> list = getData(sql, id, maKH);
        if (list.size() == 0)
            return -1;
        return 1;
    }

    public int checkValueNhanVien(String id, String maNV) {
        String sql = "SELECT * FROM GioHang WHERE maSP=? AND maNV=?";
        List<GioHang> list = getData(sql, id, maNV);
        if (list.size() == 0)
            return -1;
        return 1;
    }

    public int delete(GioHang obj) {
        return db.delete(GioHang.TB_NAME, "maSP=?", new String[]{obj.getMaSP() + ""});
    }

    public int deleteGioHang(String id,int maQuyen) {
        int kq = 0;
        if(maQuyen == 1){
            kq = db.delete(GioHang.TB_NAME, "maKH=?", new String[]{id});
        }else if(maQuyen == 2){
            kq = db.delete(GioHang.TB_NAME, "maNV=?", new String[]{id});
        }
        return kq;
    }

    public List<GioHang> getAll(String maKH, int quyen) {
        String sql = null;
        if (quyen == 1) {
            sql = "SELECT * FROM GioHang WHERE maKH=?";
        } else if(quyen == 2) {
            sql = "SELECT * FROM GioHang WHERE maNV=?";
        }
        List<GioHang> list = getData(sql, maKH);
        return list;
    }



    public ArrayList<GioHang> getListCart(String maOrder,int quyen) {
        return (ArrayList<GioHang>) getAll(maOrder,quyen);
    }

    public void plusNumber(ArrayList<GioHang> list, int position,Context context,View view, ChangeNumberItemCartList changeNumberItemCartList) {
        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        int maSP = list.get(position).getMaSP();
        SanPham objSanPham = sanPhamDAO.getID(maSP+"");
        if(objSanPham.getSoLuong() <= list.get(position).getSoLuong()){
            Snackbar snackbar = Snackbar
                    .make(view, "Sản phẩm trong kho không đủ", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        list.get(position).setSoLuong(list.get(position).getSoLuong() + 1);
        updateSoLuong(list.get(position));
        changeNumberItemCartList.changed();
    }

    public void minusNumber(ArrayList<GioHang> list, int position, Context context, ChangeNumberItemCartList changeNumberItemCartList) {
        if (list.get(position).getSoLuong() == 1) {
            delete(list.get(position));
            list.remove(position);


        } else {
            list.get(position).setSoLuong(list.get(position).getSoLuong() - 1);
            updateSoLuong(list.get(position));
        }

        changeNumberItemCartList.changed();
    }

    @SuppressLint("Range")
    public List<CountCart> getCountCart (String maGioHang,int quyen){
        String sqlCount = null;
        if(quyen == 1){
            sqlCount = "SELECT maSP,count(maSP) as soLuong FROM GioHang WHERE maKH=?";
        }else if(quyen == 2){
            sqlCount = "SELECT maSP,count(maSP) as soLuong FROM GioHang WHERE maNV=?";
        }
        List<CountCart> list = new ArrayList<CountCart>();
        Cursor c = db.rawQuery(sqlCount, new String[]{maGioHang});
        while (c.moveToNext()){
            CountCart sl = new CountCart();
            sl.soLuong = (Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(sl);
        }
        return list;
    }


    public int getTolalFee(String maKh,int quyen) {
        ArrayList<GioHang> listSanPham = getListCart(maKh,quyen);
        int fee = 0;
        for (int i = 0; i < listSanPham.size(); i++) {
            fee = fee + (listSanPham.get(i).getGiaSP() * listSanPham.get(i).getSoLuong());
        }
        return fee;
    }

}
