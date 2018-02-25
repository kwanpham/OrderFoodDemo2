package com.example.mypc.orderfooddemo2.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mypc.orderfooddemo2.DTO.LoaiMonAnDTO;
import com.example.mypc.orderfooddemo2.R;

import java.util.List;

/**
 * Created by MyPC on 15/12/2017.
 */

public class AdapterHienThiLoaiMonAnSpinder extends BaseAdapter {

    private Context context;
    private List<LoaiMonAnDTO> loaiMonAnDTOList;
    private ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public AdapterHienThiLoaiMonAnSpinder(Context context, List<LoaiMonAnDTO> loaiMonAnDTOList) {
        this.context = context;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
    }

    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return loaiMonAnDTOList.get(i).getMaLoai();
    }

    private class ViewHolderLoaiMonAn {
        TextView txtTenLoai;
    }

//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup viewGroup) {
//        View view = convertView;
//        if (view == null) {
//            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
//            view = LayoutInflater.from(context).inflate(R.layout.custom_layout_spinloaithucdon, viewGroup, false);
//
//            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);
//
//            view.setTag(viewHolderLoaiMonAn);
//
//        } else {
//            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
//        }
//
//        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
//        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
//        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());
//
//
//        return view;
//    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            view = LayoutInflater.from(context).inflate(R.layout.custom_layout_spinloaithucdon, viewGroup, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);

        } else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());


        return view;
    }
}
