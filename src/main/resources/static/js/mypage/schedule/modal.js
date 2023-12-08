export function createModal(calendar) {
    let modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'none'; // 초기에는 모달을 숨김

    let modalContent = document.createElement('div');
    modalContent.className = 'modal-content';

    let closeButton = document.createElement('span');
    closeButton.className = 'close';
    closeButton.textContent = '×';
    closeButton.addEventListener('click', function () {
        modal.style.display = 'none';
    });
    modalContent.appendChild(closeButton);
    let form = document.createElement('form');

    /**
     * 일정명
     * @type {HTMLInputElement}
     */
    let scheTitleLabel = document.createElement('label');
    scheTitleLabel.textContent = '일정명';
    let scheTitleInput = document.createElement('input');
    scheTitleInput.setAttribute('type', 'text');
    scheTitleInput.setAttribute('placeholder', '일정명을 입력해주세요');
    form.appendChild(scheTitleLabel);
    form.appendChild(scheTitleInput);

    /**
     * 시작일시
     * @type {HTMLInputElement}
     */
    let scheStartDateLabel = document.createElement('label');
    scheStartDateLabel.textContent = '시작일시';
    let scheStartDateInput = document.createElement('input');
    scheStartDateInput.setAttribute('type', 'datetime-local');
    form.appendChild(scheStartDateInput);

    /**
     * 종료일시
     * @type {HTMLLabelElement}
     */
    let scheEndDateLabel = document.createElement('label');
    scheEndDateLabel.textContent = '종료일시'
    let scheEndDateInput = document.createElement('input');
    scheEndDateInput.setAttribute('type', 'datetime-local');

    form.appendChild(scheEndDateInput);
    /** 일정 종류
     * - 개인일정, 공식일정 선택
     * @type {HTMLLabelElement}
     */
    let calendarTypeLabel = document.createElement('label');
    calendarTypeLabel.textContent = '일정 종류';
    let calendarDropdown = document.createElement('select');
    let myCalendarOption = document.createElement('option');
    myCalendarOption.textContent = '개인 일정';
    let generalCalendarOption = document.createElement('option');
    generalCalendarOption.textContent = '공식 일정';
    calendarDropdown.appendChild(myCalendarOption);
    calendarDropdown.appendChild(generalCalendarOption);
    form.appendChild(calendarTypeLabel);
    form.appendChild(calendarDropdown);

    /**
     * 참고사항
     * @type {HTMLTextAreaElement}
     */
    let notesLabel = document.createElement('label');
    notesLabel.textContent = '참고 사항';
    let notesInput = document.createElement('textarea');
    notesInput.setAttribute('placeholder', '참고 사항');
    form.appendChild(notesLabel);
    form.appendChild(notesInput);

    let submitButton = document.createElement('button');
    submitButton.textContent = '일정 추가';

    // 일정 추가 버튼 클릭 시 이벤트 처리
    submitButton.addEventListener('click', function (event) {
        event.preventDefault();
        var scheTitle = scheTitleInput.value;
        var scheStartDate = scheStartDateInput.value;
        var scheEndDate = scheEndDateInput.value;
        var calendarType = calendarDropdown.value;
        var notes = notesInput.value;

        // 데이터 객체 생성
        var data = {
            scheTitle: scheTitle,
            scheStartDate: scheStartDate,
            scheEndDate: scheEndDate,
            calendarType: calendarType,
            notes: notes
        };

        // 서버로 데이터 전송
        fetch('/schedule/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('네트워크가 좋지 않습니다.');
                }
                return response.json();
            })
            .then(function (data) {
                // 서버로부터 받은 응답 처리
                alert(data.message); // 객체의 특정 속성을 출력하도록 수정
                if (data.success) { // 서버에서 성공 여부를 받았다고 가정
                    if (calendar) { // 캘린더가 전달되었을 때
                        calendar.addEvent({
                            title: scheTitle,
                            start: scheStartDate,
                            end: scheEndDate,
                            extendedProps: {
                                calendarType: calendarType,
                                notes: notes
                            }
                        });
                    }
                }
                modal.style.display = 'none'; // 일정 추가 후 모달을 닫음
            })
            .catch(function(error) {
                console.error('Error:', error);
            });
    });

    form.appendChild(submitButton);
    modalContent.appendChild(form);

    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    return modal;
}
