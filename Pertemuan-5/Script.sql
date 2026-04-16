-- Buat dan aktifkan database
CREATE DATABASE MHS;
USE MHS;

-- Buat tabel datamhs
CREATE TABLE datamhs (
    nim      VARCHAR(15) NOT NULL,
    nama     VARCHAR(30),
    semester INT,
    kelas    VARCHAR(1),
    PRIMARY KEY (nim)
);
