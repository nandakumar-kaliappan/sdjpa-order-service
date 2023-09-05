CREATE TABLE order_line(
    id Bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    quantity_ordered INT,
    order_header_id BIGINT,
    created_date timestamp,
    last_modified_date timestamp default  NOW(),
    Constraint order_header_pk FOREIGN KEY (order_header_id) REFERENCES order_header(id)
)
