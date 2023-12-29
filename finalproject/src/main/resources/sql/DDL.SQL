/* 회원 */
DROP TABLE MEMBER 
	CASCADE CONSTRAINTS;

/* 권한 */
DROP TABLE ROLE 
	CASCADE CONSTRAINTS;

/* 주소 */
DROP TABLE ADDRESS 
	CASCADE CONSTRAINTS;

/* 주문 */
DROP TABLE ORDERS 
	CASCADE CONSTRAINTS;

/* 결제 */
DROP TABLE PAY 
	CASCADE CONSTRAINTS;

/* 파일 */
DROP TABLE FILES 
	CASCADE CONSTRAINTS;

/* 세탁물 */
DROP TABLE LAUNDRY 
	CASCADE CONSTRAINTS;

/* 세탁 카테고리 */
DROP TABLE LAUNDRY_CATEGORY 
	CASCADE CONSTRAINTS;
    
DROP SEQUENCE FILES_SEQ;

DROP SEQUENCE LAUNDRY_SEQ;

DROP SEQUENCE LAUNDRY_CATEGORY_SEQ;

DROP SEQUENCE ORDERS_SEQ;

DROP SEQUENCE PAY_SEQ;

/* 회원 */
CREATE TABLE MEMBER (
	member_id NUMBER NOT NULL, /* 회원 ID */
	member_name VARCHAR2(50) NOT NULL, /* 회원이름 */
	member_email VARCHAR2(200) NOT NULL, /* 이메일 */
	member_phone_number VARCHAR2(50) NOT NULL, /* 휴대폰번호 */
	member_password VARCHAR2(200) NOT NULL, /* 비밀번호 */
	member_join_date DATE NOT NULL, /* 가입날짜 */
	member_join_state VARCHAR2(4) NOT NULL, /* 가입상태 */
	member_subscribe VARCHAR2(4) NOT NULL, /* 구독상태 */
	member_subscribe_date DATE, /* 구독날짜 */
	member_card VARCHAR2(4) NOT NULL /* 카드등록상태 */
);

CREATE UNIQUE INDEX PK_MEMBER
	ON MEMBER (
		member_id ASC
	);

ALTER TABLE MEMBER
	ADD
		CONSTRAINT PK_MEMBER
		PRIMARY KEY (
			member_id
		);

/* 권한 */
CREATE TABLE ROLE (
	member_id NUMBER NOT NULL, /* 회원 ID */
	role_name VARCHAR2(50) NOT NULL /* 권한이름 */
);

CREATE UNIQUE INDEX PK_ROLE
	ON ROLE (
		member_id ASC
	);

ALTER TABLE ROLE
	ADD
		CONSTRAINT PK_ROLE
		PRIMARY KEY (
			member_id
		);

/* 주소 */
CREATE TABLE ADDRESS (
	address_id NUMBER NOT NULL, /* 주소 번호 */
	address_zipcode VARCHAR2(10) NOT NULL, /* 배송주소 우편번호 */
	address_road VARCHAR2(50) NOT NULL, /* 배송주소 도로명 */
	address_content VARCHAR2(50) NOT NULL, /* 배송주소 상세 주소 */
	address_category VARCHAR2(4), /* 배송 구분 */
	address_detail VARCHAR2(150), /* 상세 설명 */
	member_id NUMBER NOT NULL /* 회원 ID */
);

CREATE UNIQUE INDEX PK_ADDRESS
	ON ADDRESS (
		address_id ASC
	);

ALTER TABLE ADDRESS
	ADD
		CONSTRAINT PK_ADDRESS
		PRIMARY KEY (
			address_id
		);

/* 주문 */
CREATE TABLE ORDERS (
	orders_id NUMBER NOT NULL, /* 주문 ID */
	wash_id NUMBER NOT NULL, /* 세탁 ID */
	orders_count NUMBER NOT NULL, /* 수량 */
	orders_price NUMBER NOT NULL, /* 금액 */
	orders_date DATE NOT NULL, /* 주문날짜 */
	orders_comment VARCHAR2(60), /* 요청사항 */
	orders_status VARCHAR2(30) NOT NULL, /* 진행상태 */
	member_id NUMBER NOT NULL, /* 회원 ID */
	laundry_id NUMBER /* 세탁물 ID */
);

CREATE UNIQUE INDEX PK_ORDERS
	ON ORDERS (
		orders_id ASC
	);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT PK_ORDERS
		PRIMARY KEY (
			orders_id
		);

/* 결제 */
CREATE TABLE PAY (
	pay_id NUMBER NOT NULL, /* 결제 ID */
	pay_delivery NUMBER NOT NULL, /* 배송비 */
	pay_money NUMBER NOT NULL, /* 결제 금액 */
	pay_date DATE NOT NULL, /* 결제 일자 */
	pay_state VARCHAR2(4) NOT NULL, /* 결제 상태 */
	orders_id NUMBER NOT NULL /* 주문 ID */
);

CREATE UNIQUE INDEX PK_PAY
	ON PAY (
		pay_id ASC
	);

ALTER TABLE PAY
	ADD
		CONSTRAINT PK_PAY
		PRIMARY KEY (
			pay_id
		);

/* 파일 */
CREATE TABLE FILES (
	files_id NUMBER NOT NULL, /* 파일 ID */
	files_oname VARCHAR2(200) NOT NULL, /* 원본이름 */
	files_nname VARCHAR2(200) NOT NULL, /* 저장이름 */
	files_path VARCHAR2(200) NOT NULL, /* 파일경로 */
	files_type VARCHAR2(50) NOT NULL, /* 파일타입 */
	orders_id NUMBER NOT NULL /* 주문 ID */
);

CREATE UNIQUE INDEX PK_FILES
	ON FILES (
		files_id ASC
	);

