package com.player.movie.view;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.player.R;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{
    private final ClickSureListener clickSureListener;
    private int layoutId;
    private final String title;
    private View toInsertLayout;
    TextView sureBtnView;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view).setPositiveButton(null,null).setCancelable(true);;
        view.findViewById(R.id.dialog_sure).setOnClickListener(this);
        view.findViewById(R.id.dialog_cancle).setOnClickListener(this);
        sureBtnView = view.findViewById(R.id.dialog_sure);
        if(toInsertLayout == null){// 加载要插入的布局
            toInsertLayout = inflater.inflate(layoutId, null);
        }
        // 找到要插入的容器
        ViewGroup insertPoint = view.findViewById(R.id.insert_point);
        // 将要插入的布局添加到容器中
        insertPoint.addView(toInsertLayout);
        TextView titleView = view.findViewById(R.id.dialog_title);
        if(!"".equals(title) && title != null){
            titleView.setText(title);
        }
        return builder.create();
    }

    public CustomDialogFragment(int layoutId,String title,ClickSureListener clickSureListener){
        this.layoutId = layoutId;
        this.title = title;
        this.clickSureListener = clickSureListener;
    }

    public CustomDialogFragment(View toInsertLayout,String title,ClickSureListener clickSureListener){
        this.toInsertLayout = toInsertLayout;
        this.title = title;
        this.clickSureListener = clickSureListener;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_sure:
                clickSureListener.onSure();
                dismiss();
                break;
            case R.id.dialog_cancle:
                dismiss();
        }
    }

    public TextView getSureBtn() {
        return sureBtnView;
    }

    public interface ClickSureListener {
        void onSure();
    }
}



