var productIndex = 0;

/* 상품 추가 버튼 클릭 시 동적으로 상품 입력 필드 추가 */
function addProductField() {
    var productFields = document.getElementById('productFields');

    var productField = document.createElement('div');
    productField.appendChild(document.createElement('label'));
    productField.lastChild.htmlFor = 'product';
    productField.lastChild.textContent = '입고상품 : ';

    var selectElement = document.createElement('select');
    selectElement.id = 'product';
    selectElement.name = 'productCodes[' + productIndex + ']';

    var defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = '상품 선택하세요';
    selectElement.appendChild(defaultOption);

    /* JavaScript로 서버에서 전달한 데이터 사용 */
    /* products는 서버에서 전달한 상품 목록 데이터로 가정 */
    var products = JSON.parse(document.getElementById('productFields').getAttribute('data-products'));

    for (var i = 0; i < products.length; i++) {
        var optionElement = document.createElement('option');
        optionElement.value = products[i].productCode;
        optionElement.text = products[i].productName;
        selectElement.appendChild(optionElement);
    }

    productField.appendChild(selectElement);

    productField.appendChild(document.createElement('label'));
    productField.lastChild.htmlFor = 'quantity';
    productField.lastChild.textContent = ' 입고수량';

    var inputElement = document.createElement('input');
    inputElement.type = 'number';
    inputElement.id = 'quantity';
    inputElement.name = 'inboundQuantities[' + productIndex + ']';
    inputElement.required = true;

    productField.appendChild(inputElement);

    productField.appendChild(document.createElement('br'));

    productFields.appendChild(productField);
    productIndex++;
}
<<<<<<< HEAD
=======

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
>>>>>>> master
