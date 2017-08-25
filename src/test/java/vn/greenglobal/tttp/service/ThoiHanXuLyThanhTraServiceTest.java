package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
 
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import vn.greenglobal.tttp.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
public class ThoiHanXuLyThanhTraServiceTest {
	
	private LocalDateTime ngayBatDau;
	LocalDateTime ngayKetThuc; 
	private Long soNgayKetThuc = 0L;
	private LocalDateTime gioHanhChinhHienTai;

	@Before
	public void setup() {
		ngayBatDau = LocalDateTime.of(2017, 8, 17, 10, 03);
		ngayKetThuc = ngayBatDau;
		soNgayKetThuc = 2L;
		gioHanhChinhHienTai = LocalDateTime.of(2017, 8, 17, 10, 56);
	}
	
	@Test
	public void testTestCoSoNgayXuLy() {
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 4;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestNgayKetThucXuLy() {
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		ngayKetThuc = ngayKetThuc.plusDays(result);		
		LocalDateTime test = LocalDateTime.of(2017, 8, 21, 10, 03); 
		boolean actual = ngayKetThuc.isEqual(test);
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayBatDauNull() {
		ngayBatDau = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, 10L, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayKetThucNull() {
		ngayKetThuc = null;
		soNgayKetThuc = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayHienTaiNull() {
		gioHanhChinhHienTai = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopCaBaNgayNull() {
		gioHanhChinhHienTai = null;
		ngayBatDau = null;
		soNgayKetThuc = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestKhongCoSoNgayXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		soNgayKetThuc = 0L;
		gioHanhChinhHienTai = LocalDateTime.of(2017, 6, 17, 17, 31);
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestCo0NgayXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 8, 17, 01, 00);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 8, 18, 12, 00);
		soNgayKetThuc = 1L;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == -2;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestCo5NgayXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 8, 17, 01, 00);
		gioHanhChinhHienTai = LocalDateTime.of(2017, 8, 17, 17, 00);
		soNgayKetThuc = 5L;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		boolean actual = result == 7;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestCo5NgayKetThucXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 8, 17, 00, 00);
		ngayKetThuc = ngayBatDau;
		soNgayKetThuc = 5L;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc, gioHanhChinhHienTai);
		ngayKetThuc = ngayKetThuc.plusDays(result);		
		LocalDateTime test = LocalDateTime.of(2017, 8, 24, 00, 00); 
		boolean actual = ngayKetThuc.isEqual(test);
		assertEquals(true, actual);
	}
}
