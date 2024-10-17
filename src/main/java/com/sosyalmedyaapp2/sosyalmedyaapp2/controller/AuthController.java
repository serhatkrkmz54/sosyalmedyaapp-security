package com.sosyalmedyaapp2.sosyalmedyaapp2.controller;

import com.sosyalmedyaapp2.sosyalmedyaapp2.model.User;
import com.sosyalmedyaapp2.sosyalmedyaapp2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/kayit-ol")
    public ResponseEntity<User> kayitOl(@RequestBody @Validated User user) {
        return ResponseEntity.ok(userService.registerUserForLocal(user));
    }

    @PostMapping("/giris-yap/local")
    public ResponseEntity<User> lokalGiris(@RequestBody User user) {
    return ResponseEntity.ok(userService.loginUserForLocal(user));
    }

    @GetMapping("/giris-yap/google")
    public ResponseEntity<String> googleGiris(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
        return ResponseEntity.ok("Yönlendiriliyor...");
    }

    @GetMapping("/girisBasarili")
    public ResponseEntity<String > girisBasariliGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) throws IOException{
        User user = userService.loginUserForGoogle(oAuth2AuthenticationToken);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).location(URI.create("http://localhost:3000/anasayfa")).build();
    }
}
