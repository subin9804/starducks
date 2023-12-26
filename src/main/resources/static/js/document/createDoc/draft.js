var prtContent; // 프린트 하고 싶은 영역
var initBody;  // body 내용 원본

// 프린트하고 싶은 영역의 id 값을 통해 출력 시작
function startPrint (div_id) {
    prtContent = document.getElementById(div_id);
    window.onbeforeprint = beforePrint;
    window.onafterprint = afterPrint;
    window.print();
}

// 웹페이지 body 내용을 프린트하고 싶은 내용으로 교체
function beforePrint(){
    initBody = document.body.innerHTML;
    document.body.innerHTML = prtContent.innerHTML;
}

// 프린트 후, 웹페이지 body 복구
function afterPrint(){
    document.body.innerHTML = initBody;
}

//부트스트랩 모달
const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
    myInput.focus()
})

//임시저장 submit 처리
//
function submit2(form) {

    form.action = '/document/createDoc/temp';
    form.submit();
    return true;
}
function submit3(form) {
    form.action = '/document/createDoc/tempUpdate';
    form.submit();
    return true;
}
function submit4(form) {
    form.action = '/document/createDoc/update';
    form.submit();
    return true;
}

//발주서 임시저장 처리
function submit22(form) {

    form.action = '/document/createDoc/orderFrom/temp';
    form.submit();
    return true;
}


//문서 미리보기 실시간
function displaySelectedApv1(element) {
    var selectedEmpName = element.nextElementSibling.querySelector('.searchEmpName').innerText;
    document.getElementById('selectedApv1').innerText = selectedEmpName;
}
function displaySelectedApv2(element) {
    var selectedEmpName = element.nextElementSibling.querySelector('.searchEmpName').innerText;
    document.getElementById('selectedApv2').innerText = selectedEmpName;
}
function displaySelectedVendor(element) {


    const selectedVendorName = element.nextElementSibling.querySelector('.searchVendorName').innerText;
    const selectedVendorBoss= element.nextElementSibling.querySelector('.searchVendorBoss').innerText;
    const selectedVendorTel = element.nextElementSibling.querySelector('.searchVendorTel').innerText;
    document.getElementById('selectedVendor1').innerText = selectedVendorName;
    document.getElementById('selectedVendor2').innerText = selectedVendorName;
    document.getElementById('selectedVendor3').innerText = selectedVendorTel;
    document.getElementById('selectedVendor4').innerText = selectedVendorBoss;

}

function displaySelectedRefs() {
    // 여러 군데에 값을 출력할 때 각 출력 위치의 id를 배열로 저장
    var selectedRefsIds = ['selectedRefs', 'selectedRefs2'];

    // 반복문을 통해 각 출력 위치에 값을 설정
    selectedRefsIds.forEach(function (refsId) {
        var selectedValues = document.querySelectorAll('input[name="refEmpIdList"]:checked');
        var selectedValuesText = Array.from(selectedValues).map(function (checkbox) {
            var selectedEmpName = checkbox.nextElementSibling.querySelector('.searchEmpName').innerText;
            // return checkbox.value + ' (' + selectedEmpName + ')';
            return selectedEmpName;
        }).join(', ');

        document.getElementById(refsId).innerText = selectedValuesText;
    });
}


function showproducts() {
    // Get the selected vendor ID (replace 'yourVendorIdField' with the actual ID field)
    var selectedVendorId = $('input[name="selVendorId"]:checked').val();

    // Check if a vendor is selected
    if (selectedVendorId === undefined) {
        console.error('No vendor selected.');
        return;
    }

    // Make an AJAX request to the server to get products for the selected vendor
    $.ajax({
        type: 'GET',
        url: '/document/createDoc/' + selectedVendorId + '/products', // Replace with your actual endpoint
        success: function (data) {
            // Check if data is valid
            if (data && Array.isArray(data)) {
                // Clear existing options in the product dropdown
                $('#product').empty();

                // Add a default option
                $('#product').append($('<option>', {
                    value: '',
                    text: '상품 선택하세요'
                }));

                // Add options for each product received from the server
                $.each(data, function (index, product) {
                    $('#product').append($('<option>', {
                        value: product.productCode,
                        'data-name': product.productName,
                        'data-category': product.productCategory,
                        'data-unit':product.productUnit,
                        'data-price': product.productPrice,
                        text: product.productName
                    }));
                });

                // 여기에 추가할 부분 시작
                // 이 부분은 선택한 거래처에 대한 상품이 성공적으로 받아온 후 수행될 로직입니다.
                // 추가하고 싶은 동작을 여기에 구현하세요.
                console.log('Products for the selected vendor:', data);

                // 여기에 추가할 부분 끝
            } else {
                console.error('Invalid data received:', data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching products:', error);
        }
    });
}