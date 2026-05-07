DROP DATABASE IF EXISTS klinicka_istrazivanja;
CREATE DATABASE klinicka_istrazivanja CHARACTER SET utf8mb4;
USE klinicka_istrazivanja;

CREATE TABLE bolnica (
    bolnica_id   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    naziv        VARCHAR(100) NOT NULL,
    adresa       VARCHAR(200) NOT NULL
);

CREATE TABLE protokol (
    protokol_id  INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    naziv        VARCHAR(100) NOT NULL,
    id_oznaka    VARCHAR(50)  NOT NULL UNIQUE,  -- identifikacioni podaci
    opis         TEXT
);

CREATE TABLE istrazivac (
    istrazivac_id  INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ime            VARCHAR(50) NOT NULL,
    prezime        VARCHAR(50) NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    kvalifikacije  TEXT,
    tip            ENUM('dizajner', 'izvodjac', 'oba') NOT NULL
);

CREATE TABLE tip_instrumenta (
    tip_id  INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    naziv   VARCHAR(100) NOT NULL,
    opis    TEXT
);

CREATE TABLE supstanca (
    supstanca_id     INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    naziv            VARCHAR(100) NOT NULL,
    hemijski_sastav  VARCHAR(200),
    izvor            VARCHAR(100),
    napomena         TEXT
);

CREATE TABLE pacijent (
    pacijent_id        INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ime                VARCHAR(50) NOT NULL,
    prezime            VARCHAR(50) NOT NULL,
    datum_rodjenja     DATE        NOT NULL,
    medicinska_istorija TEXT,
    napomena           TEXT
);

-- instrument pripada jednoj bolnici i jednom tipu
CREATE TABLE instrument (
    instrument_id     INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    bolnica_id        INT         NOT NULL,
    tip_id            INT         NOT NULL,
    datum_nabavke     DATE        NOT NULL,
    datum_proizvodnje DATE,
    FOREIGN KEY (bolnica_id) REFERENCES bolnica(bolnica_id),
    FOREIGN KEY (tip_id)     REFERENCES tip_instrumenta(tip_id)
);

-- pacijent može biti u više bolnica (many-to-many sa atributima)
CREATE TABLE bolnica_pacijent (
    bolnica_id       INT         NOT NULL,
    pacijent_id      INT         NOT NULL,
    status_prisustva ENUM('aktivan', 'otpušten', 'na_čekanju') NOT NULL DEFAULT 'na_čekanju',
    datum_prijema    DATE        NOT NULL,
    iskljucivo       BOOLEAN     NOT NULL DEFAULT FALSE,
    -- iskljucivo=TRUE znači pacijent ne sme paralelno učestvovati u drugim istraživanjima
    PRIMARY KEY (bolnica_id, pacijent_id),
    FOREIGN KEY (bolnica_id)  REFERENCES bolnica(bolnica_id),
    FOREIGN KEY (pacijent_id) REFERENCES pacijent(pacijent_id)
);

-- supstanca može biti u više bolnica (many-to-many sa atributima)
CREATE TABLE bolnica_supstanca (
    bolnica_id        INT         NOT NULL,
    supstanca_id      INT         NOT NULL,
    dostupna_kolicina DECIMAL(10,2) NOT NULL DEFAULT 0,
    status            ENUM('dostupna', 'nedostupna', 'naručena') NOT NULL DEFAULT 'dostupna',
    PRIMARY KEY (bolnica_id, supstanca_id),
    FOREIGN KEY (bolnica_id)   REFERENCES bolnica(bolnica_id),
    FOREIGN KEY (supstanca_id) REFERENCES supstanca(supstanca_id)
);

-- istraživanje ima protokol
CREATE TABLE istrazivanje (
    istrazivanje_id  INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    naziv            VARCHAR(200) NOT NULL,
    protokol_id      INT          NOT NULL,
    FOREIGN KEY (protokol_id) REFERENCES protokol(protokol_id)
);

