package api.type;

public class HS_Point {
	public native static HS_Point New();
	public native static HS_Point New(HS_Point pt);
	public native static HS_Point New(HS_PointF pt);
	public native static HS_Point New(int x, int y);

	public native void set(int x, int y);
	public native void setX(int x);
	public native void setY(int y);
	
	public native void offset(int x, int y);
	public native void offset(HS_Point pt);
	public native void offset(HS_PointF pt);

	public native HS_PointF getPointF();

	public native float x();
	public native float y();

	private int X;
	private int Y;
}
