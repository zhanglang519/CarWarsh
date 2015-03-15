package cn.edu.cqu.carwarsh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@RequestMapping(value="/hello.do")
	@ResponseBody
	public String sayHello(){
		return "hahah";
	}
}
