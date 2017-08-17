"use strict"

$(document).ready(function() {
  $(".animsition").animsition({
    inClass: 'fade-in-up',
    outClass: 'fade-out-down',
    inDuration: 500,
    outDuration: 700,
    linkElement: '.animsition-link',
    // e.g. linkElement: 'a:not([target="_blank"]):not([href^="#"])'
    loading: false,
    loadingParentElement: 'body', //animsition wrapper element
    loadingClass: 'animsition-loading',
    loadingInner: '', // e.g '<img src="loading.svg" />'
    timeout: false,
    timeoutCountdown: 5000,
    onLoadEvent: true,
    browser: [ 'animation-duration', '-webkit-animation-duration'],
    // "browser" option allows you to disable the "animsition" in case the css property in the array is not supported by your browser.
    // The default setting is to disable the "animsition" in a browser that does not support "animation-duration".
    overlay : false,
    overlayClass : 'animsition-overlay-slide',
    overlayParentElement : 'body',
    transition: function(url){ window.location.href = url; }
  });

});

var isRecommandEvent = true;

var noEventView = $('.recruiting-no-event'),
yesEventView = $('.recruiting-yes-event');

if(isRecommandEvent) {
	noEventView.css('display', 'none')
	yesEventView.css('display', '')


	displayRecruitingEventList()

	
	
} else {
	yesEventView.css('display', 'none')
	noEventView.css('display', '')
}

function displayRecruitingEventList() {
  $.getJSON('/event/listRecruiting.json', function(result) {
    if(result.status != 'success') {
      console.error("getJSON() 실패: ", result.status)
      return;
    }
    
    console.log(result.data)
    $('.loader-box').hide();
    var templateFn = Handlebars.compile($('#recruiting-event-template').text())
    var generatedHTML = templateFn(result.data)
    var container = $('#recruiting-event-container')
    var html = container.html()
    container.html(html + generatedHTML).hide().fadeIn(700);
    
    readyBtns()
  }, function(err) {
    console.log(err)
  })
}


function readyBtns() {
	var applicantBtn = $('.recruiting-applicant-open-btn'),
		calleeBtn = $('.recruiting-callee-open-btn'),
		eventBox = $('.event-box');
	
applicantBtn.on('click', function(e) {
	postAppy($(this))
})

calleeBtn.on('click', function(e) {
  postPr($(this))
})

eventBox.on('click', function(e) {
	location.href = 'detail.html'
})


function postAppy(pressedBtn) {
  if(pressedBtn.attr('data-fill') == "false") {
    pressedBtn.attr('data-fill', true)
   
    $.getJSON('/musician/listAppy.json',
      {'eventNo': pressedBtn.parent('.recruiting-musibox').attr('data-eventno')},
    function(result) {
      if(result.status != 'success') {
        console.error("getJSON() 실패: ", result.status)
        return;
      }
      
      $.each(result.data.listAppy, function(i, item) {
        heartAdd(item)
      });
      
      var templateFn = Handlebars.compile($('#recruiting-appy-open-template').text())
      var generatedHTML = templateFn(result.data)
      var container = pressedBtn.next().children('.recruiting-appy-open-container')
      var html = container.html()
      container.html(html + generatedHTML)
      
      setFoldBtns(pressedBtn)
    }, function(err) {
      console.log(err)
    })//getJson()
  } else {//if()
    setFoldBtns(pressedBtn)
  }//else()
}//postAppy()


function postPr(pressedBtn) {
  if(pressedBtn.attr('data-fill') == "false") {
    pressedBtn.attr('data-fill', true)
   
    $.getJSON('/musician/listPr.json',
      {'eventNo': pressedBtn.parent('.recruiting-musibox').attr('data-eventno')},
    function(result) {
      if(result.status != 'success') {
        console.error("getJSON() 실패: ", result.status)
        return;
      }
      
      $.each(result.data.listPr, function(i, item) {
        heartAdd(item)
      });
      
      var templateFn = Handlebars.compile($('#recruiting-pr-open-template').text())
      var generatedHTML = templateFn(result.data)
      var container = pressedBtn.next().children('.recruiting-pr-open-container')
      var html = container.html()
      container.html(html + generatedHTML)
      
      setFoldBtns(pressedBtn)
    }, function(err) {
      console.log(err)
    })//getJson()
  } else {//if()
    setFoldBtns(pressedBtn)
  }//else()
}//postPr()



function setFoldBtns(pressedBtn) {
  if(pressedBtn.attr('data-open') == "true") {
    pressedBtn.html('펼치기 <i class="fa fa-angle-down" aria-hidden="true"></i>')
        .attr('data-open', false)
  } else {
    pressedBtn.html('접기 <i class="fa fa-angle-up" aria-hidden="true"></i>')
          .attr('data-open', true)
  }
  pressedBtn.siblings('.recruiting-musibox-open-container').toggle( "fold", 500 );
}


function heartAdd(item) {
  if (item.isFavorite == 1) {
    item.isFavorite = '<i class="fa fa-heart" aria-hidden="true"></i>'
  } else {
    item.isFavorite = '<i class="fa fa-heart-o" aria-hidden="true"></i>'
  }
}


}