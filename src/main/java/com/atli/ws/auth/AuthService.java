package com.atli.ws.auth;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.atli.ws.user.User;
import com.atli.ws.user.UserRepository;
import com.atli.ws.user.vm.UserVM;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {
	
	UserRepository userRepository;
	
	PasswordEncoder passwordEncoder;
	
	TokenRepository tokenRepository;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,TokenRepository tokenRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenRepository = tokenRepository; 
	}

	public AuthResponse authenticate(Credentials credentials) {
		User inDB = userRepository.findByUsername(credentials.getUsername());
		if(inDB == null) {
			throw new AuthException();
		}
		
		boolean mathes = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());
		if(!mathes) {
			throw new AuthException();
		}
		if(mathes) {
			UserVM userVM = new UserVM(inDB);
			//String token = Jwts.builder().setSubject(""+inDB.getId()).signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
			String token = generateRandomToken();
			
			Token tokenEntity = new Token();
			tokenEntity.setToken(token);
			tokenEntity.setUser(inDB);
			tokenRepository.save(tokenEntity);
			
			AuthResponse response = new AuthResponse();
			response.setUser(userVM);
			response.setToken(token);
			return response;
		}

		return null;
	}

	@Transactional
	public UserDetails getUserDetails(String token) {
		Optional<Token> optionalToken = tokenRepository.findById(token);
		if(!optionalToken.isPresent()) {
			return null;
		}
		return optionalToken.get().getUser();
		
		/* JsonWebToken kısmı
		JwtParser parser = Jwts.parser().setSigningKey("my-app-secret");
		try {
			parser.parse(token);
			Claims claims = parser.parseClaimsJws(token).getBody();
			long userId = new Long(claims.getSubject());
			User inDB = userRepository.getOne(userId);
			User actualUser = (User)((HibernateProxy)inDB).getHibernateLazyInitializer().getImplementation();
			return actualUser; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		*/
	}
	
	public String generateRandomToken(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public void clearToken(String token) {
		tokenRepository.deleteById(token);
	}

}
