package vn.greenglobal.tttp.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vn.greenglobal.tttp.enums.LoaiBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;
import vn.greenglobal.tttp.model.BaoCaoDonVi;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.BaoCaoTongHop;
import vn.greenglobal.tttp.model.BaoCaoTongHopChiTiet;
import vn.greenglobal.tttp.model.CauHinhBaoCao;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QCauHinhBaoCao;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietRepository;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
import vn.greenglobal.tttp.repository.BaoCaoDonViRepository;
import vn.greenglobal.tttp.repository.BaoCaoTongHopChiTietRepository;
import vn.greenglobal.tttp.repository.BaoCaoTongHopRepository;
import vn.greenglobal.tttp.repository.CauHinhBaoCaoRepository;
import vn.greenglobal.tttp.service.CauHinhBaoCaoService;

@Component
public class ScheduledTasks {
	
	@Autowired
	BaoCaoTongHopRepository baoCaoTongHopRepo;
	
	@Autowired
	BaoCaoTongHopChiTietRepository baoCaoTongHopChiTietRepo;
	
	@Autowired
	BaoCaoDonViRepository baoCaoDonViRepo;
	
	@Autowired
	BaoCaoDonViChiTietTamRepository baoCaoDonViChiTietTamRepo;
	
	@Autowired
	BaoCaoDonViChiTietRepository baoCaoDonViChiTietRepo;
	
	@Autowired
	CauHinhBaoCaoRepository cauHinhBaoCaoRepository;
	
	@Autowired
	CauHinhBaoCaoService cauHinhBaoCaoService;

	//second, minute, hour, day of month, month, day(s) of week
	@Scheduled(cron = "0 25 10 * * *")
	public void updateHinhThucXuLyQuanLy() throws Exception {		
		List<CauHinhBaoCao> list = (List<CauHinhBaoCao>) cauHinhBaoCaoRepository.findAll(QCauHinhBaoCao.cauHinhBaoCao.daXoa.eq(false)
				.and(QCauHinhBaoCao.cauHinhBaoCao.daTuDongGui.eq(false))
				.and(QCauHinhBaoCao.cauHinhBaoCao.ngayGuiBaoCao.before(LocalDateTime.now())));
		for (CauHinhBaoCao cauHinh : list) {
			Long nguoiTaoId = cauHinh.getNguoiTao().getId();
			//Tao bao cao tong hop
			BaoCaoTongHop baoCaoTongHop = new BaoCaoTongHop();
			baoCaoTongHop.setTenBaoCao(cauHinh.getTenBaoCao());
			baoCaoTongHop.setTenBaoCaoSearch(cauHinh.getTenBaoCaoSearch());
			baoCaoTongHop.setNamBaoCao(cauHinh.getNamBaoCao());
			baoCaoTongHop.setDanhSachBaoCao(cauHinh.getDanhSachBaoCao());
			baoCaoTongHop.setNgayBatDauBC(cauHinh.getNgayBatDauBC());
			baoCaoTongHop.setNgayKetThucBC(cauHinh.getNgayKetThucBC());
			baoCaoTongHop.setDonVi(cauHinh.getDonViGui());
			baoCaoTongHop.setKyBaoCao(cauHinh.getKyBaoCao());
			baoCaoTongHop.setThangBaoCao(cauHinh.getThangBaoCao());
			baoCaoTongHop.setQuyBaoCao(cauHinh.getQuyBaoCao());
			baoCaoTongHop = Utils.save(baoCaoTongHopRepo, baoCaoTongHop, nguoiTaoId);
			String[] danhSachBaoCao = cauHinh.getDanhSachBaoCao().split(";");
			for (String baoCao : danhSachBaoCao) {
				LoaiBaoCaoTongHopEnum loaiBaoCao = LoaiBaoCaoTongHopEnum.valueOf(baoCao.trim());
				//Tao bao cao tong hop chi tiet
				BaoCaoTongHopChiTiet baoCaoTongHopChiTiet = new BaoCaoTongHopChiTiet();
				baoCaoTongHopChiTiet.setBaoCaoTongHop(baoCaoTongHop);
				baoCaoTongHopChiTiet.setLoaiBaoCao(loaiBaoCao);
				baoCaoTongHopChiTiet.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
				Utils.save(baoCaoTongHopChiTietRepo, baoCaoTongHopChiTiet, nguoiTaoId);
			}
			
			for (CoQuanQuanLy coQuan : cauHinh.getDonViNhans()) {
				//Tao bao cao don vi
				BaoCaoDonVi baoCaoDonVi = new BaoCaoDonVi();
				baoCaoDonVi.setDonVi(coQuan);
				baoCaoDonVi.setBaoCaoTongHop(baoCaoTongHop);
				baoCaoDonVi.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
				baoCaoDonVi = Utils.save(baoCaoDonViRepo, baoCaoDonVi, nguoiTaoId);
				for (String baoCao : danhSachBaoCao) {
					LoaiBaoCaoTongHopEnum loaiBaoCao = LoaiBaoCaoTongHopEnum.valueOf(baoCao.trim());
						
					//Tao bao cao don vi chi tiet TONG HOP;
					BaoCaoDonViChiTiet baoCaoDonViChiTietTongHop = new BaoCaoDonViChiTiet();
					baoCaoDonViChiTietTongHop.setBaoCaoDonVi(baoCaoDonVi);
					baoCaoDonViChiTietTongHop.setLoaiBaoCao(loaiBaoCao);
					baoCaoDonViChiTietTongHop.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
					baoCaoDonViChiTietTongHop = Utils.save(baoCaoDonViChiTietRepo, baoCaoDonViChiTietTongHop, nguoiTaoId);
					
					//Tao bao cao don vi chi tiet
					BaoCaoDonViChiTiet baoCaoDonViChiTiet = new BaoCaoDonViChiTiet();
					baoCaoDonViChiTiet.setBaoCaoDonVi(baoCaoDonVi);
					baoCaoDonViChiTiet.setLoaiBaoCao(loaiBaoCao);
					baoCaoDonViChiTiet.setCha(baoCaoDonViChiTietTongHop);
					baoCaoDonViChiTiet.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
					baoCaoDonViChiTiet = Utils.save(baoCaoDonViChiTietRepo, baoCaoDonViChiTiet, nguoiTaoId);
					
					//Tao bao cao don vi chi tiet tam
					BaoCaoDonViChiTietTam baoCaoDonViChiTietTam = new BaoCaoDonViChiTietTam();
					baoCaoDonViChiTietTam.setBaoCaoDonViChiTiet(baoCaoDonViChiTiet);
					baoCaoDonViChiTietTam.setLoaiBaoCao(loaiBaoCao);
					Utils.save(baoCaoDonViChiTietTamRepo, baoCaoDonViChiTietTam, nguoiTaoId);
					
				}
			}
			
			
			cauHinh.setDaTuDongGui(true);
			Utils.save(cauHinhBaoCaoRepository, cauHinh, nguoiTaoId);
		}
	}
}
