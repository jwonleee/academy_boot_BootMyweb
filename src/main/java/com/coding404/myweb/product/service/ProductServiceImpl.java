package com.coding404.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public int regist(ProductVO vo) {
		return productMapper.regist(vo);
	}

	@Override
	public ArrayList<ProductVO> getList(String user_id, Criteria cri) {
		return productMapper.getList(user_id, cri);
	}

	@Override
	public int getTotal(String user_id, Criteria cri) {
		return productMapper.getTotal(user_id, cri);
	}
	
	@Override
	public List<CategoryVO> getCategory() {
		
		return productMapper.getCategory();
	}

	@Override
	public List<CategoryVO> getCategoryChild(CategoryVO vo) {
		return productMapper.getCategoryChild(vo);
	}
	

}
