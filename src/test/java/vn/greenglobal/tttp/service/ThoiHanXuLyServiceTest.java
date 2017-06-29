package vn.greenglobal.tttp.service;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import vn.greenglobal.tttp.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
public class ThoiHanXuLyServiceTest {
	
	private LocalDateTime ngayBatDau;
	private LocalDateTime ngayKetThuc;
	private LocalDateTime gioHanhChinhHienTai;

	@Before
	public void setup() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 20, 11, 31);
	}
	
	@Test
	public void testTestCoSoNgayXuLy() {
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 3;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayBatDauNull() {
		ngayBatDau = null;
		ngayKetThuc = LocalDateTime.of(2017, 6, 17, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 17, 11, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayKetThucNull() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = null;
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 17, 11, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayHienTaiNull() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 17, 17, 31);
		gioHanhChinhHienTai = null;
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopCaBaNgayNull() {
		ngayBatDau = null;
		ngayKetThuc = null;
		gioHanhChinhHienTai = null;
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestKhongCoSoNgayXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 17, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 20, 17, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyAmMot() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 15, 16, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 19, 11, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == -1;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyAmBa() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 17, 16, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 20, 07, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == -3;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyAmHai() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 17, 16, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 20, 14, 31);
		Long result = Utils.getLaySoNgay(ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == -2;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTruongHopLayDungNgayGioPhutHienTai() {
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 20, 13, 31);
		String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
		Assert.assertEquals(true, !StringUtils.isEmpty(result));
	}
	
	@Test
	public void testTruongHopLaySaiNgayGioPhutHienTai() {
		ngayKetThuc = LocalDateTime.of(2017, 6, 25, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 23, 14, 30);
		String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
		Assert.assertEquals(true, StringUtils.isEmpty(result));
	}
	
	@Test
	public void testGioPhutHienTaiGioHanhChinhNull() {
		ngayKetThuc = LocalDateTime.of(2017, 6, 25, 17, 31);
		gioHanhChinhHienTai = null;
		String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
		Assert.assertEquals(true, StringUtils.isEmpty(result));
	}
	
	@Test
	public void testGioPhutHienTaiNgayKetThucNull() {
		ngayKetThuc = null;
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 23, 14, 30);
		String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
		Assert.assertEquals(true, StringUtils.isEmpty(result));
	}
	
	@Test
	public void testGioPhutHienTaiNgayGioSai() {
		try { 
			ngayKetThuc = null;
			gioHanhChinhHienTai = LocalDateTime.of(2017, 6, -55, -14, 30);
			String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
			Assert.assertEquals(true, StringUtils.isEmpty(result));
		} catch (DateTimeException e) { 
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGioPhutHienTaiCaHaiNgayNull() {
		ngayKetThuc = null;
		gioHanhChinhHienTai = null;
		String result = Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayKetThuc);
		Assert.assertEquals(true, StringUtils.isEmpty(result));
	}
	
	@Test
	public void testKhongCoNgayTreHan() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 23, 11, 31);
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCoNgayTreHan() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 24, 11, 31);
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 1L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNgayTreHanGioHanhChinhHienTaiNull() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = null;
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNgayTreHanNgayBatDauNull() {
		ngayBatDau = null;
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 24, 11, 31);
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNgayTreHanNgayKetThucNull() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = null;
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 24, 11, 31);
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNgayTreHanCaBaNgayDeuNull() {
		ngayBatDau = null;
		ngayKetThuc = null;
		gioHanhChinhHienTai = null;
		Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCoNgayTreHanNgayGioSai() {
		try { 
			ngayBatDau = LocalDateTime.of(2017, 6, -11, 25, 31);
			ngayKetThuc = LocalDateTime.of(2017, 6, -20, 17, 31);
			gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 24, 11, 31);
			Long actual = Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayKetThuc);
			Long expected = 1L;
			Assert.assertEquals(expected, actual);
		} catch (DateTimeException e) { 
			e.printStackTrace();
		}
	}
}
