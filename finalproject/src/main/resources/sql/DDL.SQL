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
    
/* 문의 */
DROP TABLE MEMHELP 
   CASCADE CONSTRAINTS;

/* HELPCATEGORY */
DROP TABLE HelpCategory 
   CASCADE CONSTRAINTS;

/* 답변 */
DROP TABLE REPLY 
   CASCADE CONSTRAINTS;
    
DROP SEQUENCE FILES_SEQ;

DROP SEQUENCE LAUNDRY_SEQ;

DROP SEQUENCE LAUNDRY_CATEGORY_SEQ;

DROP SEQUENCE ORDERS_SEQ;

DROP SEQUENCE PAY_SEQ;

DROP SEQUENCE MEMHELP_SEQ;

DROP SEQUENCE REPLY_SEQ;

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

ALTER TABLE MEMBER
ADD CONSTRAINT unique_phone_number UNIQUE (member_phone_number);

ALTER TABLE MEMBER
ADD CONSTRAINT unique_member_email UNIQUE (member_email);

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
   address_road VARCHAR2(100) NOT NULL, /* 배송주소 도로명 */
   address_content VARCHAR2(100) NOT NULL, /* 배송주소 상세 주소 */
   address_category VARCHAR2(4), /* 배송 구분 **/
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

ALTER TABLE ORDERS
ADD ORDERS_IMAGE_DATA BLOB;

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
   orders_id NUMBER NOT NULL, /* 주문 ID */
   member_id number NOT NULL /* 회원 ID */
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

ALTER TABLE PAY
    ADD 
        CONSTRAINT FK_MEMBER_TO_PAY
        FOREIGN KEY (
            member_id
        ) 
        REFERENCES MEMBER (
        member_id
        );

/* 파일 */
CREATE TABLE FILES (
   files_id NUMBER NOT NULL, /* 파일 ID */
   files_oname VARCHAR2(200) NOT NULL, /* 원본이름 */
   files_nname VARCHAR2(200) NOT NULL, /* 저장이름 */
   files_path VARCHAR2(200) NOT NULL, /* 파일경로 */
   files_type VARCHAR2(50) NOT NULL, /* 파일타입 */
    files_data BLOB NOT NULL,
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
        
/* 문의 */
CREATE TABLE MEMHELP (
   memHelpNum NUMBER NOT NULL, /* 문의번호 */
   memHelpTitle VARCHAR2(100) NOT NULL, /* 문의제목 */
   memHelpContent VARCHAR2(500) NOT NULL, /* 문의내용 */
   memHelpFile BLOB, /* 첨부파일 */
   memHelpState VARCHAR2(10) NOT NULL, /* 문의상태 */
   memHelpDate DATE NOT NULL, /* 문의날짜 */
   member_id NUMBER, /* 회원 ID */
   memCategoryNum NUMBER /* 문의유형번호 */
);

CREATE UNIQUE INDEX PK_MEMHELP
   ON MEMHELP (
      memHelpNum ASC
   );

ALTER TABLE MEMHELP
   ADD
      CONSTRAINT PK_MEMHELP
      PRIMARY KEY (
         memHelpNum
      );

/* HELPCATEGORY */
CREATE TABLE HelpCategory (
   memCategoryNum NUMBER NOT NULL, /* 문의유형번호 */
   memHelpCategory VARCHAR2(30) NOT NULL /* 문의유형 */
);

CREATE UNIQUE INDEX PK_HelpCategory
   ON HelpCategory (
      memCategoryNum ASC
   );

ALTER TABLE HelpCategory
   ADD
      CONSTRAINT PK_HelpCategory
      PRIMARY KEY (
         memCategoryNum
      );

/* 답변 */
CREATE TABLE REPLY (
   repNum NUMBER NOT NULL, /* 답변번호 */
   repContent VARCHAR2(50) NOT NULL, /* 답변내용 */
   repDate DATE NOT NULL, /* 답변일자 */
   memHelpNum NUMBER /* 문의번호 */
);

CREATE UNIQUE INDEX PK_REPLY
   ON REPLY (
      repNum ASC
   );

ALTER TABLE REPLY
   ADD
      CONSTRAINT PK_REPLY
      PRIMARY KEY (
         repNum
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
      ) ON DELETE CASCADE;

ALTER TABLE PAY
   ADD
      CONSTRAINT FK_ORDERS_TO_PAY
      FOREIGN KEY (
         orders_id
      )
      REFERENCES ORDERS (
         orders_id
      )
      ON DELETE CASCADE;

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
        
ALTER TABLE MEMHELP
   ADD
      CONSTRAINT FK_MEMBER_TO_MEMHELP
      FOREIGN KEY (
         member_id
      )
      REFERENCES MEMBER (
         member_id
      )ON DELETE CASCADE;

ALTER TABLE MEMHELP
   ADD
      CONSTRAINT FK_HelpCategory_TO_MEMHELP
      FOREIGN KEY (
         memCategoryNum
      )
      REFERENCES HelpCategory (
         memCategoryNum
      )ON DELETE CASCADE;

ALTER TABLE REPLY
   ADD
      CONSTRAINT FK_MEMHELP_TO_REPLY
      FOREIGN KEY (
         memHelpNum
      )
      REFERENCES MEMHELP (
         memHelpNum
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
       
       
CREATE SEQUENCE MEMHELP_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;
       
       
CREATE SEQUENCE REPLY_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;

COMMIT;

-- 회원 (MEMBER) 테이블 예시 데이터
INSERT INTO MEMBER (member_id, member_name, member_email, member_phone_number, member_password, member_join_date, member_join_state, member_subscribe, member_subscribe_date, member_card)
VALUES (2, '최가인', 'chlrkdls1269@gmail.com', '010-1234-5678', 'password123', TO_DATE('2023-01-15', 'YYYY-MM-DD'), '1', '1', TO_DATE('2023-01-17', 'YYYY-MM-DD'), '1');

insert into member (member_id, member_name, member_email, member_phone_number, member_password, member_join_date, member_join_state, member_subscribe, member_subscribe_date, member_card)
values (1, 'admin', 'admin@admin', 'admin', 'admin123', sysdate, '1', '1', sysdate, '1');

-- 권한 (ROLE) 테이블 예시 데이터
INSERT INTO ROLE (member_id, role_name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO ROLE (member_id, role_name)
VALUES (2, 'ROLE_USER');

-- 주소 (ADDRESS) 테이블 예시 데이터
INSERT INTO ADDRESS (address_id, address_zipcode, address_road, address_content, address_category, address_detail, member_id)
VALUES (1, '03077', '창경궁로 254', '한성대학교 에듀센터 7층', '3', '최가인 교육장', 1);

-- 세탁 카테고리 테이블 예시 데이터
insert into laundry_category values (LAUNDRY_CATEGORY_SEQ.NEXTVAL, '상의', '정장상의, 셔츠, 블라우스, 맨투맨, 후드티, 코트, 패딩 등');
insert into laundry_category values (LAUNDRY_CATEGORY_SEQ.NEXTVAL, '하의', '정장하의, 반바지, 바지, 스커트 등');
insert into laundry_category values (LAUNDRY_CATEGORY_SEQ.NEXTVAL, '신발', '일반 운동화, 가죽 운동화, 골프화, 등산화 등');
insert into laundry_category values (LAUNDRY_CATEGORY_SEQ.NEXTVAL, '기타', '모자, 넥타이, 목도리 등');

-- 세탁물 테이블 예시 데이터
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '셔츠', 2, 2500, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '블라우스', 2, 4900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '맨투맨', 1, 5900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '후드티', 1, 5900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '조끼', 2, 4900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '원피스', 2, 7900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '정장상의', 2, 2500, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '가디건', 2, 7900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '코트', 2, 13900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '패딩', 2, 15900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '가죽자켓', 2, 17900, 1);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '정장하의', 2, 4900, 2);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '반바지', 1, 3900, 2);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '바지', 1, 2500, 2);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '스커트', 1, 5900, 2);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '운동화', 1, 5900, 3);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '캔버스', 1, 5900, 3);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '골프화', 2, 11900, 3);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '등산화', 2, 11900, 3);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '넥타이', 1, 2900, 4);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '모자', 1, 5900, 4);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '목도리', 2, 6900, 4);
insert into laundry values (LAUNDRY_SEQ.NEXTVAL, '스카프', 2, 6900, 4);


