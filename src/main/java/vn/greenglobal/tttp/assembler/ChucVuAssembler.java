package vn.greenglobal.tttp.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import vn.greenglobal.tttp.model.ChucVu;

public class ChucVuAssembler extends ResourceAssemblerSupport<ChucVu, Resource<ChucVu>> {

	public ChucVuAssembler(Class<?> controllerClass, Class<Resource<ChucVu>> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Resource<ChucVu> toResource(ChucVu entity) {
		// TODO Auto-generated method stub
		return null;
	}


}
