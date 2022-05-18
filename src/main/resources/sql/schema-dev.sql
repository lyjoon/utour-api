
DROP TABLE IF EXISTS `USER` CASCADE;
CREATE TABLE `USER` (
                        USER_ID   VARCHAR(50) NOT NULL COMMENT '사용자(로그인)ID',
                        USE_YN    CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                        PASSWORD  VARCHAR(500) COMMENT '로그인 비밀번호',
                        NAME VARCHAR(10) COMMENT '이름',
                        CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                        UPDATE_AT DATETIME COMMENT '변경일자',
                        CONSTRAINT PK_USER PRIMARY KEY (USER_ID)
);

DROP TABLE IF EXISTS CODE_GROUP CASCADE;
CREATE TABLE CODE_GROUP (
                            GROUP_CODE VARCHAR(20) NOT NULL COMMENT '그룹코드',
                            GROUP_NAME VARCHAR(50) COMMENT '그룹코드이름',
                            USE_YN    CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                            CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                            UPDATE_AT DATETIME COMMENT '변경일자',
                            CONSTRAINT PK_CODE_GROUP PRIMARY KEY (GROUP_CODE)
);

DROP TABLE IF EXISTS CODE CASCADE;
CREATE TABLE CODE (
                      GROUP_CODE   VARCHAR(20) NOT NULL COMMENT '그룹코드',
                      CODE   VARCHAR(20) NOT NULL COMMENT '공통코드',
                      CODE_NAME VARCHAR(50) NOT NULL COMMENT '공통코드값',
                      DESCRIPTION VARCHAR(500) COMMENT '비고',
                      CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                      UPDATE_AT DATETIME COMMENT '변경일자',
                      CONSTRAINT PK_CODE PRIMARY KEY (GROUP_CODE, CODE),
                      CONSTRAINT FK_CODE FOREIGN KEY (GROUP_CODE) REFERENCES CODE_GROUP (GROUP_CODE)
);

DROP TABLE IF EXISTS NATION;
CREATE TABLE NATION (
                        NATION_CODE CHAR(2) NOT NULL COMMENT '국가코드(2-ALPHA)',
                        NATION_NAME VARCHAR(50) COMMENT '국가명',
                        USE_YN CHAR(1) DEFAULT 'N' COMMENT '사용유무',
                        CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                        UPDATE_AT DATETIME COMMENT '변경일자',
                        CONSTRAINT PK_NATION PRIMARY KEY (NATION_CODE)
);

DROP TABLE IF EXISTS AREA;
CREATE TABLE NATION_AREA (
                             NATION_CODE CHAR(2) NOT NULL COMMENT '국가코드(2-ALPHA)',
                             AREA_CODE VARCHAR(5) NOT NULL COMMENT '지역코드',
                             AREA_NAME VARCHAR(50) COMMENT '지역명',
                             USE_YN CHAR(1) DEFAULT 'N' COMMENT '사용유무',
                             CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                             UPDATE_AT DATETIME COMMENT '변경일자',
                             CONSTRAINT PK_NATION_AREA PRIMARY KEY (NATION_CODE, AREA_CODE),
                             CONSTRAINT FK_NATION_AREA FOREIGN KEY (NATION_CODE) REFERENCES NATION (NATION_CODE)
);


DROP TABLE IF EXISTS NOTICE CASCADE;
CREATE TABLE NOTICE (
                        NOTICE_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '공지사항 ID',
                        TITLE VARCHAR(50) COMMENT '제목',
                        CONTENT LONGTEXT COMMENT '내용',
                        WRITER VARCHAR(20) COMMENT '작성자',
                        NOTICE_YN CHAR(1) DEFAULT 'N' COMMENT '공지등록여부',
                        PV INT DEFAULT 0 COMMENT '페이지뷰',
                        CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                        UPDATE_AT DATETIME COMMENT '변경일자',
                        CONSTRAINT PK_NOTICE PRIMARY KEY (NOTICE_ID)
);

