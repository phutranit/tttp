package vn.greenglobal.tttp.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;

public class XuLyDonFunctions {
	
	@Autowired
	static XuLyDonRepository xuLyDonRepo;
	
	@Autowired
	static DonRepository donRepo;
	
	@Autowired
	private static CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private static CongChucRepository congChucRepo;
	
	private static DonService donService = new DonService();
	private static XuLyDonService xuLyDonService = new XuLyDonService();
	
	//
	public XuLyDon dinhChiDon(XuLyDon xuLyDon, String note, Long congChucId) {
		XuLyDon xuLyDonHienTai = new XuLyDon();
		
		xuLyDonHienTai.setGhiChu(note);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
		xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
		don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
		don.setLyDoDinhChi(xuLyDon.getyKienXuLy());
		don.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
		don.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
		Utils.save(donRepo, don, congChucId);
		
		return xuLyDonHienTai;
	}
	
	// van thu trinh lanh dao
	public static XuLyDon trinhDon(XuLyDon xuLyDon, Long coQuanQuanLyId, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		
		note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase() + " "
				+ coQuanQuanLyRepo.findOne(coQuanQuanLyId).getTen().toLowerCase().trim() + " ";
		
		xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
				xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
		xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(
				xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		// xuLyDonTiepTheo.setThoiHanXuLy();
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}
	
	
	//van thu >> ket thuc : chuyenDon
	public static XuLyDon vanThuChuyenDon(XuLyDon xuLyDon, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(huongXuLyXLD);
		
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	
	// 
	public static XuLyDon deXuatThuLy(XuLyDon xuLyDon, Long congChucId) { 
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Utils.save(donRepo, don, congChucId);
		
		return xuLyDonHienTai;
	}
	
	
	//ChuyenVien >> DeXuatGiaoViecLai
	public static XuLyDon chuyenVienGiaoViecLai(XuLyDon xuLyDon, Long congChucId, String note) { 
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		
		note = note + VaiTroEnum.TRUONG_PHONG.getText().toLowerCase().trim() + " "
				+ xuLyDonHienTai.getPhongBanXuLy().getTen().trim() + " ";
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCongChuc());
		
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);	
		return xuLyDonTiepTheo;
	}
	
	/*public XuLyDon chuyenVienChuyenDon(XuLyDon xuLyDon, XuLyDon xuLyDonHienTai, Long congChucId, String note) {
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
		// huongXuLy
		note = note + huongXuLyXLD.getText().toLowerCase().trim() + " ";
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU);
		xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		if (huongXuLyXLD.equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {

			CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
			note = note + VaiTroEnum.LANH_DAO.getText() + coQuanQuanLy.getTen().toLowerCase().trim();
			xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
			xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
			xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
			xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
			Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
			don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
			xuLyDonHienTai.setGhiChu(note);
			Utils.save(donRepo, don, congChucId);
			Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {

			note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
					+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
			xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
			xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
			xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
			xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
			xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
			xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
			xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
			xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
			xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
			xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			if (xuLyDonHienTai.isDonChuyen()) {

				note = note + "đơn chuyển từ "
						+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
				xuLyDonTiepTheo.setDonChuyen(true);
				xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			}
			xuLyDonHienTai.setGhiChu(note);
			Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.CHUYEN_DON)) {

			note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
					+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
			xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
			xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
			xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
			xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
			xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
			xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
			xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
			xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
			xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
			xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
			xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			if (xuLyDonHienTai.isDonChuyen()) {

				note = note + "đơn chuyển từ "
						+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
				xuLyDonTiepTheo.setDonChuyen(true);
				xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
			}
			xuLyDonHienTai.setGhiChu(note);
			Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
//			return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
		}
		
		return xuLyDonHienTai;
	}*/
	
	public static XuLyDon truongPhongGiaoViecLai(XuLyDon xuLyDon, Long congChucId, String note) { 
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		
		CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
		note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase().trim() + " "
				+ coQuanQuanLy.getTen().toLowerCase().trim() + " ";
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setPhongBanXuLy(coQuanQuanLy);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		return xuLyDonTiepTheo;
	}
	
