package com.coding404.myweb.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.coding404.myweb.product.service.ProductMapper;

public class MenuHandler implements HandlerInterceptor {

	//메뉴핸들러
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String uri = request.getRequestURI(); //전체주소 - 포트번호, /product/productList
		System.out.println(uri);
		request.setAttribute("uri", uri); //request 객체에 저장(키, 값)
		
		// 요청 URI에 따라 다른 로직 수행
        if (uri.startsWith("/product")) {
            // "/admin"으로 시작하는 요청의 경우 특별한 작업 수행
            System.out.println("상품 페이지 요청입니다.");
            // 추가적인 로직 수행 가능
        } else {
            // 그 외의 경우 기본 로직 수행
            System.out.println("사용자 페이지 요청입니다.");
            // 기본적인 로직 수행
        }
	}
}

