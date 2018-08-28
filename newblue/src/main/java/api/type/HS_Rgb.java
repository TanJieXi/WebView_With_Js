package api.type;

public class HS_Rgb {
	public native static HS_Rgb New();
	public native static HS_Rgb New(HS_Rgb rgb);
	public native static HS_Rgb New(HS_Rgba rgba);
	public native static HS_Rgb New(int r, int g, int b);
	public native static HS_Rgb New(int color);

	public native void set(int r, int g, int b);
	public native void setR(int r);
	public native void setG(int g);
	public native void setB(int b);

	public native HS_Rgba getRGBA();
	public native int getColor();

	public native int r();
	public native int g();
	public native int b();

	private int R;	//<! 8bit
	private int G;	//<! 8bit
	private int B;	//<! 8bit
}
