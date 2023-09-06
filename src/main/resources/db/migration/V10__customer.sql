alter table order_header drop column  customer_name;
CREATE TABLE customer
(
    id                 BIGINT NOT NULL Auto_Increment PRIMARY KEY,
    customer_name      VARCHAR(50),
    address            VARCHAR(30),
    zip_code           VARCHAR(30),
    phone              varchar(20),
    email              varchar(255),
    created_date       timestamp,
    last_modified_date timestamp DEFAULT NOW()
) engine = InnoDB;

ALTER TABLE orderservice.order_header
    ADD COLUMN customer_id BIGINT NOT NULL;

ALTER TABLE  orderservice.order_header
    ADD CONSTRAINT order_customer_fk
        FOREIGN KEY (customer_id) REFERENCES customer(id);



insert into customer (customer_name, address, zip_code, phone, email)
values ('Customer 1', '123 Duval', '33040', '305.292.1435',
        'cheeseburger@margaritville.com' );

update order_header set order_header.customer_id = (select id from customer limit 1);

