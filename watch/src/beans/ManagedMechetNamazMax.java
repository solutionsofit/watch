package beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import connection.DbConnection;

@ManagedBean
@ViewScoped
public class ManagedMechetNamazMax {
	/**
	 * Массив для намазов на татарском с намазом Восход
	 */
	private ArrayList<Namaz> namazesTatSun;
	/**
	 * Массив для намазов на русском с намазом Восход
	 */
	private ArrayList<Namaz> namazesRuSun;
	/**
	 * Массив для намазов на арабском с намазом Восход
	 */
	private ArrayList<Namaz> namazesArSun;
	/**
	 * Массив для намазов на английском с намазом Восход
	 */
	private ArrayList<Namaz> namazesEnSun;

	/**
	 * Массив для намазов на татарском с намазом Фаджр
	 */
	private ArrayList<Namaz> namazesTatFajr;
	/**
	 * Массив для намазов на русском с намазом Фаджр
	 */
	private ArrayList<Namaz> namazesRuFajr;
	/**
	 * Массив для намазов на арабском с намазом Фаджр
	 */
	private ArrayList<Namaz> namazesArFajr;
	/**
	 * Массив для намазов на английском с намазом Фаджр
	 */
	private ArrayList<Namaz> namazesEnFajr;
	/**
	 * Массив только для времен намазов
	 */
	private ArrayList<String> namazTime;

	/**
	 * Сообщение до начала намаза на татарском
	 */
	private String messageBottomTat;
	/**
	 * Сообщение до начала намаза на татарском
	 */
	private String messageBottomRu;
	/**
	 * Сообщение до начала намаза на татарском
	 */
	private String messageBottomAr;
	/**
	 * Сообщение до начала намаза на татарском
	 */
	private String messageBottomEn;
	/**
	 * Дата в исламском исчеслении
	 */
	private String dateIslam;
	/**
	 * Месяц в исламском исчеслении
	 */
	private String monthIslam;
	/**
	 * Текущий день на татарском
	 */
	private String dayTat;
	/**
	 * Текущий день на русском
	 */
	private String dayRu;
	/**
	 * Текущий день на арабском
	 */
	private String dayAr;
	/**
	 * Текущий день на английском
	 */
	private String dayEn;
	/**
	 * Текущий месяц на татарском
	 */
	private String monthTat;
	/**
	 * Текущий месяц на русском
	 */
	private String monthRu;
	/**
	 * Текущий месяц на арабском
	 */
	private String monthAr;
	/**
	 * Текущий месяц на английском
	 */
	private String monthEn;
	/**
	 * Текущий месяц на татарском в исламском исчеслении
	 */
	private String monthTatIslam;
	/**
	 * Текущий месяц на русском в исламском исчеслении
	 */
	private String monthRuIslam;
	/**
	 * Текущий месяц на арабском в исламском исчеслении
	 */
	private String monthArIslam;
	/**
	 * Текущий месяц на английском в исламском исчеслении
	 */
	private String monthEnIslam;
	/**
	 * Сообщение выключите телефон на татарском
	 */
	private String messageTopTat;
	/**
	 * Сообщение выключите телефон на русском
	 */
	private String messageTopRu;
	/**
	 * Сообщение выключите телефон на арабском
	 */
	private String messageTopAr;
	/**
	 * Сообщение выключите телефон на английском
	 */
	private String messageTopEn;

	/**
	 * Переменная, которая хранит цифровое значение текущего дня. Запоминается при
	 * инициализации управляющего бина и потом сравнивает раз в 20 секунд текущую
	 * дату с этой переменной, если цифры отличаются, то вызывает метод init() для
	 * обновления данных из БД.
	 */
	private int currentDay;

