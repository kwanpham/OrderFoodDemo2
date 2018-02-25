package com.example.mypc.orderfooddemo2.FragmentApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mypc.orderfooddemo2.R;

import java.util.Calendar;


public class DataPickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, iNam, iThang, iNgay);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        EditText edNgaySinh =(EditText) getActivity().findViewById(R.id.edNgaySinhDK);
        String sNgaySinh = i + "/" + (i1 + 1) + "/" + i2;
        edNgaySinh.setText(sNgaySinh);

    }


}
