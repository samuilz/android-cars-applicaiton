package com.example.zahariev.androidcarsapplication.views.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.zahariev.androidcarsapplication.R;

public class CustomView extends View {
    private Bitmap mImage;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)  {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float imageX = (getWidth() - mImage.getWidth()) / 2;

        canvas.drawBitmap(mImage, imageX, 0, null);
    }

    private void init(@Nullable AttributeSet attrs) {
        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.mercedeze63amg);

        getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        int padding = 50;

                        mImage = getResizedBitmap(mImage,
                                getWidth() - padding, getHeight() - padding);
                    }
                });
    }

    private Bitmap getResizedBitmap(Bitmap mImage, int reqWidth, int reqHeight) {
        Matrix matrix = new Matrix();

        RectF src = new RectF(0, 0, mImage.getWidth(), mImage.getHeight());
        RectF dst = new RectF(0, 0, reqWidth, reqHeight);

        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(mImage, 0, 0, mImage.getWidth(), mImage.getHeight(), matrix, true);
    }
}
