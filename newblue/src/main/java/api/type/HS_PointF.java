package api.type;

public class HS_PointF {
	public native static HS_PointF New();
	public native static HS_PointF New(HS_PointF pt);
	public native static HS_PointF New(HS_Point pt);
	public native static HS_PointF New(float x, float y);

	public native void set(float x, float y);
	public native void setX(float x);
	public native void setY(float y);
	
	public native void offset(float x, float y);
	public native void offset(HS_PointF pt);
	public native void offset(HS_Point pt);

	public native HS_Point getPoint();

	public native float x();
	public native float y();

	private float X;
	private float Y;
}
