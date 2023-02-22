package com.coding404.myweb.product.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	//업로드 패스
	@Value("${project.uploadpath}")
	private String uploadpath;
	
	//날짜별로 폴더 생성
	public String makeDir() {
		
		//지금 날짜 가져오기
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String now = sdf.format(date);
		
		//폴더 생성구문 - upload 폴더 하위로
		String path = uploadpath + "\\" + now; //경로
		File file = new File(path);
		
		if(file.exists() == false) { //존재하면 true
			file.mkdir(); //폴더 생성
		}
		return now; //년월일 폴더위치
	}
	
	//글등록
	//한 프로세스 안에서 예외가 발생하면, 기존에 진행했던 CRUD작업을 Rollback 시킴
	//조건 - catch를 통해서 예외처리가 진행되면 transactional이 에러를 잡지 못함 = 트랜젝션 처리가 되지 않음
 	@Transactional(rollbackFor = Exception.class)
	@Override
	public int regist(ProductVO vo, List<MultipartFile> list) {
		
		//1. 글(상품) 등록 처리 ▶
		int result = productMapper.regist(vo);
		
		//2. 파일 인서트 ▶
		//반복처리
		for(MultipartFile file : list) {
			
			//파일명
			String origin = file.getOriginalFilename();
			//브라우저별로 경로가 포함되서 올라오는 경우가 있어서 간단한 처리
			String filename = origin.substring(origin.lastIndexOf("\\") + 1);
			
			//폴더 생성
			String filepath = makeDir();
			//중복파일의 처리 - 랜덤한 String 값
			String uuid = UUID.randomUUID().toString();
			//최종 저장 경로
			String savename  = uploadpath + "\\" + uuid + "_" + filename;

	
			try {
				File save = new File(savename); //세이브 경로
				file.transferTo(save); //업로드 진행
				
			} catch (Exception e) {
				e.printStackTrace();
				return 0; //실패의 의미로
			}
			
			
			//insert 이전에 prod_id가 필요한데, selectKey 방식으로 처리
			ProductUploadVO prodVO = ProductUploadVO.builder()
							.filename(filename)
							.filepath(filepath)
							.uuid(uuid)
							.prod_writer(vo.getProd_writer())
							.build();
							
			productMapper.registFile(prodVO);
			
			} //end for문
		
			return result; //성공시 1, 실패시 0
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
