package custom;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class MyViewModel {

	private int count;
	private int cc;

	@Init
	public void init() {
		count = 100;
		cc = 1;
	}

	@Command
	@NotifyChange("count")
	public void cmd() {
		count = count + cc;
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

	@Command
	@NotifyChange("cc")
	public void cmd1(int newval)
	{
		setCc(newval);
	}

}