DROP TABLE IF EXISTS NOTICE_ATTACH CASCADE;
CREATE TABLE NOTICE_ATTACH (
                               ATTACH_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '첨부파일 ID',
                               NOTICE_ID BIGINT NOT NULL COMMENT '공지사항 ID',
                               PATH VARCHAR(2000) COMMENT '파일저장경로',
                               SIZE BIGINT COMMENT '파일크기',
                               ORIGIN_NAME VARCHAR(100) COMMENT '파일원본명',
                               CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                               UPDATE_AT DATETIME COMMENT '변경일자',
                               CONSTRAINT PK_NOTICE_ATTACH PRIMARY KEY (ATTACH_ID),
                               CONSTRAINT FK_NOTICE_ATTACH FOREIGN KEY (NOTICE_ID) REFERENCES NOTICE (NOTICE_ID)
);


DROP TABLE IF EXISTS QNA CASCADE;
CREATE TABLE QNA (
                     QNA_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '질문과 답변 ID',
                     TITLE VARCHAR(50) COMMENT '제목',
                     CONTENT LONGTEXT COMMENT '내용',
                     WRITER VARCHAR(20) COMMENT '작성자',
                     PASSWORD VARCHAR(500) COMMENT '비밀번호',
                     PRIVATE_YN CHAR(1) DEFAULT 'N' COMMENT '비공개유무',
                     PV INT DEFAULT 0 COMMENT '페이지뷰',
                     CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                     UPDATE_AT DATETIME COMMENT '변경일자',
                     CONSTRAINT PK_QNA PRIMARY KEY(QNA_ID)
);

DROP TABLE IF EXISTS QNA_REPLY CASCADE;
CREATE TABLE QNA_REPLY (
                           QNA_REPLY_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '질문과 답변 댓글 ID',
                           QNA_ID BIGINT NOT NULL COMMENT '질문과 답변 ID',
                           WRITER VARCHAR(20) COMMENT '작성자',
                           CONTENT VARCHAR(4000) COMMENT '내용',
                           PASSWORD VARCHAR(500) COMMENT '비밀번호',
                           PRIVATE_YN CHAR(1) DEFAULT 'N'  COMMENT '비공개유무',
                           ADMIN_YN CHAR(1) DEFAULT 'N' COMMENT '관리자댓글유무',
                           CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                           UPDATE_AT DATETIME COMMENT '변경일자',
                           CONSTRAINT PK_QNA_REPLY PRIMARY KEY (QNA_REPLY_ID),
                           CONSTRAINT FK_QNA_REPLY FOREIGN KEY (QNA_ID) REFERENCES QNA (QNA_ID)
);


DROP TABLE IF EXISTS INQUIRY CASCADE;
CREATE TABLE INQUIRY (
                         INQUIRY_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '문의ID',
                         WRITER VARCHAR(20) COMMENT '작성자',
                         TITLE VARCHAR(50) COMMENT '제목',
                         CONTENT LONGTEXT COMMENT '내용',
                         STATUS VARCHAR(10) COMMENT '문의상태',
                         CONTACT VARCHAR(500) COMMENT '연락처(ENCRYPT)',
                         EMAIL VARCHAR(100) COMMENT '이메일',
                         CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                         UPDATE_AT DATETIME COMMENT '변경일자',
                         CONSTRAINT PK_INQUIRY PRIMARY KEY (INQUIRY_ID)
);

DROP TABLE IF EXISTS PRODUCT CASCADE;
CREATE TABLE PRODUCT (
                         PRODUCT_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품ID',
                         PRODUCT_TYPE VARCHAR(10) COMMENT '상품유형',
                         NATION_CODE CHAR(2) COMMENT '국가코드',
                         AREA_CODE VARCHAR(5) COMMENT '지역코드',
                         TITLE VARCHAR(50) COMMENT '상품명',
                         CONTENT LONGTEXT COMMENT '상품설명',
                         WRITER VARCHAR(20) COMMENT '작성자',
                         REP_IMAGE_SRC VARCHAR(2000) COMMENT '대표이미지경로',
                         USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                         CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                         UPDATE_AT DATETIME COMMENT '변경일자',
                         CONSTRAINT PK_PRODUCT PRIMARY KEY (PRODUCT_ID),
                         KEY PRODUCT_IDX01 (PRODUCT_ID, PRODUCT_TYPE),
                         KEY PRODUCT_IDX02 (NATION_CODE, AREA_CODE)
);

