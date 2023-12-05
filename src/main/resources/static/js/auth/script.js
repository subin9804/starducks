// document.addEventListener('DOMContentLoaded', function() {
//     // 모든 플로팅 라벨 필드를 선택
//     var floatingLabels = document.querySelectorAll('.form-floating input');
//
//     // 각 필드에 이벤트 리스너 추가
//     floatingLabels.forEach(function(input) {
//         input.addEventListener('focus', floatLabel);
//         input.addEventListener('blur', floatLabel);
//         input.addEventListener('input', floatLabel);
//     });
//
//     function floatLabel(e) {
//         var input = e.target;
//         var label = input.nextElementSibling;
//         // 인풋에 값이 있거나 포커스되었을 때
//         if(input.value || input === document.activeElement) {
//             label.classList.add('active');
//         } else {
//             label.classList.remove('active');
//         }
//     }
// });
