package api.type;

public class HS_Size {
	public native static HS_Size New();
	public native static HS_Size New(HS_Size size);
	public native static HS_Size New(int w, int h);

	public native void set(int w, int h);
	public native void setW(int w);
	public native void setH(int h);

	public native int w();
	public native int h();

	public native int halfW();
    public native int halfH();
    
	public native void setEmpty();
	public native boolean isEmpty();

	public native void toScale(double scale);
	public native HS_Size scale(double scale);

	private int W;	//<! 8bit
	private int H;	//<! 8bit
}
