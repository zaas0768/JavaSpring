package com.politech.signin;

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
		DBCommon<Peoson> dbCommon = new DBCommon<Peoson>("c:\\tomcat\\student190527.sqlite", "student");
		model.addAttribute("select_result", dbCommon.selectDataTableTag(new Peoson()));
		return "list";
	}
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Locale locale, Model model, @RequestParam("idx") int idx) {
		DBCommon<Peoson> dbCommon = new DBCommon<Peoson>("c:\\tomcat\\student190527.sqlite", "student");
		model.addAllAttributes(dbCommon.detailsData(new Peoson(), idx));
		return "modify";
	}
	@RequestMapping(value = "/update_data", method = RequestMethod.GET)
	public String updateData(@RequestParam("idx") int idx, @RequestParam("name") String name, @RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("birthday") String birthday, @RequestParam("favoriteColor") String favoriteColor) {
		DBCommon<Peoson> dbCommon = new DBCommon<Peoson>("c:\\tomcat\\student190527.sqlite", "student");
		dbCommon.updateData(new Peoson(name, favoriteColor, address, birthday, id, password), idx);
		return "done";
	}
}
