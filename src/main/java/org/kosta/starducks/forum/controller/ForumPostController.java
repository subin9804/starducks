package org.kosta.starducks.forum.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.forum.dto.ForumPostUpdateDto;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.entity.PostComment;
import org.kosta.starducks.forum.service.ForumPostService;
import org.kosta.starducks.forum.service.PostCommentService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 관련 컨트롤러
 */

@Controller
@RequestMapping("/forum")
public class ForumPostController {

    private final ForumPostService forumPostService;
    private final PostCommentService postCommentService;
    private final HttpServletRequest request;
    private final EmpService empService;

    public ForumPostController(ForumPostService forumPostService, PostCommentService postCommentService, HttpServletRequest request, EmpService empService) {
        this.forumPostService = forumPostService;
        this.postCommentService = postCommentService;
        this.request = request;
        this.empService = empService;
    }

    // 게시판 메인 페이지
    @GetMapping
    public String listPosts(Model model,@PageableDefault(page = 0,size = 10,sort = "postId", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        Page<ForumPost> posts = null;

        List<ForumPost> topNotices = forumPostService.getTopNotice(); // 최신 공지 2개 조회

        model.addAttribute("topNotices", topNotices); // 공지사항 데이터 추가

//        검색 키워드가 없으면 전체글을 페이저블 처리해서 보여주고, 키워드가 있으면 키워드에 맞게 글을 필터링하고, 리스트를 페이저블 처리해준다
        if(searchKeyword == null) {
            posts = forumPostService.postList(pageable);
        }else{
            posts = forumPostService.postSearchList(searchKeyword,pageable);
        }

        model.addAttribute("posts", posts.getContent()); // 모든 게시글 데이터 추가

        // 페이지 조건 생성
        int nowPage = posts.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, posts.getTotalPages());
        int totalPages = posts.getTotalPages();

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }

        model.addAttribute("posts", posts );
        model.addAttribute("topNotices", topNotices); // 공지사항 데이터 추가
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "forum/forum"; // 게시판 메인 페이지 템플릿
    }

    // 게시글 작성 페이지. 공지 선택 기능은 로그인한 사원이 인사부일때만 보이도록 선택
    @GetMapping("/add")
    public String addPostForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("post", new ForumPost()); // 타임리프에서 참조하는 이름

        Long empId = Long.parseLong(userDetails.getUsername());
        Employee employeeWithDept = empService.getEmployeeWithDepartment(empId);

//        보스 계정이면 공지 등록 버튼이 보이도록 하기 위해서 보스 권한을 갖고 있는 계정인지 확인하기
        boolean isBoss = userDetails.getAuthorities().stream()
            .anyMatch(grantedAuthority -> "ROLE_BOSS".equals(grantedAuthority.getAuthority()));
        model.addAttribute("isBoss", isBoss);

//        총무부 직원인 게 확인되면 공지 버튼이 보인다
        boolean isGADepartment = employeeWithDept != null &&
            "총무부".equals(employeeWithDept.getDept().getDeptName());
        model.addAttribute("isGADepartment", isGADepartment);

        return "forum/forumAddPost";
    }

    // 게시글 작성 완료 및 업로드되면 게시판 페이지로 이동
    @PostMapping("/add")
    public String addPost(@ModelAttribute ForumPost forumPost, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(required = false) boolean postNotice) {
        Employee employee = userDetails.getEmployee();
        if (employee == null) {
            throw new IllegalArgumentException("Invalid employee Id: " + (userDetails.getUsername()));
        }
        forumPost.setEmployee(employee);
        forumPost.setPostNotice(postNotice);

        // 공지 알림을 위해 일반 포스트와 공지 포스트 저장 메서드 분리
        if(postNotice) {
            forumPostService.noticeCreateOrUpdateForumPost(forumPost);
        } else {
            forumPostService.createOrUpdateForumPost(forumPost);
        }

        return "redirect:/forum";
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{id}")
    public String getPostDetails(@PathVariable("id") Long id, Model model) {
        ForumPost post = forumPostService.getPostByIdAndUpdateView(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);
        return "forum/forumPostDetail";
    }

    // 게시글 수정 페이지
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        ForumPost post = forumPostService.getPostById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);

        Employee employeeWithDept = empService.getEmployeeWithDepartment(Long.parseLong(userDetails.getUsername()));

        //        보스 계정이면 공지 등록 버튼이 보이도록 하기 위해서 보스 권한을 갖고 있는 계정인지 확인하기
        boolean isBoss = userDetails.getAuthorities().stream()
            .anyMatch(grantedAuthority -> "ROLE_BOSS".equals(grantedAuthority.getAuthority()));
        model.addAttribute("isBoss", isBoss);

//        총무부 직원인 게 확인되면 공지 버튼이 보인다
        boolean isGADepartment = employeeWithDept != null &&
            "총무부".equals(employeeWithDept.getDept().getDeptName());
        model.addAttribute("isGADepartment", isGADepartment);

        return "forum/forumEditPost";
    }

    //게시글 수정 완료될 때 로직
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, @ModelAttribute ForumPostUpdateDto postDTO) {
        // 기존 게시글 객체를 불러옵니다.
        ForumPost existingPost = forumPostService.getPostById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));

        // DTO의 데이터를 기존 게시글 객체에 적용합니다.
        existingPost.setPostTitle(postDTO.getPostTitle());
        existingPost.setPostContent(postDTO.getPostContent());
        existingPost.setPostNotice(postDTO.isPostNotice());

        // 게시글을 업데이트합니다.
        forumPostService.createOrUpdateForumPost(existingPost);

        return "redirect:/forum/post/{id}";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, Model model) {
        forumPostService.deleteForumPost(id);
        return "redirect:/forum";
    }

    // 댓글 추가 엔드포인트
    @PostMapping("/post/{id}/addComment")
    public String addComment(@PathVariable("id") Long id, @RequestParam String commentContent, @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostComment comment = new PostComment();
        comment.setCommentContent(commentContent);

        // 현재 인증된 사용자의 Employee 정보를 설정
        Employee employee = userDetails.getEmployee();
        if (employee == null) {
            throw new IllegalArgumentException("Invalid employee Id: " + userDetails.getUsername());
        }
        comment.setEmployee(employee);

        comment.setForumPost(forumPostService.getPostById(id).orElseThrow());
        postCommentService.createOrUpdateComment(comment);
        return "redirect:/forum/post/" + id;
    }
}
