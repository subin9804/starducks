package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfRoom is a Querydsl query type for ConfRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfRoom extends EntityPathBase<ConfRoom> {

    private static final long serialVersionUID = -11132199L;

    public static final QConfRoom confRoom = new QConfRoom("confRoom");

    public final NumberPath<Long> bookerId = createNumber("bookerId", Long.class);

    public final StringPath bookerNm = createString("bookerNm");

    public final StringPath color = createString("color");

    public final NumberPath<Long> confId = createNumber("confId", Long.class);

    public final StringPath confName = createString("confName");

    public final StringPath dept = createString("dept");

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final DateTimePath<java.time.LocalDateTime> recordDay = createDateTime("recordDay", java.time.LocalDateTime.class);

    public final EnumPath<ConfRoomEN> room = createEnum("room", ConfRoomEN.class);

    public final DatePath<java.time.LocalDate> runningDay = createDate("runningDay", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public final StringPath status = createString("status");

    public final StringPath text = createString("text");

    public QConfRoom(String variable) {
        super(ConfRoom.class, forVariable(variable));
    }

    public QConfRoom(Path<? extends ConfRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfRoom(PathMetadata metadata) {
        super(ConfRoom.class, metadata);
    }

}

