package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.User;
import vn.greenglobal.tttp.repository.UserRepository;

@RepositoryRestController
public class UserController extends BaseController<User>{

	public UserController(BaseRepository<User, ?> repo) {
		super(repo);
	}

	@Autowired
	private UserRepository userRepository;
	
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestBody User user, Errors errors) {
		System.out.println("username:"+user.getUsername());
        User u = userRepository.findByUsername(user.getUsername());
        return "login";
    }*/
	
}
