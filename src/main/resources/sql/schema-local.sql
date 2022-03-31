
drop table if exists `user` cascade;
create table `user` (
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
     description varchar(500) comment '비고',
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
     writer varchar(20) comment '작성자',
     notice_yn char(1) default 'N' comment '공지등록여부',
     pv int default 0 comment '페이지뷰',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice primary key (notice_id)
);

drop table if exists notice_attach cascade;
create table notice_attach (
     attach_id bigint not null comment '첨부파일 ID',
     notice_id bigint not null comment '공지사항 ID',
     path varchar(2000) comment '파일저장경로',
     size bigint comment '파일크기',
     origin_name varchar(100) comment '파일원본명',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_notice_attach primary key (attach_id),
     constraint fk_notice_attach foreign key (notice_id) references notice (notice_id)
);


drop table if exists qna cascade;
create table qna (
     qna_id bigint auto_increment not null comment '질문과 답변 ID',
     title varchar(50) not null comment '제목',
     content longtext comment '내용',
     writer varchar(20) comment '작성자',
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
     writer varchar(20) comment '작성자',
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
     writer varchar(20) comment '작성자',
     title varchar(50) not null comment '제목',
     content longtext comment '내용',
     status varchar(10) comment '문의상태',
     contact varchar(500) comment '연락처(encrypt)',
     email varchar(100) comment '이메일',
     create_at datetime default now() comment '작성일자',
     update_at datetime comment '변경일자',
     constraint pk_inquiry primary key (inquiry_id)
);

drop table if exists product cascade;
create table product (
    product_id bigint not null auto_increment comment '상품ID',
    product_type varchar(10) not null comment '상품유형',
    nation_code char(2) comment '국가코드',
    area_code varchar(5) comment '지역코드',
    title varchar(50) comment '상품명',
    content longtext comment '상품설명',
    writer varchar(20) not null comment '작성자',
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
    product_id bigint not null comment '상품 ID',
    product_image_group_id bigint comment '상품 이미지그룹 ID',
    image_src varchar(2000) comment '이미지경로',
    description varchar(500) comment '설명(비고)',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_product_image primary key (product_image_id),
    constraint fk_product_image_0 foreign key (product_id) references product (product_id),
    constraint fk_product_image_1 foreign key (product_image_group_id) references product_image_group (product_image_group_id)
);

drop table if exists view_component;
create table view_component (
    view_component_id bigint not null auto_increment comment '화면요소 ID',
    view_component_type varchar(10) not null comment '화면요소 유형',
    product_id bigint comment '상품 ID',
    title varchar(50) comment '제목',
    description varchar(500) comment '설명(비고)',
    ordinal int comment '순서',
    use_yn char(1) default 'Y' comment '사용유무',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component primary key (view_component_id),
    constraint fk_view_component foreign key (product_id) references product(product_id)
);

drop table if exists view_component_text;
create table view_text_component (
    view_component_id bigint not null comment '화면요소 ID',
    content longtext not null comment '내용',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component_text primary key (view_component_id),
    constraint fk_view_component_text foreign key (view_component_id) references view_component (view_component_id)
);

drop table if exists view_component_image;
create table view_component_image (
    view_component_id bigint not null comment '화면요소 ID',
    display_type varchar(10) comment '노출유형(케로셀, 목록)',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component_image primary key (view_component_id),
    constraint fk_view_component_image foreign key (view_component_id) references view_component (view_component_id)
);

drop table if exists view_component_images;
create table view_component_images (
    view_component_id bigint not null comment '화면요소 ID',
    seq int not null comment '이미지 구성요소 순번',
    image_src varchar(4000) not null comment '이미지 경로',
    title varchar(50) comment '제목',
    description varchar(500) comment '설명(비고)',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component_images primary key (view_component_id, seq),
    constraint fk_view_component_images foreign key (view_component_id) references view_component_image (view_component_id)
);

drop table if exists view_component_accommodation;
create table view_component_accommodation (
    view_component_id bigint not null comment '화면요소 ID',
    url varchar(500) comment 'url',
    address varchar(1000) comment '주소',
    contact varchar(20) comment '연락처',
    fax varchar(20) comment 'FAX',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component_accommodation primary key (view_component_id),
    constraint fk_view_component_accommodation foreign key (view_component_id) references view_component (view_component_id)
);

drop table if exists view_component_facilities;
create table view_component_facilities (
    view_component_id bigint not null comment '화면요소 ID',
    seq bigint not null comment '숙박시설 순번',
    icon_type varchar(50) not null comment '유형(icon)',
    title varchar(50) comment '제목',
    description varchar(500) comment '설명(비고)',
    create_at datetime default now() comment '작성일자',
    update_at datetime comment '변경일자',
    constraint pk_view_component_facility primary key (view_component_id, seq),
    constraint fk_view_component_facility foreign key (view_component_id) references view_component_accommodation (view_component_id)
);
