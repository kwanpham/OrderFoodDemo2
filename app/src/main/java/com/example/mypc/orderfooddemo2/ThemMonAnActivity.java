package com.example.mypc.orderfooddemo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.CustomAdapter.AdapterHienThiLoaiMonAnSpinder;
import com.example.mypc.orderfooddemo2.DAO.LoaiMonAnDAO;
import com.example.mypc.orderfooddemo2.DAO.MonAnDAO;

import com.example.mypc.orderfooddemo2.DTO.LoaiMonAnDTO;
import com.example.mypc.orderfooddemo2.DTO.MonAnDTO;

import java.util.List;


public class ThemMonAnActivity extends AppCompatActivity implements View.OnClickListener {

    public final static int REQUEST_CODE_THEMLOAITHUCDON = 0;
    public final static int REQUEST_CODE_MOHINH = 1;
    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    AdapterHienThiLoaiMonAnSpinder adapterHienThiLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    String sDuongDanHinh = "";
    EditText edTenMonAn, edGiaTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        imThemLoaiThucDon = (ImageButton) findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = (Spinner) findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = (Button) findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edGiaTien = (EditText) findViewById(R.id.edThemGiaTien);

        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

//        final DecimalFormat decimalFormat = new DecimalFormat("###.###");
//        edGiaTien.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String temp = decimalFormat.format(Integer.valueOf(edGiaTien.getText().toString().trim()));
//                edGiaTien.setText(temp);
//
//
//            }
//        });

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        HienThiSpinnerLoaiMonAn();


    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiSpinnerLoaiMonAn();
    }

    private void HienThiSpinnerLoaiMonAn() {
        loaiMonAnDTOList = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAnSpinder(this, loaiMonAnDTOList);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemMonAnActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn, REQUEST_CODE_THEMLOAITHUCDON);

                break;

            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iMoHinh.setType("image/*");


                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình thực đơn"), REQUEST_CODE_MOHINH);
                break;

            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();





                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();

                if(!tenmonan.equals("") && !giatien.equals("") && !sDuongDanHinh.equals("") && (vitri != -1)){
                    int maloai = loaiMonAnDTOList.get(vitri).getMaLoai() ;
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sDuongDanHinh);
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);

                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,"Lỗi thêm món ăn !",Toast.LENGTH_SHORT).show();
                }


                finish();
                break;

            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON) {
            if (resultCode == Activity.RESULT_OK) {
                boolean kiemtra = data.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_CODE_MOHINH && data!=null) {
            if (resultCode == Activity.RESULT_OK) {
                imHinhThucDon.setImageURI(data.getData());
                sDuongDanHinh = data.getDataString();

            }
        }

    }
}
