package com.player.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.player.R;

import java.util.ArrayList;
import java.util.List;

public class ButtomMenuDialog extends Dialog {

    public ButtomMenuDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Params {
        private final List<BottomMenu> menuList = new ArrayList<>();
        private View.OnClickListener cancelListener;
        private CharSequence menuTitle;
        private String cancelText;
        private Context context;
    }

    public static class Builder {
        private boolean canCancel = true;
        private boolean shadow = true;
        private final Params p;

        public Builder(Context context) {
            p = new Params();
            p.context = context;
        }

        public Builder setCanCancel(boolean canCancel) {
            this.canCancel = canCancel;
            return this;
        }

        public Builder setShadow(boolean shadow) {
            this.shadow = shadow;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.p.menuTitle = title;
            return this;
        }

        public Builder addMenu(String text, View.OnClickListener listener) {
            BottomMenu bm = new BottomMenu(text, listener);
            this.p.menuList.add(bm);
            return this;
        }

        public Builder addMenu(int textId, View.OnClickListener listener) {
            return addMenu(p.context.getString(textId), listener);
        }

        public Builder setCancelListener(View.OnClickListener cancelListener) {
            p.cancelListener = cancelListener;
            return this;
        }

        public Builder setCancelText(int resId) {
            p.cancelText = p.context.getString(resId);
            return this;
        }

        public Builder setCancelText(String text) {
            p.cancelText = text;
            return this;
        }

        public ButtomMenuDialog create() {
            final ButtomMenuDialog dialog = new ButtomMenuDialog(p.context, shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            window.setGravity(Gravity.BOTTOM);
            View view = LayoutInflater.from(p.context).inflate(R.layout.dialog_bottom_menu, null);
            TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
            ViewGroup layContainer = (ViewGroup) view.findViewById(R.id.lay_container);
            ViewGroup.LayoutParams lpItem = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams lpDivider = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            lpDivider.setMargins(50,0,50,0);
            int dip1 = (int) (1 * p.context.getResources().getDisplayMetrics().density + 0.5f);
            int spacing = dip1 * 12;
            if (p.menuTitle != null && !"".equals(p.menuTitle)) {
                //标题样式
                TextView tTitle = new TextView(p.context);
                tTitle.setLayoutParams(lpItem);
                tTitle.setGravity(Gravity.CENTER);
//                tTitle.setTextColor(p.context.getResources().getColor(R.color.colorAccent));
                tTitle.setText(p.menuTitle);
                tTitle.setPadding(0, spacing, 0, spacing);
                //单独给标题设置背景样式
//        tTitle.setBackgroundResource(R.drawable.common_dialog_selection_selector_top);
                layContainer.addView(tTitle);
                View viewDivider = new View(p.context);
                viewDivider.setLayoutParams(lpDivider);
                viewDivider.setBackgroundColor(0xFFCED2D6);
                layContainer.addView(viewDivider);
            }
            //每一条的样式
            for (int i = 0; i < p.menuList.size(); i++) {
                BottomMenu bottomMenu = p.menuList.get(i);
                TextView bbm = new TextView(p.context);
                bbm.setLayoutParams(lpItem);
                bbm.setPadding(0, spacing, 0, spacing);
                bbm.setGravity(Gravity.CENTER);
                bbm.setText(bottomMenu.funName);
                bbm.setTextColor(0xFF007AFF);
                bbm.setTextSize(16);
                bbm.setOnClickListener(bottomMenu.listener);
                layContainer.addView(bbm);
                if (i != p.menuList.size() - 1) {
                    View viewDivider = new View(p.context);
                    viewDivider.setLayoutParams(lpDivider);
                    viewDivider.setBackgroundColor(0xFFCED2D6);
                    layContainer.addView(viewDivider);
                }
            }

            if (p.menuTitle != null && !"".equals(p.menuTitle)) {
                btnCancel.setText(p.cancelText);
            }

            if (p.cancelListener != null) {
                btnCancel.setOnClickListener(p.cancelListener);
            } else {
                btnCancel.setOnClickListener(v -> dialog.dismiss());
            }

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(canCancel);
            dialog.setCancelable(canCancel);
            return dialog;
        }

    }

    private static class BottomMenu {
        public String funName;
        public View.OnClickListener listener;

        public BottomMenu(String funName, View.OnClickListener listener) {
            this.funName = funName;
            this.listener = listener;
        }
    }
}
