/* ȸ�� */
DROP TABLE MEMBER 
	CASCADE CONSTRAINTS;

/* ���� */
DROP TABLE ROLE 
	CASCADE CONSTRAINTS;

/* �ּ� */
DROP TABLE ADDRESS 
	CASCADE CONSTRAINTS;

/* �ֹ� */
DROP TABLE ORDERS 
	CASCADE CONSTRAINTS;

/* ���� */
DROP TABLE PAY 
	CASCADE CONSTRAINTS;

/* ���� */
DROP TABLE FILES 
	CASCADE CONSTRAINTS;

/* ��Ź�� */
DROP TABLE LAUNDRY 
	CASCADE CONSTRAINTS;

/* ��Ź ī�װ� */
DROP TABLE LAUNDRY_CATEGORY 
	CASCADE CONSTRAINTS;
    
DROP SEQUENCE FILES_SEQ;

DROP SEQUENCE LAUNDRY_SEQ;

DROP SEQUENCE LAUNDRY_CATEGORY_SEQ;

DROP SEQUENCE ORDERS_SEQ;

DROP SEQUENCE PAY_SEQ;

/* ȸ�� */
CREATE TABLE MEMBER (
	member_id NUMBER NOT NULL, /* ȸ�� ID */
	member_name VARCHAR2(50) NOT NULL, /* ȸ���̸� */
	member_email VARCHAR2(200) NOT NULL, /* �̸��� */
	member_phone_number VARCHAR2(50) NOT NULL, /* �޴�����ȣ */
	member_password VARCHAR2(200) NOT NULL, /* ��й�ȣ */
	member_join_date DATE NOT NULL, /* ���Գ�¥ */
	member_join_state VARCHAR2(4) NOT NULL, /* ���Ի��� */
	member_subscribe VARCHAR2(4) NOT NULL, /* �������� */
	member_subscribe_date DATE, /* ������¥ */
	member_card VARCHAR2(4) NOT NULL /* ī���ϻ��� */
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

/* ���� */
CREATE TABLE ROLE (
	member_id NUMBER NOT NULL, /* ȸ�� ID */
	role_name VARCHAR2(50) NOT NULL /* �����̸� */
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

/* �ּ� */
CREATE TABLE ADDRESS (
	address_id NUMBER NOT NULL, /* �ּ� ��ȣ */
	address_zipcode VARCHAR2(10) NOT NULL, /* ����ּ� �����ȣ */
	address_road VARCHAR2(50) NOT NULL, /* ����ּ� ���θ� */
	address_content VARCHAR2(50) NOT NULL, /* ����ּ� �� �ּ� */
	address_category VARCHAR2(4), /* ��� ���� */
	address_detail VARCHAR2(150), /* �� ���� */
	member_id NUMBER NOT NULL /* ȸ�� ID */
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

/* �ֹ� */
CREATE TABLE ORDERS (
	orders_id NUMBER NOT NULL, /* �ֹ� ID */
	wash_id NUMBER NOT NULL, /* ��Ź ID */
	orders_count NUMBER NOT NULL, /* ���� */
	orders_price NUMBER NOT NULL, /* �ݾ� */
	orders_date DATE NOT NULL, /* �ֹ���¥ */
	orders_comment VARCHAR2(60), /* ��û���� */
	orders_status VARCHAR2(30) NOT NULL, /* ������� */
	member_id NUMBER NOT NULL, /* ȸ�� ID */
	laundry_id NUMBER /* ��Ź�� ID */
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

/* ���� */
CREATE TABLE PAY (
	pay_id NUMBER NOT NULL, /* ���� ID */
	pay_delivery NUMBER NOT NULL, /* ��ۺ� */
	pay_money NUMBER NOT NULL, /* ���� �ݾ� */
	pay_date DATE NOT NULL, /* ���� ���� */
	pay_state VARCHAR2(4) NOT NULL, /* ���� ���� */
	orders_id NUMBER NOT NULL /* �ֹ� ID */
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

/* ���� */
CREATE TABLE FILES (
	files_id NUMBER NOT NULL, /* ���� ID */
	files_oname VARCHAR2(200) NOT NULL, /* �����̸� */
	files_nname VARCHAR2(200) NOT NULL, /* �����̸� */
	files_path VARCHAR2(200) NOT NULL, /* ���ϰ�� */
	files_type VARCHAR2(50) NOT NULL, /* ����Ÿ�� */
	orders_id NUMBER NOT NULL /* �ֹ� ID */
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

/* ��Ź�� */
CREATE TABLE LAUNDRY (
	laundry_id NUMBER NOT NULL, /* ��Ź�� ID */
	laundry_name VARCHAR2(30) NOT NULL, /* ��Ź���� */
	laundry_category VARCHAR2(4) NOT NULL, /* ��Ź������ */
	laundry_price NUMBER NOT NULL, /* ��Ź���ݾ� */
	laundry_category_id NUMBER NOT NULL /* ��ǰ ī�װ� ID */
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

/* ��Ź ī�װ� */
CREATE TABLE LAUNDRY_CATEGORY (
	laundry_category_id NUMBER NOT NULL, /* ��ǰ ī�װ� ID */
	laundry_category_name VARCHAR2(30) NOT NULL, /* ��ǰ ī�װ��� */
	laundry_category_description VARCHAR2(100) NOT NULL /* ��ǰ ī�װ� ���� */
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

-- ��Ź ī�װ� (LAUNDRY_CATEGORY) ���̺� ���� ������
INSERT INTO LAUNDRY_CATEGORY (laundry_category_id, laundry_category_name, laundry_category_description)
VALUES (1, '����', '���� ī�װ�');

-- ��Ź�� (LAUNDRY) ���̺� ���� ������
INSERT INTO LAUNDRY (laundry_id, laundry_name, laundry_category, laundry_price, laundry_category_id)
VALUES (1, '����', 1, 15000, 1);

-- ȸ�� (MEMBER) ���̺� ���� ������
INSERT INTO MEMBER (member_id, member_name, member_email, member_phone_number, member_password, member_join_date, member_join_state, member_subscribe, member_subscribe_date, member_card)
VALUES (1, '�迹��', 'example@example.com', '010-1234-5678', 'password123', TO_DATE('2023-01-15', 'YYYY-MM-DD'), '1', '1', TO_DATE('2023-01-15', 'YYYY-MM-DD'), '1');

-- ���� (ROLE) ���̺� ���� ������
INSERT INTO ROLE (member_id, role_name)
VALUES (1, '�Ϲ�ȸ��');

-- �ּ� (ADDRESS) ���̺� ���� ������
INSERT INTO ADDRESS (address_id, address_zipcode, address_road, address_content, address_category, address_detail, member_id)
VALUES (1, '12345', '���÷ε� 1����', '���� ����Ʈ 101ȣ', '��', '�迹�� ��', 1);

-- �ֹ� (ORDERS) ���̺� ���� ������
INSERT INTO ORDERS (orders_id, wash_id, orders_count, orders_price, orders_date, orders_comment, orders_status, member_id, laundry_id)
VALUES (1, 1, 2, 30000, TO_DATE('2023-01-20', 'YYYY-MM-DD'), '�ε巯�� ��Ź ��Ź�����', '1', 1, 1);

-- ���� (PAY) ���̺� ���� ������
INSERT INTO PAY (pay_id, pay_delivery, pay_money, pay_date, pay_state, orders_id)
VALUES (1, 2500, 32500, TO_DATE('2023-01-21', 'YYYY-MM-DD'), '1', 1);

-- ���� (FILES) ���̺� ���� ������
INSERT INTO FILES (files_id, files_oname, files_nname, files_path, files_type, orders_id)
VALUES (1, '��Ź�� ����', 'before.jpg', '/path/to/before.jpg', 'jpg', 1);

COMMIT;