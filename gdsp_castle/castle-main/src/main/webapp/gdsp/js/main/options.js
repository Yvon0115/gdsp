/**
 * 系统选项设置
 */
define(function(){
	return {
		components: {
			"openwith":["[openMode]","main/openwith","initOpenWith"]
		},
		includes:["main/nav","main/skin"],
		/* --------------------
		 * - MainNavigator Options -
		 * --------------------
		 * Modify these options to suit your implementation
		 */
		app: {
			//Add slimscroll to navbar menus
			//This requires you to load the slimscroll plugin
			//in every page before app.js
			navbarMenuSlimscroll: true,
			navbarMenuSlimscrollWidth: "3px", //The width of the scroll bar
			navbarMenuHeight: "200px", //The height of the inner menu
			//Sidebar push menu toggle button selector
			sidebarToggleSelector: "[data-toggle='offcanvas']",
			//Activate sidebar push menu
			sidebarPushMenu: true,
			//Activate sidebar slimscroll if the fixed layout is set (requires SlimScroll Plugin)
			sidebarSlimScroll: true,
			//Enable sidebar expand on hover effect for sidebar mini
			//This option is forced to true if both the fixed layout and sidebar mini
			//are used together
			sidebarExpandOnHover: false,
			//Enable Fast Click. Fastclick.js creates a more
			//native touch experience with touch devices. If you
			//choose to enable the plugin, make sure you load the script
			//before MainNavigator's main-app.js
			enableFastclick: true,
			//Control Sidebar Options
			enableControlSidebar: true,
			controlSidebarOptions: {
				//Which button should trigger the open/close event
				toggleBtnSelector: "[data-toggle='control-sidebar']",
				//The sidebar selector
				selector: ".control-sidebar",
				//Enable slide over content
				slide: true
			},
			//Define the set of colors to use globally around the website
			colors: {
				lightBlue: "#3c8dbc",
				red: "#f56954",
				green: "#00a65a",
				aqua: "#00c0ef",
				yellow: "#f39c12",
				blue: "#0073b7",
				navy: "#001F3F",
				teal: "#39CCCC",
				olive: "#3D9970",
				lime: "#01FF70",
				orange: "#FF851B",
				fuchsia: "#F012BE",
				purple: "#8E24AA",
				maroon: "#D81B60",
				black: "#222222",
				gray: "#d2d6de"
			},
			//application open mode
			openMode:false,
			//The standard screen sizes that bootstrap uses.
			//If you change these in the variables.less file, change
			//them here too.
			screenSizes: {
				xs: 480,
				sm: 768,
				md: 992,
				lg: 1200
			}
		}
	};
});