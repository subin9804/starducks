package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = -128236719L;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final NumberPath<Long> empId = createNumber("empId", Long.class);

    public final EnumPath<org.kosta.starducks.mypage.dto.Location> location = createEnum("location", org.kosta.starducks.mypage.dto.Location.class);

    public final StringPath notes = createString("notes");

    public final EnumPath<org.kosta.starducks.mypage.dto.ScheduleType> scheduleType = createEnum("scheduleType", org.kosta.starducks.mypage.dto.ScheduleType.class);

    public final DateTimePath<java.time.LocalDateTime> scheEndDate = createDateTime("scheEndDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheNo = createNumber("scheNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> scheStartDate = createDateTime("scheStartDate", java.time.LocalDateTime.class);

    public final StringPath scheTitle = createString("scheTitle");

    public QSchedule(String variable) {
        super(Schedule.class, forVariable(variable));
    }

    public QSchedule(Path<? extends Schedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedule(PathMetadata metadata) {
        super(Schedule.class, metadata);
    }

}

