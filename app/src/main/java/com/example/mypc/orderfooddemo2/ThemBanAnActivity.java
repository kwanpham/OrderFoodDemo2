package com.example.mypc.orderfooddemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mypc.orderfooddemo2.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edThemBanAn;
    Button btnThemBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        edThemBanAn = (EditText) findViewById(R.id.edThemBanAn);
        btnThemBanAn = (Button) findViewById(R.id.btnThemBanAn);

        banAnDAO = new BanAnDAO(this);
        btnThemBanAn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnThemBanAn:
                String sTenBanAn = edThemBanAn.getText().toString().trim();
                if(!sTenBanAn.equals("")){
                    boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
                    Intent intent = new Intent();
                    intent.putExtra("ketquathem", kiemtra);
                    setResult(RESULT_OK , intent);
                    finish();

                }
        }
    }
}
