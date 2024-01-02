package org.kosta.starducks.commons.notify.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.hr.entity.Employee;

// Auditable: 엔터티(Entity)가 생성되거나 수정될 때, 해당 이벤트에 대한 정보(예: 생성자, 수정자, 생성일자, 수정일자)를 기록하기 위해 사용
// 직접 정의한 BaseTimeEntity, BaseUserEntity와 같은 역할,,
@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Notify extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String content;

    private String url;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  // Employee가 삭제될 때 해당 Notify도 삭제됨
    private Employee receiver;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public enum NotificationType {
        DOCUMENT, CHAT, POST, SCHEDULE
    }

}