package beemart.fpoly.beemart.DTO;

public class ThongKeDoanhThu {
    String ngay;
    double doanhThu;
    int maSP;
    int soLuong;
    public ThongKeDoanhThu() {
    }

    public ThongKeDoanhThu(String ngay, double doanhThu) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
    }

    public ThongKeDoanhThu(String ngay, double doanhThu, int maSP, int soLuong) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }
}
