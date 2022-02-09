package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //view를 리턴하겠음
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 : 템플릿엔진 
		// 머스테치 기본폴더 : src/main/resources
		// 뷰리졸버 설정 : templates (prefix), .mustach (suffix) - 생략가능 //yml설정에 잡지 않아도 pom.xml에 의존성을 설정해놓으면 알아서 뷰리졸버 설정을 잡는다.
		return "index"; // src/main/resources/index.mustache로 잡히게 될거임 이걸 config에서 설정을 바꾸겠음
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	//스프링 시큐리티가 해당 주소를 낚아채버려서 login으로 옮겨지지 않음
	//SecurityConfig 파일을 만든 후 해당 메서드가 동작함
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPwd = user.getPassword();
		String encPwd = bCryptPasswordEncoder.encode(rawPwd);
		user.setPassword(encPwd);
		userRepository.save(user); //회원가입은 잘되지만 비밀번호가 암호화되어 저장되지 않음
		return "redirect:/loginForm";
	} 
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨";
	}
}
