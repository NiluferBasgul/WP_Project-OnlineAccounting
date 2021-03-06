/**
 * Created by z00382545 on 10/20/16.
 */

(function ($) {
    $.toggleShowPassword = function (options) {
        var settings = $.extend({
            field: "#password",
            control: "#toggle_show_password",
        }, options);

        var control = $(settings.control);
        var field = $(settings.field)

        control.bind('click', function () {
            if (control.is(':checked')) {
                field.attr('type', 'text');
            } else {
                field.attr('type', 'password');
            }
        })
    };

    $.transferDisplay = function () {
        $("#transferFrom").change(function() {
            if ($("#transferFrom").val() === 'Invoice') {
                $('#transferTo').val('Costs');
            } else if ($("#transferFrom").val() === 'Costs') {
                $('#transferTo').val('Invoice');
            }
        });

        $("#transferTo").change(function() {
            if ($("#transferTo").val() === 'Invoice') {
                $('#transferFrom').val('Costs');
            } else if ($("#transferTo").val() === 'Costs') {
                $('#transferFrom').val('Invoice');
            }
        });
    };



}(jQuery));

$(document).ready(function() {
    var confirm = function() {
        bootbox.confirm({
            title: "Procurement Confirmation",
            message: "Do you really want to schedule this procurement?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (result) {
                if (result == true) {
                    $('#procurementForm').submit();
                } else {
                    console.log("Scheduling cancelled.");
                }
            }
        });
    };

    $.toggleShowPassword({
        field: '#password',
        control: "#showPassword"
    });

    $.transferDisplay();

    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:mm",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00",
        minuteStep: 10
    });

    $('#submitProcurement').click(function () {
        confirm();
    });

});




