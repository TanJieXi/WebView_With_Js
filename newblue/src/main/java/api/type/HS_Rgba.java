package api.type;

public class HS_Rgba {
	public native static HS_Rgba New();
	public native static HS_Rgba New(HS_Rgba rgba);
	public native static HS_Rgba New(HS_Rgb rgb);
	public native static HS_Rgba New(int r, int g, int b, int a);
	public native static HS_Rgba New(int color);

	public native void set(int r, int g, int b, int a);
	public native void setR(int r);
	public native void setG(int g);
	public native void setB(int b);
	public native void setA(int a);

	public native HS_Rgb getRGB();
	public native int getColor();

	public native int r();
	public native int g();
	public native int b();
	public native int a();

	private int R;	//<! 8bit
	private int G;	//<! 8bit
	private int B;	//<! 8bit
	private int A;	//<! 8bit
}
