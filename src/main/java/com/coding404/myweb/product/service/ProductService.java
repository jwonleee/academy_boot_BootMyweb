package com.coding404.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

public interface ProductService {

	//글 등록(파일 업로드)
	public int regist(ProductVO vo, List<MultipartFile> list);
	public ArrayList<ProductVO> getList(String user_id, Criteria cri); //조회: 특정 회원정보만 조회
	public int getTotal(String user_id, Criteria cri); //전체 게시글수
	
	//카테고리 대분류
	public List<CategoryVO> getCategory();
	//카테고리 중, 소분류
	public List<CategoryVO> getCategoryChild(CategoryVO vo);
	
	//이미지 데이터 조회
	public List<ProductUploadVO> getProductImg(ProductVO vo);
}
