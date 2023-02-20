package com.coding404.myweb.command;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVO {
	
	/* 서버에서 유효성 검사
	 * @NotNull - null값만 허용하지 않음, 공백으로 주면 에러 안남 (wrapper의Integer, Long, String 등)
	 * @NotBlank - null값과 공백 허용하지 않음 (String에만 적용)
	 * @NotEmpty - null값을 허용하지 않음 (Array, list 적용)
	 * @Pattern - 정규표현식에 맞는 문자열을 정의할 수 있음 (String에만 적용)
	 * 
	 * @Email - 이메일 형식 검증 (공백은 통과)
	 * @Min - 최소값
	 * @Max - 최대값
	 */
	
	private int prod_id;
	private LocalDate prod_regdate;
	
	@NotBlank(message = "공백일 수 없습니다")
	@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String prod_enddate;
	private String prod_category; //insert할 때는 아직없음
	private String category_nav; //카테고리 조인된 결과
	
	
	@NotBlank(message = "공백일 수 없습니다")
	private String prod_writer;
	@NotBlank(message = "공백일 수 없습니다")
	private String prod_name;
	
	@Min(value = 0, message = "0원 이상이어야 합니다")
	private int prod_price;
	private int prod_count;
	private int prod_discount;
	private String prod_purchase_yn;
	private String prod_content;
	private String prod_comment;

}
