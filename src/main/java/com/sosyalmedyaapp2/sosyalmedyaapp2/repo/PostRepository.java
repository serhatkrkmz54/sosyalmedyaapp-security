package com.sosyalmedyaapp2.sosyalmedyaapp2.repo;

import com.sosyalmedyaapp2.sosyalmedyaapp2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
