package com.coding404.myweb.command;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	
	@NotBlank(message = "판매종료일은 필수로 입력해주세요")
	@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "판매종료일은 필수로 입력해주세요")
	private String prod_enddate;
	@NotBlank(message = "카테고리는 필수로 입력해주세요")
	private String prod_category; //insert할 때는 아직없음 (영어+숫자)
	private String category_nav; //insert할 때는 아직없음, 카테고리 조인된 결과(한글)
	
	
	@NotBlank(message = "공백일 수 없습니다")
	private String prod_writer;
	@NotBlank(message = "상품명은 필수로 입력해주세요")
	private String prod_name;
	
	@Min(value = 0)
	@NotNull(message = "0원 이상으로 입력해주세요")
	private Integer prod_price;
	@Min(value = 1)
	@NotNull(message = "1개 이상으로 입력해주세요")
	private Integer prod_count;
	@Max(value = 100)
	@NotNull(message = "100% 이하로 입력해주세요")
	private Integer prod_discount;
	private String prod_purchase_yn;
	@NotNull
	private String prod_content;
	@NotNull
	private String prod_comment;
}
