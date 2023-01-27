package beemart.fpoly.beemart.DTO;

public class LoaiSanPham {
    int maLoai;
    String tenLoaiSP;

    public LoaiSanPham(int maLoai, String tenLoaiSP) {
        this.maLoai = maLoai;
        this.tenLoaiSP = tenLoaiSP;
    }

    public LoaiSanPham() {
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }
}
