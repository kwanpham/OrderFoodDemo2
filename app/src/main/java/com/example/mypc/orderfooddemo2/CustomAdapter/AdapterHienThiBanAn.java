package com.example.mypc.orderfooddemo2.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.orderfooddemo2.DAO.BanAnDAO;
import com.example.mypc.orderfooddemo2.DAO.GoiMonDAO;
import com.example.mypc.orderfooddemo2.DTO.BanAnDTO;
import com.example.mypc.orderfooddemo2.DTO.GoiMonDTO;
import com.example.mypc.orderfooddemo2.FragmentApp.HienThiThucDonFragment;
import com.example.mypc.orderfooddemo2.R;
import com.example.mypc.orderfooddemo2.ThanhToanActivity;
import com.example.mypc.orderfooddemo2.TrangchuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MyPC on 13/12/2017.
 */

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<BanAnDTO> banAnDTOList;
    private ViewHolderBanAn viewHolderBanAn;
    private BanAnDAO banAnDAO;
    private GoiMonDAO goiMonDAO;
    private FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, List<BanAnDTO> banAnDTOList) {
        this.context = context;
        this.banAnDTOList = banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangchuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return banAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return banAnDTOList.get(i).getMaBan();
    }


    private class ViewHolderBanAn {
        ImageView imBanAn, imGoiMon, imThanhToan, imAnButton;
        TextView txtTenBanAn;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = LayoutInflater.from(context).inflate(R.layout.custom_layout_hienthibanan, viewGroup, false);
            viewHolderBanAn.imBanAn = view.findViewById(R.id.ivBanAn);
            viewHolderBanAn.imGoiMon = view.findViewById(R.id.ivGoiMon);
            viewHolderBanAn.imThanhToan = view.findViewById(R.id.ivThanhToan);
            viewHolderBanAn.imAnButton = view.findViewById(R.id.ivAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.tvTenBanAn);

            view.setTag(viewHolderBanAn);

        } else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        if (banAnDTOList.get(i).isDuocChon()) {
            HienThiButton();
        } else {
            AnButton(false);
        }

        BanAnDTO banAnDTO = banAnDTOList.get(i);
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());

        if (kttinhtrang.equals("true")){
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        } else {
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);
        }


        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imBanAn.setTag(i);


        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);
        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);


        return view;

    }


    @Override
    public void onClick(View view) {
        viewHolderBanAn = (ViewHolderBanAn) ((View) view.getParent()).getTag();
        int vitri1 = (int) viewHolderBanAn.imBanAn.getTag();
        int maban = banAnDTOList.get(vitri1).getMaBan();
        switch (view.getId()) {
            case R.id.ivBanAn:
                HienThiButton();
                banAnDTOList.get(vitri1).setDuocChon(true);
                break;
            case R.id.ivAnButton:
                AnButton(true);
                break;

            case R.id.ivGoiMon:

                Intent layItrangChu = ((TrangchuActivity) context).getIntent();
                int maNhanVien = layItrangChu.getIntExtra("manv" , 0);

                Log.d("dulieu" , maNhanVien +"");

                String tinhTrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if(tinhTrang.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngayGoi = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maban);
                    goiMonDTO.setMaNV(maNhanVien);
                    goiMonDTO.setNgayGoi(ngayGoi);
                    goiMonDTO.setTinhTrang("false");

                    long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);


                    if(kiemtra==0){
                        Toast.makeText(context,context.getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }else {
                        banAnDAO.CapNhapLaiTinhTrangBan(maban,"true");
                    }

                }

                FragmentTransaction transactionThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                Bundle bDuLieuThucDon = new Bundle();
                bDuLieuThucDon.putInt("maban", maban);
                Log.d("maban adpater" , "" + maban);

                hienThiThucDonFragment.setArguments(bDuLieuThucDon);

                transactionThucDon.replace(R.id.content , hienThiThucDonFragment).addToBackStack("hienthibanan");
                transactionThucDon.commit();

                break;

            case R.id.ivThanhToan:
                Intent iThanhToan = new Intent(context , ThanhToanActivity.class);
                iThanhToan.putExtra("maban" , maban);
                context.startActivity(iThanhToan);
                break;
        }
    }


    private void HienThiButton(){

        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);  // button phải hiện thị trước

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void AnButton(boolean hieuung){
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }


}

