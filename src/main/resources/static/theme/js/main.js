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
        paginationChange();
    });

    paginationChange();

    function paginationChange() {

        $(".page-prev-btn").text("Poprzednia");
        $(".page-next-btn").text("Następna");

        $(".page-nav .page-separator").each(function(){
            $(this).text(". .");
        });

        $(".page-nav .page-mobile-hidden").each(function(){
            $(this).css("display", "block");
        });


        if ($(window).width() < 1200) {
            $(".page-prev-btn").text("<");
            $(".page-next-btn").text(">");
        }

        if ($(window).width() < 992) {

            $(".page-nav .page-separator").each(function(){
                $(this).text(".");
            });

            if($('.page-mobile-hidden').length == 6) {
                $(".page-nav .page-mobile-hidden").first().css("display", "none");
                $(".page-nav .page-mobile-hidden").last().css("display", "none");
            }
        }

        if ($(window).width() < 500) {
            $(".page-nav .page-mobile-hidden").each(function(){
                $(this).css("display", "none");
            });
        }




    }


});