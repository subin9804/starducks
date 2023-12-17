package org.kosta.starducks.commons;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUserEntity {

    @CreatedBy  // 생성한 유저
    @Column(updatable = false)  // 수정 불가
    private String createdBy;

    @LastModifiedBy // 마지막으로 수정한 유저
    @Column(insertable = false) // 생성 불가
    private String modifiedBy;

}
