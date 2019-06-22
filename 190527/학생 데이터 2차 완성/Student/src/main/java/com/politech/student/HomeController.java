package com.politech.student;

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
		SelectData selectData = new SelectData();
		selectData.selectStudent();
		selectData.getSeoul();
		model.addAttribute("select_result", selectData.getHtmlString());
		model.addAttribute("seoul_count", selectData.seoulCount);
		return "list";
	}
	@RequestMapping(value = "/input_student", method = RequestMethod.GET)
	public String inputStudent(Locale locale, Model model) {
		return "input_student";
	}	
	@RequestMapping(value = "/input_data", method = RequestMethod.GET)
	public String inputData(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("birthday") String birthday) {
		Student student = new Student(0, name, address, birthday);
		InsertData insertData = new InsertData();
		insertData.insertStudent(student);
		return "done";
	}
	@RequestMapping(value = "/create_db", method = RequestMethod.GET)
	public String createDB(Locale locale, Model model) {
		CreateDatabase createDatabase = new CreateDatabase();
		createDatabase.createTable();
		return "done";
	}
}
