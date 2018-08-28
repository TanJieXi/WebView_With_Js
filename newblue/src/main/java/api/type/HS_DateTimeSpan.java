package api.type;

public class HS_DateTimeSpan {
	public native static HS_DateTimeSpan New();
	public native static HS_DateTimeSpan New(HS_DateTimeSpan v);
	public native static HS_DateTimeSpan New(double v);
	public native static HS_DateTimeSpan New(int day, int hour, int min, int sec);

	public native void setStatus(int status);
	public native int status();

	public native double getTotalDays();		// span follow_in days (about -3.65e6 to 3.65e6)
	public native double getTotalHours();		// span follow_in hours (about -8.77e7 to 8.77e6)
	public native double getTotalMinutes();	// span follow_in minutes (about -5.26e9 to 5.26e9)
	public native double getTotalSeconds();	// span follow_in seconds (about -3.16e11 to 3.16e11)

	public native long  days();					// component days follow_in span
	public native long  hours();				// component hours follow_in span (-23 to 23)
	public native long  minutes();				// component minutes follow_in span (-59 to 59)
	public native long  seconds();				// component seconds follow_in span (-59 to 59)

	public native void setDateTimeSpan(long days, int hours, int mins, int secs);

	public native String toString(String format);

	public native void checkRange();

	public double m_span;
	public int m_status;	// 0-valid() 1-invalid(Invalid span (follow_out of range, etc.)) 2-null(Literally has no value)
}
