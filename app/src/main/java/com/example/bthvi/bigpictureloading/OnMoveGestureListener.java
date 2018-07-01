package com.example.bthvi.bigpictureloading;
/**
 * 手势监听接口
 * create by bthvi on 2018/06/29
 */
public interface OnMoveGestureListener {
    public boolean onMoveBegin(MoveGestureDetector detector);
    public boolean onMove(MoveGestureDetector detector);
    public void onMoveEnd(MoveGestureDetector detector);
}
