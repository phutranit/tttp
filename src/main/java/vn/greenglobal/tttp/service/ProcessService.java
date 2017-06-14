package vn.greenglobal.tttp.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.QProcess;

@Component
public class ProcessService {

	BooleanExpression base = QProcess.process.daXoa.eq(false);

	public Predicate predicateFindAll(String vaiTro, CoQuanQuanLy donVi, boolean isOwner, ProcessTypeEnum processType) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QProcess.process.vaiTro.loaiVaiTro.eq(VaiTroEnum.valueOf(StringUtils.upperCase(vaiTro))))
			.and(QProcess.process.coQuanQuanLy.eq(donVi))
			.and(QProcess.process.owner.eq(isOwner))
			.and(QProcess.process.processType.eq(processType));
		return predAll;
	}
	
	public Predicate predicateFindAllByDonVi(CoQuanQuanLy donVi, ProcessTypeEnum processType) {
		BooleanExpression predAll = base;
		predAll = predAll
			.and(QProcess.process.coQuanQuanLy.eq(donVi))
			.and(QProcess.process.processType.eq(processType));
		return predAll;
	}

}
