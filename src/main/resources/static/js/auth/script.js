document.addEventListener('DOMContentLoaded', function () {
    // 서버에서 전달된 메시지 확인
    var message = /*[[${message}]]*/ 'null';

    // 메시지가 존재하면 알림창으로 표시
    if (message !== 'null' && message !== '') {
        alert(message);
    }
});