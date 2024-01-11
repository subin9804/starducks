


window.document.addEventListener("DOMContentLoaded", function() {
    function toggleSubMenu(menuName) {
        var submenu = document.querySelector('.' + menuName + '-submenu');
        submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    }

    function handleSubMenuClick(menuName, subMenuName) {
        // Add your logic for handling submenu clicks here
        console.log('SubMenu Clicked:', menuName, subMenuName);
    }

    function handleMenuClick(menuName) {
        // Add your logic for handling menu clicks here
        console.log('Menu Clicked:', menuName);
    }

    function handleLogout() {
        // Add your logic for handling logout here
        console.log('Logout Clicked');
    }

    // 라디오 버튼 변경 이벤트에 대한 처리
    document.querySelectorAll('input[name="nav"]').forEach((radio) => {


        radio.addEventListener('change', function () {
            // 모든 패널 숨김
            document.querySelectorAll('ul').forEach((ul) => {
                ul.style.display = 'none';
            });

            // 현재 선택된 라디오 버튼에 해당하는 패널 보임
            const panelId = this.getAttribute('aria-controls');
            console.log(panelId)
            document.getElementById(panelId).style.display = 'block';
        });
    });


    // 서버에 알림을 구독하는 url
    const apiUrl = '/api/v1/notify/subscribe';

    // 새로운 알림 표시
    let notifyStatus = localStorage.getItem("classNotify");

    if(notifyStatus == null || notifyStatus == undefined) {
        localStorage.setItem("classNotify", "false")
    } else {
        if (notifyStatus == "true") {
            $("#notify").addClass("notify");
        } else {
            $("#notify").removeClass("notify");
        }
    }


    setupNotifications(apiUrl)

    function setupNotifications(apiUrl) {
        // SSE 엔드포인트 URL로 새 EventSource 생성
        const eventSource = new EventSource(apiUrl);

        eventSource.onopen = () => {
            // 연결 시 할 일
            // console.log("연결됨!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        };

        console.log('Initial readyState:', eventSource.readyState);

        // SSE 이벤트에 대한 이벤트 리스너
        eventSource.onmessage = (event) => {
            const eventData = event.data;
            console.log('받은 SSE 메시지: ', eventData);

            // 받은 데이터를 사용하여 푸시 알림창을 생성
            handleEventUpdate(eventData);
        };

        eventSource.onerror = (event) => {
            // 오류 처리
            console.error('오류가 발생했습니다: ', event);
            eventSource.close();
        };

        // 페이지를 나갈 때 연결 닫기
        window.onbeforeunload = function () {
            if (eventSource) {
                eventSource.close();
            }
        };

        function handleEventUpdate(eventData) {
            const notificationData = JSON.parse(eventData);

            // 알림 표시
            localStorage.setItem("classNotify", "true")
            $("#notify").addClass("notify");

            // 알림창 띄우기
            $("#sideAlert").click(function () {
                // 채팅일 경우에만 팝업창으로 오픈
                if(notificationData.type == "CHAT") {
                    window.open(notificationData.url, 'ChatPopup', 'width=450 ,height=600');
                } else {
                    window.location.href = notificationData.url;
                }
            }).css({
                "transform": "translateX(-350px)",
            }).append(
                "<p>" + notificationData.content + "</p>" +
                "<p class='s-font'>" + notificationData.createdAt + "</p>"
            )

            // 5초 후에 알림창 닫기
            setTimeout(() => {
                $("#sideAlert").css("transform", "translateX(350px)")

                // 알림창 초기화 (clear text content)
                $("#sideAlert").text("");
            }, 5000)

            // 알림 리스트에 등록
            let newLi = $("<li>").click(function () {
                // 채팅일 경우에만 팝업창으로 오픈
                if(notificationData.type == "CHAT") {
                    window.open(notificationData.url, 'ChatPopup', 'width=450 ,height=600');
                } else {
                    window.location.href = notificationData.url;
                }
            }).hover(
                function () {
                    // 마우스가 올라갔을 때의 스타일
                    $(this).css("background-color", "#D6DEC6FF");
                },
                function () {
                    // 마우스가 나갔을 때의 스타일
                    $(this).css("background-color", "#E3EAC8FF");
                }
            );
            let content = $("<p>").text(notificationData.content);
            let time = $("<p>").text(notificationData.createdAt).addClass("s-font");

            $("#notify-drop").prepend(newLi.append(content).append(time));
        }
    }



    /** 헤더 알림창 */
    // 드롭다운 버튼 클릭 시 리스트 토글
    $("#notify").click(function(event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        $("#notify").removeClass("notify");
        $("#notify-drop").toggle(); // toggle() 메서드를 사용하여 토글
        localStorage.setItem("classNotify", "false")
    });

    // 문서 클릭 시 리스트 숨김
    $(document).on('click', function(event) {
        if (!$(event.target).closest('#notify').length && !$(event.target).closest('#notify-drop').length) {
            $("#notify-drop").css("display", "none");
        }
    });
})





async function errorAlert(message) {
    await Swal.fire({
        icon: "error",
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}

async function warning(message) {
    await Swal.fire({
        icon: "info",
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}

async function successAlert(message) {
    Swal.fire({
        icon: "success",
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}

