--
-- Base de données :  AVOTE
--

DROP DATABASE IF EXISTS AVOTE;
CREATE DATABASE IF NOT EXISTS AVOTE;
USE AVOTE;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

-- --------------------------------------------------------

--
-- Structure de la table addresses
--

CREATE TABLE IF NOT EXISTS addresses (
  address_id INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  location   VARCHAR(255) NOT NULL,
  zip_code   VARCHAR(100) NOT NULL,
  city       VARCHAR(255) NOT NULL,
  country    VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

--
-- Structure de la table users
--

CREATE TABLE IF NOT EXISTS users (
  user_id      INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  first_name   VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  address_id   INT                               DEFAULT NULL,
  birthday     DATE                              DEFAULT NULL,
  email        VARCHAR(255) NOT NULL UNIQUE,
  phone_number VARCHAR(30)                       DEFAULT NULL,
  password     VARCHAR(255) NOT NULL,
  created_at   DATE         NOT NULL,
  is_activated BOOLEAN      NOT NULL,
  is_deleted   BOOLEAN      NOT NULL,

  CONSTRAINT user_address_FK FOREIGN KEY (address_id)
  REFERENCES addresses (address_id)
);

-- --------------------------------------------------------

--
-- Structure de la table user_API
--

CREATE TABLE IF NOT EXISTS user_API (
  user_id INT      NOT NULL PRIMARY KEY,
  api_key CHAR(32) NOT NULL
);

-- --------------------------------------------------------

--
-- Structure de la table restrictions
--

CREATE TABLE IF NOT EXISTS restrictions (
  restriction_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  age_min        INT                      DEFAULT NULL,
  age_max        INT                      DEFAULT NULL,
  city           VARCHAR(255)             DEFAULT NULL,
  department     VARCHAR(3)               DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Structure de la table polls
--

CREATE TABLE IF NOT EXISTS polls (
  poll_id        INT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  owner_id       INT      NOT NULL,
  subject        TEXT     NOT NULL,
  anonymity      BOOLEAN  NOT NULL,
  poll_type      INT      NOT NULL,
  number_rounds  INT      NOT NULL,
  is_published   BOOLEAN  NOT NULL,
  is_closed      BOOLEAN  NOT NULL,
  created_at     DATETIME NOT NULL,
  published_at   DATETIME                      DEFAULT NULL,
  closed_at      DATETIME                      DEFAULT NULL,
  restriction_id INT                           DEFAULT NULL,

  CONSTRAINT poll_restriction_FK FOREIGN KEY (restriction_id)
  REFERENCES restrictions (restriction_id),
  CONSTRAINT poll_owner_FK FOREIGN KEY (owner_id)
  REFERENCES users (user_id)
);

-- --------------------------------------------------------

--
-- Structure de la table choices
--

CREATE TABLE IF NOT EXISTS choices (
  choice_id INT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  poll_id   INT      NOT NULL,
  label     TINYTEXT NOT NULL,
  color     TINYTEXT,

  CONSTRAINT choice_poll_FK FOREIGN KEY (poll_id)
  REFERENCES polls (poll_id)
    ON DELETE CASCADE
);

-- --------------------------------------------------------

--
-- Structure de la table user_votes
--

CREATE TABLE IF NOT EXISTS user_polls (
  user_id INT NOT NULL,
  poll_id INT NOT NULL,

  PRIMARY KEY (user_id, poll_id),

  CONSTRAINT user_vote_user_id_FK FOREIGN KEY (user_id)
  REFERENCES users (user_id),
  CONSTRAINT user_vote_poll_id_FK FOREIGN KEY (poll_id)
  REFERENCES polls (poll_id)
);

-- --------------------------------------------------------

--
-- Structure de la table votes
--

CREATE TABLE IF NOT EXISTS votes (
  vote_id   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  choice_id INT NOT NULL,
  user_id   INT                      DEFAULT NULL,
  value     INT                      DEFAULT NULL,

  CONSTRAINT vote_choice_id_FK FOREIGN KEY (choice_id)
  REFERENCES choices (choice_id),
  CONSTRAINT vote_user_id_FK FOREIGN KEY (user_id)
  REFERENCES users (user_id)
);

-- --------------------------------------------------------

CREATE TRIGGER SetCreatedAtNow
BEFORE INSERT ON users
FOR EACH ROW
  SET NEW.created_at = NOW();

CREATE TRIGGER SetCreatedAtPollNow
BEFORE INSERT ON polls
FOR EACH ROW
  SET NEW.created_at = NOW();
-- ---

CREATE TABLE IF NOT EXISTS DEPARTMENTS (
  department_id VARCHAR(3)   NOT NULL PRIMARY KEY,
  department    VARCHAR(100) NOT NULL
);

INSERT INTO DEPARTMENTS VALUES ('01', 'Ain');
INSERT INTO DEPARTMENTS VALUES ('02', 'Aisne');
INSERT INTO DEPARTMENTS VALUES ('03', 'Allier');
INSERT INTO DEPARTMENTS VALUES ('04', 'Alpes-de-Haute-Provence');
INSERT INTO DEPARTMENTS VALUES ('05', 'Hautes-Alpes');
INSERT INTO DEPARTMENTS VALUES ('06', 'Alpes-Martimes');
INSERT INTO DEPARTMENTS VALUES ('07', 'Ardèche');
INSERT INTO DEPARTMENTS VALUES ('08', 'Ardennes');
INSERT INTO DEPARTMENTS VALUES ('09', 'Ariège');
INSERT INTO DEPARTMENTS VALUES ('10', 'Aube');
INSERT INTO DEPARTMENTS VALUES ('11', 'Aude');
INSERT INTO DEPARTMENTS VALUES ('12', 'Aveyron');
INSERT INTO DEPARTMENTS VALUES ('13', 'Bouches-du-Rhône');
INSERT INTO DEPARTMENTS VALUES ('14', 'Calvados');
INSERT INTO DEPARTMENTS VALUES ('15', 'Cantal');
INSERT INTO DEPARTMENTS VALUES ('16', 'Charente');
INSERT INTO DEPARTMENTS VALUES ('17', 'Charente-Maritime');
INSERT INTO DEPARTMENTS VALUES ('18', 'Cher');
INSERT INTO DEPARTMENTS VALUES ('19', 'Corrèze');
# INSERT INTO DEPARTMENTS values('2A', 'Corse-du-Sud');
# INSERT INTO DEPARTMENTS values('2B', 'Haute-Corse');
INSERT INTO DEPARTMENTS VALUES ('21', 'Côte-d\'Or');
INSERT INTO DEPARTMENTS VALUES ('22', 'Côtes-d\'Armor');
INSERT INTO DEPARTMENTS VALUES ('23', 'Creuse');
INSERT INTO DEPARTMENTS VALUES ('24', 'Dordogne');
INSERT INTO DEPARTMENTS VALUES ('25', 'Doubs');
INSERT INTO DEPARTMENTS VALUES ('26', 'Drôme');
INSERT INTO DEPARTMENTS VALUES ('27', 'Eure');
INSERT INTO DEPARTMENTS VALUES ('28', 'Eure-et-Loir');
INSERT INTO DEPARTMENTS VALUES ('29', 'Finistère');
INSERT INTO DEPARTMENTS VALUES ('30', 'Gard');
INSERT INTO DEPARTMENTS VALUES ('31', 'Haute-Garonne	');
INSERT INTO DEPARTMENTS VALUES ('32', 'Gers');
INSERT INTO DEPARTMENTS VALUES ('33', 'Gironde');
INSERT INTO DEPARTMENTS VALUES ('34', 'Hérault');
INSERT INTO DEPARTMENTS VALUES ('35', 'Ille-et-Vilaine');
INSERT INTO DEPARTMENTS VALUES ('36', 'Indre');
INSERT INTO DEPARTMENTS VALUES ('37', 'Indre-et-Loire');
INSERT INTO DEPARTMENTS VALUES ('38', 'Isère');
INSERT INTO DEPARTMENTS VALUES ('39', 'Jura');
INSERT INTO DEPARTMENTS VALUES ('40', 'Landes');
INSERT INTO DEPARTMENTS VALUES ('41', 'Loir-et-Cher');
INSERT INTO DEPARTMENTS VALUES ('42', 'Loire');
INSERT INTO DEPARTMENTS VALUES ('43', 'Haute-Loire');
INSERT INTO DEPARTMENTS VALUES ('44', 'Loire-Atlantique');
INSERT INTO DEPARTMENTS VALUES ('45', 'Loiret');
INSERT INTO DEPARTMENTS VALUES ('46', 'Lot');
INSERT INTO DEPARTMENTS VALUES ('47', 'Lot-et-Garonne');
INSERT INTO DEPARTMENTS VALUES ('48', 'Lozère');
INSERT INTO DEPARTMENTS VALUES ('49', 'Maine-et-Loire');
INSERT INTO DEPARTMENTS VALUES ('50', 'Manche');
INSERT INTO DEPARTMENTS VALUES ('51', 'Marne');
INSERT INTO DEPARTMENTS VALUES ('52', 'Haute-Marne');
INSERT INTO DEPARTMENTS VALUES ('53', 'Mayenne');
INSERT INTO DEPARTMENTS VALUES ('54', 'Meurthe-et-Moselle');
INSERT INTO DEPARTMENTS VALUES ('55', 'Meuse');
INSERT INTO DEPARTMENTS VALUES ('56', 'Morbihan');
INSERT INTO DEPARTMENTS VALUES ('57', 'Moselle');
INSERT INTO DEPARTMENTS VALUES ('58', 'Nièvre');
INSERT INTO DEPARTMENTS VALUES ('59', 'Nord');
INSERT INTO DEPARTMENTS VALUES ('60', 'Oise');
INSERT INTO DEPARTMENTS VALUES ('61', 'Orne');
INSERT INTO DEPARTMENTS VALUES ('62', 'Pas-de-Calais	');
INSERT INTO DEPARTMENTS VALUES ('63', 'Puy-de-Dôme');
INSERT INTO DEPARTMENTS VALUES ('64', 'Pyrénées-Atlantique');
INSERT INTO DEPARTMENTS VALUES ('65', 'Hautes-Pyrénées');
INSERT INTO DEPARTMENTS VALUES ('66', 'Pyrénées-Orientales');
INSERT INTO DEPARTMENTS VALUES ('67', 'Bas-Rhin');
INSERT INTO DEPARTMENTS VALUES ('68', 'Haut-Rhin');
INSERT INTO DEPARTMENTS VALUES ('69', 'Rhône');
INSERT INTO DEPARTMENTS VALUES ('70', 'Haute-Saône');
INSERT INTO DEPARTMENTS VALUES ('71', 'Saône-et-Loire');
INSERT INTO DEPARTMENTS VALUES ('72', 'Sarthe');
INSERT INTO DEPARTMENTS VALUES ('73', 'Savoie');
INSERT INTO DEPARTMENTS VALUES ('74', 'Haute-Savoie');
INSERT INTO DEPARTMENTS VALUES ('75', 'Paris');
INSERT INTO DEPARTMENTS VALUES ('76', 'Seine-Maritime');
INSERT INTO DEPARTMENTS VALUES ('77', 'Seine-et-Marne');
INSERT INTO DEPARTMENTS VALUES ('78', 'Yvelines');
INSERT INTO DEPARTMENTS VALUES ('79', 'Deux-Sèvres');
INSERT INTO DEPARTMENTS VALUES ('80', 'Somme');
INSERT INTO DEPARTMENTS VALUES ('81', 'Tarn');
INSERT INTO DEPARTMENTS VALUES ('82', 'Tarn-et-Garonne');
INSERT INTO DEPARTMENTS VALUES ('83', 'Var');
INSERT INTO DEPARTMENTS VALUES ('84', 'Vaucluse');
INSERT INTO DEPARTMENTS VALUES ('85', 'Vendée');
INSERT INTO DEPARTMENTS VALUES ('86', 'Vienne');
INSERT INTO DEPARTMENTS VALUES ('87', 'Haute-Vienne');
INSERT INTO DEPARTMENTS VALUES ('88', 'Vosges');
INSERT INTO DEPARTMENTS VALUES ('89', 'Yonne');
INSERT INTO DEPARTMENTS VALUES ('91', 'Essonne');
INSERT INTO DEPARTMENTS VALUES ('92', 'Hauts-de-Seine');
INSERT INTO DEPARTMENTS VALUES ('93', 'Seine-St-Denis');
INSERT INTO DEPARTMENTS VALUES ('94', 'Val-de-Marne');
INSERT INTO DEPARTMENTS VALUES ('95', 'Val-d\'Oise');