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


    add.addEventListener("click", handleClick);
    submit.addEventListener("click", handleSubmitClick);

    function handleClick(e) {
        e.preventDefault()
        console.log("버튼 눌림")
        console.log(productSelect.value)

        let item = {
            empId: employeeSelect.value,
            productCode: productSelect.value,
            inboundQuantity: quantityInput.value
        };

        if (!isProductCodeInList(item.productCode)) {
            list.push(item);

            // let li = document.createElement("li");
            // let selectedOption = productSelect.options[productSelect.selectedIndex]
            // let itemName = selectedOption.getAttribute("data-name")
            // li.innerHTML = list.length + "." + itemName + "|수량 : "  + quantityInput.value;
            // contentList.appendChild(li);

            let row = contentTable.insertRow(-1);
            let cell1 = row.insertCell(0);
            let cell2 = row.insertCell(1);
            let cell3 = row.insertCell(2);
            let cell4 = row.insertCell(3);
            let cell5 = row.insertCell(4);
            let cell6 = row.insertCell(5);
            let cell7 = row.insertCell(6);




            let selectedOption = productSelect.options[productSelect.selectedIndex];
            let itemName = selectedOption.getAttribute("data-name");
            let itemCategory=selectedOption.getAttribute("data-category");
            let itemPrice   = selectedOption.getAttribute("data-price");
            let itemStock   =  selectedOption.getAttribute("data-cnt")

            cell1.innerHTML = num;
            cell2.innerHTML = itemCategory;
            cell3.innerHTML = itemName;
            cell4.innerHTML = itemPrice;
            cell5.innerHTML = itemStock;
            cell6.innerHTML = quantityInput.value;
            cell7.innerHTML = `<button class="commonBtn delete-button" type="button" onclick="deleteRow(this)">X</button>`;

            num++;



        }
    }

    function handleSubmitClick(e) {
        e.preventDefault();

        // list를 숨겨진 입력 필드에 저장
        document.getElementById("warehouseOutboundDtos").value = JSON.stringify(list);
        console.log(JSON.stringify(list));


        $.ajax({
            type: "POST",
            url: "/logistic/outbound/warehouse/add",
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

                //입고내역 보여주는 페이지로 리디렉션
                window.location.href = '/logistic/outbound/warehouse/list'

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

    window.deleteRow = function(button) {
        // 행 삭제 동작 처리
        var row = button.parentNode.parentNode;
        row.parentNode.removeChild(row);

        // 목록 배열 업데이트
        var index = row.cells[0].innerHTML - 1;
        list.splice(index, 1);
        num--;
        updateRowNumbers();
    };

    function updateRowNumbers() {
        var rows = contentTable.rows;
        for (var i = 1; i < rows.length; i++) {
            rows[i].cells[0].innerHTML = i;
        }
    }



});
