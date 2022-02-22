insert into code_group (group_code, group_name, use_yn) values ('product_type', '상품유형', 'Y');
insert into code_group (group_code, group_name, use_yn) values ('display_type', '노출유형', 'Y');

insert into code (group_code, code, code_name, description) values ('product_type', 'Hotel', '호텔', null);
insert into code (group_code, code, code_name, description) values ('product_type', 'CarRental', '렌터카', null);
insert into code (group_code, code, code_name, description) values ('product_type', 'Scuba', '스쿠버', null);
insert into code (group_code, code, code_name, description) values ('display_type', 'carousel', '케로셀', null);
insert into code (group_code, code, code_name, description) values ('display_type', 'list', '목록', null);
