package me.parkinje.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , updatable = false)
    private Long id;

    @Column(name="title" , nullable = false)
    private String title;


    @Column(name="content" , nullable = false)
    private String content;

    @JsonIgnore
    @OneToMany (mappedBy = "article")
    private List<Reply> replyList = new ArrayList<>();

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "author",nullable = false)
    private String author;

    @Builder
    public Article(String author,String title,String content,List<Reply> replyList){
        this.title=title;
        this.content=content;
        this.author=author;
        this.replyList=replyList;
    }

    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }

    public void replyUpdate(List<Reply> replyList){
        this.replyList=replyList;
    }

}
