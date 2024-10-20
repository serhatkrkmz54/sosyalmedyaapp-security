package com.sosyalmedyaapp2.sosyalmedyaapp2.controller;

import com.sosyalmedyaapp2.sosyalmedyaapp2.model.Post;
import com.sosyalmedyaapp2.sosyalmedyaapp2.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post-ac")
    public ResponseEntity<Post> postAc(@RequestPart @Validated Post post,
                                       @RequestPart(value = "postPicture", required = false)MultipartFile postPictureFile) {
        return ResponseEntity.ok(postService.postAc(post,postPictureFile));
    }
}
