package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	//구글로부터 받은 userRequest 데이터에 대한 후처리가 이루어지는 함수
	//코드를 받는 것이 아니라, 엑세스토큰을 받아 엑세스토큰을 통해 사용자프로필정보들을 같이 받아와서 userRequest에 저장된다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("clientRegistration : "+userRequest.getClientRegistration());		//registrationId로 어떤 OAuth로 로그인했는지 확인가능 - google, facebook 등등..
		System.out.println("accessTokenValue : "+userRequest.getAccessToken().getTokenValue());
		
		//구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> 구글로부터 code를 리턴받아서 OAuth-Client 라이브러리가 받음 
		// -> 해당 라이브러리가 코드를 실행시켜 구글로부터 Access Token을 요청 -> 접근토큰을 받은 상태가 userRequest 객체에 담긴 상태이다. 
		// userRequest 정보 -> loadUser메서드를 호출 -> 구글로부터 회원프로필 정보를 받아온다.  
		System.out.println("attributes : "+super.loadUser(userRequest).getAttributes());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		//받아온 회원정보 Attribute로 회원가입을 강제로 진행시키겠음
		return super.loadUser(userRequest);
	}
}
