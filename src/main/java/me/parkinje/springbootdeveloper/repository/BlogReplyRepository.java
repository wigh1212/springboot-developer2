package me.parkinje.springbootdeveloper.repository;

import me.parkinje.springbootdeveloper.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogReplyRepository extends JpaRepository<Reply,Long> {
    Optional<Reply> findById(Long Id); //email로 사용자 정보를 가져옴
}
