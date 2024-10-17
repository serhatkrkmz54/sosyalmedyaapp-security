package com.sosyalmedyaapp2.sosyalmedyaapp2.service;

import com.sosyalmedyaapp2.sosyalmedyaapp2.enums.AuthProvider;
import com.sosyalmedyaapp2.sosyalmedyaapp2.model.User;
import com.sosyalmedyaapp2.sosyalmedyaapp2.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public User registerUserForLocal(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthprovide(AuthProvider.LOCAL);
        return userRepository.save(user);
    }
    public User loginUserForLocal(User user) {
        User kullaniciKontrol = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(kullaniciKontrol!=null) {
            if(!passwordEncoder.matches(user.getPassword(),kullaniciKontrol.getPassword())){
                throw new RuntimeException("Kullanıcı parolası eşleşmiyor!");
            }
            return kullaniciKontrol;
        }
        throw new RuntimeException("Kullanıcı bulunamadı!");
    }

    public User loginUserForGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        log.info("Kullanıcı Email Googleden {}",email);
        log.info("Kullanıcı Name Googleden {}",name);

        User user = userRepository.findByEmail(email).orElse(null);
        if(user==null) {
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAuthprovide(AuthProvider.GOOGLE);
            return userRepository.save(user);
        }
        return user;
    }

}
