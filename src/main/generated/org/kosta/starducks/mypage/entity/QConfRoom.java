package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfRoom is a Querydsl query type for ConfRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfRoom extends EntityPathBase<ConfRoom> {

    private static final long serialVersionUID = -11132199L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfRoom confRoom = new QConfRoom("confRoom");

    public final org.kosta.starducks.hr.entity.QEmployee booker;

    public final NumberPath<Long> confId = createNumber("confId", Long.class);

    public final StringPath confName = createString("confName");

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final DateTimePath<java.time.LocalDateTime> recordDay = createDateTime("recordDay", java.time.LocalDateTime.class);

    public final EnumPath<ConfRoomEN> room = createEnum("room", ConfRoomEN.class);

    public final DatePath<java.time.LocalDate> runningDay = createDate("runningDay", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> StartTime = createTime("StartTime", java.time.LocalTime.class);

    public final StringPath status = createString("status");

    public QConfRoom(String variable) {
        this(ConfRoom.class, forVariable(variable), INITS);
    }

    public QConfRoom(Path<? extends ConfRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfRoom(PathMetadata metadata, PathInits inits) {
        this(ConfRoom.class, metadata, inits);
    }

    public QConfRoom(Class<? extends ConfRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.booker = inits.isInitialized("booker") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("booker"), inits.get("booker")) : null;
    }

}

