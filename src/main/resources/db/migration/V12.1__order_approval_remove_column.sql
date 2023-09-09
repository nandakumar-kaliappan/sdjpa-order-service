ALTER TABLE orderservice.order_header
    DROP foreign key order_approval_fk;
ALTER TABLE orderservice.order_header
    DROP COLUMN order_approval_id;