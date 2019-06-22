var number1 = 0;
var number2 = 0;
$(document).on('click', '.player_list button', function(event){
  if (number1 == Number($(this).parents('li').find('.no').text()) || number2 == Number($(this).parents('li').find('.no').text())) {
    return;
  }
  if (number1 > 0 && number2 > 0) {
    number1 = number2;
    number2 = Number($(this).parents('li').find('.no').text());
    $('[name="select1"]').val(number1);
    $('[name="select2"]').val(number2);
  } else if (number1 > 0) {
    number2 = Number($(this).parents('li').find('.no').text());
    $('[name="select1"]').val(number1);
    $('[name="select2"]').val(number2);
  } else {
    number1 = Number($(this).parents('li').find('.no').text());
    $('[name="select1"]').val(number1);
  }
});