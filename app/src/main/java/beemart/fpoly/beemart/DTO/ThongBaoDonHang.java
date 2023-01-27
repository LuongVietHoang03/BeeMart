package beemart.fpoly.beemart.DTO;

public class ThongBaoDonHang {
    int idthongBao;
    int maKH;
    int maHoaDon;
    int tinhTrang;

    public ThongBaoDonHang() {
    }

    public ThongBaoDonHang(int idthongBao, int maKH, int maHoaDon, int tinhTrang) {
        this.idthongBao = idthongBao;
        this.maKH = maKH;
        this.maHoaDon = maHoaDon;
        this.tinhTrang = tinhTrang;
    }

    public int getIdthongBao() {
        return idthongBao;
    }

    public void setIdthongBao(int idthongBao) {
        this.idthongBao = idthongBao;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
