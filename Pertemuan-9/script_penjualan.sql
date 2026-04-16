CREATE DATABASE db_penjualan;
USE db_penjualan;

CREATE TABLE barang (
    kd_barang  VARCHAR(10) NOT NULL PRIMARY KEY,
    nm_barang  VARCHAR(50),
    harga      DOUBLE,
    stok       INT
);

CREATE TABLE customer (
    kd_customer VARCHAR(10) NOT NULL PRIMARY KEY,
    nm_customer VARCHAR(50),
    alamat      VARCHAR(100),
    telp        VARCHAR(15)
);

CREATE TABLE supplier (
    kd_supplier VARCHAR(10) NOT NULL PRIMARY KEY,
    nm_supplier VARCHAR(50),
    alamat      VARCHAR(100),
    telp        VARCHAR(15)
);

CREATE TABLE transaksi (
    no_transaksi VARCHAR(15) NOT NULL PRIMARY KEY,
    kd_customer  VARCHAR(10),
    tgl_transaksi DATE,
    total        DOUBLE,
    FOREIGN KEY (kd_customer) REFERENCES customer(kd_customer)
);

CREATE TABLE detail_transaksi (
    id_detail    INT AUTO_INCREMENT PRIMARY KEY,
    no_transaksi VARCHAR(15),
    kd_barang    VARCHAR(10),
    qty          INT,
    subtotal     DOUBLE,
    FOREIGN KEY (no_transaksi) REFERENCES transaksi(no_transaksi),
    FOREIGN KEY (kd_barang)    REFERENCES barang(kd_barang)
);

CREATE TABLE inventory (
    id_inventory INT AUTO_INCREMENT PRIMARY KEY,
    kd_barang    VARCHAR(10),
    kd_supplier  VARCHAR(10),
    tgl_masuk    DATE,
    qty_masuk    INT,
    FOREIGN KEY (kd_barang)   REFERENCES barang(kd_barang),
    FOREIGN KEY (kd_supplier) REFERENCES supplier(kd_supplier)
);
