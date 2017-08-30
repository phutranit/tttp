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

	@Before
	public void setup() {
		ngayBatDau = LocalDateTime.of(2017, 8, 30, 10, 03);
		ngayKetThuc = ngayBatDau;
		soNgayKetThuc = 4L;
	}
	
	@Test
	public void testTestCoSoNgayXuLy() {
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc);
		boolean actual = result == 6;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestNgayKetThucXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 9, 2, 10, 03);
		soNgayKetThuc = 1L;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc);
		ngayKetThuc = ngayBatDau.plusDays(result);		
		LocalDateTime test = LocalDateTime.of(2017, 9, 4, 10, 03); 
		boolean actual = ngayKetThuc.isEqual(test);
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayBatDauNull() {
		ngayBatDau = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, 10L);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testSoNgayXuLyTruongHopNgayKetThucNull() {
		ngayKetThuc = null;
		soNgayKetThuc = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}

	@Test
	public void testSoNgayXuLyTruongHopCaBaNgayNull() {
		ngayBatDau = null;
		soNgayKetThuc = null;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestKhongCoSoNgayXuLy() {
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		soNgayKetThuc = 0L;
		Long result = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayKetThuc);
		boolean actual = result == 0;
		assertEquals(true, actual);
	}
}
