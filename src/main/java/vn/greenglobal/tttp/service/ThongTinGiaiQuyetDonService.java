package vn.greenglobal.tttp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongTinGiaiQuyetDonService {

	@Autowired
	ThongTinGiaiQuyetDonRepository thongTinGiaiQuyetDonRepository;
	
	BooleanExpression base = QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.daXoa.eq(false);
	
	public Predicate predicateFindByDonCongChuc(List<Don> dons, Long congChucId) {
		return base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.don.in(dons))
				.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.canBoXuLyChiDinh.id.eq(congChucId)
						.or(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.canBoThamTraXacMinh.id.eq(congChucId))
						.or(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.canBoGiaiQuyet.id.eq(congChucId))
						);
	}
	
	public boolean isExists(ThongTinGiaiQuyetDonRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id));
	}

	public Predicate predicateFindByDon(Long donId) {
		return base.and(QThongTinGiaiQuyetDon.thongTinGiaiQuyetDon.don.id.eq(donId));
	}
	
	public List<PropertyChangeObject> getListThayDoi(ThongTinGiaiQuyetDon thongTinNew, ThongTinGiaiQuyetDon thongTinOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (thongTinNew.getSoQuyetDinhThanhLapDTXM() != null 
				&& !thongTinNew.getSoQuyetDinhThanhLapDTXM().equals(thongTinOld.getSoQuyetDinhThanhLapDTXM())) {
			list.add(new PropertyChangeObject("Số quyết định thành lập đoàn tổ xác minh", thongTinOld.getSoQuyetDinhThanhLapDTXM(), thongTinNew.getSoQuyetDinhThanhLapDTXM()));
		}
		
		if (thongTinNew.getSoQuyetDinhGiaHanGiaiQuyet() != null 
				&& !thongTinNew.getSoQuyetDinhGiaHanGiaiQuyet().equals(thongTinOld.getSoQuyetDinhGiaHanGiaiQuyet())) {
			list.add(new PropertyChangeObject("Số quyết định gia hạn giải quyết", thongTinOld.getSoQuyetDinhGiaHanGiaiQuyet(), thongTinNew.getSoQuyetDinhGiaHanGiaiQuyet()));
		}
		
		if (thongTinNew.getSoQuyetDinhGiaHanTTXM() != null 
				&& !thongTinNew.getSoQuyetDinhGiaHanTTXM().equals(thongTinOld.getSoQuyetDinhGiaHanTTXM())) {
			list.add(new PropertyChangeObject("Số quyết định gia hạn thẩm tra xác minh", thongTinOld.getSoQuyetDinhGiaHanTTXM(), thongTinNew.getSoQuyetDinhGiaHanTTXM()));
		}
		
		if (thongTinNew.getNgayRaQuyetDinhGiaHanGiaiQuyet() != null 
				&& !thongTinNew.getNgayRaQuyetDinhGiaHanGiaiQuyet().format(formatter).equals(
						thongTinOld.getNgayRaQuyetDinhGiaHanGiaiQuyet() != null ? thongTinOld.getNgayRaQuyetDinhGiaHanGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày ra quyết định gia hạn giải quyết", 
					thongTinOld.getNgayRaQuyetDinhGiaHanGiaiQuyet() != null ? thongTinOld.getNgayRaQuyetDinhGiaHanGiaiQuyet().format(formatter) : "",
							thongTinNew.getNgayRaQuyetDinhGiaHanGiaiQuyet().format(formatter)));
		}
		
		if (thongTinNew.getDiaDiemDoiThoai() != null 
				&& !thongTinNew.getDiaDiemDoiThoai().equals(thongTinOld.getDiaDiemDoiThoai())) {
			list.add(new PropertyChangeObject("Địa điểm đối thoại", thongTinOld.getDiaDiemDoiThoai(), thongTinNew.getDiaDiemDoiThoai()));
		}
		
		if (thongTinNew.getLyDoGiaHanGiaiQuyet() != null 
				&& !thongTinNew.getLyDoGiaHanGiaiQuyet().equals(thongTinOld.getLyDoGiaHanGiaiQuyet())) {
			list.add(new PropertyChangeObject("Lý do gia hạn giải quyết", thongTinOld.getLyDoGiaHanGiaiQuyet(), thongTinNew.getLyDoGiaHanGiaiQuyet()));
		}
		
		if (thongTinNew.getLyDoGiaHanTTXM() != null 
				&& !thongTinNew.getLyDoGiaHanTTXM().equals(thongTinOld.getLyDoGiaHanTTXM())) {
			list.add(new PropertyChangeObject("Lý do gia hạn thẩm tra xác minh", thongTinOld.getLyDoGiaHanTTXM(), thongTinNew.getLyDoGiaHanTTXM()));
		}
		
		if (thongTinNew.getNoiDung() != null 
				&& !thongTinNew.getNoiDung().equals(thongTinOld.getNoiDung())) {
			list.add(new PropertyChangeObject("Nội dung", thongTinOld.getNoiDung(), thongTinNew.getNoiDung()));
		}
		
		if (thongTinNew.getThoiGianDoiThoai() != null 
				&& !thongTinNew.getThoiGianDoiThoai().format(formatter).equals(
						thongTinOld.getThoiGianDoiThoai() != null ? thongTinOld.getThoiGianDoiThoai().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Thời gian đối thoại", 
					thongTinOld.getThoiGianDoiThoai() != null ? thongTinOld.getThoiGianDoiThoai().format(formatter) : "",
							thongTinNew.getThoiGianDoiThoai().format(formatter)));
		}
		
		if (thongTinNew.getNgayBaoCaoKetQuaTTXM() != null 
				&& !thongTinNew.getNgayBaoCaoKetQuaTTXM().format(formatter).equals(
						thongTinOld.getNgayBaoCaoKetQuaTTXM() != null ? thongTinOld.getNgayBaoCaoKetQuaTTXM().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày báo cáo kết quả TTXM", 
					thongTinOld.getNgayBaoCaoKetQuaTTXM() != null ? thongTinOld.getNgayBaoCaoKetQuaTTXM().format(formatter) : "",
							thongTinNew.getNgayBaoCaoKetQuaTTXM().format(formatter)));
		}
		
		if (thongTinNew.getNgayBatDauGiaiQuyet() != null 
				&& !thongTinNew.getNgayBatDauGiaiQuyet().format(formatter).equals(
						thongTinOld.getNgayBatDauGiaiQuyet() != null ? thongTinOld.getNgayBatDauGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày bắt đầu giải quyết", 
					thongTinOld.getNgayBatDauGiaiQuyet() != null ? thongTinOld.getNgayBatDauGiaiQuyet().format(formatter) : "",
							thongTinNew.getNgayBatDauGiaiQuyet().format(formatter)));
		}
		
		if (thongTinNew.getNgayKetThucGiaiQuyet() != null 
				&& !thongTinNew.getNgayKetThucGiaiQuyet().format(formatter).equals(
						thongTinOld.getNgayKetThucGiaiQuyet() != null ? thongTinOld.getNgayKetThucGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày kết thúc giải quyết", 
					thongTinOld.getNgayKetThucGiaiQuyet() != null ? thongTinOld.getNgayKetThucGiaiQuyet().format(formatter) : "",
							thongTinNew.getNgayKetThucGiaiQuyet().format(formatter)));
		}
		
		if (thongTinNew.getNgayHetHanGiaiQuyet() != null 
				&& !thongTinNew.getNgayHetHanGiaiQuyet().format(formatter).equals(
						thongTinOld.getNgayHetHanGiaiQuyet() != null ? thongTinOld.getNgayHetHanGiaiQuyet().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày hết hạn giải quyết", 
					thongTinOld.getNgayHetHanGiaiQuyet() != null ? thongTinOld.getNgayHetHanGiaiQuyet().format(formatter) : "",
							thongTinNew.getNgayHetHanGiaiQuyet().format(formatter)));
		}
		
		if (thongTinNew.getNgayBatDauTTXM() != null 
				&& !thongTinNew.getNgayBatDauTTXM().format(formatter).equals(
						thongTinOld.getNgayBatDauTTXM() != null ? thongTinOld.getNgayBatDauTTXM().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày bắt đầu TTXM", 
					thongTinOld.getNgayBatDauTTXM() != null ? thongTinOld.getNgayBatDauTTXM().format(formatter) : "",
							thongTinNew.getNgayBatDauTTXM().format(formatter)));
		}
		
		if (thongTinNew.getNgayKetThucTTXM() != null 
				&& !thongTinNew.getNgayKetThucTTXM().format(formatter).equals(
						thongTinOld.getNgayKetThucTTXM() != null ? thongTinOld.getNgayKetThucTTXM().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày kết thúc TTXM", 
					thongTinOld.getNgayKetThucTTXM() != null ? thongTinOld.getNgayKetThucTTXM().format(formatter) : "",
							thongTinNew.getNgayKetThucTTXM().format(formatter)));
		}
		
		if (thongTinNew.getNgayHetHanTTXM() != null 
				&& !thongTinNew.getNgayHetHanTTXM().format(formatter).equals(
						thongTinOld.getNgayHetHanTTXM() != null ? thongTinOld.getNgayHetHanTTXM().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày hết hạn TTXM", 
					thongTinOld.getNgayHetHanTTXM() != null ? thongTinOld.getNgayHetHanTTXM().format(formatter) : "",
							thongTinNew.getNgayHetHanTTXM().format(formatter)));
		}		
		
		if (thongTinNew.getNgayRaQuyetDinhGiaHanTTXM() != null 
				&& !thongTinNew.getNgayRaQuyetDinhGiaHanTTXM().format(formatter).equals(
						thongTinOld.getNgayRaQuyetDinhGiaHanTTXM() != null ? thongTinOld.getNgayRaQuyetDinhGiaHanTTXM().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày ra quyết định gia hạn TTXM", 
					thongTinOld.getNgayRaQuyetDinhGiaHanTTXM() != null ? thongTinOld.getNgayRaQuyetDinhGiaHanTTXM().format(formatter) : "",
							thongTinNew.getNgayRaQuyetDinhGiaHanTTXM().format(formatter)));
		}
		
		if (thongTinNew.isLapToDoanXacMinh() != thongTinOld.isLapToDoanXacMinh()) {
			list.add(new PropertyChangeObject("Lập tổ đoàn xác minh", thongTinOld.isLapToDoanXacMinh() ? "Có" : "Không", thongTinNew.isLapToDoanXacMinh() ? "Có" : "Không"));
		}
		
		if (thongTinNew.isGiaHanGiaiQuyet() != thongTinOld.isGiaHanGiaiQuyet()) {
			list.add(new PropertyChangeObject("Gia hạn giải quyết", thongTinOld.isGiaHanGiaiQuyet() ? "Có" : "Không", thongTinNew.isGiaHanGiaiQuyet() ? "Có" : "Không"));
		}
		if (thongTinNew.isDoiThoai() != thongTinOld.isDoiThoai()) {
			list.add(new PropertyChangeObject("Đối thoại", thongTinOld.isDoiThoai() ? "Có" : "Không", thongTinNew.isDoiThoai() ? "Có" : "Không"));
		}
		if (thongTinNew.isGiaoCoQuanDieuTra() != thongTinOld.isGiaoCoQuanDieuTra()) {
			list.add(new PropertyChangeObject("Giao cơ quan điều tra", thongTinOld.isGiaoCoQuanDieuTra() ? "Có" : "Không", thongTinNew.isGiaoCoQuanDieuTra() ? "Có" : "Không"));
		}
		if (thongTinNew.isKhoiTo() != thongTinOld.isKhoiTo()) {
			list.add(new PropertyChangeObject("Khởi tố", thongTinOld.isKhoiTo() ? "Có" : "Không", thongTinNew.isKhoiTo() ? "Có" : "Không"));
		}
		if (thongTinNew.isQuyetDinhGiaiQuyetKhieuNai() != thongTinOld.isQuyetDinhGiaiQuyetKhieuNai()) {
			list.add(new PropertyChangeObject("Quyết định giải quyết khiếu nại", thongTinOld.isQuyetDinhGiaiQuyetKhieuNai() ? "Có" : "Không", thongTinNew.isQuyetDinhGiaiQuyetKhieuNai() ? "Có" : "Không"));
		}
		if (thongTinNew.isTheoDoiThucHien() != thongTinOld.isTheoDoiThucHien()) {
			list.add(new PropertyChangeObject("Theo dõi thực hiện", thongTinOld.isTheoDoiThucHien() ? "Có" : "Không", thongTinNew.isTheoDoiThucHien() ? "Có" : "Không"));
		}
		if (thongTinNew.getSoVuGiaoCoQuanDieuTra() != thongTinOld.getSoVuGiaoCoQuanDieuTra()) {
			list.add(new PropertyChangeObject("Số vụ giao cơ quan điều tra", thongTinOld.getSoVuGiaoCoQuanDieuTra() + "", thongTinNew.getSoVuGiaoCoQuanDieuTra() + ""));
		}
		if (thongTinNew.getSoDoiTuongGiaoCoQuanDieuTra() != thongTinOld.getSoDoiTuongGiaoCoQuanDieuTra()) {
			list.add(new PropertyChangeObject("Số đối tượng giao cơ quan điều tra", thongTinOld.getSoDoiTuongGiaoCoQuanDieuTra() + "", thongTinNew.getSoDoiTuongGiaoCoQuanDieuTra() + ""));
		}
		if (thongTinNew.getSoVuBiKhoiTo() != thongTinOld.getSoVuBiKhoiTo()) {
			list.add(new PropertyChangeObject("Số vụ bị khởi tố", thongTinOld.getSoVuBiKhoiTo() + "", thongTinNew.getSoVuBiKhoiTo() + ""));
		}
		if (thongTinNew.getSoDoiTuongBiKhoiTo() != thongTinOld.getSoDoiTuongBiKhoiTo()) {
			list.add(new PropertyChangeObject("Số đối tượng bị khiếu tố", thongTinOld.getSoDoiTuongBiKhoiTo() + "", thongTinNew.getSoDoiTuongBiKhoiTo() + ""));
		}
		if (thongTinNew.getTongSoNguoiXuLyHanhChinh() != thongTinOld.getTongSoNguoiXuLyHanhChinh()) {
			list.add(new PropertyChangeObject("Tổng số người xử lý hành chính", thongTinOld.getTongSoNguoiXuLyHanhChinh() + "", thongTinNew.getTongSoNguoiXuLyHanhChinh() + ""));
		}
		if (thongTinNew.getSoNguoiDaBiXuLyHanhChinh() != thongTinOld.getSoNguoiDaBiXuLyHanhChinh()) {
			list.add(new PropertyChangeObject("Số người đã bị xử lý hành chính", thongTinOld.getSoNguoiDaBiXuLyHanhChinh() + "", thongTinNew.getSoNguoiDaBiXuLyHanhChinh() + ""));
		}
		if (thongTinNew.getTienPhaiThuNhaNuoc() != thongTinOld.getTienPhaiThuNhaNuoc()) {
			list.add(new PropertyChangeObject("Tiền phải thu cho nhà nước", thongTinOld.getTienPhaiThuNhaNuoc() + "", thongTinNew.getTienPhaiThuNhaNuoc() + ""));
		}
		if (thongTinNew.getDatPhaiThuNhaNuoc() != thongTinOld.getDatPhaiThuNhaNuoc()) {
			list.add(new PropertyChangeObject("Đất phải thu cho nhà nước", thongTinOld.getDatPhaiThuNhaNuoc() + "", thongTinNew.getDatPhaiThuNhaNuoc() + ""));
		}
		if (thongTinNew.getTienPhaiTraCongDan() != thongTinOld.getTienPhaiTraCongDan()) {
			list.add(new PropertyChangeObject("Tiền phải trả công dân", thongTinOld.getTienPhaiTraCongDan() + "", thongTinNew.getTienPhaiTraCongDan() + ""));
		}
		if (thongTinNew.getDatPhaiTraCongDan() != thongTinOld.getDatPhaiTraCongDan()) {
			list.add(new PropertyChangeObject("Đất phải trả công dân", thongTinOld.getDatPhaiTraCongDan() + "", thongTinNew.getDatPhaiTraCongDan() + ""));
		}
		if (thongTinNew.getTienDaThuNhaNuoc() != thongTinOld.getTienDaThuNhaNuoc()) {
			list.add(new PropertyChangeObject("Tiền đã thu cho nhà nước", thongTinOld.getTienDaThuNhaNuoc() + "", thongTinNew.getTienDaThuNhaNuoc() + ""));
		}
		if (thongTinNew.getDatDaThuNhaNuoc() != thongTinOld.getDatDaThuNhaNuoc()) {
			list.add(new PropertyChangeObject("Đất đã thu cho nhà nước", thongTinOld.getDatDaThuNhaNuoc() + "", thongTinNew.getDatDaThuNhaNuoc() + ""));
		}
		if (thongTinNew.getTienDaTraCongDan() != thongTinOld.getTienDaTraCongDan()) {
			list.add(new PropertyChangeObject("Tiền đã trả công dân", thongTinOld.getTienDaTraCongDan() + "", thongTinNew.getTienDaTraCongDan() + ""));
		}
		if (thongTinNew.getDatDaTraCongDan() != thongTinOld.getDatDaTraCongDan()) {
			list.add(new PropertyChangeObject("Đất đã trả công dân", thongTinOld.getDatDaTraCongDan() + "", thongTinNew.getDatDaTraCongDan() + ""));
		}
		return list;
	}
	
	public ThongTinGiaiQuyetDon save(ThongTinGiaiQuyetDon obj, Long congChucId) {
		return Utils.save(thongTinGiaiQuyetDonRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ThongTinGiaiQuyetDon obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(thongTinGiaiQuyetDonRepository, obj, congChucId, eass, status);		
	}
	
}
