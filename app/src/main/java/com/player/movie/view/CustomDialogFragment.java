package com.player.movie.view;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.player.R;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{
    AlertDialog.Builder builder;
    ClickSureListener clickSureListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view).setPositiveButton(null,null).setCancelable(true);;
        view.findViewById(R.id.dialog_sure).setOnClickListener(this);
        view.findViewById(R.id.dialog_cancle).setOnClickListener(this);
        return builder.create();
    }

    public CustomDialogFragment(ClickSureListener clickSureListener){
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

    public interface ClickSureListener {
        void onSure();
    }
}



