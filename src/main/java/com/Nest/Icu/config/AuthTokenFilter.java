package com.Nest.Icu.config;

import com.Nest.Icu.service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailService ud;
	
	@Autowired
	private JwtTokenUtility jh;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			token = requestToken.substring(7);
			try {
				username = this.jh.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e) {
				System.out.println("unalble to get jwt");
			}
			catch(ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
			}
			catch(MalformedJwtException e) {
				System.out.println("invalid jwt");
			}
		}
		else {
			System.out.println("JWT Token does not begin with bearer");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails u = this.ud.loadUserByUsername(username);
			if(this.jh.validateToken(token, u)) {
				
				UsernamePasswordAuthenticationToken ut = new UsernamePasswordAuthenticationToken(ud,null,u.getAuthorities());
				ut.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(ut );
			}
			else {
				System.out.println("Invalid Jwt");
			}
		}
		else {
			System.out.println("username is null or context is none ");
		}
		
		filterChain.doFilter(request, response);
	}

	
}
