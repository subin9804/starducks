package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendar is a Querydsl query type for Calendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendar extends EntityPathBase<Calendar> {

    private static final long serialVersionUID = 391359480L;

    public static final QCalendar calendar = new QCalendar("calendar");

    public final NumberPath<Long> empId = createNumber("empId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> scheCreateTime = createDateTime("scheCreateTime", java.time.LocalDateTime.class);

    public final StringPath scheDetail = createString("scheDetail");

    public final EnumPath<ScheduleCategory> scheduleCategory = createEnum("scheduleCategory", ScheduleCategory.class);

    public final DatePath<java.time.LocalDate> scheEndDate = createDate("scheEndDate", java.time.LocalDate.class);

    public final NumberPath<Long> scheNo = createNumber("scheNo", Long.class);

    public final DatePath<java.time.LocalDate> scheStartDate = createDate("scheStartDate", java.time.LocalDate.class);

    public final StringPath scheTitle = createString("scheTitle");

    public QCalendar(String variable) {
        super(Calendar.class, forVariable(variable));
    }

    public QCalendar(Path<? extends Calendar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendar(PathMetadata metadata) {
        super(Calendar.class, metadata);
    }

}