-- dizajneri istraživanja (many-to-many)
CREATE TABLE dizajner_istrazivanja (
    istrazivanje_id  INT NOT NULL,
    istrazivac_id    INT NOT NULL,
    PRIMARY KEY (istrazivanje_id, istrazivac_id),
    FOREIGN KEY (istrazivanje_id) REFERENCES istrazivanje(istrazivanje_id),
    FOREIGN KEY (istrazivac_id)   REFERENCES istrazivac(istrazivac_id)
);

-- koje supstance su potrebne za istraživanje
CREATE TABLE istrazivanje_supstanca (
    istrazivanje_id   INT           NOT NULL,
    supstanca_id      INT           NOT NULL,
    potrebna_kolicina DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (istrazivanje_id, supstanca_id),
    FOREIGN KEY (istrazivanje_id) REFERENCES istrazivanje(istrazivanje_id),
    FOREIGN KEY (supstanca_id)    REFERENCES supstanca(supstanca_id)
);

-- koji tipovi instrumenata su potrebni za istraživanje
CREATE TABLE istrazivanje_tip_instrumenta (
    istrazivanje_id  INT NOT NULL,
    tip_id           INT NOT NULL,
    PRIMARY KEY (istrazivanje_id, tip_id),
    FOREIGN KEY (istrazivanje_id) REFERENCES istrazivanje(istrazivanje_id),
    FOREIGN KEY (tip_id)          REFERENCES tip_instrumenta(tip_id)
);

-- izvođenje = konkretno sprovođenje istraživanja u bolnici
CREATE TABLE izvodjenje (
    izvodjenje_id    INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    istrazivanje_id  INT  NOT NULL,
    bolnica_id       INT  NOT NULL,
    datum            DATE NOT NULL,
    status           ENUM('planirano','zapoceto','otkazano',
                          'uspesno','neuspesno') NOT NULL DEFAULT 'planirano',
    FOREIGN KEY (istrazivanje_id) REFERENCES istrazivanje(istrazivanje_id),
    FOREIGN KEY (bolnica_id)      REFERENCES bolnica(bolnica_id)
);

-- koji istraživači učestvuju u izvođenju i u kojoj ulozi
CREATE TABLE ucesnik_izvodjenja (
    izvodjenje_id  INT          NOT NULL,
    istrazivac_id  INT          NOT NULL,
    uloga          VARCHAR(100) NOT NULL,
    putanja_beleske VARCHAR(300),
    PRIMARY KEY (izvodjenje_id, istrazivac_id),
    FOREIGN KEY (izvodjenje_id) REFERENCES izvodjenje(izvodjenje_id),
    FOREIGN KEY (istrazivac_id) REFERENCES istrazivac(istrazivac_id)
);

-- termin (sesija) vezan za jedno izvođenje
CREATE TABLE termin (
    termin_id      INT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    izvodjenje_id  INT      NOT NULL,
    datum          DATE     NOT NULL,
    vreme_pocetka  TIME     NOT NULL,
    vreme_zavrsetka TIME    NOT NULL,
    FOREIGN KEY (izvodjenje_id) REFERENCES izvodjenje(izvodjenje_id)
);

-- utrošak supstanci po terminu — inventar se ažurira triggerom
CREATE TABLE utrosak_supstance (
    termin_id    INT           NOT NULL,
    supstanca_id INT           NOT NULL,
    bolnica_id   INT           NOT NULL,
    kolicina     DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (termin_id, supstanca_id),
    FOREIGN KEY (termin_id)    REFERENCES termin(termin_id),
    FOREIGN KEY (supstanca_id, bolnica_id)
        REFERENCES bolnica_supstanca(supstanca_id, bolnica_id)
);

-- utrošak instrumenata po terminu
CREATE TABLE utrosak_instrumenta (
    termin_id     INT NOT NULL,
    instrument_id INT NOT NULL,
    PRIMARY KEY (termin_id, instrument_id),
    FOREIGN KEY (termin_id)     REFERENCES termin(termin_id),
    FOREIGN KEY (instrument_id) REFERENCES instrument(instrument_id)
);