ALTER TABLE orderservice.order_header
    ADD COLUMN last_modified_date timestamp default NOW()