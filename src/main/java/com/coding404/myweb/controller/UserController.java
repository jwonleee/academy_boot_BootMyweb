package com.coding404.myweb.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coding404.myweb.command.UserVO;
import com.coding404.myweb.util.KakaoAPI;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired //서비스 영역과 같음
	private KakaoAPI kakaoAPI;

	//카카오 redirect_uri
	@GetMapping("/kakao")
	public String kakao(@RequestParam("code") String code) {
		System.out.println("인가코드:" + code);
			
		String token = kakaoAPI.getAccessToken(code);
			
		System.out.println("어세스토큰:" + token);
		
		Map<String, Object> map =  kakaoAPI.getUserInfo(token);
		
		System.out.println("사용자데이터:" + map.toString());
		
		//우리 데이터베이스에서 조회해서 로그인처리 ...하면 됨
		
//		return "redirect:/main";
		return null;
	}
	
	
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginForm(UserVO vo) {
		UserVO userVO = new UserVO();
		userVO.setId("test123");
		userVO.setPw("test1234");
		System.out.println("이거이거왜 안나와");
		return "redirect:/user/mypage";
	}
	
	@GetMapping("/userDetail")
	public String userDetail() {
		return "user/userDetail";
	}
	
	
}
