package com.coding404.myweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.product.service.ProductService;

@RestController
public class AjaxController {

	//대분류 카테코리 요청
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getCategory")
	public List<CategoryVO> getCategory() { //무조건 카테고리 레벨=1인 데이터가 restAPI가 줄 것은 없음
		return productService.getCategory();
	}
	
	//중분류 소분류 카테고리 요청
	@GetMapping("/getCategoryChild/{group_id}/{category_lv}/{category_detail_lv}")
	public List<CategoryVO> getCategoryChild(@PathVariable("group_id") String group_id,
											 @PathVariable("category_lv") int category_lv,
											 @PathVariable("category_detail_lv") int category_detail_lv) {
		CategoryVO vo = CategoryVO.builder()
								  .group_id(group_id)
								  .category_lv(category_lv)
								  .category_detail_lv(category_detail_lv)
								  .build();
		
		return productService.getCategoryChild(vo); //vo를 전달하고 반환받아 나온 것을 화면에 뿌려줌
	}
	
	
}
