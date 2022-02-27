insert into code_group (group_code, group_name, use_yn) values ('PRODUCT_TYPE', '상품유형', 'Y');
insert into code_group (group_code, group_name, use_yn) values ('DISPLAY_TYPE', '노출유형', 'Y');

insert into code (group_code, code, code_name, description) values ('PRODUCT_TYPE', 'Hotel', '호텔', null);
insert into code (group_code, code, code_name, description) values ('PRODUCT_TYPE', 'CarRental', '렌터카', null);
insert into code (group_code, code, code_name, description) values ('PRODUCT_TYPE', 'Scuba', '스쿠버', null);
insert into code (group_code, code, code_name, description) values ('DISPLAY_TYPE', 'carousel', '케로셀', null);
insert into code (group_code, code, code_name, description) values ('DISPLAY_TYPE', 'list', '목록', null);
