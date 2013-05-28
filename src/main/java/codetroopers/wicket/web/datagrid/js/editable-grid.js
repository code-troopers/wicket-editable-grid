!function ($) {

    "use strict"; // jshint ;_;

    function _setFocusOnNextElement() {
        $(".datagrid tfoot tr input:first").focus()
    }

    /**
     * method to submit datagrid lines with enter key press
     */
    $(document).keypress(function(e) {
        if (e.which == 13) {
            var $input = $(e.target);
            if ($input.parents('.datagrid').length > 0) {
                $("a", $input.parents("tr")).click();
                setTimeout(_setFocusOnNextElement,500);
                e.preventDefault();
            }
        }
    });
}(window.jQuery);
