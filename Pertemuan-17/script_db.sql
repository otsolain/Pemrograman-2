CREATE DATABASE dbrentcar;
USE dbrentcar;

CREATE TABLE tbmobil (
    kode_mobil  VARCHAR(10)  NOT NULL PRIMARY KEY,
    nama_mobil  VARCHAR(50),
    merk        VARCHAR(30),
    tahun       INT,
    kapasitas   INT,
    harga_sewa  DOUBLE,
    status      VARCHAR(15)  DEFAULT 'Tersedia'  -- Tersedia / Disewa
);

CREATE TABLE tbcustomer (
    kode_customer VARCHAR(10)  NOT NULL PRIMARY KEY,
    nama          VARCHAR(50),
    alamat        VARCHAR(100),
    no_telepon    VARCHAR(15),
    no_ktp        VARCHAR(20)
);

CREATE TABLE tbsewa (
    kode_sewa       VARCHAR(15)  NOT NULL PRIMARY KEY,
    kode_customer   VARCHAR(10),
    kode_mobil      VARCHAR(10),
    tgl_sewa        DATE,
    tgl_kembali_rencana DATE,
    tgl_kembali_aktual  DATE,
    lama_sewa       INT,
    total_biaya     DOUBLE,
    status          VARCHAR(15)  DEFAULT 'Aktif',  -- Aktif / Selesai
    FOREIGN KEY (kode_customer) REFERENCES tbcustomer(kode_customer),
    FOREIGN KEY (kode_mobil)    REFERENCES tbmobil(kode_mobil)
);

CREATE TABLE tbuser (
    username VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(32),
    nama     VARCHAR(50)
);

INSERT INTO tbuser VALUES ('admin', MD5('admin'), 'Administrator');
INSERT INTO tbmobil VALUES ('M001','Avanza','Toyota',2022,7,350000,'Tersedia');
INSERT INTO tbmobil VALUES ('M002','Innova','Toyota',2023,8,500000,'Tersedia');
INSERT INTO tbmobil VALUES ('M003','Jazz','Honda',2021,5,300000,'Tersedia');
