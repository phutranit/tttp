package vn.greenglobal.tttp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QuocTich;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DanTocRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.QuocTichRepository;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;

@Component
public class CongDanService {

	@Autowired
	private DanTocRepository danTocRepo;

	@Autowired
	private QuocTichRepository quocTichRepo;

	@Autowired
	private DonViHanhChinhRepository donViHanhChinhRepo;

	@Autowired
	private ToDanPhoRepository toDanPhoRepo;

	BooleanExpression base = QCongDan.congDan.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long tinhThanh, Long quanHuyen, Long phuongXa, Long toDanPho) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QCongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa.trim())
					.or(QCongDan.congDan.soDienThoai.containsIgnoreCase(tuKhoa.trim()))
					.or(QCongDan.congDan.diaChi.containsIgnoreCase(tuKhoa.trim()))
					.or(QCongDan.congDan.soCMNDHoChieu.containsIgnoreCase(tuKhoa.trim())));
		}

		if (tinhThanh != null && tinhThanh > 0) {
			predAll = predAll.and(QCongDan.congDan.tinhThanh.id.eq(tinhThanh));
		}
		if (quanHuyen != null && quanHuyen > 0) {
			predAll = predAll.and(QCongDan.congDan.quanHuyen.id.eq(quanHuyen));
		}

		if (phuongXa != null && phuongXa > 0) {
			predAll = predAll.and(QCongDan.congDan.phuongXa.id.eq(phuongXa));
		}

		if (toDanPho != null && toDanPho > 0) {
			predAll = predAll.and(QCongDan.congDan.toDanPho.id.eq(toDanPho));
		}
		return predAll;
	}

	public Predicate predicateFindCongDanBySuggests(String tuKhoa, String soCMND, String diaChi) {
		BooleanExpression predAll = base;
		if (StringUtils.isNotEmpty(tuKhoa)) {
			predAll = predAll.and(QCongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa));
		}
		if (StringUtils.isNotEmpty(soCMND)) {
			predAll = predAll.and(QCongDan.congDan.soCMNDHoChieu.containsIgnoreCase(soCMND));
		}
		if (StringUtils.isNotEmpty(diaChi)) {
			predAll = predAll.and(QCongDan.congDan.diaChi.containsIgnoreCase(diaChi));
		}
		return predAll;
	}
	
	public Predicate predicateFindCongDanExists(String tuKhoa, String soCMND, String diaChi) {
		BooleanExpression predAll = base
				.and(QCongDan.congDan.hoVaTen.equalsIgnoreCase(tuKhoa))
				.and(QCongDan.congDan.soCMNDHoChieu.equalsIgnoreCase(soCMND))
				.and(QCongDan.congDan.diaChi.equalsIgnoreCase(diaChi));		
		return predAll;
	}
	
	public Predicate predicateFindCongDanExists(String soCMND) {
		return base.and(QCongDan.congDan.soCMNDHoChieu.equalsIgnoreCase(soCMND));
	}

	public List<PropertyChangeObject> getListThayDoi(CongDan congDanNew, CongDan congDanOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (congDanNew.getHoVaTen() != null && !congDanNew.getHoVaTen().isEmpty() && congDanOld.getHoVaTen() != null
				&& !congDanNew.getHoVaTen().equals(congDanOld.getHoVaTen())) {
			list.add(new PropertyChangeObject("Họ và tên", congDanOld.getHoVaTen(), congDanNew.getHoVaTen()));
		}
		if (congDanNew.getSoDienThoai() != null && !congDanNew.getSoDienThoai().isEmpty() && congDanOld.getSoDienThoai() != null
				&& !congDanNew.getSoDienThoai().equals(congDanOld.getSoDienThoai())) {
			list.add(new PropertyChangeObject("Số điện thoại", congDanOld.getSoDienThoai(),
					congDanNew.getSoDienThoai()));
		}
		if (congDanNew.getSoCMNDHoChieu() != null && !congDanNew.getSoCMNDHoChieu().isEmpty() && congDanOld.getSoCMNDHoChieu() != null
				&& !congDanNew.getSoCMNDHoChieu().equals(congDanOld.getSoCMNDHoChieu())) {
			list.add(new PropertyChangeObject("Số CMND/Hộ chiếu", congDanOld.getSoCMNDHoChieu(),
					congDanNew.getSoCMNDHoChieu()));
		}
		if (congDanNew.getDiaChi() != null && !congDanNew.getDiaChi().isEmpty() && congDanOld.getDiaChi() != null
				&& !congDanNew.getDiaChi().equals(congDanOld.getDiaChi())) {
			list.add(new PropertyChangeObject("Địa chỉ", congDanOld.getDiaChi(), congDanNew.getDiaChi()));
		}
		if (congDanNew.getNgaySinh() != null && congDanOld.getNgaySinh() != null && congDanOld.getNgaySinh() != null
				&& !congDanOld.getNgaySinh().format(formatter).equals(congDanNew.getNgaySinh().format(formatter))) {
			list.add(new PropertyChangeObject("Ngày sinh", congDanOld.getNgaySinh().format(formatter),
					congDanNew.getNgaySinh().format(formatter)));
		}
		if (congDanNew.isGioiTinh() != congDanOld.isGioiTinh()) {
			list.add(new PropertyChangeObject("Giới tính", congDanOld.isGioiTinh() ? "Nam" : "Nữ",
					congDanNew.isGioiTinh() ? "Nam" : "Nữ"));
		}
		if (congDanNew.getDanToc() != null && congDanNew.getDanToc() != congDanOld.getDanToc()) {
			DanToc danTocNew = danTocRepo.findOne(congDanNew.getDanToc().getId());
			list.add(new PropertyChangeObject("Dân tộc",
					congDanOld.getDanToc() != null ? congDanOld.getDanToc().getTen() : "", danTocNew.getTen()));
		}
		if (congDanNew.getQuocTich() != null && congDanNew.getQuocTich() != congDanOld.getQuocTich()) {
			QuocTich quocTichNew = quocTichRepo.findOne(congDanNew.getQuocTich().getId());
			list.add(new PropertyChangeObject("Quốc tịch",
					congDanOld.getQuocTich() != null ? congDanOld.getQuocTich().getTen() : "", quocTichNew.getTen()));
		}
		if (congDanNew.getTinhThanh() != null && congDanNew.getTinhThanh() != congDanOld.getTinhThanh()) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(congDanNew.getTinhThanh().getId());
			list.add(new PropertyChangeObject("Tỉnh thành",
					congDanOld.getTinhThanh() != null ? congDanOld.getTinhThanh().getTen() : "",
					donViHanhChinhNew.getTen()));
		}
		if (congDanNew.getQuanHuyen() != null && congDanNew.getQuanHuyen() != congDanOld.getQuanHuyen()) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(congDanNew.getQuanHuyen().getId());
			list.add(new PropertyChangeObject("Quận huyện",
					congDanOld.getQuanHuyen() != null ? congDanOld.getQuanHuyen().getTen() : "",
					donViHanhChinhNew.getTen()));
		}
		if (congDanNew.getPhuongXa() != null && congDanNew.getPhuongXa() != congDanOld.getPhuongXa()) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(congDanNew.getPhuongXa().getId());
			list.add(new PropertyChangeObject("Phường xã",
					congDanOld.getPhuongXa() != null ? congDanOld.getPhuongXa().getTen() : "",
					donViHanhChinhNew.getTen()));
		}
		if (congDanNew.getToDanPho() != null && congDanNew.getToDanPho() != congDanOld.getToDanPho()) {
			ToDanPho toDanPhoNew = toDanPhoRepo.findOne(congDanNew.getToDanPho().getId());
			list.add(new PropertyChangeObject("Thôn tổ",
					congDanOld.getToDanPho() != null ? congDanOld.getToDanPho().getTen() : "", toDanPhoNew.getTen()));
		}
		return list;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCongDan.congDan.id.eq(id));
	}

	public boolean isExists(CongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCongDan.congDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CongDan delete(CongDanRepository repo, Long id) {
		CongDan congDan = repo.findOne(predicateFindOne(id));

		if (congDan != null) {
			congDan.setDaXoa(true);
		}

		return congDan;
	}

	public boolean checkUsedData(DonCongDanRepository donCongDanRepository, Long id) {
		List<Don_CongDan> donCongDanList = (List<Don_CongDan>) donCongDanRepository
				.findAll(QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.congDan.id.eq(id)));

		if (donCongDanList != null && donCongDanList.size() > 0) {
			return true;
		}

		return false;
	}

}
