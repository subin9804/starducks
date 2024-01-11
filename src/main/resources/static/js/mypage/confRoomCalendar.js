$(document).ready(function () {
    let roomId = "ROOM1";

    // 리소스 데이터 요청
    let req1 = $.ajax({
        url: "/mypage/conf/room",
        type: "GET",
        dataType: "json"
    });
    // 이벤트 데이터 요청
    let req2 = $.ajax({
        url: "/mypage/conf/list",
        type: "GET",
        dataType: "json"
    });

    // 두 개의 ajax 요청이 완료될 때까지 대기
    $.when(req1, req2).done(function (rooms, list) {
        // 기본 view는 ROOM1
        let resources = rooms[0];
        let bookList = list[0];
        console.log(bookList);

        let events = [];
        for(let book of bookList) {
            if(book.room == "ROOM1") {
                // console.log(book.room)
                let event = {
                    id: book.confId,
                    resourceId: book.room,
                    start: book.runningDay + 'T' + book.startTime,
                    end: book.runningDay + 'T' + book.endTime,
                    title: book.confName,
                    color: book.color,
                    textColor: "black",
                    bookerId: book.bookerId,
                    bookerNm: book.bookerNm,
                    dept: book.dept,
                    memo: book.text,
                    status: book.status
                }
                events.push(event);
            }
        }

        // console.log(events);


        // FullCalendar 라이브러리 사용
        var calendar = new FullCalendar.Calendar($('#scheduler')[0], {
            initialView: 'timeGridWeek', // 기본 뷰 설정
            slotMinTime: '08:00:00',
            slotMaxTime: '20:00:00',
            height: 705,
            allDaySlot: false,
            resources: resources,
            selectable: true,
            events: events,
            locale: "ko",
            eventRender: function (info) {
                // console.log(info.el.style.color);
                // 각 이벤트에 대한 스타일 지정
                info.el.style.backgroundColor = info.event.extendedProps.backgroundColor || 'lightgray'; // 기본값은 lightgray
                info.el.style.textColor = "black";
            },
            eventClick: function (info) {
                // console.log("인포!!:" + JSON.stringify((info)))
                // alert('Event clicked: ' + info.event.title);

                let data = {
                    title: info.event.title,
                    start: info.event.start,
                    end: info.event.end,
                    id: info.event.id,
                    color: info.event.backgroundColor,
                    resourceId: roomId,
                    bookerId: info.event.extendedProps.bookerId,
                    bookerNm: info.event.extendedProps.bookerNm,
                    dept: info.event.extendedProps.dept,
                    memo: info.event.extendedProps.memo,
                    status: info.event.extendedProps.status

                }


                showBookedPopup(data);

            },
            select: function(info) {
                // alert('Selected: ' + info.startStr + ' to ' + info.endStr);
                // alert('Resource ID: ' + roomId);
                let data = [info.startStr, info.startStr, info.endStr]

                /** 이전 날짜 방지 코드 */
                let today = new Date()
                let selected = new Date(info.start)
                if(today > selected) {
                    errorAlert("현재 시간 이후로 예약이 가능합니다.")
                    .then(() => {
                        location.reload();
                    })

                /** 현재 시간 이후로는 예약 가능 */
                } else {

                    /** 중복 방지 코드 S */
                    if (!isOverlapping(roomId, info.startStr, info.endStr, bookList)) {
                        // 중복이 아니라면 팝업 오픈
                        showBookingPopup(data);
                    } else {
                        // 중복이라면 경고창 표시
                        errorAlert("중복예약은 불가합니다.").then(() => {
                            location.reload();
                        })
                    }
                    /** 중복 방지 코드 E */
                }
            }
        });
        calendar.render();

        // 회의실 탭을 클릭하면 해당 회의실 예약 현황으로 리렌더링
        $(".rooms").click((e) => {
            let id = e.target.innerText
            roomId = id;

            let eventData = [];
            // console.log(id + " clicked")
            for(let book of bookList) {
                if (book.room == id) {
                    let event = {
                        id: book.confId,
                        resourceId: id,
                        start: book.runningDay + 'T' + book.startTime,
                        end: book.runningDay + 'T' + book.endTime,
                        title: book.confName,
                        color: book.color,
                        textColor: "black",
                        bookerId: book.bookerId,
                        bookerNm: book.bookerNm,
                        dept: book.dept,
                        memo: book.text
                    }
                    // console.log(event)
                    eventData.push(event);
                }
            }

            changeRoomColor (e)

            // FullCalendar의 events 옵션 업데이트
            calendar.setOption('events', eventData);
            calendar.renderEvents();
        });


        // 예약하기 모달창 띄우기
        function showBookingPopup(data) {
            let modal = $('#bookingModal')

            $('#room').val(roomId);
            $('#runningDay').val(dateFormat(data[0]));
            $('#startTime').val(timeFormat(data[1]));
            $('#endTime').val(timeFormat(data[2]));

            // console.log(data)
            modal.css('display', 'block');
            setTimeout(function() {
                $('#bookingForm').css({
                    'display': 'block',
                    'transform': 'translateY(50px)',
                    'opacity': '1'
                })
            }, 10)
            $('body').css('overflow', 'hidden');

            $("#submitBtn").click((e) => {
                // 중복 방지 코드
                let start = $('#runningDay').val() + 'T' + $('#startTime').val();
                let end = $('#runningDay').val() + 'T' + $('#endTime').val();

                if(!isOverlapping($('#room').val(), start, end, bookList)) {
                    // 중복이 아니라면 전송
                    submit(e, 'post', null)

                } else {
                    // 중복이라면 경고창 표시
                    errorAlert("중복예약은 불가합니다.")
                    .then(() => {
                        location.reload();
                    })
                }
            });
        }

        // 예약 수정 모달창 띄우기
        function putBookModal(data) {
            // console.log("수정하기!")

            let modal = $('#bookingModal')
            console.log("data22" + JSON.stringify(data));

            let startTime = data.start.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' })
            let endTime = data.end.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' })

            $('#room').val(data.resourceId);
            $('#runningDay').val(dateFormat(data.start.toISOString()));
            $('#startTime').val(startTime);
            $('#endTime').val(endTime);
            $('#confName').val(data.title);
            $('#text').val(data.memo);
            $('#color').val(data.color);

            $('#showModal').css('display', 'none');
            modal.css('display', 'block');
            $('#bookingForm').css({
                'display': 'block',
                'opacity': '1'
            })
            $('body').css('overflow', 'hidden');

            $("#submitBtn").click((e) => {
                // 중복 방지 코드
                let start = new Date($('#runningDay').val() + 'T' + $('#startTime').val()); // 선택한 시작시간
                let end = new Date($('#runningDay').val() + 'T' + $('#endTime').val());   // 선택한 종료시간

                let dupl = false;

                for(let event of bookList) {
                    let today = new Date()
                    let eventStart = new Date(event.runningDay + 'T' + event.startTime);
                    let eventEnd = new Date(event.runningDay + 'T' + event.endTime);
                    let eventId = event.confId;
                    let message = "";


                    if(data.id == eventId) {
                        continue;
                    }

                    // 시작 시간이 현재보다 이전이면 예약 불가
                    if(today > start) {
                        dupl = true;
                        message = errorAlert("현재 시간 이후로 예약이 가능합니다.")
                    } else {
                        dupl = false;

                        if((start < eventEnd) && (end > eventStart)) {
                            // 시간도 중복되고 회의실도 중복되면 예약 불가
                            if ($('#room').val() == event.room) {
                                dupl = true;
                                message = errorAlert("중복예약은 불가합니다.")
                            } else {
                                dupl = false;
                            }
                        }
                    }

                    console.log(dupl)
                    console.log("eventStart" + eventStart)
                    console.log("eventEnd" + eventEnd)

                }

                if(!dupl) {
                    // 중복이 아니라면 전송
                    submit(e, 'put', data.id);
                } else {
                    // 중복이라면 경고창 표시
                    errorAlert(message)
                        .then(() => {
                            location.reload();
                        })
                    console.log("실패!")
                }
            });
        }

        // 예약 조회 모달창 띄우기
        function showBookedPopup(data) {
            let modal = $('#showModal')
            // console.log("data" + JSON.stringify(data));

            $('#sRoom').text(data.resourceId);
            $('#dept').text(data.dept);
            $('#empName').text(data.bookerNm);
            $('#sRunningDay').text(dateFormat(data.start.toISOString()));
            $('#sStartT').text(data.start.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' }))
            $('#sEndT').text(data.end.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' }));
            $('#sConfName').text(data.title);
            $('#sMemo').text(data.memo);

            modal.css('display', 'block');
            $('body').css('overflow', 'hidden');
            setTimeout(function() {
                $('#showModal-content').css({
                    'display': 'block',
                    'transform': 'translateY(70px)',
                    'opacity': '1'
                })
            }, 10)

            // 작성자와 로그인 계정 아이디가 다를 경우 삭제와 수정 버튼 안보이게 처리
            if((data.bookerId != $('#empId').val())) {
                $('#btns').css('display', 'none');
            }

            // 이미 지난 회의일 경우 삭제와 수정 버튼 안보이게 처리 + 완료 표시 추가
            if((new Date(data.runningDay + 'T' + data.start) < new Date())) {
                $('#btns').css('display', 'none');

                // 새로운 <p> 요소를 생성
                let newParagraph = $('<p>');

                newParagraph.text("완료된 회의입니다.");

                newParagraph.css({
                    'padding': '10px',
                    'border': '5px solid black',
                    'text-align': 'center',
                    'font-weight': 'bold'
                });

                $("#content").append(newParagraph);
            }

            $("#edit-book").click(function (e) {
                putBookModal(data);
            })

            $("#delete-book").click(function (e) {
                if(data.bookerId == $('#empId').val()) {
                    deleteBook(data.id);
                }
            })
        }

    }).fail(function(error) {
        // 에러처리
        alert(error.message);
    })



    // 모달창 닫기
    $('#closeModalBtn').click(function() {
        closeModal();
    });
    $('#closeBtn').click(function() {
        closeModal();
    });

    $('.customModal').click(function(event) {
        if (event.target === this) {
            closeModal();
        }
    });

    function closeModal() {
        // $('#room').val('');
        // $('#runningDay').val('');
        // $('#startTime').val('');
        // $('#endTime').val('');
        // $('#confName').val('');
        // $('#text').val('');
        // $('#color').val("#f00");

        // $('.customModal').css('display', 'none')
        // $('body').css('overflow', 'auto');
        // $('.modal-content').css({
        //     'opacity': '0',
        //     'transform': 'translateY(0)'
        // }

        location.reload();
    }

});

// date input에 넣을 값을 YYYY-MM-DD 형식으로 생성
function dateFormat(date) {
    let formattedDate = date.substring(0, 10);
    return formattedDate;
}

// time input에 넣을 값을 YYYY-MM-DD 형식으로
function timeFormat(time) {
    let formattedtime = time.toString().slice(11, 19);
    return formattedtime;
}

function changeRoomColor (e) {
    console.log(e)
    roomId = e.textContent;

    for (let room of $('.rooms')) {
        $(room).removeClass('selected');
    }
    $(e.currentTarget).addClass('selected')
}



// 중복 여부 체크 함수
function isOverlapping(room, newStart, newEnd, events) {
    /** 중복 방지 코드 S */
    let dupl = false;

    const infoStartStr = new Date(newStart);   // 선택한 시작시간
    const infoEndStr = new Date(newEnd);       // 선택한 종료시간

    // 모든 예약들이 선택한 시간과 중복 되지 않는지 확인
    for (let i = 0; i < events.length; i++) {
        let existingEvent = events[i];
        const eventStart = new Date((existingEvent.runningDay + 'T' + existingEvent.startTime));
        const eventEnd = new Date((existingEvent.runningDay + 'T' + existingEvent.endTime));

        if ((infoStartStr < eventEnd) && (infoEndStr > eventStart)) {
            // 중복되는 경우
            if (room == existingEvent.room) {

                return true;
            }
        }

        // console.log("info.startStr: " +  infoStartStr)
        // console.log("existingEvent.start: " + eventStart)
    }
    // 중복되지 않는 경우
    return false;
}