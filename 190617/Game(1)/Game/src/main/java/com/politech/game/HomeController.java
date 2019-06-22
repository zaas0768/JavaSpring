package com.politech.game;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		return "list";
	}
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Locale locale, Model model) {
		return "join";
	}
	@RequestMapping(value = "/my_account", method = RequestMethod.GET)
	public String myAccount(Locale locale, Model model) {
		return "my_card";
	}
	@RequestMapping(value = "/do_join", method = RequestMethod.GET)
	public String doJoin(Locale locale, Model model, @RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("name") String name) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : md.digest()) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			password = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		DBCommon<Player> dbCommon = new DBCommon<Player>("c:\\tomcat\\game.db", "player");
		dbCommon.insertData(new Player(id, password, name));
		model.addAttribute("message", "가입되었습니다.");
		return "done";
	}	
}
