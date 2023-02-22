package com.coding404.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper
public interface ProductMapper {

	public int regist(ProductVO vo);
	public int registFile(ProductUploadVO vo);
	//웬만해서는 파라미터 2개 이상 쓰는건 좋지 않음. 얘가 잘 모르니까 param이용해서 확실히 알려주기
	//매개변수로 전달되는 데이터가 2개 이상이라면 이름 붙이기
	public ArrayList<ProductVO> getList(@Param("user_id") String user_id,
										@Param("cri") Criteria cri); //조회: 특정 회원정보만 조회
	public int getTotal(@Param("user_id") String user_id, @Param("cri") Criteria cri); //전체 게시글수
	
	//카테고리 대분류
	public List<CategoryVO> getCategory();
	//카테고리 중, 소분류
	public List<CategoryVO> getCategoryChild(CategoryVO vo);
	
}
