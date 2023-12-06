function quilljsediterInit(postContent) {
    var options = {
        modules: {
            toolbar: [
                [{ header: [1, 2, false] }],
                ['bold', 'italic', 'underline'],
                ['image', 'code-block'],
                [{ list: 'ordered' }, { list: 'bullet' }]
            ]
        },
        placeholder: '자세한 내용을 입력해 주세요!',
        theme: 'snow'
    };

    var quill = new Quill('#editor', options);

    // 수정 페이지에서 게시글 내용을 보이도록 에디터에 설정
    if (postContent) {
        quill.clipboard.dangerouslyPasteHTML(postContent);
    }


    // 에디터 내용이 변경될 때마다 숨겨진 입력 필드에 값을 업데이트
    quill.on('text-change', function() {
        document.getElementById("quill_html").value = quill.root.innerHTML;
    });

    // 이미지 업로드 핸들러
    quill.getModule('toolbar').addHandler('image', function() {
        selectLocalImage();
    });

    // 이미지 업로드 함수
    function selectLocalImage() {
        const fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.click();

        fileInput.addEventListener("change", function() {
            const formData = new FormData();
            const file = fileInput.files[0];
            formData.append('uploadFile', file);

            // 이미지 파일을 서버에 업로드하는 AJAX 요청
            $.ajax({
                type: 'post',
                enctype: 'multipart/form-data',
                url: '/board/register/imageUpload',
                data: formData,
                processData: false, // processData와 contentType을 false로 설정
                contentType: false, // 파일 업로드 시 이 옵션이 필요
                dataType: 'json',
                success: function (data) {
                    // 업로드된 이미지를 에디터에 삽입
                    const range = quill.getSelection();
                    data.uploadPath = data.uploadPath.replace(/\\/g, '/');
                    quill.insertEmbed(range.index, 'image', "/board/display?fileName=" + data.uploadPath + "/" + data.uuid + "_" + data.fileName);
                },
                error: function (err) {
                    console.error("이미지 업로드 실패:", err);
                }
            });
        });
    }

}

// 페이지 로드 완료 시 에디터 초기화
window.onload = function() {
    var quillHtmlElement = document.getElementById('quill_html');

    // quill_html 요소가 존재하는 경우에만 에디터 초기화를 실행
    if (quillHtmlElement) {
        var postContent = quillHtmlElement.value;
        quilljsediterInit(postContent);
    }
};

// 댓글 수정 폼을 표시하고, 수정된 내용을 AJAX 요청으로 전송
function showEditForm(commentId) {
    // 댓글 내용과 수정 폼을 토글
    var content = document.getElementById('comment-content-' + commentId);
    var editForm = document.getElementById('edit-comment-form-' + commentId);
    content.style.display = content.style.display === 'none' ? '' : 'none';
    editForm.style.display = editForm.style.display === 'none' ? '' : 'none';
}

function updateComment(commentId) {
    var commentContent = document.getElementById('edit-comment-textarea-' + commentId).value;
    var url = '/api/comments/' + commentId; // RESTful API 엔드포인트

    // AJAX 요청
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content: commentContent })
    })
        .then(response => response.json())
        .then(data => {
            // 요청 성공 시, 댓글 내용 업데이트 및 수정 폼 숨기기
            document.getElementById('comment-content-' + commentId).innerText = commentContent;
            showEditForm(commentId); // 수정 폼 숨김
        })
        .catch(error => console.error('Error:', error));
}

// 댓글 수정 이벤트 리스너
document.querySelectorAll('.edit-comment').forEach(button => {
    button.addEventListener('click', function() {
        let commentId = this.dataset.commentId;
        let commentContent = document.querySelector(`#comment-content-${commentId}`).innerText;
        // 기존 내용을 입력 필드에 설정
        document.querySelector(`#edit-comment-input-${commentId}`).value = commentContent;
        // 수정 모드 활성화 (UI 변경 등)
    });
});

// 댓글 저장 이벤트 리스너
document.querySelectorAll('.save-comment').forEach(button => {
    button.addEventListener('click', function() {
        let commentId = this.dataset.commentId;
        let updatedContent = document.querySelector(`#edit-comment-input-${commentId}`).value;
        // AJAX 요청으로 수정된 내용 전송
        fetch(`/api/comments/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: updatedContent })
        }).then(response => {
            // 처리 결과에 따른 UI 업데이트
        });
    });
});

// 댓글 삭제 이벤트 리스너
document.querySelectorAll('.delete-comment').forEach(button => {
    button.addEventListener('click', function() {
        let commentId = this.dataset.commentId;
        // AJAX 요청으로 삭제 요청 전송
        fetch(`/api/comments/${commentId}`, {
            method: 'DELETE'
        }).then(response => {
            // 처리 결과에 따른 UI 업데이트
        });
    });
});


/** 콘텐츠 내용에서 <p>태그가 자꾸 보여서 없애려는 수단 나중에 시도해보기
 $(document).ready(function() {
 var content = $("#content").html(); // 여기서 'content'는 실제 요소의 ID에 맞게 변경해야 합니다.

 if (content.startsWith("<p>")) {
 content = content.substring(3);
 }

 if (content.endsWith("</p>")) {
 content = content.substring(0, content.length - 4);
 }

 $("#content").html(content);
 });
 */