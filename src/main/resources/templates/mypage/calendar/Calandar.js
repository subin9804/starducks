document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var modal; // 모달 변수

    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            start: 'dayGridMonth,timeGridWeek',
            center: 'prev title next',
            end: 'today addEventButton'
        },
        customButtons: {
            addEventButton: {
                text: '+ 일정 등록',
                click: function() {
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
        closeButton.addEventListener('click', function() {
            modal.style.display = 'none'; // 닫기 버튼을 누르면 모달을 숨김
        });
        modalContent.appendChild(closeButton);

        var form = document.createElement('form');

        var eventNameInput = document.createElement('input');
        eventNameInput.setAttribute('type', 'text');
        eventNameInput.setAttribute('placeholder', '일정명');
        form.appendChild(eventNameInput);

        var dateInput = document.createElement('input');
        dateInput.setAttribute('type', 'datetime-local');
        form.appendChild(dateInput);

        var calendarInput = document.createElement('input');
        calendarInput.setAttribute('type', 'text');
        calendarInput.setAttribute('placeholder', '캘린더');
        form.appendChild(calendarInput);

        var locationInput = document.createElement('input');
        locationInput.setAttribute('type', 'text');
        locationInput.setAttribute('placeholder', '장소');
        form.appendChild(locationInput);

        var notesInput = document.createElement('textarea');
        notesInput.setAttribute('placeholder', '참고 사항');
        form.appendChild(notesInput);

        var submitButton = document.createElement('button');
        submitButton.textContent = '일정 추가';
        submitButton.addEventListener('click', function(event) {
            event.preventDefault();

            var eventName = eventNameInput.value;
            var dateStr = dateInput.value;
            var calendarName = calendarInput.value;
            var location = locationInput.value;
            var notes = notesInput.value;

            var date = new Date(dateStr);

            if (!isNaN(date.valueOf()) && eventName.trim() !== '') {
                calendar.addEvent({
                    title: eventName,
                    start: date,
                    extendedProps: {
                        calendar: calendarName,
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
