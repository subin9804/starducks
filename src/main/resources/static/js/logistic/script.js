// 리스트에 추가된 목록을 관리하기 위한 배열
window.document.addEventListener("DOMContentLoaded", function() {

    const add = document.getElementById("addBtn");
    const submit = document.getElementById("submitBtn");
    const contentList = document.getElementById("contentList");
    const list = new Array();
    let num = 1;
    const productSelect = document.getElementById("product");
    const quantityInput = document.getElementById("quantity");
    const employeeSelect = document.getElementById("employee");

    add.removeEventListener("click", handleClick);
    add.addEventListener("click", handleClick);
    submit.removeEventListener("click", handleSubmitClick);
    submit.addEventListener("click", handleSubmitClick);

    function handleClick(e) {
        e.preventDefault()
        console.log("버튼 눌림")

        let item = {
            empId: employeeSelect.value,
            productCode: productSelect.value,
            inboundQuantity: quantityInput.value
        };

        if (!isProductCodeInList(item.productCode)) {
            list.push(item);

            let li = document.createElement("li");
            let selectedOption = productSelect.options[productSelect.selectedIndex]
            let itemName = selectedOption.getAttribute("data-name")
            li.innerHTML = list.length + "." + itemName + "|수량 : "  + quantityInput.value;
            contentList.appendChild(li);
        }
    }

    function handleSubmitClick(e) {
        e.preventDefault();

        // list를 숨겨진 입력 필드에 저장
        document.getElementById("warehouseInboundDtos").value = JSON.stringify(list);
        console.log(JSON.stringify(list));


        $.ajax({
            type: "POST",
            url: "/warehouseinbound/add",
            contentType: "application/json", // JSON 데이터를 보내고 있다면 설정
            data:JSON.stringify(list),
                //JSON.stringify({
                //warehouseInboundDtos: list
            //}) ,
               // {
               // warehouseInboundDtos: document.getElementById("warehouseInboundDtos").value
           // }, // 폼 데이터를 직렬화하여 전송
            success: function(response) {
                // 서버 응답에 대한 처리
                console.log("Server response:", response);

                // 성공적으로 서버로 전송한 후에 리스트 초기화
                list.length = 0;
                contentList.innerHTML = "";
                num = 1;
            },


            error: function(xhr, status, error) {
                console.error("Error sending data to server:", error);
                console.log(xhr.responseText);
            } // 추가된 부분

        });

    }

    function isProductCodeInList(productCode) {
        return list.some(existingItem => existingItem.productCode == productCode);
    }



});
