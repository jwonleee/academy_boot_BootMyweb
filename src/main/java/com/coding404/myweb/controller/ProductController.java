package com.coding404.myweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

	//화면은 getMapping ?
	
	@GetMapping("/productReg")
	public String reg() {
		return "/product/productReg";
	}
	
	@GetMapping("/productList")
	public String list() {
		return "/product/productList";
	}
	
	@GetMapping("/productDetail")
	public String detail() {
		return "/product/productDetail";
	}
}
