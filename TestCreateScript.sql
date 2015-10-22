DROP schema test;
CREATE schema test;
USE test;
CREATE table application ( uuid varchar(32) NOT NULL, title varchar(128) NOT NULL, description longtext, PRIMARY KEY (uuid) );
CREATE table category ( uuid varchar(32) NOT NULL, title varchar(128) NOT NULL, description longtext, PRIMARY KEY (uuid) );
CREATE table feature ( uuid varchar(32) NOT NULL, title varchar(128) NOT NULL, description longtext, PRIMARY KEY (uuid) );
CREATE table application_belongs_to_category ( application_uuid varchar(32) NOT NULL, category_uuid varchar(32) NOT NULL, 
	PRIMARY KEY (application_uuid,category_uuid), FOREIGN KEY (application_uuid) REFERENCES application(uuid), FOREIGN KEY (category_uuid) REFERENCES category(uuid));
CREATE table application_has_feature ( application_uuid varchar(32) NOT NULL, feature_uuid varchar(32) NOT NULL, 
	PRIMARY KEY (application_uuid,feature_uuid), FOREIGN KEY (application_uuid) REFERENCES application(uuid), FOREIGN KEY (feature_uuid) REFERENCES feature(uuid));
    
INSERT INTO application values('test_id_1','Dropbox','Dropbox Description');
INSERT INTO application values('test_id_2','Box.UP','Box.UP Description');
    
INSERT INTO category values('test_id_11','Cloud Speicher','Cloud Speicher Description');
INSERT INTO category values('test_id_12','Wiki','Wiki Description');
    
INSERT INTO feature values('test_id_21','Kalender anlegen','Kalender anlegen Description');
INSERT INTO feature values('test_id_22','Termine teilen','Termine teilen Description');

INSERT INTO application_belongs_to_category values('test_id_1','test_id_11');
INSERT INTO application_belongs_to_category values('test_id_2','test_id_11');
    
INSERT INTO application_has_feature values('test_id_1','test_id_21');
INSERT INTO application_has_feature values('test_id_1','test_id_22');
INSERT INTO application_has_feature values('test_id_2','test_id_21');