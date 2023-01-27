package beemart.fpoly.beemart.DTO;

public class CountCart {
    public int soLuong;

    public CountCart(int soLuong) {
        this.soLuong = soLuong;
    }

    public CountCart() {
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
