document.addEventListener('DOMContentLoaded', function() {
    const csrfToken = document.querySelector("meta[name='_csrf']").content;
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;

    let schedules;
    fetchSchedule().then (() => {

        console.log(schedules)
        let events = [];


        for(let schedule of schedules) {
            let event = {
                id: schedule.scheNo,
                title: schedule.scheTitle,
                start: schedule.scheStartDate,
                end: schedule.scheEndDate,
                note: schedule.notes,
                backgroundColor: '#d5ffe4'
            }
            events.push(event);

            // html에 리스트 삽입
            let tr = $('<tr>');
            let date = $('<td>');
            let title = $('<td>');

            let editDate = (schedule.scheStartDate).substring(5, 10)


            tr.addClass('d-flex');
            date.text(editDate);
            title.text(schedule.scheTitle);

            tr.append(date);
            tr.append(title);

            $('#tbody').append(tr)
        }
        console.log(events)
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            height: '405',  // 높이를 조절
            headerToolbar: {
                right: '',
                center: 'prev title next',
                left: ''
            },

            scrollTime: null,

            events: events,
            eventRender: function (info) {
                // 이벤트가 렌더링될 때 호출되는 콜백 함수

                // info.el은 이벤트 요소를 나타냅니다.
                var eventElement = info.el;

                // 이벤트 요소의 내용을 title로 설정
                eventElement.innerHTML = info.event.title;
            },
            // FullCalendar가 월을 변경할 때 호출되는 콜백 함수
            // viewRender: function (view, element) {
            //
            // }
        });
        calendar.render();
    })

    function fetchSchedule() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: "/mypage/api/show",
                method: 'GET',
                dataType: 'json',
                // headers: {
                //     'Content-Type': 'application/json',
                //     [csrfHeader]: csrfToken // CSRF 토큰을 헤더에 추가
                // },
                success: function (data) {
                    schedules = data;
                    console.log(data)
                    resolve(data)
                },
                error: function (err) {
                    reject(err);
                }
            })
        })
    }




    // FullCalendar의 헤더에 있는 글씨 크기 조절
    $('.fc-toolbar-chunk').css({
        'font-size': '10px',
        'display': 'flex'
    });
});