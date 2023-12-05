var productIndex = 0;

/* 상품 추가 버튼 클릭 시 동적으로 상품 입력 필드 추가 */
function addProductField() {
    var productFields = document.getElementById('productFields');

    var productField = document.createElement('div');
    productField.appendChild(document.createElement('label'));
    productField.lastChild.htmlFor = 'product';
    productField.lastChild.textContent = '입고상품 : ';

    var selectElement = document.createElement('select');
    selectElement.id = 'product';
    selectElement.name = 'productCodes[' + productIndex + ']';

    var defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = '상품 선택하세요';
    selectElement.appendChild(defaultOption);

    /* JavaScript로 서버에서 전달한 데이터 사용 */
    /* products는 서버에서 전달한 상품 목록 데이터로 가정 */
    var products = JSON.parse(document.getElementById('productFields').getAttribute('data-products'));

    for (var i = 0; i < products.length; i++) {
        var optionElement = document.createElement('option');
        optionElement.value = products[i].productCode;
        optionElement.text = products[i].productName;
        selectElement.appendChild(optionElement);
    }

    productField.appendChild(selectElement);

    productField.appendChild(document.createElement('label'));
    productField.lastChild.htmlFor = 'quantity';
    productField.lastChild.textContent = ' 입고수량';

    var inputElement = document.createElement('input');
    inputElement.type = 'number';
    inputElement.id = 'quantity';
    inputElement.name = 'inboundQuantities[' + productIndex + ']';
    inputElement.required = true;

    productField.appendChild(inputElement);

    productField.appendChild(document.createElement('br'));

    productFields.appendChild(productField);
    productIndex++;
}
