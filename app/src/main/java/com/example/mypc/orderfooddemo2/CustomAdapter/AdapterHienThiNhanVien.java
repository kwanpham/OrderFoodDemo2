package com.example.mypc.orderfooddemo2.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypc.orderfooddemo2.DTO.NhanVienDTO;
import com.example.mypc.orderfooddemo2.R;

import java.util.List;

/**
 * Created by Nhox on 4/13/2016.
 */
public class AdapterHienThiNhanVien extends BaseAdapter {

    Context context;

    List<NhanVienDTO> nhanVienDTOList;
    ViewHolderNhanVien viewHolderNhanVien;

    public AdapterHienThiNhanVien(Context context,List<NhanVienDTO> nhanVienDTOList){
        this.context = context;

        this.nhanVienDTOList = nhanVienDTOList;
    }

    @Override
    public int getCount() {
        return nhanVienDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienDTOList.get(position).getMANV();
    }

    public class ViewHolderNhanVien{
        ImageView imHinhNhanVien;
        TextView txtTenNhanVien,txtCMND;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            view = LayoutInflater.from(context).inflate(R.layout.custom_layout_hienthinhanvien , parent, false);

            viewHolderNhanVien.imHinhNhanVien = (ImageView) view.findViewById(R.id.imHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = (TextView) view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtCMND = (TextView) view.findViewById(R.id.txtCMND);

            view.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(position);

        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getTENDN());
        viewHolderNhanVien.txtCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
        return view;
    }
}
