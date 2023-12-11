/** 예약 생성, 수정, 삭제 */

// $(document).ready(function() {


    // 예약 생성 및 수정
    function submit(e, action, id) {
        e.preventDefault();

        //폼데이터 가져오기
        let jsonData = {};
        jsonData.room = ($('#room').val());
        jsonData.confName = ($('#confName').val());
        jsonData.runningDay = ($('#runningDay').val());
        jsonData.startTime = ($('#startTime').val());
        jsonData.endTime = ($('#endTime').val());
        jsonData.text = ($('#text').val());
        jsonData.color = ($('#color').val());

        if(id != null) {
            jsonData.id = id;
        }

        console.log(jsonData)


    // 예약 생성
    if(action == 'post') {

        // Ajax 요청
        $.ajax({
            url: '/mypage/conf/add',
            type: 'POST',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function(rep) {
                console.log('성공')

                Swal.fire({
                    icon: "success",
                    title: "회의실이 예약되었습니다.",
                    showConfirmButton: false,
                    timer: 1500
                })
                    .then(() => {
                        location.reload();
                    })

            },
            error: function (err) {
                console.log( err.message)
            }

        })

    // 예약 수정
    } else if(action == 'put') {
        // Ajax 요청
        $.ajax({
            url: '/mypage/conf/edit/' + id,
            type: 'PUT',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function(rep) {
                console.log('성공')

                Swal.fire({
                    icon: "success",
                    title: "예약이 수정되었습니다.",
                    showConfirmButton: false,
                    timer: 1500
                })
                    .then(() => {
                        location.reload();
                    })

            },
            error: function (err) {
                console.log( err.message)
            }

        })
    }
}



// 예약 삭제
function deleteBook(id) {
    Swal.fire({
        title: "정말 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제!",
        cancelButtonText: "취소"
    })
    .then((result) => {
        if(result.isConfirmed) {
            // Ajax 요청
            $.ajax({
                url: '/mypage/conf/delete/' + id,
                type: 'DELETE',

                success: function (rep) {
                    console.log('성공')

                    Swal.fire({
                        icon: "success",
                        title: "예약이 삭제되었습니다.",
                        showConfirmButton: false,
                        timer: 1500
                    })
                        .then(() => {
                            location.reload();
                        })

                },
                error: function (err) {
                    Swal.fire({
                        icon: "error",
                        title: "예약 삭제를 실패했습니다.",
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            })
        }
    })
}




// });