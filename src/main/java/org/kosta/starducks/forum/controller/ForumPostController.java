package org.kosta.starducks.forum.controller;

import org.kosta.starducks.forum.dto.ForumPostUpdateDTO;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.entity.PostComment;
import org.kosta.starducks.forum.service.ForumPostService;
import org.kosta.starducks.forum.service.PostCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/forum")
public class ForumPostController {

    private final ForumPostService forumPostService;
    private final PostCommentService postCommentService;

    public ForumPostController(ForumPostService forumPostService, PostCommentService postCommentService) {
        this.forumPostService = forumPostService;
        this.postCommentService = postCommentService;
    }

    // 게시판 메인 페이지
    @GetMapping
    public String listPosts(Model model,@PageableDefault(page = 0,size = 5,sort = "postId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ForumPost> posts = forumPostService.postList(pageable);

//        페이지 조건 생성
        int nowPage = posts.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, posts.getTotalPages());
        int totalPages = posts.getTotalPages();

        model.addAttribute("posts", posts );
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "forum/forum"; // 게시판 메인 페이지 템플릿
    }

    // 게시글 작성 페이지로 이동
    @GetMapping("/add")
    public String addPostForm(Model model) {
        model.addAttribute("post", new ForumPost()); //타임리프에서 참조하는 이름 현재는 post
        return "forum/forumAddPost"; // 게시글 추가 페이지 템플릿
    }

    // 게시글 작성 완료 및 업로드
    @PostMapping("/add")
    public String addPost(@ModelAttribute ForumPost forumPost, @RequestParam(required = false) boolean postNotice) {
        forumPost.setPostNotice(postNotice);
        forumPostService.createOrUpdateForumPost(forumPost);
        return "redirect:/forum";
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{id}") //   forum/id 가 페이지 주소
    public String getPostDetails(@PathVariable("id") Long id, Model model) {
        ForumPost post = forumPostService.getPostByIdAndUpdateView(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);
        return "forum/forumPostDetail";
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        ForumPost post = forumPostService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);
        return "forum/forumEditPost";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, @ModelAttribute ForumPostUpdateDTO postDTO) {
        // 기존 게시글 객체를 불러옵니다.
        ForumPost existingPost = forumPostService.getPostById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));

        // DTO의 데이터를 기존 게시글 객체에 적용합니다.
        existingPost.setPostTitle(postDTO.getPostTitle());
        existingPost.setPostContent(postDTO.getPostContent());
        existingPost.setPostNotice(postDTO.isPostNotice());

        // 게시글을 업데이트합니다.
        forumPostService.createOrUpdateForumPost(existingPost);

        return "redirect:/forum";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        forumPostService.deleteForumPost(id);
        return "redirect:/forum";
    }

    @GetMapping("/search") //검색 기능 구현
    public String searchPosts(@RequestParam String query, Model model) {
        List<ForumPost> searchResults = forumPostService.searchPosts(query);
        model.addAttribute("posts", searchResults);
        return "forum/forum";
    }

    // 댓글 추가 엔드포인트
    @PostMapping("/post/{id}/addComment")
    public String addComment(@PathVariable("id") Long id, @RequestParam String commentContent) {
        PostComment comment = new PostComment();
        comment.setCommentContent(commentContent);
        comment.setForumPost(forumPostService.getPostById(id).orElseThrow());
        postCommentService.createOrUpdateComment(comment);
        return "redirect:/forum/post/" + id;
    }
}
