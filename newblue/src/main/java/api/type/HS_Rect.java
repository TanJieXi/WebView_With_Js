package api.type;

public class HS_Rect {
	public native static HS_Rect New();
	public native static HS_Rect New(HS_Rect rect);
	public native static HS_Rect New(HS_Point pt1, HS_Point pt2);
	public native static HS_Rect New(HS_Point pt, HS_Size size);
	public native static HS_Rect New(int x, int y, int w, int h);

	public native void set(int x, int y, int w, int h);
	public native void setLeft(int x);
	public native void setTop(int h);
	public native void setRight(int w);
	public native void setBottom(int h);
	public native void setWidth(int w);
	public native void setHeight(int h);

	public native int left();
	public native int top();
	public native int right();
	public native int bottom();
	public native int width();
	public native int height();

	public native int halfW();
    public native int halfH();

    public native HS_Point topLeft();
    public native HS_Point topRight();
    public native HS_Point bottomLeft();
    public native HS_Point bottomRight();
	public native HS_Point center();
	public native HS_Size size();
	public native int centerX();
    public native int centerY();

	public native void setEmpty();
	public native boolean isEmpty();

	public native void normalize();

	public native void offset(int x, int y);
	public native void offset(HS_Point pt);

	public native void deflate(int x, int y);
	public native void deflate(HS_Point pt);
	public native void deflate(int l, int t, int r, int b);
	public native void deflate(HS_Rect rect);

	public native void inflate(int x, int y);
	public native void inflate(HS_Point pt);
	public native void inflate(int l, int t, int r, int b);
	public native void inflate(HS_Rect rect);

	public native boolean contains(int x, int y);
	public native boolean contains(HS_Point pt);
	public native boolean contains(HS_Rect rect);

	public native boolean isIntersectRect(HS_Rect rect);
	public native boolean intersectRect(HS_Rect rect);
	public native static boolean intersectRect(HS_Rect dest, HS_Rect src1, HS_Rect src2);

	public native boolean unionRect(HS_Rect rect);
	public native static boolean unionRect(HS_Rect dest, HS_Rect src1, HS_Rect src2);

	private int X;
	private int Y;
	private int W;
	private int H;
}
