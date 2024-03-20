package me.parkinje.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="replyid" , updatable = false)
    private Long replyid;

    @Column(name="id" , nullable = false)
    private Long id;


    @Column(name="reply" , nullable = true)
    private String reply;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Article article;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public Reply(String reply,Long id) {
        this.reply = reply;
        this.id = id;
    }

}
