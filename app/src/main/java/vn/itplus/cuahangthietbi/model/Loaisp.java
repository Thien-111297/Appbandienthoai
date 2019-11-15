package vn.itplus.cuahangthietbi.model;

public class Loaisp {
    public int id;
    public String tenLoaiSP;
    public String hinhAnhLoaiSP;

    public Loaisp(int id, String tenLoaiSP, String hinhAnhLoaiSP) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public Loaisp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhAnhLoaiSP() {
        return hinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        this.hinhAnhLoaiSP = hinhAnhLoaiSP;
    }
}
