package com.example.mypc.orderfooddemo2.DTO;

/**
 * Created by MyPC on 13/12/2017.
 */

public class BanAnDTO {
    int MaBan;
    String TenBan;
    boolean duocChon;

//    public BanAnDTO(int maBan, String tenBan) {
//        MaBan = maBan;
//        TenBan = tenBan;
//    }

    public BanAnDTO() {

    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }

    public boolean isDuocChon() {
        return duocChon;
    }

    public void setDuocChon(boolean duocChon) {
        this.duocChon = duocChon;
    }
}
