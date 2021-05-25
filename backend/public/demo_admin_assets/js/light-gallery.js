(function($) {
  'use strict';
  if ($("#lightgallery").length) {
    
    // $('.lightGallery').click(function() {
    //   var tag = $(this).prop("tagName");
    //   if (tag == "tbody") {

    //   }else if (tag == "img") {
    //     $("#lightgallery").lightGallery();  
    //   }
    // });

    $("#lightgallery").lightGallery();
  }



  if ($("#lightgallery-without-thumb").length) {
    $("#lightgallery-without-thumb").lightGallery({
      thumbnail: true,
      animateThumb: false,
      showThumbByDefault: false
    });
  }

  if ($("#video-gallery").length) {
    $("#video-gallery").lightGallery();
  }
})(jQuery);