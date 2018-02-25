package com.example.mypc.orderfooddemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.DAO.BanAnDAO;
import com.example.mypc.orderfooddemo2.FragmentApp.HienThiBanAnFragment;

/**
 * Created by MyPC on 24/02/2018.
 */

public class SuaBanAnActivity extends AppCompatActivity {

    Button btnDongYSua;
    EditText edSuaTenBan;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);

        btnDongYSua = (Button) findViewById(R.id.btnSuaBanAn);
        edSuaTenBan = (EditText) findViewById(R.id.edSuaBanAn);

        banAnDAO = new BanAnDAO(this);

        final int maban = getIntent().getIntExtra("maban", 0);

        btnDongYSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenban = edSuaTenBan.getText().toString().trim();
                if (tenban.equals("")) {
                    Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
                } else {
                    boolean kiemtra = banAnDAO.CapNhapLaiTenBan(maban, tenban);
                    Intent intent = new Intent();
                    intent.putExtra("kiemtra", kiemtra);
                    setResult(HienThiBanAnFragment.RESQUEST_CODE_SUA, intent);
                    finish();

                }
            }
        });

    }
}
