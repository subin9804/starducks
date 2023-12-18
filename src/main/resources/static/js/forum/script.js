//댓글 삭제 함수. 시큐리티 때문에 토큰 추가함
function deletePostComment(commentId) {

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  if (confirm('정말 삭제하시겠습니까?')) {
    fetch(`/api/comments/${commentId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken // CSRF 토큰을 헤더에 추가
      }
    }).then(response => {
      if (response.ok) {
        const elementToRemove = document.getElementById('comment-block-' + commentId);
        if (elementToRemove) {
          elementToRemove.remove();

        } else {
          console.error('삭제할 댓글 요소를 찾을 수 없습니다.');
        }
        location.reload();
      } else {
        alert('댓글 삭제에 실패했습니다.');
      }
    }).catch(error => {
      console.error('Error:', error);
      alert('오류가 발생했습니다: ' + error.message);
    });
  }
}

// 댓글 수정 폼을 수정 누르기 전까지는 숨겼다가 누르면 표시하고, 수정된 내용을 AJAX 요청으로 전송
function showEditForm(commentId) {
  var contentElement = document.getElementById('comment-content-' + commentId);
  var editForm = document.getElementById('edit-comment-form-' + commentId);
  var editTextarea = document.getElementById('edit-comment-textarea-' + commentId);

  // 기존 댓글 내용을 수정 폼의 textarea에 설정
  if (contentElement && editTextarea) {
    editTextarea.value = contentElement.innerText;
  }

  // 댓글 내용과 수정 폼을 토글
  if (contentElement && editForm) {
    contentElement.style.display = contentElement.style.display === 'none' ? '' : 'none';
    editForm.style.display = editForm.style.display === 'none' ? '' : 'none';
  }
}

// 댓글 수정 이벤트 리스너
document.querySelectorAll('.edit-comment').forEach(button => {
  button.addEventListener('click', function () {
    let commentId = this.dataset.commentId;
    let commentContent = document.querySelector(`#comment-content-${commentId}`).innerText;
    // 기존 내용을 입력 필드에 설정
    document.querySelector(`#edit-comment-input-${commentId}`).value = commentContent;
    // 수정 모드 활성화 (UI 변경 등)
  });
});