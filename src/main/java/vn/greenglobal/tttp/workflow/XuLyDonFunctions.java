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
	XuLyDonRepository xuLyDonRepo;
	
	@Autowired
	DonRepository donRepo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
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
