$(function() {
    "use strict";
    $.validator.setDefaults({
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorPlacement: function(error, element) {}
    });
    $("#phpcontactform").submit(function(e) {
        e.preventDefault();
    }).validate({
        rules: {
            name: "required",
            password: "required",
            new_password: "required",
            confirm_password: "required",
            role: "required",
            phone_number: "required",
            l_name: "required",
            email: {
                required: true,
                email: true
            },
            subject: "required",
            phone: "required",
            message: "required",
        },
        messages: {
            first_name: "Your first name please",
            last_name: "Your last name please",
            email: "We need your email address",
            subject: "We need your subject",
            phone: "Please enter your phone number",
            message: "Please enter your message",
        },
        submitHandler: function(form) {
            $("#js-contact-btn").attr("disabled", true);
            var redirect = $('#phpcontactform').data('redirect');
            var noredirect = false;
            if (redirect == 'none' || redirect == "" || redirect == null) {
                noredirect = true;
            }
            $("#js-contact-btn").text('Please wait');
            var success_msg = $('#js-contact-result').data('success-msg');
            var error_msg = $('#js-contact-result').data('error-msg');
            var dataString = $(form).serialize();
            $.ajax({
                type: "POST",
                data: dataString,
                url: "php/contact.php",
                cache: false,
                success: function(d) {
                    $(".form-group").removeClass("has-success");
                    if (d == 'success') {
                        if (noredirect) {
                            $("#js-contact-btn").text(success_msg);
                            $('#phpcontactform')[0].reset();
                        } else {
                            window.location.href = redirect;
                        }
                    } else {
                        $("#js-contact-btn").text(error_msg);
                        setTimeout(function() {
                            $("#js-contact-btn").text('Send Message');
                        }, 2000);
                    }
                    $("#js-contact-btn").attr("disabled", false);
                },
                error: function(d) {
                    $("#js-contact-btn").text('Cannot access Server');
                    $("#js-contact-btn").attr("disabled", false);
                    setTimeout(function() {
                        $("#js-contact-btn").text('Send Message');
                    }, 2000);
                }
            });
            return false;
        }
    });
}) 