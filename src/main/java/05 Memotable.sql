CREATE TABLE MEMO(
	MNO INT PRIMARY KEY AUTO_INCREMENT,
  WRITER VARCHAR(50) NOT NULL,
	MEMO VARCHAR(200) NOT NULL
);

insert into MEMO(writer, memo) values('aaa', 'aaa');
insert into MEMO(writer, memo) values('aaa', 'bbb');

CREATE TABLE USERS(
	ID VARCHAR(50) PRIMARY KEY,
    PW VARCHAR(50) NOT NULL,
    NAME VARCHAR(50),
    EMAIL VARCHAR(50) UNIQUE
);

insert into users(ID, pw, name, email) values('aaa', '1234', '홍길동', 'aaa@naver.com');

select * from memo;
select * from users;

## n : 1의 조인
select * from memo m left outer	join users u on m.WRITER = u.id;

## 1 : n의 조인
select * from users u left outer join memo m on u.id = m.writer;

## 1번 방법
select * from product as p
left outer join(
	SELECT
	   #A1.*,
       #A2.CATEGORY_DETAIL_NM,
       #A3.CATEGORY_DETAIL_NM,
	   #CONCAT(A1.group_ID, A1.CATEGORY_ID, A1.category_LV,  A1.category_detail_LV) AS CATEGORY_KEY, ##카테고리 키
       CONCAT(A1.group_ID, A1.CATEGORY_ID) AS CATEGORY_KEY, ##카테고리 키 (그룹 + 키 형태로 카테고리분류)
 	   CASE A1.category_parent_LV
 	        WHEN 0 THEN A1.category_detail_NM
 			WHEN 1 THEN CONCAT(A2.category_detail_NM,' > ', A1.category_detail_NM)
 			WHEN 2 THEN CONCAT(A3.category_detail_NM, ' > ', A2.category_detail_NM,' > ', A1.category_detail_NM)
 	   END as CATEGORY_NAV
FROM PRODUCT_CATEGORY A1
LEFT OUTER JOIN PRODUCT_CATEGORY A2
ON A1.CATEGORY_PARENT_LV = A2.CATEGORY_LV AND A1.CATEGORY_DETAIL_PARENT_LV = A2.CATEGORY_DETAIL_LV AND A1.GROUP_ID = A2.GROUP_ID
LEFT OUTER JOIN PRODUCT_CATEGORY A3
ON A2.CATEGORY_PARENT_LV = A3.CATEGORY_LV AND A2.CATEGORY_DETAIL_PARENT_LV = A3.CATEGORY_DETAIL_LV
ORDER BY CATEGORY_NAV ASC
) as c
on c.category_key = p.PROD_CATEGORY
where PROD_WRITER = 'admin'
and prod_name like '%아이%' ## +조건 +조건
order by prod_price asc, prod_id desc
limit 0, 10;


## 2번 방법
select p.*,
	   c.category_nav
from (
	select *
    from product
	where PROD_WRITER = 'admin'
	and prod_name like '%삼성%' ## +조건 +조건
	order by prod_price asc, prod_id desc
	limit 0, 10
) p
left outer join (
	SELECT
	   #A1.*,
       #A2.CATEGORY_DETAIL_NM,
       #A3.CATEGORY_DETAIL_NM,
	   #CONCAT(A1.group_ID, A1.CATEGORY_ID, A1.category_LV,  A1.category_detail_LV) AS CATEGORY_KEY, ##카테고리 키
       CONCAT(A1.group_ID, A1.CATEGORY_ID) AS CATEGORY_KEY, ##카테고리 키 (그룹 + 키 형태로 카테고리분류)
 	   CASE A1.category_parent_LV
 	        WHEN 0 THEN A1.category_detail_NM
 			WHEN 1 THEN CONCAT(A2.category_detail_NM,' > ', A1.category_detail_NM)
 			WHEN 2 THEN CONCAT(A3.category_detail_NM, ' > ', A2.category_detail_NM,' > ', A1.category_detail_NM)
 	   END as CATEGORY_NAV
FROM PRODUCT_CATEGORY A1
LEFT OUTER JOIN PRODUCT_CATEGORY A2
ON A1.CATEGORY_PARENT_LV = A2.CATEGORY_LV AND A1.CATEGORY_DETAIL_PARENT_LV = A2.CATEGORY_DETAIL_LV AND A1.GROUP_ID = A2.GROUP_ID
LEFT OUTER JOIN PRODUCT_CATEGORY A3
ON A2.CATEGORY_PARENT_LV = A3.CATEGORY_LV AND A2.CATEGORY_DETAIL_PARENT_LV = A3.CATEGORY_DETAIL_LV
ORDER BY CATEGORY_NAV ASC
) c
on p.PROD_CATEGORY = c.category_key;

###################################################################################################################
SELECT
	   #A1.*,
       #A2.CATEGORY_DETAIL_NM,
       #A3.CATEGORY_DETAIL_NM,
	   #CONCAT(A1.group_ID, A1.CATEGORY_ID, A1.category_LV,  A1.category_detail_LV) AS CATEGORY_KEY, ##카테고리 키
       CONCAT(A1.group_ID, A1.CATEGORY_ID) AS CATEGORY_KEY, ##카테고리 키 (그룹 + 키 형태로 카테고리분류)
 	   CASE A1.category_parent_LV
 	        WHEN 0 THEN A1.category_detail_NM
 			WHEN 1 THEN CONCAT(A2.category_detail_NM,' > ', A1.category_detail_NM)
 			WHEN 2 THEN CONCAT(A3.category_detail_NM, ' > ', A2.category_detail_NM,' > ', A1.category_detail_NM)
 	   END as CATEGORY_NAV
FROM PRODUCT_CATEGORY A1
LEFT OUTER JOIN PRODUCT_CATEGORY A2
ON A1.CATEGORY_PARENT_LV = A2.CATEGORY_LV AND A1.CATEGORY_DETAIL_PARENT_LV = A2.CATEGORY_DETAIL_LV AND A1.GROUP_ID = A2.GROUP_ID
LEFT OUTER JOIN PRODUCT_CATEGORY A3
ON A2.CATEGORY_PARENT_LV = A3.CATEGORY_LV AND A2.CATEGORY_DETAIL_PARENT_LV = A3.CATEGORY_DETAIL_LV
ORDER BY CATEGORY_NAV ASC;

select * from product;