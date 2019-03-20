package kr.co.mymelon;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	//mymelon 프로젝트의 첫페이지 호출
	// http://localhost:9090/mymelon/home.do
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav=new ModelAndView();
		//redirect : 등록된 명령어를 호출할 수 있다
		mav.setViewName("redirect:/mediagroup/list.do");
		return mav;
	}
	
}
