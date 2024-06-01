package me.parkinje.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.parkinje.springbootdeveloper.domain.Article;
import me.parkinje.springbootdeveloper.domain.Reply;
import me.parkinje.springbootdeveloper.dto.AddArticleRequest;
import me.parkinje.springbootdeveloper.dto.ArticleListViewResponse;
import me.parkinje.springbootdeveloper.dto.ArticleResponse;
import me.parkinje.springbootdeveloper.dto.UpdateArticleRequest;
import me.parkinje.springbootdeveloper.repository.BlogReplyRepository;
import me.parkinje.springbootdeveloper.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogReplyRepository replyRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request,String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public Article save(Reply reply, Long id) {

        Article article=findById(id);

        Reply savedReply = replyRepository.save(reply); // 새로운 Reply 저장

        System.out.println(savedReply.getId() + " : "+savedReply.getReply());

        article.getReplyList().add(savedReply);

        return blogRepository.save(article);

    }
    public Page<ArticleResponse> apiFindAll(int page,int size){
        Pageable pageable = PageRequest.of(page, size);

        return (Page<ArticleResponse>) blogRepository.findAll(pageable)
                .map(ArticleResponse::new);
    }

    public Page<ArticleListViewResponse> findAll(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return (Page<ArticleListViewResponse>) blogRepository.findAll(pageable)
                .map(ArticleListViewResponse::new);
    }

    public Article findById(long id){

        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : "+id));
    }

    public void delete(long id){

        Article article=blogRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found : "+id));

        authorizeArticleAuthor(article);

        article.getReplyList().stream().forEach(replyRepository::delete);

        article.getReplyList().clear();

        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : "+id));

        authorizeArticleAuthor(article);

        article.update(request.getTitle(),request.getContent());

        return article;
    }


    //게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
