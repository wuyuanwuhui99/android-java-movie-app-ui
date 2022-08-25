package com.player.movie.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widhtmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth  = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight  = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidht = 0;
        int lineHeight = 0;

        int widht = 0;
        int height = 0;
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);

            MarginLayoutParams lp = null;

            if (childAt.getLayoutParams() instanceof MarginLayoutParams){
                lp = (MarginLayoutParams) childAt.getLayoutParams();
            }else{
                lp = new MarginLayoutParams(0,0);
            }

            int childwidht = childAt.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = childAt.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;


            if (lineWidht+childwidht >measureWidth){
                widht = lineWidht;
                height+= lineHeight;

                lineHeight = childHeight;
                lineWidht = childwidht;
            }else{
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidht = childwidht;
            }

            if (i == childCount-1){
                height += lineHeight;
                widht = Math.max(widht,lineWidht);
            }

        }
        setMeasuredDimension((widhtmode==MeasureSpec.EXACTLY)?measureWidth:widht,(heightmode==MeasureSpec.EXACTLY)?measureHeight:height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //还是先获取所有的子view
        int count = getChildCount();
        //定义列宽
        int lineWidth = 0;
        //定义行高
        int lineHeight = 0;
        //定义上、左边距
        int top = 0,left=0;

        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            //因为onMeasure(int widthMeasureSpec, int heightMeasureSpec)方法已经执行完，所有这里我们可以直接调用
            //子view的宽+左右边距
            int childWidth = childAt.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight = childAt.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            //这里的if判断和onMeasure中是一样的逻辑，不再赘述
            if(childWidth+lineWidth>getMeasuredWidth()){
                //累加top
                top+=lineHeight;
                //因为换行了left置为0
                left = 0;
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else{
                lineHeight = Math.max(lineHeight,childHeight);
                //行宽累加
                lineWidth+=childWidth;
            }
            //计算子view的左、上、右、下的值
            int lc = left+layoutParams.leftMargin;
            int tc = top+layoutParams.topMargin;
            //右边就等于自己的宽+左边的边距即lc
            int rc = lc+childAt.getMeasuredWidth();
            //底部逻辑同上
            int bc = tc+childAt.getMeasuredHeight();
            //布局
            childAt.layout(lc,tc,rc,bc);
            //这一句很重要，因为一行中有多个view，所有left是累加的关系。
            left+=childWidth;
        }

    }
}