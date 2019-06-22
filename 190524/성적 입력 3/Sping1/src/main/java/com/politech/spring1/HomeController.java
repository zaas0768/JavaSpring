package com.politech.spring1;

import java.sql.SQLException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "input";
	}
	@RequestMapping(value = "/create_table", method = RequestMethod.GET)
	public String createTable(Locale locale, Model model) {
		DataReader dataReader = new DataReader("c:\\tomcat\\students3.sqlite", "students");
		dataReader.open();
		try {
			dataReader.createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataReader.close();
		return "done";
	}
	@RequestMapping(value = "/input_data", method = RequestMethod.GET)
	public String inputData(@RequestParam("name") String name, 
			@RequestParam("middle_score") String middleScore, 
			@RequestParam("final_score") String finalScore) {
		DataReader dataReader = new DataReader("c:\\tomcat\\students3.sqlite", "students");
		dataReader.open();
		try {
			dataReader.insertData(name, Integer.parseInt(middleScore), Integer.parseInt(finalScore));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataReader.close();
		return "done";
	}
}
