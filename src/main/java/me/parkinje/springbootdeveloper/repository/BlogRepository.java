package me.parkinje.springbootdeveloper.repository;
import me.parkinje.springbootdeveloper.domain.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface BlogRepository extends JpaRepository<Article,Long> {
    Page<Article> findAll(Pageable pageable);
}
