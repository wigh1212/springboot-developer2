package me.parkinje.springbootdeveloper.repository;
import me.parkinje.springbootdeveloper.domain.Article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article,Long> {

}
