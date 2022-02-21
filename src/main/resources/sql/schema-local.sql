drop table if exists member cascade;

drop table if exists code cascade;
drop table if exists code_group cascade;

drop table if exists area;
drop table if exists nation;

drop table if exists notice_attachment cascade;
drop table if exists notice cascade;
drop table if exists qna_reply cascade;
drop table if exists qna cascade;

drop table if exists inquiry_attachment cascade;
drop table if exists inquiry cascade;
drop table if exists answer_attachment cascade;
drop table if exists answer cascade;

drop table if exists tour_stay_type_facility cascade;
drop table if exists tour_stay_type_image cascade;
drop table if exists tour_stay_type cascade;
drop table if exists tour_stay_facility cascade;
drop table if exists tour_stay cascade;

-- drop table if exists tb_tour_component_vidio cascade;
-- drop table if exists tb_tour_component_table cascade;
drop table if exists tour_component_map cascade;
drop table if exists tour_component cascade;

drop table if exists tour_list cascade;
drop table if exists tour_image cascade;
drop table if exists tour cascade;

create table member (
     user_id   varchar(50) not null comment '사용자(로그인)ID',
     use_yn    char(1) default 'Y' comment '사용유무',
     password  varchar(500) comment '로그인 비밀번호',
     name varchar(10) comment '이름',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_user primary key (user_id)
);

create table code_group (
     group_code varchar(20) not null comment '그룹코드',
     group_name varchar(50) comment '그룹코드이름',
     use_yn    char(1) default 'Y' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_code_group primary key (group_code)
);

create table code (
     group_code   varchar(20) not null comment '그룹코드',
     code   varchar(20) not null comment '공통코드',
     code_name varchar(50) not null comment '공통코드값',
     description varchar(50) comment '비고',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_code primary key (group_code, code),
     constraint fk_code foreign key (group_code) references code_group (group_code)
);

create table nation (
     nation_code char(2) not null comment '국가코드(2-alpha)',
     nation_name varchar(50) comment '국가명',
     use_yn char(1) not null default 'N' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_nation primary key (nation_code)
);

create table area (
     nation_code char(2) not null comment '국가코드(2-alpha)',
     area_code varchar(5) not null comment '지역코드',
     area_name varchar(50) comment '지역명',
     use_yn char(1) not null default 'N' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_area primary key (nation_code, area_code),
     constraint fk_area foreign key (nation_code) references nation (nation_code)
);

create table notice (
     notice_id bigint auto_increment not null comment '순번',
     title varchar(50) not null comment '제목',
     content longtext comment '내용',
     writer varchar(50) comment '작성자',
     notice_yn char(1) default 'N' comment '공지등록여부',
     pv int default 0 comment '페이지뷰',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice primary key (notice_id)
);

create table notice_attachment (
     notice_id bigint not null comment '게시글ID',
     attachment_id int not null comment '파일ID',
     path varchar(2000) comment '파일저장경로',
     size bigint comment '파일크기',
     origin_name varchar(100) comment '파일본래이름',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice_attachment primary key (notice_id, attachment_id),
     constraint fk_notice_attachment foreign key (notice_id) references notice (notice_id)
);

create table qna (
     qna_id bigint auto_increment not null comment '질문답변ID',
     title varchar(50) not null comment '제목',
     content   longtext comment '내용',
     writer varchar(50) comment '작성자',
     password varchar(500) comment '비밀번호',
     private_yn char(1) default 'N' comment '비공개유무',
     pv int default 0 comment '페이지뷰',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_qna primary key(qna_id)
);

create table qna_reply (
     qna_id bigint not null comment '게시글ID',
     qna_reply_id int not null comment '댓글ID',
     writer varchar(50) comment '작성자',
     content varchar(4000) comment '내용',
     password varchar(500) comment '비밀번호',
     private_yn char(1) default 'N'  comment '비공개유무',
     admin_yn char(1) default 'N' comment '관리자댓글유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_qna_reply primary key (qna_id, qna_reply_id),
     constraint fk_qna_reply foreign key (qna_id) references qna (qna_id)
);