DROP TABLE IF EXISTS PRODUCT_IMAGE_GROUP;
CREATE TABLE PRODUCT_IMAGE_GROUP (
                                     PRODUCT_IMAGE_GROUP_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 이미지그룹 ID',
                                     PRODUCT_ID BIGINT NOT NULL COMMENT '상품 ID',
                                     GROUP_NAME VARCHAR(100) COMMENT '그룹명',
                                     USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                                     CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                     UPDATE_AT DATETIME COMMENT '변경일자',
                                     CONSTRAINT PK_PRODUCT_IMAGE_GROUP PRIMARY KEY (PRODUCT_IMAGE_GROUP_ID),
                                     CONSTRAINT FK_PRODUCT_IMAGE_GROUP FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);

DROP TABLE IF EXISTS PRODUCT_IMAGE;
CREATE TABLE PRODUCT_IMAGE (
                               PRODUCT_IMAGE_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 이미지 ID',
                               PRODUCT_ID BIGINT NOT NULL COMMENT '상품 ID',
                               PRODUCT_IMAGE_GROUP_ID BIGINT COMMENT '상품 이미지그룹 ID',
                               IMAGE_SRC VARCHAR(2000) COMMENT '이미지경로',
                               DESCRIPTION VARCHAR(500) COMMENT '설명(비고)',
                               CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                               UPDATE_AT DATETIME COMMENT '변경일자',
                               CONSTRAINT PK_PRODUCT_IMAGE PRIMARY KEY (PRODUCT_IMAGE_ID),
                               CONSTRAINT FK_PRODUCT_IMAGE_0 FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID),
                               CONSTRAINT FK_PRODUCT_IMAGE_1 FOREIGN KEY (PRODUCT_IMAGE_GROUP_ID) REFERENCES PRODUCT_IMAGE_GROUP (PRODUCT_IMAGE_GROUP_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT;
CREATE TABLE VIEW_COMPONENT (
                                VIEW_COMPONENT_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '화면요소 ID',
                                VIEW_COMPONENT_TYPE VARCHAR(20) COMMENT '화면요소 유형',
                                PRODUCT_ID BIGINT COMMENT '상품 ID',
                                TITLE VARCHAR(50) COMMENT '제목',
                                DESCRIPTION VARCHAR(500) COMMENT '설명(비고)',
                                ORDINAL INT COMMENT '순서',
                                USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                                CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                UPDATE_AT DATETIME COMMENT '변경일자',
                                CONSTRAINT PK_VIEW_COMPONENT PRIMARY KEY (VIEW_COMPONENT_ID),
                                CONSTRAINT FK_VIEW_COMPONENT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT_TEXT;
CREATE TABLE VIEW_COMPONENT_TEXT (
                                     VIEW_COMPONENT_ID BIGINT NOT NULL COMMENT '화면요소 ID',
                                     CONTENT LONGTEXT COMMENT '내용',
                                     CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                     UPDATE_AT DATETIME COMMENT '변경일자',
                                     CONSTRAINT PK_VIEW_COMPONENT_TEXT PRIMARY KEY (VIEW_COMPONENT_ID),
                                     CONSTRAINT FK_VIEW_COMPONENT_TEXT FOREIGN KEY (VIEW_COMPONENT_ID) REFERENCES VIEW_COMPONENT (VIEW_COMPONENT_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT_IMAGE;
CREATE TABLE VIEW_COMPONENT_IMAGE (
                                      VIEW_COMPONENT_ID BIGINT NOT NULL COMMENT '화면요소 ID',
                                      DISPLAY_TYPE VARCHAR(10) COMMENT '노출유형(케로셀, 목록)',
                                      CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                      UPDATE_AT DATETIME COMMENT '변경일자',
                                      CONSTRAINT PK_VIEW_COMPONENT_IMAGE PRIMARY KEY (VIEW_COMPONENT_ID),
                                      CONSTRAINT FK_VIEW_COMPONENT_IMAGE FOREIGN KEY (VIEW_COMPONENT_ID) REFERENCES VIEW_COMPONENT (VIEW_COMPONENT_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT_IMAGE_SET;
CREATE TABLE VIEW_COMPONENT_IMAGE_SET (
                                          VIEW_COMPONENT_ID BIGINT NOT NULL COMMENT '화면요소 ID',
                                          VIEW_COMPONENT_SEQ INT NOT NULL COMMENT '이미지 구성요소 순번',
                                          IMAGE_SRC VARCHAR(4000) COMMENT '이미지 경로',
                                          TITLE VARCHAR(50) COMMENT '제목',
                                          DESCRIPTION VARCHAR(500) COMMENT '설명(비고)',
                                          CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                          UPDATE_AT DATETIME COMMENT '변경일자',
                                          CONSTRAINT PK_VIEW_COMPONENT_IMAGE_SET PRIMARY KEY (VIEW_COMPONENT_ID, VIEW_COMPONENT_SEQ),
                                          CONSTRAINT FK_VIEW_COMPONENT_IMAGE_SET FOREIGN KEY (VIEW_COMPONENT_ID) REFERENCES VIEW_COMPONENT_IMAGE (VIEW_COMPONENT_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT_ACCOMMODATION;
CREATE TABLE VIEW_COMPONENT_ACCOMMODATION (
                                              VIEW_COMPONENT_ID BIGINT NOT NULL COMMENT '화면요소 ID',
                                              URL VARCHAR(500) COMMENT 'URL',
                                              ADDRESS VARCHAR(1000) COMMENT '주소',
                                              CONTACT VARCHAR(20) COMMENT '연락처',
                                              FAX VARCHAR(20) COMMENT 'FAX',
                                              CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                              UPDATE_AT DATETIME COMMENT '변경일자',
                                              CONSTRAINT PK_VIEW_COMPONENT_ACCOMMODATION PRIMARY KEY (VIEW_COMPONENT_ID),
                                              CONSTRAINT FK_VIEW_COMPONENT_ACCOMMODATION FOREIGN KEY (VIEW_COMPONENT_ID) REFERENCES VIEW_COMPONENT (VIEW_COMPONENT_ID)
);

DROP TABLE IF EXISTS VIEW_COMPONENT_FACILITY;
CREATE TABLE VIEW_COMPONENT_FACILITY (
                                         VIEW_COMPONENT_ID BIGINT NOT NULL COMMENT '화면요소 ID',
                                         VIEW_COMPONENT_SEQ BIGINT NOT NULL COMMENT '숙박시설 순번',
                                         ICON_TYPE VARCHAR(50) COMMENT '유형(ICON)',
                                         TITLE VARCHAR(50) COMMENT '제목',
                                         DESCRIPTION VARCHAR(500) COMMENT '설명(비고)',
                                         CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                                         UPDATE_AT DATETIME COMMENT '변경일자',
                                         CONSTRAINT PK_VIEW_COMPONENT_FACILITY PRIMARY KEY (VIEW_COMPONENT_ID, VIEW_COMPONENT_SEQ),
                                         CONSTRAINT FK_VIEW_COMPONENT_FACILITY FOREIGN KEY (VIEW_COMPONENT_ID) REFERENCES VIEW_COMPONENT_ACCOMMODATION (VIEW_COMPONENT_ID)
);


DROP TABLE IF EXISTS CAROUSEL CASCADE;
CREATE TABLE CAROUSEL (
                          CAROUSEL_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '케로셀 ID',
                          TITLE VARCHAR(50) COMMENT '제목',
                          IMAGE_SRC VARCHAR(2000) COMMENT '이미지경로',
                          LINK_URL VARCHAR(1000) COMMENT '연결 URL',
                          ORDINAL_POSITION INT COMMENT '순서',
                          USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                          CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                          UPDATE_AT DATETIME COMMENT '변경일자',
                          CONSTRAINT PK_CAROUSEL PRIMARY KEY(CAROUSEL_ID)
);

DROP TABLE IF EXISTS COMMERCE CASCADE;
CREATE TABLE COMMERCE (
                          COMMERCE_ID BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품목록 ID',
                          PRODUCT_ID BIGINT NOT NULL COMMENT '상품 ID',
                          ORDINAL_POSITION INT COMMENT '순서',
                          USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용유무',
                          CREATE_AT DATETIME DEFAULT NOW() COMMENT '작성일자',
                          UPDATE_AT DATETIME COMMENT '변경일자',
                          CONSTRAINT PK_COMMERCE PRIMARY KEY(COMMERCE_ID),
                          CONSTRAINT FK_COMMERCE FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);
