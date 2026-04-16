CREATE DATABASE dbaplikasipenilaianmahasiswa;
USE dbaplikasipenilaianmahasiswa;

CREATE TABLE tbmahasiswa (
    nim      VARCHAR(15) NOT NULL PRIMARY KEY,
    nama     VARCHAR(50),
    semester INT,
    kelas    VARCHAR(5),
    password VARCHAR(32)
);

CREATE TABLE tbmatakuliah (
    kode_mk  VARCHAR(10) NOT NULL PRIMARY KEY,
    nama_mk  VARCHAR(50),
    sks      INT
);

CREATE TABLE tbuser (
    username VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(32),
    nama     VARCHAR(50)
);

INSERT INTO tbuser VALUES ('admin', MD5('admin'), 'Administrator');
