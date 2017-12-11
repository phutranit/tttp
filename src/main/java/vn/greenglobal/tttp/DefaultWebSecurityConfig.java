package vn.greenglobal.tttp;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Order(2)
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	Config config;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("tttp123").password("tttp@123").roles("USER", "ADMIN",
				"ACTUATOR");
	}

	@Override
	public void configure(WebSecurity sec) throws Exception {
		sec.ignoring().antMatchers(
				"/auth/login", "/auth/logout", "/auth/sendEmail", "/auth/confirmCode", "/auth/resetPassword", "/v2/api-docs",
				"/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhToCao", "/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhKhieuNai",
				"/soTiepCongDans/inPhieuHen", "/documents/uploadhandler", "/tttpdata/files/**",
				"/soTiepCongDans/excel", "/xuLyDons/inPhieuDeXuatThuLy", "/dons/xuatExcel",
				"/xuLyDons/inPhieuKhongDuDieuKienThuLyKhieuNai", "/xuLyDons/inPhieuDuThaoThongBaoThuLyGQTC",
				"/xuLyDons/inPhieuDuThaoThongBaoThuLyKhieuNai", "/xuLyDons/inPhieuDeXuatKienNghi",
				"/xuLyDons/inPhieuKhongDuDieuKienThuLy", "/soTiepCongDans/word", "/configuration/ui",
				"/configuration/security", "/xuLyDons/inPhieuTraDonVaHuongDanKhieuNai", "/swagger-resources",
				"/swagger-ui.html", "/swagger-resources/configuration/ui", "/xuLyDons/inPhieuChuyenDonToCao",
				"/xuLyDons/inPhieuTraDonChuyenKhongDungThamQuyen", "/xuLyDons/inPhieuDuThaoThongBaoThuLyKienNghi",
				"/xuLyDons/inPhieuChuyenDonKienNghiPhanAnh", "/swagger-resources/configuration/security",
				"/thongKeBaoCaos/tongHopKetQuaTiepCongDan/xuatExcel", "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao/xuatExcel",
				"/soTiepCongDans/inPhieuTuChoi", "/soTiepCongDans/inPhieuHuongDanKhieuNai", 
				"/soTiepCongDans/inPhieuHuongDanToCao","/traCuuDons/congDan",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDauTuXayDungCoBan/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoTaiChinhNganSach/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDatDai/xuatExcel",
				"/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonViCon/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaPhatHienThamNhungQuaThanhTra/xuatExcel",
				"/soTiepCongDans/danhSachYeuCauGapLanhDao/excel",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraLai/xuatExcel",
				"/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonVi/xuatExcel",
				"/thongKeBaoCaos/tongHopKetQuaThanhTraTheoChuyenNganh/xuatExcel",
				"/theoDoiGiamSats/danhSachDonDungHanTreHanTaiDonVi/xuatExcel",
				"/xuLyDons/inPhieuKhongDuDieuKienThuLyToCao", "/xuLyDons/inPhieuKhongDuDieuKienThuLyKienNghi",
				"/giaiQuyetDons/inPhieuGiaoNhiemVuXacMinhKienNghi", "/xuLyDons/inPhieuHuongDanToCao",
				"/xuLyDons/inPhieuHuongDanKienNghi",
				"/webjars/**").antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		final SecurityFilter filter = new SecurityFilter(config, "ParameterClient,HeaderClient", "custom");
		http.addFilterBefore(filter, BasicAuthenticationFilter.class).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER);

		http.authorizeRequests().anyRequest().authenticated()
				.antMatchers("/cas/**").permitAll()
				.and().logout().logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true)
				.and().csrf().disable();
	}
}
