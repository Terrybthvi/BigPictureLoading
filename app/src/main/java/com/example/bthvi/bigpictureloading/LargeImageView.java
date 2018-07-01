package com.example.bthvi.bigpictureloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义加载超大图片的View
 *
 * create by bthvi on 2018/06/29
 */
public class LargeImageView extends View {
    /**
     *用来解码一张图片的某个矩形区域
     */
    private BitmapRegionDecoder mDecoder;

    /**
     * 图片的宽度和高度
     */
    private int mImageWidth,mImageHeight;

    /**
     *绘制图片区域
     */
    private volatile Rect rect = new Rect();
    /**
     * 手势监听器
     */
    private MoveGestureDetector moveGestureDetector;
    /**
     * 图片解码时的参数配置类
     */
    private static final BitmapFactory.Options mOptions = new BitmapFactory.Options();

    /**
     * 图片解码时所用的颜色模式
     */
    static {
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    /**
     * 构造方法
     * @param context
     */
    public LargeImageView(Context context) {
        super(context);
        initView();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        moveGestureDetector.onToucEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Bitmap bm = mDecoder.decodeRegion(rect, mOptions);
        canvas.drawBitmap(bm, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        //默认直接显示图片的中心区域，可以自己去调节
        rect.left = imageWidth / 2 - width / 2;
        rect.top = imageHeight / 2 - height / 2;
        rect.right = rect.left + width;
        rect.bottom = rect.top + height;

    }
    /**
     * 初始化
     */
    private void initView() {
        moveGestureDetector = new MoveGestureDetector(getContext());
        SimpleMoveGestureDetector simpleMoveGestureDetector = new SimpleMoveGestureDetector(){
            @Override
            public boolean onMove(MoveGestureDetector detector){
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();

                if (mImageWidth > getWidth()){
                    rect.offset(-moveX,0);
                    checkWidth();
                    invalidate();
                }
                if (mImageHeight > getHeight())
                {
                    rect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }

                return true;
            }
        };
        moveGestureDetector.setOnMoveGestureListener(simpleMoveGestureDetector);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setInputStream(InputStream is)
    {
        try
        {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            // Grab the bounds for the scene dimensions
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tmpOptions);
            /**
             * 获取图片的宽高
             */
            mImageWidth = mDecoder.getWidth();
            mImageHeight = mDecoder.getHeight();

            requestLayout();
            invalidate();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {

            try
            {
                if (is != null) is.close();
            } catch (Exception e)
            {
            }
        }
    }


    private void checkWidth() {
        Rect rect2 = rect;
        int imageWidth = mImageWidth;
        if (rect2.right > imageWidth){
            rect2.right = imageWidth;
            rect2.left = imageWidth - getWidth();
        }

        if (rect2.left < 0){
            rect2.left = 0;
            rect2.right = getWidth();
        }
    }
    private void checkHeight()
    {

        Rect rect3 = rect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect3.bottom > imageHeight)
        {
            rect3.bottom = imageHeight;
            rect3.top = imageHeight - getHeight();
        }

        if (rect3.top < 0)
        {
            rect3.top = 0;
            rect3.bottom = getHeight();
        }
    }

}
