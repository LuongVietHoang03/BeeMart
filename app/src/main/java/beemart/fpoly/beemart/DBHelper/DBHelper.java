package beemart.fpoly.beemart.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static String DB_NAME = "BeeMart.db";
    static int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_tbsanpham = "CREATE TABLE SanPham (" +
                "maSP INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "anhSP BLOB ," +
                "linkAnhSP TEXT ," +
                "tenSP TEXT NOT NULL," +
                "giaSP INTEGER NOT NULL," +
                "giamGia INTEGER NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "maLoai INTEGER REFERENCES LoaiSanPham(maLoai)," +
                "ngaySanXuat DATE NOT NULL," +
                "hanSuDung DATE NOT NULL)";
        db.execSQL(sql_tbsanpham);


        String sql_tbhoadon = "CREATE TABLE HoaDon (" +
                "maHoaDon INTEGER PRIMARY KEY," +
                "ngayMua DATE NOT NULL," +
                "tongTien INTEGER NOT NULL," +
                "trangThai TEXT NOT NULL," +
                "maKH INTEGER REFERENCES KhachHang(maKH)," +
                "maNV INTEGER REFERENCES NhanVien(maNV)," +
                "maCuaHang INTEGER REFERENCES ChiTietCuaHang(maCuaHang))";
        db.execSQL(sql_tbhoadon);


        String sql_tbloaisanpham = "CREATE TABLE LoaiSanPham (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoaiSP TEXT NOT NULL)";
        db.execSQL(sql_tbloaisanpham);


        String sql_tbkhachhang = "CREATE TABLE KhachHang (" +
                "maKH INTEGER PRIMARY KEY AUTOINCREMENT," +
                "anhKH BLOB ," +
                "tenKH TEXT NOT NULL," +
                "soDienThoai INTEGER NOT NULL UNIQUE," +
                "diemThuong INTEGER," +
                "viTien INTEGER ," +
                "eMail TEXT ," +
                "diaChi TEXT," +
                "userNameKH TEXT UNIQUE," +
                "passWordKH TEXT)";
        db.execSQL(sql_tbkhachhang);


        String sql_tbnhanvien = "CREATE TABLE NhanVien (" +
                "maNV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "anhNV BLOB NOT NULL," +
                "tenNV TEXT NOT NULL," +
                "userName TEXT NOT NULL UNIQUE," +
                "passWord TEXT NOT NULL)";
        db.execSQL(sql_tbnhanvien);


        String sql_tbphanhoi = "CREATE TABLE PhanHoi (" +
                "maPhanHoi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "noiDung TEXT NOT NULL," +
                "maKH INTEGER REFERENCES KhachHang(maKH))";
        db.execSQL(sql_tbphanhoi);


        String sql_giohang = "CREATE TABLE GioHang (" +
                "maSP INTEGER  REFERENCES SanPham(maSP)," +
                "anhSP TEXT ," +
                "linkAnhSP TEXT ," +
                "tenSP TEXT NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "giaSP TEXT NOT NULL," +
                "maKH INTEGER REFERENCES KhachHang(maKH)," +
                "maNV INTEGER REFERENCES NhanVien(maNV))"
                ;
        db.execSQL(sql_giohang);

        String sql_chitietcuahang = "CREATE TABLE ChiTietCuaHang (" +
                "maCuaHang INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "tenCuaHang TEXT NOT NULL," +
                "diaChiCuaHang TEXT NOT NULL," +
                "soDienThoaiCH INTEGER NOT NULL)";
        db.execSQL(sql_chitietcuahang);

        String sql_chitietdonhang="CREATE TABLE ChiTietDonHang (" +
                "maHoaDon INTEGER REFERENCES HoaDon(maHoaDon)," +
                "maSP INTEGER REFERENCES SanPham(maSP)," +
                "donGia INTEGER NOT NULL," +
                "soLuong INTEGER NOT NULL)";
        db.execSQL(sql_chitietdonhang);

        String sql_tbvitien="CREATE TABLE ViTien (" +
                "maGiaoDich INTEGER PRIMARY KEY ," +
                "tienNap INTEGER NOT NULL ," +
                "maKH INTEGER REFERENCES KhachHang(maKH)," +
                "thoiGian DATE NOT NULL,"  +
                "trangThaiXN INTEGER NOT NULL)";
        db.execSQL(sql_tbvitien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableSanPham ="drop table if exists SanPham";
        db.execSQL(dropTableSanPham);
        String dropTableThanhVien ="drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSanPham ="drop table if exists LoaiSanPham";
        db.execSQL(dropTableLoaiSanPham);
        String dropTableNhanVien ="drop table if exists NhanVien";
        db.execSQL(dropTableNhanVien);
        String dropTableHoaDon ="drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);
        String dropTablePhanHoi ="drop table if exists PhanHoi";
        db.execSQL(dropTablePhanHoi);
        String dropTableChiTietDonHang ="drop table if exists ChiTietDonHang";
        db.execSQL(dropTableChiTietDonHang);
        String dropTableChiTietCuaHang ="drop table if exists ChiTietCuaHang";
        db.execSQL(dropTableChiTietCuaHang);
        String dropTableGioHang ="drop table if exists GioHang";
        db.execSQL(dropTableGioHang);
        String dropTableViTien ="drop table if exists ViTien";
        db.execSQL(dropTableViTien);

        onCreate(db);
    }
}
