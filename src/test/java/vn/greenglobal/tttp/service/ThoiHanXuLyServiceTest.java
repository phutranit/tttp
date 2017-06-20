package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.Calendar;

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
	
	private Calendar lichHienTaiTrongTuan;
	private LocalDateTime ngayBatDau;
	private LocalDateTime ngayKetThuc;
	private LocalDateTime gioHanhChinhHienTai;

	@Before
	public void setup() {
		lichHienTaiTrongTuan = Calendar.getInstance();
		ngayBatDau = LocalDateTime.of(2017, 6, 11, 17, 31);
		ngayKetThuc = LocalDateTime.of(2017, 6, 20, 17, 31);
		gioHanhChinhHienTai = LocalDateTime.now();
	}
	
	@Test
	public void testTestLaySoNgayXuLy() {
		Long result = Utils.getLaySoNgay(lichHienTaiTrongTuan, ngayBatDau, ngayKetThuc, gioHanhChinhHienTai);
		
		boolean actual = result == 3;
		assertEquals(true, actual);
	}
	
	@Test
	public void testTestLayGioPhut() {
		lichHienTaiTrongTuan = Calendar.getInstance();
		String result = Utils.getLaySoGioPhut(lichHienTaiTrongTuan, gioHanhChinhHienTai, ngayKetThuc);
		System.out.println(String.format("Còn %s phút", result));
		Assert.assertEquals(true, !StringUtils.isEmpty(result));
	}
	
	@Test
	public void testTestNgayTreHan() {
		Long actual = Utils.getLayNgayTreHan(lichHienTaiTrongTuan, ngayBatDau, ngayKetThuc);
		Long expected = 0L;
		Assert.assertEquals(expected, actual);
	}
}
