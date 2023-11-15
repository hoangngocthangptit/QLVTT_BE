CREATE PROC sp_ThongKeNhapXuat
AS
BEGIN
SELECT 
	T2.MaVT,
	CAST(T2.SoLuongNhap AS INT) AS SoLuongNhap,
	CAST(T2.SoLuongXuat AS INT) AS SoLuongXuat,
	CAST(T2.TongDonGiaNhap AS INT) AS TongDonGiaNhap,
	CAST(T2.TongDonGiaXuat AS INT) AS TongDonGiaXuat,
	VatTu.TenVT
FROM
(SELECT MaVT, SUM(SoLuongNhap) AS SoLuongNhap, SUM(SoLuongXuat) AS SoLuongXuat, SUM(TongDonGiaNhap) AS TongDonGiaNhap, SUM(TongDonGiaXuat) AS TongDonGiaXuat
FROM (
	SELECT MaVT, SUM(CTPN.SoLuong) AS SoLuongNhap, 0 AS SoLuongXuat, SUM(CTPN.DONGIA * CTPN.SoLuong) AS TongDonGiaNhap, 0 AS TongDonGiaXuat
	FROM CTPN
	GROUP BY MaVT
	UNION
	SELECT MaVT, 0 AS SoLuongNhap, SUM(CTPX.SoLuong) AS SoLuongXuat, 0 AS TongDonGiaNhap, SUM(CTPX.DONGIA * CTPX.SoLuong) AS TongDonGiaXuat
	FROM CTPX
	GROUP BY MaVT
) AS T1
GROUP BY MaVT) T2
INNER JOIN VatTu ON T2.MaVT = VatTu.MaVT
END

CREATE PROC sp_ThongKeNhapXuatChiNhanh @maCN NVARCHAR(255)
AS
BEGIN
SELECT 
	T2.MaVT,
	CAST(T2.SoLuongNhap AS INT) AS SoLuongNhap,
	CAST(T2.SoLuongXuat AS INT) AS SoLuongXuat,
	CAST(T2.TongDonGiaNhap AS INT) AS TongDonGiaNhap,
	CAST(T2.TongDonGiaXuat AS INT) AS TongDonGiaXuat,
	VatTu.TenVT
FROM
(SELECT MaVT, SUM(SoLuongNhap) AS SoLuongNhap, SUM(SoLuongXuat) AS SoLuongXuat, SUM(TongDonGiaNhap) AS TongDonGiaNhap, SUM(TongDonGiaXuat) AS TongDonGiaXuat
FROM (
	SELECT MaVT, SUM(CTPN.SoLuong) AS SoLuongNhap, 0 AS SoLuongXuat, SUM(CTPN.DONGIA * CTPN.SoLuong) AS TongDonGiaNhap, 0 AS TongDonGiaXuat
	FROM CTPN
	INNER JOIN PhieuNhap ON CTPN.MaPN = PhieuNhap.MaPN
	WHERE PhieuNhap.MaKho IN (SELECT Makho FROM Kho WHERE maCN = @maCN)
	GROUP BY MaVT
	UNION
	SELECT MaVT, 0 AS SoLuongNhap, SUM(CTPX.SoLuong) AS SoLuongXuat, 0 AS TongDonGiaNhap, SUM(CTPX.DONGIA * CTPX.SoLuong) AS TongDonGiaXuat
	FROM CTPX
	INNER JOIN PhieuXuat ON CTPX.MaPX = PhieuXuat.MaPX
	WHERE PhieuXuat.MaKho IN (SELECT Makho FROM Kho WHERE maCN = @maCN)
	GROUP BY MaVT
) AS T1
GROUP BY MaVT) T2
INNER JOIN VatTu ON T2.MaVT = VatTu.MaVT
END

CREATE PROC sp_ThongKeNhapXuatKhoTheoNgay 
AS
BEGIN
SELECT
	T2.MaKho,
	Kho.TenKho,
	T2.Ngay,
	CAST(T2.TongDonGiaNhap AS int) AS TongDonGiaNhap,
	CAST(T2.TongSoLuongNhap AS int) AS TongSoLuongNhap,
	CAST(T2.TongDonGiaXuat AS int) AS TongDonGiaXuat,
	CAST(T2.TongSoLuongXuat AS int) AS TongSoLuongXuat,
	ChiNhanh.ChiNhanh
FROM
(
	SELECT
		T1.MaKho, T1.Ngay, SUM(T1.TongDonGiaNhap) AS TongDonGiaNhap, SUM(T1.TongSoLuongNhap) AS TongSoLuongNhap,
		SUM(T1.TongDonGiaXuat) AS TongDonGiaXuat, SUM(T1.TongSoLuongXuat) AS TongSoLuongXuat
	FROM
	(
		SELECT
			px.MaKho, px.Ngay, 0 AS TongDonGiaNhap, SUM(CTPX.DONGIA * CTPX.SOLUONG) AS TongDonGiaXuat,
			0 AS TongSoLuongNhap, SUM(CTPX.SOLUONG) AS TongSoLuongXuat
		FROM PhieuXuat px
		INNER JOIN CTPX ON px.MaPX = CTPX.MaPX
		GROUP BY px.MaKho, px.Ngay
		UNION
		SELECT pn.MaKho, pn.Ngay, SUM(CTPN.DONGIA * CTPN.SOLUONG) AS TongDonGiaNhap, 0 AS TongDonGiaXuat,
			SUM(CTPN.SOLUONG) AS TongSoLuongNhap, 0 AS TongSoLuongXuat
		FROM PhieuNhap pn
		INNER JOIN CTPN ON pn.MaPN = CTPN.MaPN
		GROUP BY pn.MaKho, pn.Ngay
	) T1
	GROUP BY T1.MaKho, T1.Ngay
) T2
INNER JOIN Kho ON T2.MaKho = Kho.Makho
INNER JOIN ChiNhanh ON Kho.MaCN = ChiNhanh.MaCN
END

CREATE PROC sp_ThongKeNhapTheoThang 
AS
BEGIN
SELECT CAST(SUM(CTPN.SoLuong) AS int) AS SoLuongNhap, CAST(SUM(CTPN.DONGIA * CTPN.SOLUONG) AS int) AS TongDonGiaNhap,
		CAST(MONTH(PhieuNhap.Ngay) AS int) AS Thang, CAST(YEAR(PhieuNhap.Ngay) AS int) AS Nam 
FROM CTPN
INNER JOIN PhieuNhap ON CTPN.MaPN = PhieuNhap.MaPN
GROUP BY MONTH(PhieuNhap.Ngay), YEAR(PhieuNhap.Ngay)
END

CREATE PROC sp_ThongKeXuatTheoThang
AS
BEGIN
SELECT CAST(SUM(CTPX.SoLuong) AS int) AS SoLuongXuat, CAST(SUM(CTPX.DONGIA * CTPX.SOLUONG) AS int) AS TongDonGiaXuat,
		CAST(MONTH(PhieuXuat.Ngay) AS int) AS Thang, CAST(YEAR(PhieuXuat.Ngay) AS int) AS Nam
FROM CTPX
INNER JOIN PhieuXuat ON CTPX.MaPX = PhieuXuat.MaPX
GROUP BY MONTH(PhieuXuat.Ngay), YEAR(PhieuXuat.Ngay)
END