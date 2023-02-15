package com.coding404.myweb.util;

import lombok.Data;

@Data
public class Criteria {
	
	//SQL에 전단할 page, amount 을 가지고 다니는 클래스
	private int page; //조회하는 페이지 번호
	private int amount; //데이터 개수
	
	
	//검색 키워드
	private String searchName; //상품명
	private String searchContent; //상품내용
	private String searchPrice; //정렬방식
	private String startDate; //판매시작일
	private String endDate; //판매종료일

	
	public Criteria() { //기본 생성자 setting
		this.page = 1;
		this.amount = 10;
	}

	public Criteria(int page, int amount) {
		super();
		this.page = page;
		this.amount = amount;
	}
	
	public int getPageStart() { //앞쪽에 들어갈 변수
		return (page - 1) * amount;
	}
	
	
	
}