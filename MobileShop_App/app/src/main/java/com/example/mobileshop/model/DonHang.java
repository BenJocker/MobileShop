package com.example.mobileshop.model;

public class DonHang {
    private int id;
    private String tenkh;
    private int sdtkh;
    private String email;

    public DonHang(int id, String tenkh, int sdtkh, String email) {
        this.id = id;
        this.tenkh = tenkh;
        this.sdtkh = sdtkh;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public int getSdtkh() {
        return sdtkh;
    }

    public void setSdtkh(int sdtkh) {
        this.sdtkh = sdtkh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
