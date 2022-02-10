package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//시큐리티 설정에서 loginProcessingUrl("/login")
// 동작 방식 : /login 요청이 오면, 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	//로그인시 name-attribute에 있는 이름값이 "username"으로 기본값으로 셋팅되어 있다.
	//다른 username이 아닌 다른 이름으로 설정하고 싶다면, SecurityConfig에 usernameParameter()메서드에 바꾸고 싶은 이름을 설정해야 한다.
	// 시큐리티 Session <- Authentication <- UserDetails = PrincipalDetails <- User Object
	// 시큐리티 세션 <- Authentication(UserDetails)
	// SecuritySession(Authentication) 요런 형태
	// 즉 SecuritySession 내부에는 Authentication 객체를 품고 있고, 
	// 그 Authentication 객체는 UserDetails를 품어야하는데 이는 UserDetails = PrincipalDetails(얘가 상속받았으므로) 이고
	// 그 PrincipalDetails는 User 객체의 정보를 품고 있다.
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username : "+username);
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
