$(function() {
    "use strict";

    // discount create and edit part
    $('.submit_discount_h').click(function() {
        var title1 = $('.title').val();
        var description1 = $('.description').val();
        var discount_photo = $('.discount_photo').val();
        var image = $('#file');
        if (image[0].files.length > 0) {
            // var file_size = image[0].files[0].size;
            // if (file_size > 400000) {
            //     Notificationsystem(2);
            //     return;
            // }
        }

        if (!title1 || !description1 || !discount_photo)
        {
            Notificationsystem(1);
            return;
        }

        $('.submit_discount').click();
    });
    $('.submit_discount_edit').click(function() {
        var title1 = $('.title').val();
        var description1 = $('.description').val();
        var image = $('#file');
        // if (image[0].files.length > 0) {
        //     var file_size = image[0].files[0].size;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }else{
        //     var file_size = $('#hidden_img').val(); //or  document.images[i].src;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }

        if (!title1 || !description1)
        {
            Notificationsystem(1);
            return;
        }

        $('.submit_discount').click();
    });

    //vendor create and edit part
    $('.submit_vendor_h').click(function() {
        var vendorname = $('.vendorname').val();
        var email = $('.email').val();
        var phone = $('.phone').val();
        var location = $('.location').val();
        var photo = $('.photo').val();

        var image = $('#file');
        // if (image[0].files.length > 0) {
        //     var file_size = image[0].files[0].size;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }

        if (!vendorname || !email || !phone || !location || !photo)
        {
            Notificationsystem(3);
            return;
        }

        $('.submit_vendor').click();
    });
    $('.submit_vendor_edit').click(function() {
        var vendorname = $('.vendorname').val();
        var email = $('.email').val();
        var phone = $('.phone').val();
        var location = $('.location').val();

        var image = $('#file');
        // if (image[0].files.length > 0) {
        //     var file_size = image[0].files[0].size;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }else{
        //     var file_size = $('#hidden_img').val(); //or  document.images[i].src;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }

        if (!vendorname || !email || !phone || !location)
        {
            Notificationsystem(3);
            return;
        }

        $('.submit_vendor').click();
    });    

    //category create and edit part
    $('.submit_category_h').click(function() {
        var category_name = $('.category_name').val();
        var category_photo = $('.category_photo').val();

        var image = $('#file');
        // if (image[0].files.length > 0) {
        //     var file_size = image[0].files[0].size;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }

        if (!category_name || !category_photo)
        {
            Notificationsystem(4);
            return;
        }

        $('.submit_category').click();
    });
    $('.submit_category_edit').click(function() {
        var category_name = $('.category_name').val();

        var image = $('#file');
        // if (image[0].files.length > 0) {
        //     var file_size = image[0].files[0].size;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }else{
        //     var file_size = $('#hidden_img').val(); //or  document.images[i].src;
        //     if (file_size > 400000) {
        //         Notificationsystem(2);
        //         return;
        //     }
        // }

        if (!category_name)
        {
            Notificationsystem(4);
            return;
        }

        $('.submit_category').click();
    });   
});

function Notificationsystem(flag) {
    if (flag == 1) {
        $.toast({
            heading: 'Error',
            text: "Please confirm a title and description and Photo. It's required fields.",
            position: String("top-right"),
            icon: 'error',
            stack: false,
            loaderBg: '#f96868'
        });
    }if(flag == 2) {
        $.toast({
            heading: 'Error',
            text: "Please choose the photo size as than 400 KB smaller.",
            position: String("top-right"),
            icon: 'error',
            stack: false,
            loaderBg: '#f96868'
        });
    } if (flag == 3) {
        $.toast({
            heading: 'Error',
            text: "Please confirm the Name and Profile Photo and Email, Phone and Location well. It's required fields.",
            position: String("top-right"),
            icon: 'error',
            stack: false,
            loaderBg: '#f96868'
        });
    } if (flag == 4) {
        $.toast({
            heading: 'Error',
            text: "Please confirm the Name and Photo. It's required fields.",
            position: String("top-right"),
            icon: 'error',
            stack: false,
            loaderBg: '#f96868'
        });
    }       
}
