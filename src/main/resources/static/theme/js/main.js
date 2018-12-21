$(function() {

    changeSidebar();

    $('#toggleSidebarBtn').click(function() {

        $sidebar = $( "#sidebar" );

        if ( $sidebar.hasClass("mobile-sidebar")) {
            if ( $sidebar.hasClass( "active" )) {
                hideSidebar($sidebar);
            }
            else {
                showSidebar($sidebar);
            }
        }

    });

    function showSidebar($sidebar) {
        $sidebar.addClass("active");
        $( "body" ).css("overflow-y", "hidden");
    }

    function hideSidebar($sidebar) {
        $sidebar.removeClass("active");
        $( "body" ).css("overflow-y", "scroll");
    }

    function changeSidebar() {
        if ($(window).width() < 768) {
            $("#sidebar").addClass("mobile-sidebar");
            $("#sidebar").css("display", "block");
            $("#toggleSidebarBtn").css("display", "block");
        } else {
            $("#sidebar").removeClass("mobile-sidebar");
            $("#toggleSidebarBtn").css("display", "none");
        }
    }

    $(window).on('resize', function() {
        changeSidebar();
    });


});