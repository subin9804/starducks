document.addEventListener('DOMContentLoaded', function() {
    const csrfToken = document.querySelector("meta[name='_csrf']").content;
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;

    let schedules;
    fetchSchedule().then (() => {

        // console.log(schedules)
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

            date.text(editDate);
            title.text(schedule.scheTitle);

            date.css("width", "80px")

            tr.append(date);
            tr.append(title);

            $('#tschedule').append(tr)
        }
        // console.log(events)
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            height: '410',  // 높이를 조절
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
            }
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
                success: function (data) {
                    schedules = data;
                    // console.log(data)
                    resolve(data)
                },
                error: function (err) {
                    reject(err);
                }
            })
        })
    }



    fetchMail().then((data) => {

        for(let mail of data.content) {

            // html에 리스트 삽입
            let tr = $('<tr>');
            let date = $('<td>');
            let title = $('<td>');

            let editDate = (mail.sentDate).substring(0, 10)

            date.text(editDate);
            title.text(mail.subject);

            date.css("width", "80px")
            tr.append(date);
            tr.append(title);

            $('#tmail').append(tr)
        }
    })

    function fetchMail() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: '/mypage/api/mail',
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    // console.log(data)
                    resolve(data);
                },
                error: function () {
                    reject(err)
                }
            })
        })
    }


    // FullCalendar의 헤더에 있는 글씨 크기 조절
    $('.fc-toolbar-chunk').css({
        'font-size': '10px',
        'display': 'flex'
    });


    // 날씨 위젯
    // navigator.geolocation.getCurrentPosition(success, errorCallback);
    let position = {
        latitude: 37.3388756,
        longitude: 127.1093775
    }


    function success(position) {

        // console.log(position)
        // const latitude = position.coords.latitude;
        // const longitude = position.coords.longitude;

        const latitude = position.latitude;
        const longitude = position.longitude;

        getWeather(latitude, longitude);
    }

    function errorCallback(error) {
        alert(`ERROR(${error.code}): ${error.message}`);
    }

    const getWeather = async(lat, lng) => {

        try {
            // API_KEY는 index.html에서 th:inline="javascript"로 받아옴!
            $.ajax({
                url: `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=${API_KEY}&units=metric`,
                success: async function (res) {
                    // id 찾아서 매칭 후 description 한글 번역된 거 가져오려고 했는데 도시이름 한글로 번역하기 귀찮아서 그냥 둠
                    // const weatherId = res.weather[0].id;
                    const description = res.weather[0].main;

                    // const weatherKo = weatherDescKo[weatherId];
                    // console.log(res)

                    // 날씨 아이콘 가져오기
                    const weatherIcon = res.weather[0].icon;
                    const weatherIconAdrs = `http://openweathermap.org/img/wn/10d@2x.png`;

                    // 소수점 버리기
                    const temp = Math.round(res.main.temp);

                    const cityName = await getCity(lat, lng);

                    let setWeather = {
                        description: description,
                        name: cityName.locality,
                        temp: temp,
                        icon: weatherIconAdrs,
                    };

                    printHtml(setWeather);

                },
                error: function(error) {
                    console.log(error);
                }
            })

        } catch (err){
            console.error(err);
        }
    };

    // 위치기반 도시 이름 가져오기
    const getCity = async(lat, lng) => {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${lat}&longitude=${lng}&localityLanguage=en`,
                method: 'GET',
                success: function (res) {
                    resolve(res)
                },
                error: function (error) {
                    reject(error);
                }

            })
        });
    }

    function printHtml(wether) {
        $("#description").text(wether.description)
        $("#city").text(wether.name);
        $("#weatherImg").attr("src" , wether.icon);
        $("#temp").text(wether.temp + " ºC");

    }

    success(position)


});

function redirect(url, id) {
    return 'location.href=\'' + url + id + '\'';
}



