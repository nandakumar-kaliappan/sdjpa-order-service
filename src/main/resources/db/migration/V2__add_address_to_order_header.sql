ALTER TABLE orderservice.order_header
    ADD column billing_address VARCHAR(30),
    ADD column billing_zip_code VARCHAR(30),
    ADD column shipping_address VARCHAR(30),
    ADD column shipping_zip_code VARCHAR(30)