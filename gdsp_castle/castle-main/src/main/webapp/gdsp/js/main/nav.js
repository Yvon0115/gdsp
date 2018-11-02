define(["cas/core","cas/options","plugins/fastclick/fastclick","plugins/jquery/jquery.slimscroll"],function ($F,$options) {

    var that = $.MainNavigator= {

        /* Layout
         * ======
         * Fixes the layout height in case min-height fails.
         *
         * @type Object
         * @usage $.MainNavigator.layout.activate()
         *        $.MainNavigator.layout.fix()
         *        $.MainNavigator.layout.fixSidebar()
         */
        layout: {
            activate: function () {
                var _this = this;
                _this.fix();
                _this.fixSidebar();
                $(window, ".wrapper").resize(function () {
                    _this.fix();
                    _this.fixSidebar();
                });
            },
            fix: function () {
                //Get window height and the wrapper height
                var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
                var window_height = $(window).height();
                var sidebar_height = $(".sidebar").height();
                //Set the min-height of the content and sidebar based on the
                //the height of the document.
                if ($("body").hasClass("fixed")) {

                } else {
                    var postSetWidth;
                    if (window_height >= sidebar_height) {
                        $(".content-wrapper").css('min-height', window_height - neg);
                        postSetWidth = window_height - neg;
                    } else {
                        $(".content-wrapper").css('min-height', sidebar_height);
                        postSetWidth = sidebar_height;
                    }

                    //Fix for the control sidebar height
                    var controlSidebar = $($options.app.controlSidebarOptions.selector);
                    if (typeof controlSidebar !== "undefined") {
                        if (controlSidebar.height() > postSetWidth)
                            $(".content-wrapper, .right-side").css('min-height', controlSidebar.height());
                    }

                }
            },
            fixSidebar: function () {
                //Make sure the body tag has the .fixed class
                if (!$("body").hasClass("fixed")) {
                    if (typeof $.fn.slimScroll != 'undefined') {
                        $(".sidebar").slimScroll({destroy: true}).height("auto");
                    }
                    return;
                } else if (typeof $.fn.slimScroll == 'undefined' && console) {
                    console.error("Error: the fixed layout requires the slimscroll plugin!");
                }
                //Enable slimscroll for fixed layout
                if ($options.app.sidebarSlimScroll) {
                    if (typeof $.fn.slimScroll != 'undefined') {
                        //Destroy if it exists
                        $(".sidebar").slimScroll({destroy: true}).height("auto");
                        //Add slimscroll
                        $(".sidebar").slimscroll({
                            height: ($(window).height() - $(".main-header").height()-$(".main-sidebar div.header").outerHeight()-47) + "px",
                            color: "rgba(0,0,0,0.2)",
                            size: "3px"
                        });
                    }
                }
            }
        },

        /* PushMenu()
         * ==========
         * Adds the push menu functionality to the sidebar.
         *
         * @type Function
         * @usage: $.MainNavigator.pushMenu("[data-toggle='offcanvas']")
         */
        pushMenu: {
            activate: function (toggleBtn) {
                //Get the screen sizes
                var screenSizes = $options.app.screenSizes;

                //Enable sidebar toggle
                $(toggleBtn).on('click', function (e) {
                    e.preventDefault();

                    //Enable sidebar push menu
                    if ($(window).width() > (screenSizes.sm - 1)) {
                        $("body").toggleClass('sidebar-collapse');
                    }
                    //Handle sidebar push menu for small screens
                    else {
                        if ($("body").hasClass('sidebar-open')) {
                            $("body").removeClass('sidebar-open');
                            $("body").removeClass('sidebar-collapse')
                        } else {
                            $("body").addClass('sidebar-open');
                        }
                    }
                });

                $(".content-wrapper").click(function () {
                    //Enable hide menu when clicking on the content-wrapper on small screens
                    if ($(window).width() <= (screenSizes.sm - 1) && $("body").hasClass("sidebar-open")) {
                        $("body").removeClass('sidebar-open');
                    }
                });

                //Enable expand on hover for sidebar mini
                if ($options.app.sidebarExpandOnHover
                    || ($('body').hasClass('fixed')
                    && $('body').hasClass('sidebar-mini'))) {
                    this.expandOnHover();
                }

            },
            expandOnHover: function () {
                var _this = this;
                var screenWidth = $options.app.screenSizes.sm - 1;
                //Expand sidebar on hover
                $('.main-sidebar').hover(function () {
                    if ($('body').hasClass('sidebar-mini')
                        && $("body").hasClass('sidebar-collapse')
                        && $(window).width() > screenWidth) {
                        _this.expand();
                    }
                }, function () {
                    if ($('body').hasClass('sidebar-mini')
                        && $('body').hasClass('sidebar-expanded-on-hover')
                        && $(window).width() > screenWidth) {
                        _this.collapse();
                    }
                });
            },
            expand: function () {
                $("body").removeClass('sidebar-collapse').addClass('sidebar-expanded-on-hover');
            },
            collapse: function () {
                if ($('body').hasClass('sidebar-expanded-on-hover')) {
                    $('body').removeClass('sidebar-expanded-on-hover').addClass('sidebar-collapse');
                }
            }
        },
        /* Tree()
         * ======
         * Converts the sidebar into a multilevel
         * tree view menu.
         *
         * @type Function
         * @Usage: $.MainNavigator.tree('.sidebar')
         */
        tree: function (menu) {
            var _this = this;

            //$.history.init(function(url) {
            //    $content.load(url);
            //});
            $("li a", $(menu)).on('click', function (e) {
                //Get the clicked link and the next element
                var $this = $(this);
                var checkElement = $this.next();

                //Check if the next element is a menu and is visible
                if ((checkElement.is('.treeview-menu')) && (checkElement.is(':visible'))) {
                    //Close the menu
                    checkElement.slideUp('normal', function () {
                        checkElement.removeClass('menu-open');
                        //Fix the layout in case the sidebar stretches over the height of the window
                        //_this.layout.fix();
                    });
                    checkElement.parent("li").removeClass("active");
                }
                //If the menu is not visible
                else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
                    //Get the parent menu
                    var parent = $this.parents('ul').first();
                    //Close all open menus within the parent
                    var ul = parent.find('ul:visible').slideUp('normal');
                    //Remove the menu-open class from the parent
                    ul.removeClass('menu-open');
                    //Get the parent li
                    var parent_li = $this.parent("li");

                    //Open the target menu and add the menu-open class
                    checkElement.slideDown('normal', function () {
                        //Add the class active to the parent li
                        checkElement.addClass('menu-open');
                        parent.find('li.active').removeClass('active');
                        parent_li.addClass('active');
                        //Fix the layout in case the sidebar stretches over the height of the window
                        _this.layout.fix();
                    });
                }
                //if this isn't a link, prevent the page from being redirected
                if (checkElement.is('.treeview-menu')) {
                    if($this.attr("href") == "#"){
                        e.preventDefault();
                    }
                }else if($this.not("[openMode=dialog],[openMode=nwin]")){
                    $this.parents("ul:first").find('li.active').removeClass('active');
                    $this.parent("li").addClass("active");
                }
            });
        },

        /* ControlSidebar
         * ==============
         * Adds functionality to the right sidebar
         *
         * @type Object
         * @usage $.MainNavigator.controlSidebar.activate(options)
         */
        controlSidebar: {
            //instantiate the object
            activate: function () {
                //Get the object
                var _this = this;
                //Update options
                var o = $options.app.controlSidebarOptions;
                //Get the sidebar
                var sidebar = $(o.selector);
                //The toggle button
                var btn = $(o.toggleBtnSelector);

                //Listen to the click event
                btn.on('click', function (e) {
                    e.preventDefault();
                    //If the sidebar is not open
                    if (!sidebar.hasClass('control-sidebar-open')
                        && !$('body').hasClass('control-sidebar-open')) {
                        //Open the sidebar
                        _this.open(sidebar, o.slide);
                    } else {
                        _this.close(sidebar, o.slide);
                    }
                });

                //If the body has a boxed layout, fix the sidebar bg position
                var bg = $(".control-sidebar-bg");
                _this._fix(bg);

                //If the body has a fixed layout, make the control sidebar fixed
                if ($('body').hasClass('fixed')) {
                    _this._fixForFixed(sidebar);
                } else {
                    //If the content height is less than the sidebar's height, force max height
                    if ($('.content-wrapper, .right-side').height() < sidebar.height()) {
                        _this._fixForContent(sidebar);
                    }
                }
            },
            //Open the control sidebar
            open: function (sidebar, slide) {
                var _this = this;
                //Slide over content
                if (slide) {
                    sidebar.addClass('control-sidebar-open');
                } else {
                    //Push the content by adding the open class to the body instead
                    //of the sidebar itself
                    $('body').addClass('control-sidebar-open');
                }
            },
            //Close the control sidebar
            close: function (sidebar, slide) {
                if (slide) {
                    sidebar.removeClass('control-sidebar-open');
                } else {
                    $('body').removeClass('control-sidebar-open');
                }
            },
            _fix: function (sidebar) {
                var _this = this;
                if ($("body").hasClass('layout-boxed')) {
                    sidebar.css('position', 'absolute');
                    sidebar.height($(".wrapper").height());
                    $(window).resize(function () {
                        _this._fix(sidebar);
                    });
                } else {
                    sidebar.css({
                        'position': 'fixed',
                        'height': 'auto'
                    });
                }
            },
            _fixForFixed: function (sidebar) {
                sidebar.css({
                    'position': 'fixed',
                    'max-height': '100%',
                    'overflow': 'auto',
                    'padding-bottom': '50px'
                });
            },
            _fixForContent: function (sidebar) {
                $(".content-wrapper").css('min-height', sidebar.height());
            }
        }
    };

    $F.regInitMethod("globalMainNav",function(){
        var app = $.MainNavigator,o=$options.app;
        //Activate the layout maker
        app.layout.activate();

        //Enable sidebar tree view controls
        app.tree('.sidebar');

        //Enable control sidebar
        if (o.enableControlSidebar) {
            app.controlSidebar.activate();
        }

        //Activate sidebar push menu
        if (o.sidebarPushMenu) {
            app.pushMenu.activate(o.sidebarToggleSelector);
        }

        //Activate fast click
        if (o.enableFastclick && typeof FastClick != 'undefined') {
            FastClick.attach(document.body);
        }
        $F.regInitMethod("globalMainNav");
    });
    return $.MainNavigator;
});
