package org.hrabosch.messenger.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hrabosch.messenger.config.properties.JwtTokenProperties;
import org.hrabosch.messenger.entity.JwtToken;
import org.hrabosch.messenger.entity.MongoUserDetails;
import org.hrabosch.messenger.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private JwtTokenProperties jwtTokenProperties;
    private JwtTokenRepository jwtTokenRepository;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(JwtTokenProperties jwtTokenProperties, JwtTokenRepository jwtTokenRepository, UserDetailsService userDetailsService) {
        this.jwtTokenProperties = jwtTokenProperties;
        this.jwtTokenRepository = jwtTokenRepository;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        // TODO use claims for roles

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtTokenProperties.getValidity());

        String token =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtTokenProperties.getSecretKey())
                .compact();

        jwtTokenRepository.save(new JwtToken(token));
        return token;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtTokenProperties.getAuthorizationHeader());
        if (bearerToken != null ) {
            return bearerToken;
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException,IllegalArgumentException{
        Jwts.parser().setSigningKey(jwtTokenProperties.getSecretKey()).parseClaimsJws(token);
        return true;
    }
    public boolean isTokenPresentInDB (String token) {
        return jwtTokenRepository.findById(token).isPresent();
    }

    public UserDetails getUserDetails(String token) {
        String userName =  getUsername(token);
        UserDetails userDetails = new MongoUserDetails(userName);
        return userDetails;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtTokenProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
