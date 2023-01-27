package beemart.fpoly.beemart.DTO;

public class NhanVien {
    int maNV;
    byte[] anhNV;
    String tenNV;
    String userName;
    String passWord;

    public NhanVien(int maNV, byte[] anhNV, String tenNV, String userName, String passWord) {
        this.maNV = maNV;
        this.anhNV = anhNV;
        this.tenNV = tenNV;
        this.userName = userName;
        this.passWord = passWord;
    }

    public NhanVien() {
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public byte[] getAnhNV() {
        return anhNV;
    }

    public void setAnhNV(byte[] anhNV) {
        this.anhNV = anhNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}

