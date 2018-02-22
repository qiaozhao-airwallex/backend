create table user
(
  id            INT unsigned NOT NULL AUTO_INCREMENT,
  username      varchar(50) not null,
  password      varchar(80) not null,
  PRIMARY KEY   (id),
  UNIQUE KEY    (username)
) default charset 'utf8' ENGINE='innodb';

create table product
(
  id            INT unsigned NOT NULL AUTO_INCREMENT,
  subject       varchar(50) not null,
  description   varchar(1000) not null default "",
  owner_id      INT unsigned NOT NULL,
  published     BOOLEAN,
  PRIMARY KEY   (id),
  INDEX owner_idx (owner_id),
  FOREIGN KEY (owner_id) REFERENCES user (id)
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
