package beemart.fpoly.beemart.DTO;

public class DoanhSoNhanVien {
    int maNV;
    int soLuong;
    int tongDoanhThu;

    public DoanhSoNhanVien() {
    }

    public DoanhSoNhanVien(int maNV, int soLuong, int tongDoanhThu) {
        this.maNV = maNV;
        this.soLuong = soLuong;
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(int tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }
}
