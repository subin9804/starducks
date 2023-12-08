document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var modal;
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            start: 'dayGridMonth,timeGridWeek',
            center: 'prev title next',
            end: 'today'
        },
        locale: "ko",
        initialDate: '2023-12-27',
        navLinks: true,
        selectable: true,
        selectMirror: true,
        select: function (arg) {
            if (!modal) { // 모달이 생성되지 않았을 때만 생성
                createModal();
            }
            modal.style.display = 'block'; // 모달을 보이게 함
        },
        editable: false,
        dayMaxEvents: true,
        // DB에서 일정 정보를 가져와서 캘린더에서 표시할 수 있는 형태로 변환하는 역할
        events: function (fetchInfo, successCallback, errorCallback) {
            fetchShowSingleSchedule().then(function (data) {
                var events = data.map(function (schedule) {
                    return {
                        title: schedule.scheTitle,
                        start: schedule.scheStartDate,
                        url: '/schedule/detailSche/' + schedule.code,
                        end: schedule.scheEndDate,
                        extendedProps: {
                            scheduleType: schedule.scheduleType,
                            notes: schedule.notes,
                            empId: schedule.empId
                        }
                    };
                });
                successCallback(events);
            }).catch(function (error) {
                errorCallback(error);
            });
        },
        events: [

            /*[# th:each="cal : ${ calendar }"]*/
            {
                // title: /*[[${cal.title}]]*/,
                // start: /*[[${cal.start}]]*/,
                // url: '/mypage/detailSche/'+/*[[${cal.code}]]*/,
                // end: /*[[${cal.end}]]*/
            },
            /*[/]*/

        ]
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
        class ScheduleTypeEnum {
            static get PERSONAL_SCHEDULE() {
                return 'PERSONAL_SCHEDULE';
            }

            static get OFFICIAL_SCHEDULE() {
                return 'OFFICIAL_SCHEDULE';
            }
        }

        var scheduleTypeLabel = document.createElement('label');
        scheduleTypeLabel.textContent = '일정 종류';
        var scheduleTypeDropdown = document.createElement('select');
        var myScheduleTypeOption = document.createElement('option');
        myScheduleTypeOption.textContent = '개인 일정';
        myScheduleTypeOption.value = ScheduleTypeEnum.PERSONAL_SCHEDULE; // Enum 값 설정
        var generalScheduleOption = document.createElement('option');
        generalScheduleOption.textContent = '공식 일정';
        generalScheduleOption.value = ScheduleTypeEnum.OFFICIAL_SCHEDULE; // Enum 값 설정
        scheduleTypeDropdown.appendChild(myScheduleTypeOption);
        scheduleTypeDropdown.appendChild(generalScheduleOption);
        form.appendChild(scheduleTypeLabel);
        form.appendChild(scheduleTypeDropdown);

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
        submitButton.textContent = '등록';
        submitButton.addEventListener('click', function (event) {
            event.preventDefault();
            var scheTitle = scheTitleInput.value;
            var scheStartDate = scheStartDateInput.value;
            var scheEndDate = scheEndDateInput.value;
            var scheduleType = scheduleTypeDropdown.value;
            var notes = notesInput.value;

            var start = new Date(scheStartDate);
            var end = new Date(scheEndDate);

            if (scheTitle.trim() !== '' && start && end) {
                calendar.addEvent({
                    title: scheTitle,
                    start: start,
                    end: end,
                    extendedProps: {
                        calendar: scheduleType,
                        notes: notes
                    }
                });
                modal.style.display = 'none'; // 일정 추가 후 모달을 닫음

                var data = {
                    scheTitle: scheTitle,
                    scheStartDate: scheStartDate,
                    scheEndDate: scheEndDate,
                    scheduleType: scheduleType,
                    notes: notes
                };

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
                        alert("일정이 성공적으로 추가되었습니다."); // 성공 또는 오류 메시지
                    })
                    .catch(function (error) {
                        alert('일정 추가 중 오류 발생: ' + error.message);
                    });
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