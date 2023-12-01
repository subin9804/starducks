function quilljsediterInit() {
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
    quilljsediterInit();
};

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
