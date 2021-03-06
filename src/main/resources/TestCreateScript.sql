DROP SCHEMA provider_toolup;
CREATE SCHEMA provider_toolup;
USE provider_toolup;
CREATE TABLE application (
  uuid        VARCHAR(64)  NOT NULL,
  title       VARCHAR(128) NOT NULL,
  description LONGTEXT,
  FULLTEXT (title,description),
  PRIMARY KEY (uuid)
);
CREATE TABLE category (
  uuid          VARCHAR(64)  NOT NULL,
  title         VARCHAR(128) NOT NULL,
  description   LONGTEXT,
  supercategory VARCHAR(64),
  FULLTEXT (title,description),
  PRIMARY KEY (uuid),
  FOREIGN KEY (supercategory) REFERENCES category (uuid)
    ON DELETE SET NULL
);
CREATE TABLE feature (
  uuid        VARCHAR(64)  NOT NULL,
  title       VARCHAR(128) NOT NULL,
  description LONGTEXT,
  FULLTEXT (title,description),
  PRIMARY KEY (uuid)
);
CREATE TABLE application_belongs_to_category (
  application_uuid VARCHAR(64) NOT NULL,
  category_uuid    VARCHAR(64) NOT NULL,
  PRIMARY KEY (application_uuid, category_uuid),
  FOREIGN KEY (application_uuid) REFERENCES application (uuid)
    ON DELETE CASCADE,
  FOREIGN KEY (category_uuid) REFERENCES category (uuid)
    ON DELETE CASCADE
);
CREATE TABLE application_has_feature (
  application_uuid VARCHAR(64) NOT NULL,
  feature_uuid     VARCHAR(64) NOT NULL,
  PRIMARY KEY (application_uuid, feature_uuid),
  FOREIGN KEY (application_uuid) REFERENCES application (uuid)
    ON DELETE CASCADE,
  FOREIGN KEY (feature_uuid) REFERENCES feature (uuid)
    ON DELETE CASCADE
);

INSERT INTO application VALUES ('application-test_id_1', 'Dropbox', 'Dropbox Description');
INSERT INTO application VALUES ('application-test_id_2', 'Box.UP', 'Box.UP Description');

INSERT INTO category
VALUES ('category-test_id_13', 'Überkategorie1', 'Eine Kategorie zum Testen der Superkategorie-Beziehung', NULL);
INSERT INTO category
VALUES ('category-test_id_14', 'Überkategorie2', 'Eine Kategorie zum Testen der Superkategorie-Beziehung', NULL);
INSERT INTO category
VALUES ('category-test_id_15', 'Überkategorie3', 'Eine Kategorie zum Testen der Superkategorie-Beziehung', NULL);
INSERT INTO category
VALUES ('category-test_id_16', 'Zwischenkategorie2', 'Eine Kategorie zum Testen der Superkategorie-Beziehung', 'category-test_id_13');
INSERT INTO category
VALUES ('category-test_id_17', 'Zwischenkategorie1', 'Eine Kategorie zum Testen der Superkategorie-Beziehung', 'category-test_id_13');
INSERT INTO category VALUES ('category-test_id_11', 'Cloud Speicher', 'Cloud Speicher Description', 'category-test_id_17');
INSERT INTO category VALUES ('category-test_id_12', 'Wiki', 'Wiki Description', 'category-test_id_17');

INSERT INTO feature VALUES ('feature-test_id_21', 'Kalender anlegen', 'Kalender anlegen Description');
INSERT INTO feature VALUES ('feature-test_id_22', 'Termine teilen', 'Termine teilen Description');
INSERT INTO feature VALUES ('feature-test_id_23', 'Nicht zugeordnetes Feature',
                            'Dieses Feature ist keiner Application zugeordnet. Wird für einen Lookup-Test benötigt.');

INSERT INTO application_belongs_to_category VALUES ('application-test_id_1', 'category-test_id_11');
INSERT INTO application_belongs_to_category VALUES ('application-test_id_1', 'category-test_id_12');
INSERT INTO application_belongs_to_category VALUES ('application-test_id_1', 'category-test_id_17');
INSERT INTO application_belongs_to_category VALUES ('application-test_id_2', 'category-test_id_11');
INSERT INTO application_belongs_to_category VALUES ('application-test_id_2', 'category-test_id_17');

INSERT INTO application_has_feature VALUES ('application-test_id_1', 'feature-test_id_21');
INSERT INTO application_has_feature VALUES ('application-test_id_1', 'feature-test_id_22');
INSERT INTO application_has_feature VALUES ('application-test_id_2', 'feature-test_id_21');

ALTER TABLE application
  ADD short_description LONGTEXT;
ALTER TABLE application
  ADD contact LONGTEXT;
ALTER TABLE application
  ADD provider LONGTEXT;