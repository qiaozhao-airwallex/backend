create table product
(
  id            INT unsigned NOT NULL AUTO_INCREMENT,
  subject       varchar(50) not null default "",
  description   varchar(1000) not null default "",
  published     BOOLEAN,
  PRIMARY KEY   (id)
) default charset 'utf8' ENGINE='innodb';

create table image
(
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  product_id      INT unsigned NOT NULL,
  filename        varchar(100),
  dis_order       INT unsigned NOT NULL,
  PRIMARY KEY   (id),
  INDEX product_idx (product_id),
  FOREIGN KEY (product_id) REFERENCES product (id)
) default charset 'utf8' ENGINE='innodb';
