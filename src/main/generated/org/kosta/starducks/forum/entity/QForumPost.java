package org.kosta.starducks.forum.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QForumPost is a Querydsl query type for ForumPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QForumPost extends EntityPathBase<ForumPost> {

    private static final long serialVersionUID = -1114823995L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QForumPost forumPost = new QForumPost("forumPost");

    public final ListPath<PostComment, QPostComment> comments = this.<PostComment, QPostComment>createList("comments", PostComment.class, QPostComment.class, PathInits.DIRECT2);

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final StringPath postContent = createString("postContent");

    public final DateTimePath<java.time.LocalDateTime> postDate = createDateTime("postDate", java.time.LocalDateTime.class);

    public final BooleanPath postDelete = createBoolean("postDelete");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final BooleanPath postNotice = createBoolean("postNotice");

    public final StringPath postTitle = createString("postTitle");

    public final NumberPath<Integer> postView = createNumber("postView", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QForumPost(String variable) {
        this(ForumPost.class, forVariable(variable), INITS);
    }

    public QForumPost(Path<? extends ForumPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QForumPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QForumPost(PathMetadata metadata, PathInits inits) {
        this(ForumPost.class, metadata, inits);
    }

    public QForumPost(Class<? extends ForumPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

