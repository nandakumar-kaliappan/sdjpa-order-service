CREATE TABLE IF NOT EXISTS product(
    id BIGINT NOT NULL Auto_Increment PRIMARY KEY,
    description VARCHAR(100),
    product_status varchar(20),
    created_date timestamp,
    last_modified_date timestamp DEFAULT NOW()
)engine = InnoDB;