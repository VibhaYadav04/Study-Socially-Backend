package com.blog.security;

import java.awt.RenderingHints.Key;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtTokenHelper {
	
	
	
	private static final String  secretKey ="72bde9664b709af6f66b9b3cc475aeb6dbf89445e8263436ca881c40af79b623" ;
	private static final long EXPIRATION_TIME = 1000 * 60 * 24;
	
	
	public String generateToken(UserDetails userDetails)
	{
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token)
	{
		return extractClaims(token, Claims::getSubject);
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction)
	{
		final Claims claims = extractAllClaims(token);
		return claimsTFunction.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private SecretKey getSignInKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails)
	{
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public boolean isTokenExpired(String token) 
	{
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private String secret = "jwtTokenKey";

	// Retrieve username from jwt token
	public String getUserNameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {

		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	private Key getSigninKey() {
		byte[] key = Decoders.BASE64.decode(secret);
		return (Key) Keys.hmacShaKeyFor(key);
	}

	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
	}*/

	// check if the token has expired
//    private Boolean isTokenExpired(String token){
//
//        final Date expiration = getExpirationFromToken(token);
//        return expiration.before(new Date());
//    }

//    public String generateToken(UserDetails userDetails){
//
//        Map<String,Object> claims = new HashMap<>();
//        return doGenerateToken(claims,userDetails.getUsername());
//
//    }

	/*
	 * public String doGenerateToken(Map<String,Object> claims, String subject){
	 * 
	 * return Jwts.builder() .setClaims(claims) .setSubject(subject)
	 * .setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
	 * Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	 * .signWith(getSigninKey(), SignatureAlgorithm.HS256) .compact(); }
	 */

//    

//    public Boolean validateToken(String token, UserDetails userDetails)
//    {
//        final String username = getUserNameFromToken(token);
//
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//        
//    }
//  
	/*
	 * public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
	 * final Claims claims = extractAllClaims(token); return
	 * claimResolver.apply(claims); }
	 * 
	 * public String extractUserName(String token) { return extractClaim(token,
	 * Claims::getSubject); }
	 * 
	 * private Key getSigninKey() { byte[] key = Decoders.BASE64.decode(
	 * "jwtTokenKey"); return (Key) Keys.hmacShaKeyFor(key); }
	 * 
	 * private Claims extractAllClaims(String token) { return
	 * Jwts.builder().setSigningKey(getSigninKey().build().parseClaimsJws(token).
	 * getBody()); }
	 * 
	 * public Boolean validateToken(String token, UserDetails userDetails) { final
	 * String username = extractUserName(token);
	 * 
	 * return (username.equals(userDetails.getUsername()) &&
	 * !isTokenExpired(token));
	 * 
	 * } //check if the token has expired private Boolean isTokenExpired(String
	 * token){ return extractClaim(token, Claims::getExpiration).before(new Date());
	 * }
	 */
}