	@PostConstruct
	public void init() {

		ArrayList<String> data_ar = new ArrayList<>();

		SimpleDateFormat formatterdate = new SimpleDateFormat("dd.MM.yyyy");

		Date today = new Date(System.currentTimeMillis());

		namazesTatFajr = getNamazes((getNamazes(today, "tat")), "clock_namaz_sunrise");
		namazesRuFajr = getNamazes((getNamazes(today, "ru")), "clock_namaz_sunrise");
		namazesArFajr = getNamazes((getNamazes(today, "ar")), "clock_namaz_sunrise");
		namazesEnFajr = getNamazes((getNamazes(today, "en")), "clock_namaz_sunrise");

		namazesTatSun = getNamazes((getNamazes(today, "tat")), "clock_namaz_fajr");
		namazesRuSun = getNamazes((getNamazes(today, "ru")), "clock_namaz_fajr");
		namazesArSun = getNamazes((getNamazes(today, "ar")), "clock_namaz_fajr");
		namazesEnSun = getNamazes((getNamazes(today, "en")), "clock_namaz_fajr");

		ArrayList<Message> data = new ArrayList<>();
		data = getMessagesByLanguage("tat");
		for (Message c : data) {
			if (c.getTemplate().equals("clock_msg_before")) {
				messageBottomTat = c.getMessage();
			} else if (c.getTemplate().equals("clock_msg_phone")) {
				messageTopTat = c.getMessage();
			}
		}
		data.clear();
		data = getMessagesByLanguage("ru");
		for (Message c : data) {
			if (c.getTemplate().equals("clock_msg_before")) {
				messageBottomRu = c.getMessage();
			} else if (c.getTemplate().equals("clock_msg_phone")) {
				messageTopRu = c.getMessage();
			}
		}
		data.clear();
		data = getMessagesByLanguage("ar");
		for (Message c : data) {
			if (c.getTemplate().equals("clock_msg_before")) {
				messageBottomAr = c.getMessage();
			} else if (c.getTemplate().equals("clock_msg_phone")) {
				messageTopAr = c.getMessage();
			}
		}

		data.clear();
		data = getMessagesByLanguage("en");
		for (Message c : data) {
			if (c.getTemplate().equals("clock_msg_before")) {
				messageBottomEn = c.getMessage();
			} else if (c.getTemplate().equals("clock_msg_phone")) {
				messageTopEn = c.getMessage();
			}
		}

		dateIslam = formatterdate.format(getIslamDateByToday(today));
		data_ar = dataOutput(today, "ar");
		monthTatIslam = getMonth("tat").get(Integer.parseInt(data_ar.get(5)) + 11);// +11 потому что в базе данных
																					// мусульманские месяца идут с 11
																					// индекса
		monthRuIslam = getMonth("ru").get(Integer.parseInt(data_ar.get(5)) + 11);// +11 потому что в базе данных
																					// мусульманские месяца идут с 11
																					// индекса
		monthArIslam = getMonth("ar").get(Integer.parseInt(data_ar.get(5)) + 11);// +11 потому что в базе данных
																					// мусульманские месяца идут с 11
																					// индекса
		monthEnIslam = getMonth("en").get(Integer.parseInt(data_ar.get(5)) + 11);// +11 потому что в базе данных
																					// мусульманские месяца идут с 11
																					// индекса

		monthAr = getMonth("ar").get(today.getMonth());
		monthTat = getMonth("tat").get(today.getMonth());
		monthRu = getMonth("ru").get(today.getMonth());
		monthEn = getMonth("en").get(today.getMonth());

		dayTat = getDay("tat");
		dayRu = getDay("ru");
		dayAr = getDay("ar");
		dayEn = getDay("en");

		namazTime = Time("tat");

	}

