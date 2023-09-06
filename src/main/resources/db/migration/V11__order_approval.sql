CREATE TABLE order_approval
(
    id                 BIGINT NOT NULL Auto_Increment PRIMARY KEY,
    approved_by        VARCHAR(50),
    created_date       timestamp,
    last_modified_date timestamp DEFAULT NOW()
) engine = InnoDB;

ALTER TABLE orderservice.order_header
    ADD COLUMN order_approval_id BIGINT;

ALTER TABLE orderservice.order_header
    ADD CONSTRAINT order_approval_fk
        FOREIGN KEY (order_approval_id) REFERENCES order_approval(id);
