document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var modal; // 모달 변수

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
        var eventNameLabel = document.createElement('label');
        eventNameLabel.textContent = '일정명';
        var eventNameInput = document.createElement('input');
        eventNameInput.setAttribute('type', 'text');
        eventNameInput.setAttribute('placeholder', '일정명을 입력해주세요');
        form.appendChild(eventNameLabel);
        form.appendChild(eventNameInput);

        /**
         * 일시
         * @type {HTMLInputElement}
         */
        var dateLabel = document.createElement('label');
        dateLabel.textContent = '일시';
        var startDateInput = document.createElement('input');
        startDateInput.setAttribute('type', 'datetime-local');
        form.appendChild(startDateInput);

        var endDateInput = document.createElement('input');
        endDateInput.setAttribute('type', 'datetime-local');
        form.appendChild(endDateInput);

        /**
         * 내 캘린더
         * 내 일정, 총무부 일정 선택
         * @type {HTMLLabelElement}
         */
        var calendarLabel = document.createElement('label');
        calendarLabel.textContent = '내 캘린더';
        var calendarDropdown = document.createElement('select');
        var myCalendarOption = document.createElement('option');
        myCalendarOption.textContent = '내 캘린더';
        var generalCalendarOption = document.createElement('option');
        generalCalendarOption.textContent = '총무부 캘린더';
        calendarDropdown.appendChild(myCalendarOption);
        calendarDropdown.appendChild(generalCalendarOption);
        form.appendChild(calendarLabel);
        form.appendChild(calendarDropdown);

        /**
         * 장소
         * @type {HTMLLabelElement}
         */
        var locationLabel = document.createElement('label');
        locationLabel.textContent = '장소';
        var locationDropdown = document.createElement('select');
        var firstRoomOption = document.createElement('option');
        firstRoomOption.textContent = '회의실1';
        var secondRoomOption = document.createElement('option');
        secondRoomOption.textContent = '회의실2';
        locationDropdown.appendChild(firstRoomOption);
        locationDropdown.appendChild(secondRoomOption);
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

            var eventName = eventNameInput.value;
            var startDate = startDateInput.value;
            var endDate = endDateInput.value;
            var calendarType = calendarDropdown.value;
            var location = locationDropdown.value;
            var notes = notesInput.value;

            var start = new Date(startDate);
            var end = new Date(endDate);

            if (eventName.trim() !== '' && !isNaN(start.valueOf()) && !isNaN(end.valueOf())) {
                calendar.addEvent({
                    title: eventName,
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