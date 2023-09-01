DROP TABLE IF EXISTS orderservice.order_header;

CREATE TABLE orderservice.order_header(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    customer_name VARCHAR(255)
)ENGINE = InnoDB;

