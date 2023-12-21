document.addEventListener('DOMContentLoaded', function () {
    // CSRF 토큰과 헤더 이름을 읽어옴
    var csrfToken = document.querySelector('meta[name="_csrf"]').content;
    console.log("CSRF Token ==> " + csrfToken);

    var csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    console.log("CSRF Header Name ==> " + csrfHeaderName);

    var calendarEl = document.getElementById('calendar');
    var modal;

    function getEmpId() {
        var empId = sessionStorage.getItem('empId');
        return empId;
    }

    // 서버로부터 특정 사용자의 일정 정보를 가져오는 함수
    function fetchShowSingleSchedule(empId) {
        return fetch('/mypage/schedule/api/show', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfToken
            }
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('네트워크가 좋지 않습니다.');
                }
                return response.json();
            })
            .then(function (data) {
                console.log("데이터 리스트!!!!!! 나오니?????" + data);
                return data;
            })
            .catch(function (error) {
                console.error("에러 발생: " + error);
                throw error;
            });
    }

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
        // 이벤트명 : function(){} : 각 날짜에 대한 이벤트를 통해 처리할 내용..
        select: function (arg) {
            if (arg.start && arg.end) { // 빈 셀을 선택했을 때만 모달 생성
                if (!modal) { // 모달이 생성되지 않았을 때만 생성
                    createModal();
                }
                modal.style.display = 'block'; // 모달을 보이게 함
            }
        },
        editable: false,        // 툴바 이동 금지
        dayMaxEvents: true,
        // DB에서 일정 정보를 가져와서 캘린더에서 표시할 수 있는 형태로 변환하는 역할
        events: function (fetchInfo, successCallback, errorCallback) {
            // 사용자의 ID를 얻어오는 함수 호출
            var empId = getEmpId();

            // 해당 사용자의 일정 정보를 가져옴
            fetchShowSingleSchedule(empId).then(function (data) {
                var events = data.map(function (schedule) {
                    return {
                        // 반환된 일정 정보를 FullCalendar에서 사용 가능한 형식으로 매핑
                        title: schedule.scheTitle,      // 일정 제목
                        start: schedule.scheStartDate, // 시작일시
                        end: schedule.scheEndDate,     // 종료일시
                        url: '/mypage/schedule/detail/' + schedule.scheNo, // 상세 페이지 UR
                    };
                });
                // 변환한 이벤트 데이터를 FullCalendar에 성공 콜백으로 전달합니다.
                successCallback(events);    // FullCalendar에 가져온 이벤트 데이터를 전달
            })
                .catch(function (error) {
                    console.error("에러 발생: " + error);
                    throw error;
                });
        },
        eventClick: function (info) {
            // console.log(info.event.url);
            if (info.event.url) {
                window.location.href = info.event.url; // 클릭한 일정의 URL로 이동
            }
        }
    });

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

            var data = {
                scheTitle: scheTitle,
                scheStartDate: scheStartDate,
                scheEndDate: scheEndDate,
                scheduleType: scheduleType,
                notes: notes
            };

            fetch('/mypage/schedule/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeaderName]: csrfToken // 여기에서 CSRF 토큰을 헤더에 추가
                },
                body: JSON.stringify(data)
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error('네트워크가 좋지 않습니다.');
                    }
                    return response.json();
                })
                .then(function (responseData) {
                    alert("일정이 성공적으로 추가되었습니다."); // 성공 또는 오류 메시지

                    // 서버로부터 반환받은 일정 ID를 사용하여 새 이벤트의 URL을 설정
                    var newEvent = {
                        title: scheTitle,
                        start: scheStartDate,
                        end: scheEndDate,
                        url: '/mypage/schedule/detail/' + responseData.scheNo,
                    };
                    console.log("responseData.scheNo ==> " + responseData.scheNo);
                    calendar.addEvent(newEvent);

                    modal.style.display = 'none';
                })
                .catch(function (error) {
                    console.error("에러 발생: " + error);
                    throw error;
                });
        });

        form.appendChild(submitButton);
        modalContent.appendChild(form);
        modal.appendChild(modalContent);
        document.body.appendChild(modal);
    }

    fetchShowSingleSchedule();

    calendar.render();
});
