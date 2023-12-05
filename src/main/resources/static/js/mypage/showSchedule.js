document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var modal;

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
        },
        events: function (fetchInfo, successCallback, errorCallback) {
            fetchShowSingleSchedule().then(function (data) {
                var events = data.map(function (schedule) {
                    return {
                        title: schedule.scheTitle,
                        start: schedule.scheStartDate,
                        end: schedule.scheEndDate,
                        extendedProps: {
                            scheduleType: schedule.scheduleType,
                            location: schedule.location,
                            notes: schedule.notes,
                            empId: schedule.empId
                        }
                    };
                });
                successCallback(events);
            }).catch(function (error) {
                errorCallback(error);
            });
        }
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
    calendar.render();
});