ALTER TABLE FILES
	ADD
		CONSTRAINT PK_FILES
		PRIMARY KEY (
			files_id
		);

/* 세탁물 */
CREATE TABLE LAUNDRY (
	laundry_id NUMBER NOT NULL, /* 세탁물 ID */
	laundry_name VARCHAR2(30) NOT NULL, /* 세탁물명 */
	laundry_category VARCHAR2(4) NOT NULL, /* 세탁물유형 */
	laundry_price NUMBER NOT NULL, /* 세탁물금액 */
	laundry_category_id NUMBER NOT NULL /* 상품 카테고리 ID */
);

CREATE UNIQUE INDEX PK_LAUNDRY
	ON LAUNDRY (
		laundry_id ASC
	);

ALTER TABLE LAUNDRY
	ADD
		CONSTRAINT PK_LAUNDRY
		PRIMARY KEY (
			laundry_id
		);

/* 세탁 카테고리 */
CREATE TABLE LAUNDRY_CATEGORY (
	laundry_category_id NUMBER NOT NULL, /* 상품 카테고리 ID */
	laundry_category_name VARCHAR2(30) NOT NULL, /* 상품 카테고리명 */
	laundry_category_description VARCHAR2(100) NOT NULL /* 상품 카테고리 설명 */
);

CREATE UNIQUE INDEX PK_LAUNDRY_CATEGORY
	ON LAUNDRY_CATEGORY (
		laundry_category_id ASC
	);

ALTER TABLE LAUNDRY_CATEGORY
	ADD
		CONSTRAINT PK_LAUNDRY_CATEGORY
		PRIMARY KEY (
			laundry_category_id
		);

ALTER TABLE ROLE
	ADD
		CONSTRAINT FK_MEMBER_TO_ROLE
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		)ON DELETE CASCADE;

ALTER TABLE ADDRESS
	ADD
		CONSTRAINT FK_MEMBER_TO_ADDRESS
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		)ON DELETE CASCADE;

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_MEMBER_TO_ORDERS
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		)ON DELETE CASCADE;

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_LAUNDRY_TO_ORDERS
		FOREIGN KEY (
			laundry_id
		)
		REFERENCES LAUNDRY (
			laundry_id
		)ON DELETE CASCADE;

ALTER TABLE PAY
	ADD
		CONSTRAINT FK_ORDERS_TO_PAY
		FOREIGN KEY (
			orders_id
		)
		REFERENCES ORDERS (
			orders_id
		);

ALTER TABLE FILES
	ADD
		CONSTRAINT FK_ORDERS_TO_FILES
		FOREIGN KEY (
			orders_id
		)
		REFERENCES ORDERS (
			orders_id
		)ON DELETE CASCADE;

ALTER TABLE LAUNDRY
	ADD
		CONSTRAINT FK_LAUNDRY_CATEGORY_TO_LAUNDRY
		FOREIGN KEY (
			laundry_category_id
		)
		REFERENCES LAUNDRY_CATEGORY (
			laundry_category_id
		)ON DELETE CASCADE;
        
CREATE SEQUENCE PAY_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
CREATE SEQUENCE ORDERS_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
CREATE SEQUENCE LAUNDRY_CATEGORY_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
CREATE SEQUENCE LAUNDRY_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
CREATE SEQUENCE FILES_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
COMMIT;

-- 세탁 카테고리 (LAUNDRY_CATEGORY) 테이블 예시 데이터
INSERT INTO LAUNDRY_CATEGORY (laundry_category_id, laundry_category_name, laundry_category_description)
VALUES (1, '상의', '상의 카테고리');

-- 세탁물 (LAUNDRY) 테이블 예시 데이터
INSERT INTO LAUNDRY (laundry_id, laundry_name, laundry_category, laundry_price, laundry_category_id)
VALUES (1, '셔츠', 1, 15000, 1);

-- 회원 (MEMBER) 테이블 예시 데이터
INSERT INTO MEMBER (member_id, member_name, member_email, member_phone_number, member_password, member_join_date, member_join_state, member_subscribe, member_subscribe_date, member_card)
VALUES (1, '김예시', 'example@example.com', '010-1234-5678', 'password123', TO_DATE('2023-01-15', 'YYYY-MM-DD'), '1', '1', TO_DATE('2023-01-15', 'YYYY-MM-DD'), '1');

-- 권한 (ROLE) 테이블 예시 데이터
INSERT INTO ROLE (member_id, role_name)
VALUES (1, '일반회원');

-- 주소 (ADDRESS) 테이블 예시 데이터
INSERT INTO ADDRESS (address_id, address_zipcode, address_road, address_content, address_category, address_detail, member_id)
VALUES (1, '12345', '예시로드 1번길', '예시 아파트 101호', '집', '김예시 집', 1);

-- 주문 (ORDERS) 테이블 예시 데이터
INSERT INTO ORDERS (orders_id, wash_id, orders_count, orders_price, orders_date, orders_comment, orders_status, member_id, laundry_id)
VALUES (1, 1, 2, 30000, TO_DATE('2023-01-20', 'YYYY-MM-DD'), '부드러운 세탁 부탁드려요', '1', 1, 1);

-- 결제 (PAY) 테이블 예시 데이터
INSERT INTO PAY (pay_id, pay_delivery, pay_money, pay_date, pay_state, orders_id)
VALUES (1, 2500, 32500, TO_DATE('2023-01-21', 'YYYY-MM-DD'), '1', 1);

-- 파일 (FILES) 테이블 예시 데이터
INSERT INTO FILES (files_id, files_oname, files_nname, files_path, files_type, orders_id)
VALUES (1, '세탁전 사진', 'before.jpg', '/path/to/before.jpg', 'jpg', 1);

COMMIT;