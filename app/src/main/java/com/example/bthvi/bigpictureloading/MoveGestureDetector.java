package com.example.bthvi.bigpictureloading;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
/**
 * 手势处理
 * create by bthvi on 2018/06/29
 */
public class MoveGestureDetector extends BaseGestureDetector{
    /**
     * 当前点
     */
    private PointF mCurrentPointer;
    /**
     * 上次触摸点
     */
    private PointF mPrePointer;
    //仅仅为了减少创建内存
    private PointF mDeltaPointer = new PointF();

    //用于记录最终结果，并返回
    private PointF mExtenalPointer = new PointF();

    private OnMoveGestureListener mListenter;

    public MoveGestureDetector(Context context) {
        super(context);
    }
    public void setOnMoveGestureListener(OnMoveGestureListener listener){
        this.mListenter = listener;
    }

    public OnMoveGestureListener getmListenter(){
        return this.mListenter;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        /**
         * 这里就是处理多点触控，MotionEvent.ACTION_MASK(0x000000ff)与触摸事件进行逻辑运算and。
         * 无论多少手指在屏幕上操作，进过逻辑and运算后 都是一个手指
         */
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListenter.onMoveEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean update = mListenter.onMove(this);
                if (update) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode){
            case MotionEvent.ACTION_DOWN:
                /**防止没收到 UP 或 CANCEL*/
                resetState();
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListenter.onMove(this);
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        final MotionEvent prev = mPreMotionEvent;

        mPrePointer = calculateCenterPointer(prev);
        mCurrentPointer = calculateCenterPointer(event);

        boolean mSkipThisEvent = prev.getPointerCount() != event.getPointerCount();

        mExtenalPointer.x = mSkipThisEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
        mExtenalPointer.y = mSkipThisEvent ? 0 : mCurrentPointer.y - mPrePointer.y;

    }

    /**
     * 计算多指触控中心点
     * @param event
     * @return
     */
    private PointF calculateCenterPointer(MotionEvent event) {
        /**
         * 触摸点数
         */
        final int count = event.getPointerCount();
        float x=0,y=0;
        for (int i = 0; i< count;i++){
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= count;
        y /= count;
        return new PointF(x,y);
    }
    public float getMoveX()
    {
        return mExtenalPointer.x;

    }

    public float getMoveY()
    {
        return mExtenalPointer.y;
    }

}
