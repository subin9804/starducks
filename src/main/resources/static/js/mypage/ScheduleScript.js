document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var modal;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            start: 'dayGridMonth,timeGridWeek',
            center: 'prev title next',
            end: 'today addEventButton'
        },
        locale: "ko",
        initialDate: '2023-12-27',
        customButtons: {
            addEventButton: {
                text: '일정 등록',
                click: function () {
                    if (!modal) { // 모달이 생성되지 않았을 때만 생성
                        createModal();
                    }
                    modal.style.display = 'block'; // 모달을 보이게 함
                }
            }
        },
        events: function (fetchInfo, successCallback, errorCallback) {
// fetchShowSingleSchedule(empId)가 어디서 오는지 확인하세요.
            fetchShowSingleSchedule().then(function (data) {
                var events = data.map(function (schedule) {
                    return {
                        title: schedule.scheTitle,
                        start: schedule.scheStartDate,
                        end: schedule.scheEndDate,
                        extendedProps: {
                            scheduleType: schedule.scheduleType,
                            location: schedule.location,
                            notes: schedule.notes,
                            empId: schedule.empId
                        }
                    };
                });
                successCallback(events);
            }).catch(function (error) {
                errorCallback(error);
            });
        }
    });

    function fetchShowSingleSchedule() {
        var empId = 1;
        return fetch('/schedule/show/' + empId)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('네트워크가 좋지 않습니다.');
                }
                return response.json();
            });
    }

    function createModal() {
        modal = document.createElement('div');
        modal.className = 'modal';
        modal.style.display = 'none'; // 초기에는 모달을 숨김

        // 모달 내용 생성
        var modalContent = document.createElement('div');
        modalContent.className = 'modal-content';

        var closeButton = document.createElement('span');
        closeButton.className = 'close';
        closeButton.textContent = '×';
        closeButton.addEventListener('click', function () {
            modal.style.display = 'none'; // 닫기 버튼을 누르면 모달을 숨김
        });
        modalContent.appendChild(closeButton);

        var form = document.createElement('form');

        /**
         * 일정명
         * @type {HTMLInputElement}
         */
        var scheTitleLabel = document.createElement('label');
        scheTitleLabel.textContent = '일정명';
        var scheTitleInput = document.createElement('input');
        scheTitleInput.setAttribute('type', 'text');
        scheTitleInput.setAttribute('placeholder', '일정명을 입력해주세요');
        form.appendChild(scheTitleLabel);
        form.appendChild(scheTitleInput);

        /**
         * 시작일시
         * @type {HTMLInputElement}
         */
        var scheStartDateLabel = document.createElement('label');
        scheStartDateLabel.textContent = '시작일시';
        var scheStartDateInput = document.createElement('input');
        scheStartDateInput.setAttribute('type', 'datetime-local');
        form.appendChild(scheStartDateInput);

        /**
         * 종료일시
         * @type {HTMLLabelElement}
         */
        var scheEndDateLabel = document.createElement('label');
        scheEndDateLabel.textContent = '종료일시'
        var scheEndDateInput = document.createElement('input');
        scheEndDateInput.setAttribute('type', 'datetime-local');
        form.appendChild(scheEndDateInput);

        /** 일정 종류
         * - 개인일정, 공식일정 선택
         * @type {HTMLLabelElement}
         */
        var calendarTypeLabel = document.createElement('label');
        calendarTypeLabel.textContent = '일정 종류';

        var calendarDropdown = document.createElement('select');
        var myCalendarOption = document.createElement('option');
        myCalendarOption.textContent = '개인 일정';

        var generalCalendarOption = document.createElement('option');
        generalCalendarOption.textContent = '공식 일정';
        calendarDropdown.appendChild(myCalendarOption);
        calendarDropdown.appendChild(generalCalendarOption);
        form.appendChild(calendarTypeLabel);
        form.appendChild(calendarDropdown);

        /**
         * 장소
         * @type {HTMLLabelElement}
         */
        var locationLabel = document.createElement('label');
        locationLabel.textContent = '장소';
        var locationDropdown = document.createElement('select');
        var conferenceRoomAOption = document.createElement('option');
        conferenceRoomAOption.textContent = '회의실A';
        var conferenceRoomBOption = document.createElement('option');
        conferenceRoomBOption.textContent = '회의실B';
        locationDropdown.appendChild(conferenceRoomAOption);
        locationDropdown.appendChild(conferenceRoomBOption);
        form.appendChild(locationLabel);
        form.appendChild(locationDropdown);

        /**
         * 참고사항
         * @type {HTMLTextAreaElement}
         */
        var notesLabel = document.createElement('label');
        notesLabel.textContent = '참고 사항';
        var notesInput = document.createElement('textarea');
        notesInput.setAttribute('placeholder', '참고 사항');
        form.appendChild(notesLabel);
        form.appendChild(notesInput);

        var submitButton = document.createElement('button');
        submitButton.textContent = '일정 추가';
        submitButton.addEventListener('click', function (event) {
            event.preventDefault();

            var scheTitle = scheTitleInput.value;
            var scheStartDate = scheStartDateInput.value;
            var scheEndDate = scheEndDateInput.value;
            var calendarType = calendarDropdown.value;
            var location = locationDropdown.value;
            var notes = notesInput.value;

            var start = new Date(scheStartDate);
            var end = new Date(scheEndDate);

            if (scheTitle.trim() !== '' && start && end) {
                calendar.addEvent({
                    title: scheTitle,
                    start: start,
                    end: end,
                    extendedProps: {
                        calendar: calendarType,
                        location: location,
                        notes: notes
                    }
                });
                alert('일정이 추가되었습니다.');
                modal.style.display = 'none'; // 일정 추가 후 모달을 닫음
            } else {
                alert('잘못된 입력입니다. 일정명과 날짜를 확인해주세요.');
            }
        });
        form.appendChild(submitButton);

        modalContent.appendChild(form);
        modal.appendChild(modalContent);

        document.body.appendChild(modal);
    }

    calendar.render();
});