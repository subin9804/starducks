import {createModal} from './modal.js'; // 모달 생성 함수를 불러옵니다.

export function addModal() {
    createModal();

    const modal = document.querySelector('.modal');

    // '일정명' input 요소를 가져옵니다.
    var scheTitleInput = modal.querySelector('input[type="text"]');
    scheTitleInput.addEventListener('input', function(event) {
        console.log('일정명 Input 값 변경:', event.target.value);
    });

    // '시작일시' input 요소를 가져옵니다.
    var scheStartDateInput = modal.querySelector('input[type="datetime-local"]');
    scheStartDateInput.addEventListener('input', function(event) {
        console.log('시작일시 Input 값 변경:', event.target.value);
        // 추가 작업을 수행할 수 있습니다.
    });

    // '종료일시' input 요소를 가져옵니다.
    var scheEndDateInput = modal.querySelectorAll('input[type="datetime-local"]')[1];
    scheEndDateInput.addEventListener('input', function(event) {
        console.log('종료일시 Input 값 변경:', event.target.value);
        // 추가 작업을 수행할 수 있습니다.
    });

    // '일정 종류' select 요소를 가져옵니다.
    var calendarDropdown = modal.querySelector('select');
    calendarDropdown.addEventListener('change', function(event) {
        console.log('일정 종류 선택 변경:', event.target.value);
        // 추가 작업을 수행할 수 있습니다.
    });

// '참고사항' textarea 요소를 가져옵니다.
    var notesInput = document.querySelector('textarea');
    notesInput.addEventListener('input', function(event) {
        console.log('참고 사항 Input 값 변경:', event.target.value);
        // 추가 작업을 수행할 수 있습니다.
    });
}