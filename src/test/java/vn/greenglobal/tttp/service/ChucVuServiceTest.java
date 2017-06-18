package vn.greenglobal.tttp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.repository.ChucVuRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class ChucVuServiceTest {
	
	@Mock
	private ChucVuRepository chucVuRepo;
	
	@InjectMocks
	private ChucVuService chucVuService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllChucVu(){
		List<ChucVu> toDoList = new ArrayList<ChucVu>();
		toDoList.add(new ChucVu("Lanh dao"));
		toDoList.add(new ChucVu("Truong Phong"));
		toDoList.add(new ChucVu("Chuyen vien"));
		when(chucVuRepo.findAll(chucVuService.predicateFindAll("thanh"))).thenReturn(toDoList);
		
		assertEquals(3, chucVuService.findAll("thanh").size());
	}
}
