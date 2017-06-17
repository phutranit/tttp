package vn.greenglobal.tttp.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import vn.greenglobal.Application;
import vn.greenglobal.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChucVuControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	
	@Test
	public void verifyAllChucVuList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/chucVus")
				//token van thu
				.header(HttpHeaders.AUTHORIZATION, "Bearer eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..ISocx98ucoPe5hKs.kKO6JRmD3pv-GSeCe2mfwlTFeROyR-yz7P_APdmuyemSQryBNCbw7jB3WM7eK82fIw46JOBpjvtG1hGPVJ0uVUcIes1B_dyGzE3TUM1vOv0koj5s_XKCv2OXbdqANTuwxL5WKgo8a79sTvH5aOj5mOVhJ6OjoiK1MYJWheTFVK5JnpMkDY046Ulwv5OOkciWlY5gvL3lQWGuYPbrZkIQoco8TJnzYAeux0MD60mLBk_mr_FxyU88fqtaVfCq0PlWC_01eitTIx6G3MBc0sY2tinsoVdEt_LoKFI9XRfIDnYWH-W8mtzhi_ZMkB4ITnOkqjXS4_-Hv3GjLyyg4vLbhzam2__TLx1mOrrrrR7AV7z5dh4I8MfzeNyWKo1DI-uR6hmYFJP4ULzk-9tDtgH31ue0qU6jaA1MZ0Tv0en7aYGfjzXSZBUZhgdE0d0N.B-gMb3RWM1iCtneECMzx2w")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())	
		.andExpect(jsonPath("$._embedded.chucVus", hasSize(8))).andDo(print());
	}
}
