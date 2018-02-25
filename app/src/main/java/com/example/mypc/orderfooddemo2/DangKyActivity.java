package com.example.mypc.orderfooddemo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.DAO.NhanVienDAO;
import com.example.mypc.orderfooddemo2.DAO.QuyenDAO;
import com.example.mypc.orderfooddemo2.DTO.NhanVienDTO;
import com.example.mypc.orderfooddemo2.FragmentApp.DataPickerFragment;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edTenDangNhapDK, edMatKhauDK, edNgaySinhDK, edCMNDDK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    TextView tvTieuDeDK;
    RadioButton rdNam, rdNu;
    private String sGioiTinh;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;

    int manv = 0;
    int landautien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        InitView();
        nhanVienDAO = new NhanVienDAO(DangKyActivity.this);
        quyenDAO = new QuyenDAO(this);
        manv = getIntent().getIntExtra("manv", 0);
        landautien = getIntent().getIntExtra("landautien", 0);

        if (landautien != 0) {
            quyenDAO.ThemQuyen("quản lý");
            quyenDAO.ThemQuyen("nhân viên");
        }


        if (manv != 0) {
            tvTieuDeDK.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manv);

            edTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));

            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")) {
                rdNam.setChecked(true);
            } else {
                rdNu.setChecked(true);
            }

            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));

        }

    }


    public void InitView() {
        edTenDangNhapDK = (EditText) findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK = (EditText) findViewById(R.id.edMatKhauDK);
        edNgaySinhDK = (EditText) findViewById(R.id.edNgaySinhDK);
        edCMNDDK = (EditText) findViewById(R.id.edCMNDDK);
        btnDongYDK = (Button) findViewById(R.id.btnDongYDK);
        btnThoatDK = (Button) findViewById(R.id.btnThoatDK);
        rgGioiTinh = (RadioGroup) findViewById(R.id.rgGioiTinh);
        tvTieuDeDK = (TextView) findViewById(R.id.tvTieuDeDangKy);
        rdNam = (RadioButton) findViewById(R.id.rdNam);
        rdNu = (RadioButton) findViewById(R.id.rdNu);

        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edNgaySinhDK.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnDongYDK:
                if (manv != 0) {
                    suaNhanVien();
                } else {
                    themNhanVien();
                }
                break;

            case R.id.btnThoatDK:
                finish();
                break;

        }

    }

    @Override
    public void onFocusChange(View view, boolean hasForce) {

        if (view.getId() == R.id.edNgaySinhDK) {
            if (hasForce) {
                DataPickerFragment dataPickerFragment = new DataPickerFragment();
                dataPickerFragment.show(getSupportFragmentManager(), "Ngay sinh");


            }

        }

    }

    private void themNhanVien() {
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        String sNgaySinh = edNgaySinhDK.getText().toString();
        String sCMND = edCMNDDK.getText().toString();


        if (sTenDangNhap.equals("") || sNgaySinh.equals("") || sMatKhau.equals("") || sCMND.equals("")) {
            Toast.makeText(DangKyActivity.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_LONG);
        } else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setCMND(Integer.parseInt(sCMND));
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            if (landautien != 0) {
                nhanVienDTO.setMAQUYEN(1);
            } else {
                nhanVienDTO.setMAQUYEN(2);
            }
            long kiemTra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if (kiemTra != 0) {
                Toast.makeText(DangKyActivity.this, "Bạn đã thêm thành công ", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(DangKyActivity.this, "Thêm dữ liệu thất bại , vui lòng kiểm tra lại ", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void suaNhanVien() {
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        String sNgaySinh = edNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edCMNDDK.getText().toString());
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;

            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manv);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setCMND(sCMND);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);



        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if (kiemtra) {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
        }

    }

}
