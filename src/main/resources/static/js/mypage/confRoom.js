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
            height: 800,
            allDaySlot: false,
            resources: resources,
            selectable: true,
            events: events,
            eventRender: function (info) {
                // 각 이벤트에 대한 스타일 지정
                info.el.style.backgroundColor = info.event.extendedProps.color || 'lightgray'; // 기본값은 lightgray
            },
            eventClick: function (info) {
                // console.log("인포!!:" + JSON.stringify((info)))
                // alert('Event clicked: ' + info.event.title);

                let data = {
                    title: info.event.title,
                    start: info.event.start,
                    end: info.event.end,
                    id: info.event.id,
                    resourceId: roomId,
                    bookerId: info.event.extendedProps.bookerId,
                    bookerNm: info.event.extendedProps.bookerNm,
                    dept: info.event.extendedProps.dept,
                    memo: info.event.extendedProps.memo

                }

                showBookedPopup(data);
            },
            select: function(info) {
                alert('Selected: ' + info.startStr + ' to ' + info.endStr);
                alert('Resource ID: ' + roomId);
                let data = [info.startStr, info.startStr, info.endStr]

                showBookingPopup(data);
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
            calendar.rerenderEvents();
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
    }

    // 예약 조회 모달창 띄우기
    function showBookedPopup(data) {
        let modal = $('#showModal')
        console.log("data" + JSON.stringify(data));

        $('#sRoom').text(data.resourceId);
        $('#dept').text(data.dept);
        $('#empName').text(data.bookerNm);
        $('#sRunningDay').text(dateFormat(data.start.toISOString()));
        $('#sStartT').text(timeFormat(data.start.toISOString()));
        $('#sEndT').text(timeFormat(data.end.toISOString()));
        $('#sConfName').text(data.title);
        $('#sMemo').text(data.memo);

        modal.css('display', 'block');
    }


    // 예약 생성
    $('#bookingForm').submit(function(e) {
        e.preventDefault();

        //폼데이터 가져오기
        let jsonData = {};
        jsonData.room = ($('#room').val());
        jsonData.confName = ($('#confName').val());
        jsonData.runningDay = ($('#runningDay').val());
        jsonData.startTime = ($('#startTime').val());
        jsonData.endTime = ($('#endTime').val());
        jsonData.text = ($('#text').val());
        jsonData.color = ($('#color').val());

        console.log(jsonData)

        // Ajax 요청
        $.ajax({
            url: '/mypage/conf/add',
            type: 'POST',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function(rep) {
                console.log('성공')

                Swal.fire({
                    icon: "success",
                    title: "Your work has been saved",
                    showConfirmButton: false,
                    timer: 1500
                })
                    .then(() => {
                        location.reload();
                    })

            },
            error: function (err) {
                console.log( err.message)
            }

        })
    })



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