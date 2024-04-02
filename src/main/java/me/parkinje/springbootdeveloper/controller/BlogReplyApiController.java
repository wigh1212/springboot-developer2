package me.parkinje.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkinje.springbootdeveloper.domain.Article;
import me.parkinje.springbootdeveloper.domain.Reply;
import me.parkinje.springbootdeveloper.dto.AddArticleReplyRequest;
import me.parkinje.springbootdeveloper.dto.AddArticleRequest;
import me.parkinje.springbootdeveloper.service.BlogService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogReplyApiController {
    private final BlogService blogService;

    @PostMapping("/api/reply/{id}")
    //@RequestBody 로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticleReply(@PathVariable long id,@RequestBody AddArticleReplyRequest request, Principal principal){
        System.out.println("id 값 확인 :"+id);
        Article article=blogService.findById(id);

        List<Reply> saveReplyList=blogService.save(request.toEntity(principal.getName(),article),id).getReplyList();

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }
}
