package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

import lombok.Data;

/**
 * @author 전상훈
 * 
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인 진행이 완료가 되면 시큐리티 세션을 만들어서 넣어준다. (SecurityContextHolder)
 * 오브젝트-> Authentication 타입의 객체
 * Authentication 안에 User 정보가 있어야 함.
 * User오브젝트타입 -> UserDetails 타입의 객체여야 한다.
 * 
 * Security Session 영역 -> Authentication 객체 -> UserDetails타입의 유저정보. -> User 오브젝트에 접근가능 
 *
 */

@Data
public class PrincipalDetails implements UserDetails{
	
	private User user; //컴포지션
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		//예시) 1년동안 회원이 로그인을 안하면, 휴면 계정으로 하기로 함.
		//현재시간 - 로긴시간 => 1년을 초과하면 false
		
		return true;
	}
}