create table inquiry (
     inquiry_id bigint not null auto_increment comment '문의ID',
     writer varchar(50) comment '작성자',
     content longtext comment '내용',
     contact varchar(500) comment '연락처(encrypt)',
     email varchar(100) not null comment '이메일',
     nation_code char(2) comment '국가코드',
     area_code varchar(5) comment '지역코드',
     tour_id bigint comment '투어ID',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_inquiry primary key (inquiry_id)
);

create table inquiry_attachment (
     inquiry_id bigint not null comment '여행문의ID',
     attachment_id int not null comment '파일ID',
     path varchar(2000) comment '파일저장경로',
     size bigint comment '파일크기',
     origin_name varchar(100) comment '파일본래이름',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_inquiry_attachment primary key (inquiry_id, attachment_id),
     constraint fk_inquiry_attachment foreign key (inquiry_id) references inquiry (inquiry_id)
);

create table answer (
     answer_id bigint not null comment '답변ID',
     inquiry_id bigint not null comment '문의ID',
     title varchar(50) not null comment '제목',
     writer varchar(50) not null comment '작성자',
     content longtext comment '내용',
     email_reply_yn char(1) default 'N' comment '이메일회신유무',
     email_reply_at datetime comment '이메일회신일자',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_answer primary key (answer_id),
     constraint fk_answer foreign key (inquiry_id) references inquiry (inquiry_id)
);

create table answer_attachment (
     answer_id bigint not null comment '답변ID',
     attachment_id int not null comment '답변파일ID',
     path varchar(2000) comment '파일저장경로',
     size bigint comment '파일크기',
     origin_name varchar(100) comment '파일본래이름',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_answer_attachment primary key (answer_id, attachment_id),
     constraint fk_answer_attachment foreign key (answer_id) references answer (answer_id)
);

create table hotel (
    hotel_id bigint not null auto_increment comment '호텔 ID',
    nation_code char(2) comment '국가코드',
    area_code varchar(5) comment '지역코드',
    title varchar(50) comment '상품명',
    writer varchar(50) not null comment '작성자',
    content longtext comment '여행상품내용',
    hotel_name varchar(100) not null comment '호텔명',
    hotel_class varchar(10) not null comment '호텔유형(성급)',
    hotel_url varchar(100) comment '웹사이트 URL',
    hotel_contact varchar(30) comment '연락처',
    hotel_address varchar(200) comment '주소',
    use_yn char(1) default 'Y' comment '사용유무',
    pv int default 0 comment '페이지뷰',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_hotel primary key (hotel_id)
);

create table hotel_image (
    hotel_id bigint not null comment '호텔 ID',
    image_id int not null comment '이미지 ID',
    src varchar(2000) not null comment '이미지src',
    alt varchar(100) not null comment '이미지alt',
    description varchar(4000) not null comment '이미지설명',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_tour_image primary key (hotel_id, image_id),
    constraint fk_tour_image foreign key (hotel_id) references hotel (hotel_id)
);

create table hotel_facility (
    hotel_id bigint not null comment '여행ID',
    facility_id int not null comment '여행숙소시설ID',
    icon varchar(50) not null comment '아이콘',
    description varchar(100) not null comment '설명',
    use_yn char(1) default 'Y' comment '사용유무',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_tour_stay_facility primary key (hotel_id, facility_id),
    constraint fk_tour_stay_facility foreign key (hotel_id) references hotel (hotel_id)
);


create table product (
    product_id bigint not null comment '딜ID',
    hotel_id bigint comment '호텔ID',
    display_type varchar(10) default 'list' comment '노출유형(케로셀, 목록)',
    use_yn char(1) default 'Y' comment '사용유무',
    title varchar(100) comment '노출상품명',
    description varchar(100) comment '노출상품 설명',
    ordinal int default 0 comment '순서',
    src varchar(2000) comment '이미지src',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_article primary key (product_id),
    constraint fk_article foreign key (hotel_id) references hotel (hotel_id)
);


