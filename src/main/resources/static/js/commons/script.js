


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

});



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

