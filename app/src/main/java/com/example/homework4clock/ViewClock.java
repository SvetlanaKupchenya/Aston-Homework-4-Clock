package com.example.homework4clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class ViewClock extends View {

    private int height = 0;
    private int width = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;

    public ViewClock(Context context) {
        super(context);
    }

    public ViewClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        int min = Math.min(height, width);
        radius = min/2;
        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }

        canvas.drawColor(Color.WHITE);
        drawCircle(canvas);
        drawCenter(canvas);
        drawScale(canvas);
        drawArrows(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(22);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width/2, height/2, radius - 22, paint);
    }

    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2, height/2, 12, paint);
    }

    private void drawScale(Canvas canvas) {
        paint.setAntiAlias(true);
        for (int i = 1; i <= 12; i++) {
            double angle = Math.PI / 6 * (i - 3);
            canvas.drawLine((float) (width / 2 + Math.cos(angle) * radius * 0.83),
                    (float) (height / 2 + Math.sin(angle) * radius * 0.83),
                    (float) (width / 2 + Math.cos(angle) * (radius-15)),
                    (float) (height / 2 + Math.sin(angle) * (radius-15)),
                    paint);
        }
    }

    private void drawArrow(Canvas canvas, double loc, int indexCut, int color) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int arrowCut = 0;
        if (indexCut == 1) {arrowCut = 0;}
            else if (indexCut == 2) {arrowCut = 3;}
            else if (indexCut == 3) {arrowCut = 6;}
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawLine((float) (width / 2 - Math.cos(angle) * radius * (8-arrowCut) / 22),
                (float) (height / 2 - Math.sin(angle) * radius * (8-arrowCut) / 22),
                (float) (width / 2 + Math.cos(angle) * radius * (14-arrowCut) / 22),
                (float) (height / 2 + Math.sin(angle) * radius * (14-arrowCut) / 22),
                paint);
    }

    private void drawArrows(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawArrow(canvas, (hour + c.get(Calendar.MINUTE) / 60.0) * 5f, 1, Color.BLACK);
        drawArrow(canvas, c.get(Calendar.MINUTE), 2, Color.RED);
        drawArrow(canvas, c.get(Calendar.SECOND), 3, Color.BLUE);
    }

}
