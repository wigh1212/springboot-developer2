package me.parkinje.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.parkinje.springbootdeveloper.domain.Article;
import me.parkinje.springbootdeveloper.domain.Reply;

@Getter
@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 추가
public class AddArticleReplyRequest {

    private String reply;
    private Article article;
    public Reply toEntity(String userName,Article article){ //생성자를 사용해 객체 생성

        return Reply.builder()
                .userName(userName)
                .reply(reply)
                .article(article)
                .build();
    }
}
