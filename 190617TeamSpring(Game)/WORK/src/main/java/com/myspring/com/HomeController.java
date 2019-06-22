package com.myspring.com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myspring.account.Account;
import com.myspring.character.Battle;
import com.myspring.character.Character;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final int MAX_STATUS = 20;
	private static final int[][] STANDARD_STATUS = {
		// Warrior
		{ 120, 8, 10, 40, 70, 7 },
		// Magician
		{ 80, 16, 4, 60, 30, 10 },
		// Fighter
		{ 100, 10, 8, 50, 50, 9 }
	};
		
	private static final int[] STANDARD_WEIGHT = { 5, 1, 1, 2, 2, 1 };

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	public void battleSessionCheck(HttpSession session) {
		String battleInfo = (String) session.getAttribute("battle");
		if (!(battleInfo == null || battleInfo == "finished")) {
			/*
			 * 전투 강제이탈 조치
			*/
			System.out.println("Battle Forced Exit");
			session.setAttribute("battle", "finished");
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String noSessionForm(HttpServletRequest request, Model model) {

		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		dbAccount.selectData(new Account());
		DBCommon<Character> dbCharacter = new DBCommon<Character>("c:\\tomcat\\my_game.sqlite", "character");
		dbCharacter.selectData(new Character());

		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("login_id");
		if (loginId == null) {
			session.invalidate();
			return "no_session_form";
		} else {
			battleSessionCheck(session);
			if (dbCharacter.duplicateCheck("id", loginId)) {
				model.addAttribute("select_result", "<input type=\"button\" class=\"yes_session\" onclick = \"location.href = 'do_delete_character'\" value=\"캐릭터 삭제\" />");
			} else {
				model.addAttribute("select_result", "<input type=\"button\" class=\"yes_session\" onclick = \"location.href = 'create_character_form'\" value=\"캐릭터 생성\" />");
			}
			session.invalidate();
			session = request.getSession();
			session.setAttribute("login_id", loginId);
			return "yes_session_form";
		}
	}

	@RequestMapping(value = "/account_create", method = RequestMethod.GET)
	public String accountCreate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String keyNumber = (String) session.getAttribute("key_number");
		if (keyNumber == null) {
			return "redirect:/check_identify";
		} else {
			return "account/account_create";
		}
	}

	@RequestMapping(value = "/do_create_account", method = RequestMethod.POST)
	public String doCreateAccount(Model model, HttpServletRequest request, @RequestParam("id") String id,
			@RequestParam("password1") String password1, @RequestParam("password2") String password2) {
		HttpSession session = request.getSession();
		String keyNumber = (String) session.getAttribute("key_number");
		if (keyNumber == null) {
			model.addAttribute("message", "주민번호 검증이 되지 않았습니다.");
			model.addAttribute("url", "check_identify");
			return "home";
		}
		
		if (!password1.equals(password2)) {
			model.addAttribute("message", "비밀번호를 다시 확인해주세요.");
			model.addAttribute("url", "account_create");
			return "home";
		}

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password1.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : md.digest()) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			password1 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			Integer.parseInt(keyNumber);
			if (keyNumber.length() != 6) {
				logger.info("Key Format Problem");
				return "redirect:/";
			}
		} catch (NumberFormatException e) {
			logger.info("String Key Number Entered");
			return "redirect:/";
		}

		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		if (dbAccount.insertData(new Account(keyNumber, id, password1))) {
			model.addAttribute("message", "성공적으로 가입되었습니다.");
			model.addAttribute("url", "login");
			return "home";
		} else {
			model.addAttribute("message", "이미 존재하는 ID입니다.");
			model.addAttribute("url", "account_create");
			return "home";
		}
	}

	@RequestMapping(value = "/check_identify", method = RequestMethod.GET)
	public String checkIdentify() {
		return "account/check_identify";
	}

	@RequestMapping(value = "/duplicate_check", method = RequestMethod.POST)
	public String duplicateCheck(Model model, HttpServletRequest request,
			@RequestParam("key_number") String keyNumber) {
		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		if (dbAccount.duplicateCheck("keyNumber", keyNumber)) {
			model.addAttribute("message", "이미 계정이 존재합니다.");
			model.addAttribute("url", "login");
			return "home";
		} else {
			try {
				Integer.parseInt(keyNumber);
				if (keyNumber.length() != 6) {
					throw new Exception();
				}
			} catch (NumberFormatException e) {
				model.addAttribute("message", "주민등록번호는 숫자만 입력해주세요.");
				model.addAttribute("url", "check_identify");
				return "home";
			} catch (Exception e) {
				model.addAttribute("message", "6자리의 번호를 입력해주세요.");
				model.addAttribute("url", "check_identify");
				return "home";
			}
			HttpSession session = request.getSession();
			session.setAttribute("key_number", keyNumber);
			return "redirect:/account_create";
		}
	}

	@RequestMapping(value = "/search_id", method = RequestMethod.GET)
	public String findId() {
		return "account/search_id";
	}

	@RequestMapping(value = "/do_search_id", method = RequestMethod.POST)
	public String dofindId(Model model, @RequestParam("key_number") String keyNumber) {
		try {
			Integer.parseInt(keyNumber);
			if (keyNumber.length() != 6) {
				throw new Exception();
			}
		} catch (NumberFormatException e) {
			model.addAttribute("message", "주민등록번호는 숫자만 입력해주세요.");
			model.addAttribute("url", "search_id");
			return "home";
		} catch (Exception e) {
			model.addAttribute("message", "6자리의 번호를 입력해주세요.");
			model.addAttribute("url", "search_id");
			return "home";
		}
		
		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		HashMap<String, String> searchIdResult = dbAccount.doSearchId(keyNumber);
		if (searchIdResult.get("success").equals("success")) {
			model.addAttribute("id", searchIdResult.get("id"));
			return "account/show_id";
		} else if (searchIdResult.get("success").equals("fail")) {
			model.addAttribute("message", "해당 번호로 등록된 계정이 존재하지 않습니다.");
			model.addAttribute("url", "search_id");
			return "home";
		} else {
			logger.info("Unknown Error");
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/search_pw", method = RequestMethod.GET)
	public String findPw() {
		return "account/search_pw";
	}

	@RequestMapping(value = "/do_search_pw", method = RequestMethod.POST)
	public String dofindpw(HttpServletRequest request, Model model, @RequestParam("key_number") String keyNumber,
			@RequestParam("id") String id) {
		try {
			Integer.parseInt(keyNumber);
			if (keyNumber.length() != 6) {
				throw new Exception();
			}
		} catch (NumberFormatException e) {
			model.addAttribute("message", "주민등록번호는 숫자만 입력해주세요.");
			model.addAttribute("url", "search_pw");
			return "home";
		} catch (Exception e) {
			model.addAttribute("message", "6자리의 번호를 입력해주세요.");
			model.addAttribute("url", "search_pw");
			return "home";
		}

		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		if (dbAccount.doSearchPw(keyNumber, id)) {
			HttpSession session = request.getSession();
			session.setAttribute("key_number", keyNumber);
			session.setAttribute("id_result", id);
			return "redirect:/update_pw";
		} else {
			model.addAttribute("message", "입력된 정보가 맞지 않습니다.");
			model.addAttribute("url", "search_pw");
			return "home";
		}
	}

	@RequestMapping(value = "/update_pw", method = RequestMethod.GET)
	public String updatePw() {
		return "account/update_pw";
	}

	@RequestMapping(value = "/do_update_pw", method = RequestMethod.POST)
	public String doupdatePw(HttpServletRequest request, Model model, @RequestParam("password1") String password1,
			@RequestParam("password2") String password2) {
		if (!password1.equals(password2)) {
			model.addAttribute("message", "비밀번호를 다시 확인해주세요.");
			model.addAttribute("url", "account_create");
			return "home";
		}
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password1.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : md.digest()) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			password1 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id_result");
		if (id == null) {
			logger.info("Compulsory Approach");
			return "redirect:/";
		}

		String keyNumber = (String) session.getAttribute("key_number");
		try {
			Integer.parseInt(keyNumber);
			if (keyNumber.length() != 6) {
				logger.info("Key Format Problem");
				return "redirect:/";
			}
		} catch (NumberFormatException e) {
			logger.info("String Key Number Entered Or Compulsory Approach");
			return "redirect:/";
		}

		session.invalidate();

		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		dbAccount.pwUpdateData(new Account(keyNumber, id, password1), keyNumber, id);

		model.addAttribute("message", "성공적으로 변경되었습니다.");
		model.addAttribute("url", "login");
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "account/login";
	}

	@RequestMapping(value = "/do_login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, Model model, @RequestParam("id") String id,
			@RequestParam("password") String password) {
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
		DBCommon<Account> dbAccount = new DBCommon<Account>("c:\\tomcat\\my_game.sqlite", "account");
		HashMap<String, String> loginCheck = dbAccount.loginCheck(id, password);
		if (loginCheck.get("success").equals("success")) {
			HttpSession session = request.getSession();
			battleSessionCheck(session);
			String inputId = loginCheck.get("id");
			session.setAttribute("login_id", inputId);
			model.addAttribute("message", inputId + "님 환영합니다.");
			model.addAttribute("url", "/com");
			return "home";
		} else if (loginCheck.get("success").equals("fail")) {
			model.addAttribute("message", "입력된 정보가 맞지 않습니다.");
			model.addAttribute("url", "login");
			return "home";
		} else {
			logger.info("Unknown Error");
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		battleSessionCheck(session);
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/create_character_form", method = RequestMethod.GET)
	public String createCharacterForm(Locale locale, Model model) {
		return "character/create_character_form";
	}

	@RequestMapping(value = "/do_create_character", method = RequestMethod.POST)
	public String doCreateCharacter(HttpServletRequest request, Model model, @RequestParam("job") String job,
			@RequestParam("hp") String hp, @RequestParam("atk") String atk, @RequestParam("def") String def,
			@RequestParam("atk_rate") String atkRate, @RequestParam("def_rate") String defRate,
			@RequestParam("speed") String speed, @RequestParam("remain_status") String remainStatus) {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("login_id");
		if (loginId == null) {
			logger.info("Compulsory Approach");
			model.addAttribute("message", "로그인 후 이용해주세요.");
			model.addAttribute("url", "login");
			return "home";
		}
		
		int jobIndex;
		if (!(job.equals("warrior") || job.equals("magician") || job.equals("fighter"))) {
			logger.info("No Job Selected");
			model.addAttribute("message", "직업을 선택해주세요.");
			model.addAttribute("url", "create_character_form");
			return "home";
		} else {
			if (job.equals("warrior")) {
				jobIndex = 0;
			} else if (job.equals("magician")) {
				jobIndex = 1;
			} else if (job.equals("fighter")) {
				jobIndex = 2;
			} else {
				logger.info("Job Search Method Problem");
				model.addAttribute("url", "create_character_form");
				return "home";
			}
		}
		ArrayList<Integer> status = new ArrayList<Integer>();
		try {
			status.add(Integer.parseInt(hp));
			status.add(Integer.parseInt(atk));
			status.add(Integer.parseInt(def));
			status.add(Integer.parseInt(atkRate));
			status.add(Integer.parseInt(defRate));
			status.add(Integer.parseInt(speed));
			int arraySum = 0;
			for (int i = 0; i < status.size(); i++) {
				arraySum += status.get(i);
			}
			if (arraySum > MAX_STATUS) {
				logger.info("Malicious Attack Detected");
				model.addAttribute("message", "비정상적 접근");
				model.addAttribute("url", "/com");
				return "home";
			} else if (arraySum < MAX_STATUS) {
				throw new Exception();
			}
			if (Integer.parseInt(remainStatus) != 0) {
				throw new Exception();
			}

		} catch (NumberFormatException e) {
			logger.info("String Status Entered");
			model.addAttribute("message", "능력치는 숫자만 입력해주새요.");
			model.addAttribute("url", "create_character_form");
			return "home";
		} catch (Exception e) {
			logger.info("Status Distribution Problem");
			model.addAttribute("message", "스텟 분배를 완료해주세요.");
			model.addAttribute("url", "create_character_form");
			return "home";

		}
		
		int[] resultStatus = new int[STANDARD_WEIGHT.length];
		for (int i = 0; i < STANDARD_WEIGHT.length; i++) {
			resultStatus[i] = STANDARD_STATUS[jobIndex][i] + (status.get(i) * STANDARD_WEIGHT[i]);
		}
		
		Character insertCharacter = new Character(loginId ,resultStatus[0], resultStatus[1], resultStatus[2], resultStatus[3], resultStatus[4], resultStatus[5], job);
		DBCommon<Character> dbCharacter = new DBCommon<Character>("C:\\tomcat\\my_game.sqlite", "character");
		if (dbCharacter.duplicateCheck("id", loginId)) {
			model.addAttribute("message", "캐릭터가 이미 존재합니다.");
			model.addAttribute("url", "/com");
			return "home";
		}
		if (dbCharacter.insertData(insertCharacter)) {
			model.addAttribute("message", "캐릭터가 성공적으로 생성되었습니다.");
			model.addAttribute("url", "/com");
			return "home";
		} else {
			logger.info("Character Insert Error");
			model.addAttribute("message", "캐릭터가 생성 실패");
			model.addAttribute("url", "create_character_form");
			return "home";
		}
	}

	@RequestMapping(value = "/do_delete_character", method = RequestMethod.GET)
	public String doDeleteCharacter(Model model, HttpServletRequest request) {
		DBCommon<Character> dbCommon = new DBCommon<Character>("C:\\tomcat\\my_game.sqlite", "character");
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("login_id");
		if (loginId == null) {
			model.addAttribute("message", "로그인 해주세요.");
			model.addAttribute("url", "login");
			return "home";
		} else {
			dbCommon.deleteData("id", loginId);
			model.addAttribute("message", "삭제되었습니다.");
			model.addAttribute("url", "/com");
			return "home";
		}
	}

	@RequestMapping(value = "/select_enemy_form", method = RequestMethod.GET)
	public String selectEnemy(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();		
		String loginId = (String) session.getAttribute("login_id");
		
		if (loginId == null) {
			logger.info("Login Session Not Found");
			model.addAttribute("message", "로그인 해주세요.");
			model.addAttribute("url", "login");
			return "home";
		}
				
		DBCommon<Character> dbCharacter = new DBCommon<Character>("c:\\tomcat\\my_game.sqlite", "character");
		if (!dbCharacter.duplicateCheck("id", loginId)) {
			model.addAttribute("message", "캐릭터를 생성해주세요.");
			model.addAttribute("url", "create_character_form");
			return "home";
		}
		
		String selectResult = dbCharacter.selectDataTableTag(new Character(), loginId);
		
		model.addAttribute("select_result", selectResult);
		
		return "battle/select_enemy_form";
	}

	@RequestMapping(value = "/battle_form", method = RequestMethod.POST)
	public String battle(HttpServletRequest request, Model model, @RequestParam("id") String enemyId) {

		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("login_id");

		if (loginId == null) {
			logger.info("Login Session Not Found");
			model.addAttribute("message", "로그인 해주세요.");
			model.addAttribute("url", "login");
			return "home";
		}

		DBCommon<Character> dbCharacter = new DBCommon<Character>("c:\\tomcat\\my_game.sqlite", "character");
		
		if (!dbCharacter.duplicateCheck("id", loginId)) {
			model.addAttribute("message", "캐릭터를 생성해주세요.");
			model.addAttribute("url", "create_character_form");
			return "home";
		}
		
		battleSessionCheck(session);
		session.invalidate();
		session = request.getSession();
		session.setAttribute("login_id", loginId);
		
		HashMap<String, String> selectedCharacter;

		selectedCharacter = dbCharacter.selectDataById(new Character(), loginId);

		Character myCharacter = new Character();
		int myGauge;
		int myCurrentHp;
		try {
			String strMyGauge = (String) session.getAttribute("my_gauge");
			try {
				myGauge = Integer.parseInt(strMyGauge);
			} catch (NumberFormatException e) {
				myGauge = 0;
			}
			
			String strMyCurrentHp = (String) session.getAttribute("my_currenthp");
			try {
				myCurrentHp = Integer.parseInt(strMyCurrentHp);
			} catch (NumberFormatException e) {
				myCurrentHp = Integer.parseInt(selectedCharacter.get("hp"));
			}
			
			myCharacter.setHp(Integer.parseInt(selectedCharacter.get("hp")));
			myCharacter.setAtk(Integer.parseInt(selectedCharacter.get("atk")));
			myCharacter.setDef(Integer.parseInt(selectedCharacter.get("def")));
			myCharacter.setAtkRate(Integer.parseInt(selectedCharacter.get("atkRate")));
			myCharacter.setDefRate(Integer.parseInt(selectedCharacter.get("defRate")));
			myCharacter.setSpeed(Integer.parseInt(selectedCharacter.get("speed")));
			myCharacter.setJob(selectedCharacter.get("job"));
			myCharacter.setAtkGauge(myGauge);
			myCharacter.setCurrentHp(myCurrentHp);
		} catch (NumberFormatException e) {
			logger.info("String My Character Status Entered");
			return "redirect:/select_enemy_form";
		}

		selectedCharacter = dbCharacter.selectDataById(new Character(), enemyId);
		Character enemyCharacter = new Character();
		int enemyGauge;
		int enemyCurrentHp;
		try {
			String strEnemyGauge = (String) session.getAttribute("enemy_gauge");
			try {
				enemyGauge = Integer.parseInt(strEnemyGauge);
			} catch (NumberFormatException e) {
				enemyGauge = 0;
			}
			String strEnemyCurrentHp = (String) session.getAttribute("enemy_currenthp");
			try {
				enemyCurrentHp = Integer.parseInt(strEnemyCurrentHp);
			} catch (NumberFormatException e) {
				enemyCurrentHp = Integer.parseInt(selectedCharacter.get("hp"));
			}
			enemyCharacter.setHp(Integer.parseInt(selectedCharacter.get("hp")));
			enemyCharacter.setAtk(Integer.parseInt(selectedCharacter.get("atk")));
			enemyCharacter.setDef(Integer.parseInt(selectedCharacter.get("def")));
			enemyCharacter.setAtkRate(Integer.parseInt(selectedCharacter.get("atkRate")));
			enemyCharacter.setDefRate(Integer.parseInt(selectedCharacter.get("defRate")));
			enemyCharacter.setSpeed(Integer.parseInt(selectedCharacter.get("speed")));
			enemyCharacter.setJob(selectedCharacter.get("job"));
			enemyCharacter.setAtkGauge(enemyGauge);
			enemyCharacter.setCurrentHp(enemyCurrentHp);
		} catch (NumberFormatException e) {
			logger.info("String Enemy Character Status Entered");
			return "redirect:/select_enemy_form";
		}

		Battle battle = new Battle(myCharacter, enemyCharacter);

		model.addAttribute("my_first_gauge", myGauge);
		model.addAttribute("enemy_first_gauge", enemyGauge);
		
		ArrayList<int[]> gaugeList = new ArrayList<int[]>();
		int myLastGauge = myGauge;
		int enemyLastGauge = enemyGauge;
		while (myLastGauge < 100 && enemyLastGauge < 100) {
			gaugeList.add(battle.turnExecute());
			myLastGauge = gaugeList.get(gaugeList.size() - 1)[0];
			enemyLastGauge = gaugeList.get(gaugeList.size() - 1)[1];
		}

		if (gaugeList.size() == 0) {
			int[] oneGauge = { myLastGauge, enemyLastGauge };
			gaugeList.add(oneGauge);
		}

		String resultString = new String();
		for (int i = 0; i < gaugeList.size(); i++) {
			for (int j = 0; j < gaugeList.get(i).length; j++) {
				resultString += gaugeList.get(i)[j] + "&";
			}
			resultString = resultString.substring(0, resultString.length() - 1);
			resultString += "|";
		}
		resultString = resultString.substring(0, resultString.length() - 1);
		
		model.addAttribute("my_max_hp", myCharacter.getHp());
		model.addAttribute("my_current_hp", myCurrentHp);
		
		model.addAttribute("enemy_max_hp", enemyCharacter.getHp());
		model.addAttribute("enemy_current_hp", enemyCurrentHp);
		
		model.addAttribute("my_character", myCharacter.getJob());
		model.addAttribute("enemy_character", enemyCharacter.getJob());
		
		model.addAttribute("gauge_list", resultString);

		session.setAttribute("my_character", myCharacter);
		session.setAttribute("my_gauge", Integer.toString(myLastGauge));
		session.setAttribute("my_current_hp", Integer.toString(myCurrentHp));
		
		session.setAttribute("enemy_id", enemyId);
		session.setAttribute("enemy_character", enemyCharacter);
		session.setAttribute("enemy_gauge", Integer.toString(enemyLastGauge));
		session.setAttribute("enemy_current_hp", Integer.toString(enemyCurrentHp));
		
		session.setAttribute("battle", "started");

		return "battle/battle_form";
	}
	
	@RequestMapping(value = "/do_attack", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> doAttack(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		Character myCharacter = (Character) session.getAttribute("my_character");
		Character enemyCharacter = (Character) session.getAttribute("enemy_character");
		String loginId = (String) session.getAttribute("login_id");
		String enemyId = (String) session.getAttribute("enemy_id");
		int myGauge;
		int myCurrentHp;
		int enemyGauge;
		int enemyCurrentHp;
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		if (myCharacter == null || enemyCharacter == null || loginId == null || enemyId == null) {
			resultMap.put("result", "Session Problem");
			return resultMap;
		}
		try {
			myGauge = Integer.parseInt((String) session.getAttribute("my_gauge"));
			myCurrentHp = Integer.parseInt((String) session.getAttribute("my_current_hp"));
			enemyGauge = Integer.parseInt((String) session.getAttribute("enemy_gauge"));
			enemyCurrentHp = Integer.parseInt((String) session.getAttribute("enemy_current_hp"));
		} catch(NumberFormatException e) {
			resultMap.put("result", "Session Problem");
			return resultMap;
		}
		
		Battle battle = new Battle(myCharacter, enemyCharacter);
		
		int giveDamage = 0;
		while (myGauge >= 100) {
			giveDamage = battle.attackExecute(0);
			enemyCurrentHp -= giveDamage;
			myGauge -= 100;
			myCharacter.setAtkGauge(myGauge);
		}
		int receiveDamage = 0;
		while (enemyGauge >= 100) {
			receiveDamage = battle.attackExecute(1);
			myCurrentHp -= receiveDamage;
			enemyGauge -= 100;
			enemyCharacter.setAtkGauge(enemyGauge);
		}
		
		if (myCurrentHp <= 0 && enemyCurrentHp <= 0) {
			resultMap.put("battleResult", "tie");
			session.setAttribute("battle", "finished");
		} else if (myCurrentHp <= 0) {
			resultMap.put("battleResult", "lose");
			session.setAttribute("battle", "finished");
		} else if (enemyCurrentHp <= 0) {
			resultMap.put("battleResult", "win");
			session.setAttribute("battle", "finished");
		} else {
			resultMap.put("battleResult", "going");
			session.setAttribute("battle", "going");
			
			ArrayList<int[]> gaugeList = new ArrayList<int[]>();
			int myLastGauge = myGauge;
			int enemyLastGauge = enemyGauge;
			while (myLastGauge < 100 && enemyLastGauge < 100) {
				gaugeList.add(battle.turnExecute());
				myLastGauge = gaugeList.get(gaugeList.size() - 1)[0];
				enemyLastGauge = gaugeList.get(gaugeList.size() - 1)[1];
			}

			if (gaugeList.size() == 0) {
				int[] oneGauge = { myLastGauge, enemyLastGauge };
				gaugeList.add(oneGauge);
			}
			
			String resultString = new String();
			for (int i = 0; i < gaugeList.size(); i++) {
				for (int j = 0; j < gaugeList.get(i).length; j++) {
					resultString += gaugeList.get(i)[j] + "&";
				}
				resultString = resultString.substring(0, resultString.length() - 1);
				resultString += "|";
			}
			resultString = resultString.substring(0, resultString.length() - 1);
			
			resultMap.put("giveDamage", Integer.toString(giveDamage));
			resultMap.put("receiveDamage", Integer.toString(receiveDamage));
			
			resultMap.put("gaugeList", resultString);

			session.setAttribute("my_gauge", Integer.toString(myLastGauge));
			session.setAttribute("my_current_hp", Integer.toString(myCurrentHp));
			session.setAttribute("enemy_gauge", Integer.toString(enemyLastGauge));
			session.setAttribute("enemy_current_hp", Integer.toString(enemyCurrentHp));
		}
		
		resultMap.put("result", "success");
		return resultMap;
		
	}

}
