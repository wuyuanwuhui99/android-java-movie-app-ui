package com.player.movie.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    private int year;
    private int month;
    private int day;
    private final DatePickerFragment.CheckListener checkListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog(getActivity(), (view, year1, month1, day1) -> {
            // 处理日期选择事件
            // 例如：显示选择的日期
             String selectedDate = String.format("%d-%02d-%02d", year1, month1 + 1, day1);
            // Toast.makeText(getActivity(), selectedDate, Toast.LENGTH_SHORT).show();
            checkListener.onCheck(selectedDate);
        }, year, month, day);
    }
    public DatePickerFragment(int year, int month, int day,CheckListener checkListener){
        this.year = year;
        this.month = month;
        this.day = day;
        this.checkListener = checkListener;
    }

    public interface CheckListener{
        void onCheck(String selectedDate);
    }
}