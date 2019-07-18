package com.pjs.finaltest;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.sqlite.SQLiteConfig;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		DBCommon<Student> dbCommon = new DBCommon<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
		model.addAttribute("select_result", dbCommon.selectDataTableTag(new Student()));
		return "list2";
	}
	
	@RequestMapping(value = "/create_table", method = RequestMethod.GET)
	public String createTable(Locale locale, Model model) {
		DBCommon<Student> dbCommon = new DBCommon<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
		dbCommon.createTable(new Student());
		return "done2";
	}
	
	
	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String input(Locale locale, Model model) {
		return "input2";
	}
	
	@RequestMapping(value = "/input_data", method = RequestMethod.GET)
	public String inputData(
			@RequestParam("name") String name,
			@RequestParam("middleScore") String middleScore, 
			@RequestParam("finalScore") String finalScore) {
		Insert<Student> Insert = new Insert<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
		Insert.insertData(new Student(name, middleScore, finalScore));
		System.out.println("������ �Է� �Ϸ�");
		return "done2";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Locale locale, Model model, @RequestParam("idx") int idx) {
		DBCommon<Student> dbCommon = new DBCommon<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
		Student details = dbCommon.detailsData(new Student(), idx);
		if (details != null) {
			model.addAttribute("idx_value", details.idx);
			model.addAttribute("name_value", details.name);
			model.addAttribute("middleScore_value", details.middleScore);
			model.addAttribute("finalScore_value", details.finalScore);
		}
		System.out.println("���� ��) "+details.idx+" "+details.name+" "+details.middleScore+" "+details.finalScore);
		return "update2";
	}
	
	@RequestMapping(value = "/update_data", method = RequestMethod.GET)
	public String update_data(Locale locale, Model model,
			@RequestParam("idx") int idx,
			@RequestParam("name") String name,
			@RequestParam("middleScore") String middleScore, 
			@RequestParam("finalScore") String finalScore) {
		System.out.println("���� �Ϸ�) "+idx+" "+name+" "+middleScore+" "+finalScore);
		DBCommon<Student> dbCommon = new DBCommon<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
		dbCommon.updateData(new Student(name, middleScore, finalScore), idx);
		return "done2";
	}
	
	@RequestMapping(value = "/search_data", method = RequestMethod.GET)
	public String searchData(Locale locale, Model model, @RequestParam("name") String name) {
//		DBCommon<Student> dbCommon = new DBCommon<Student>("c:\\tomcat\\student190624.sqlite", "student190624");
//		model.addAttribute("select_result", dbCommon.searchDataTableTag(new Student(), "name", name));
//		System.out.println("�˻���) "+name);
		
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			connection = DriverManager.getConnection("jdbc:sqlite:/c:\\tomcat\\student190624.sqlite", config.toProperties());

			String query = "SELECT * FROM student190624 WHERE name LIKE '%" + name + "%'";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			StringBuilder htmlString = new StringBuilder();
			while (resultSet.next()) {
				htmlString.append("<tr>");
				String fieldName = resultSet.getString("name");
				int idx = resultSet.getInt("idx");
				int middleScore = resultSet.getInt("middleScore");
				int finalScore = resultSet.getInt("finalScore");
				htmlString.append("<td>" + idx + "</td>");
				htmlString.append("<td>" + fieldName + "</td>");
				htmlString.append("<td>" + middleScore + "</td>");
				htmlString.append("<td>" + finalScore + "</td>");
				htmlString.append("</tr>");
			}
			model.addAttribute("select_result", htmlString);
			preparedStatement.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connection = null;
		
		return "list2";
	}

}
