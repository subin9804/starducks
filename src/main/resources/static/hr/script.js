window.document.addEventListener("DOMContentLoaded", function() {

    /* position을 select태그 쓰지 않고 dropdown박스로 만들기 */
    /* 각 항목 선택 시 hidden되어있는 input에 값이 입력됨 */

    let position = document.getElementById("position");
    let options = document.getElementsByClassName("option");
    let selectbox = document.getElementsByClassName("selectbox");

    for(option of options) {

        option.addEventListener("click", function(e) {
            let text = e.target.innerText;
            let value = e.target.getAttribute('value');
            console.log(value)
            position.value = value;
            selectbox[0].innerText = text;
        })
    }

})