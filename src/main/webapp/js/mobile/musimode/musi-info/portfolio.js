var source = []

$('.loader-box').show()

Handlebars.registerHelper('isIndex', function(key, options) {
  if (key == "Y") {
    return options.fn(this);
  } else {
    return options.inverse(this);
  }
});

$("#portfolio-add-btn").on('click', function() {
  location.href = 'career.html?no=0'
})

displayMusiInfoPortfolio()

function displayMusiInfoPortfolio() {
  $.getJSON('/portfolio/myPortfolio.json', function(result) {
    console.log(result.data)
    if(result.data.getPortfolio.length == 0) {
      var templateFn = Handlebars.compile($('#musician-info0-portfolio-template').text())
      var generatedHTML = templateFn(result.data)
      var container = $('.portfolio-container')
      var html = container.html()
      container.html(html + generatedHTML)
      $("#musician-portfolio-edit-btn").css("display", "none")
      $('.loader-box').hide()
      return
    }

    var templateFn = Handlebars.compile($('#musician-info-portfolio-template').text())
    var generatedHTML = templateFn(result.data)
    var container = $('.portfolio-container')
    var html = container.html()
    $('.loader-box').hide()
    container.html(html + generatedHTML).hide().fadeIn(700)
    
            $("#spec-backscreen").css("background", "url('"+ result.data.getPortfolio[0].photo +"')")
        $("#spec-backscreen").css("background-size", "cover")
        $("#spec-backscreen").css("background-position", "center")

    for(var j = 0; j < result.data.getPortfolio.length; j++){
      var spno = result.data.getPortfolio[j].spno
      for(var i = 0; i < result.data.getPortfolio[j].list.length; i++) {
        if(result.data.getPortfolio[j].list[i].fileMap.key == "N") {
          $(".timeline-movie[data-spno=" + spno + "]").append("<img id='timeline-picture' src='https://img.youtube.com/vi/" + result.data.getPortfolio[j].list[i].fileMap.value.substring(17,28) + "/0.jpg'>")
          $(".spec-movie[data-spno=" + spno + "]").append("<iframe width='791' height='876' src='https://www.youtube.com/embed/" + result.data.getPortfolio[j].list[i].fileMap.value.substring(17,28) + "?rel=0&amp;showinfo=0' frameborder='0' allowfullscreen></iframe>")
        }
      }
    }

    var timeLine = $('.timeline-line')

    $(".musician-portfolio-edit-btn").on('click', function(e) {
      location.href = 'career.html?no=' + $(this).attr('data-spno')
    })

    if($("div[id=timeline-container]").length <= 1) {
      timeLine.css("background", "radial-gradient(circle, black, black, white)")
    } else if ($("div[id=timeline-container]").length >= 2) {
      var length = $("div[id=timeline-container]").length
      timeLine[0].style.background = "linear-gradient(to top, black 60%, white)"
        timeLine[length - 1].style.background = "linear-gradient(to bottom, black 60%, white)"
    }

    $(".timeline-content").on('click', function(){
      var no = $(this).attr("data-no")
      $(".spec-detail[data-no=" + no + "]").toggle('slide', {direction:'down'}, 400)
      $(".spec-detail[data-no=" + no + "]").scrollTop(0)
      $("#spec-backscreen").toggle('slide', {direction:'down'}, 400)
      $("#spec-deepscreen").toggle('slide', {direction:'down'}, 400)
      $("#container").css('position', 'fixed')
    })

    $(".spec-close").on('click', function() {
      var no = $(this).attr("data-no")
      $(".spec-detail[data-no=" + no + "]").toggle('slide', {direction:'down'}, 400)
      $("#spec-backscreen").toggle('slide', {direction:'down'}, 400)
      $("#spec-deepscreen").toggle('slide', {direction:'down'}, 400)
      $("#container").css('position', 'relative')
    })




  })
}

