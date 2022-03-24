
drop table if exists user cascade;
create table user (
     user_id   varchar(50) not null comment '사용자(로그인)ID',
     use_yn    char(1) default 'Y' comment '사용유무',
     password  varchar(500) comment '로그인 비밀번호',
     name varchar(10) comment '이름',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_user primary key (user_id)
);

drop table if exists code_group cascade;
create table code_group (
     group_code varchar(20) not null comment '그룹코드',
     group_name varchar(50) comment '그룹코드이름',
     use_yn    char(1) default 'Y' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_code_group primary key (group_code)
);

drop table if exists code cascade;
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

drop table if exists nation;
create table nation (
     nation_code char(2) not null comment '국가코드(2-alpha)',
     nation_name varchar(50) comment '국가명',
     use_yn char(1) not null default 'N' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_nation primary key (nation_code)
);

drop table if exists area;
create table nation_area (
     nation_code char(2) not null comment '국가코드(2-alpha)',
     area_code varchar(5) not null comment '지역코드',
     area_name varchar(50) comment '지역명',
     use_yn char(1) not null default 'N' comment '사용유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_nation_area primary key (nation_code, area_code),
     constraint fk_nation_area foreign key (nation_code) references nation (nation_code)
);


drop table if exists notice cascade;
create table notice (
     notice_id bigint auto_increment not null comment '공지사항 ID',
     title varchar(50) not null comment '제목',
     content longtext comment '내용',
     writer varchar(50) comment '작성자',
     notice_yn char(1) default 'N' comment '공지등록여부',
     pv int default 0 comment '페이지뷰',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice primary key (notice_id)
);

drop table if exists notice_attachment cascade;
create table notice_attachment (
     notice_attachment_id bigint not null comment '공지사항 첨부파일 ID',
     notice_id bigint not null comment '공지사항 ID',
     path varchar(2000) comment '파일저장경로',
     size bigint comment '파일크기',
     origin_name varchar(100) comment '파일원본명',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice_attachment primary key (notice_attachment_id),
     constraint fk_notice_attachment foreign key (notice_id) references notice (notice_id)
);


drop table if exists qna cascade;
create table qna (
     qna_id bigint auto_increment not null comment '질문과 답변 ID',
     title varchar(50) not null comment '제목',
     content longtext comment '내용',
     writer varchar(50) comment '작성자',
     password varchar(500) comment '비밀번호',
     private_yn char(1) default 'N' comment '비공개유무',
     pv int default 0 comment '페이지뷰',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_qna primary key(qna_id)
);

drop table if exists qna_reply cascade;
create table qna_reply (
     qna_reply_id bigint auto_increment not null comment '질문과 답변 댓글 ID',
     qna_id bigint not null comment '질문과 답변 ID',
     writer varchar(50) comment '작성자',
     content varchar(4000) comment '내용',
     password varchar(500) comment '비밀번호',
     private_yn char(1) default 'N'  comment '비공개유무',
     admin_yn char(1) default 'N' comment '관리자댓글유무',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_qna_reply primary key (qna_reply_id),
     constraint fk_qna_reply foreign key (qna_id) references qna (qna_id)
);


drop table if exists inquiry cascade;
create table inquiry (
     inquiry_id bigint not null auto_increment comment '문의ID',
     writer varchar(50) comment '작성자',
     content longtext comment '내용',
     contact varchar(500) comment '연락처(encrypt)',
     email varchar(100) comment '이메일',
     nation_code char(2) comment '국가코드',
     area_code varchar(5) comment '지역코드',
     product_id bigint comment '상품ID',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_inquiry primary key (inquiry_id)
);

drop table if exists product cascade;
create table product (
    product_id bigint not null auto_increment comment '상품ID',
    product_type varchar(10) comment '상품유형',
    nation_code char(2) comment '국가코드',
    area_code varchar(5) comment '지역코드',
    title varchar(100) comment '상품명',
    description longtext comment '상품설명',
    writer varchar(50) not null comment '작성자',
    rep_image_src varchar(2000) comment '대표이미지경로',
    use_yn char(1) default 'Y' comment '사용유무',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product primary key (product_id),
    key product_idx01 (product_id, product_type),
    key product_idx02 (nation_code, area_code)
);

drop table if exists product_image_group;
create table product_image_group (
    product_image_group_id bigint not null auto_increment comment '상품 이미지그룹 ID',
    product_id bigint not null comment '상품 ID',
    group_name varchar(100) comment '그룹명',
    use_yn char(1) default 'Y' comment '사용유무',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_image_group primary key (product_image_group_id),
    constraint fk_product_image_group foreign key (product_id) references product(product_id)
);

drop table if exists product_image;
create table product_image (
    product_image_id bigint not null auto_increment comment '상품 이미지 ID',
    product_image_group_id bigint not null comment '상품 이미지그룹 ID',
    image_src varchar(2000) comment '이미지경로',
    image_description varchar(500) comment '이미지설명',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_image primary key (product_image_id),
    constraint fk_product_image foreign key (product_image_group_id) references product_image_group (product_image_group_id)
);

drop table if exists product_component;
create table product_component (
    product_component_id bigint not null auto_increment comment '상품 컴포넌트 ID',
    product_id bigint not null comment '상품 ID',
    component_type varchar(10) not null comment '컴포넌트 유형',
    ordinal_position int comment '순서',
    use_yn char(1) default 'Y' comment '사용유무',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_component primary key (product_component_id),
    constraint fk_product_component foreign key (product_id) references product(product_id)
);

drop table if exists product_component_facility;
create table product_component_facility (
    product_component_facility_id bigint auto_increment not null comment '상품 컴포넌트 시설 ID',
    product_component_id bigint not null comment '상품 컴포넌트 ID',
    icon_type varchar(50) not null comment '아이콘 유형',
    description varchar(100) not null comment '설명',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_component_facility primary key (product_component_facility_id),
    constraint fk_product_component_facility foreign key (product_component_id) references product_component (product_component_id)
);

drop table if exists product_component_text;
create table product_component_text (
    product_component_text_id bigint auto_increment not null comment '상품 컴포넌트 텍스트 ID',
    product_component_id bigint not null comment '상품 컴포넌트 ID',
    title varchar(50) not null comment '제목',
    content longtext not null comment '내용',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_component_text primary key (product_component_text_id),
    constraint fk_product_component_text foreign key (product_component_id) references product_component (product_component_id)
);
