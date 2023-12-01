// 예제에서는 /api/attendance/daily/{empId} 엔드포인트로 요청하여 JSON 데이터를 받아오는 함수
function fetchDailyAttendance(empId) {
    fetch(`/api/attendance/daily/${empId}`)
        .then(response => response.json())
        .then(data => {
            console.log('Daily Attendance:', data);
            // 여기서 받아온 JSON 데이터(data)를 활용하여 필요한 작업을 수행
        })
        .catch(error => console.error('Error fetching data:', error));
}

// 예제에서는 사용자의 EmpId를 1로 가정하고 호출
fetchDailyAttendance(1);


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
        events: function (fetchInfo, successCallback, failureCallback) {
            // fetchDailyAttendance 함수 호출
            fetchDailyAttendance().then(function (data) {
                // FullCalendar 이벤트 형식으로 데이터 변환
                var events = data.map(function (attendance) {
                    return {
                        title: '출근 | ' + attendance.startTime,
                        color: 'rgba(1,1,1,0)',
                        textColor: 'black',
                        start: attendance.workDate
                    };
                });

                // 성공 콜백 호출
                successCallback(events);
            }).catch(function (error) {
                // 실패 콜백 호출
                failureCallback(error);
            });
        },
    });
    calendar.render();
});

function fetchDailyAttendance() {
    // 서버에서 출근 정보를 가져오는 비동기 요청
    return fetch('/api/attendance/daily/{empId}') // empId에 실제 값 적용 필요
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}