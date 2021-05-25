;(function($) {
    "use strict"; 
    
    //* Navbar Fixed  
    function navbarFixed(){
        if ( $('.main_header_area').length ){ 
            $(window).on('scroll', function() {
                var scroll = $(window).scrollTop();   
                if (scroll >= 295) {
                    $(".main_header_area").addClass("navbar_fixed");
                } else {
                    $(".main_header_area").removeClass("navbar_fixed");
                }
            });
        };
    };  
    
    /* Main Slider js */
    function main_slider(){
        if ( $('.main_slider_area').length ){
            $("#main_slider").revolution({
                sliderType:"standard",
                sliderLayout:"auto",
                delay:9000,
                disableProgressBar:"off",   
                navigation: {
                    onHoverStop: 'off',
                    touch:{
                        touchenabled:"on"
                    },
                    arrows: {
                        style:"zeus",
                        enable:true,
                        hide_onmobile:true,
                        hide_under:767,
                        hide_onleave:true,
                        hide_delay:200,
                        hide_delay_mobile:1200,
                        tmp:'<div class="tp-title-wrap"><div class="tp-arr-imgholder"></div></div>',
                        left: {
                            h_align: "left",
                            v_align: "center",
                            h_offset: 30,
                            v_offset: 0
                        },
                        right: {
                            h_align: "right",
                            v_align: "center",
                            h_offset: 30,
                            v_offset: 0
                        }
                    },
                },
                responsiveLevels:[4096,1199,992,767,640],
                gridwidth:[1170,930,690,690,500],
                gridheight:[930,930,750,500,500],
                lazyType:"smart", 
                fallbacks: {
                    simplifyAll:"off",
                    nextSlideOnWindowFocus:"off",
                    disableFocusListener:false,
                }
            }); 
        }
    };
    
    
    //* Counter Js 
    function counterUp(){
        if ( $('.previous_right').length ){ 
            $('.counter').counterUp({
                delay: 10,
                time: 400
            });
        };
    }; 
    
    //* TestimonialCarousel Js 
    function testimonialCarousel(){
        if ( $('.testimonial_area, .join_conversation').length ){ 
            $('.testimonial').owlCarousel({
            	loop: true,
            	margin: 30,
            	nav: false,
				items: 2,
				autoplay: true,
            	responsive: {
            		0: {
            			items: 1
            		},
            		767: {
            			items: 2
            		}, 
            	}
            });
			
			$('.conversation').owlCarousel({ 
                margin: 30, 
                autoplay: false,
                dots: true,
                items: 3,
                responsiveClass: true,
                responsive: {
                    0: {
                        items: 1, 
                    }, 
                    768: {
                        items: 2, 
                    },
                    1199: {
                        items: 3, 
                    }
                }
            });  
        };
    }; 
	
    //* Select js
    function selectmenu(){
        if ( $('.post_select').length ){ 
            $('select').niceSelect();
        };
    }; 
    
    //* Select2 js
    function select2 (){
        if ( $('.select2').length ){ 
            $('select').select2();
        };
    }; 
	
	
    //* Magnificpopup js
    function magnificPopup() {
        if ($('.business_video').length) { 
            //Video Popup
            $('.popup-youtube').magnificPopup({
                disableOn: 700,
                type: 'iframe',
                mainClass: 'mfp-fade',
                removalDelay: 160,
                preloader: false, 
                fixedContentPos: false,
            });   
        };
    }; 
    
    //* Isotope js
    function portfolioIsotope(){
        if ( $('.portfolio_area').length ){ 
            // Activate isotope in container
            $(".portfoli_inner").imagesLoaded( function() {
                $(".portfoli_inner").isotope({
                    layoutMode: 'fitRows',  
                }); 
            }); 
            
            // Add isotope click function 
            $(".protfoli_filter li").on('click',function(){
                $(".protfoli_filter li").removeClass("active");
                $(this).addClass("active"); 
                var selector = $(this).attr("data-filter");
                $(".portfoli_inner").isotope({
                    filter: selector,
                    animationOptions: {
                        duration: 450,
                        easing: "linear",
                        queue: false,
                    }
                });
                return false;
            });  
        };
    }; 
    
    //* Select js
    function selectmenu(){
        if ( $('.post_select').length ){ 
            $('select').niceSelect();
        };
    }; 
     
    // Scroll to top
    function scrollToTop() {
        if ($('.scroll-top').length) {  
            $(window).on('scroll', function () {
                if ($(this).scrollTop() > 200) {
                    $('.scroll-top').fadeIn();
                } else {
                    $('.scroll-top').fadeOut();
                }
            }); 
            //Click event to scroll to top
            $('.scroll-top').on('click', function () {
                $('html, body').animate({
                    scrollTop: 0
                }, 1000);
                return false;
            });
        }
    }
    
    // Preloader JS
    function preloader(){
        if( $('.preloader').length ){
            $(window).on('load', function() {
                $('.preloader').fadeOut();
                $('.preloader').delay(50).fadeOut('slow');  
            })   
        }
    }
    
    /*Function Calls*/ 
    new WOW().init();
    navbarFixed (); 
    main_slider ();  
	counterUp ();
	testimonialCarousel ();
	selectmenu ();
	select2 ();
	scrollToTop ();
	portfolioIsotope ();
	preloader ();
    
})(jQuery);