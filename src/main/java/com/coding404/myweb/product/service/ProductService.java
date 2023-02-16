package com.coding404.myweb.product.service;

import java.util.ArrayList;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

public interface ProductService {

	public int regist(ProductVO vo);
	public ArrayList<ProductVO> getList(String user_id, Criteria cri); //조회: 특정 회원정보만 조회
	public int getTotal(String user_id, Criteria cri); //전체 게시글수
}
