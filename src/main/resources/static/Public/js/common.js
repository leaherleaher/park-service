// JavaScript Document
	
	var ttgWeb = {
		//左右高度相等
		lautoh : function(){
			var h = $(".content").height();
			$("menu").css("height",h + "px");
            $("iframe").css("height",h + "px");
		},

		//隔行变色
		oddColor : function(){
			$(".tr_color tbody tr:odd").css({"background-color":"#efefef"});
			$(".editLink ul").each(function(){
				$(this).children("li:even").css({"background-color":"#efefef"});
			})
		}
	}
$(function(){
//IE6、7提示
	if (window.ActiveXObject) {
		var ua = navigator.userAgent.toLowerCase();
		var ie=ua.match(/msie ([\d.]+)/)[1]
		if(ie==6.0|ie==7.0){
		$("body").prepend("<div id='popDiv'>您使用的是较低版本浏览器，建议您使用IE8.0以上版本浏览器或者<a href='http://www.firefox.com.cn/download/'>firefox</a>、<a href='http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html'>chrome</a>、<a href='http://chrome.360.cn/'>360极速</a>浏览器，获得更好的操作体验。<span></span></div>");
		}
	 }	
	 $("#popDiv span").click(function(){
	 	$("#popDiv").hide();
	 });
});


$(window).load(function() {  
	ttgWeb.lautoh();
	ttgWeb.oddColor();
	$(".sel_wrap").on("change", function() {
	    var o;
	    var opt = $(this).find('option');
	    opt.each(function(i) {
	        if (opt[i].selected == true) {
	            o = opt[i].innerHTML;
	        }
	    })
	    $(this).find('label').html(o);
	}).trigger('change');
});

/*页面数据加载*/
function loading() {
    layui.use('layer', function(){
        var layer = layui.layer;
        var msg=layer.msg('玩命加载中...', {
            icon: 16
            ,time:60000
            ,anim: -1
            ,fixed: false
        });
        return msg;
    });
}

/*页面数据加载*/
function closeAll() {
    layui.use('layer', function(){
        var layerrr = layui.layer;
        layerrr.closeAll();
    });
}

function closeOne(index) {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.close(index);
    });
}

/*错误提示*/
function msg(msg) {
	msg = msg.trim();
    layui.use('layer', function(){
        var layerr = layui.layer;
        layerr.msg(msg);
    });
}

/*正确提示*/
function msgOk(msg) {
    msg = msg.trim();
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg(msg, {icon: 6});
    });
}

//校验手机号码
function isPoneAvailable(poneInput) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(poneInput)) {
        return false;
    } else {
        return true;
    }
}

//校验邮箱
function checkEmail($email) {
    var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
    if(myReg.test($email)){
        return true;
    }else{
        return false;
    }
}

