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
		System.out.println("사용자데이터(닉네임, 이메일):" + map.toString());
		
		//서비스 데이터베이스(Database)의 가입, 탈퇴 등 회원 정보 처리는 서비스에서 자체 구현
		//카카오는 서비스 데이터에 접근하지 않으므로 회원 정보를 대신 저장하거나 삭제할 수 없음
		//서비스 서버는 카카오로부터 받은 사용자 정보로 회원가입 되어 있는지 확인
		//1. 이미 회원 - 해당 사용자의 로그인에 대한 세션 발급 후, 로그인 완료 처리
		//2. 회원 X - 카카오에서 받은 정보로 서비스 데이터베이스에 회원가입 처리
		
		return "redirect:/main";
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
