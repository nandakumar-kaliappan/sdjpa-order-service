ALTER TABLE order_Line ADD COLUMN product_id BIGINT;
ALTER TABLE order_line ADD CONSTRAINT order_line_prosuct_FK
    FOREIGN KEY (product_id) REFERENCES orderservice.product(id);