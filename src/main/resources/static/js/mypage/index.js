document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        height: '460',  // 높이를 조절
        headerToolbar: {
            right: '',
            center: 'prev title next',
            left: ''
        },

        scrollTime: "00:00",
        // eventContent: function (arg) {
        //
        // },
        //
        // events:
        //
        // },

    });
    calendar.render();

    // FullCalendar의 헤더에 있는 글씨 크기 조절
    $('.fc-toolbar-chunk').css({
        'font-size': '10px',
        'display': 'flex'
    });
});