	public void refreshDataFromDb() {

		if (currentDay != getCurrentDay()) {
			try {
				System.out.println("reload()");
				reloadPage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			init();
		}

	}

	public void reloadPage() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	/**
	 * Получение текущего дня в числовом виде.
	 * 
	 * @return 1 - Понедельник, 2 - Вторник, ..., 7 - Воскресенье
	 */
	private int getCurrentDay() {

		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		switch (day) {
		case 1:
			day = 7;
			break;
		case 2:
			day = 1;
			break;
		case 3:
			day = 2;
			break;
		case 4:
			day = 3;
			break;
		case 5:
			day = 4;
			break;
		case 6:
			day = 5;
			break;
		case 7:
			day = 6;
			break;

		default:
			break;
		}

		return day;
	}

	/**
	 * Возвращает только массив времен намаза, без названия
	 * 
	 * @param lang
	 * @return
	 */
	public ArrayList<String> Time(String lang) {

		ArrayList<Namaz> namaz = new ArrayList<Namaz>();
		ArrayList<String> time = new ArrayList<String>();
		Date today = new Date(System.currentTimeMillis());
		namaz = getNamazes(today, lang);
		for (Namaz n : namaz) {
			time.add(n.getDate());
		}

		return time;

	}

	/**
	 * Пятничный намаз меняется автоматически в функии бд, выводит 7 позиций, а нам
	 * на сайт нужно только 6. Выводит 6 намазов, так как 2й намаз на странице
	 * попеременно выводится либо фаджр, либо восход, соответственно 3я переменная
	 * передаёт template, который нужно забрать из бд
	 * 
	 * @param today
	 * @param lang
	 * @param viewnamaz
	 * @return
	 */
	private ArrayList<Namaz> getNamazes(ArrayList<Namaz> namaz, String viewnamaz) {

		ArrayList<Namaz> massiv = new ArrayList<>(namaz);
		Namaz delete = null;
		for (Namaz n : massiv) {
			if (n.getTemplate().equals(viewnamaz)) {
				delete = n;
			}
		}
		if (delete != null) {
			massiv.remove(delete);
		}
		return massiv;
	}

	/**
	 * Функция вывода намаза из бд,возвращает как названия намазов так и их время
	 */

	public static ArrayList<Namaz> getNamazes(Date today, String lang) {
		Connection conn = null;
		ResultSet res = null;
		PreparedStatement stmt = null;
		ArrayList<Namaz> massiv = new ArrayList<Namaz>();
		try {
			conn = DbConnection.Connection_to_my_db_Max();
			String query2 = "select * from get_namaz('" + today + "','" + lang + "','Болгар')";

			stmt = conn.prepareStatement(query2);

			res = stmt.executeQuery();
			while (res.next()) {
				Namaz namaz = new Namaz();
				namaz.setDate(res.getTime("r_time"));
				namaz.setName(res.getString("r_message"));
				namaz.setTemplate(res.getString("r_template"));
				massiv.add(namaz);
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				res.close();
			} catch (SQLException e) {
			}
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}

		}

		return massiv;
	}

