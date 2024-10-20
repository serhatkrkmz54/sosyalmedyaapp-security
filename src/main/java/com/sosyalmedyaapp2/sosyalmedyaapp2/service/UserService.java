package com.sosyalmedyaapp2.sosyalmedyaapp2.service;

import com.sosyalmedyaapp2.sosyalmedyaapp2.enums.AuthProvider;
import com.sosyalmedyaapp2.sosyalmedyaapp2.model.Post;
import com.sosyalmedyaapp2.sosyalmedyaapp2.model.User;
import com.sosyalmedyaapp2.sosyalmedyaapp2.repo.UserRepository;
import com.sosyalmedyaapp2.sosyalmedyaapp2.response.LoginLocalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MinioService minioService;

    public User registerUserForLocal(User user, MultipartFile profilePictureFile) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthprovide(AuthProvider.LOCAL);
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            String profilePicPath = minioService.uploadProfilePic(profilePictureFile, user.getEmail());
            user.setProfilePicture(profilePicPath);
        }
        return userRepository.save(user);
    }
    public User loginUserForLocal(LoginLocalResponse loginLocalResponse) {
        User kullaniciKontrol = userRepository.findByEmail(loginLocalResponse.getEmail()).orElse(null);
        if(kullaniciKontrol!=null) {
            if(!passwordEncoder.matches(loginLocalResponse.getPassword(),kullaniciKontrol.getPassword())){
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

    public List<Post> tumPostlariGetirEmail(String email) {
        return userRepository.tumPostlarıGorEmail(email);
    }

    public List<Post> tumPostlariGetirID(Long userId) {
        return userRepository.tumPostlariGorID(userId);
    }

}