	public static XuLyDon truongPhongGiaoViec(XuLyDon xuLyDon, Long congChucId, String note) { 
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		
		note = note + VaiTroEnum.CHUYEN_VIEN.getText().toLowerCase().trim() + " "
				+ xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
		xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
		xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
		
		return xuLyDonTiepTheo;
	}
		
	
	public static XuLyDon chuyenVienChuyenChoVanThuYeuCauGapLanhDao(XuLyDon xuLyDon, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
		note = note + VaiTroEnum.LANH_DAO.getText() + coQuanQuanLy.getTen().toLowerCase().trim();
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
		xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
		don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		
		xuLyDonHienTai.setGhiChu(note);
		Utils.save(donRepo, don, congChucId);
		return xuLyDonHienTai;
	}
	
	public static XuLyDon chuyenVienChuyenChoVanThuDeXuatThuLy(XuLyDon xuLyDon, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.DE_XUAT_THU_LY);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
				+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(HuongXuLyXLDEnum.DE_XUAT_THU_LY);
		xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public static XuLyDon chuyenVienChuyenDon(XuLyDon xuLyDon, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.CHUYEN_DON);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
				+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(HuongXuLyXLDEnum.CHUYEN_DON);
		xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public static XuLyDon chuyenVienXuLyKhongDuDieuKienThuLy(XuLyDon xuLyDon, Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
				+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Don don = donRepo.findOne(donId);
		don.setCanBoXuLyPhanHeXLD(congChucRepo.findOne(congChucId));
		don.setCurrentState(xuLyDonHienTai.getNextState());
		don.setCurrentForm(xuLyDonHienTai.getNextForm());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public XuLyDon chuyenVienTraDonVaHuongDan(XuLyDon xuLyDon, String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
				+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public XuLyDon chuyenVienLuuDonVaTheoDoi(XuLyDon xuLyDon,  String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
				+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI);
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		if (xuLyDonHienTai.isDonChuyen()) {

			note = note + "đơn chuyển từ "
					+ xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim();
			xuLyDonTiepTheo.setDonChuyen(true);
			xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		}
		xuLyDonHienTai.setGhiChu(note);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public static XuLyDon chuyenVienTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon,Long congChucId, String note) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setNextState(xuLyDon.getNextState());
		xuLyDonHienTai.setHuongXuLy(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
		+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
		//
		xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
		xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
		//
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
		xuLyDonTiepTheo.setHuongXuLy(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
			
		note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
		xuLyDonTiepTheo.setDonChuyen(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonHienTai.setGhiChu(note);
		
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	public XuLyDon vanThuTraDonVaHuongDan(XuLyDon xuLyDon, String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN);
		
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
		Utils.save(donRepo, don, congChucId);
		
		return xuLyDonHienTai;
	}
	
	public XuLyDon vanThuLuuVaTheoDoi(XuLyDon xuLyDon, String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI);
		
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
		Utils.save(donRepo, don, congChucId);
		
		return xuLyDonHienTai;
	}
	
	public XuLyDon vanThuKhongDuDieuKienThuLy(XuLyDon xuLyDon, String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY);
		
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		don.setTrangThaiDon(TrangThaiDonEnum.DINH_CHI);
		Utils.save(donRepo, don, congChucId);
		
		return xuLyDonHienTai;
	}
	
	public static XuLyDon vanThuTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		don.setHuongXuLyXLD(HuongXuLyXLDEnum.TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN);
		
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonTiepTheo;
	}
	
	/*public XuLyDon vanThuTraLaiDonKhongDungThamQuyen(XuLyDon xuLyDon, String note, Long congChucId) {
		Long donId = xuLyDon.getDon().getId();
		XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(xuLyDonRepo, donId);
		XuLyDon xuLyDonTiepTheo = new XuLyDon();
		
		xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
		xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
		Don don = donRepo.findOne(donService.predicateFindOne(xuLyDonHienTai.getDon().getId()));
		HuongXuLyXLDEnum huongXuLyXLD = xuLyDonHienTai.getHuongXuLy();
		don.setHuongXuLyXLD(huongXuLyXLD);
		
		xuLyDonTiepTheo.setDon(don);
		xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getCoQuanChuyenDon());
		xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
		xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
		xuLyDonTiepTheo.setDaXoa(true);
		xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getPhongBanXuLy());
		don.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
		Utils.save(donRepo, don, congChucId);
		Utils.save(xuLyDonRepo, xuLyDonHienTai, congChucId);
		
		return xuLyDonHienTai;
	}*/
	
}