	/**
	 * Возвращает сообщения "До начала намаза" и "Выключите телефон"
	 * 
	 * @param today
	 * @param lang
	 * @return
	 */
	private ArrayList<Message> getMessagesByLanguage(String lang) {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		ArrayList<Message> messages = new ArrayList<>();

		try {
			connection = DbConnection.Connection_to_my_db_Max();
			String query2 = "select\n" + "mt.template,m.message \n" + "from messages m \n"
					+ "join msg_templates mt on mt.id = m.id_msg_templates \n"
					+ "join languages l on l.id = m.id_languages\n" + "where \n" + "mt.template like 'clock_msg_%'\n"
					+ "and mt.template <> 'clock_msg_suhoor'\n" + "and l.code =?;";
			ps = connection.prepareStatement(query2);
			ps.setString(1, lang);
			rs = ps.executeQuery();
			while (rs.next()) {
				messages.add(new Message(rs.getString("template"), rs.getString("message")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return messages;
	}

	/**
	 * Эта функция нужна лишь для того чтобы достать исламские месяца
	 * 
	 * @param today
	 * @param lang
	 * @return
	 */
	public static ArrayList<String> dataOutput(Date today, String lang) {

		Connection conn = null;
		ResultSet res = null;
		PreparedStatement stmt = null;

		ArrayList<String> data = new ArrayList<>();
		try {
			conn = DbConnection.Connection_to_my_db_Max();
			String query2 = "select\n" + "m.message \n" + "from messages m \n"
					+ "join msg_templates mt on mt.id = m.id_msg_templates \n"
					+ "join languages l on l.id = m.id_languages\n" + "where mt.template like 'clock_msg_%' \n"
					+ "and l.code = '" + lang + "'";

			stmt = conn.prepareStatement(query2);

			res = stmt.executeQuery();

			while (res.next()) {
				data.add(res.getString("message"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				res.close();
			} catch (SQLException e) {
			}
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}
		try {
			conn = DbConnection.Connection_to_my_db_Max();
			String query2 = "select * from get_islam_date('" + today + "');";

			stmt = conn.prepareStatement(query2);

			res = stmt.executeQuery();

			while (res.next()) {
				data.add(res.getString("r_value"));
				data.add(res.getString("r_week_day"));
				data.add(res.getString("r_month"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				res.close();
			} catch (SQLException e) {
			}
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		return data;

	}

	/**
	 * Функция возвращающая массив с месяцами(исламскими и обычными(обычные
	 * начинаются с 0 и кончаются 11,исламские с 11 до 23))\ Нужно доставать
	 * исламские месяцы при помощи скрипта который написал Дядя Женя,но он у меня не
	 * работает, так что пока так:(((
	 * 
	 */
	public static ArrayList<String> getMonth(String lang) {

		Connection conn = null;
		ResultSet res = null;
		PreparedStatement stmt = null;

		ArrayList<String> month = new ArrayList<String>();

		try {
			conn = DbConnection.Connection_to_my_db_Max();
			String query2 = "select \n" + "l.code\n" + ",mt.template\n" + ",m.message\n" + "from messages m\n"
					+ "join msg_templates mt on mt.id = m.id_msg_templates\n"
					+ "join languages l on l.id = m.id_languages\n" + "where 1=1\n"
					+ "and mt.template like 'clock_month%'\n" + "and l.code = '" + lang + "'\n" + "order by m.id";

			stmt = conn.prepareStatement(query2);

			res = stmt.executeQuery();

			while (res.next()) {
				month.add(res.getString("message"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				res.close();
			} catch (SQLException e) {
			}
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {

			}

		}

		return month;
	}

	/**
	 * <p>
	 * Возвращает текущий день на определенном языке
	 * </p>
	 * 
	 * @param lang
	 * @return
	 * 
	 */
	private String getDay(String lang) {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		ArrayList<String> days = new ArrayList<String>();

		try {
			connection = DbConnection.Connection_to_my_db_Max();
			String query2 = "select \n" + "l.code\n" + ",mt.template\n" + ",m.message\n" + "from messages m\n"
					+ "join msg_templates mt on mt.id = m.id_msg_templates\n"
					+ "join languages l on l.id = m.id_languages\n" + "where 1=1\n"
					+ "and mt.template like 'clock_week%'\n" + "and l.code = '" + lang + "'\n" + "order by m.id";

			ps = connection.prepareStatement(query2);

			rs = ps.executeQuery();

			while (rs.next()) {
				days.add(rs.getString("message"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return days.get(getCurrentDay() - 1);
	}

	/**
	 * Возвращает текущую дату по исламскому календарю, берет её из бд
	 * 
	 * @param today
	 * @return
	 */
	private Date getIslamDateByToday(Date today) {

		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		Date islam = today;
		try {
			connection= DbConnection.Connection_to_my_db_Max();
			String q = "select * from get_islam_date(?);";
			ps = connection.prepareStatement(q);
			ps.setDate(1, today);
			rs = ps.executeQuery();
			while (rs.next()) {
				islam = rs.getDate("r_value");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return islam;
	}
	// Getters and Setters

	public ArrayList<Namaz> getNamazesTatSun() {
		return namazesTatSun;
	}

	public void setNamazesTatSun(ArrayList<Namaz> namazesTatSun) {
		this.namazesTatSun = namazesTatSun;
	}

	public ArrayList<Namaz> getNamazesRuSun() {
		return namazesRuSun;
	}

	public void setNamazesRuSun(ArrayList<Namaz> namazesRuSun) {
		this.namazesRuSun = namazesRuSun;
	}

	public ArrayList<Namaz> getNamazesArSun() {
		return namazesArSun;
	}

	public void setNamazesArSun(ArrayList<Namaz> namazesArSun) {
		this.namazesArSun = namazesArSun;
	}

	public ArrayList<Namaz> getNamazesEnSun() {
		return namazesEnSun;
	}

	public void setNamazesEnSun(ArrayList<Namaz> namazesEnSun) {
		this.namazesEnSun = namazesEnSun;
	}

	public ArrayList<Namaz> getNamazesTatFajr() {
		return namazesTatFajr;
	}

	public void setNamazesTatFajr(ArrayList<Namaz> namazesTatFajr) {
		this.namazesTatFajr = namazesTatFajr;
	}

	public ArrayList<Namaz> getNamazesRuFajr() {
		return namazesRuFajr;
	}

	public void setNamazesRuFajr(ArrayList<Namaz> namazesRuFajr) {
		this.namazesRuFajr = namazesRuFajr;
	}

	public ArrayList<Namaz> getNamazesArFajr() {
		return namazesArFajr;
	}

	public void setNamazesArFajr(ArrayList<Namaz> namazesArFajr) {
		this.namazesArFajr = namazesArFajr;
	}

	public ArrayList<Namaz> getNamazesEnFajr() {
		return namazesEnFajr;
	}

	public void setNamazesEnFajr(ArrayList<Namaz> namazesEnFajr) {
		this.namazesEnFajr = namazesEnFajr;
	}

	public ArrayList<String> getNamazTime() {
		return namazTime;
	}

	public void setNamazTime(ArrayList<String> namazTime) {
		this.namazTime = namazTime;
	}

	public String getMessageBottomTat() {
		return messageBottomTat;
	}

	public void setMessageBottomTat(String messageBottomTat) {
		this.messageBottomTat = messageBottomTat;
	}

	public String getMessageBottomRu() {
		return messageBottomRu;
	}

	public void setMessageBottomRu(String messageBottomRu) {
		this.messageBottomRu = messageBottomRu;
	}

	public String getMessageBottomAr() {
		return messageBottomAr;
	}

	public void setMessageBottomAr(String messageBottomAr) {
		this.messageBottomAr = messageBottomAr;
	}

	public String getMessageBottomEn() {
		return messageBottomEn;
	}

	public void setMessageBottomEn(String messageBottomEn) {
		this.messageBottomEn = messageBottomEn;
	}

	public String getDateIslam() {
		return dateIslam;
	}

	public void setDateIslam(String dateIslam) {
		this.dateIslam = dateIslam;
	}

	public String getMonthIslam() {
		return monthIslam;
	}

	public void setMonthIslam(String monthIslam) {
		this.monthIslam = monthIslam;
	}

	public String getDayTat() {
		return dayTat;
	}

	public void setDayTat(String dayTat) {
		this.dayTat = dayTat;
	}

	public String getDayRu() {
		return dayRu;
	}

	public void setDayRu(String dayRu) {
		this.dayRu = dayRu;
	}

	public String getDayAr() {
		return dayAr;
	}

	public void setDayAr(String dayAr) {
		this.dayAr = dayAr;
	}

	public String getDayEn() {
		return dayEn;
	}

	public void setDayEn(String dayEn) {
		this.dayEn = dayEn;
	}

	public String getMonthTat() {
		return monthTat;
	}

	public void setMonthTat(String monthTat) {
		this.monthTat = monthTat;
	}

	public String getMonthRu() {
		return monthRu;
	}

	public void setMonthRu(String monthRu) {
		this.monthRu = monthRu;
	}

	public String getMonthAr() {
		return monthAr;
	}

	public void setMonthAr(String monthAr) {
		this.monthAr = monthAr;
	}

	public String getMonthEn() {
		return monthEn;
	}

	public void setMonthEn(String monthEn) {
		this.monthEn = monthEn;
	}

	public String getMonthTatIslam() {
		return monthTatIslam;
	}

	public void setMonthTatIslam(String monthTatIslam) {
		this.monthTatIslam = monthTatIslam;
	}

	public String getMonthRuIslam() {
		return monthRuIslam;
	}

	public void setMonthRuIslam(String monthRuIslam) {
		this.monthRuIslam = monthRuIslam;
	}

	public String getMonthArIslam() {
		return monthArIslam;
	}

	public void setMonthArIslam(String monthArIslam) {
		this.monthArIslam = monthArIslam;
	}

	public String getMonthEnIslam() {
		return monthEnIslam;
	}

	public void setMonthEnIslam(String monthEnIslam) {
		this.monthEnIslam = monthEnIslam;
	}

	public String getMessageTopTat() {
		return messageTopTat;
	}

	public void setMessageTopTat(String messageTopTat) {
		this.messageTopTat = messageTopTat;
	}

	public String getMessageTopRu() {
		return messageTopRu;
	}

	public void setMessageTopRu(String messageTopRu) {
		this.messageTopRu = messageTopRu;
	}

	public String getMessageTopAr() {
		return messageTopAr;
	}

	public void setMessageTopAr(String messageTopAr) {
		this.messageTopAr = messageTopAr;
	}

	public String getMessageTopEn() {
		return messageTopEn;
	}

	public void setMessageTopEn(String messageTopEn) {
		this.messageTopEn = messageTopEn;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

}