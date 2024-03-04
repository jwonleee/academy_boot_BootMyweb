package com.coding404.myweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.coding404.myweb.util.interceptor.MenuHandler;
import com.coding404.myweb.util.interceptor.UserAuthHandler;

@Configuration //스프링 설정 파일
public class WebConfig implements WebMvcConfigurer{

	//프리핸들러
	@Bean
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler();
	}
	
	//포스트핸들러
	@Bean
	public MenuHandler menuHandler() {
		return new MenuHandler();
	}
	
	//인터셉터 추가
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(userAuthHandler())
//				.addPathPatterns("/main")
//				.addPathPatterns("/product/*") //product 시작하는 경로 다 추가
//				.addPathPatterns("/user/*") //user 시작하는 경로 다 추가
//				.excludePathPatterns("/user/login") //login 경로 제외
//				.excludePathPatterns("/user/join"); //join 경로 제외
		
				//.addPathPatterns("/**") //**해야 다른 /product까지 잡힘, *만 있으면 바로 하위의 /user만 잡힘 
				//.excludePathPatterns("/user/login")
				//.excludePathPatterns("/user/join")
				//.excludePathPatterns("/js/*")
				//.excludePathPatterns("/css/*")
				//.excludePathPatterns("/img/*");
				//REST API 패스에서 제외..? 해야함
		
		registry.addInterceptor(menuHandler())
				.addPathPatterns("/main")
				.addPathPatterns("/product/*")
				.addPathPatterns("/user/*");
	}

	
}
