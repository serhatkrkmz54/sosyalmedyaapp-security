package com.sosyalmedyaapp2.sosyalmedyaapp2.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name-user}")
    private String bucketNameUser;

    @Value("${minio.bucket-name-post}")
    private String bucketNamePost;

    public MinioService(@Value("${minio.url}") String minioUrl,
                        @Value("${minio.access-key-user}") String accessKeyUser,
                        @Value("${minio.secret-key-user}") String secretKeyUser,
                        @Value("${minio.access-key-post}") String accessKeyPost,
                        @Value("${minio.secret-key-post}") String secretKeyPost) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKeyUser,secretKeyUser)
                .build();
    }

    public String uploadProfilePic(MultipartFile file, String email){
        String objectName = "profile-pictures-"+email+"-"+file.getOriginalFilename();
        try(InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketNameUser)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Profil fotoğrafı yükleme hatası: " +e.getMessage());
        }
    }

    public String uploadPostPic(MultipartFile postFile, String email) {
        String postObjectName = "post-pictures-"+email+"-"+postFile.getOriginalFilename();
        try(InputStream inputStream = postFile.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketNamePost)
                            .object(postObjectName)
                            .stream(inputStream, postFile.getSize(), -1)
                            .contentType(postFile.getContentType())
                            .build());

        return postObjectName;
        }catch (Exception e) {
            throw new RuntimeException("Post Resim yükleme hatası: "+e.getMessage());
        }
    }
}
