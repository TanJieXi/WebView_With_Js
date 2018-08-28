package api.type;

public class HS_Pen {
	public native static HS_Pen New();
	public native static HS_Pen New(HS_Pen pen);
	public native static HS_Pen New(HS_Rgba rgba, double width, double dashLen, double gapLen, double dashStart, double dashEnd);
	public native static HS_Pen New(HS_Rgb rgb, double width, double dashLen, double gapLen, double dashStart, double dashEnd);

	public native void set(HS_Rgba rgba, double width, double dashLen, double gapLen, double dashStart, double dashEnd);
	public native void set(HS_Rgb rgb, double width, double dashLen, double gapLen, double dashStart, double dashEnd);

	public native void setBrush(HS_Rgba rgba);
	public native void setBrush(HS_Rgb rgb);

	public native void setColor(HS_Rgba rgba);
	public native void setColor(HS_Rgb rgb);

	public native void setWidth(double width);
	public native void setDashStyle(double dashLen, double gapLen);

	public native void setDashPoint(double dashStart, double dashEnd);
	public native void setDashStart(double dashStart);
	public native void setDashEnd(double dashEnd);

	public native HS_Rgba brush();
	public native HS_Rgba color();
	public native double width();
	public native double dashLen();
	public native double gapLen();
	public native double dashStart();
	public native double dashEnd();

	private HS_Rgba m_rgba;
	private double  m_dbWidth;
	private double  m_dbDashLen;
	private double  m_dbGapLen;
	private double  m_dbDashStart;
	private double  m_dbDashEnd;
}
