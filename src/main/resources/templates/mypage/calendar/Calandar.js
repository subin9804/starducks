document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

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
                    var dateStr = prompt('Enter a date in YYYY-MM-DD format');
                    var date = new Date(dateStr + 'T00:00:00');

                    if (!isNaN(date.valueOf())) {
                        calendar.addEvent({                             title: 'dynamic event', // 이벤트 제목
                            start: date, // 이벤트 시작일
                            allDay: true // 종일 이벤트 여부
                        });
                        alert('일정 등록 완료'); // 성공 알림 메시지
                    } else {
                        alert('다시 선택하시오'); // 날짜가 유효하지 않을 때의 알림 메시지
                    }
                }
            }
        }
    });

    calendar.render(); // 캘린더를 렌더링합니다.
});
