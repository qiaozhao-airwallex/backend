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
  product_id      INT unsigned,
  created_by      INT unsigned NOT NULL,
  filename        varchar(100),
  dis_order       INT unsigned,
  PRIMARY KEY   (id),
  INDEX product_idx (product_id),
  FOREIGN KEY (product_id) REFERENCES product (id),
  INDEX created_by_idx (created_by),
  FOREIGN KEY (created_by) REFERENCES user (id)
) default charset 'utf8' ENGINE='innodb';

create table friend
(
  user_id         INT unsigned NOT NULL,
  friend_id       INT unsigned NOT NULL,
  INDEX frd_user_idx (user_id),
  FOREIGN KEY (user_id) REFERENCES user (id),
  INDEX frd_friend_idx (friend_id),
  FOREIGN KEY (friend_id) REFERENCES user (id)
) default charset 'utf8' ENGINE='innodb';
