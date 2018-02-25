package com.example.mypc.orderfooddemo2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mypc.orderfooddemo2.DTO.BanAnDTO;
import com.example.mypc.orderfooddemo2.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 13/12/2017.
 */

public class BanAnDAO {

    SQLiteDatabase database;

    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();

    }

    public boolean ThemBanAn(String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemtra = database.insert(CreateDatabase.TB_BANAN, null, contentValues);

        return (kiemtra != 0);

    }

    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<>();
        String query = "Select * From " +CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }

        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maBan){
        String tinhTrang ="";

        String query = "Select * from " + CreateDatabase.TB_BANAN + " Where " + CreateDatabase.TB_BANAN_MABAN + " = " + "'" + maBan + "'" ;
        Cursor cursor = database.rawQuery(query ,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhTrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhTrang;
    }

    public boolean CapNhapLaiTinhTrangBan(int maban , String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG , tinhTrang);

        long kiemtra = database.update(CreateDatabase.TB_BANAN , contentValues , CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'" ,null );

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean xoaBanAnTheoMa(int maban){
        long kiemtra  = database.delete(CreateDatabase.TB_BANAN , CreateDatabase.TB_BANAN_MABAN + " = " + maban , null );

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }

    }

    public boolean CapNhapLaiTenBan(int maban , String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN , tenban);

        long kiemtra = database.update(CreateDatabase.TB_BANAN , contentValues , CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'" ,null );

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
