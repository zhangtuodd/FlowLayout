package com.example.zhangtuo.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangtuo on 2017/7/15.
 * <p>
 * 流式布局
 */

public class FlowLayout extends ViewGroup {

    private Context context;

    TextView textView;
    //一个标签的宽度
    int flagWidth;
    //父布局的padding值
    int px1;
    //子view之间的padding值
    int px2;
    //标签内的paddingTop值
    int px3;


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    public int dp2px(int dp) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);

    }

    private void initView(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        //父布局的padding值
        px1 = dp2px(15);
        //子view之间的padding值
        px2 = dp2px(10);
        //子view的宽度
        flagWidth = (screenWidth - px1 * 2 - px2 * 2) / 3;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }


    public void setDataList(List<String> list) {

        if (list == null) {
            return;
        }

        int left;
        int top;
        int right;
        int bottom;
        int lineHeight = 0;

        for (int i = 0; i < list.size(); i++) {
            textView = new TextView(context);
            textView.setBackground(getResources().getDrawable(R.drawable.module_biz_my_drawable_flag_bg));
            textView.setText(list.get(i));
            lineHeight = (int) (textView.getTextSize() + textView.getPaddingTop() * 2 + textView.getCompoundPaddingTop() * 2);
            LayoutParams textParams = new LayoutParams(flagWidth, lineHeight);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(textParams);

            //计算子布局坐标
            if (i < 3) {
                left = (flagWidth + px2) * i + px1;
                top = px1;
                right = left + flagWidth;
                bottom = lineHeight + px1;
            } else {
                left = (flagWidth + px2) * (i % 3) + px1;
                top = lineHeight + px2 + px1;
                right = left + flagWidth;
                bottom = top + lineHeight;
            }
            //设置子view位置
            textView.layout(left, top, right, bottom);
            this.addView(textView);
        }
        //计算父布局宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (list.size() < 4) {
            params.height = lineHeight + px1 * 2;
        } else {
            params.height = lineHeight * 2 + px1 * 2 + px2;
        }
        setLayoutParams(params);
        setPadding(px1, px1, px1, px1);
    }
}
