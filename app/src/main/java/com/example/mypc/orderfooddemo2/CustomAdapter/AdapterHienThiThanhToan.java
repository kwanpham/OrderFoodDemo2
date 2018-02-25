package com.example.mypc.orderfooddemo2.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mypc.orderfooddemo2.DTO.ThanhToanDTO;
import com.example.mypc.orderfooddemo2.R;

import java.util.List;
/**
 * Created by MyPC on 21/02/2018.
 */

public class AdapterHienThiThanhToan extends BaseAdapter {

    private Context context;
    private List<ThanhToanDTO> thanhToanDTOList;
    ViewHolderThanhToan viewHolderThanhToan;

    public AdapterHienThiThanhToan(Context context, List<ThanhToanDTO> thanhToanDTOList) {
        this.context = context;
        this.thanhToanDTOList = thanhToanDTOList;
    }

    private class ViewHolderThanhToan{
        TextView tvTenMonAn,tvSoLuong,tvGiaTien;

    }


    @Override
    public int getCount() {
        return thanhToanDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            view = LayoutInflater.from(context).inflate(R.layout.custom_layout_thanhtoan, parent , false);

            viewHolderThanhToan.tvTenMonAn = (TextView) view.findViewById(R.id.tvTenMonAnThanhToan);
            viewHolderThanhToan.tvGiaTien = (TextView) view.findViewById(R.id.tvGiaTienThanhToan);
            viewHolderThanhToan.tvSoLuong = (TextView) view.findViewById(R.id.tvSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }else{
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(position);

        viewHolderThanhToan.tvTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.tvSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.tvGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));

        return view;
    }

}
