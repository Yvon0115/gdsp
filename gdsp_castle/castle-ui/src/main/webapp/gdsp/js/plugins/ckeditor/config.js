/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
//	config.toolbarGroups = [
//		{ name: 'clipboard',   groups: [ 'Undo','Redo' ] },
//		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
//		{ name: 'links' },
//		{ name: 'insert' ,	   groups: [ 'SpecialChar' ]},
//		{ name: 'forms'	},	   
//		{ name: 'tools' },
//		'/',
//		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
//		{ name: 'others' },
//		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
//		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
//		{ name: 'styles' },
//		{ name: 'colors' ,	   groups: [ 'TextColor','BGColor' ] },
//		{ name: 'about' }
//	];
	config.toolbar = 'Custom';
	config.toolbar_Custom =
		[
		    { name: 'document', items : [ 'Source' ] },
		    { name: 'clipboard', items : [ 'Cut','Copy','-','Undo','Redo' ] },
		    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
		    { name: 'insert', items : [ 'Image','Table','SpecialChar' ] },
		    { name: 'paragraph', items : [ 'JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },
		    { name: 'styles', items : [ 'Font','FontSize' ] },
		    { name: 'colors', items : [ 'TextColor','BGColor' ] },
		    { name: 'tools', items : [ 'Maximize' ] }
		];
	
//	config.toolbar_Full =
//		[
//		    { name: 'document', items : [ 'Source','-','Save','NewPage','DocProps','Preview','Print','-','Templates' ] },
//		    { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
//		    { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
//		    { name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 
//		        'HiddenField' ] },
//		    '/',
//		    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
//		    { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
//		    '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
//		    { name: 'links', items : [ 'Link','Unlink','Anchor' ] },
//		    { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
//		    '/',
//		    { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
//		    { name: 'colors', items : [ 'TextColor','BGColor' ] },
//		    { name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }
//		];
	
	config.height = 400;
	
	config.skin = 'bootstrapck';
	
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋;楷体/楷体;华文楷体/华文楷体;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+ config.font_names;
	
	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Underline,Subscript,Superscript';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	config.enterMode = CKEDITOR.ENTER_BR;
	
    config.image_previewText = '外联图片请直接输入图片地址。';//这里的内容自己可以定义。同样可以设置""空
    
    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
  
    config.filebrowserUploadUrl = projectName + "/systools/ckeditor/upload.d";
    
	config.filebrowserImageUploadUrl = projectName + '/systools/ckeditor/upload.d?type=Images';  
	//隐藏下边元素信息	
	config.removePlugins = 'elementspath';
	//完全隐藏下边框
	config.resize_enabled = false;
	//config.shiftEnterMode = CKEDITOR.ENTER_P;
	
};


