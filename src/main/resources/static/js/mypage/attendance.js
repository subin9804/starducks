document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        height: 800,
        initialView: 'dayGridMonth',
        headerToolbar: {
            right: '',
            center: 'prev title next',
            left: ''
        },
        // eventContent: 'some text',
        events: [
            {
                title: '출근 | 9:00',
                color:'rgba(1,1,1,0)',
                textColor: 'black',
                start: '2023-11-27'
            },
            {
                title: '퇴근 | 6:00',
                color:'rgba(1,1,1,0)',
                textColor: 'black',
                start: '2023-11-27'
            }
        ]
    });
    calendar.render();
});