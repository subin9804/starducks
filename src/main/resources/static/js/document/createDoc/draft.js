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

function displaySelectedApv1(element) {
    var selectedEmpName = element.nextElementSibling.querySelector('.searchEmpName').innerText;
    document.getElementById('selectedApv1').innerText = selectedEmpName;
}
function displaySelectedApv2(element) {
    var selectedEmpName = element.nextElementSibling.querySelector('.searchEmpName').innerText;
    document.getElementById('selectedApv2').innerText = selectedEmpName;
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