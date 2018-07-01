package com.example.bthvi.bigpictureloading;

import android.content.Context;
import android.view.MotionEvent;
/**
 * 手势处理抽象类
 * create by bthvi on 2018/06/29
 */
public abstract class BaseGestureDetector
{

    protected boolean mGestureInProgress;

    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurrentMotionEvent;

    protected Context mContext;

    public BaseGestureDetector(Context context)
    {
        mContext = context;
    }


    public boolean onToucEvent(MotionEvent event)
    {

        if (!mGestureInProgress)
        {
            handleStartProgressEvent(event);
        } else
        {
            handleInProgressEvent(event);
        }

        return true;

    }

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);

    protected void resetState()
    {
        if (mPreMotionEvent != null)
        {
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if (mCurrentMotionEvent != null)
        {
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }
        mGestureInProgress = false;
    }


}