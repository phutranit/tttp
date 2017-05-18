-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.18-0ubuntu0.16.04.1 - (Ubuntu)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.4.0.5164
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for tttp
DROP DATABASE IF EXISTS `tttp`;
CREATE DATABASE IF NOT EXISTS `tttp` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_vietnamese_ci */;
USE `tttp`;

-- Dumping structure for table tttp.capcoquanquanly
DROP TABLE IF EXISTS `capcoquanquanly`;
CREATE TABLE IF NOT EXISTS `capcoquanquanly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKql46k6sa1y99p4irrs1y4gm` (`nguoiSua_id`),
  KEY `FKnbmssluv9b76okncln5bv6ve8` (`nguoiTao_id`),
  KEY `FK226iui4n9f6kwwwt3ose76fl2` (`cha_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.capcoquanquanly: 16 rows
/*!40000 ALTER TABLE `capcoquanquanly` DISABLE KEYS */;
INSERT INTO `capcoquanquanly` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `cha_id`) VALUES
	(1, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '001', 'Cấp Chính phủ', 'Cấp Chính phủ', 1, 1, NULL),
	(2, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '002', 'UBND Tỉnh, TP', 'UBND Tỉnh, TP', 1, 1, 1),
	(3, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '003', 'UBND Quận, Huyện', 'UBND Quận, Huyện', 1, 1, 2),
	(4, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '004', 'UBND Phường, Xã, Thị trấn', 'UBND Phường, Xã, Thị trấn', 1, 1, 3),
	(5, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '005', 'Sở ban ngành', 'Sở ban ngành', 1, 1, 2),
	(6, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '006', 'Phòng ban', 'Phòng ban', 1, 1, 5),
	(7, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '007', 'Chi cục', 'Chi cục', 1, 1, 5),
	(8, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '008', 'Cấp Bộ, Ngành', 'Cấp Bộ, Ngành', 1, 1, 1),
	(9, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '009', 'Tổng cục', 'Tổng cục', 1, 1, 8),
	(10, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '010', 'Cục', 'Cục', 1, 1, 8),
	(11, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '011', 'Vụ', 'Vụ', 1, 1, 8),
	(12, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '012', 'Trung tâm', 'Trung tâm', 1, 1, 8),
	(13, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '013', 'Cục thuộc Tổng cục', 'Cục thuộc Tổng cục', 1, 1, 9),
	(14, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '014', 'Vụ thuộc Tổng cục', 'Vụ thuộc Tổng cục', 1, 1, 9),
	(15, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '015', 'Trung tâm thuộc Tổng cục', 'Trung tâm thuộc Tổng cục', 1, 1, 9),
	(16, b'0', '2017-04-23 13:55:52', '2017-04-23 13:55:52', '016', 'Cơ quan hành chính sự nghiệp', 'Cơ quan hành chính sự nghiệp', 1, 1, 5);
/*!40000 ALTER TABLE `capcoquanquanly` ENABLE KEYS */;

-- Dumping structure for table tttp.capdonvihanhchinh
DROP TABLE IF EXISTS `capdonvihanhchinh`;
CREATE TABLE IF NOT EXISTS `capdonvihanhchinh` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3fh3i3p8mx3dnvle2fd1g3inc` (`nguoiSua_id`),
  KEY `FKfr9ltxr889al8r58kide6qp5q` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.capdonvihanhchinh: 9 rows
/*!40000 ALTER TABLE `capdonvihanhchinh` DISABLE KEYS */;
INSERT INTO `capdonvihanhchinh` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '001', 'Tỉnh', 'Cấp tỉnh', 1, 1),
	(2, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '002', 'Huyện', 'Cấp Huyện', 1, 1),
	(3, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '003', 'Xã', 'Cấp Xã', 1, 1),
	(4, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '004', 'TP', 'TP trực thuộc TW', 1, 1),
	(5, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '005', 'Quận', 'Cấp Quận', 1, 1),
	(6, b'0', '2017-04-23 11:06:20', '2017-04-23 11:06:22', '006', 'Phường', 'Cấp Phường', 1, 1),
	(7, b'0', '2017-04-23 11:06:22', '2017-04-23 11:06:22', '007', 'Thị trấn', 'Thị trấn', 1, 1),
	(8, b'0', '2017-04-23 11:06:22', '2017-04-23 11:06:22', '008', 'Thị xã', 'Thị xã', 1, 1),
	(9, b'0', '2017-04-23 11:06:22', '2017-04-23 11:06:22', '009', 'TP trực thuộc tỉnh', 'TP trực thuộc tỉnh', 1, 1);
/*!40000 ALTER TABLE `capdonvihanhchinh` ENABLE KEYS */;

-- Dumping structure for table tttp.chucvu
DROP TABLE IF EXISTS `chucvu`;
CREATE TABLE IF NOT EXISTS `chucvu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `lanhDao` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa4m4n19mqfls51jse63v2ncyw` (`nguoiSua_id`),
  KEY `FK2dhtqx8qhmlx7c0gf4tl03jmf` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.chucvu: 4 rows
/*!40000 ALTER TABLE `chucvu` DISABLE KEYS */;
INSERT INTO `chucvu` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `lanhDao`) VALUES
	(1, b'0', '2017-05-17 19:32:37', '2017-04-24 10:38:09', 'Lãnh đạo tiếp dân', 'Lãnh đạo tiếp dân', 1, 1, b'1'),
	(2, b'0', '2017-05-17 19:33:09', '2017-04-24 10:38:15', 'Trưởng phòng xử lý đơn', 'Trưởng phòng xử lý đơn', 1, 1, b'0'),
	(3, b'0', '2017-05-17 19:33:01', '2017-05-03 15:56:59', 'Chuyên viên xử lý đơn', 'Chuyên viên xử lý đơn', 1, 1, b'0'),
	(4, b'0', '2017-05-17 19:11:34', '2017-05-03 15:59:46', 'Văn thư', 'Văn thư', 1, 1, b'0');
/*!40000 ALTER TABLE `chucvu` ENABLE KEYS */;

-- Dumping structure for table tttp.chungchihanhnghe
DROP TABLE IF EXISTS `chungchihanhnghe`;
CREATE TABLE IF NOT EXISTS `chungchihanhnghe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `duongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `congDan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpalqugnxv6moc20ivs1sjybbh` (`nguoiSua_id`),
  KEY `FKl9aqo0q30ficruu92la0xnkkh` (`nguoiTao_id`),
  KEY `FK34nvh79x3thvpo652yyjd35i0` (`congDan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.chungchihanhnghe: 0 rows
/*!40000 ALTER TABLE `chungchihanhnghe` DISABLE KEYS */;
/*!40000 ALTER TABLE `chungchihanhnghe` ENABLE KEYS */;

-- Dumping structure for table tttp.congchuc
DROP TABLE IF EXISTS `congchuc`;
CREATE TABLE IF NOT EXISTS `congchuc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `diaChi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `dienThoai` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `gioiTinh` bit(1) NOT NULL,
  `hoVaTen` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ngayCap` datetime DEFAULT NULL,
  `ngaySinh` datetime DEFAULT NULL,
  `soCMNDHoCHieu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `chucVu_id` bigint(20) DEFAULT NULL,
  `coQuanQuanLy_id` bigint(20) DEFAULT NULL,
  `nguoiDung_id` bigint(20) NOT NULL,
  `noiCapCMND_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt6pg6su8p9bg5h1cgxswpkam5` (`nguoiSua_id`),
  KEY `FK5vpxg545trubavoaane211k5p` (`nguoiTao_id`),
  KEY `FKd2p1qcfhvws57wdftekjkhund` (`chucVu_id`),
  KEY `FKsedbgmc9y7moxeg2byas7gafw` (`coQuanQuanLy_id`),
  KEY `FKagb75px243w4ihob0ff4xg674` (`nguoiDung_id`),
  KEY `FK6wvul42u3plmn4lyt0dujj7nw` (`noiCapCMND_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.congchuc: 11 rows
/*!40000 ALTER TABLE `congchuc` DISABLE KEYS */;
INSERT INTO `congchuc` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `diaChi`, `dienThoai`, `email`, `gioiTinh`, `hoVaTen`, `ma`, `ngayCap`, `ngaySinh`, `soCMNDHoCHieu`, `nguoiSua_id`, `nguoiTao_id`, `chucVu_id`, `coQuanQuanLy_id`, `nguoiDung_id`, `noiCapCMND_id`) VALUES
	(1, b'0', '2017-05-17 19:48:22', '2017-04-24 10:49:48', '24 Trần Phú', '01206189116', 'admin-tttp@gmail.com', b'1', 'Administrator', '001', '2012-11-13 00:00:00', '1994-06-24 00:00:00', '201702245', 1, 1, 1, 2, 1, 86),
	(2, b'0', '2017-05-17 19:15:55', '2017-05-03 15:30:58', 'K377/34 Hải phòng', '01206189116', 'namtvd@greenglobal.vn', b'1', 'Tran Vo Dinh Nam', '002', '2012-11-18 13:00:00', '1994-06-29 13:00:00', '201702275', 1, 1, 2, 90, 2, 86),
	(3, b'0', '2017-05-17 19:16:16', '2017-05-03 15:51:28', 'Lê Duẫn', '01206189116', 'thanhpx@greenglobal.vn', b'1', 'Phạm Xuân Thành', '003', '2012-11-18 20:00:00', '1994-06-29 20:00:00', '201702276', 1, 1, 3, 90, 4, 86),
	(4, b'0', '2017-05-17 19:22:32', '2017-05-03 15:52:46', 'Lê Duẫn', '01206189116', 'anvt@greenglobal.vn', b'1', 'Võ Tiến An', '004', '1969-12-30 06:00:00', '1994-05-30 06:00:00', '201702264', 1, 1, 4, 90, 5, 86),
	(5, b'0', '2017-05-17 19:15:47', '2017-05-17 19:06:57', 'Trần Phú', '01206189445', 'vinhlv@gmail.com', b'1', 'Lý Văn Vinh', '005', '2010-06-07 10:00:00', '1984-06-06 10:00:00', '201702245', 1, 1, 3, 90, 6, 86),
	(6, b'0', '2017-05-17 19:42:57', '2017-05-17 19:42:57', 'Hoàng Hoa Thám', '01206185446', 'thanghq@gmail.com', b'1', 'Huỳnh Quang Thắng', '006', '2017-05-08 17:00:00', '2017-05-01 17:00:00', '201702245', 1, 1, 3, 90, 7, 86),
	(7, b'0', '2017-05-17 19:43:49', '2017-05-17 19:43:49', 'Hùng Vương', '01204548796', 'toantt@gmail.com', b'1', 'Tôn Thất Toàn', '007', '2017-05-09 17:00:00', '2017-05-08 17:00:00', '201451157', 1, 1, 3, 90, 8, 86),
	(8, b'0', '2017-05-17 19:44:59', '2017-05-17 19:44:59', 'Phan Châu Trinh', '01206215448', 'trandtb@gmail.com', b'1', 'Đinh Trương Bảo Trân', '008', '2017-05-02 17:00:00', '2017-04-30 17:00:00', '201451157', 1, 1, 3, 90, 9, 86),
	(9, b'0', '2017-05-17 19:47:34', '2017-05-17 19:47:34', 'Ngô Quyền', '01206541254', 'thanhhx@gmail.com', b'1', 'Hà Xuân Thành', '009', '2017-05-02 17:00:00', '2017-05-10 17:00:00', '201548854', 1, 1, 1, 2, 10, 86),
	(10, b'0', '2017-05-17 19:54:26', '2017-05-17 19:52:42', 'Trần Phú', '01206523664', 'thienpd@gmail.com', b'1', 'Phùng Duy Thiện', '010', '2017-05-17 10:00:00', '2017-05-08 10:00:00', '201456625', 1, 1, 1, 2, 11, 86),
	(11, b'0', '2017-05-17 19:54:33', '2017-05-17 19:53:38', 'Trần Phú', '01206214552', 'vutx@gmail.com', b'1', 'Trần Xuân Vũ', '011', '2017-05-10 10:00:00', '2017-05-01 10:00:00', '201702215', 1, 1, 1, 2, 12, 86);
/*!40000 ALTER TABLE `congchuc` ENABLE KEYS */;

