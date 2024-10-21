package com.sosyalmedyaapp2.sosyalmedyaapp2.service;

import com.sosyalmedyaapp2.sosyalmedyaapp2.model.Post;
import com.sosyalmedyaapp2.sosyalmedyaapp2.model.User;
import com.sosyalmedyaapp2.sosyalmedyaapp2.repo.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MinioService minioService;

    public PostService(PostRepository postRepository, MinioService minioService) {
        this.postRepository = postRepository;
        this.minioService = minioService;
    }

    public Post postAc(Post post, MultipartFile postPictureFile) {
        if(postPictureFile != null && !postPictureFile.isEmpty()){
            String postPicturePath = minioService.uploadPostPic(postPictureFile,post.getUser().getEmail());
            post.setPostPicture(postPicturePath);
        }
        post.setUser(new User());
        return postRepository.save(post);
    }
}
