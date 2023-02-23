package com.coding404.myweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/main") // 8484/ 시작하는 페이지는 없음
	public String home() {
		return "main";
	}
	
}
