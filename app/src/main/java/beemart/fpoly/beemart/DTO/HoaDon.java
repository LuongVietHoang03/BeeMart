package beemart.fpoly.beemart.DTO;

import java.util.Date;

public class HoaDon {
     int maHoaDon;
     String ngayMua;
     double tongTien;
     int trangThai;
     int maKH;
     int maNV;
     int maCuaHang;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, String ngayMua, double tongTien, int trangThai, int maKH, int maNV, int maCuaHang) {
        this.maHoaDon = maHoaDon;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.trangThai = trangThai;

        this.maKH = maKH;
        this.maNV = maNV;
        this.maCuaHang = maCuaHang;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }



    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(int maCuaHang) {
        this.maCuaHang = maCuaHang;
    }
}
