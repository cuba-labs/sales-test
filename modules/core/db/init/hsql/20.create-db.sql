-- begin SALES_ORDER_LINE
alter table SALES_ORDER_LINE add constraint FK_SALES_ORDER_LINE_PRODUCT foreign key (PRODUCT_ID) references SALES_PRODUCT(ID)^
alter table SALES_ORDER_LINE add constraint FK_SALES_ORDER_LINE_ORDER foreign key (ORDER_ID) references SALES_ORDER(ID)^
create index IDX_SALES_ORDER_LINE_PRODUCT on SALES_ORDER_LINE (PRODUCT_ID)^
create index IDX_SALES_ORDER_LINE_ORDER on SALES_ORDER_LINE (ORDER_ID)^
-- end SALES_ORDER_LINE
-- begin SALES_ORDER
alter table SALES_ORDER add constraint FK_SALES_ORDER_CUSTOMER foreign key (CUSTOMER_ID) references SALES_CUSTOMER(ID)^
create index IDX_SALES_ORDER_CUSTOMER on SALES_ORDER (CUSTOMER_ID)^
-- end SALES_ORDER
