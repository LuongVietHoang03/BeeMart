package beemart.fpoly.beemart.DTO;

import java.io.Serializable;

public class ViTien implements Serializable {
    int maGiaoDich;
    int maKH;
    String thoiGian;
    int trangThai;
    double tienNap;

    public ViTien(int maGiaoDich, int maKH, String thoiGian, int trangThai, double tienNap) {
        this.maGiaoDich = maGiaoDich;
        this.maKH = maKH;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.tienNap = tienNap;
    }

    public ViTien() {
    }


    public double getTienNap() {
        return tienNap;
    }

    public void setTienNap(double tienNap) {
        this.tienNap = tienNap;
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
