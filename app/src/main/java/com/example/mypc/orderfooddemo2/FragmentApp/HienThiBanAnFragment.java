package com.example.mypc.orderfooddemo2.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.CustomAdapter.AdapterHienThiBanAn;
import com.example.mypc.orderfooddemo2.DAO.BanAnDAO;
import com.example.mypc.orderfooddemo2.DTO.BanAnDTO;
import com.example.mypc.orderfooddemo2.Database.CreateDatabase;
import com.example.mypc.orderfooddemo2.R;
import com.example.mypc.orderfooddemo2.SuaBanAnActivity;
import com.example.mypc.orderfooddemo2.ThemBanAnActivity;
import com.example.mypc.orderfooddemo2.TrangchuActivity;

import java.util.List;

/**
 * Created by MyPC on 22/11/2017.
 */

public class HienThiBanAnFragment extends Fragment {

    public final static int RESQUEST_CODE_THEM = 111;
    public final static int RESQUEST_CODE_SUA = 116;
    GridView gvHienThiBanAn;
    BanAnDAO banAnDAO;
    List<BanAnDTO> banAnDTOList;
    AdapterHienThiBanAn adapterHienThiBanAn;
    SharedPreferences sharedPreferences;
    int maquyen;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);

        gvHienThiBanAn = (GridView) view.findViewById(R.id.gvHienThiBanAn);
        banAnDAO = new BanAnDAO(getActivity());
        HienThiDanhSachBanAn();
        ((TrangchuActivity) getActivity()).getSupportActionBar().setTitle("Bàn Ăn");

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        Log.d("maquyen" ,"" +maquyen);


        if (maquyen == 1) {
            registerForContextMenu(gvHienThiBanAn);
            setHasOptionsMenu(true);
        }


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen != 0) {
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, "Thêm bàn ăn");
            MenuItem itXoaCSDL = menu.add(1, R.id.itXoaCSDL, 1, "Xoa CSDL");


            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // Hien thi icon

            itXoaCSDL.setIcon(R.drawable.password);
            itXoaCSDL.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn, RESQUEST_CODE_THEM);
                break;

            case R.id.itXoaCSDL:
                CreateDatabase createDatabase = new CreateDatabase(getContext());
                createDatabase.xoaDataBase(getContext());
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESQUEST_CODE_THEM:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = data;
                    boolean kiemtra = intent.getBooleanExtra("ketquathem", false);
                    if (kiemtra) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case RESQUEST_CODE_SUA:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = data;
                    boolean kiemtra = intent.getBooleanExtra("kiemtra", false);

                    if (kiemtra) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                    }
                }

        }
    }


    private void HienThiDanhSachBanAn() {
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
//        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnDTOList.get(vitri).getMaBan();
        switch (item.getItemId()) {
            case R.id.itSua:
                Intent iSuaBanAn = new Intent(getActivity(), SuaBanAnActivity.class);
                iSuaBanAn.putExtra("maban", maban);
                startActivityForResult(iSuaBanAn, RESQUEST_CODE_SUA);
                break;

            case R.id.itXoa:
                boolean kiemtra = banAnDAO.xoaBanAnTheoMa(maban);
                if (kiemtra) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                    HienThiDanhSachBanAn();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                break;


        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiDanhSachBanAn();
    }
}
