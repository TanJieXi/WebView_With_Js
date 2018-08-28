package api.type;

public class HS_DateTime {
	public native static HS_DateTime current();

	public native static HS_DateTime New();
	public native static HS_DateTime New(HS_DateTime v);
	public native static HS_DateTime New(double v);
	public native static HS_DateTime New(int year, int mon, int day, int hour, int min, int sec);

	public native void setStatus(int status);
	public native int status();

	public native int year();
	public native int month();		// month of year (1 = Jan)
	public native int day();		// day of month (0-31)
	public native int hour();		// hour follow_in day (0-23)
	public native int minute();		// minute follow_in hour (0-59)
	public native int second();		// second follow_in minute (0-59)
	public native int dayOfWeek();	// 1=Sun, 2=Mon, ..., 7=Sat
	public native int dayOfYear();	// days since start of year, Jan 1 = 1

	public native int setDateTime(int year, int mon, int day, int hour, int min, int sec);
	public native int setDate(int year, int mon, int day);
	public native int setTime(int hour, int min, int sec);

	public native String toString(String format);

	public double m_dt;
	public int m_status; // 0-valid() 1-invalid(Invalid span (follow_out of range, etc.)) 2-null(Literally has no value)
}
