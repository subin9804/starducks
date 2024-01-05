document.addEventListener('DOMContentLoaded', function () {
    // CSRF 토큰과 헤더 이름을 읽어옴
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;

    var calendarEl = document.getElementById('calendar');
    var modal = document.getElementById('addModal'); // HTML 모달 창

    // 서버 측 principal에서 empId 가져 오기
    var empId = $('#empId').val();
    console.log("empId ==> " + empId);

// 모달을 열 때 호출되는 함수
    function showModal() {
        modal.style.display = 'block';

        // 모달 창에 CSS를 적용
        modal.style.cssText = `
        display: block;
    `;

        setTimeout(function () {
            $('#bookingForm').css({
                'display': 'block',
                'transform': 'translateY(50px)',
                'opacity': '1'
            });
        }, 10);

        $('body').css('overflow', 'hidden');
    }

// 모달의 "x" 모양을 나타내는 요소를 가져옵니다.
    var closeModalBtn = document.getElementById('closeModalBtn');

// "x" 모양 요소에 클릭 이벤트 리스너를 추가합니다.
    closeModalBtn.addEventListener('click', function () {
        hideModal(); // 모달을 닫는 함수 호출
    });


// 모달을 닫을 때 호출되는 함수
    function hideModal() {
        modal.style.display = 'none';

        // 모달 창에서 CSS를 제거
        modal.style.cssText = '';

        $('#bookingForm').css({
            'display': 'none',
            'transform': '',
            'opacity': ''
        });

        $('body').css('overflow', '');
    }


// 서버로부터 특정 사용자의 일정 정보를 가져오는 함수
    function fetchShowSingleSchedule(empId) {
        return axios.get('/mypage/schedule/api/show', {
            params: {empId: empId}
        })
            .then(function (response) {
                var contentType = response.headers['content-type'];
                if (contentType && contentType.indexOf('application/json') !== -1) {
                    // JSON 형식의 응답
                    console.log('서버 응답은 JSON 형식입니다.');
                    console.log('서버 응답 데이터:', response.data); // 전체 서버 응답 데이터 출력
                    return response.data; // JSON 데이터 반환
                } else {
                    console.log('서버 응답은 JSON 형식이 아닙니다.');
                    throw new Error('서버 응답이 JSON 형식이 아닙니다.');
                }
            })
            .catch(function (error) {
                console.error('에러 발생:', error);
                throw error;
            });
    }

// fetchAndAddEventsToCalendar() 함수 내에서 호출 직전
    function fetchAndAddEventsToCalendar() {
        console.log("fetchAndAddEventsToCalendar 함수 호출 직전:", new Date());
        fetchShowSingleSchedule(empId)
            .then(function (data) {
                // 로드한 일정 데이터를 캘린더에 추가
                addEventsToCalendar(data);
                console.log("fetchAndAddEventsToCalendar에 있는 data:");
                console.log(data);
            })
            .catch(function (error) {
                console.error("에러 발생: " + error);
            });
    }

// 모달 내의 제출 버튼 이벤트 리스너
    function handleSubmitButtonClick(event) {
        event.preventDefault();

        var scheNo = document.getElementById('scheNo').value;
        var scheTitle = document.getElementById('scheTitle').value;
        var scheStartDateInput = document.getElementsByName('scheStartDate')[0];
        var scheEndDateInput = document.getElementsByName('scheEndDate')[0];

        // LocalDate 객체 생성
        var scheStartDate = new Date(scheStartDateInput.value);
        var scheEndDate = new Date(scheEndDateInput.value);

        // 종료일에 하루를 더해줌
        scheEndDate.setDate(scheEndDate.getDate() + 1);

        var scheduleType = document.getElementsByName('scheduleType')[0].value;
        var notes = document.getElementsByName('notes')[0].value;

        // 서버로 보낼 데이터 객체 생성
        var data = {
            empId: empId,
            scheTitle: scheTitle,
            scheStartDate: scheStartDate,
            scheEndDate: scheEndDate,
            scheduleType: scheduleType,
            notes: notes
        };

        // 서버로 데이터를 전송하는 Axios 요청
        axios.post('/mypage/schedule/add', data, {
            headers: {
                'Content-Type': 'application/json', // JSON 데이터를 보낼 때 Content-Type을 application/json으로 설정
                [csrfHeaderName]: csrfToken
            }
        })
            .then(function (response) {
                if (!response.data.ok) {
                    throw new Error('네트워크가 좋지 않습니다.');
                }
                return response.data;
            })
            .then(function (responseData) {
                alert("일정이 성공적으로 추가되었습니다."); // 성공 또는 오류 메시지

                // 서버로부터 반환받은 일정 ID를 사용하여 새 이벤트의 URL을 설정
                var newEvent = {
                    empId: empId,
                    title: scheTitle,
                    start: scheStartDate,
                    end: scheEndDate,
                    scheduleType: scheduleType,
                    url: '/mypage/schedule/detail/' + responseData.scheNo,
                };
                console.log("responseData.scheNo ==> " + responseData.scheNo);
                calendar.addEvent(newEvent);

                // 모달을 닫음
                hideModal();
            })
            .catch(function (error) {
                console.error("에러 발생: " + error);
                throw error;
            });
    }

// 캘린더 객체 생성
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            start: 'dayGridMonth,timeGridWeek',
            center: 'prev title next',
            end: 'today'
        },
        locale: "ko",
        navLinks: true,
        selectable: true,
        selectMirror: true,
        // 이벤트명 : function(){} : 각 날짜에 대한 이벤트를 통해 처리할 내용
        select: function (arg) {
            if (arg.start && arg.end) { // 빈 셀을 선택했을 때만 모달 생성
                showModal(); // 모달을 표시
            }
        },
        editable: false,        // 툴바 이동 금지
        dayMaxEvents: true,
        displayEventTime: false // 시간 표시 x
});

// 일정을 캘린더에 추가하는 함수
    function addEventsToCalendar(events) {
        if (events && Array.isArray(events)) {
            events.forEach(function (eventData) {
                console.log("eventData:", eventData);

                // 종료일에 하루를 더해주는 조건 추가
                if (eventData.allDay) {
                    eventData.end = moment(eventData.end).add(1, 'days');
                }

                var newEvent = {
                    empId: eventData.empId,
                    title: eventData.scheTitle,
                    start: eventData.scheStartDate,
                    end: eventData.scheEndDate,
                    scheduleType: eventData.scheduleType,
                    notes: eventData.notes, // 이벤트 데이터에 notes 속성 추가
                    url: '/mypage/schedule/detail/' + eventData.scheNo,
                };
                calendar.addEvent(newEvent);
            });
        }
        console.log("addEventsToCalendar 함수 호출됨", events); // 확인을 위한 로그 출력
    }

// 서버로부터 일정 정보를 가져오는 함수 정의
    calendar.setOption('eventSources', [
        {
            url: '/mypage/schedule/api/show',
            method: 'GET',
            extraParams: {
                empId: empId
            },
            failure: function (error) {
                console.error("에러 발생: " + error);
            },
            color: 'blue', // 이벤트 색상 등 설정 가능
        }
    ]);

// 초기 일정 로드
    fetchShowSingleSchedule(empId)
        .then(function (data) {
            console.log("일정 데이터를 불러옴:", new Date());
            // 로드한 일정 데이터를 캘린더에 추가
            addEventsToCalendar(data)
            console.log("fetchAndAddEventsToCalendar에 있는 data:");
            console.log(data);
        })
        .catch(function (error) {
            console.error("에러 발생: " + error);
        });

    calendar.render();
});