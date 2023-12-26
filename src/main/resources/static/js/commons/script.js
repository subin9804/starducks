


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


})

/** 알림 기능 */
const apiUrl = '/api/v1/notify/subscribe';
const authToken = '있으면 넣을 것'
// let lastEventId = 1;

// SSE 엔드포인트 URL로 새 EventSource 생성
const eventSource = new EventSource(apiUrl);

eventSource.onopen = () => {
    // 연결 시 할 일
    console.log("연결됨!!!!!!!!!!!!!!!!!!!!!!!!!!!")

};

console.log('Initial readyState:', eventSource.readyState);

// SSE 이벤트에 대한 이벤트 리스너
eventSource.onmessage = (event) => {
    console.log("헤이")
    // 수신된 SSE 메시지 처리
    const eventData = JSON.parse(event.data);
    console.log('받은 SSE 메시지: ', JSON.stringify(eventData));

    // SSE 메시지를 처리하는 논리를 여기에 구현하세요
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

