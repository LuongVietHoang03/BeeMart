package beemart.fpoly.beemart.DTO;

public class ThongKe {
    String ngay;
    int doanhThu;

    public ThongKe() {
    }

    public ThongKe(String ngay, int doanhThu) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }
}
