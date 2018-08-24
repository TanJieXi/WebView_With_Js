package com.example.newblue.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BabView extends View {

    private String TAG = BabView.class.getName();

    @SuppressWarnings("unused")
    private Context context = null;

    private float Max = 210;//最大值
    private float Min = 60;//最小值
    private float nowVule = 0;//平均值

    private float paddingView = 25;//padding
    private float xInterval = 100;//X间距
    private float yInterval = 0;//Y间距
    private int xLine = 15;//横线
    private int yLine = 30;//竖线min=10*3
    private Paint paint, textPaint, greenPaint, paintLine;
    private int dataInterval = 0;//数据间距

    public List<Float> dataList = new ArrayList<Float>();//数据


    public BabView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

//设置宽高
        setMeasuredDimension(width, height);
    }

    private void init(Context context) {
        this.context = context;

        paddingView = toPixel(context, paddingView);
        xInterval = toPixel(context, xInterval);
        yInterval = (toPixel(context, 300) - (paddingView * 2)) / xLine;
        dataInterval = toPixel(context, 100 / 20);

        nowVule = (Max - Min) / xLine;

        //框架
        paint = new Paint();
        paint.setColor(Color.BLACK);
        //绿色区域
        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);

        //文字
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(toPixel(context, 18));
        textPaint.setTextAlign(Align.CENTER);
        //线
        paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStrokeWidth(2);
    }

    private int toPixel(Context context, float dip) {
        Resources res = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip,
                res.getDisplayMetrics());
        return (int) px;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int height = getHeight() - paddingTop - paddingBottom ;
        int width = getWidth() - paddingLeft - paddingRight;

        canvas.save();
        canvas.translate(paddingLeft, paddingTop);
        canvas.clipRect(0, 0, width, height);

        //绿色
        canvas.drawRect(paddingView, paddingView + ((210 - 160) / nowVule * yInterval), paddingView + xInterval * yLine, paddingView + ((210 - 120) / nowVule * yInterval), greenPaint);


        //横线
        for (int i = 0; i <= xLine; i++) {
            canvas.drawLine(paddingView, paddingView + i * yInterval, paddingView + xInterval * yLine, paddingView + i * yInterval, paint);
        }

        //竖线
        for (int i = 0; i <= yLine; i++) {
            canvas.drawLine(paddingView + i * xInterval, paddingView, paddingView + i * xInterval, paddingView + xLine * yInterval, paint);
            if (i % 3 == 0) {
                canvas.drawText(i / 3 + "min", paddingView+ i * xInterval-6, textPaint.getTextSize() + paddingView + xLine * yInterval, textPaint);
            }

            if (i % 6 == 0) {

                for (int j = xLine; j >= 0; j--) {
                    if (j % 3 == 0) {
                        canvas.drawText((int) (j * nowVule + Min) + "", paddingView + i * xInterval, textPaint.getTextSize() / 2 + paddingView + (xLine * yInterval - j * yInterval) - 8, textPaint);
                    }


                }
            }
        }
        canvas.restore();

        drawLine(canvas, width, height);

    }

    private void drawLine(Canvas canvas, int width, int height) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.clipRect(0, 0, width, height);
        float px = 0;
        float py = 0;


        if (dataList != null && dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                float v = dataList.get(i);


                if (Max < v) {
                    v = Max;
                }
                if (Min > v) {
                    v = Min;
                }

                float x = paddingView + (i * dataInterval);
                float y = paddingView + ((210 - v) / nowVule * yInterval);
                float minY = paddingView + ((210 - Min) / nowVule * yInterval);
                ;
                if (v > Min && py > 0 && py < minY && y > 0 && y < minY) {
                    canvas.drawLine(px, py, x, y, paintLine);
                }
                px = x;
                py = y;
            }
        }

        canvas.restore();
    }

    public void setData(float data) {
        dataList.add(data);
        invalidate();
    }
}
