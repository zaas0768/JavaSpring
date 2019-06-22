$(document).on('click', '.turn', function(event){
  $('.player1').animate({'top': 80, 'left': 180}, 300, function(){
    $('.player1').animate({'top': 100, 'left': 50}, 100);
  });
  $('.player2').animate({'top': 80, 'left': 270}, 300, function(){
    $('.player2').animate({'top': 100, 'left': 450}, 100);
  });
});