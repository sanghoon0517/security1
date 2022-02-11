package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //view를 리턴하겠음
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/login") //일반적인 로그인 테스트  - UserDetails
	public @ResponseBody String testLogin(Authentication authentication
			,@AuthenticationPrincipal UserDetails userDetails) {
//			,@AuthenticationPrincipal PrincipalDetails userDetails2) { //DI 의존성 주입 (@AuthenticationPrincipal를 이용해 UserDetails객체 접근가능) 
		//@AuthenticationPrincipal를 통해서 세션정보에 접근할 수 있음. 그ㅐ서 UserDetails에 있는 객체의 정보를 get해올 수 있음
		System.out.println("/test/login=====================");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("User : "+ principalDetails.getUser());
		
		System.out.println("userDetails : "+userDetails.getUsername());
//		System.out.println("userDetails2 : "+userDetails2.getUser()); //PrincipalDetails타입이 UserDetails타입을 상속 받았으므로, 다형성으로 PrincipalDetails로 받을 수 있음 
		return "세션정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login") //구글로그인- OAuth로그인
	public @ResponseBody String testOAuthLogin(
			Authentication authentication ,
			@AuthenticationPrincipal OAuth2User oAuth) { 
		System.out.println("/test/oauth/login=====================");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("User : "+ oAuth2User.getAttributes());
		System.out.println("oAuth2User : "+oAuth.getAttributes());
		
		return "OAuth 세션정보 확인하기";
	}
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 : 템플릿엔진 
		// 머스테치 기본폴더 : src/main/resources
		// 뷰리졸버 설정 : templates (prefix), .mustach (suffix) - 생략가능 //yml설정에 잡지 않아도 pom.xml에 의존성을 설정해놓으면 알아서 뷰리졸버 설정을 잡는다.
		return "index"; // src/main/resources/index.mustache로 잡히게 될거임 이걸 config에서 설정을 바꾸겠음
	}
	
	//OAuth2.0로그인과 일반 로그인 둘 다 가능
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		System.out.println("principalDetails : "+principalDetails.getUser());
		
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
	
	@Secured("ROLE_ADMIN") //권한을 하나만 걸고 싶을때
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //권한을 1개 이상 걸고 싶을때
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
	
}
