package com.example.customfloatview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customfloatview.ActivityManager;
import com.example.customfloatview.R;


/**
 * @Author lili
 * @Date 2023/8/3-18:37
 * 自定义浮动布局
 */
public class FloatingView extends RelativeLayout {
    private TextView textView1, textView2;
    private ImageView closeButton;
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private CloseClickListener closeClickListener;

    public FloatingView(Context context) {
        super(context);
        init();
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取屏幕宽度和高度
        WindowManager wm = (WindowManager) ActivityManager.getActivityManager().getCurrentActivity().getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;

            // 添加两个TextView
            textView1 = new TextView(getContext());
            textView2 = new TextView(getContext());
            closeButton = new ImageView(getContext());
            closeButton.setImageResource(R.drawable.ic_close);
            closeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (closeClickListener != null) {
                        closeClickListener.onClick();
                    }
                }
            });

            // 设置TextView、CloseButton的位置和大小
            LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams1.setMargins(10, 10, 0, 0);
            textView1.setLayoutParams(layoutParams1);

            LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.addRule(RelativeLayout.BELOW, textView1.getId());
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams2.setMargins(10, 70, 0, 20);
            textView2.setLayoutParams(layoutParams2);

            LayoutParams closeButtonParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            closeButtonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            closeButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            closeButtonParams.setMargins(0, 10, 0, 0);
            closeButton.setLayoutParams(closeButtonParams);

            addView(textView1);
            addView(textView2);
            addView(closeButton);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //起始坐标
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //当前触摸点与上一次触摸点的偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                // 根据偏移量更新新的位置坐标，通过将当前位置坐标`getX()`和`getY()`与偏移量相加得到新的X坐标`newX`和Y坐标`newY`
                float newX = getX() + offsetX;
                float newY = getY() + offsetY;

                // 修正超出屏幕的位置
                //如果新的X坐标小于0，将其设置为0；如果新的X坐标大于屏幕宽度减去View宽度，将其设置为屏幕宽度减去View宽度；
                //如果新的Y坐标小于0，将其设置为0；如果新的Y坐标大于屏幕高度减去View高度，将其设置为屏幕高度减去View高度。
                if (newX < 0) {
                    newX = 0;
                } else if (newX > screenWidth - getWidth()) {
                    newX = screenWidth - getWidth();
                }

                if (newY < 0) {
                    newY = 0;
                } else if (newY > screenHeight - getHeight()) {
                    newY = screenHeight - getHeight();
                }

                // 更新View的位置
                setX(newX);
                setY(newY);

                lastX = x;
                lastY = y;
                break;
        }
        return true;
    }


    public void setText(String text1, String text2) {
        textView1.setText(text1);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView2.setText(text2);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
    }

    public CloseClickListener getCloseClickListener() {
        return closeClickListener;
    }

    public void setCloseClickListener(CloseClickListener closeClickListener) {
        this.closeClickListener = closeClickListener;
    }

    public interface CloseClickListener {
        void onClick();
    }
}

