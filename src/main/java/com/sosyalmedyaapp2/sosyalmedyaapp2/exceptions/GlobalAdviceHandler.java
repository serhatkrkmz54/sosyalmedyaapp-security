package com.sosyalmedyaapp2.sosyalmedyaapp2.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalAdviceHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail hataDetay = null;
        exception.printStackTrace();
        if (exception instanceof BadCredentialsException) {
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),exception.getMessage());
            hataDetay.setProperty("Açıklama", "Email veya parola geçersiz.");
            return hataDetay;
        }
        if (exception instanceof AccountStatusException){
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            hataDetay.setProperty("Açıklama","Kullanıcı kilitlenmiştir.");
        }
        if (exception instanceof AccessDeniedException) {
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            hataDetay.setProperty("Açıklama","Buraya girmek için yetkiniz yok.");
        }
        if (exception instanceof SignatureException) {
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            hataDetay.setProperty("Açıklama","JWT imzası geçersiz.");
        }
        if (exception instanceof ExpiredJwtException) {
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            hataDetay.setProperty("Açıklama","JWT Token süresi dolumu.");
        }
        if (hataDetay==null) {
            hataDetay = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),exception.getMessage());
            hataDetay.setProperty("Açıkalama","Bilinmeyen hata oluştu.");
        }
        return hataDetay;
    }
}
