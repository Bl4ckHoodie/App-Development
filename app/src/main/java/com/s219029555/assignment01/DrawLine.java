package com.s219029555.assignment01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class DrawLine extends View {
    Paint paint = new Paint();
    Point point1 = new Point(0,0);
    Point point = new Point(0,0);
    public DrawLine(Context context) {
        super(context);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
    }
    public DrawLine(Context context, Point pointS, Point pointE, int color) {
        super(context);
        this.point = pointS;
        this.point1 = pointE;
        paint.setStrokeWidth(10);
        paint.setColor(color);
    }
    public DrawLine(Context context, Point point,int color) {
        super(context);
        this.point1 = point;
        paint.setStrokeWidth(10);
        paint.setColor(color);
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(point.x, point.y, point1.x, point1.y, paint);
    }
}
