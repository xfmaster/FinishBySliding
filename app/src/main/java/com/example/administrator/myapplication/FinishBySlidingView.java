package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

public class FinishBySlidingView extends LinearLayout {
    private float moveX, downX, downY, moveY;
    private Scroller mScroller;
    private int width;
    private Activity mActivity;
    private Paint mShadowPaint;
    private int mShadowWidth = 80;
    private Drawable mLeftShadow;
    private float dx;
    private ViewPager mViewPager;
    private static int THRESHOLDS = 50;

    public FinishBySlidingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inite(context);
    }

    public FinishBySlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inite(context);
    }

    public FinishBySlidingView(Context context) {
        super(context);
        inite(context);
    }

    private void inite(Context context) {
        mScroller = new Scroller(context);
        mActivity = (Activity) context;
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(0xff000000);
        mLeftShadow = context.getResources()
                .getDrawable(R.drawable.flip_shadow);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            width = getWidth();
        }
        super.onLayout(changed, 0, 0, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) (ev.getRawX() - downX);
                moveY = (int) (ev.getRawY() - downY);
                Log.d("xf", moveY + "========");
                if (mViewPager == null && Math.abs(moveY) > 10 && Math.abs(moveX) < THRESHOLDS) {
                    return false;
                } else if ((mViewPager != null && mViewPager.getCurrentItem() == 0 && moveX > THRESHOLDS && Math.abs(moveY) < THRESHOLDS) || (mViewPager == null && moveX > 50 && Math.abs(moveY) < THRESHOLDS)) {
                    return true;
                } else if (mViewPager != null && mViewPager.getCurrentItem() != 0 && moveX < 0) {
                    return false;
                }
                moveX = ev.getRawX();
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                downY = event.getRawY() - downY;
                float scrollX = 0;
                Log.d("xf", width + "-----");
                if (moveX > 0) {
                    this.scrollTo((int) -moveX, 0);
                    scrollX = this.getScrollX();
                } else if (moveX <= 0 && scrollX > 0) {
                    this.scrollTo((int) moveX, 0);
                    scrollX = this.getScrollX();
                }
                dx = Math.abs(scrollX) / width;
                moveX = (int) (event.getRawX());
                break;
            case MotionEvent.ACTION_UP:
                int dX = this.getScrollX();
                if (-dX >= width / 4) {
                    dx = 1;
                    postInvalidate();
                    mActivity.finish();
                    mActivity.overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else if (-dX < width / 4) {
                    scrollToStart();
                }
                break;

            default:
                break;
        }

        return true;
    }

    /**
     * 滑动到起始位置
     */
    private void scrollToStart() {
        int delta = this.getScrollX();
        mScroller.startScroll(this.getScrollX(), 0, -delta, 0, 500);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);
        drawBackround(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getView(this);
    }

    private void drawBackround(Canvas canvas) {
        mShadowPaint.setAlpha((int) ((1 - dx) * 180));
        canvas.drawRect(this.getScrollX(), 0, 0, getHeight(), mShadowPaint);
    }

    /**
     * 作者：xf
     * 时间：2016/3/28 0028 21:17
     * 画阴影部分
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {

		/* 保存画板 */
        canvas.save();
         /*设置 drawable 的大小范围*/
        mLeftShadow.setBounds(0, 0, mShadowWidth, getHeight());
        /*让画布平移一定距离*/
        canvas.translate(-mShadowWidth, 0);
        /*绘制Drawable*/
        mLeftShadow.draw(canvas);
        /*恢复画布的状态*/
        canvas.restore();
    }

    /**
     * 作者：xf
     * 时间：2016/3/28 0028 22:25
     * 递归获取子View
     */
    private void getView(ViewGroup view) {
        int childViewCount = view.getChildCount();
        Log.d("xf", "getView: " + childViewCount);
        if (childViewCount == 0)
            return;
        for (int i = 0; i < childViewCount; i++) {
            View childView = getChildAt(i);
            Log.d("xf", "getView: " + (getChildAt(i) instanceof Button));
            if (childView instanceof ViewPager) {
                setViewPager((ViewPager) childView);
            } else if (childView instanceof ScrollView) {
                return;
            } else if (childView instanceof ViewGroup) {
                getView((ViewGroup) childView);
            }
        }
    }

    /**
     * 作者：xf
     * 时间：2016/3/29 0029 20:46
     * 处理ViewPager的不能滑动关闭的问题
     */
    private void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

}
