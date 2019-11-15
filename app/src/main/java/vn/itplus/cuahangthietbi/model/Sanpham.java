package vn.itplus.cuahangthietbi.model;

import java.io.Serializable;

public class Sanpham  implements Serializable {
    private int id;
    private String ten;
    private int gia;
    private String hinhAnh;
    private String moTa;
    private int idSanPham;

    public Sanpham(int id, String ten, int gia, String hinhAnh, String moTa, int idSanPham) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.idSanPham = idSanPham;
    }

    public Sanpham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }
}
