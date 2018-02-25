package com.example.mypc.orderfooddemo2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.CustomAdapter.AdapterHienThiThanhToan;
import com.example.mypc.orderfooddemo2.DAO.BanAnDAO;
import com.example.mypc.orderfooddemo2.DAO.GoiMonDAO;
import com.example.mypc.orderfooddemo2.DTO.ThanhToanDTO;
import com.example.mypc.orderfooddemo2.FragmentApp.HienThiBanAnFragment;
import com.example.mypc.orderfooddemo2.FragmentApp.HienThiDanhSachMonAnFragment;
import com.example.mypc.orderfooddemo2.FragmentApp.HienThiThucDonFragment;

import java.util.List;

/**
 * Created by MyPC on 21/02/2018.
 */

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    Button btnThanhToan , btnThoat;
    TextView tvTongTien;
    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    AdapterHienThiThanhToan adapterHienThiThanhToan;
    List<ThanhToanDTO> thanhToanDTOList ;
    private long tongtien = 0;
    private int maban = 0;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridView = (GridView) findViewById(R.id.gvThanhToan);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnThoat = (Button) findViewById(R.id.btnThoatThanhToan);
        tvTongTien = (TextView) findViewById(R.id.tvTongTien);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);



        maban = getIntent().getIntExtra("maban" , 0); // gán mặc đinh mã ban = 0
        if(maban !=0){

            hienThiThanhToan();

            for (int i = 0 ; i < thanhToanDTOList.size() ; i++){
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiaTien();

                tongtien += (soluong*giatien);
            }

            tvTongTien.setText(getResources().getString(R.string.tongcong) + " " + tongtien);

            btnThanhToan.setOnClickListener(this);
            btnThoat.setOnClickListener(this);
        }
    }

    private void hienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);

        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this , thanhToanDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ){
            case R.id.btnThanhToan:
                boolean kiemTraBanAn = banAnDAO.CapNhapLaiTinhTrangBan(maban , "false");
                boolean kiemTraGoiMon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban , "true");


                if(kiemTraBanAn && kiemTraGoiMon){

                    Intent trangChuIntent = new Intent(this, TrangchuActivity.class);
                    startActivity(trangChuIntent);
                    finish();
                    Toast.makeText(this, "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "Thanh Toán Lỗi !", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnThoatThanhToan:
                finish();
                break;
        }


    }
}
