package org.kosta.starducks.commons;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)  // 수정 불가
    private LocalDateTime CreatedAt;   // 생성시각

    @LastModifiedDate
    @Column(insertable = false)     // 생성 불가
    private LocalDateTime modifiedAt;   // 수정시각
}
