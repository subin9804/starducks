package org.kosta.starducks.forum.controller;

import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.service.ForumPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forum")
public class ForumPostController {

    private final ForumPostService forumPostService;

    public ForumPostController(ForumPostService forumPostService) {
        this.forumPostService = forumPostService;
    }

    // 게시판 메인 페이지
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", forumPostService.getAllForumPosts());
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
    public String addPost(@ModelAttribute ForumPost forumPost) {
        forumPostService.createOrUpdateForumPost(forumPost);
        return "redirect:forum/forum";
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        ForumPost post = forumPostService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);
        return "forum/forumEditPost";
    }

    // 게시글 수정 완료 및 업로드
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute ForumPost forumPost) {
        forumPost.setPostId(id);
        forumPostService.createOrUpdateForumPost(forumPost);
        return "redirect:forum/forum";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        forumPostService.deleteForumPost(id);
        return "redirect:forum/forum";
    }
}
