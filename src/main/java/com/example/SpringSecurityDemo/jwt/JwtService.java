package com.example.SpringSecurityDemo.jwt;


import com.example.SpringSecurityDemo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String SUPER_KEY = "53A73E5F1C4E0A2D3B5F2D784E6A1B423D6F247D1F6E5C3A596D635A75327855";

    public String extractUsername(String token){
        return extractClaim(token, Claims :: getSubject);
    }


    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(setSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractTokenExpiration(token).before(new Date());
    }

    private Date extractTokenExpiration(String token){
        return extractClaim(token, Claims :: getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(setSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key setSigningKey(){

        byte[] bytes = Decoders.BASE64.decode(SUPER_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }















//
//    @Value("${token.signing.key}")
//    private String jwtSigningKey;
//
//    public String extractUsername(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//
//    /**
//     * Генерация токена
//     *
//     * @param userDetails данные пользователя
//     * @return токен
//     */
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        if (userDetails instanceof User customUserDetails) {
//            claims.put("id", customUserDetails.getId());
//            claims.put("email", customUserDetails.getEmail());
//            claims.put("role", customUserDetails.getRole());
//        }
//        return generateToken(claims, userDetails);
//    }
//
//    /**
//     * Проверка токена на валидность
//     *
//     * @param token       токен
//     * @param userDetails данные пользователя
//     * @return true, если токен валиден
//     */
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String userName = extractUsername(token);
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    /**
//     * Извлечение данных из токена
//     *
//     * @param token           токен
//     * @param claimsResolvers функция извлечения данных
//     * @param <T>             тип данных
//     * @return данные
//     */
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolvers.apply(claims);
//    }
//
//    /**
//     * Генерация токена
//     *
//     * @param extraClaims дополнительные данные
//     * @param userDetails данные пользователя
//     * @return токен
//     */
//    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    /**
//     * Проверка токена на просроченность
//     *
//     * @param token токен
//     * @return true, если токен просрочен
//     */
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    /**
//     * Извлечение даты истечения токена
//     *
//     * @param token токен
//     * @return дата истечения
//     */
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    /**
//     * Извлечение всех данных из токена
//     *
//     * @param token токен
//     * @return данные
//     */
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
//                .getBody();
//    }
//
//    /**
//     * Получение ключа для подписи токена
//     *
//     * @return ключ
//     */
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}

