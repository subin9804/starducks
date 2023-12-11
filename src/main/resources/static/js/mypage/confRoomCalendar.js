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
                console.log(book.room)
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
                    memo: book.text
                }
                events.push(event);
            }
        }

        console.log(events);


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
            eventRender: function (info) {
                console.log(info.el.style.color);
                // 각 이벤트에 대한 스타일 지정
                info.el.style.backgroundColor = info.event.extendedProps.backgroundColor || 'lightgray'; // 기본값은 lightgray
                info.el.style.textColor = "black";
            },
            eventClick: function (info) {
                console.log("인포!!:" + JSON.stringify((info)))
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

                }

                showBookedPopup(data);
            },
            select: function(info) {
                alert('Selected: ' + info.startStr + ' to ' + info.endStr);
                alert('Resource ID: ' + roomId);
                let data = [info.startStr, info.startStr, info.endStr]

                showBookingPopup(data);
                // $('#calendar').fullCalendar('unselect');
            },
            eventOverlap: function(stillEvent, movingEvent) {
                // 중복 여부를 확인하고 중복을 허용할지 결정
                return !isEventOverlapping(movingEvent);
            }
        });
        calendar.render();

        $(".rooms").click((e) => {
            let id = e.target.innerText
            roomId = id;

            let eventData = [];
            console.log(id + " clicked")
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
                    console.log(event)
                    eventData.push(event);
                }
            }

            changeRoomColor (e)

            // FullCalendar의 events 옵션 업데이트
            calendar.setOption('events', eventData);
            calendar.renderEvents();
        });

    }).fail(function(error) {
        // 에러처리
        alert(error.message);
    })



    // 예약하기 모달창 띄우기
    function showBookingPopup(data) {
        let modal = $('#bookingModal')

        $('#room').val(roomId);
        $('#runningDay').val(dateFormat(data[0]));
        $('#startTime').val(timeFormat(data[1]));
        $('#endTime').val(timeFormat(data[2]));

        console.log(data)
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
           submit(e, 'post', null)
        });
    }

    // 예약 조회 모달창 띄우기
    function showBookedPopup(data) {
        let modal = $('#showModal')
        console.log("data" + JSON.stringify(data));

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

        $("#edit-book").click(function (e) {
            putBookModal(data);
        })

        $("#delete-book").click(function (e) {
            deleteBook(data.id);
        })
    }

    // 예약 수정 모달창 띄우기
    function putBookModal(data) {
        console.log("수정하기!")

        let modal = $('#bookingModal')
        console.log("data22" + JSON.stringify(data));

        $('#room').val(data.resourceId);
        $('#runningDay').val(dateFormat(data.start.toISOString()));
        $('#startTime').val(data.start.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' }));
        $('#endTime').val(data.end.toLocaleTimeString('ko-KR', { hour12: false, hour: '2-digit', minute: '2-digit', second: '2-digit', timeZone: 'Asia/Seoul' }));
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
            submit(e, 'put', data.id);
        });
    }



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

function dateFormat(date) {
    // date input에 넣을 값을 YYYY-MM-DD 형식으로 생성
    let formattedDate = date.substring(0, 10);
    return formattedDate;
}

function timeFormat(time) {
    // time input에 넣을 값을 YYYY-MM-DD 형식으로
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
function isEventOverlapping(newEvent) {
    var existingEvents = $('#calendar').fullCalendar('clientEvents');

    for (var i = 0; i < existingEvents.length; i++) {
        var existingEvent = existingEvents[i];
        if (
            newEvent.start.isBefore(existingEvent.end) &&
            newEvent.end.isAfter(existingEvent.start)
        ) {
            // 중복되는 경우
            return true;

        }
    }

    // 중복되지 않은 경우
    return false;
}