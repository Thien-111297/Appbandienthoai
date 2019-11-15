package vn.itplus.cuahangthietbi.model;

public class GioHang {
    public int idSp;
    public String tenSp;
    public long giaSp;
    public String hinhAnhSp;
    public int soLuongSp;

    public GioHang(int idSp, String tenSp, long giaSp, String hinhAnhSp, int soLuongSp) {
        this.idSp = idSp;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.hinhAnhSp = hinhAnhSp;
        this.soLuongSp = soLuongSp;
    }

    public GioHang() {
    }

    public int getIdSp() {
        return idSp;
    }

    public void setIdSp(int idSp) {
        this.idSp = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public long getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(long giaSp) {
        this.giaSp = giaSp;
    }

    public String getHinhAnhSp() {
        return hinhAnhSp;
    }

    public void setHinhAnhSp(String hinhAnhSp) {
        this.hinhAnhSp = hinhAnhSp;
    }

    public int getSoLuongSp() {
        return soLuongSp;
    }

    public void setSoLuongSp(int soLuongSp) {
        this.soLuongSp = soLuongSp;
    }
}
