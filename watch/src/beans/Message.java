package beans;

public class Message {
	private String template;
	private String message;

	public Message(String temp, String mes) {
		this.template = temp;
		this.message = mes;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage(String temp) {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
