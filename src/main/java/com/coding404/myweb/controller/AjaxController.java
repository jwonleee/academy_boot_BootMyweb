package com.coding404.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductService;

import lombok.val;

@RestController // 리턴에 실리는 값들이 데이터로 나감
public class AjaxController {

	// 대분류 카테고리 요청
	@Autowired
	private ProductService productService;

	// application.property에서 참조
	@Value("${project.uploadpath}")
	private String uploadpath;

	@GetMapping("/getCategory")
	public List<CategoryVO> getCategory() { // 무조건 카테고리 레벨=1인 데이터가 restAPI가 줄 것은 없음
		return productService.getCategory();
	}

	// 중분류 소분류 카테고리 요청
	@GetMapping("/getCategoryChild/{group_id}/{category_lv}/{category_detail_lv}")
	public List<CategoryVO> getCategoryChild(@PathVariable("group_id") String group_id,
			@PathVariable("category_lv") int category_lv, @PathVariable("category_detail_lv") int category_detail_lv) {
		CategoryVO vo = CategoryVO.builder().group_id(group_id).category_lv(category_lv)
				.category_detail_lv(category_detail_lv).build();

		return productService.getCategoryChild(vo); // vo를 전달하고 반환받아 나온 것을 화면에 뿌려줌
	}

	// 0222 10:30 추가 - 이미지 정보를 처리
	// 1. ?키=값
	// 2. @Pathvariable
	// 화면에는 2진데이터 타입이 반환됩니다.

	// 이미지 불러오는 방법1
//	@GetMapping("/display/{filepath}/{uuid}/{filename}")
//	public byte[] display(@PathVariable("filepath") String filepath,
//						  @PathVariable("uuid") String uuid,
//						  @PathVariable("filename") String filename) {
//
//		// 파일이 저장된 경로 String savename = uploadpath +
//		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
//		File file = new File(savename);
//
//		// 저장된 이미지 파일의 이진데이터 형식을 구함, 자바에는 없고 스프링에만 있음
//		byte[] result = null; // 파일 경로가 없으면 null값 들어옴
//
//		try {
//			result = FileCopyUtils.copyToByteArray(file);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	// 이미지 불러오는 방법2
//	produces: 컨트롤러가 생성하는 응답의 미디어 타입을 명시적으로 지정, 요청의 Accept 헤더에 지정된 미디어 타입과 일치해야함
	@GetMapping(value="/display/{filepath}/{uuid}/{filename}", produces="img/jpg")
	public ResponseEntity<byte[]> display(@PathVariable("filepath") String filepath,
						  @PathVariable("uuid") String uuid,
						  @PathVariable("filename") String filename) {
		
		//파일이 저장된 경로
		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
		File file = new File(savename);
		
		//저장된 이미지 파일의 이진데이터 형식을 구함
		byte[] result = null; //1. data
		ResponseEntity<byte[]> entity = null;
		
		try {
			result = FileCopyUtils.copyToByteArray(file);
			
			//2. header
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장
			
			//3. 응답본문
			entity = new ResponseEntity<>(result, header, HttpStatus.OK); //데이터, 헤더, 상태값
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	// 특정 이미지 불러오기 - prod_id 값 받아서 이미지 정보를 반환 (함수의 모형을 선언)
	@PostMapping("/getProductImg")
	public ResponseEntity<List<ProductUploadVO>> getProductImg(@RequestBody ProductVO vo) {

		System.out.println("보자" + vo.toString()); // 확인
		// 1. data
		// List<ProductUploadVO> list = productService.getProductImg(vo);
		// ResponseEntity<List<ProductUploadVO>> entity = new ResponseEntity<>(list,
		// HttpStatus.OK); 아래와 같음

		return new ResponseEntity<>(productService.getProductImg(vo), HttpStatus.OK);
	}

	// 다운로드 기능
	@GetMapping("/download/{filepath}/{uuid}/{filename}")
	public ResponseEntity<byte[]> download(@PathVariable("filepath") String filepath,
										   @PathVariable("uuid") String uuid,
										   @PathVariable("filename") String filename) {
		// 파일이 저장된 경로
		String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
		File file = new File(savename);

		// 저장된 이미지 파일의 이진데이터 형식을 구함
		byte[] result = null; // 1. data
		ResponseEntity<byte[]> entity = null;

		try {
			result = FileCopyUtils.copyToByteArray(file);

			// 2. header
			HttpHeaders header = new HttpHeaders();

			// 다운로드임을 명시 (파일 이름을 UTF-8로 URL 인코딩, 공백을 %20으로 대체)
			String encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			header.add("Content-Disposition", "attachment; filename=" + encodedFilename);
			//header.add("Content-Disposition", "attachment; filename=" + filename); // 떨어트려줄 이름 자체가 파일명

			// 3. 응답본문
			entity = new ResponseEntity<>(result, header, HttpStatus.OK); // 데이터, 헤더, 상태값
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

}
