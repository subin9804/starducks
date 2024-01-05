package org.kosta.starducks.header.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEmp is a Querydsl query type for ChatRoomEmp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEmp extends EntityPathBase<ChatRoomEmp> {

    private static final long serialVersionUID = -516583703L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomEmp chatRoomEmp = new QChatRoomEmp("chatRoomEmp");

    public final QChatRoom chatRoom;

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChatRoomEmp(String variable) {
        this(ChatRoomEmp.class, forVariable(variable), INITS);
    }

    public QChatRoomEmp(Path<? extends ChatRoomEmp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomEmp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomEmp(PathMetadata metadata, PathInits inits) {
        this(ChatRoomEmp.class, metadata, inits);
    }

    public QChatRoomEmp(Class<? extends ChatRoomEmp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom")) : null;
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

