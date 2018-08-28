package api.type;

public class HS_Font {
	public native static HS_Font New();
	public native static HS_Font New(HS_Font font);
	public native static HS_Font New(HS_Rgba rgba, String font_path, double font_size, boolean point_unit, double dpi);
	public native static HS_Font New(HS_Rgb rgb, String font_path, double font_size, boolean point_unit, double dpi);

	public native void set(HS_Rgba rgba, String font_path, double font_size, boolean point_unit, double dpi);
	public native void set(HS_Rgb rgb, String font_path, double font_size, boolean point_unit, double dpi);

	public native void setColor(HS_Rgba rgba);
	public native void setColor(HS_Rgb rgb);

	public native void setFontPath(String font_path);
	public native void setFontSize(double font_size);
	public native void setFontWidth(double width);
	public native void setFontHeight(double height);
	public native void setPointUnit(boolean point_unit);
	public native void setDpi(double dpi);
	public native void setSmooth(boolean smooth);
	public native void setKerning(boolean kerning);
	public native void setBold(int bold);
	public native void setUnderline(boolean underline);
	public native void setItalic(double italic); // italic=[-1.0, 1.0]
	public native void setStrike(boolean strike);

	public native HS_Rgba color();
	public native String path();
	public native double  size();
	public native double  width();
	public native double  height();
	public native boolean pointUnit();
	public native double  dpi();
	public native boolean smooth();
	public native boolean kerning();
	public native int     bold();
	public native boolean underline();
	public native double  italic();
    public native boolean strike();

	private HS_Rgba m_rgba;
	private String m_strFontPath;
	private double  m_dbFontSize;
	private double  m_dbWidth;
	private double  m_dbHeight;
	private boolean m_bPointUnit;
	private double  m_dbDpi;
	private boolean m_bSmooth;
	private boolean m_bKerning;
	private int     m_nWeight;
	private boolean m_bUnderline;
	private double  m_dbItalic;
	private boolean m_bStrike;
}
