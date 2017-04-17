package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.User;

@RestResource(path = "users")
public interface UserRepository extends MutableRepo<User>{

	public User findByUsername(String username);
}
