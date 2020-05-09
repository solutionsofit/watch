package beans;


import java.sql.Time;
import java.text.SimpleDateFormat;

public class Namaz {
	SimpleDateFormat ad = new SimpleDateFormat("HH:mm");
	private String date;
	private String name;
	private String template;
	

	public String getDate() {
		return date;
	}

	public void setDate(Time date) {
		this.date = ad.format(date);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Namaz getNamazFromTemplate(String template) {
		return null;
	}
}
