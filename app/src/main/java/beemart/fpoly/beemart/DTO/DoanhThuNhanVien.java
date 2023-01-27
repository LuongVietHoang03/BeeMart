package beemart.fpoly.beemart.DTO;

public class DoanhThuNhanVien {
    String tenKH;
    String tenNV;
    int tongTien;

    public DoanhThuNhanVien() {
    }

    public DoanhThuNhanVien(String tenKH, String tenNV, int tongTien) {
        this.tenKH = tenKH;
        this.tenNV = tenNV;
        this.tongTien = tongTien;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