/*
create table tour_stay_type (
     tour_id bigint not null comment '여행ID',
     tour_stay_id int not null comment '여행숙소ID',
     tour_stay_type_id int not null comment '여행숙소유형ID',
     name varchar(50) not null comment '객실이름',
     description varchar(100) comment '객실설명',
     number_of_people int comment '객실인원',
     bed_type varchar(10) comment '침구류유형',
     room_cnt int comment '방갯수',
     toilet_cnt int comment '화장실갯수',
     use_yn char(1) default 'Y' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_stay_type primary key (tour_id, tour_stay_id, tour_stay_type_id),
     constraint fk_tour_stay_type foreign key (tour_id, tour_stay_id) references tour_stay (tour_id, tour_stay_id)
) ;

create table tour_stay_type_image (
     tour_id bigint not null comment '여행ID',
     tour_stay_id int not null comment '여행숙소ID',
     tour_stay_type_id int not null comment '여행숙소유형ID',
     tour_stay_type_image_id int not null comment '여행숙소유형사진ID',
     img_src varchar(2000) not null comment '이미지url',
     img_alt varchar(100) not null comment '이미지alt',
     ordr int default 0 comment '이미지노출순서',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_stay_type_image primary key (tour_id, tour_stay_id, tour_stay_type_id, tour_stay_type_image_id),
     constraint fk_tour_stay_type_image foreign key (tour_id, tour_stay_id, tour_stay_type_id) references tour_stay_type (tour_id, tour_stay_id, tour_stay_type_id)
) ;

create table tour_stay_type_facility (
     tour_id bigint not null comment '여행ID',
     tour_stay_id int not null comment '여행숙소ID',
     tour_stay_type_id int not null comment '여행숙소유형ID',
     tour_stay_type_facility_id int not null comment '여행숙소객실유형시설ID',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_stay_type_facility primary key (tour_id, tour_stay_id, tour_stay_type_id, tour_stay_type_facility_id),
     constraint fk_tour_stay_type_facility foreign key (tour_id, tour_stay_id, tour_stay_type_id) references tour_stay_type (tour_id, tour_stay_id, tour_stay_type_id)
) ;

create table tour_component (
     tour_id bigint not null comment '여행ID',
     tour_component_id bigint not null comment '여행-내용구성ID',
     tour_component_type varchar(10) not null comment '내용구성항목유형',
     use_yn char(1) default 'Y' comment '사용유무',
     ordr int default 0 comment '노출순서',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_component primary key (tour_id, tour_component_id),
     constraint fk_tour_component foreign key (tour_id) references tour (tour_id)
) ;

create table tour_component_map (
     tour_id bigint not null comment '여행ID',
     tour_component_id bigint not null comment '여행-내용구성ID',
     lat decimal(9, 6) comment '위도',
     lng decimal(9, 6) comment '경도',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_component_map primary key (tour_id, tour_component_id),
     constraint fk_tour_component_map foreign key (tour_id, tour_component_id) references tour_component (tour_id, tour_component_id)
) ;
*/

/**
create table tb_tour_component_vidio (
     tour_id bigint not null comment '여행ID',
     tour_component_id bigint not null comment '여행-내용구성ID',
     title varchar(50) comment '비디오제목',
     vidio_url varchar(2000) comment '비디오url',
     description varchar(1000) comment '비디오설명',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_component_vidio primary key (tour_id, tour_component_id),
     constraint fk_tour_component_vidio foreign key (tour_id) references tb_tour (tour_id)
) ;

-- (보류) 태이블 컴포넌트
create table tb_tour_component_table (
     tour_id bigint not null comment '여행ID',
     tour_component_id bigint not null comment '여행-내용구성ID',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_tour_component_table primary key (tour_id, tour_component_id),
     constraint fk_tour_component_table foreign key (tour_id) references tb_tour_component (tour_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '여행 컴포넌트 - 태이블';
*/
