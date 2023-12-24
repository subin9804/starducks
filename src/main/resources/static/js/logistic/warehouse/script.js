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
        e.preventDefault();

        console.log("버튼 눌림")
        console.log(productSelect.value)

        if (!productSelect.value ) {
            Swal.fire({
                icon: "error",
                title: "품목을 선택하세요"
            });
            return;
        }

        let item = {
            empId: employeeSelect.value,
            productCode: productSelect.value,
            inboundQuantity: quantityInput.value
        };

        console.log(item);

        //입고 수량이 0이거나 0보다 작은 값일 때 리스트에 추가할 수 없게 한다.
        if(parseInt(quantityInput.value) <= 0 || !quantityInput.value){

            Swal.fire({
                icon: "error",
                title: "유효한 수량을 입력해 주세요"

            });
            return;
        }



        if (!isProductCodeInList(item.productCode)) {
            list.push(item);



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

        Swal.fire({title: '입고 등록 확인',
            text: '입고 등록 하시겠습니까?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'}).then((result) =>{
                if(result.isConfirmed){

                    // list를 숨겨진 입력 필드에 저장
                    document.getElementById("warehouseInboundDtos").value = JSON.stringify(list);
                    console.log(JSON.stringify(list));


                    $.ajax({
                        type: "POST",
                        url: "/logistic/inbound/warehouse/add",
                        contentType: "application/json", // JSON 데이터를 보내고 있다면 설정
                        data:JSON.stringify(list),
                        beforeSend: function(xhr) {
                            // Set CSRF token in the request header
                            xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
                        },
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
                            window.location.href = '/logistic/inbound/warehouse/list'

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

        })

        return false;

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
