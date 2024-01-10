package org.kosta.starducks.header.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1091637599L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final org.kosta.starducks.commons.QBaseTimeEntity _super = new org.kosta.starducks.commons.QBaseTimeEntity(this);

    public final ListPath<ChatMessage, QChatMessage> chatMessageList = this.<ChatMessage, QChatMessage>createList("chatMessageList", ChatMessage.class, QChatMessage.class, PathInits.DIRECT2);

    public final ListPath<ChatRoomEmp, QChatRoomEmp> chatRoomEmps = this.<ChatRoomEmp, QChatRoomEmp>createList("chatRoomEmps", ChatRoomEmp.class, QChatRoomEmp.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath roomName = createString("roomName");

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

