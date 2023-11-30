package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = -128236719L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final org.kosta.starducks.hr.entity.QEmpEntity emp;

    public final EnumPath<org.kosta.starducks.mypage.dto.Location> location = createEnum("location", org.kosta.starducks.mypage.dto.Location.class);

    public final StringPath notes = createString("notes");

    public final EnumPath<org.kosta.starducks.mypage.dto.ScheduleType> scheduleType = createEnum("scheduleType", org.kosta.starducks.mypage.dto.ScheduleType.class);

    public final DateTimePath<java.time.LocalDateTime> scheEndDate = createDateTime("scheEndDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheNo = createNumber("scheNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> scheStartDate = createDateTime("scheStartDate", java.time.LocalDateTime.class);

    public final StringPath scheTitle = createString("scheTitle");

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.emp = inits.isInitialized("emp") ? new org.kosta.starducks.hr.entity.QEmpEntity(forProperty("emp")) : null;
    }

}
