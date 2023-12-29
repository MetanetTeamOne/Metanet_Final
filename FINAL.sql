/* 회원 */
DROP TABLE MEMBER 
	CASCADE CONSTRAINTS;

/* 카드정보 */
DROP TABLE CREDITCARD 
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

/* 배송 */
DROP TABLE DELIVERY 
	CASCADE CONSTRAINTS;

/* 파일 */
DROP TABLE FILES 
	CASCADE CONSTRAINTS;

/* 세탁-주문 */
DROP TABLE LAUNDRY_ORDER 
	CASCADE CONSTRAINTS;

/* 세탁물 */
DROP TABLE LAUNDRY 
	CASCADE CONSTRAINTS;

/* 세탁 카테고리 */
DROP TABLE LAUNDRY_CATEGORY 
	CASCADE CONSTRAINTS;

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

/* 카드정보 */
CREATE TABLE CREDITCARD (
	card_number VARCHAR2(30) NOT NULL, /* 카드번호 */
	card_content VARCHAR2(50), /* 카드설명 */
	card_company VARCHAR2(50) NOT NULL, /* 카드사 */
	card_expiredate VARCHAR2(10) NOT NULL, /* 유효기간 */
	member_id NUMBER /* 회원 ID */
);

CREATE UNIQUE INDEX PK_CREDITCARD
	ON CREDITCARD (
		card_number ASC
	);

ALTER TABLE CREDITCARD
	ADD
		CONSTRAINT PK_CREDITCARD
		PRIMARY KEY (
			card_number
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

/* 배송 */
CREATE TABLE DELIVERY (
	delivery_id NUMBER NOT NULL, /* 배송 ID */
	pickup_address VARCHAR2(50) NOT NULL, /* 수거주소 */
	delivery_status VARCHAR2(50) NOT NULL, /* 배송주소 */
	delivery_pickup_date DATE NOT NULL, /* 수거일정 */
	delivery_date DATE NOT NULL, /* 배송일정 */
	delivery_comment VARCHAR2(50), /* 배송요청 */
	pay_id NUMBER NOT NULL /* 결제 ID */
);

CREATE UNIQUE INDEX PK_DELIVERY
	ON DELIVERY (
		delivery_id ASC
	);

ALTER TABLE DELIVERY
	ADD
		CONSTRAINT PK_DELIVERY
		PRIMARY KEY (
			delivery_id
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

/* 세탁-주문 */
CREATE TABLE LAUNDRY_ORDER (
	laundry_order_id NUMBER NOT NULL, /* 세탁주문 ID */
	laundry_order_count NUMBER, /* 주문수량 */
	laundry_order_price NUMBER, /* 주문가격 */
	laundry_id NUMBER /* 세탁물 ID */
);

CREATE UNIQUE INDEX PK_LAUNDRY_ORDER
	ON LAUNDRY_ORDER (
		laundry_order_id ASC
	);

ALTER TABLE LAUNDRY_ORDER
	ADD
		CONSTRAINT PK_LAUNDRY_ORDER
		PRIMARY KEY (
			laundry_order_id
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

ALTER TABLE CREDITCARD
	ADD
		CONSTRAINT FK_MEMBER_TO_CREDITCARD
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		);

ALTER TABLE ROLE
	ADD
		CONSTRAINT FK_MEMBER_TO_ROLE
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		);

ALTER TABLE ADDRESS
	ADD
		CONSTRAINT FK_MEMBER_TO_ADDRESS
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_MEMBER_TO_ORDERS
		FOREIGN KEY (
			member_id
		)
		REFERENCES MEMBER (
			member_id
		);

ALTER TABLE ORDERS
	ADD
		CONSTRAINT FK_LAUNDRY_TO_ORDERS
		FOREIGN KEY (
			laundry_id
		)
		REFERENCES LAUNDRY (
			laundry_id
		);

ALTER TABLE PAY
	ADD
		CONSTRAINT FK_ORDERS_TO_PAY
		FOREIGN KEY (
			orders_id
		)
		REFERENCES ORDERS (
			orders_id
		);

ALTER TABLE DELIVERY
	ADD
		CONSTRAINT FK_PAY_TO_DELIVERY
		FOREIGN KEY (
			pay_id
		)
		REFERENCES PAY (
			pay_id
		);

ALTER TABLE FILES
	ADD
		CONSTRAINT FK_ORDERS_TO_FILES
		FOREIGN KEY (
			orders_id
		)
		REFERENCES ORDERS (
			orders_id
		);

ALTER TABLE LAUNDRY_ORDER
	ADD
		CONSTRAINT FK_LAUNDRY_TO_LAUNDRY_ORDER
		FOREIGN KEY (
			laundry_id
		)
		REFERENCES LAUNDRY (
			laundry_id
		);

ALTER TABLE LAUNDRY
	ADD
		CONSTRAINT FK_LAUNDRY_CATEGORY_TO_LAUNDRY
		FOREIGN KEY (
			laundry_category_id
		)
		REFERENCES LAUNDRY_CATEGORY (
			laundry_category_id
		);