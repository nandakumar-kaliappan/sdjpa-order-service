CREATE TABLE IF NOT EXISTS category
(
    id                 BIGINT NOT NULL Auto_Increment PRIMARY KEY,
    description        VARCHAR(100),
    created_date       timestamp,
    last_modified_date timestamp DEFAULT NOW()
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS product_category
(
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    CONSTRAINT pk_product_id_fk FOREIGN KEY (product_id)
        REFERENCES orderservice.product (id),
    CONSTRAINT pk_category_id_fk FOREIGN KEY (category_id)
        REFERENCES orderservice.category (id)
) engine = InnoDB;

INSERT INTO orderservice.product(orderservice.product.description, product_status, created_date,
                                 last_modified_date)
values ('Product 1', 'NEW', NOW(), NOW());
INSERT INTO orderservice.product(orderservice.product.description, product_status, created_date,
                                 last_modified_date)
values ('Product 2', 'NEW', NOW(), NOW());
INSERT INTO orderservice.product(orderservice.product.description, product_status, created_date,
                                 last_modified_date)
values ('Product 3', 'NEW', NOW(), NOW());
INSERT INTO orderservice.product(orderservice.product.description, product_status, created_date,
                                 last_modified_date)
values ('Product 4', 'NEW', NOW(), NOW());

INSERT INTO orderservice.category(orderservice.category.description,
                                  created_date,
                                  last_modified_date)
values ('CAT 1', NOW(), NOW());
INSERT INTO orderservice.category(orderservice.category.description,
                                  created_date,
                                  last_modified_date)
values ('CAT 2', NOW(), NOW());
INSERT INTO orderservice.category(orderservice.category.description,
                                  created_date,
                                  last_modified_date)
values ('CAT 2', NOW(), NOW());

INSERT INTO orderservice.product_category(product_id, category_id)
SELECT p.id, c.id
FROM orderservice.product p,
     category c
WHERE p.description = 'product 1'
  AND c
          .description = 'CAT 1';
INSERT INTO orderservice.product_category(product_id, category_id)
SELECT p.id, c.id
FROM orderservice.product p,
     category c
WHERE p.description = 'product 2'
  AND c
          .description = 'CAT 1';
INSERT INTO orderservice.product_category(product_id, category_id)
SELECT p.id, c.id
FROM orderservice.product p,
     category c
WHERE p.description = 'product 1'
  AND c
          .description = 'CAT 3';
INSERT INTO orderservice.product_category(product_id, category_id)
SELECT p.id, c.id
FROM orderservice.product p,
     category c
WHERE p.description = 'product 4'
  AND c
          .description = 'CAT 3';