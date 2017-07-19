"use strict"


var myAlias = true,
  value = "abc",
  messageBox = $('#messageBox'),
  sendBtn = $('#send-btn')

sendBtn.on('click', function() {
  $('<li>').addClass(myAlias ? "me" : "him")
    .html(value)
    .appendTo(messageBox)
  messageBox.scrollTop(messageBox.prop('scrollHeight'));

  if (myAlias) {
    myAlias = false
  } else {
    myAlias = true
  }
})


$(document).ready(function() {
      $(".animsition").animsition({
        inClass: 'fade-in-up',
        outClass: 'fade-out-down',
        inDuration: 500,
        outDuration: 700,
        linkElement: '.animsition-link',
        // e.g. linkElement: 'a:not([target="_blank"]):not([href^="#"])'
        loading: true,
        loadingParentElement: 'body', //animsition wrapper element
        loadingClass: 'animsition-loading',
        loadingInner: '', // e.g '<img src="loading.svg" />'
        timeout: false,
        timeoutCountdown: 5000,
        onLoadEvent: true,
        browser: ['animation-duration', '-webkit-animation-duration'],
        // "browser" option allows you to disable the "animsition" in case the css property in the array is not supported by your browser.
        // The default setting is to disable the "animsition" in a browser that does not support "animation-duration".
        overlay: false,
        overlayClass: 'animsition-overlay-slide',
        overlayParentElement: 'body',
        transition: function(url) {
          window.location.href = url;
        }
      });
