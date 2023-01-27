package beemart.fpoly.beemart.DTO;

import java.io.Serializable;
import java.sql.Blob;

public class KhachHang implements Serializable {
    int maKH;
    String tenKH;
    byte[] anhKH;
    Long soDienThoai;
    double diemThuong;
    String userNameKH;
    String passwordKH;
    String eMail;
    String diaChi;
    double viTien;



    public KhachHang(int maKH, String tenKH, byte[] anhKH, Long soDienThoai, double diemThuong, String userNameKH, String passwordKH,String eMail,String diaChi,double viTien) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.anhKH = anhKH;
        this.soDienThoai = soDienThoai;
        this.diemThuong = diemThuong;
        this.userNameKH = userNameKH;
        this.passwordKH = passwordKH;
        this.eMail = eMail;
        this.diaChi = diaChi;
        this.viTien = viTien;
    }

    public KhachHang() {
    }

    public double getViTien() {
        return viTien;
    }

    public void setViTien(double viTien) {
        this.viTien = viTien;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public byte[] getAnhKH() {
        return anhKH;
    }

    public void setAnhKH(byte[] anhKH) {
        this.anhKH = anhKH;
    }

    public Long getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(Long soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public double getDiemThuong() {
        return diemThuong;
    }

    public void setDiemThuong(double diemThuong) {
        this.diemThuong = diemThuong;
    }

    public String getUserNameKH() {
        return userNameKH;
    }

    public void setUserNameKH(String userNameKH) {
        this.userNameKH = userNameKH;
    }

    public String getPasswordKH() {
        return passwordKH;
    }

    public void setPasswordKH(String passwordKH) {
        this.passwordKH = passwordKH;
    }


    public double TinhDiemThuong(double tongTien){
        return tongTien*0.02;
    }
}
