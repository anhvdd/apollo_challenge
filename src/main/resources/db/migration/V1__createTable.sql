-- ----------------------------
-- Sequence structure for customer_seq
-- ----------------------------
CREATE SEQUENCE customer_seq;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
CREATE TABLE customer (
                          "id" int NOT NULL DEFAULT NEXTVAL ('customer_seq'),
                          "name" varchar(255) NOT NULL,
                          "is_delete" bool,
                          "created_time" timestamp(6),
                          "updated_time" timestamp(6),
                          PRIMARY KEY (id)
);

-- ----------------------------
-- Sequence structure for tag_seq
-- ----------------------------
CREATE SEQUENCE tag_seq;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
CREATE TABLE tag (
                     "id" int NOT NULL DEFAULT NEXTVAL ('tag_seq'),
                     "title" varchar(255) NOT NULL,
                     "is_delete" bool,
                     "created_time" timestamp(6),
                     "updated_time" timestamp(6),
                     PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
CREATE TABLE customer_tag (
                              "customer_id" int NOT NULL,
                              "tag_id" int NOT NULL,
                              PRIMARY KEY (customer_id, tag_id)
);
