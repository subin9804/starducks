

function toggleOptions1(checkbox) {
    // 선택 상자의 상태에 따라 .options1를 표시하거나 숨깁니다.
    let options1 = $(checkbox).siblings(".options1");
    if (checkbox.checked) {
        options1.show();
    } else {
        options1.hide();
    }
}
function toggleOptions2(checkbox) {
    // 선택 상자의 상태에 따라 .options2를 표시하거나 숨깁니다.
    let options2 = $(checkbox).siblings(".options2");
    if (checkbox.checked) {
        options2.show();
    } else {
        options2.hide();
    }
}

window.document.addEventListener("DOMContentLoaded", function() {

    /* position을 select태그 쓰지 않고 dropdown박스로 만들기 */
    /* 각 항목 선택 시 hidden되어있는 input에 값이 입력됨 */
    // 부서
    $(".box1").change(function () {
        // 선택 상자의 상태에 따라 .options를 표시하거나 숨깁니다.
        toggleOptions1(this);
    });
    $(".option1").click(function(e) {
        let text = $(e.target).text();
        let value = $(e.target).attr('value');
        console.log('test' + text)
        $("#dept").val(value)
        console.log("position" + $("#dept").val())
        $("#allLabel1").text(text);
        console.log($("#allLabel1").val())
    })

    // 직급
    $(".box2").change(function () {
        // 선택 상자의 상태에 따라 .options를 표시하거나 숨깁니다.
        toggleOptions2(this);
    });
    $(".option2").click(function(e) {
        let text = $(e.target).text();
        let value = $(e.target).attr('value');
        console.log('test' + text)
        $("#position").val(value)
        console.log("position" + $("#position").val())
        $("#allLabel2").text(text);
        console.log($("#allLabel2").val())
    })

    // 프로필 및 도장 이미지
    $("#profileImg").on('click', () => {
        $("#profile").click();
        $("#profile").val("");
    })

    $("#stampImg").click(() => {
        $("#stamp").click();
        $("#stamp").val("");
    })

    // input 요소에 파일이 선택되었을 때 이벤트를 처리하는 부분
    $("#profile").on("change", function() {
        const fileInput = $(this)[0];
        const file = fileInput.files[0];

        if (file) {
            let reader = new FileReader();

            // 파일을 읽어오고 읽기가 완료되면 미리보기 설정
            reader.onload = function (e) {
                $("#profileImg").attr("src", e.target.result);
            };

            // 파일을 읽어오기
            reader.readAsDataURL(file);
        }
    });
    $("#stamp").on("change", function() {
        const fileInput = $(this)[0];
        const file = fileInput.files[0];

        if (file) {
            let reader = new FileReader();

            // 파일을 읽어오고 읽기가 완료되면 미리보기 설정
            reader.onload = function (e) {
                $("#stampImg").attr("src", e.target.result);
            };

            // 파일을 읽어오기
            reader.readAsDataURL(file);
        }
    });

    // $(document).ready(function () {
    //     // Form이 성공적으로 제출되면 Ajax를 사용하여 서버로부터 메시지를 받아 SweetAlert을 표시
    //     $("#form").submit(function (event) {
    //         event.preventDefault();
    //
    //         $.ajax({
    //             type: "POST",
    //             url: "/hr/emp/save",
    //             data: $(this).serializeArray(),
    //             contentType: "application/json; charset=UTF-8", // 폼 데이터를 전송할 때의 content type
    //
    //             success: function (response) {
    //                 // 서버로부터 받은 응답을 확인하고 SweetAlert을 표시
    //                 swal("Success!", response, "success");
    //                 redirect("/hr")
    //             },
    //             error: function (error) {
    //                 // 실패한 경우에 대한 처리를 추가
    //                 console.log("Error:", error);
    //             }
    //         });
    //     });
    // });

})