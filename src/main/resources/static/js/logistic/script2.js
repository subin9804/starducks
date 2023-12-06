// 리스트에 추가된 목록을 관리하기 위한 배열
window.document.addEventListener("DOMContentLoaded", function() {

    const add = document.getElementById("addBtn");
    const contentList = document.getElementById("contentList");
    const list = new Array();
    let num = 1;
    const productSelect = document.getElementById("product");
    const quantityInput = document.getElementById("quantity");

    // click 이벤트에 대한 리스너 등록 전에 기존 리스너 제거
    add.removeEventListener("click", handleClick);
    add.addEventListener("click", handleClick);

    function handleClick(e) {
        e.preventDefault();

        let selectedOption = productSelect.options[productSelect.selectedIndex]
        let itemName = selectedOption.getAttribute("data-name")
        let item = { order: num, name: itemName, quantity: quantityInput.value }

        list.push(item);

        let li = document.createElement("li");
        li.innerHTML = item.order + item.name  + item.quantity;
        contentList.appendChild(li);
        num++;
    }

});