-- 주문 (ORDERS) 테이블 예시 데이터
insert into orders values(ORDERS_SEQ.NEXTVAL, 1, 3, 5000, sysdate, '깨끗하게 빨아주세요', '1', 1, 1, EMPTY_BLOB());

-- 결제 (PAY) 테이블 예시 데이터
INSERT INTO PAY (pay_id, pay_delivery, pay_money, pay_date, pay_state, orders_id, member_id)
VALUES (1, 2500, 32500, TO_DATE('2023-01-21', 'YYYY-MM-DD'), '1', 1, 1);

INSERT INTO PAY (pay_id, pay_delivery, pay_money, pay_date, pay_state, orders_id, member_id)
VALUES (2, 2500, 32500, TO_DATE('2023-01-21', 'YYYY-MM-DD'), '1', 1, 1);

insert into HELPCATEGORY values (1, '회원정보');
insert into HELPCATEGORY values (2, '설정/보안/인증');
insert into HELPCATEGORY values (3, '주문/결제');
insert into HELPCATEGORY values (4, '취소/환불');
insert into HELPCATEGORY values (5, '배송');
insert into HELPCATEGORY values (6, '영수증');
insert into HELPCATEGORY values (7, '고객의소리/기타');
insert into HELPCATEGORY values (8, '세탁정보');

desc memhelp;

insert into MEMHELP values(MEMHELP_SEQ.NEXTVAL, '세탁이 잘 안됐어여', '세탁물을 수령했는데 오염이 남아있네요', EMPTY_BLOB(), '1', SYSDATE, 2, 7);
insert into MEMHELP values(MEMHELP_SEQ.NEXTVAL, '영수증을 발급받고 싶어요', '저번주에 세탁을 맡겼는데 영수증을 발급받고싶습니다', EMPTY_BLOB(), '1', SYSDATE, 2, 6);

select * from MEMHELP where member_id = 2 order by memhelpnum desc;

SELECT    memHelpNum, memHelpTitle,  memHelpDate,  memCategoryNum   FROM    MEMHELP   WHERE     MEMBER_ID = 2   ORDER BY    MEMHELPNUM DESC;

COMMIT;