package org.kosta.starducks.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendance is a Querydsl query type for Attendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendance extends EntityPathBase<Attendance> {

    private static final long serialVersionUID = -391951069L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendance attendance = new QAttendance("attendance");

    public final org.kosta.starducks.hr.entity.QEmployee emp;

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final BooleanPath isVacation = createBoolean("isVacation");

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> workDate = createDate("workDate", java.time.LocalDate.class);

    public final NumberPath<Long> workId = createNumber("workId", Long.class);

    public QAttendance(String variable) {
        this(Attendance.class, forVariable(variable), INITS);
    }

    public QAttendance(Path<? extends Attendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendance(PathMetadata metadata, PathInits inits) {
        this(Attendance.class, metadata, inits);
    }

    public QAttendance(Class<? extends Attendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.emp = inits.isInitialized("emp") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("emp"), inits.get("emp")) : null;
    }

}

