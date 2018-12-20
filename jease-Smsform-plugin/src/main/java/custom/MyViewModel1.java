package custom;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import custom.SMS;


public class MyViewModel1 {

	private int count;
	private int cc;

	private String ani;
	private String text;


	@Init
	public void init() {
		count = 100;
		cc = 1;
		ani = "";
		text = "test тест";
	}

	@Command
	@NotifyChange("count")
	public void cmd() {
		count = count + cc;
		SMS.sendSMS(ani, text);
	}

	public int getCount() {
		return count;
	}

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Command
	@NotifyChange("cc")
	public void cmd1(int newval)
	{
		setCc(newval);
	}

}
