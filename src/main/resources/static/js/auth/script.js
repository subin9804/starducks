// 이메일 인풋은 valid가 되려면 @가 있는 경우메만 그렇다보니
// 다른 인풋들과 동일하게 아무 값이나 입력됐어도 플로팅 라벨이 유지되려면 이렇게 설정해줘야 한다
document.addEventListener("DOMContentLoaded", function() {
    var inputs = document.querySelectorAll(".form-floating input");

    inputs.forEach(function(input) {
        input.addEventListener("blur", function() {
            if (this.value !== "") {
                this.classList.add("has-value");
            } else {
                this.classList.remove("has-value");
            }
        });
    });
});