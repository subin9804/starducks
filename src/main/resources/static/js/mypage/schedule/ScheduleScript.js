

document.addEventListener('DOMContentLoaded', function () {     // HTML 문서가 로드되면 실행
    var calendarEl = document.getElementById('calendar');   // HTML에서 id가 'calendar'인 요소를 찾아서 변수 calendarEl에 할당
    var modal;  // 변수 modal을 초기화
    var calendar = new FullCalendar.Calendar(calendarEl, {  // calendarEl 요소에 달력을 초기화
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
            if (arg.start && arg.end) { // 빈 셀을 선택했을 때만 모달 생성
                if (!modal) { // 모달이 생성되지 않았을 때만 생성
                    createModal();
                }
                modal.style.display = 'block'; // 모달을 보이게 함
            }
        },
        editable: false,
        dayMaxEvents: true,

        // FullCalendar에서 이벤트를 가져오는 역할
        // DB에서 일정 정보를 가져와서 캘린더에서 표시할 수 있는 형태로 변환하는 역할
        events: function (fetchInfo, successCallback, errorCallback) {
            var empId = 1;

            // 사용자의 일정 정보를 가져옴
            fetchShowSingleSchedule(empId).then(function (data) {   // 서버에 요청을 보내어 해당 사용자의 일정 정보를 가져옴
                console.log("받아온 데이265165터: " + JSON.stringify(data)); // 데이터 확인을 위한 console.log

                var events = data.map(function (schedule)  {
                    console.log(schedule.url)
                    return {
                        // 반환된 일정 정보를 FullCalendar에서 사용 가능한 형식으로 매핑
                        title: schedule.title,
                        start: schedule.start,
                        url: schedule.url,   // 해당 일정의 상세 페이지로 이동
                        end: schedule.end,
                        // extendedProps: {
                        //     scheduleType: schedule.scheduleType,
                        //     notes: schedule.notes,
                        //     empId: schedule.empId
                        // }
                    };
                    console.log("schedule.scheTitle" + schedule.scheTitle);
                });
                successCallback(events);    // FullCalendar에 가져온 이벤트 데이터를 전달
            })
        },
        eventClick: function (info) {
            console.log(info.event.url);
            if(info.event.url) {
                window.open(info.event.url);
            }

        }
    });

    // 서버로부터 특정 사용자의 일정 정보를 가져오는 함수
    // fetch를 사용하여 일정 정보를 요청하고, 받은 응답을 JSON 형태로 변환하여 반환
    function fetchShowSingleSchedule(empId) {
        return fetch('/schedule/show/' + empId)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('네트워크가 좋지 않습니다.');
                }
                return response.text(); // response.json() 대신 response.text()로 변경
            })
            .then(function (data) {
                console.log("받아온 데이터: " + data); // 데이터 확인을 위한 console.log
                return JSON.parse(data); // JSON 형식으로 파싱하여 반환
            })
            .catch(function (error) {
                console.error("에러 발생: " + error); // 에러가 발생한 경우 콘솔에 출력
                throw error; // 에러를 다시 던져서 처리
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

        submitButton.textContent = '일정 등록';
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

    calendar.render();  // 초기 설정이 완료된 달력을 화면에 렌더링
});