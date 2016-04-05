# FinishBySliding
通过手势滑动退出activity，并带阴影效果加，修改了viewpager不能用的bug
#先看一张运行图片
####![image](https://github.com/xfhomedream/FinishBySliding/blob/master/app/src/main/res/mipmap-hdpi/cutpic.jpg)
##FinishBySlidingView是继承LinearLayout的，该自定义view主要对touch事件的分发、拦截、滑动处理
##1、该方法就是对touch事件的拦截做处
 ```java@Override
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
    ```
2、该方法就是对touch事件的滑动做处理
 
 ```java@Override
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
     ```
    3、 最重要的可以看到前一个activity的界面，就是设置主题如：
     ```xml<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="android:windowBackground">@color/transparent</item>
        <item name="android:windowIsTranslucent">true</item>
    </style>
     