-- Dumping structure for table tttp.congdan
DROP TABLE IF EXISTS `congdan`;
CREATE TABLE IF NOT EXISTS `congdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `diaChi` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `gioiTinh` bit(1) NOT NULL,
  `hoVaTen` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ngayCap` datetime DEFAULT NULL,
  `ngaySinh` datetime DEFAULT NULL,
  `soCMNDHoChieu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soDienThoai` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `danToc_id` bigint(20) DEFAULT NULL,
  `noiCapCMND_id` bigint(20) DEFAULT NULL,
  `phuongXa_id` bigint(20) NOT NULL,
  `quanHuyen_id` bigint(20) NOT NULL,
  `quocTich_id` bigint(20) DEFAULT NULL,
  `tinhThanh_id` bigint(20) NOT NULL,
  `toDanPho_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ceaui7e987tj9f7mltj76frh` (`nguoiSua_id`),
  KEY `FKlkhlre4pd0j9hh29fl0ukl4qo` (`nguoiTao_id`),
  KEY `FKpluk9dnwforiccib6l9hk8gek` (`danToc_id`),
  KEY `FKsw4o27m7new9e192nx2qglu65` (`noiCapCMND_id`),
  KEY `FK2lv30se81ev0gj0qyii981rsh` (`phuongXa_id`),
  KEY `FKioxhrhif5gvxdrchn9vo0qobh` (`quanHuyen_id`),
  KEY `FKd6x82c9yfp37e143ucaycy1l3` (`quocTich_id`),
  KEY `FKg6c7j69ne2q6tka6qtj0we9nc` (`tinhThanh_id`),
  KEY `FK2pa8vhiyhcgwvgbuu14u911o5` (`toDanPho_id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.congdan: 22 rows
/*!40000 ALTER TABLE `congdan` DISABLE KEYS */;
INSERT INTO `congdan` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `diaChi`, `gioiTinh`, `hoVaTen`, `ngayCap`, `ngaySinh`, `soCMNDHoChieu`, `soDienThoai`, `nguoiSua_id`, `nguoiTao_id`, `danToc_id`, `noiCapCMND_id`, `phuongXa_id`, `quanHuyen_id`, `quocTich_id`, `tinhThanh_id`, `toDanPho_id`) VALUES
	(17, b'0', '2017-05-17 09:45:37', '2017-05-17 09:45:37', '221 Hà Huy Tập nối dài', b'0', 'Huỳnh Bảo Ngọc', '2011-10-21 00:00:00', '1989-09-02 00:00:00', '201574408', '0932110442', 1, 1, 1, 86, 31, 15, 1, 9, 116),
	(16, b'0', '2017-05-16 20:44:33', '2017-05-16 20:44:21', 'K377/34 Hải Phòng', b'1', 'Trần Võ Đình Nam', '2010-11-19 17:00:00', '1994-01-06 17:00:00', '201702275', '01206189116', 1, 1, 1, 86, 31, 15, 1, 9, 107),
	(18, b'0', '2017-05-17 11:23:07', '2017-05-17 10:05:05', '20 Nguyễn Huy Tưởng', b'1', 'Lê Văn Quý', NULL, '1975-09-29 00:00:00', '201425632', '01206116305', 1, 1, NULL, 86, 26, 14, NULL, 9, 61),
	(19, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '35 Phan Chu Trinh', b'1', 'Võ Hoài Nam', '2010-12-22 00:00:00', '1983-10-05 00:00:00', '201356214', '0982137146', 1, 1, 1, 86, 40, 16, 1, 9, 197),
	(20, b'0', '2017-05-17 19:38:44', '2017-05-17 10:05:05', '12 Cù Chính Lan', b'0', 'Đỗ Bảo Trân', NULL, NULL, '201356214', '0982137146', 1, 1, 1, 86, 35, 15, 1, 9, 147),
	(21, b'0', '2017-05-17 11:23:07', '2017-05-17 10:05:05', '15 Tản Đà', b'1', 'Trần Bá Hòa', NULL, '1975-09-29 00:00:00', '201115642', '01206116305', 1, 1, NULL, 86, 34, 15, NULL, 9, 140),
	(22, b'0', '2017-05-17 12:36:17', '2017-05-17 10:05:05', '23 Phan Tứ', b'1', 'Đặng Đình Cường', NULL, NULL, '201575538', '0932110442', 1, 1, NULL, 86, 57, 18, NULL, 9, 375),
	(23, b'0', '2017-05-17 11:23:07', '2017-05-17 10:05:05', '30 Quang Trung', b'0', 'Nguyễn Thị Kim', NULL, '1983-10-05 00:00:00', '201245578', '0982137146', 1, 1, NULL, 86, 40, 16, NULL, 9, 197),
	(24, b'0', '2017-05-17 20:16:43', '2017-05-17 10:14:19', '20 Nguyễn Huy Tưởng', b'1', 'Nguyễn Văn Khải', NULL, NULL, '201425632', '01206116305', 1, 1, 1, 86, 26, 14, 1, 9, 61),
	(25, b'0', '2017-05-17 10:40:57', '2017-05-17 10:40:57', '88 Cần Giuộc', b'0', 'Nguyễn Thị Trung Chinh', '2012-01-24 00:00:00', '1970-02-10 00:00:00', '201052987', '01206116305', 1, 1, 1, 86, 36, 15, 1, 9, 165),
	(26, b'0', '2017-05-17 12:35:12', '2017-05-17 11:13:00', '221 Trường Chinh', b'0', 'Phạm Thị Cẩm Nhung', NULL, '1993-09-02 00:00:00', '201648215', '01214645823', 1, 1, 1, 86, 35, 15, 1, 9, 155),
	(27, b'0', '2017-05-17 12:36:17', '2017-05-17 11:23:07', '70 Nguyễn Lương Bằng', b'1', 'Đinh Nguyễn Anh Khuê', NULL, '1993-10-05 00:00:00', '201356214', '0982137146', 1, 1, 1, 86, 24, 14, 1, 9, 40),
	(28, b'0', '2017-05-17 11:36:30', '2017-05-17 11:36:30', '70 Nguyễn Lương Bằng', b'0', 'Dương Thanh Ái Quyên', '2010-12-23 00:00:00', '1993-09-29 00:00:00', '201425632', '01206116305', 1, 1, 1, 86, 24, 14, 1, 9, 40),
	(29, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', '534 Trần Cao Vân', b'1', 'Huỳnh Quốc Đạt', '2011-10-21 00:00:00', '1987-09-23 00:00:00', '201537408', '0906345210', 1, 1, 1, 86, 34, 15, 1, 9, 145),
	(30, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '30 Bà Huyện Thanh Quan', b'1', 'Lê Quang Tuấn', '2015-12-21 00:00:00', '1987-09-20 00:00:00', '201575786', '01202052973', 1, 1, NULL, 86, 57, 18, NULL, 9, 371),
	(31, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '24 Quang Trung', b'1', 'Nguyễn Tiến Bá', '2010-12-02 00:00:00', '1983-10-05 00:00:00', '205356214', '0905234123', 1, 1, 1, 86, 40, 16, 1, 9, 197),
	(32, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '50 Hà Huy Tập', b'0', 'Nguyễn Hồng Quyên', '2013-12-22 00:00:00', '1981-06-05 00:00:00', '201356467', '0903300143', 1, 1, NULL, 86, 35, 15, NULL, 9, 147),
	(33, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '43 Hồ Tùng Mậu', b'1', 'Nguyễn Bá Quang', '1999-12-23 00:00:00', '1970-08-19 00:00:00', '201422432', '0973389168', 1, 1, NULL, 86, 26, 14, NULL, 9, 61),
	(34, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '30 Quang Trung', b'0', 'Trần Thị Nguyệt', '1998-12-22 00:00:00', '1976-10-05 00:00:00', '201245523', '0987371772', 1, 1, NULL, 86, 40, 16, NULL, 9, 197),
	(35, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '15 Tản Đà', b'1', 'Huỳnh Văn Chương', '1991-12-23 00:00:00', '1965-08-29 00:00:00', '200213224', '01636629861', 1, 1, NULL, 86, 34, 15, NULL, 9, 140),
	(36, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', '23 Ngô Thì Nhậm', b'1', 'Trần Quang Diệu', '2014-03-09 00:00:00', '1978-09-29 00:00:00', '204025632', '01261246721', 1, 1, 1, 86, 26, 14, 1, 9, 61),
	(37, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', '12 Hàm Nghi', b'0', 'Nguyễn Thị Nụ', '2012-01-24 00:00:00', '1970-02-10 00:00:00', '201052966', '01201090931', 1, 1, 1, 86, 34, 15, 1, 9, 138);
/*!40000 ALTER TABLE `congdan` ENABLE KEYS */;

-- Dumping structure for table tttp.coquanquanly
DROP TABLE IF EXISTS `coquanquanly`;
CREATE TABLE IF NOT EXISTS `coquanquanly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `capCoQuanQuanLy_id` bigint(20) NOT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  `donViHanhChinh_id` bigint(20) NOT NULL,
  `loaiCoQuanQuanLy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5xnhgo4gm076ij94qfk2yxvw` (`nguoiSua_id`),
  KEY `FKbda07equ39nxg1exbow209u1t` (`nguoiTao_id`),
  KEY `FKjxxgfun75pmqjp1upgenk7q3p` (`capCoQuanQuanLy_id`),
  KEY `FK6g0yfb2vr2lrf7a96p11mb11j` (`cha_id`),
  KEY `FKqvda4xoqiflqmtx16b3df104a` (`donViHanhChinh_id`),
  KEY `FK7e22uj5l0xg0f6lgkb1lee6nk` (`loaiCoQuanQuanLy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.coquanquanly: 92 rows
/*!40000 ALTER TABLE `coquanquanly` DISABLE KEYS */;
INSERT INTO `coquanquanly` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `capCoQuanQuanLy_id`, `cha_id`, `donViHanhChinh_id`, `loaiCoQuanQuanLy_id`) VALUES
	(1, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '001', 'UBND Thành phố Đà Nẵng', 'UBND Thành phố Đà Nẵng', 1, 1, 2, NULL, 9, NULL),
	(2, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '002', 'Thanh tra thành phố', 'Thanh tra thành phố', 1, 1, 5, 1, 9, NULL),
	(3, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '003', 'Sở Công Thương', 'Sở Công Thương', 1, 1, 5, 1, 9, NULL),
	(4, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '004', 'Sở Giao thông Vận tải', 'Sở Giao thông Vận tải', 1, 1, 5, 1, 9, NULL),
	(5, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '005', 'Sở Giáo dục và Đào tạo', 'Sở Giáo dục và Đào tạo', 1, 1, 5, 1, 9, NULL),
	(6, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '006', 'Sở Khoa học và Công nghệ', 'Sở Khoa học và Công nghệ', 1, 1, 5, 1, 9, NULL),
	(7, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '007', 'Sở Kế hoạch và Đầu tư', 'Sở Kế hoạch và Đầu tư', 1, 1, 5, 1, 9, NULL),
	(8, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '008', 'Sở Lao động, Thương binh và Xã hội', 'Sở Lao động, Thương binh và Xã hội', 1, 1, 5, 1, 9, NULL),
	(9, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '009', 'Sở Ngoại vụ', 'Sở Ngoại vụ', 1, 1, 5, 1, 9, NULL),
	(10, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '010', 'Sở Nông nghiệp và Phát triển nông thôn', 'Sở Nông nghiệp và Phát triển nông thôn', 1, 1, 5, 1, 9, NULL),
	(11, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '011', 'Sở Nội vụ', 'Sở Nội vụ', 1, 1, 5, 1, 9, NULL),
	(12, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '012', 'Sở Thông tin và Truyền thông', 'Sở Thông tin và Truyền thông', 1, 1, 5, 1, 9, NULL),
	(13, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '013', 'Sở Tài chính', 'Sở Tài chính', 1, 1, 5, 1, 9, NULL),
	(14, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '014', 'Sở Tài nguyên và Môi trường', 'Sở Tài nguyên và Môi trường', 1, 1, 5, 1, 9, NULL),
	(15, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '015', 'Sở Tư pháp', 'Sở Tư pháp', 1, 1, 5, 1, 9, NULL),
	(16, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '016', 'Sở Văn hóa, Thể thao và Du lịch', 'Sở Văn hóa, Thể thao và Du lịch', 1, 1, 5, 1, 9, NULL),
	(17, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '017', 'Sở Xây dựng', 'Sở Xây dựng', 1, 1, 5, 1, 9, NULL),
	(18, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '018', 'Sở Y tế', 'Sở Y tế', 1, 1, 5, 1, 9, NULL),
	(19, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '019', 'UBND quận Liên Chiểu', 'UBND quận Liên Chiểu', 1, 1, 3, 1, 14, NULL),
	(20, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '020', 'UBND quận Thanh Khê', 'UBND quận Thanh Khê', 1, 1, 3, 1, 15, NULL),
	(21, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '021', 'UBND quận Hải Châu', 'UBND quận Hải Châu', 1, 1, 3, 1, 16, NULL),
	(22, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '022', 'UBND quận Sơn Trà', 'UBND quận Sơn Trà', 1, 1, 3, 1, 17, NULL),
	(23, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '023', 'UBND quận Ngũ Hành Sơn', 'UBND quận Ngũ Hành Sơn', 1, 1, 3, 1, 18, NULL),
	(24, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '024', 'UBND quận Cẩm Lệ', 'UBND quận Cẩm Lệ', 1, 1, 3, 1, 19, NULL),
	(25, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '025', 'UBND huyện Hòa Vang', 'UBND huyện Hòa Vang', 1, 1, 3, 1, 20, NULL),
	(26, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '026', 'UBND huyện Hoàng Sa', 'UBND huyện Hoàng Sa', 1, 1, 3, 1, 21, NULL),
	(27, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '027', 'UBND phường Hòa Hiệp Bắc', 'UBND phường Hòa Hiệp Bắc', 1, 1, 4, 19, 22, NULL),
	(28, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '028', 'UBND phường Hòa Hiệp Nam', 'UBND phường Hòa Hiệp Nam', 1, 1, 4, 19, 23, NULL),
	(29, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '029', 'UBND Phường Hòa Khánh Bắc', 'UBND Phường Hòa Khánh Bắc', 1, 1, 4, 19, 24, NULL),
	(30, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '030', 'UBND phường Hòa Khánh Nam', 'UBND phường Hòa Khánh Nam', 1, 1, 4, 19, 25, NULL),
	(31, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '031', 'UBND phường Hòa Minh', 'UBND phường Hòa Minh', 1, 1, 4, 19, 26, NULL),
	(32, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '032', 'UBND phường Tam Thuận', 'UBND phường Tam Thuận', 1, 1, 4, 20, 27, NULL),
	(33, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '033', 'UBND phường Thanh Khê Tây', 'UBND phường Thanh Khê Tây', 1, 1, 4, 20, 28, NULL),
	(34, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '034', 'UBND phường Thanh Khê Đông', 'UBND phường Thanh Khê Đông', 1, 1, 4, 20, 29, NULL),
	(35, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '035', 'UBND phường Xuân Hà', 'UBND phường Xuân Hà', 1, 1, 4, 20, 30, NULL),
	(36, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '036', 'UBND phường Tân Chính', 'UBND phường Tân Chính', 1, 1, 4, 20, 31, NULL),
	(37, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '037', 'UBND phường Chính Gián', 'UBND phường Chính Gián', 1, 1, 4, 20, 32, NULL),
	(38, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '038', 'UBND phường Vĩnh Trung', 'UBND phường Vĩnh Trung', 1, 1, 4, 20, 33, NULL),
	(39, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '039', 'UBND phường Thạc Gián', 'UBND phường Thạc Gián', 1, 1, 4, 20, 34, NULL),
	(40, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '040', 'UBND phường An Khê', 'UBND phường An Khê', 1, 1, 4, 20, 35, NULL),
	(41, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '041', 'UBND phường Hòa Khê', 'UBND phường Hòa Khê', 1, 1, 4, 20, 36, NULL),
	(42, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '042', 'UBND phường Thanh Bình', 'UBND phường Thanh Bình', 1, 1, 4, 21, 37, NULL),
	(43, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '043', 'UBND phường Thuận Phước', 'UBND phường Thuận Phước', 1, 1, 4, 21, 38, NULL),
	(44, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '044', 'UBND phường Thạch Thang', 'UBND phường Thạch Thang', 1, 1, 4, 21, 39, NULL),
	(45, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '045', 'UBND phường Hải Châu I', 'UBND phường Hải Châu I', 1, 1, 4, 21, 40, NULL),
	(46, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '046', 'UBND phường Hải Châu II', 'UBND phường Hải Châu II', 1, 1, 4, 21, 41, NULL),
	(47, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '047', 'UBND phường Phước Ninh', 'UBND phường Phước Ninh', 1, 1, 4, 21, 42, NULL),
	(48, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '048', 'UBND phường Hòa Thuận Tây', 'UBND phường Hòa Thuận Tây', 1, 1, 4, 21, 43, NULL),
	(49, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '049', 'UBND phường Hòa Thuận Đông', 'UBND phường Hòa Thuận Đông', 1, 1, 4, 21, 44, NULL),
	(50, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '050', 'UBND phường Nam Dương', 'UBND phường Nam Dương', 1, 1, 4, 21, 45, NULL),
	(51, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '051', 'UBND phường Bình Hiên', 'UBND phường Bình Hiên', 1, 1, 4, 21, 46, NULL),
	(52, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '052', 'UBND phường Bình Thuận', 'UBND phường Bình Thuận', 1, 1, 4, 21, 47, NULL),
	(53, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '053', 'UBND phường Hòa Cường Bắc', 'UBND phường Hòa Cường Bắc', 1, 1, 4, 21, 48, NULL),
	(54, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '054', 'UBND phường Hòa Cường Nam', 'UBND phường Hòa Cường Nam', 1, 1, 4, 21, 49, NULL),
	(55, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '055', 'UBND phường Thọ Quang', 'UBND phường Thọ Quang', 1, 1, 4, 22, 50, NULL),
	(56, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '056', 'UBND phường Nại Hiên Đông', 'UBND phường Nại Hiên Đông', 1, 1, 4, 22, 51, NULL),
	(57, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '057', 'UBND phường Mân Thái', 'UBND phường Mân Thái', 1, 1, 4, 22, 52, NULL),
	(58, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '058', 'UBND phường An Hải Bắc', 'UBND phường An Hải Bắc', 1, 1, 4, 22, 53, NULL),
	(59, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '059', 'UBND phường Phước Mỹ', 'UBND phường Phước Mỹ', 1, 1, 4, 22, 54, NULL),
	(60, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '060', 'UBND phường An Hải Tây', 'UBND phường An Hải Tây', 1, 1, 4, 22, 55, NULL),
	(61, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '061', 'UBND phường An Hải Đông', 'UBND phường An Hải Đông', 1, 1, 4, 22, 56, NULL),
	(62, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '062', 'UBND phường Mỹ An', 'UBND phường Mỹ An', 1, 1, 4, 23, 57, NULL),
	(63, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '063', 'UBND phường Khuê Mỹ', 'UBND phường Khuê Mỹ', 1, 1, 4, 23, 58, NULL),
	(64, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '064', 'UBND phường Hòa Quý', 'UBND phường Hòa Quý', 1, 1, 4, 23, 59, NULL),
	(65, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '065', 'UBND phường Hòa Hải', 'UBND phường Hòa Hải', 1, 1, 4, 23, 60, NULL),
	(66, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '066', 'UBND phường Khuê Trung', 'UBND phường Khuê Trung', 1, 1, 4, 24, 61, NULL),
	(67, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '067', 'UBND phường Hòa Phát', 'UBND phường Hòa Phát', 1, 1, 4, 24, 67, NULL),
	(68, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '068', 'UBND phường Hòa An', 'UBND phường Hòa An', 1, 1, 4, 24, 68, NULL),
	(69, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '069', 'UBND phường Hòa Thọ Tây', 'UBND phường Hòa Thọ Tây', 1, 1, 4, 24, 69, NULL),
	(70, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '070', 'UBND phường Hòa Thọ Đông', 'UBND phường Hòa Thọ Đông', 1, 1, 4, 24, 70, NULL),
	(71, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '071', 'UBND phường Hòa Xuân', 'UBND phường Hòa Xuân', 1, 1, 4, 24, 71, NULL),
	(72, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '072', 'UBND xã Hòa Bắc', 'UBND xã Hòa Bắc', 1, 1, 4, 25, 72, NULL),
	(73, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '073', 'UBND xã Hòa Liên', 'UBND xã Hòa Liên', 1, 1, 4, 25, 73, NULL),
	(74, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '074', 'UBND xã Hòa Ninh', 'UBND xã Hòa Ninh', 1, 1, 4, 25, 74, NULL),
	(75, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '075', 'UBND xã Hòa Sơn', 'UBND xã Hòa Sơn', 1, 1, 4, 25, 75, NULL),
	(76, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '076', 'UBND xã Hòa Nhơn', 'UBND xã Hòa Nhơn', 1, 1, 4, 25, 76, NULL),
	(77, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '077', 'UBND xã Hòa Phú', 'UBND xã Hòa Phú', 1, 1, 4, 25, 77, NULL),
	(78, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '078', 'UBND xã Hòa Phong', 'UBND xã Hòa Phong', 1, 1, 4, 25, 78, NULL),
	(79, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '079', 'UBND xã Hòa Châu', 'UBND xã Hòa Châu', 1, 1, 4, 25, 79, NULL),
	(80, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '080', 'UBND xã Hòa Tiến', 'UBND xã Hòa Tiến', 1, 1, 4, 25, 80, NULL),
	(81, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '081', 'UBND xã Hòa Phước', 'UBND xã Hòa Phước', 1, 1, 4, 25, 81, NULL),
	(82, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '082', 'UBND xã Hòa Khương', 'UBND xã Hòa Khương', 1, 1, 4, 25, 82, NULL),
	(83, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '083', 'UBND Thành phố Hồ Chí Minh', 'UBND Thành phố Hồ Chí Minh', 1, 1, 2, NULL, 10, NULL),
	(84, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '084', 'UBND Thành phố Hà Nội', 'UBND Thành phố Hà Nội', 1, 1, 2, NULL, 12, NULL),
	(85, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '085', 'UBND Thành phố Hải Phòng', 'UBND Thành phố Hải Phòng', 1, 1, 2, NULL, 11, NULL),
	(86, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '086', 'Công an Đà Nẵng', 'Công an Đà Nẵng', 1, 1, 5, 1, 9, 2),
	(87, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '087', 'Công an Hồ Chí Minh', 'Công an Hồ Chí Minh', 1, 1, 5, 83, 10, 2),
	(88, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '088', 'Công an Hà Nội', 'Công an Hà Nội', 1, 1, 5, 84, 12, 2),
	(89, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '089', 'Công an Hải Phòng', 'Công an Hải Phòng', 1, 1, 5, 85, 11, 2),
	(90, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '002', 'Phòng xử lý đơn', 'Phòng xử lý đơn', 1, 1, 6, 2, 9, NULL),
	(91, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '002', 'Phòng giải quyết đơn', 'Phòng giải quyết đơn', 1, 1, 6, 2, 9, NULL),
	(92, b'0', '2017-04-23 14:05:22', '2017-04-23 14:05:20', '002', 'Phòng Thanh tra phòng chống tham nhũng', 'Phòng Thanh tra phòng chống tham nhũng', 1, 1, 6, 2, 9, NULL);
/*!40000 ALTER TABLE `coquanquanly` ENABLE KEYS */;

-- Dumping structure for table tttp.coquantochuctiepdan
DROP TABLE IF EXISTS `coquantochuctiepdan`;
CREATE TABLE IF NOT EXISTS `coquantochuctiepdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `chucVu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiDaiDien` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm45j02khl648myfq5ms97tdhv` (`nguoiSua_id`),
  KEY `FKj92x42wpougt7hrlkk1gigwfa` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.coquantochuctiepdan: 15 rows
/*!40000 ALTER TABLE `coquantochuctiepdan` DISABLE KEYS */;
INSERT INTO `coquantochuctiepdan` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `chucVu`, `nguoiDaiDien`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(8, b'0', '2017-05-17 12:35:12', '2017-05-17 12:34:41', 'Chủ tịch', 'Nguyễn Trường Thành', 'UBND quận Hải Châu', 1, 1),
	(9, b'0', '2017-05-17 12:35:12', '2017-05-17 12:34:41', 'Chủ tịch', 'Phan Công Minh', 'UBND quận Thanh Khê', 1, 1),
	(10, b'0', '2017-05-17 12:36:16', '2017-05-17 12:34:41', 'Chủ tịch', 'Nguyễn Trường Thành', 'UBND quận Hải Châu', 1, 1),
	(11, b'0', '2017-05-17 12:36:17', '2017-05-17 12:34:41', 'Chủ tịch', 'Phan Công Minh', 'UBND quận Thanh Khê', 1, 1),
	(12, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', 'Chủ tịch', 'Nguyễn Quốc Đạt', 'UBND phường Hòa Minh', 1, 1),
	(13, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', 'Chủ tịch', 'Trần Đinh Lực', 'UBND Phường Hải Châu 1', 1, 1),
	(14, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', 'Tổ trưởng', 'Trần Bá Nam', 'Tổ dân phố 5', 1, 1),
	(15, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', 'Chủ tịch', 'Nguyễn Quốc Đạt', 'UBND phường Hòa Minh', 1, 1),
	(16, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', 'Chủ tịch', 'Trần Đinh Lực', 'UBND Phường Hải Châu 1', 1, 1),
	(17, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', 'Chủ tịch', 'Nguyễn Quốc Đạt', 'UBND phường Hòa Minh', 1, 1),
	(18, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', 'Chủ tịch', 'Trần Đinh Lực', 'UBND Phường Hải Châu 1', 1, 1),
	(19, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', 'Tổ trưởng', 'Trần Bá Nam', 'Tổ dân phố 5', 1, 1),
	(20, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', 'Chủ tịch', 'Nguyễn Quốc Đạt', 'UBND phường Hòa Minh', 1, 1),
	(21, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', 'Chủ tịch', 'Trần Đinh Lực', 'UBND Phường Hải Châu 1', 1, 1),
	(22, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', 'Tổ trưởng', 'Trần Bá Nam', 'Tổ dân phố 5', 1, 1);
/*!40000 ALTER TABLE `coquantochuctiepdan` ENABLE KEYS */;

-- Dumping structure for table tttp.coquantochuctiepdan_has_sotiepcongdan
DROP TABLE IF EXISTS `coquantochuctiepdan_has_sotiepcongdan`;
CREATE TABLE IF NOT EXISTS `coquantochuctiepdan_has_sotiepcongdan` (
  `soTiepCongDan_id` bigint(20) NOT NULL,
  `coQuanToChucTiepDan_id` bigint(20) NOT NULL,
  KEY `FKnr156hp7pqg91p7l8ayqqvurw` (`coQuanToChucTiepDan_id`),
  KEY `FK80365qi9uuf6aff00r76ysts8` (`soTiepCongDan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.coquantochuctiepdan_has_sotiepcongdan: 15 rows
/*!40000 ALTER TABLE `coquantochuctiepdan_has_sotiepcongdan` DISABLE KEYS */;
INSERT INTO `coquantochuctiepdan_has_sotiepcongdan` (`soTiepCongDan_id`, `coQuanToChucTiepDan_id`) VALUES
	(42, 9),
	(42, 8),
	(43, 11),
	(43, 10),
	(44, 12),
	(44, 13),
	(44, 14),
	(45, 15),
	(45, 16),
	(46, 17),
	(46, 18),
	(46, 19),
	(47, 20),
	(47, 21),
	(47, 22);
/*!40000 ALTER TABLE `coquantochuctiepdan_has_sotiepcongdan` ENABLE KEYS */;

-- Dumping structure for table tttp.dantoc
DROP TABLE IF EXISTS `dantoc`;
CREATE TABLE IF NOT EXISTS `dantoc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `tenKhac` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `thieuSo` bit(1) NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9x71u9y734rik892bx8a1fdhc` (`nguoiSua_id`),
  KEY `FKcqhioyh3xr66oc2eqwx3lw67i` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.dantoc: 61 rows
/*!40000 ALTER TABLE `dantoc` DISABLE KEYS */;
INSERT INTO `dantoc` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `tenKhac`, `thieuSo`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '001', 'Kinh', 'Kinh', 'Việt', b'0', 1, 1),
	(2, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '002', 'Tày', 'Tày', 'Thổ, Ngạn, Phén, Thù Lao, Pa Dí', b'1', 1, 1),
	(3, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '003', 'Thái', 'Thái', 'Tày, Tày Khao (Thái trắng), Tày Dăm (Thái đen), Tày Mười Tây Thanh, Màn Thanh, Hang Ông, Tày Mường, Pi Thay, Thổ Đà Bắc', b'1', 1, 1),
	(4, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '004', 'Hoa', 'Hoa', 'Hán, Triều Châu, Phúc Kiến, Quảng Đông, Hải Nam, Hạ, Xạ Phạng', b'1', 1, 1),
	(5, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '005', 'Khơ Me', 'Khơ Me', 'Cur, Cul, Cu, Thổ, Việt gốc Miên, Khơ-me Krôm', b'1', 1, 1),
	(6, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '006', 'Mường', 'Mường', 'Mol, Mual, Mọi, Mọi Bi, Ao Tá (Ậu Tá)', b'1', 1, 1),
	(7, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '007', 'Nùng', 'Nùng', 'Xuồng, Giang, Nùng An, Phàn Sinh, Nùng Cháo, Nùng Lòi, Quý Rim, Khèn Lài, ...', b'1', 1, 1),
	(8, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '008', 'HMông', 'HMông', 'Mèo, Mẹo, Hoa, Mèo Xanh, Mèo Đỏ, Mèo Đen, Ná Mẻo, Mán Trắng', b'1', 1, 1),
	(9, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '009', 'Dao', 'Dao', 'Mán, Động, Trại, Xá, Dìu Miền, Kiềm, Miền, Quần Trắng, Dao Đỏ, Quần Chẹt, Lô Gang, Dao Tiền, Thanh Y,Lan Tẻn, Đại Bản,Tiểu Bản, Cóc Ngáng, Cóc Mùn, Sơn Đầu, ...  Mán, Động, Trại, Xá, Dìu Miền, Kiềm, Miền, Quần Trắng, Dao Đỏ, Quần Chẹt, Lô Gang, Dao Tiền', b'1', 1, 1),
	(10, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '010', 'Gia Rai', 'Gia Rai', 'Giơ-rai, Tơ-buăn, Chơ-rai, Hơ-bau, Hđrung, Chor, ...', b'1', 1, 1),
	(11, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '011', 'Ngái', 'Ngái', 'Xín, Lê, Đản, Khách Gia', b'1', 1, 1),
	(12, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '012', 'Ê - Đê', 'Ê - Đê', 'Ra-đê, Đê, Kpạ, A-đham, Krung, Ktul, Đliê Ruê, Blô, E-pan, Mđhur, Bih, ...', b'1', 1, 1),
	(13, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '013', 'Ba na', 'Ba na', 'Gơ-lar, Tơ-lô, Giơ-lâng (Y-Lăng), Rơ-ngao, Krem, Roh, ConKđê, A-la Công, Kpăng Công, Bơ-nâm', b'1', 1, 1),
	(14, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '014', 'Xơ Đăng', 'Xơ Đăng', 'Xơ-teng, Hđang, Tơ-đra, Mơ-nâm, Ha-lăng, Ca-dông, Kmrăng, Con Lan, Bri-la, Tang', b'1', 1, 1),
	(15, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '015', 'Sán Chay', 'Sán Chay', 'Cao Lan-Sán chỉ, Cao Lan, Mán Cao Lan, Hờn Bạn, Sán Chỉ (Sơn Tử)', b'1', 1, 1),
	(16, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '016', 'Cơ Ho', 'Cơ Ho', 'Xrê, Nôp (Tu Lốp), Cơ-don, Chil, Lat (Lach), Trinh', b'1', 1, 1),
	(17, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '017', 'Chăm', 'Chăm', 'Chàm, Chiêm Thành, Hroi', b'1', 1, 1),
	(18, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '018', 'SánDìu', 'SánDìu', 'Sán Dẻo, Trại, Trại Đất, Mán Quần Cộc', b'1', 1, 1),
	(19, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '019', 'HRê', 'HRê', 'Chăm Rê, Chom, Krẹ Lũy', b'1', 1, 1),
	(20, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '020', 'MNông', 'MNông', 'Pnông, Nông, Pré, Bu-đâng, ĐiPri, Biat, Gar, Rơ-lam, Chil', b'1', 1, 1),
	(21, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '021', 'RagLai', 'RagLai', 'Ra-clây, Rai, Noang, La Oang', b'1', 1, 1),
	(22, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '022', 'XTiêng', 'XTiêng', 'Xa Điêng', b'1', 1, 1),
	(23, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '023', 'Bru', 'Bru', 'Bru, Vân Kiều, Măng Coong, Tri Khùa', b'1', 1, 1),
	(24, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '024', 'Thổ', 'Thổ', 'Kẹo, Mọn, Cuối, Họ, Đan Lai, Ly Hà, Tày Pọng, Con Kha, Xá Lá Vàng', b'1', 1, 1),
	(25, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '025', 'Giáy', 'Giáy', 'Nhắng, Dẩng, Pầu Thìn Pu Nà, Cùi Chu, Xa', b'1', 1, 1),
	(26, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '026', 'Cơ Tu', 'Cơ Tu', 'Ca-tu, Cao, Hạ, Phương, Ca-tang', b'1', 1, 1),
	(27, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '027', 'Gié', 'Gié', 'Đgiéh, Tareh, Giang Rẫy Pin, Triêng, Treng, Ta Riêng, Ve (Veh), La-ve, Ca-tang', b'1', 1, 1),
	(28, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '028', 'Mạ', 'Mạ', 'Châu Mạ, Mạ Ngăn, Mạ Xốp, Mạ Tô, Mạ Krung, ...', b'1', 1, 1),
	(29, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '029', 'Khơ Mú', 'Khơ Mú', 'Xá Cẩu, Mứn Xen, Pu Thênh Tềnh, Tày Hay', b'1', 1, 1),
	(30, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '030', 'Co', 'Co', 'Cor, Col, Cùa, Trầu', b'1', 1, 1),
	(31, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '031', 'Ta Ôi', 'Ta Ôi', 'Tôi-ôi, Pa-co, Pa-hi (Ba-hi)', b'1', 1, 1),
	(32, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '032', 'Chơ Ro', 'Chơ Ro', 'Dơ-ro, Châu-ro', b'1', 1, 1),
	(33, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '033', 'Kháng', 'Kháng', 'Xá Khao, Xá Súa, Xá Dón, Xá Dẩng, Xá Hốc, Xá Ái, Xá Bung, Quảng Lâm', b'1', 1, 1),
	(34, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '034', 'Xing Mun', 'Xing Mun', 'Puộc, Pụa', b'1', 1, 1),
	(35, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '035', 'Ha Nhì', 'Ha Nhì', 'U Ni, Xá U Ni', b'1', 1, 1),
	(36, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '036', 'Chu Ru', 'Chu Ru', 'Chơ-ru, Chu', b'1', 1, 1),
	(37, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '037', 'Lào', 'Lào', 'Lào Bốc, Lào Nọi', b'1', 1, 1),
	(38, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '038', 'La Chi', 'La Chi', 'Cù Tê, La Quả', b'1', 1, 1),
	(39, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '039', 'La Ha', 'La Ha', 'Xá Khao, Khlá Phlạo', b'1', 1, 1),
	(40, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '040', 'Phù Lá', 'Phù Lá', 'Bồ Khô Pạ, Mu Di, Pạ Xá, Phó, Phổ, VaXơ', b'1', 1, 1),
	(41, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '041', 'La Hủ', 'La Hủ', 'Lao, Pu Đang, Khù Xung, Cò Xung, Khả Quy', b'1', 1, 1),
	(42, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '042', 'Lự', 'Lự', 'Lừ, Nhuồn Duôn, Mun Di', b'1', 1, 1),
	(43, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '043', 'Lô Lô', 'Lô Lô', 'Lô Lô', b'1', 1, 1),
	(44, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '044', 'Chứt', 'Chứt', 'Sách, May, Rục, Mã-liêng, A-rem, Tu Vang, Pa-leng, Xơ-lang, Tơ-hung, Chà-củi, Tắc-củi, U-mo, Xá Lá Vàng', b'1', 1, 1),
	(45, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '045', 'Mảng', 'Mảng', 'Mảng Ư, Xá Lá Vàng', b'1', 1, 1),
	(46, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '046', 'Pà Thẻn', 'Pà Thẻn', 'Pà Hưng, Tống', b'1', 1, 1),
	(47, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '047', 'Cơ Lao', 'Cơ Lao', 'Cơ Lao', b'1', 1, 1),
	(48, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '048', 'Cống', 'Cống', 'Xắm Khống, Mấng Nhé, Xá Xeng', b'1', 1, 1),
	(49, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '049', 'Bố Y', 'Bố Y', 'Chủng Chá, Trọng Gia, Tu Dí, Tu Dìn', b'1', 1, 1),
	(50, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '050', 'Xi La', 'Xi La', 'Cù Dề Xừ, Khả pẻ', b'1', 1, 1),
	(51, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '051', 'Pu Péo', 'Pu Péo', 'Ka Bèo, Pen Ti Lô Lô', b'1', 1, 1),
	(52, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '052', 'Brâu', 'Brâu', 'Brao', b'1', 1, 1),
	(53, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '053', 'Ơ Đu', 'Ơ Đu', 'Tày Hạt', b'1', 1, 1),
	(54, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '054', 'Rơ Măm', 'Rơ Măm', 'Rơ Măm', b'1', 1, 1),
	(55, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '055', 'Hán', 'Hán', 'Hán', b'1', 1, 1),
	(56, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '056', 'Cà Dong', 'Cà Dong', 'Cà Dong', b'1', 1, 1),
	(57, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '057', 'Chiêng', 'Chiêng', 'Chiêng', b'1', 1, 1),
	(58, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '058', 'Tà Riềng', 'Tà Riềng', 'Tà Riềng', b'1', 1, 1),
	(59, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '059', 'Dẻ', 'Dẻ', 'Dẻ', b'1', 1, 1),
	(60, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '060', 'Nước Ngoài', 'Nước Ngoài', 'Nước Ngoài', b'0', 1, 1),
	(61, b'0', '2017-04-23 15:25:00', '2017-04-23 15:25:02', '061', 'Không xác định', 'Không xác định', 'Không xác định', b'0', 1, 1);
/*!40000 ALTER TABLE `dantoc` ENABLE KEYS */;

-- Dumping structure for table tttp.documentmetadata
DROP TABLE IF EXISTS `documentmetadata`;
CREATE TABLE IF NOT EXISTS `documentmetadata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `fileLocation` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKku6e9kbdas0a2o8lxyouvvb4r` (`nguoiSua_id`),
  KEY `FK4644u67y4ovy6nag7msp9ggdr` (`nguoiTao_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.documentmetadata: 0 rows
/*!40000 ALTER TABLE `documentmetadata` DISABLE KEYS */;
/*!40000 ALTER TABLE `documentmetadata` ENABLE KEYS */;

-- Dumping structure for table tttp.don
DROP TABLE IF EXISTS `don`;
CREATE TABLE IF NOT EXISTS `don` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `boSungThongTinBiKhieuTo` bit(1) NOT NULL,
  `coThongTinCoQuanDaGiaiQuyet` bit(1) NOT NULL,
  `coUyQuyen` bit(1) NOT NULL,
  `daGiaiQuyet` bit(1) NOT NULL,
  `daXuLy` bit(1) NOT NULL,
  `diaChiCoQuanBKT` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ghiChuXuLyDon` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `hinhThucDaGiaiQuyet` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `huongGiaiQuyetDaThucHien` longtext COLLATE utf8_vietnamese_ci,
  `huongXuLyXLD` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `lanGiaiQuyet` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `loaiDoiTuong` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `loaiDon` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `loaiNguoiBiKhieuTo` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `loaiNguoiDungDon` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `lyDoDinhChi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ngayLapDonGapLanhDaoTmp` datetime DEFAULT NULL,
  `ngayQuyetDinhDinhChi` datetime DEFAULT NULL,
  `ngayTiepNhan` datetime NOT NULL,
  `nguonTiepNhanDon` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `noiDung` longtext COLLATE utf8_vietnamese_ci NOT NULL,
  `quyTrinhXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soDienThoaiCoQuanBKT` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soLanKhieuNaiToCao` int(11) NOT NULL,
  `soNguoi` int(11) NOT NULL,
  `soQuyetDinhDinhChi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `tenCoQuanBKT` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `thanhLapDon` bit(1) NOT NULL,
  `thanhLapTiepDanGapLanhDao` bit(1) NOT NULL,
  `tongSoLuotTCD` int(11) NOT NULL,
  `trangThaiDon` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `yKienXuLyDon` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `yeuCauCuaCongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `yeuCauGapTrucTiepLanhDao` bit(1) NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `canBoXuLy_id` bigint(20) DEFAULT NULL,
  `chiTietLinhVucDonThuChiTiet_id` bigint(20) DEFAULT NULL,
  `coQuanDaGiaiQuyet_id` bigint(20) DEFAULT NULL,
  `donLanTruoc_id` bigint(20) DEFAULT NULL,
  `linhVucDonThu_id` bigint(20) NOT NULL,
  `linhVucDonThuChiTiet_id` bigint(20) NOT NULL,
  `phongBanGiaiQuyet_id` bigint(20) DEFAULT NULL,
  `phuongXaCoQuanBKT_id` bigint(20) DEFAULT NULL,
  `quanHuyenCoQuanBKT_id` bigint(20) DEFAULT NULL,
  `thamQuyenGiaiQuyet_id` bigint(20) DEFAULT NULL,
  `tinhThanhCoQuanBKT_id` bigint(20) DEFAULT NULL,
  `toDanPhoCoQuanBKT_id` bigint(20) DEFAULT NULL,
  `processType` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `currentState_id` bigint(20) DEFAULT NULL,
  `canBoXuLyPhanHeXLD_id` bigint(20) DEFAULT NULL,
  `currentForm_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKle6vcyxx4cpgoqtm2o98e1gig` (`nguoiSua_id`),
  KEY `FKi26gnvwpqihb6ghk6kpldpywj` (`nguoiTao_id`),
  KEY `FKgld302prex5kyuixl71f50lta` (`canBoXuLy_id`),
  KEY `FKo3340sqgl2gp9hs7qdxnialet` (`chiTietLinhVucDonThuChiTiet_id`),
  KEY `FK6rr83fn2mfxl34o5gqn50tmya` (`coQuanDaGiaiQuyet_id`),
  KEY `FKms6sdk1weha5o1tmql3f8ba62` (`donLanTruoc_id`),
  KEY `FKs68ok103jkvgf0n6cd92w4yoi` (`linhVucDonThu_id`),
  KEY `FKk5mqov4ao40w3919uaigupajy` (`linhVucDonThuChiTiet_id`),
  KEY `FKehu8rqxcgq6015b5avbmnxum` (`phongBanGiaiQuyet_id`),
  KEY `FKpcpbo49ut556fvf83md8qcawc` (`phuongXaCoQuanBKT_id`),
  KEY `FKibg0ncl6prdjnyixb89aawems` (`quanHuyenCoQuanBKT_id`),
  KEY `FKjmui05kok7bl12k4tx8nyb49j` (`thamQuyenGiaiQuyet_id`),
  KEY `FK78k72cpy4bq422xjwbjwoxl66` (`tinhThanhCoQuanBKT_id`),
  KEY `FKjgyea79csokc501q1dc2p7ly1` (`toDanPhoCoQuanBKT_id`),
  KEY `FK6gxjnr1mm4l4v5yg3v32w3et` (`currentState_id`),
  KEY `FKjme5he22rybjexnb5n30boea4` (`canBoXuLyPhanHeXLD_id`),
  KEY `FK31n1rsmpwimy8dqkdoaieit0` (`currentForm_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.don: 11 rows
/*!40000 ALTER TABLE `don` DISABLE KEYS */;
INSERT INTO `don` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `boSungThongTinBiKhieuTo`, `coThongTinCoQuanDaGiaiQuyet`, `coUyQuyen`, `daGiaiQuyet`, `daXuLy`, `diaChiCoQuanBKT`, `ghiChuXuLyDon`, `hinhThucDaGiaiQuyet`, `huongGiaiQuyetDaThucHien`, `huongXuLyXLD`, `lanGiaiQuyet`, `loaiDoiTuong`, `loaiDon`, `loaiNguoiBiKhieuTo`, `loaiNguoiDungDon`, `lyDoDinhChi`, `ma`, `ngayLapDonGapLanhDaoTmp`, `ngayQuyetDinhDinhChi`, `ngayTiepNhan`, `nguonTiepNhanDon`, `noiDung`, `quyTrinhXuLy`, `soDienThoaiCoQuanBKT`, `soLanKhieuNaiToCao`, `soNguoi`, `soQuyetDinhDinhChi`, `tenCoQuanBKT`, `thanhLapDon`, `thanhLapTiepDanGapLanhDao`, `tongSoLuotTCD`, `trangThaiDon`, `yKienXuLyDon`, `yeuCauCuaCongDan`, `yeuCauGapTrucTiepLanhDao`, `nguoiSua_id`, `nguoiTao_id`, `canBoXuLy_id`, `chiTietLinhVucDonThuChiTiet_id`, `coQuanDaGiaiQuyet_id`, `donLanTruoc_id`, `linhVucDonThu_id`, `linhVucDonThuChiTiet_id`, `phongBanGiaiQuyet_id`, `phuongXaCoQuanBKT_id`, `quanHuyenCoQuanBKT_id`, `thamQuyenGiaiQuyet_id`, `tinhThanhCoQuanBKT_id`, `toDanPhoCoQuanBKT_id`, `processType`, `currentState_id`, `canBoXuLyPhanHeXLD_id`, `currentForm_id`) VALUES
	(18, b'0', '2017-05-17 09:45:37', '2017-05-17 09:45:36', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'QUYET_DINH_HANH_CHINH', 'DON_KHIEU_NAI', NULL, 'CA_NHAN', '', '', '2017-05-17 09:45:36', NULL, '2017-05-17 09:39:35', 'TRUC_TIEP', 'Khiếu nại 2 cán bộ Công an kiểm soát trật tự tại khu công nghiệp An Đồn đối với hành vi yêu cầu tôi dừng xe lại nhưng lại không xuất trình những giấy tờ chứng minh việc đang thi hành công vụ, không đeo biển hiệu.					', NULL, '', 0, 1, '', '', b'0', b'0', 1, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, NULL, NULL, NULL, 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(19, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:04', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_TO_CAO', NULL, 'DOAN_DONG_NGUOI', '', '', '2017-05-17 10:05:04', NULL, '2017-05-17 09:48:57', 'TRUC_TIEP', 'Tố cáo ông Trần Văn X, cán bộ địa chính - xây dựng Quận Hải Châu nhận hối lộ số tiền 100.000.000 triệu đồng để không gây khó dễ trong việc cấp giấy phép xây dựng 					', NULL, '', 0, 6, '', '', b'0', b'0', 1, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, NULL, NULL, NULL, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(20, b'0', '2017-05-17 20:16:43', '2017-05-17 10:14:18', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_KIEN_NGHI_PHAN_ANH', NULL, 'CO_QUAN_TO_CHUC', '', '', '2017-05-17 10:14:18', NULL, '2017-05-17 10:06:27', 'TRUC_TIEP', 'Chúng tôi là tập thể nhân dân sinh sống tại tổ 5- Hòa Minh - Liên Chiểu - Đà Nẵng, xin được phản ánh về tình hình ô nhiễm môi trường trong thời gian qua tại nơi chúng tôi sống.\nCụ thể như sau: mấy năm gần đây cạnh nhà chúng tôi ở mọc lên hai trạm trộn bêtông nhựa đường nóng hoạt động suất ngày đêm, gây ô nhiễm nghiêm trọng tại nơi chung tôi sinh sống. Nhà máy hoạt động gây ra những ảnh hưởng đến đời sống sinh hoạt của người dân :\nGây khói bụi đến ngạt thở.\nTiếng ồn của nhà máy, của máy xúc làm đầu chúng tôi như nổ tung không ăn ngủ gì được.\nTiếng rung của nhà máy gầm lên làm nứt nhà chúng tôi.\nTiếng còi, tiếng đật thùng xe ben kêu vang trời.		', NULL, '', 0, 1, '', '', b'1', b'0', 1, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, 6, NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(21, b'0', '2017-05-17 10:40:57', '2017-05-17 10:40:57', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'QUYET_DINH_HANH_CHINH', 'DON_KIEN_NGHI_PHAN_ANH', NULL, 'CO_QUAN_TO_CHUC', '', '', '2017-05-17 10:40:57', NULL, '2017-05-17 10:37:16', 'TRUC_TIEP', 'Chúng tôi là tập thể giáo viên trường tiểu học Điện Biên Phủ, nay chúng tôi xin kiến nghị về việc không đồng ý sự quyết định bổ nhiệm bà Vương Thị Vân về làm hiệu trưởng trường tiểu học Điện Biên Phủ vì theo thông tin chính xác thì năm học 2013-2014 bà Vương Thị Vân đã bị kỷ luật và lên làm việc tại Phòng Giáo Dục.', NULL, '', 0, 1, '', '', b'0', b'0', 1, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, 13, NULL, NULL, 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(22, b'0', '2017-05-17 12:35:12', '2017-05-17 11:13:00', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'QUYET_DINH_HANH_CHINH', 'DON_KHIEU_NAI', NULL, 'CA_NHAN', '', '', '2017-05-17 11:13:00', NULL, '2017-05-17 05:33:58', 'TRUC_TIEP', 'Ông nội tôi có 06 người con (04 nam 02 nữ) trong đó bố tôi là người con trai thứ hai trong nhà. ông nội tôi có hai mảnh đất. Một mảnh đã cho người con trai thứ ba trong nhà (em bố tôi) sở hữu. Mảnh đất còn lại hiện nay gia đình đang ở được chia làm 2, bố tôi được hưởng 1/2 mảnh đất trên và đã được tách tên sở hữu đứng tên bố tôi (được vẽ trong bản đồ địa chính 299 của xã vẽ năm 1996) nhưng chưa có sổ đỏ (do địa phương chưa cấp sổ đỏ từ trước đến nay)', NULL, '', 0, 1, '', '', b'0', b'1', 1, NULL, '', '', b'1', 1, 1, 1, 15, NULL, NULL, 1, 14, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(23, b'0', '2017-05-17 12:36:17', '2017-05-17 11:23:06', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_TO_CAO', NULL, 'DOAN_DONG_NGUOI', '', '', '2017-05-17 11:23:06', NULL, '2017-05-17 05:33:58', 'TRUC_TIEP', 'Anh Trần Võ Đình Nam có hành vi lừa đảo, chiếm đoạt tài sản của bà Khuê là một chiếc nhẫn vàng 18k trị giá 1,8 chỉ.	', NULL, '', 0, 2, '', '', b'0', b'1', 1, NULL, '', '', b'1', 1, 1, 1, 17, NULL, NULL, 1, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(24, b'0', '2017-05-17 11:36:30', '2017-05-17 11:36:30', b'0', b'0', b'0', b'0', b'0', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_KIEN_NGHI_PHAN_ANH', NULL, 'CO_QUAN_TO_CHUC', '', '', '2017-05-17 11:36:30', NULL, '2017-05-17 11:23:16', 'TRUC_TIEP', 'Chúng tôi là tập thể nhân dân sinh sống tại tổ 5- Hòa Khánh Bắc - Liên Chiểu - Đà Nẵng, xin được phản ánh về tình hình ô nhiễm môi trường trong thời gian qua tại nơi chúng tôi sống.\nCụ thể như sau: mấy năm gần đây cạnh nhà chúng tôi ở mọc lên hai trạm trộn bêtông nhựa đường nóng hoạt động suất ngày đêm, gây ô nhiễm nghiêm trọng tại nơi chung tôi sinh sống. Nhà máy hoạt động gây ra những ảnh hưởng đến đời sống sinh hoạt của người dân :\nGây khói bụi đến ngạt thở.\nTiếng ồn của nhà máy, của máy xúc làm đầu chúng tôi như nổ tung không ăn ngủ gì được.\nTiếng rung của nhà máy gầm lên làm nứt nhà chúng tôi.\nTiếng còi, tiếng đật thùng xe ben kêu vang trời.', NULL, '', 0, 1, '', '', b'0', b'0', 1, 'DANG_XU_LY', '', '', b'1', 1, 1, 1, 6, NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(25, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', b'0', b'0', b'0', b'0', b'1', '', '', NULL, ' ', NULL, '', 'QUYET_DINH_HANH_CHINH', 'DON_KHIEU_NAI', NULL, 'CA_NHAN', '', '', '2017-05-17 12:40:20', NULL, '2017-05-17 12:37:12', 'TRUC_TIEP', 'Khiếu nại công ty TNHH A trong quá trình sản xuất đã lấn chiếm trái phép diện tích đất công', NULL, '', 0, 1, '', '', b'0', b'0', 0, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, NULL, NULL, NULL, 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(26, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', b'0', b'0', b'0', b'0', b'1', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_TO_CAO', NULL, 'DOAN_DONG_NGUOI', '', '', '2017-05-17 12:48:49', NULL, '2017-05-17 12:40:53', 'TRUC_TIEP', 'Tố cáo ông Nguyễn Văn B nhận hối lộ của công ty C 1 tỷ đồng và làm ngơ cho việc công ty C trong quá trình sản xuất đã xả chất thải chưa qua xử lý ra môi trường gây ô nhiễm môi trường', NULL, '', 0, 6, '', '', b'0', b'0', 0, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, NULL, NULL, NULL, 18, 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(27, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', b'0', b'0', b'0', b'0', b'1', '', '', NULL, ' ', NULL, '', 'HANH_VI_HANH_CHINH', 'DON_KIEN_NGHI_PHAN_ANH', NULL, 'CO_QUAN_TO_CHUC', '', '', '2017-05-17 13:01:05', NULL, '2017-05-17 12:57:33', 'TRUC_TIEP', 'Phản ánh công ty VinGrow trong quá trình sản xuất đã xả chất thải chưa qua xử lý gây ô nhiễm môi trường gây ảnh hưởng đến sức khỏe của các khu dân cư lân cận', NULL, '', 0, 1, '', '', b'0', b'0', 0, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, 6, NULL, NULL, 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(28, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:23', b'0', b'0', b'0', b'0', b'1', '', '', NULL, ' ', NULL, '', 'QUYET_DINH_HANH_CHINH', 'DON_KIEN_NGHI_PHAN_ANH', NULL, 'CO_QUAN_TO_CHUC', '', '', '2017-05-17 14:03:23', NULL, '2017-05-17 13:59:59', 'TRUC_TIEP', 'Cơ sở sản xuất gia công cơ khí, sản xuất ốc vít của hộ kinh doanh Tường Vinh hoạt động liên tục từ thứ Hai đến thứ Bảy hàng tuần, từ 07h15 đến 17,18giờ00 (chỉ nghỉ khoản 1,5 giờ vào buổi trưa). Cơ sở sản xuất này gây nên tiếng ồn rất lớn, tiếng máy dập kim loại, tiếng khoan cắt kim loại làm ảnh hưởng nghiêm trọng và gây khó khăn cho sinh hoạt của các hộ dân xung quanh.					', NULL, '', 0, 1, '', '', b'0', b'0', 0, 'DANG_XU_LY', '', '', b'0', 1, 1, 1, 13, NULL, NULL, 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `don` ENABLE KEYS */;

-- Dumping structure for table tttp.donvihanhchinh
DROP TABLE IF EXISTS `donvihanhchinh`;
CREATE TABLE IF NOT EXISTS `donvihanhchinh` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `capDonViHanhChinh_id` bigint(20) NOT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlupepl2viw3pp85r19c9t3lnt` (`nguoiSua_id`),
  KEY `FKlh7l3e1vu15jwjn1p8sy1yuv6` (`nguoiTao_id`),
  KEY `FKpl3lg3vfs2rl19013rgnfc2f2` (`capDonViHanhChinh_id`),
  KEY `FK39gtupy50f9p0yg1pnv1fdq6q` (`cha_id`)
) ENGINE=MyISAM AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.donvihanhchinh: 69 rows
/*!40000 ALTER TABLE `donvihanhchinh` DISABLE KEYS */;
INSERT INTO `donvihanhchinh` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `capDonViHanhChinh_id`, `cha_id`) VALUES
	(24, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '016', 'phường Hòa Khánh Bắc', 'phường Hòa Khánh Bắc', 1, 1, 6, 14),
	(25, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '017', 'phường Hòa Khánh Nam', 'phường Hòa Khánh Nam', 1, 1, 6, 14),
	(23, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '015', 'phường Hòa Hiệp Nam', 'phường Hòa Hiệp Nam', 1, 1, 6, 14),
	(22, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '014', 'phường Hòa Hiệp Bắc', 'phường Hòa Hiệp Bắc', 1, 1, 6, 14),
	(9, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '001', 'thành phố Đà Nẵng', 'thành phố Đà Nẵng', 1, 1, 4, NULL),
	(10, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '002', 'thành phố Hồ Chí Minh', 'thành phố Hồ Chí Minh', 1, 1, 4, NULL),
	(11, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '003', 'thành phố Hải Phòng', 'thành phố Hải Phòng', 1, 1, 4, NULL),
	(12, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '004', 'thành phố Hà Nội', 'thành phố Hà Nội', 1, 1, 4, NULL),
	(13, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '005', 'thành phố Cần Thơ', 'thành phố Cần Thơ', 1, 1, 4, NULL),
	(14, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '006', 'quận Liên Chiểu', 'quận Liên Chiểu', 1, 1, 5, 9),
	(15, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '007', 'quận Thanh Khê', 'quận Thanh Khê', 1, 1, 5, 9),
	(16, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '008', 'quận Hải Châu', 'quận Hải Châu', 1, 1, 5, 9),
	(17, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '009', 'quận Sơn Trà', 'quận Sơn Trà', 1, 1, 5, 9),
	(18, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '010', 'quận Ngũ Hành Sơn', 'quận Ngũ Hành Sơn', 1, 1, 5, 9),
	(19, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '011', 'quận Cẩm Lệ', 'quận Cẩm Lệ', 1, 1, 5, 9),
	(20, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '012', 'huyện Hòa Vang', 'huyện Hòa Vang', 1, 1, 5, 9),
	(21, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '013', 'huyện Hoàng Sa', 'huyện Hoàng Sa', 1, 1, 5, 9),
	(26, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '018', 'phường Hòa Minh', 'phường Hòa Minh', 1, 1, 6, 14),
	(27, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '019', 'phường Tam Thuận', 'phường Tam Thuận', 1, 1, 6, 15),
	(28, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '020', 'phường Thanh Khê Tây', 'phường Thanh Khê Tây', 1, 1, 6, 15),
	(29, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '021', 'phường Thanh Khê Đông', 'phường Thanh Khê Đông', 1, 1, 6, 15),
	(30, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '022', 'phường Xuân Hà', 'phường Xuân Hà', 1, 1, 6, 15),
	(31, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '023', 'phường Tân Chính', 'phường Tân Chính', 1, 1, 6, 15),
	(32, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '024', 'phường Chính Gián', 'phường Chính Gián', 1, 1, 6, 15),
	(33, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '025', 'phường Vĩnh Trung', 'phường Vĩnh Trung', 1, 1, 6, 15),
	(34, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '026', 'phường Thạc Gián', 'phường Thạc Gián', 1, 1, 6, 15),
	(35, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '027', 'phường An Khê', 'phường An Khê', 1, 1, 6, 15),
	(36, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '028', 'phường Hòa Khê', 'phường Hòa Khê', 1, 1, 6, 15),
	(37, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '029', 'phường Thanh Bình', 'phường Thanh Bình', 1, 1, 6, 16),
	(38, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '030', 'phường Thuận Phước', 'phường Thuận Phước', 1, 1, 6, 16),
	(39, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '031', 'phường Thạch Thang', 'phường Thạch Thang', 1, 1, 6, 16),
	(40, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '032', 'phường Hải Châu  I', 'phường Hải Châu  I', 1, 1, 6, 16),
	(41, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '033', 'phường Hải Châu II', 'phường Hải Châu II', 1, 1, 6, 16),
	(42, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '034', 'phường Phước Ninh', 'phường Phước Ninh', 1, 1, 6, 16),
	(43, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '035', 'phường Hòa Thuận Tây', 'phường Hòa Thuận Tây', 1, 1, 6, 16),
	(44, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '036', 'phường Hòa Thuận Đông', 'phường Hòa Thuận Đông', 1, 1, 6, 16),
	(45, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '037', 'phường Nam Dương', 'phường Nam Dương', 1, 1, 6, 16),
	(46, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '038', 'phường Bình Hiên', 'phường Bình Hiên', 1, 1, 6, 16),
	(47, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '039', 'phường Bình Thuận', 'phường Bình Thuận', 1, 1, 6, 16),
	(48, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '040', 'phường Hòa Cường Bắc', 'phường Hòa Cường Bắc', 1, 1, 6, 16),
	(49, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '041', 'phường Hòa Cường Nam', 'phường Hòa Cường Nam', 1, 1, 6, 16),
	(50, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '042', 'phường Thọ Quang', 'phường Thọ Quang', 1, 1, 6, 17),
	(51, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '043', 'phường Nại Hiên Đông', 'phường Nại Hiên Đông', 1, 1, 6, 17),
	(52, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '044', 'phường Mân Thái', 'phường Mân Thái', 1, 1, 6, 17),
	(53, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '045', 'phường An Hải Bắc', 'phường An Hải Bắc', 1, 1, 6, 17),
	(54, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '046', 'phường Phước Mỹ', 'phường Phước Mỹ', 1, 1, 6, 17),
	(55, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '047', 'phường An Hải Tây', 'phường An Hải Tây', 1, 1, 6, 17),
	(56, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '048', 'phường An Hải Đông', 'phường An Hải Đông', 1, 1, 6, 17),
	(57, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '049', 'phường Mỹ An', 'phường Mỹ An', 1, 1, 6, 18),
	(58, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '050', 'phường Khuê Mỹ', 'phường Khuê Mỹ', 1, 1, 6, 18),
	(59, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '051', 'phường Hoà Quý', 'phường Hoà Quý', 1, 1, 6, 18),
	(60, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '052', 'phường Hoà Hải', 'phường Hoà Hải', 1, 1, 6, 18),
	(61, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '053', 'phường Khuê Trung', 'phường Khuê Trung', 1, 1, 6, 19),
	(70, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '057', 'phường Hòa Thọ Đông', 'phường Hòa Thọ Đông', 1, 1, 6, 19),
	(69, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '056', 'phường Hòa Thọ Tây', 'phường Hòa Thọ Tây', 1, 1, 6, 19),
	(68, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '055', 'phường Hòa An', 'phường Hòa An', 1, 1, 6, 19),
	(67, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '054', 'phường Hòa Phát', 'phường Hòa Phát', 1, 1, 6, 19),
	(71, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '058', 'phường Hòa Xuân', 'phường Hòa Xuân', 1, 1, 6, 19),
	(72, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '059', 'xã Hòa Bắc', 'xã Hòa Bắc', 1, 1, 3, 20),
	(73, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '060', 'xã Hòa Liên', 'xã Hòa Liên', 1, 1, 3, 20),
	(74, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '061', 'xã Hòa Ninh', 'xã Hòa Ninh', 1, 1, 3, 20),
	(75, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '062', 'xã Hòa Sơn', 'xã Hòa Sơn', 1, 1, 3, 20),
	(76, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '063', 'xã Hòa Nhơn', 'xã Hòa Nhơn', 1, 1, 3, 20),
	(77, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '064', 'xã Hòa Phú', 'xã Hòa Phú', 1, 1, 3, 20),
	(78, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '065', 'xã Hòa Phong', 'xã Hòa Phong', 1, 1, 3, 20),
	(79, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '066', 'xã Hòa Châu', 'xã Hòa Châu', 1, 1, 3, 20),
	(80, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '067', 'xã Hòa Tiến', 'xã Hòa Tiến', 1, 1, 3, 20),
	(81, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '068', 'xã Hòa Phước', 'xã Hòa Phước', 1, 1, 3, 20),
	(82, b'0', '2017-04-23 11:18:40', '2017-04-23 11:18:40', '069', 'xã Hòa Khương', 'xã Hòa Khương', 1, 1, 3, 20);
/*!40000 ALTER TABLE `donvihanhchinh` ENABLE KEYS */;

-- Dumping structure for table tttp.don_congdan
DROP TABLE IF EXISTS `don_congdan`;
CREATE TABLE IF NOT EXISTS `don_congdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `chucVu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `diaChiCoQuan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `donVi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `luatSu` bit(1) NOT NULL,
  `ngayCapTheLuatSu` datetime DEFAULT NULL,
  `noiCapTheLuatSu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `phanLoaiCongDan` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `soDienThoai` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soTheLuatSu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `tenCoQuan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `thongTinGioiThieu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `congDan_id` bigint(20) NOT NULL,
  `don_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfevxgcufw32414nfloji3dd1e` (`nguoiSua_id`),
  KEY `FK46a1i8bk19bcrkbyat97ru8lj` (`nguoiTao_id`),
  KEY `FKape2lfk6jntnixwhs9j7nlt6e` (`congDan_id`),
  KEY `FKeewjtm7v72lb549lihi8yavbv` (`don_id`)
) ENGINE=MyISAM AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.don_congdan: 26 rows
/*!40000 ALTER TABLE `don_congdan` DISABLE KEYS */;
INSERT INTO `don_congdan` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `chucVu`, `diaChiCoQuan`, `donVi`, `luatSu`, `ngayCapTheLuatSu`, `noiCapTheLuatSu`, `phanLoaiCongDan`, `soDienThoai`, `soTheLuatSu`, `tenCoQuan`, `thongTinGioiThieu`, `nguoiSua_id`, `nguoiTao_id`, `congDan_id`, `don_id`) VALUES
	(32, b'0', '2017-05-17 09:45:37', '2017-05-17 09:45:37', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 17, 18),
	(33, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 20, 19),
	(34, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 18, 19),
	(35, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 19, 19),
	(36, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 22, 19),
	(37, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 21, 19),
	(38, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 23, 19),
	(39, b'0', '2017-05-17 10:14:19', '2017-05-17 10:14:19', '', '47 Hòa Minh - Liên Chiểu - Đà Nẵng', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '0511 3758465', '', 'Công ty Vin Group', '', 1, 1, 24, 20),
	(40, b'0', '2017-05-17 10:40:57', '2017-05-17 10:40:57', '', '26 Trường Chinh', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '05113123486', '', 'Trường tiểu học Điện Biên Phủ', '', 1, 1, 25, 21),
	(41, b'0', '2017-05-17 11:13:00', '2017-05-17 11:13:00', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 26, 22),
	(42, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 27, 23),
	(43, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 18, 23),
	(44, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 20, 23),
	(45, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 21, 23),
	(46, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 22, 23),
	(47, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 23, 23),
	(48, b'0', '2017-05-17 11:36:30', '2017-05-17 11:36:30', '', '76 Hòa Khánh Bắc - Liên Chiểu - Đà Nẵng', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '05113754851', '', 'Vin Group', '', 1, 1, 28, 24),
	(49, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 29, 25),
	(50, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 30, 26),
	(51, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '', '', '', '', 1, 1, 31, 26),
	(52, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 33, 26),
	(53, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 34, 26),
	(54, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 35, 26),
	(55, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', '', b'0', NULL, '', 'THANH_VIEN_DOAN_NHIEU_NGUOI', '', '', '', '', 1, 1, 32, 26),
	(56, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', '', 'Hải Phòng', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '05113458652', '', 'Vina Group', '', 1, 1, 36, 27),
	(57, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', '', '346 Tôn Đức Thắng', '', b'0', NULL, '', 'NGUOI_DUNG_DON', '05113245695', '', 'Hòa Bình', '', 1, 1, 37, 28);
/*!40000 ALTER TABLE `don_congdan` ENABLE KEYS */;

-- Dumping structure for table tttp.giayuyquyen
DROP TABLE IF EXISTS `giayuyquyen`;
CREATE TABLE IF NOT EXISTS `giayuyquyen` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `duongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `congDan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrmlg1xckpiydvluj2oaocb0lx` (`nguoiSua_id`),
  KEY `FKhelmbi3iux2lyljlh17fqt4vs` (`nguoiTao_id`),
  KEY `FKjt8utfc7xoehvnirferc17e6i` (`congDan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.giayuyquyen: 0 rows
/*!40000 ALTER TABLE `giayuyquyen` DISABLE KEYS */;
/*!40000 ALTER TABLE `giayuyquyen` ENABLE KEYS */;

-- Dumping structure for table tttp.lichsuthaydoi
DROP TABLE IF EXISTS `lichsuthaydoi`;
CREATE TABLE IF NOT EXISTS `lichsuthaydoi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `chiTietThayDoi` longtext COLLATE utf8_vietnamese_ci,
  `doiTuongThayDoi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `idDoiTuong` bigint(20) DEFAULT NULL,
  `noiDung` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbf8c0l5r75h4000beia7f1rg0` (`nguoiSua_id`),
  KEY `FKa9ima7a6hqqc2mqldaj93owui` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.lichsuthaydoi: 17 rows
/*!40000 ALTER TABLE `lichsuthaydoi` DISABLE KEYS */;
INSERT INTO `lichsuthaydoi` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `chiTietThayDoi`, `doiTuongThayDoi`, `idDoiTuong`, `noiDung`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(4, b'0', '2017-05-16 20:44:33', '2017-05-16 20:44:33', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Tân Chính\',\'newValue\': \'phường Tân Chính\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 01\',\'newValue\': \'Tổ 01\'}]', 'CONG_DAN', 16, 'Cập nhật thông tin công dân Trần Võ Đình Nam', 1, 1),
	(5, b'0', '2017-05-17 10:15:01', '2017-05-17 10:15:01', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 24, 'Cập nhật thông tin công dân Nguyễn Văn Khải', 1, 1),
	(6, b'0', '2017-05-17 10:16:44', '2017-05-17 10:16:44', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 24, 'Cập nhật thông tin công dân Nguyễn Văn Khải', 1, 1),
	(7, b'0', '2017-05-17 10:24:00', '2017-05-17 10:24:00', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 24, 'Cập nhật thông tin công dân Nguyễn Văn Khải', 1, 1),
	(8, b'0', '2017-05-17 10:25:14', '2017-05-17 10:25:14', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 24, 'Cập nhật thông tin công dân Nguyễn Văn Khải', 1, 1),
	(9, b'0', '2017-05-17 11:13:27', '2017-05-17 11:13:27', '[{\'propertyName\': \'Ngày sinh\',\'oldValue\': \'02-09-1993\',\'newValue\': \'09-02-1993\'},{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường An Khê\',\'newValue\': \'phường An Khê\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 10\',\'newValue\': \'Tổ 10\'}]', 'CONG_DAN', 26, 'Cập nhật thông tin công dân Phạm Thị Cẩm Nhung', 1, 1),
	(10, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Thạc Gián\',\'newValue\': \'phường Thạc Gián\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 21, 'Cập nhật thông tin công dân Trần Bá Hòa', 1, 1),
	(11, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '[{\'propertyName\': \'Ngày sinh\',\'oldValue\': \'02-09-1989\',\'newValue\': \'29-09-1989\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Ngũ Hành Sơn\',\'newValue\': \'quận Ngũ Hành Sơn\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Mỹ An\',\'newValue\': \'phường Mỹ An\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 10\',\'newValue\': \'Tổ 10\'}]', 'CONG_DAN', 22, 'Cập nhật thông tin công dân Đặng Đình Cường', 1, 1),
	(12, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường An Khê\',\'newValue\': \'phường An Khê\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 02\',\'newValue\': \'Tổ 02\'}]', 'CONG_DAN', 20, 'Cập nhật thông tin công dân Đỗ Bảo Trân', 1, 1),
	(13, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Hải Châu\',\'newValue\': \'quận Hải Châu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hải Châu  I\',\'newValue\': \'phường Hải Châu  I\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 02\',\'newValue\': \'Tổ 02\'}]', 'CONG_DAN', 23, 'Cập nhật thông tin công dân Nguyễn Thị Kim', 1, 1),
	(14, b'0', '2017-05-17 11:23:07', '2017-05-17 11:23:07', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 18, 'Cập nhật thông tin công dân Lê Văn Quý', 1, 1),
	(15, b'0', '2017-05-17 11:53:56', '2017-05-17 11:53:56', '[{\'propertyName\': \'Ngày sinh\',\'oldValue\': \'05-10-1993\',\'newValue\': \'10-05-1993\'},{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Khánh Bắc\',\'newValue\': \'phường Hòa Khánh Bắc\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 27, 'Cập nhật thông tin công dân Đinh Nguyễn Anh Khuê', 1, 1),
	(16, b'0', '2017-05-17 11:53:56', '2017-05-17 11:53:56', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Ngũ Hành Sơn\',\'newValue\': \'quận Ngũ Hành Sơn\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Mỹ An\',\'newValue\': \'phường Mỹ An\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 10\',\'newValue\': \'Tổ 10\'}]', 'CONG_DAN', 22, 'Cập nhật thông tin công dân Đặng Đình Cường', 1, 1),
	(17, b'0', '2017-05-17 12:35:12', '2017-05-17 12:35:12', '[{\'propertyName\': \'Ngày sinh\',\'oldValue\': \'09-02-1993\',\'newValue\': \'02-09-1993\'},{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường An Khê\',\'newValue\': \'phường An Khê\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 10\',\'newValue\': \'Tổ 10\'}]', 'CONG_DAN', 26, 'Cập nhật thông tin công dân Phạm Thị Cẩm Nhung', 1, 1),
	(18, b'0', '2017-05-17 12:36:17', '2017-05-17 12:36:17', '[{\'propertyName\': \'Ngày sinh\',\'oldValue\': \'10-05-1993\',\'newValue\': \'05-10-1993\'},{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Khánh Bắc\',\'newValue\': \'phường Hòa Khánh Bắc\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 27, 'Cập nhật thông tin công dân Đinh Nguyễn Anh Khuê', 1, 1),
	(19, b'0', '2017-05-17 12:36:17', '2017-05-17 12:36:17', '[{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Ngũ Hành Sơn\',\'newValue\': \'quận Ngũ Hành Sơn\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Mỹ An\',\'newValue\': \'phường Mỹ An\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 10\',\'newValue\': \'Tổ 10\'}]', 'CONG_DAN', 22, 'Cập nhật thông tin công dân Đặng Đình Cường', 1, 1),
	(20, b'0', '2017-05-17 19:38:44', '2017-05-17 19:38:44', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Thanh Khê\',\'newValue\': \'quận Thanh Khê\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường An Khê\',\'newValue\': \'phường An Khê\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 02\',\'newValue\': \'Tổ 02\'}]', 'CONG_DAN', 20, 'Cập nhật thông tin công dân Đỗ Bảo Trân', 1, 1),
	(21, b'0', '2017-05-17 20:16:43', '2017-05-17 20:16:43', '[{\'propertyName\': \'Dân tộc\',\'oldValue\': \'Kinh\',\'newValue\': \'Kinh\'},{\'propertyName\': \'Quốc tịch\',\'oldValue\': \'Việt Nam\',\'newValue\': \'Việt Nam\'},{\'propertyName\': \'Tỉnh thành\',\'oldValue\': \'thành phố Đà Nẵng\',\'newValue\': \'thành phố Đà Nẵng\'},{\'propertyName\': \'Quận huyện\',\'oldValue\': \'quận Liên Chiểu\',\'newValue\': \'quận Liên Chiểu\'},{\'propertyName\': \'Phường xã\',\'oldValue\': \'phường Hòa Minh\',\'newValue\': \'phường Hòa Minh\'},{\'propertyName\': \'Thôn tổ\',\'oldValue\': \'Tổ 05\',\'newValue\': \'Tổ 05\'}]', 'CONG_DAN', 24, 'Cập nhật thông tin công dân Nguyễn Văn Khải', 1, 1);
/*!40000 ALTER TABLE `lichsuthaydoi` ENABLE KEYS */;

-- Dumping structure for table tttp.linhvucdonthu
DROP TABLE IF EXISTS `linhvucdonthu`;
CREATE TABLE IF NOT EXISTS `linhvucdonthu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `linhVucKhac` bit(1) NOT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kjnjuj5m9k3h26hws6dfo1ci` (`nguoiSua_id`),
  KEY `FK4a77ckc5l0x15i8fgjfh6s94i` (`nguoiTao_id`),
  KEY `FK5ufddli1x5lbcvwd06o3omv4v` (`cha_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.linhvucdonthu: 19 rows
/*!40000 ALTER TABLE `linhvucdonthu` DISABLE KEYS */;
INSERT INTO `linhvucdonthu` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `linhVucKhac`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `cha_id`) VALUES
	(1, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '001', 'Hành chính', 'Hành chính', 1, 1, NULL),
	(2, b'0', '2017-04-24 10:32:30', '2017-04-24 10:32:37', b'0', '002', 'Tham nhũng', 'Tham nhũng', 1, 1, 1),
	(3, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '003', 'Phản ánh', 'Phản ánh', 1, 1, 1),
	(4, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '004', 'Kiến nghị', 'Kiến nghị', 1, 1, 1),
	(5, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '005', 'Khác', 'Khác', 1, 1, 1),
	(6, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '006', 'Môi trường', 'Môi trường', 1, 1, 3),
	(7, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '007', 'An toàn giao thông', 'An toàn giao thông', 1, 1, 3),
	(8, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '008', 'An ninh trật tự', 'An ninh trật tự', 1, 1, 3),
	(9, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '009', 'Hạ tầng đô thị', 'Hạ tầng đô thị', 1, 1, 3),
	(10, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '010', 'Khác', 'Khác', 1, 1, 3),
	(11, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '011', 'An ninh trật tự', 'An ninh trật tự', 1, 1, 4),
	(12, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '012', 'Hạ tầng đô thị', 'Hạ tầng đô thị', 1, 1, 4),
	(13, b'0', '2017-04-24 10:32:37', '2017-04-24 10:32:37', b'0', '013', 'Khác', 'Khác', 1, 1, 4),
	(14, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '014', 'Đất đai, nhà cửa', 'Đất đai, nhà cửa', 1, 1, 1),
	(15, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '015', 'Tranh chấp', 'Tranh chấp', 1, 1, 14),
	(16, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '016', 'Vi phạm pháp luật trong các lĩnh vực', 'Vi phạm pháp luật trong các lĩnh vực', 1, 1, 1),
	(17, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '017', 'Văn hóa, xã hội', 'Văn hóa, xã hội', 1, 1, 16),
	(18, b'0', '2017-04-24 10:31:04', '2017-04-24 10:32:37', b'0', '018', 'Tư pháp', 'Tư pháp', 1, 1, NULL),
	(19, b'0', '2017-04-24 10:32:30', '2017-04-24 10:32:37', b'0', '019', 'Tham nhũng', 'Tham nhũng', 1, 1, 18);
/*!40000 ALTER TABLE `linhvucdonthu` ENABLE KEYS */;

-- Dumping structure for table tttp.loaicoquanquanly
DROP TABLE IF EXISTS `loaicoquanquanly`;
CREATE TABLE IF NOT EXISTS `loaicoquanquanly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK828hnqxbnb71xfuwd5o29999k` (`nguoiSua_id`),
  KEY `FK6rpc6hsmc4i3w3iiwy0vcmw96` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.loaicoquanquanly: 2 rows
/*!40000 ALTER TABLE `loaicoquanquanly` DISABLE KEYS */;
INSERT INTO `loaicoquanquanly` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:41', 'Bộ Công An', 'Bộ Công An', 1, 1),
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:41', 'Sở Y Tế', 'Sở Y Tế', 1, 1);
/*!40000 ALTER TABLE `loaicoquanquanly` ENABLE KEYS */;

-- Dumping structure for table tttp.loaitailieu
DROP TABLE IF EXISTS `loaitailieu`;
CREATE TABLE IF NOT EXISTS `loaitailieu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3wnssk7lrmpa95kuuip3024y7` (`nguoiSua_id`),
  KEY `FKmsjjnjbwyc2ethakh05latafr` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.loaitailieu: 5 rows
/*!40000 ALTER TABLE `loaitailieu` DISABLE KEYS */;
INSERT INTO `loaitailieu` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-05-03 15:47:23', '2017-04-27 08:29:07', 'Loai tai lieu 1', 'Loại tài liệu 1', 1, 1),
	(2, b'0', '2017-05-03 15:53:34', '2017-05-03 15:47:36', 'Loại tài liệu 2', 'Loại tài liệu 2', 1, 1),
	(3, b'0', '2017-05-03 15:53:31', '2017-05-03 15:50:26', 'Loại tài liệu 3', 'Loại tài liệu 3', 1, 1),
	(4, b'0', '2017-05-03 15:53:45', '2017-05-03 15:53:45', 'Loại tài liệu 4', 'Loại tài liệu 4', 1, 1),
	(5, b'0', '2017-05-03 15:53:56', '2017-05-03 15:53:53', 'Loại tài liệu 5', 'Loại tài liệu 5', 1, 1);
/*!40000 ALTER TABLE `loaitailieu` ENABLE KEYS */;

-- Dumping structure for table tttp.medial_doncongdan
DROP TABLE IF EXISTS `medial_doncongdan`;
CREATE TABLE IF NOT EXISTS `medial_doncongdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6usuw7ves846reoahcdnm1hqt` (`nguoiSua_id`),
  KEY `FKmj2yc9vessmt4q5ctc7p1cyow` (`nguoiTao_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.medial_doncongdan: 0 rows
/*!40000 ALTER TABLE `medial_doncongdan` DISABLE KEYS */;
/*!40000 ALTER TABLE `medial_doncongdan` ENABLE KEYS */;

-- Dumping structure for table tttp.nguoidung
DROP TABLE IF EXISTS `nguoidung`;
CREATE TABLE IF NOT EXISTS `nguoidung` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `hinhDaiDien` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `matKhau` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `salkey` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `tenDangNhap` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `vaiTroMacDinh_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj7xotjm1dm5pirxi26gxos8t5` (`nguoiSua_id`),
  KEY `FKeabbvfd8a1o51p2pbjnvcbydk` (`nguoiTao_id`),
  KEY `FK4oq97pi5mobmpar00hwqj327q` (`vaiTroMacDinh_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.nguoidung: 11 rows
/*!40000 ALTER TABLE `nguoidung` DISABLE KEYS */;
INSERT INTO `nguoidung` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `active`, `hinhDaiDien`, `matKhau`, `salkey`, `tenDangNhap`, `nguoiSua_id`, `nguoiTao_id`, `vaiTroMacDinh_id`) VALUES
	(1, b'0', '2017-05-17 19:48:22', '2017-04-24 10:49:48', b'1', '', '8dc12f49943d3305e49bd87d26e4c4bf', 'L8LWQ8eZFMpu1+AsqxRb/seH166G/iRY', 'admin', 1, 1, 1),
	(2, b'0', '2017-05-17 19:15:55', '2017-05-03 15:30:58', b'1', '', 'e6f02da77290f66dbba7b0947764281d', 'yyLqe+u5/MImnlmm/muyF4hljjgtl7zS', 'namtvd', 1, 1, 2),
	(4, b'0', '2017-05-17 19:16:16', '2017-05-03 15:51:28', b'1', '', '2ee7904aa806d4e0b26b65db1e5cf6dc', 'yqdA4DKrjXsNBNb7fde/8ruFCXcwS/DY', 'thanhpx', 1, 1, 3),
	(5, b'0', '2017-05-17 19:22:32', '2017-05-03 15:52:46', b'1', '', '11563480e09f3f4bd87f820f3634784a', 'lUJE/vyVzynuxuQ3U+jhzskqXg07SJEY', 'anvt', 1, 1, 4),
	(6, b'0', '2017-05-17 19:15:47', '2017-05-17 19:06:57', b'1', '', 'db964ed0e48334b3145c84d9740bcb54', 'p5sEg9zcVa9QVGrK/ddgUiBwfNRPmC7O', 'vinhlv', 1, 1, 3),
	(11, b'0', '2017-05-17 19:54:26', '2017-05-17 19:52:42', b'1', '', '0859b1a9b885e1212e0ff56097521897', 'szB1ijYcKLXkKIaOrNnnM/4F6iCFwIwu', 'thienpd', 1, 1, 1),
	(7, b'0', '2017-05-17 19:42:57', '2017-05-17 19:42:57', b'1', '', 'ccb3e33d991c1e3867c6457973146b3a', 'g1/38gxr0prekM1TK9r2KfOhMKDCmld4', 'thanghq', 1, 1, 3),
	(8, b'0', '2017-05-17 19:43:49', '2017-05-17 19:43:49', b'1', '', '22a2b80d443ad69e2164935a339c82b0', 'eOazgi3Kkhx6rHsyTbYEEdDEw8hwzO+g', 'toantt', 1, 1, 3),
	(9, b'0', '2017-05-17 19:44:59', '2017-05-17 19:44:59', b'1', '', '34386061c26dace16a6000c0074c040d', 'f1o5WB5PF5Fmcd2Ek2mDgLPZt5SX8726', 'trandtb', 1, 1, 3),
	(10, b'0', '2017-05-17 19:47:34', '2017-05-17 19:47:34', b'1', '', 'ed550b3c90352310e6e1c777220b4cc7', '6CzDlL5evdj8CR6Y2TRyKv+bTp6X7wJn', 'thanhhx', 1, 1, 1),
	(12, b'0', '2017-05-17 19:54:33', '2017-05-17 19:53:38', b'1', '', '1abac9086a232c85244587a99bfcd60f', 'f8O5JLRCsM0BnNX9dH0ZPFig6q2o9Fop', 'vutx', 1, 1, 1);
/*!40000 ALTER TABLE `nguoidung` ENABLE KEYS */;

-- Dumping structure for table tttp.nguoidung_quyen
DROP TABLE IF EXISTS `nguoidung_quyen`;
CREATE TABLE IF NOT EXISTS `nguoidung_quyen` (
  `nguoidung_id` bigint(20) NOT NULL,
  `quyens` longtext COLLATE utf8_vietnamese_ci,
  KEY `FKqdfgx2vnr6lihkltybhafc0gx` (`nguoidung_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.nguoidung_quyen: 0 rows
/*!40000 ALTER TABLE `nguoidung_quyen` DISABLE KEYS */;
/*!40000 ALTER TABLE `nguoidung_quyen` ENABLE KEYS */;

-- Dumping structure for table tttp.nguoidung_vaitro
DROP TABLE IF EXISTS `nguoidung_vaitro`;
CREATE TABLE IF NOT EXISTS `nguoidung_vaitro` (
  `nguoidung_id` bigint(20) NOT NULL,
  `vaitro_id` bigint(20) NOT NULL,
  PRIMARY KEY (`nguoidung_id`,`vaitro_id`),
  KEY `FKnyd5p3gsmyqm2b2j7hna2him8` (`vaitro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.nguoidung_vaitro: 14 rows
/*!40000 ALTER TABLE `nguoidung_vaitro` DISABLE KEYS */;
INSERT INTO `nguoidung_vaitro` (`nguoidung_id`, `vaitro_id`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 2),
	(4, 3),
	(5, 4),
	(6, 3),
	(7, 3),
	(8, 3),
	(9, 3),
	(10, 1),
	(11, 1),
	(12, 1);
/*!40000 ALTER TABLE `nguoidung_vaitro` ENABLE KEYS */;

-- Dumping structure for table tttp.quoctich
DROP TABLE IF EXISTS `quoctich`;
CREATE TABLE IF NOT EXISTS `quoctich` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ma` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdfnt1b6uy4xax7d4bb9nvfwse` (`nguoiSua_id`),
  KEY `FKnsx4y3fp1qw87nradwkd9vnw0` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=252 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.quoctich: 250 rows
/*!40000 ALTER TABLE `quoctich` DISABLE KEYS */;
INSERT INTO `quoctich` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ma`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(106, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IT', 'Italia', 'Italia', 1, 1),
	(107, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'JM', 'Gia-mai-ca', 'Gia-mai-ca', 1, 1),
	(108, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'JP', 'Nhật', 'Nhật', 1, 1),
	(109, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'JO', 'Gioóc-đa-ni', 'Gioóc-đa-ni', 1, 1),
	(110, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KZ', 'Ca-dắc-xtan', 'Ca-dắc-xtan', 1, 1),
	(111, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KE', 'Cộng hòa Kenya', 'Cộng hòa Kenya', 1, 1),
	(112, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KI', 'Cộng hòa Kiribati', 'Cộng hòa Kiribati', 1, 1),
	(113, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KP', 'Cộng hòa Dân chủ Nhân dân Triều Tiên', 'Cộng hòa Dân chủ Nhân dân Triều Tiên', 1, 1),
	(114, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KR', 'Hàn Quốc', 'Hàn Quốc', 1, 1),
	(115, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KW', 'Cô-oét', 'Cô-oét', 1, 1),
	(116, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KG', 'Cư-rơ-gư-dơ-xtan', 'Cư-rơ-gư-dơ-xtan', 1, 1),
	(117, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LA', 'Lào', 'Lào', 1, 1),
	(118, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LV', 'Lát-vi-a', 'Lát-vi-a', 1, 1),
	(119, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LB', 'Li-băng', 'Li-băng', 1, 1),
	(120, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LS', 'Vương quốc Lesotho', 'Vương quốc Lesotho', 1, 1),
	(121, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LR', 'Cộng hòa Liberia', 'Cộng hòa Liberia', 1, 1),
	(122, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LY', 'Li-bi', 'Li-bi', 1, 1),
	(123, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LI', 'Thân vương quốc Liechtenstein', 'Thân vương quốc Liechtenstein', 1, 1),
	(124, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LT', 'Lít-va', 'Lít-va', 1, 1),
	(125, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LU', 'Lúc-xăm-bua', 'Lúc-xăm-bua', 1, 1),
	(126, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MO', 'Ma Cao', 'Ma Cao', 1, 1),
	(127, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MK', 'Cộng hòa Macedonia', 'Cộng hòa Macedonia', 1, 1),
	(128, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MG', 'Cộng hòa Madagascar', 'Cộng hòa Madagascar', 1, 1),
	(129, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MW', 'Ma-la-uy', 'Ma-la-uy', 1, 1),
	(130, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MY', 'Malaysia', 'Malaysia', 1, 1),
	(131, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MV', 'Man-đi-vơ', 'Man-đi-vơ', 1, 1),
	(132, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ML', 'Cộng hòa Mali', 'Cộng hòa Mali', 1, 1),
	(133, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MT', 'Cộng hòa Malta', 'Cộng hòa Malta', 1, 1),
	(134, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MH', 'Quần đảo Marshall', 'Quần đảo Marshall', 1, 1),
	(135, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MQ', 'Martinique', 'Martinique', 1, 1),
	(136, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MR', 'Mô-ri-ta-ni', 'Mô-ri-ta-ni', 1, 1),
	(137, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MU', 'Mô-ri-xơ', 'Mô-ri-xơ', 1, 1),
	(138, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'YT', 'Tỉnh Mayotte', 'Tỉnh Mayotte', 1, 1),
	(139, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MX', 'Mê-xi-cô', 'Mê-xi-cô', 1, 1),
	(140, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FM', 'Liên bang Micronesia', 'Liên bang Micronesia', 1, 1),
	(141, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MD', 'Moldova', 'Moldova', 1, 1),
	(142, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MC', 'Công quốc Monaco', 'Công quốc Monaco', 1, 1),
	(143, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MN', 'Mông Cổ', 'Mông Cổ', 1, 1),
	(144, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MS', 'Montserrat', 'Montserrat', 1, 1),
	(145, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MA', 'Vương quốc Maroc', 'Vương quốc Maroc', 1, 1),
	(146, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MZ', 'Cộng hòa Mozambique', 'Cộng hòa Mozambique', 1, 1),
	(147, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MM', 'Mi-an-ma', 'Mi-an-ma', 1, 1),
	(148, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NA', 'Cộng hòa Namibia', 'Cộng hòa Namibia', 1, 1),
	(149, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NR', 'Cộng hòa Nauru', 'Cộng hòa Nauru', 1, 1),
	(150, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NP', 'Nê-pan', 'Nê-pan', 1, 1),
	(151, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NL', 'Hà Lan', 'Hà Lan', 1, 1),
	(152, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AN', 'Antille thuộc Hà Lan', 'Antille thuộc Hà Lan', 1, 1),
	(153, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NC', 'Kanaky và Le caillou', 'Kanaky và Le caillou', 1, 1),
	(154, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NZ', 'Niu Di-lân', 'Niu Di-lân', 1, 1),
	(155, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NI', 'Nicaragua', 'Nicaragua', 1, 1),
	(156, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NE', 'Cộng hoà Niger', 'Cộng hoà Niger', 1, 1),
	(157, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NG', 'Cộng hòa Liên bang Nigeria', 'Cộng hòa Liên bang Nigeria', 1, 1),
	(158, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NU', 'Niue', 'Niue', 1, 1),
	(159, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NF', 'Đảo Norfolk', 'Đảo Norfolk', 1, 1),
	(160, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MP', 'Quần đảo Bắc Mariana', 'Quần đảo Bắc Mariana', 1, 1),
	(161, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'NO', 'Na Uy', 'Na Uy', 1, 1),
	(162, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'OM', 'Ô-man', 'Ô-man', 1, 1),
	(163, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PK', 'Pa-ki-xtan', 'Pa-ki-xtan', 1, 1),
	(164, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PW', 'Cộng hòa Palau', 'Cộng hòa Palau', 1, 1),
	(165, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PS', 'Chính quyền Quốc gia Palestine', 'Chính quyền Quốc gia Palestine', 1, 1),
	(166, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PA', 'Cộng hòa Panama', 'Cộng hòa Panama', 1, 1),
	(167, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PG', 'Pa-pu-a Niu Ghi-nê', 'Pa-pu-a Niu Ghi-nê', 1, 1),
	(168, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PY', 'Cộng hòa Paraguay', 'Cộng hòa Paraguay', 1, 1),
	(169, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PE', 'Cộng hòa Peru', 'Cộng hòa Peru', 1, 1),
	(170, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PH', 'Philippines', 'Philippines', 1, 1),
	(171, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PN', 'Quần đảo Pitcairn', 'Quần đảo Pitcairn', 1, 1),
	(172, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PL', 'Ba Lan', 'Ba Lan', 1, 1),
	(173, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PT', 'Bồ Đào Nha', 'Bồ Đào Nha', 1, 1),
	(174, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PR', 'Pu-éc-tô Ri-cô', 'Pu-éc-tô Ri-cô', 1, 1),
	(175, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'QA', 'Ca-ta', 'Ca-ta', 1, 1),
	(176, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'RE', 'Đảo Réunion', 'Đảo Réunion', 1, 1),
	(177, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'RO', 'Rumani', 'Rumani', 1, 1),
	(178, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'RU', 'Nga', 'Nga', 1, 1),
	(179, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'RW', 'Cộng hòa Ru-an-đa', 'Cộng hòa Ru-an-đa', 1, 1),
	(180, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SH', 'Xanh hê-li-na', 'Xanh hê-li-na', 1, 1),
	(181, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KN', 'Liên bang Saint Kitts và Nevis ', 'Liên bang Saint Kitts và Nevis ', 1, 1),
	(182, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LC', 'Saint Lucia', 'Saint Lucia', 1, 1),
	(183, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PM', 'Saint-Pierre và Miquelon', 'Saint-Pierre và Miquelon', 1, 1),
	(184, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VC', 'Saint Vincent và Grenadines', 'Saint Vincent và Grenadines', 1, 1),
	(185, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'WS', 'Nhà nước Độc lập Samoa', 'Nhà nước Độc lập Samoa', 1, 1),
	(186, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SM', 'Cộng hòa Đại bình yên San Marino', 'Cộng hòa Đại bình yên San Marino', 1, 1),
	(187, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ST', 'Cộng hòa Dân chủ São Tomé và Príncipe', 'Cộng hòa Dân chủ São Tomé và Príncipe', 1, 1),
	(188, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SA', 'Ả-rập Xê-út', 'Ả-rập Xê-út', 1, 1),
	(189, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SN', 'Cộng hoà Sénégal', 'Cộng hoà Sénégal', 1, 1),
	(190, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CS', 'Serbia và Montenegro', 'Serbia và Montenegro', 1, 1),
	(191, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SC', 'Xây-sen', 'Xây-sen', 1, 1),
	(192, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SL', 'Xi-ê-ra Lê-ôn', 'Xi-ê-ra Lê-ôn', 1, 1),
	(193, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SG', 'Singapore ', 'Singapore ', 1, 1),
	(194, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SK', 'Cộng hoà Slovak', 'Cộng hoà Slovak', 1, 1),
	(195, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SI', 'Cộng hòa Slovenia', 'Cộng hòa Slovenia', 1, 1),
	(196, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SB', 'Quần đảo Solomon', 'Quần đảo Solomon', 1, 1),
	(197, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SO', 'Cộng hoà Liên bang Somalia', 'Cộng hoà Liên bang Somalia', 1, 1),
	(198, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ZA', 'Cộng hòa Nam Phi', 'Cộng hòa Nam Phi', 1, 1),
	(199, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GS', 'Nam Georgia và Quần đảo Nam Sandwich', 'Nam Georgia và Quần đảo Nam Sandwich', 1, 1),
	(200, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ES', 'Tây Ban Nha', 'Tây Ban Nha', 1, 1),
	(201, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'LK', 'Sri Lanka', 'Sri Lanka', 1, 1),
	(202, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SD', 'Xu-đăng', 'Xu-đăng', 1, 1),
	(203, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SR', 'Xu-ri-nam', 'Xu-ri-nam', 1, 1),
	(204, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SJ', 'Svalbard và Jan Mayen', 'Svalbard và Jan Mayen', 1, 1),
	(205, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SZ', 'Xoa-di-len', 'Xoa-di-len', 1, 1),
	(206, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SE', 'Thụy Điển', 'Thụy Điển', 1, 1),
	(207, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CH', 'Thụy Sĩ', 'Thụy Sĩ', 1, 1),
	(208, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SY', 'Syria', 'Syria', 1, 1),
	(209, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TW', 'Trung quốc (Đài Loan)', 'Trung quốc (Đài Loan)', 1, 1),
	(210, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TJ', 'Ta-gi-ki-xtan', 'Ta-gi-ki-xtan', 1, 1),
	(211, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TZ', 'Tanzania', 'Tanzania', 1, 1),
	(212, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TH', 'Thái Lan', 'Thái Lan', 1, 1),
	(213, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TL', 'Đông Ti-mo', 'Đông Ti-mo', 1, 1),
	(214, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TG', 'Cộng hòa Togo', 'Cộng hòa Togo', 1, 1),
	(215, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TK', 'Tokelau', 'Tokelau', 1, 1),
	(216, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TO', 'Tonga', 'Tonga', 1, 1),
	(217, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TT', 'Trinidad và Tobago', 'Trinidad và Tobago', 1, 1),
	(218, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TN', 'Tunisia', 'Tunisia', 1, 1),
	(219, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TR', 'Turkey', 'Turkey', 1, 1),
	(220, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TM', 'Tuốc-mê-ni-xtan', 'Tuốc-mê-ni-xtan', 1, 1),
	(221, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TC', 'Quần đảo Turks và Caicos', 'Quần đảo Turks và Caicos', 1, 1),
	(222, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TV', 'Tuvalu', 'Tuvalu', 1, 1),
	(223, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'UG', 'Cộng hòa Uganda', 'Cộng hòa Uganda', 1, 1),
	(224, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'UA', 'Ukraina', 'Ukraina', 1, 1),
	(225, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AE', 'Các Tiểu Vương quốc Ả Rập Thống nhất', 'Các Tiểu Vương quốc Ả Rập Thống nhất', 1, 1),
	(226, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GB', 'Vương quốc Liên hiệp Anh và Bắc Ireland', 'Vương quốc Liên hiệp Anh và Bắc Ireland', 1, 1),
	(227, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'US', 'Hoa Kỳ', 'Hoa Kỳ', 1, 1),
	(228, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'UM', 'Các tiểu đảo xa của Hoa Kỳ', 'Các tiểu đảo xa của Hoa Kỳ', 1, 1),
	(229, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'UY', 'Uruguay', 'Uruguay', 1, 1),
	(230, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'UZ', 'U-dơ-bê-ki-xtan', 'U-dơ-bê-ki-xtan', 1, 1),
	(231, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VU', 'Cộng hòa Vanuatu', 'Cộng hòa Vanuatu', 1, 1),
	(233, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VE', 'Cộng hòa Bolivar Venezuela', 'Cộng hòa Bolivar Venezuela', 1, 1),
	(234, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AF', 'Cộng hòa Hồi giáo Afghanistan', 'Cộng hòa Hồi giáo Afghanistan', 1, 1),
	(235, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VG', 'Quần đảo Virgin thuộc Anh', 'Quần đảo Virgin thuộc Anh', 1, 1),
	(236, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VI', 'Quần đảo Virgin thuộc Mỹ', 'Quần đảo Virgin thuộc Mỹ', 1, 1),
	(237, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'WF', 'Wallis và Futuna', 'Wallis và Futuna', 1, 1),
	(238, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'EH', 'Tây Sahara', 'Tây Sahara', 1, 1),
	(239, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'YE', 'Y-ê-men', 'Y-ê-men', 1, 1),
	(240, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ZM', 'Zambia ', 'Zambia ', 1, 1),
	(241, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ZW', 'Cộng hòa Zimbabwe', 'Cộng hòa Zimbabwe', 1, 1),
	(242, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BIH', 'Bosna và Hercegovina', 'Bosna và Hercegovina', 1, 1),
	(243, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CUW', 'Curaçao', 'Curaçao', 1, 1),
	(244, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GGY', 'Địa hạt Guernsey', 'Địa hạt Guernsey', 1, 1),
	(245, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IMN', 'Đảo Man', 'Đảo Man', 1, 1),
	(246, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'JEY', 'Jersey', 'Jersey', 1, 1),
	(247, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MNE', 'Cộng hòa Montenegro', 'Cộng hòa Montenegro', 1, 1),
	(248, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BLM', 'Saint-Barthélemy', 'Saint-Barthélemy', 1, 1),
	(249, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'MAF', 'Saint Martin (French part)', 'Saint Martin (French part)', 1, 1),
	(250, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SXM', 'Sint Maarten (Dutch part)', 'Sint Maarten (Dutch part)', 1, 1),
	(251, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SSD', 'South Sudan', 'South Sudan', 1, 1),
	(1, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VIE', 'Việt Nam', 'Việt Nam', 1, 1),
	(2, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AX', 'And Islands', 'And Islands', 1, 1),
	(3, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AL', 'An-ba-ni', 'An-ba-ni', 1, 1),
	(4, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DZ', 'An-giê-ri', 'An-giê-ri', 1, 1),
	(5, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AS', 'Samoa thuộc Mỹ', 'Samoa thuộc Mỹ', 1, 1),
	(6, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AD', 'An-đô-ra', 'An-đô-ra', 1, 1),
	(7, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AO', 'Ăng-gô-la', 'Ăng-gô-la', 1, 1),
	(8, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AI', 'Anguilla', 'Anguilla', 1, 1),
	(9, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AQ', 'Nam cực', 'Nam cực', 1, 1),
	(10, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AG', 'An-ti-goa và Bác-bu-đa', 'An-ti-goa và Bác-bu-đa', 1, 1),
	(11, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AR', 'Ác-hen-ti-na', 'Ác-hen-ti-na', 1, 1),
	(12, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AM', 'Ác-mê-ni-a', 'Ác-mê-ni-a', 1, 1),
	(13, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AW', 'Aruba', 'Aruba', 1, 1),
	(14, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AU', 'Úc', 'Úc', 1, 1),
	(15, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AT', 'Áo', 'Áo', 1, 1),
	(16, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'AZ', 'A-déc-bai-gian', 'A-déc-bai-gian', 1, 1),
	(17, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BS', 'Ba-ha-mát', 'Ba-ha-mát', 1, 1),
	(18, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BH', 'Vương quốc Ba-ranh', 'Vương quốc Ba-ranh', 1, 1),
	(19, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BD', 'Băng-la-đét', 'Băng-la-đét', 1, 1),
	(20, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BB', 'Bác-ba-đốt', 'Bác-ba-đốt', 1, 1),
	(21, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BY', 'Bê-la-rút', 'Bê-la-rút', 1, 1),
	(22, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BE', 'Bỉ', 'Bỉ', 1, 1),
	(23, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BZ', 'Bê-li-xê', 'Bê-li-xê', 1, 1),
	(24, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BJ', 'Bê-nanh', 'Bê-nanh', 1, 1),
	(25, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BM', 'Quần đảo Bermuda', 'Quần đảo Bermuda', 1, 1),
	(26, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BT', 'Bu-tan', 'Bu-tan', 1, 1),
	(27, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BO', 'Bô-li-vi-a', 'Bô-li-vi-a', 1, 1),
	(28, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BA', 'Bosnia và herzegovina', 'Bosnia và herzegovina', 1, 1),
	(29, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BW', 'Bốt-xoa-na', 'Bốt-xoa-na', 1, 1),
	(30, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BV', 'Đảo Bouvet', 'Đảo Bouvet', 1, 1),
	(31, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BR', 'Bra-xin', 'Bra-xin', 1, 1),
	(32, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IO', 'Lãnh thổ Ấn Độ Dương', 'Lãnh thổ Ấn Độ Dương', 1, 1),
	(33, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BN', 'Nhà nước Brunei Darussalam', 'Nhà nước Brunei Darussalam', 1, 1),
	(34, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BG', 'Bun-ga-ri', 'Bun-ga-ri', 1, 1),
	(35, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BF', 'Buốc-ki-na Pha-xô', 'Buốc-ki-na Pha-xô', 1, 1),
	(36, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'BI', 'Cộng hòa Bu-run-đi', 'Cộng hòa Bu-run-đi', 1, 1),
	(37, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KH', 'Campuchia', 'Campuchia', 1, 1),
	(38, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CM', 'Cộng hòa Cameroon', 'Cộng hòa Cameroon', 1, 1),
	(39, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CA', 'Ca-na-đa', 'Ca-na-đa', 1, 1),
	(40, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CV', 'Cáp Ve', 'Cáp Ve', 1, 1),
	(41, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KY', 'Quần đảo Cayman', 'Quần đảo Cayman', 1, 1),
	(42, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CF', 'Cộng hòa Trung Phi', 'Cộng hòa Trung Phi', 1, 1),
	(43, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TD', 'Sát', 'Sát', 1, 1),
	(44, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CL', 'Chi-lê', 'Chi-lê', 1, 1),
	(45, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CN', 'Trung Quốc', 'Trung Quốc', 1, 1),
	(46, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CX', 'Đảo Christmas', 'Đảo Christmas', 1, 1),
	(47, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CC', 'Tiểu bang và vùng lãnh thổ Úc', 'Tiểu bang và vùng lãnh thổ Úc', 1, 1),
	(48, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CO', 'Cộng hòa Cô-lôm-bi-a', 'Cộng hòa Cô-lôm-bi-a', 1, 1),
	(49, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'KM', 'Cô-mo', 'Cô-mo', 1, 1),
	(50, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CG', 'Công-gô', 'Công-gô', 1, 1),
	(51, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CD', 'Cộng hòa Dân chủ Công-gô', 'Cộng hòa Dân chủ Công-gô', 1, 1),
	(52, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CK', 'Quần đảo Cook', 'Quần đảo Cook', 1, 1),
	(53, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CR', 'Cộng hoà Costa Rica', 'Cộng hoà Costa Rica', 1, 1),
	(54, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CI', 'Bờ Biển Ngà', 'Bờ Biển Ngà', 1, 1),
	(55, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HR', 'Crô-a-ti-a', 'Crô-a-ti-a', 1, 1),
	(56, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CU', 'Cu Ba', 'Cu Ba', 1, 1),
	(57, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CY', 'Cộng hòa Síp', 'Cộng hòa Síp', 1, 1),
	(58, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'CZ', 'Cộng hòa Séc', 'Cộng hòa Séc', 1, 1),
	(59, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DK', 'Đan Mạch', 'Đan Mạch', 1, 1),
	(60, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DJ', 'Ả Rập', 'Ả Rập', 1, 1),
	(61, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DM', 'Thịnh vượng chung Dominica', 'Thịnh vượng chung Dominica', 1, 1),
	(62, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DO', 'Cộng hòa Đô-mi-ni-ca-na', 'Cộng hòa Đô-mi-ni-ca-na', 1, 1),
	(63, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'EC', 'Cộng hòa Ê-cu-a-đo', 'Cộng hòa Ê-cu-a-đo', 1, 1),
	(64, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'EG', 'Ai Cập', 'Ai Cập', 1, 1),
	(65, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'SV', 'Cộng hòa En Xan-va-đo', 'Cộng hòa En Xan-va-đo', 1, 1),
	(66, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GQ', 'Ghi-nê Xích Đạo', 'Ghi-nê Xích Đạo', 1, 1),
	(67, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ER', 'Ê-ri-tơ-rê-a', 'Ê-ri-tơ-rê-a', 1, 1),
	(68, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'EE', 'E-xtô-ni-a', 'E-xtô-ni-a', 1, 1),
	(69, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ET', 'Ê-ti-ô-pi-a', 'Ê-ti-ô-pi-a', 1, 1),
	(70, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FK', 'Quần đảo Falkland', 'Quần đảo Falkland', 1, 1),
	(71, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FO', 'Quần đảo Faroe', 'Quần đảo Faroe', 1, 1),
	(72, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FJ', 'Cộng hòa Quần đảo Phi-gi', 'Cộng hòa Quần đảo Phi-gi', 1, 1),
	(73, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FI', 'Phần Lan', 'Phần Lan', 1, 1),
	(74, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'FR', 'Pháp', 'Pháp', 1, 1),
	(75, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GF', 'Guyane thuộc Pháp', 'Guyane thuộc Pháp', 1, 1),
	(76, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'PF', 'Polynesia thuộc Pháp', 'Polynesia thuộc Pháp', 1, 1),
	(77, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'TF', 'Vùng đất phía nam thuộc Pháp', 'Vùng đất phía nam thuộc Pháp', 1, 1),
	(78, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GA', 'Cộng hòa Ga-bông', 'Cộng hòa Ga-bông', 1, 1),
	(79, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GM', 'Cộng hòa Găm-bi-a', 'Cộng hòa Găm-bi-a', 1, 1),
	(80, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GE', 'Georgia, Hoa Kỳ', 'Georgia, Hoa Kỳ', 1, 1),
	(81, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'DE', 'Đức', 'Đức', 1, 1),
	(82, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GH', 'Ga-na', 'Ga-na', 1, 1),
	(83, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GI', 'Gibraltar', 'Gibraltar', 1, 1),
	(84, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GR', 'Hy Lạp', 'Hy Lạp', 1, 1),
	(85, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GL', 'Greenland', 'Greenland', 1, 1),
	(86, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GD', 'Grê-na-đa', 'Grê-na-đa', 1, 1),
	(87, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GP', 'Goa-đê-lốp', 'Goa-đê-lốp', 1, 1),
	(88, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GU', 'Lãnh thổ Guam', 'Lãnh thổ Guam', 1, 1),
	(89, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GT', 'Cộng hoà Guatemala', 'Cộng hoà Guatemala', 1, 1),
	(90, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GN', 'Cộng hòa Ghi-nê', 'Cộng hòa Ghi-nê', 1, 1),
	(91, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GW', 'Ghi-nê Bít-xao', 'Ghi-nê Bít-xao', 1, 1),
	(92, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'GY', 'Guy-a-na', 'Guy-a-na', 1, 1),
	(93, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HT', 'Cộng hòa Ha-i-ti', 'Cộng hòa Ha-i-ti', 1, 1),
	(94, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HM', 'Đảo Heard và quần đảo McDonald', 'Đảo Heard và quần đảo McDonald', 1, 1),
	(95, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'VA', 'Thành Quốc Vatican', 'Thành Quốc Vatican', 1, 1),
	(96, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HN', 'Cộng hoà Honduras', 'Cộng hoà Honduras', 1, 1),
	(97, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HK', 'Hồng Kông', 'Hồng Kông', 1, 1),
	(98, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'HU', 'Hung-ga-ri', 'Hung-ga-ri', 1, 1),
	(99, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IS', 'Ai-xơ-len', 'Ai-xơ-len', 1, 1),
	(100, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IN', 'Ấn Độ', 'Ấn Độ', 1, 1),
	(101, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'ID', 'In-đô-nê-xi-a', 'In-đô-nê-xi-a', 1, 1),
	(102, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IR', 'Iran', 'Iran', 1, 1),
	(103, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IQ', 'Iraq', 'Iraq', 1, 1),
	(104, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IE', 'Ai-len', 'Ai-len', 1, 1),
	(105, b'0', '2017-04-23 15:54:07', '2017-04-23 15:54:07', 'IL', 'Israel', 'Israel', 1, 1);
/*!40000 ALTER TABLE `quoctich` ENABLE KEYS */;

-- Dumping structure for table tttp.sotiepcongdan
DROP TABLE IF EXISTS `sotiepcongdan`;
CREATE TABLE IF NOT EXISTS `sotiepcongdan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `diaDiemGapLanhDao` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ghiChuXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `hoanThanhTCDLanhDao` bit(1) NOT NULL,
  `huongGiaiQuyetTCDLanhDao` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `huongXuLy` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ketQuaGiaiQuyet` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `loaiTiepDan` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ngayHenGapLanhDao` datetime DEFAULT NULL,
  `ngayTiepDan` datetime NOT NULL,
  `noiDungBoSung` longtext COLLATE utf8_vietnamese_ci,
  `noiDungTiepCongDan` longtext COLLATE utf8_vietnamese_ci,
  `soThuTuLuotTiep` int(11) NOT NULL,
  `thoiHan` datetime DEFAULT NULL,
  `trangThaiKetQua` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `yKienXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `canBoTiepDan_id` bigint(20) NOT NULL,
  `don_id` bigint(20) NOT NULL,
  `donViChuTri_id` bigint(20) DEFAULT NULL,
  `donViTiepDan_id` bigint(20) NOT NULL,
  `phongBanGiaiQuyet_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbo31wp05ykmu54gqqps7yykxs` (`nguoiSua_id`),
  KEY `FK2r3f13m3a8ea034sb5uq54td` (`nguoiTao_id`),
  KEY `FKpoqd3i2h5lxtvo7au4oss8s51` (`canBoTiepDan_id`),
  KEY `FK3vr19x07drifxy8dfdwynpwvx` (`don_id`),
  KEY `FK1fgxsmec6nwo5c7rux40pogp8` (`donViChuTri_id`),
  KEY `FK6grbn4yn0d8g5ebg6jhbsew1m` (`donViTiepDan_id`),
  KEY `FKk0e4yuj18g9sn7okgv6yncp7h` (`phongBanGiaiQuyet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.sotiepcongdan: 13 rows
/*!40000 ALTER TABLE `sotiepcongdan` DISABLE KEYS */;
INSERT INTO `sotiepcongdan` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `diaDiemGapLanhDao`, `ghiChuXuLy`, `hoanThanhTCDLanhDao`, `huongGiaiQuyetTCDLanhDao`, `huongXuLy`, `ketQuaGiaiQuyet`, `loaiTiepDan`, `ngayHenGapLanhDao`, `ngayTiepDan`, `noiDungBoSung`, `noiDungTiepCongDan`, `soThuTuLuotTiep`, `thoiHan`, `trangThaiKetQua`, `yKienXuLy`, `nguoiSua_id`, `nguoiTao_id`, `canBoTiepDan_id`, `don_id`, `donViChuTri_id`, `donViTiepDan_id`, `phongBanGiaiQuyet_id`) VALUES
	(35, b'0', '2017-05-17 09:45:37', '2017-05-17 09:45:37', '', '', b'0', NULL, 'TU_CHOI', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 09:39:35', ' ', 'Chưa có thông tin hồ sơ tài liệu bằng chứng', 1, NULL, '', '', 1, 1, 1, 18, NULL, 2, NULL),
	(36, b'0', '2017-05-17 10:05:05', '2017-05-17 10:05:05', '', '', b'0', NULL, 'BO_SUNG_THONG_TIN', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 09:48:57', ' ', 'Bổ sung thông tin đơn thư', 1, NULL, '', '', 1, 1, 1, 19, NULL, 2, NULL),
	(37, b'0', '2017-05-17 20:16:43', '2017-05-17 10:14:19', '', '', b'0', NULL, 'TIEP_NHAN_DON', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 10:06:27', ' ', ' ', 1, NULL, '', '', 1, 1, 1, 20, NULL, 2, NULL),
	(38, b'0', '2017-05-17 10:40:57', '2017-05-17 10:40:57', '', '', b'0', NULL, 'TRA_DON_VA_HUONG_DAN', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 10:37:16', ' ', 'Đến cơ quan đúng thẩm quyền để trình đơn.\nSở Tài Nguyên Môi Trường', 1, NULL, '', '', 1, 1, 1, 21, NULL, 2, NULL),
	(39, b'0', '2017-05-17 11:13:27', '2017-05-17 11:13:00', '', '', b'0', NULL, 'YEU_CAU_GAP_LANH_DAO', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 11:06:40', ' ', ' ', 1, NULL, '', '', 1, 1, 1, 22, NULL, 2, NULL),
	(40, b'0', '2017-05-17 11:53:56', '2017-05-17 11:23:07', '', '', b'0', NULL, 'YEU_CAU_GAP_LANH_DAO', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 11:14:02', ' ', ' ', 1, NULL, '', '', 1, 1, 1, 23, NULL, 2, NULL),
	(41, b'0', '2017-05-17 11:36:30', '2017-05-17 11:36:30', '', '', b'0', NULL, 'YEU_CAU_GAP_LANH_DAO', ' ', 'THUONG_XUYEN', NULL, '2017-05-17 11:23:16', ' ', ' ', 1, NULL, '', '', 1, 1, 1, 24, NULL, 2, NULL),
	(42, b'0', '2017-05-17 12:35:12', '2017-05-17 12:34:41', '', '', b'0', 'GIAI_QUYET_NGAY', 'KHOI_TAO', ' ', 'DINH_KY', NULL, '2017-05-17 05:33:58', ' ', ' ', 0, NULL, '', '', 1, 1, 11, 22, NULL, 2, NULL),
	(43, b'0', '2017-05-17 12:36:17', '2017-05-17 12:34:41', '', '', b'0', 'CHO_GIAI_QUYET', 'KHOI_TAO', ' ', 'DINH_KY', NULL, '2017-05-17 05:33:58', ' ', ' ', 0, NULL, '', '', 1, 1, 11, 23, 21, 2, NULL),
	(44, b'0', '2017-05-17 12:40:20', '2017-05-17 12:40:20', '', '', b'0', 'GIAI_QUYET_NGAY', 'KHOI_TAO', '', 'DOT_XUAT', NULL, '2017-05-17 12:37:12', ' ', ' ', 0, NULL, '', '', 1, 1, 9, 25, NULL, 2, NULL),
	(45, b'0', '2017-05-17 12:48:49', '2017-05-17 12:48:49', '', '', b'0', 'GIAI_QUYET_NGAY', 'KHOI_TAO', '', 'DOT_XUAT', NULL, '2017-05-17 12:40:53', ' ', ' ', 0, NULL, '', '', 1, 1, 9, 26, NULL, 2, NULL),
	(46, b'0', '2017-05-17 13:01:05', '2017-05-17 13:01:05', '', '', b'0', 'GIAI_QUYET_NGAY', 'KHOI_TAO', '', 'DOT_XUAT', NULL, '2017-05-17 12:57:33', ' ', ' ', 0, NULL, '', '', 1, 1, 10, 27, NULL, 2, NULL),
	(47, b'0', '2017-05-17 14:03:24', '2017-05-17 14:03:24', '', '', b'0', 'CHO_GIAI_QUYET', 'KHOI_TAO', '', 'DOT_XUAT', NULL, '2017-05-17 13:59:59', ' ', ' ', 0, NULL, '', '', 1, 1, 10, 28, 20, 2, NULL);
/*!40000 ALTER TABLE `sotiepcongdan` ENABLE KEYS */;

-- Dumping structure for table tttp.sotiepcongdan_has_donviphoihop
DROP TABLE IF EXISTS `sotiepcongdan_has_donviphoihop`;
CREATE TABLE IF NOT EXISTS `sotiepcongdan_has_donviphoihop` (
  `soTiepCongDan_id` bigint(20) NOT NULL,
  `coQuanQuanLy_id` bigint(20) NOT NULL,
  KEY `FKgdg9nrv2ekd97wwgpeernsdcu` (`coQuanQuanLy_id`),
  KEY `FK3q6ie589rhosaj8by8f0ca7wn` (`soTiepCongDan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.sotiepcongdan_has_donviphoihop: 2 rows
/*!40000 ALTER TABLE `sotiepcongdan_has_donviphoihop` DISABLE KEYS */;
INSERT INTO `sotiepcongdan_has_donviphoihop` (`soTiepCongDan_id`, `coQuanQuanLy_id`) VALUES
	(43, 20),
	(47, 23);
/*!40000 ALTER TABLE `sotiepcongdan_has_donviphoihop` ENABLE KEYS */;

-- Dumping structure for table tttp.tailieubangchung
DROP TABLE IF EXISTS `tailieubangchung`;
CREATE TABLE IF NOT EXISTS `tailieubangchung` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `duongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soTrang` int(11) NOT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `tenFile` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `tinhTrangTaiLieu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `don_id` bigint(20) NOT NULL,
  `loaiTaiLieu_id` bigint(20) NOT NULL,
  `soTiepCongDan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn55t7kgdw0fo299vsa2y7mwwn` (`nguoiSua_id`),
  KEY `FK6v9f6okh002ajyv65ckxhlnh2` (`nguoiTao_id`),
  KEY `FKm7lvmkr4t8q5e3i2ybcxhakxr` (`don_id`),
  KEY `FKtqbmjxy42x1t9ks4dp67h74m5` (`loaiTaiLieu_id`),
  KEY `FKr434cjl5l7ievuaxfltn7thm8` (`soTiepCongDan_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.tailieubangchung: 0 rows
/*!40000 ALTER TABLE `tailieubangchung` DISABLE KEYS */;
/*!40000 ALTER TABLE `tailieubangchung` ENABLE KEYS */;

-- Dumping structure for table tttp.tailieudinhkemdinhchi
DROP TABLE IF EXISTS `tailieudinhkemdinhchi`;
CREATE TABLE IF NOT EXISTS `tailieudinhkemdinhchi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `duongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `tenFile` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `don_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbxas19mar2dk163dujssve1te` (`nguoiSua_id`),
  KEY `FKa4352w1u7hn8veupofrhlv0bp` (`nguoiTao_id`),
  KEY `FKebmwuaxcis8i4uhro9o6ehasa` (`don_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.tailieudinhkemdinhchi: 0 rows
/*!40000 ALTER TABLE `tailieudinhkemdinhchi` DISABLE KEYS */;
/*!40000 ALTER TABLE `tailieudinhkemdinhchi` ENABLE KEYS */;

-- Dumping structure for table tttp.tailieuvanthu
DROP TABLE IF EXISTS `tailieuvanthu`;
CREATE TABLE IF NOT EXISTS `tailieuvanthu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `duongDan` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `loaiTepDinhKem` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ngayQuyetDinh` datetime DEFAULT NULL,
  `soQuyetDinh` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `tenFile` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `don_id` bigint(20) NOT NULL,
  `soTiepCongDan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbeblfdalvwob08ct7d667ia0v` (`nguoiSua_id`),
  KEY `FKvbwlu7jvghf673lonj7i5as7` (`nguoiTao_id`),
  KEY `FK9avt6pu1oim13y02mqxc8icm5` (`don_id`),
  KEY `FKknbyx9548nh4feci6uov61ecv` (`soTiepCongDan_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.tailieuvanthu: 0 rows
/*!40000 ALTER TABLE `tailieuvanthu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tailieuvanthu` ENABLE KEYS */;

-- Dumping structure for table tttp.thamquyengiaiquyet
DROP TABLE IF EXISTS `thamquyengiaiquyet`;
CREATE TABLE IF NOT EXISTS `thamquyengiaiquyet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkr8yg0qgmaabbgp2ecdqnnxvt` (`nguoiSua_id`),
  KEY `FK87j0pf567gb3x08ob6bl70m0k` (`nguoiTao_id`),
  KEY `FK11pilsd6jrm09e11f8y1db26x` (`cha_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.thamquyengiaiquyet: 9 rows
/*!40000 ALTER TABLE `thamquyengiaiquyet` DISABLE KEYS */;
INSERT INTO `thamquyengiaiquyet` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `cha_id`) VALUES
	(1, b'0', '2017-04-27 09:36:37', '2017-04-27 09:36:37', 'Thẩm quyền giải quyết 001', 'Thẩm quyền giải quyết 001', 1, 1, NULL),
	(2, b'0', '2017-04-27 09:36:43', '2017-04-27 09:36:43', 'Thẩm quyền giải quyết 002', 'Thẩm quyền giải quyết 002', 1, 1, NULL),
	(3, b'0', '2017-05-03 14:25:00', '2017-05-03 14:25:00', 'Thẩm quyền giải quyết 003', 'Thẩm quyền giải quyết 003', 1, 1, NULL),
	(4, b'0', '2017-05-03 14:25:52', '2017-05-03 14:25:52', 'Thẩm quyền giải quyết 004', 'Thẩm quyền giải quyết 004', 1, 1, NULL),
	(5, b'0', '2017-05-03 15:45:04', '2017-05-03 14:29:18', 'Thẩm quyền giải quyết 005', 'Thẩm quyền giải quyết 005', 1, 1, NULL),
	(6, b'0', '2017-05-03 15:37:56', '2017-05-03 14:31:54', 'Thẩm quyền giải quyết 006', 'Thẩm quyền giải quyết 006', 1, 1, NULL),
	(7, b'0', '2017-05-03 15:33:28', '2017-05-03 15:05:07', 'Thẩm quyền giải quyết 007', 'Thẩm quyền giải quyết 007', 1, 1, NULL),
	(8, b'0', '2017-05-03 15:32:08', '2017-05-03 15:07:22', 'Thẩm quyền giải quyết 008', 'Thẩm quyền giải quyết 008', 1, 1, NULL),
	(9, b'0', '2017-05-03 15:32:00', '2017-05-03 15:08:26', 'Thẩm quyền giải quyết 009', 'Thẩm quyền giải quyết 009', 1, 1, NULL);
/*!40000 ALTER TABLE `thamquyengiaiquyet` ENABLE KEYS */;

-- Dumping structure for table tttp.thamso
DROP TABLE IF EXISTS `thamso`;
CREATE TABLE IF NOT EXISTS `thamso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `giaTri` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh7tmt45f1ik590qqcwg0v4w1s` (`nguoiSua_id`),
  KEY `FKlo4ke6t4f9ofyii0gr2g1sl87` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.thamso: 13 rows
/*!40000 ALTER TABLE `thamso` DISABLE KEYS */;
INSERT INTO `thamso` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `giaTri`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-04-23 11:09:41', '2017-04-23 11:09:42', '1', 'CDVHC_TINH', 'CDVHC_TINH', 1, 1),
	(2, b'0', '2017-05-03 15:27:16', '2017-04-23 11:09:42', '4', 'CDVHC_THANH_PHO_TRUC_THUOC_TW', 'CDVHC_THANH_PHO_TRUC_THUOC_TW', 1, 1),
	(3, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '9', 'CDVHC_THANH_PHO-TRUC_THUOC_TINH', 'CDVHC_THANH_PHO-TRUC_THUOC_TINH', 1, 1),
	(4, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '5', 'CDVHC_QUAN', 'CDVHC_QUAN', 1, 1),
	(5, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '2', 'CDVHC_HUYEN', 'CDVHC_HUYEN', 1, 1),
	(6, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '6', 'CDVHC_PHUONG', 'CDVHC_PHUONG', 1, 1),
	(7, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '3', 'CDVHC_XA', 'CDVHC_XA', 1, 1),
	(8, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '7', 'CDVHC_THI_TRAN', 'CDVHC_THI_TRAN', 1, 1),
	(9, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '8', 'CDVHC_THI_XA', 'CDVHC_THI_XA', 1, 1),
	(10, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '5', 'CCQQL_SO_BAN_NGANH', 'CCQQL_SO_BAN_NGANH', 1, 1),
	(11, b'0', '2017-05-03 15:29:44', '2017-04-23 11:09:42', '2', 'LCCQQL_BO_CONG_AN', 'LCCQQL_BO_CONG_AN', 1, 1),
	(12, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '6', 'CCQQL_PHONG_BAN', 'CCQQL_PHONG_BAN', 1, 1),
	(13, b'0', '2017-04-23 11:09:42', '2017-04-23 11:09:42', '1', 'CQQL_UBNDTP_DA_NANG', 'CQQL_UBNDTP_DA_NANG', 1, 1);
/*!40000 ALTER TABLE `thamso` ENABLE KEYS */;

-- Dumping structure for table tttp.thoihan
DROP TABLE IF EXISTS `thoihan`;
CREATE TABLE IF NOT EXISTS `thoihan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `loaiThoiHanEnum` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `soNgay` int(11) NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqhlfoj57okaw62pljwr3jie4y` (`nguoiSua_id`),
  KEY `FKqin81uy1k2wju3v9epl83qa4v` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.thoihan: 11 rows
/*!40000 ALTER TABLE `thoihan` DISABLE KEYS */;
INSERT INTO `thoihan` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `loaiThoiHanEnum`, `soNgay`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-04-28 15:34:43', '2017-04-28 15:34:43', 'THOIHAN_XULYDON', 5, 1, 1),
	(2, b'0', '2017-04-28 15:34:51', '2017-04-28 15:34:51', 'THOIHAN_XULYDON', 10, 1, 1),
	(3, b'0', '2017-04-28 15:34:56', '2017-04-28 15:34:56', 'THOIHAN_XULYDON', 15, 1, 1),
	(4, b'0', '2017-04-28 15:37:04', '2017-04-28 15:37:04', 'THOIHAN_GIAIQUYETKHIEUNAI', 30, 1, 1),
	(5, b'0', '2017-04-28 15:37:07', '2017-04-28 15:37:07', 'THOIHAN_GIAIQUYETKHIEUNAI', 45, 1, 1),
	(6, b'0', '2017-04-28 15:37:10', '2017-04-28 15:37:10', 'THOIHAN_GIAIQUYETKHIEUNAI', 60, 1, 1),
	(7, b'0', '2017-04-28 15:37:18', '2017-04-28 15:37:18', 'THOIHAN_GIAIQUYETTOCAO', 30, 1, 1),
	(8, b'0', '2017-04-28 15:37:20', '2017-04-28 15:37:20', 'THOIHAN_GIAIQUYETTOCAO', 45, 1, 1),
	(9, b'0', '2017-04-28 15:37:25', '2017-04-28 15:37:25', 'THOIHAN_GIAIQUYETTOCAO', 60, 1, 1),
	(10, b'0', '2017-04-28 15:38:00', '2017-04-28 15:38:00', 'THOIHAN_KIENNGHIPHANANH', 60, 1, 1),
	(11, b'0', '2017-04-28 15:38:04', '2017-04-28 15:38:04', 'THOIHAN_KIENNGHIPHANANH', 90, 1, 1);
/*!40000 ALTER TABLE `thoihan` ENABLE KEYS */;

-- Dumping structure for table tttp.todanpho
DROP TABLE IF EXISTS `todanpho`;
CREATE TABLE IF NOT EXISTS `todanpho` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `moTa` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `donViHanhChinh_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeo4oo81c3t3f9p9vli7wj92c4` (`nguoiSua_id`),
  KEY `FKq98lg4lom4m036wihayx73rva` (`nguoiTao_id`),
  KEY `FKlxe3bui2j0p33kjqfk33aoodg` (`donViHanhChinh_id`)
) ENGINE=MyISAM AUTO_INCREMENT=526 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.todanpho: 500 rows
/*!40000 ALTER TABLE `todanpho` DISABLE KEYS */;
INSERT INTO `todanpho` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `moTa`, `ten`, `nguoiSua_id`, `nguoiTao_id`, `donViHanhChinh_id`) VALUES
	(1, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 23),
	(2, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 23),
	(3, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 25),
	(4, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 32),
	(5, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 23),
	(6, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 23),
	(7, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 23),
	(8, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 23),
	(9, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 23),
	(10, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 23),
	(11, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 23),
	(12, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 23),
	(13, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 22),
	(14, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 22),
	(15, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 22),
	(49, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 25),
	(48, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 25),
	(57, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 26),
	(19, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 22),
	(20, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 22),
	(21, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 22),
	(22, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 22),
	(23, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 22),
	(24, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 22),
	(25, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 22),
	(50, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 25),
	(51, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 25),
	(52, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 25),
	(53, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 25),
	(54, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 25),
	(55, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 25),
	(36, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 24),
	(37, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 24),
	(38, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 24),
	(39, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 24),
	(40, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 24),
	(41, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 24),
	(42, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 24),
	(43, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 24),
	(44, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 24),
	(45, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 24),
	(56, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 25),
	(58, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 26),
	(59, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 26),
	(60, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 26),
	(61, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 26),
	(62, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 26),
	(63, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 26),
	(64, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 26),
	(65, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 26),
	(66, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 26),
	(67, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 27),
	(68, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 27),
	(69, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 27),
	(70, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 27),
	(71, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 27),
	(72, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 27),
	(73, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 27),
	(74, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 27),
	(75, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 27),
	(76, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 27),
	(77, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 28),
	(78, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 28),
	(79, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 28),
	(80, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 28),
	(81, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 28),
	(82, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 28),
	(83, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 28),
	(84, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 28),
	(85, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 28),
	(86, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 28),
	(87, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 29),
	(88, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 29),
	(89, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 29),
	(90, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 29),
	(91, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 29),
	(92, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 29),
	(93, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 29),
	(94, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 29),
	(95, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 29),
	(96, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 29),
	(97, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 30),
	(98, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 30),
	(99, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 30),
	(100, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 30),
	(101, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 30),
	(102, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 30),
	(103, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 30),
	(104, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 30),
	(105, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 30),
	(106, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 30),
	(107, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 31),
	(108, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 31),
	(109, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 31),
	(110, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 31),
	(111, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 31),
	(112, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 31),
	(113, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 31),
	(114, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 31),
	(115, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 31),
	(116, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 31),
	(117, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 32),
	(118, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 32),
	(119, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 32),
	(120, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 32),
	(121, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 32),
	(122, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 32),
	(123, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 32),
	(124, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 32),
	(125, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 32),
	(126, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 33),
	(127, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 33),
	(128, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 33),
	(129, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 33),
	(130, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 33),
	(131, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 33),
	(132, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 33),
	(133, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 33),
	(134, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 33),
	(135, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 33),
	(136, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 34),
	(137, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 34),
	(138, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 34),
	(139, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 34),
	(140, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 34),
	(141, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 34),
	(142, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 34),
	(143, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 34),
	(144, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 34),
	(145, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 34),
	(146, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 35),
	(147, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 35),
	(148, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 35),
	(149, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 35),
	(150, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 35),
	(151, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 35),
	(152, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 35),
	(153, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 35),
	(154, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 35),
	(155, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 35),
	(156, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 36),
	(157, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 36),
	(158, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 36),
	(159, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 36),
	(160, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 36),
	(161, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 36),
	(162, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 36),
	(163, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 36),
	(164, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 36),
	(165, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 36),
	(166, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 37),
	(167, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 37),
	(168, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 37),
	(169, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 37),
	(170, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 37),
	(171, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 37),
	(172, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 37),
	(173, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 37),
	(174, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 37),
	(175, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 37),
	(176, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 38),
	(177, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 38),
	(178, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 38),
	(179, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 38),
	(180, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 38),
	(181, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 38),
	(182, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 38),
	(183, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 38),
	(184, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 38),
	(185, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 38),
	(186, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 39),
	(187, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 39),
	(188, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 39),
	(189, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 39),
	(190, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 39),
	(191, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 39),
	(192, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 39),
	(193, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 39),
	(194, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 39),
	(195, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 39),
	(196, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 40),
	(197, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 40),
	(198, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 40),
	(199, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 40),
	(200, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 40),
	(201, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 40),
	(202, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 40),
	(203, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 40),
	(204, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 40),
	(205, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 40),
	(206, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 41),
	(207, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 41),
	(208, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 41),
	(209, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 41),
	(210, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 41),
	(211, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 41),
	(212, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 41),
	(213, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 41),
	(214, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 41),
	(215, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 41),
	(216, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 42),
	(217, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 42),
	(218, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 42),
	(219, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 42),
	(220, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 42),
	(221, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 42),
	(222, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 42),
	(223, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 42),
	(224, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 42),
	(225, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 42),
	(226, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 43),
	(227, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 43),
	(228, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 43),
	(229, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 43),
	(230, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 43),
	(231, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 43),
	(232, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 43),
	(233, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 43),
	(234, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 43),
	(235, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 43),
	(236, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 44),
	(237, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 44),
	(238, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 44),
	(239, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 44),
	(240, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 44),
	(241, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 44),
	(242, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 44),
	(243, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 44),
	(244, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 44),
	(245, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 44),
	(246, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 45),
	(247, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 45),
	(248, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 45),
	(249, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 45),
	(250, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 45),
	(251, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 45),
	(252, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 45),
	(253, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 45),
	(254, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 45),
	(255, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 45),
	(256, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 46),
	(257, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 46),
	(258, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 46),
	(259, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 46),
	(260, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 46),
	(261, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 46),
	(262, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 46),
	(263, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 46),
	(264, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 46),
	(265, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 46),
	(266, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 47),
	(267, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 47),
	(268, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 47),
	(269, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 47),
	(270, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 47),
	(271, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 47),
	(272, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 47),
	(273, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 47),
	(274, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 47),
	(275, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 47),
	(276, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 48),
	(277, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 48),
	(278, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 48),
	(279, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 48),
	(280, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 48),
	(281, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 48),
	(282, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 48),
	(283, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 48),
	(284, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 48),
	(285, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 48),
	(286, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 49),
	(287, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 49),
	(288, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 49),
	(289, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 49),
	(290, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 49),
	(291, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 49),
	(292, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 49),
	(293, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 49),
	(294, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 49),
	(295, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 49),
	(296, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 50),
	(297, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 50),
	(298, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 50),
	(299, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 50),
	(300, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 50),
	(301, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 50),
	(302, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 50),
	(303, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 50),
	(304, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 50),
	(305, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 50),
	(306, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 51),
	(307, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 51),
	(308, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 51),
	(309, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 51),
	(310, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 51),
	(311, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 51),
	(312, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 51),
	(313, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 51),
	(314, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 51),
	(315, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 51),
	(316, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 52),
	(317, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 52),
	(318, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 52),
	(319, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 52),
	(320, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 52),
	(321, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 52),
	(322, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 52),
	(323, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 52),
	(324, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 52),
	(325, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 52),
	(326, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 53),
	(327, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 53),
	(328, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 53),
	(329, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 53),
	(330, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 53),
	(331, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 53),
	(332, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 53),
	(333, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 53),
	(334, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 53),
	(335, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 53),
	(336, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 54),
	(337, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 54),
	(338, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 54),
	(339, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 54),
	(340, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 54),
	(341, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 54),
	(342, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 54),
	(343, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 54),
	(344, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 54),
	(345, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 54),
	(346, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 55),
	(347, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 55),
	(348, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 55),
	(349, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 55),
	(350, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 55),
	(351, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 55),
	(352, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 55),
	(353, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 55),
	(354, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 55),
	(355, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 55),
	(356, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 56),
	(357, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 56),
	(358, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 56),
	(359, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 56),
	(360, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 56),
	(361, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 56),
	(362, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 56),
	(363, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 56),
	(364, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 56),
	(365, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 56),
	(366, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 57),
	(367, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 57),
	(368, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 57),
	(369, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 57),
	(370, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 57),
	(371, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 57),
	(372, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 57),
	(373, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 57),
	(374, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 57),
	(375, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 57),
	(376, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 58),
	(377, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 58),
	(378, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 58),
	(379, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 58),
	(380, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 58),
	(381, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 58),
	(382, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 58),
	(383, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 58),
	(384, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 58),
	(385, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 58),
	(386, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 59),
	(387, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 59),
	(388, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 59),
	(389, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 59),
	(390, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 59),
	(391, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 59),
	(392, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 59),
	(393, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 59),
	(394, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 59),
	(395, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 59),
	(396, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 60),
	(397, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 60),
	(398, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 60),
	(399, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 60),
	(400, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 60),
	(401, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 60),
	(402, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 60),
	(403, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 60),
	(404, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 60),
	(405, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 60),
	(406, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 61),
	(407, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 61),
	(408, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 61),
	(409, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 61),
	(410, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 61),
	(411, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 61),
	(412, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 61),
	(413, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 61),
	(414, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 61),
	(415, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 61),
	(416, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 62),
	(417, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 62),
	(418, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 62),
	(419, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 62),
	(420, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 62),
	(421, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 62),
	(422, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 62),
	(423, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 62),
	(424, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 62),
	(425, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 62),
	(426, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 63),
	(427, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 63),
	(428, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 63),
	(429, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 63),
	(430, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 63),
	(431, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 63),
	(432, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 63),
	(433, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 63),
	(434, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 63),
	(435, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 63),
	(436, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 64),
	(437, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 64),
	(438, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 64),
	(439, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 64),
	(440, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 64),
	(441, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 64),
	(442, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 64),
	(443, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 64),
	(444, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 64),
	(445, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 64),
	(446, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 65),
	(447, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 65),
	(448, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 65),
	(449, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 65),
	(450, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 65),
	(451, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 65),
	(452, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 65),
	(453, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 65),
	(454, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 65),
	(455, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 65),
	(456, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 66),
	(457, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 66),
	(458, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 66),
	(459, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 66),
	(460, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 66),
	(461, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 66),
	(462, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 66),
	(463, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 66),
	(464, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 66),
	(465, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 66),
	(466, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 67),
	(467, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 67),
	(468, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 67),
	(469, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 67),
	(470, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 67),
	(471, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 67),
	(472, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 67),
	(473, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 67),
	(474, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 67),
	(475, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 67),
	(476, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 68),
	(477, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 68),
	(478, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 68),
	(479, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 68),
	(480, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 68),
	(481, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 68),
	(482, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 68),
	(483, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 68),
	(484, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 68),
	(485, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 68),
	(486, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 69),
	(487, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 69),
	(488, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 69),
	(489, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 69),
	(490, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 69),
	(491, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 69),
	(492, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 69),
	(493, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 69),
	(494, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 69),
	(495, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 69),
	(496, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 70),
	(497, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 70),
	(498, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 70),
	(499, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 70),
	(500, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 70),
	(501, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 70),
	(502, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 70),
	(503, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 70),
	(504, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 70),
	(505, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 70),
	(506, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 01', 'Tổ 01', 1, 1, 71),
	(507, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 02', 'Tổ 02', 1, 1, 71),
	(508, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 03', 'Tổ 03', 1, 1, 71),
	(509, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 04', 'Tổ 04', 1, 1, 71),
	(510, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 05', 'Tổ 05', 1, 1, 71),
	(511, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 06', 'Tổ 06', 1, 1, 71),
	(512, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 07', 'Tổ 07', 1, 1, 71),
	(513, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 08', 'Tổ 08', 1, 1, 71),
	(514, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 09', 'Tổ 09', 1, 1, 71),
	(515, b'0', '2017-04-25 09:16:05', '2017-04-25 09:16:05', 'Tổ 10', 'Tổ 10', 1, 1, 71);
/*!40000 ALTER TABLE `todanpho` ENABLE KEYS */;

-- Dumping structure for table tttp.vaitro
DROP TABLE IF EXISTS `vaitro`;
CREATE TABLE IF NOT EXISTS `vaitro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `loaiVaiTro` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3uwscuj1f6gvxme8ifyuwfoga` (`nguoiSua_id`),
  KEY `FKba7mse8kaa7rhx6623lpvkau4` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.vaitro: 4 rows
/*!40000 ALTER TABLE `vaitro` DISABLE KEYS */;
INSERT INTO `vaitro` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `loaiVaiTro`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-05-11 11:00:16', '2017-04-25 15:36:45', 'LANH_DAO', 'Lãnh đạo', 1, 1),
	(2, b'0', '2017-05-17 18:35:29', '2017-05-10 14:48:29', 'TRUONG_PHONG', 'Trưởng Phòng', 1, 1),
	(3, b'0', '2017-05-17 18:36:55', '2017-05-10 14:48:46', 'CHUYEN_VIEN', 'Chuyên viên', 1, 1),
	(4, b'0', '2017-05-17 18:49:16', '2017-05-10 14:48:55', 'VAN_THU', 'Văn thư', 1, 1);
/*!40000 ALTER TABLE `vaitro` ENABLE KEYS */;

-- Dumping structure for table tttp.vaitro_quyen
DROP TABLE IF EXISTS `vaitro_quyen`;
CREATE TABLE IF NOT EXISTS `vaitro_quyen` (
  `vaitro_id` bigint(20) NOT NULL,
  `quyens` longtext COLLATE utf8_vietnamese_ci,
  KEY `FKsx7h61ya2vb2sjj0rp32b3p38` (`vaitro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.vaitro_quyen: 4 rows
/*!40000 ALTER TABLE `vaitro_quyen` DISABLE KEYS */;
INSERT INTO `vaitro_quyen` (`vaitro_id`, `quyens`) VALUES
	(1, 'thamquyengiaiquyet:xem,thamquyengiaiquyet:them,thamquyengiaiquyet:sua,thamquyengiaiquyet:xoa,vuviec:lietke,vuviec:xem,vuviec:them,vuviec:sua,vuviec:xoa,loaitailieu:lietke,loaitailieu:xem,loaitailieu:them,loaitailieu:sua,loaitailieu:xoa,chucvu:lietke,chucvu:xem,chucvu:them,chucvu:sua,chucvu:xoa,quoctich:lietke,quoctich:xem,quoctich:them,quoctich:sua,quoctich:xoa,capdonvihanhchinh:lietke,capdonvihanhchinh:xem,capdonvihanhchinh:them,capdonvihanhchinh:sua,capdonvihanhchinh:xoa,donvihanhchinh:lietke,donvihanhchinh:xem,donvihanhchinh:them,donvihanhchinh:sua,donvihanhchinh:xoa,capcoquanquanly:lietke,capcoquanquanly:xem,capcoquanquanly:them,capcoquanquanly:sua,capcoquanquanly:xoa,coquanquanly:lietke,coquanquanly:xem,coquanquanly:them,coquanquanly:sua,coquanquanly:xoa,todanpho:lietke,todanpho:them,todanpho:xem,todanpho:sua,todanpho:xoa,dantoc:lietke,dantoc:them,dantoc:xem,dantoc:sua,dantoc:xoa,linhvucdonthu:lietke,linhvucdonthu:xem,linhvucdonthu:them,linhvucdonthu:sua,linhvucdonthu:xoa,congchuc:lietke,congchuc:xem,congchuc:them,congchuc:sua,congchuc:xoa,congdan:lietke,congdan:xem,congdan:them,congdan:sua,congdan:xoa,vaitro:lietke,vaitro:xem,vaitro:them,vaitro:sua,vaitro:xoa,don:lietke,don:xem,don:them,don:sua,don:xoa,sotiepcongdan:lietke,sotiepcongdan:xem,sotiepcongdan:them,sotiepcongdan:sua,sotiepcongdan:xoa,thamquyengiaiquyet:lietke,loaicoquanquanly:lietke,loaicoquanquanly:xem,loaicoquanquanly:them,loaicoquanquanly:sua,loaicoquanquanly:xoa,thoihan:lietke,thoihan:xem,thoihan:them,thoihan:sua,thoihan:xoa,thamso:sua,thamso:them,thamso:xem,thamso:lietke,thamso:xoa'),
	(2, 'thamquyengiaiquyet:lietke,thamquyengiaiquyet:xem,thamquyengiaiquyet:them,thamquyengiaiquyet:sua,thamquyengiaiquyet:xoa,vuviec:lietke,vuviec:xem,vuviec:them,vuviec:sua,vuviec:xoa,loaitailieu:lietke,loaitailieu:xem,loaitailieu:them,loaitailieu:sua,loaitailieu:xoa,chucvu:lietke,chucvu:xem,chucvu:them,chucvu:sua,chucvu:xoa,quoctich:lietke,quoctich:xem,quoctich:them,quoctich:sua,quoctich:xoa,capdonvihanhchinh:lietke,capdonvihanhchinh:xem,capdonvihanhchinh:them,capdonvihanhchinh:sua,capdonvihanhchinh:xoa,donvihanhchinh:lietke,donvihanhchinh:xem,donvihanhchinh:them,donvihanhchinh:sua,donvihanhchinh:xoa,loaicoquanquanly:lietke,loaicoquanquanly:xem,loaicoquanquanly:them,loaicoquanquanly:sua,loaicoquanquanly:xoa,capcoquanquanly:lietke,capcoquanquanly:xem,capcoquanquanly:them,capcoquanquanly:sua,capcoquanquanly:xoa,coquanquanly:lietke,coquanquanly:xem,coquanquanly:them,coquanquanly:sua,coquanquanly:xoa,todanpho:lietke,todanpho:xem,todanpho:them,todanpho:sua,todanpho:xoa,dantoc:lietke,dantoc:xem,dantoc:them,dantoc:sua,dantoc:xoa,linhvucdonthu:lietke,linhvucdonthu:xem,linhvucdonthu:sua,linhvucdonthu:xoa,linhvucdonthu:them,congchuc:lietke,congchuc:xem,congchuc:them,congchuc:sua,congchuc:xoa,congdan:lietke,congdan:xem,congdan:them,congdan:sua,congdan:xoa,vaitro:lietke,vaitro:xem,vaitro:sua,vaitro:xoa,vaitro:them,thoihan:xem,thoihan:lietke,thoihan:them,thoihan:sua,thoihan:xoa,thamso:lietke,thamso:xem,thamso:them,thamso:sua,thamso:xoa,sotiepcongdan:lietke,sotiepcongdan:xem,sotiepcongdan:them,sotiepcongdan:sua,sotiepcongdan:xoa,don:lietke,don:xem,don:them,don:sua,don:xoa'),
	(3, 'thamquyengiaiquyet:lietke,thamquyengiaiquyet:xem,thamquyengiaiquyet:them,thamquyengiaiquyet:sua,thamquyengiaiquyet:xoa,vuviec:xem,vuviec:lietke,vuviec:them,vuviec:sua,vuviec:xoa,loaitailieu:lietke,loaitailieu:xem,loaitailieu:them,loaitailieu:sua,loaitailieu:xoa,chucvu:lietke,chucvu:xem,chucvu:them,chucvu:sua,chucvu:xoa,quoctich:lietke,quoctich:xem,quoctich:them,quoctich:sua,quoctich:xoa,capdonvihanhchinh:lietke,capdonvihanhchinh:xem,capdonvihanhchinh:them,capdonvihanhchinh:sua,capdonvihanhchinh:xoa,donvihanhchinh:lietke,donvihanhchinh:xem,donvihanhchinh:them,donvihanhchinh:sua,donvihanhchinh:xoa,loaicoquanquanly:lietke,loaicoquanquanly:xem,loaicoquanquanly:them,loaicoquanquanly:sua,loaicoquanquanly:xoa,capcoquanquanly:lietke,capcoquanquanly:xem,capcoquanquanly:them,capcoquanquanly:sua,capcoquanquanly:xoa,coquanquanly:lietke,coquanquanly:xem,coquanquanly:them,coquanquanly:sua,coquanquanly:xoa,todanpho:lietke,todanpho:xem,todanpho:them,todanpho:sua,todanpho:xoa,dantoc:lietke,dantoc:xem,dantoc:them,dantoc:sua,dantoc:xoa,linhvucdonthu:lietke,linhvucdonthu:xem,linhvucdonthu:them,linhvucdonthu:sua,linhvucdonthu:xoa,congchuc:lietke,congchuc:xem,congchuc:them,congchuc:sua,congchuc:xoa,congdan:lietke,congdan:xem,congdan:them,congdan:sua,congdan:xoa,vaitro:lietke,vaitro:xem,vaitro:them,vaitro:sua,vaitro:xoa,thoihan:lietke,thoihan:xem,thoihan:them,thoihan:sua,thoihan:xoa,thamso:lietke,thamso:xem,thamso:them,thamso:sua,thamso:xoa,sotiepcongdan:lietke,sotiepcongdan:xem,sotiepcongdan:them,sotiepcongdan:sua,sotiepcongdan:xoa,don:lietke,don:xem,don:them,don:xoa,don:sua'),
	(4, 'thamquyengiaiquyet:lietke,thamquyengiaiquyet:xem,thamquyengiaiquyet:them,thamquyengiaiquyet:sua,thamquyengiaiquyet:xoa,vuviec:lietke,vuviec:xem,vuviec:them,vuviec:sua,vuviec:xoa,loaitailieu:lietke,loaitailieu:xem,loaitailieu:them,loaitailieu:sua,loaitailieu:xoa,chucvu:lietke,chucvu:xem,chucvu:them,chucvu:sua,chucvu:xoa,quoctich:xem,quoctich:lietke,quoctich:them,quoctich:sua,quoctich:xoa,capdonvihanhchinh:lietke,capdonvihanhchinh:xem,capdonvihanhchinh:them,capdonvihanhchinh:sua,capdonvihanhchinh:xoa,donvihanhchinh:lietke,donvihanhchinh:xem,donvihanhchinh:them,donvihanhchinh:sua,donvihanhchinh:xoa,loaicoquanquanly:lietke,loaicoquanquanly:xem,loaicoquanquanly:them,loaicoquanquanly:sua,loaicoquanquanly:xoa,capcoquanquanly:lietke,capcoquanquanly:xem,capcoquanquanly:them,capcoquanquanly:sua,capcoquanquanly:xoa,coquanquanly:xem,coquanquanly:lietke,coquanquanly:them,coquanquanly:sua,coquanquanly:xoa,todanpho:lietke,todanpho:xem,todanpho:them,todanpho:sua,todanpho:xoa,dantoc:lietke,dantoc:xem,dantoc:them,dantoc:sua,dantoc:xoa,linhvucdonthu:lietke,linhvucdonthu:xem,linhvucdonthu:them,linhvucdonthu:sua,linhvucdonthu:xoa,congchuc:lietke,congchuc:xem,congchuc:them,congchuc:sua,congchuc:xoa,congdan:xem,congdan:lietke,congdan:them,congdan:sua,congdan:xoa,vaitro:lietke,vaitro:xem,vaitro:them,vaitro:sua,vaitro:xoa,thoihan:lietke,thoihan:xem,thoihan:them,thoihan:sua,thoihan:xoa,thamso:lietke,thamso:xem,thamso:them,thamso:sua,thamso:xoa,sotiepcongdan:lietke,sotiepcongdan:xem,sotiepcongdan:them,sotiepcongdan:sua,sotiepcongdan:xoa,don:lietke,don:xem,don:them,don:sua,don:xoa');
/*!40000 ALTER TABLE `vaitro_quyen` ENABLE KEYS */;

-- Dumping structure for table tttp.wf_donvi_has_state
DROP TABLE IF EXISTS `wf_donvi_has_state`;
CREATE TABLE IF NOT EXISTS `wf_donvi_has_state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `processType` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soThuTu` int(11) NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `coQuanQuanLy_id` bigint(20) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfct82l6mkxfs1gusl6wnar14` (`nguoiSua_id`),
  KEY `FKnkcvpj9phqj35q4xg3x3y40tu` (`nguoiTao_id`),
  KEY `FK3qqivwc1sk7rkvn58iuqq7f6p` (`coQuanQuanLy_id`),
  KEY `FKe0luwxjqa1syg49qwrq15nyh4` (`state_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.wf_donvi_has_state: 8 rows
/*!40000 ALTER TABLE `wf_donvi_has_state` DISABLE KEYS */;
INSERT INTO `wf_donvi_has_state` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `processType`, `soThuTu`, `nguoiSua_id`, `nguoiTao_id`, `coQuanQuanLy_id`, `state_id`) VALUES
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 1, 1, 1, 2, 1),
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 2, 1, 1, 2, 2),
	(3, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 3, 1, 1, 2, 3),
	(4, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 4, 1, 1, 2, 4),
	(5, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 5, 1, 1, 2, 5),
	(6, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 6, 1, 1, 2, 6),
	(7, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 7, 1, 1, 2, 7),
	(8, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'XU_LY_DON', 8, 1, 1, 2, 8);
/*!40000 ALTER TABLE `wf_donvi_has_state` ENABLE KEYS */;

-- Dumping structure for table tttp.wf_form
DROP TABLE IF EXISTS `wf_form`;
CREATE TABLE IF NOT EXISTS `wf_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `alias` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlm02efkge96lyl5if8n1p6crj` (`nguoiSua_id`),
  KEY `FK5yg708co9s0w6g7hge4f40vjm` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.wf_form: 7 rows
/*!40000 ALTER TABLE `wf_form` DISABLE KEYS */;
INSERT INTO `wf_form` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `alias`, `ten`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form1', 'Form 1', 1, 1),
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form2', 'Form 2', 1, 1),
	(3, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form3', 'Form 3', 1, 1),
	(4, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form4', 'Form 4', 1, 1),
	(5, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form5', 'Form 5', 1, 1),
	(6, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form6', 'Form 6', 1, 1),
	(7, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'form7', 'Form 7', 1, 1);
/*!40000 ALTER TABLE `wf_form` ENABLE KEYS */;

-- Dumping structure for table tttp.wf_process
DROP TABLE IF EXISTS `wf_process`;
CREATE TABLE IF NOT EXISTS `wf_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ghiChu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `owner` bit(1) NOT NULL,
  `tenQuyTrinh` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `cha_id` bigint(20) DEFAULT NULL,
  `coQuanQuanLy_id` bigint(20) DEFAULT NULL,
  `vaiTro_id` bigint(20) DEFAULT NULL,
  `processType` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKakdlqf2r15r03vitqn6pgfful` (`nguoiSua_id`),
  KEY `FKq544di50aornaqepvt1ajkrtw` (`nguoiTao_id`),
  KEY `FKfly0pot9pe5j719spr32mmryt` (`cha_id`),
  KEY `FK23i54r4o0gdumh61dodgqjbi` (`coQuanQuanLy_id`),
  KEY `FKjhucndrur3oxxu59brw9hxn25` (`vaiTro_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.wf_process: 5 rows
/*!40000 ALTER TABLE `wf_process` DISABLE KEYS */;
INSERT INTO `wf_process` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ghiChu`, `owner`, `tenQuyTrinh`, `nguoiSua_id`, `nguoiTao_id`, `cha_id`, `coQuanQuanLy_id`, `vaiTro_id`, `processType`) VALUES
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', NULL, b'0', 'Xu ly don cua Van Thu', 1, 1, NULL, 2, 4, 'XU_LY_DON'),
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', NULL, b'1', 'Xu ly don cua Van Thu', 1, 1, 1, 2, 4, 'XU_LY_DON'),
	(3, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', NULL, b'0', 'Xu ly don cua Lanh Dao', 1, 1, NULL, 2, 1, 'XU_LY_DON'),
	(4, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', NULL, b'0', 'Xu ly don cua Truong Phong', 1, 1, NULL, 2, 2, 'XU_LY_DON'),
	(5, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', NULL, b'0', 'Xu ly don cua Chuyen Vien', 1, 1, NULL, 2, 3, 'XU_LY_DON');
/*!40000 ALTER TABLE `wf_process` ENABLE KEYS */;

-- Dumping structure for table tttp.wf_state
DROP TABLE IF EXISTS `wf_state`;
CREATE TABLE IF NOT EXISTS `wf_state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `ghiChu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8_vietnamese_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55rccdtd35bei5r9gwghfdrx2` (`nguoiSua_id`),
  KEY `FKijg4n683sup3sxn3q281w3ddn` (`nguoiTao_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.wf_state: 9 rows
/*!40000 ALTER TABLE `wf_state` DISABLE KEYS */;
INSERT INTO `wf_state` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `ghiChu`, `ten`, `type`, `nguoiSua_id`, `nguoiTao_id`) VALUES
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Bắt đầu', 'Bắt đầu', 'BAT_DAU', 1, 1),
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Trình lãnh đạo', 'Trình lãnh đạo', 'TRINH_LANH_DAO', 1, 1),
	(3, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Giao việc trưởng phòng', 'Lãnh đạo giao việc trưởng phòng', 'LANH_DAO_GIAO_VIEC_TRUONG_PHONG', 1, 1),
	(4, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Đề xuất giao việc lại', 'Trường phòng đề xuất giao việc lại', 'TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI', 1, 1),
	(5, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Giao việc cán bộ', 'Trưởng phòng giao việc cán bộ', 'TRUONG_PHONG_GIAO_VIEC_CAN_BO', 1, 1),
	(6, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Đề xuất giao việc lại', 'Cán bộ đề xuất giao việc lại', 'CAN_BO_DE_XUAT_GIAO_VIEC_LAI', 1, 1),
	(7, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Đề xuất hướng xử lý', 'Cán bộ đề xuất hướng xử lý', 'CAN_BO_DE_XUAT_HUONG_XU_LY', 1, 1),
	(8, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Kết thúc', 'Kết thúc', 'KET_THUC', 1, 1),
	(9, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 'Giao việc cán bộ', 'Lãnh đạo giao việc cán bộ', 'LANH_DAO_GIAO_VIEC_CAN_BO', 1, 1);
/*!40000 ALTER TABLE `wf_state` ENABLE KEYS */;

-- Dumping structure for table tttp.wf_transition
DROP TABLE IF EXISTS `wf_transition`;
CREATE TABLE IF NOT EXISTS `wf_transition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `currentState_id` bigint(20) DEFAULT NULL,
  `form_id` bigint(20) DEFAULT NULL,
  `nextState_id` bigint(20) DEFAULT NULL,
  `process_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp1w8snyjgn1kslmn96iefhwwp` (`nguoiSua_id`),
  KEY `FKikhx6ex9a2hb0hysjqefwphub` (`nguoiTao_id`),
  KEY `FKkp7pja633l2ks30et92m11b9k` (`currentState_id`),
  KEY `FKgax7pfxrbm1droa3nwdtxvopf` (`form_id`),
  KEY `FKrfk6n3ds4jr0hv5ptoo1je3l3` (`nextState_id`),
  KEY `FKdcmimeash24b61vjf4b65t6ej` (`process_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.wf_transition: 14 rows
/*!40000 ALTER TABLE `wf_transition` DISABLE KEYS */;
INSERT INTO `wf_transition` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `nguoiSua_id`, `nguoiTao_id`, `currentState_id`, `form_id`, `nextState_id`, `process_id`) VALUES
	(1, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 1, 1, 2, 1),
	(2, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 1, 1, 2, 2),
	(3, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 2, 2, 3, 3),
	(4, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 3, 3, 4, 4),
	(5, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 3, 4, 5, 4),
	(6, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 4, 2, 3, 3),
	(7, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 5, 5, 6, 5),
	(8, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 5, 6, 7, 5),
	(9, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 6, 4, 5, 4),
	(10, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 7, 7, 8, 1),
	(11, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 7, 7, 8, 2),
	(12, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 6, 3, 4, 4),
	(13, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 9, 5, 6, 5),
	(14, b'0', '2017-05-03 13:20:39', '2017-05-03 13:20:39', 1, 1, 9, 6, 7, 5);
/*!40000 ALTER TABLE `wf_transition` ENABLE KEYS */;

-- Dumping structure for table tttp.xulydon
DROP TABLE IF EXISTS `xulydon`;
CREATE TABLE IF NOT EXISTS `xulydon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daXoa` bit(1) NOT NULL,
  `ngaySua` datetime DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL,
  `chucVu` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `chucVuGiaoViec` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `diaDiem` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ghiChu` longtext COLLATE utf8_vietnamese_ci,
  `huongXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `moTaTrangThai` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ngayHenGapLanhDao` datetime DEFAULT NULL,
  `ngayQuyetDinhDinhChi` datetime DEFAULT NULL,
  `noiDungThongTinTrinhLanhDao` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `noiDungYeuCauXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `quyTrinhXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `soQuyetDinhDinhChi` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `thoiHanXuLy` datetime DEFAULT NULL,
  `thuTuThucHien` int(11) NOT NULL,
  `trangThaiDon` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `yKienXuLy` varchar(255) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `nguoiSua_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `canBoXuLy_id` bigint(20) DEFAULT NULL,
  `canBoXuLyChiDinh_id` bigint(20) DEFAULT NULL,
  `coQuanChuyenDon_id` bigint(20) DEFAULT NULL,
  `coQuanTiepNhan_id` bigint(20) DEFAULT NULL,
  `congChuc_id` bigint(20) DEFAULT NULL,
  `don_id` bigint(20) DEFAULT NULL,
  `phongBanGiaiQuyet_id` bigint(20) DEFAULT NULL,
  `phongBanXuLy_id` bigint(20) DEFAULT NULL,
  `phongBanXuLyChiDinh_id` bigint(20) DEFAULT NULL,
  `thamQuyenGiaiQuyet_id` bigint(20) DEFAULT NULL,
  `donChuyen` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKimnqncmjaeqlwywm28g1uyv6y` (`nguoiSua_id`),
  KEY `FKdsdt96gw7ak1k3x54w6k7ihaw` (`nguoiTao_id`),
  KEY `FK9ipyliv44wr0tht0x6w0b5ker` (`canBoXuLy_id`),
  KEY `FK1wbfcaniqa9bj0ehcm0iip0hn` (`canBoXuLyChiDinh_id`),
  KEY `FKofebyud3a5iipeeq6kq4dpg7q` (`coQuanChuyenDon_id`),
  KEY `FKhdd3wbwlbtro4kiqx66gmvgj5` (`coQuanTiepNhan_id`),
  KEY `FKb6ncetnq1ib406vpj9kww15vw` (`congChuc_id`),
  KEY `FK2jqskulrukamhc8897sofcxja` (`don_id`),
  KEY `FK9rk7scncacsb59wjw6t6p2bn3` (`phongBanGiaiQuyet_id`),
  KEY `FKe0w8kx6irtoyqc88bcaqi0x61` (`phongBanXuLy_id`),
  KEY `FKl94mjqmxb6vchw7y3h1m3a6dr` (`phongBanXuLyChiDinh_id`),
  KEY `FK79w5mvipene2vpj5aj4rt478` (`thamQuyenGiaiQuyet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- Dumping data for table tttp.xulydon: 0 rows
/*!40000 ALTER TABLE `xulydon` DISABLE KEYS */;
INSERT INTO `xulydon` (`id`, `daXoa`, `ngaySua`, `ngayTao`, `chucVu`, `chucVuGiaoViec`, `diaDiem`, `ghiChu`, `huongXuLy`, `moTaTrangThai`, `ngayHenGapLanhDao`, `ngayQuyetDinhDinhChi`, `noiDungThongTinTrinhLanhDao`, `noiDungYeuCauXuLy`, `quyTrinhXuLy`, `soQuyetDinhDinhChi`, `thoiHanXuLy`, `thuTuThucHien`, `trangThaiDon`, `yKienXuLy`, `nguoiSua_id`, `nguoiTao_id`, `canBoXuLy_id`, `canBoXuLyChiDinh_id`, `coQuanChuyenDon_id`, `coQuanTiepNhan_id`, `congChuc_id`, `don_id`, `phongBanGiaiQuyet_id`, `phongBanXuLy_id`, `phongBanXuLyChiDinh_id`, `thamQuyenGiaiQuyet_id`, `donChuyen`) VALUES
	(10, b'0', '2017-05-17 20:16:42', '2017-05-17 20:16:42', 'VAN_THU', NULL, NULL, ' ', NULL, '', NULL, NULL, '', '', NULL, NULL, '2017-05-17 10:06:27', 0, 'DANG_XU_LY', '', 1, 1, NULL, NULL, NULL, NULL, NULL, 20, NULL, 2, NULL, NULL, b'0');
/*!40000 ALTER TABLE `xulydon` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;