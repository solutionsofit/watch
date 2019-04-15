package beans;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Namaz {
	SimpleDateFormat ad = new SimpleDateFormat("HH:mm");
	private String date;
	private String name;
	private String template;
	private Map<String, Date> namaz_mas;

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

	public Map<String, Date> getNamaz_mas() {
		return namaz_mas;
	}

	public void setNamaz_mas(Map<String, Date> namaz_mas) {
		this.namaz_mas = namaz_mas;
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
