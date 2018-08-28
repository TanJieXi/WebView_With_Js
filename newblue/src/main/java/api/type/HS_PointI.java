package api.type;

public class HS_PointI {
	public native static HS_PointI New();
	public native static HS_PointI New(HS_PointI pt);
	public native static HS_PointI New(HS_PointF pt);
	public native static HS_PointI New(int x, int y);

	public native void set(int x, int y);
	public native void setX(int x);
	public native void setY(int y);
	
	public native void offset(int x, int y);
	public native void offset(HS_PointI pt);
	public native void offset(HS_PointF pt);

	public native HS_PointF getPointF();

	public native float x();
	public native float y();

	private int X;
	private int Y;
}
