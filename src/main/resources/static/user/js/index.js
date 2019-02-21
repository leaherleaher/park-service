let provinces = new Array('京','津','冀','晋','蒙','辽','吉','黑','沪',
    '苏', '浙','皖','闽','赣','鲁','豫','鄂','湘',
    '粤','桂', '琼','渝','川', '贵','云','藏','陕',
    '甘','青','宁', '新','台','港','澳');

let keyNums = new Array(
    '0','1','2','3','4','5','6','7','8','9',
    'Q','W','E','R','T','Y','U','I','O','P',
	'A','S','D','F','G','H','J','K','L','Z',
    'X','C','V','B','N','M');
let next=0;
	function showProvince(){
			$("#pro").html("");
			let ss="";
			for(let i=0;i<provinces.length;i++){
				ss=ss+addKeyProvince(i)
			}
			$("#pro").html("<ul class='clearfix ul_pro'>"+ss+"<li class='li_return' onclick='cleanRen();'><span>X</span></li><li class='li_close' onclick='closePro();'><span>关闭</span></li><li class='li_clean' onclick='cleanPro();'><span>清空</span></li></ul>");
	}
	function showKeybord(){
			$("#pro").html("");
			let sss="";
			for(let i=0;i<keyNums.length;i++){
				sss=sss+'<li class="ikey ikey'+i+' '+(i>9?"li_zm":"li_num")+' '+(i>28?"li_w":"")+'" ><span onclick="choosekey(this,'+i+');">'+keyNums[i]+'</span></li>'
			}
			$("#pro").html("<ul class='clearfix ul_keybord'>"+sss+"<li class='li_return' onclick='cleanRen();'><span>X</span></li><li class='li_close' onclick='closePro();'><span>关闭</span></li><li class='li_clean' onclick='cleanPro();'><span>清空</span></li></ul>");
	}
    function addKeyProvince(provinceIds){
        let addHtml = '<li>';
            addHtml += '<span onclick="chooseProvince(this);">'+provinces[provinceIds]+'</span>';
            addHtml += '</li>';
            return addHtml;
    }
    function chooseProvince(obj){
		$(".input_pro span").text($(obj).text());
		$(".input_pro").addClass("hasPro");
		$(".input_pp").find("span").text("");
		$(".ppHas").removeClass("ppHas");
		next=0;
		showKeybord();
	}
	function choosekey(obj,jj){
		if(jj==29){
			console.log($(".car_input").attr("data-pai"))
			layer.closeAll();
		}else if(jj==37){
			if($(".ppHas").length==0){
				$(".hasPro").find("span").text("");
				$(".hasPro").removeClass("hasPro");
				showProvince();
                //showKeybord()
				next=0;
			}
			$(".ppHas:last").find("span").text("");
			$(".ppHas:last").removeClass("ppHas");
			next=next-1;
			if(next<1){
				next=0;
			}
		}else{
			if(next>5){
				return
			}
			for(let i = 0; i<$(".input_pp").length;i++){
				if(next==0 & jj<10 & $(".input_pp:eq("+next+")").hasClass("input_zim")){
					layer.open({
						content: '车牌第二位为字母',
						skin: 'msg',
						time: 1,
                        style: 'width:100%;height:100px;font-size:45px;padding-top:50px'
					});
					return
				}
				$(".input_pp:eq("+next+")").find("span").text($(obj).text());
				$(".input_pp:eq("+next+")").addClass("ppHas");
				next=next+1;
				if(next>5){
					next=6;
				}
				getpai();
				return
			}
		}
	}
	function closePro(){
		layer.closeAll()
	}
	function cleanPro(){
		$(".ul_input").find("span").text("");
		$(".hasPro").removeClass("hasPro");
		$(".ppHas").removeClass("ppHas");
		next=0;
	}
	function cleanRen(){
		console.log(123)
	}
	function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
	function getpai(){
		let pai=trimStr($(".car_input").text());
		$(".car_input").attr("data-pai",pai);
	}
window.onload = function() {
	$(".input_pro").click(function(){
		layer.open({
			type: 1
			,content: '<div id="pro"></div>'
			,anim: 'up'
			,shade :false
			,style: 'position:fixed; bottom:0; left:0; width: 100%; height: auto; padding:0; border:none;'
		});
		showProvince()
        //showKeybord()
	});
	$(".input_pp").click(function(){
		if($(".input_pro").hasClass("hasPro")){ // 如果已选择省份
			layer.open({
				type: 1
				,content: '<div id="pro"></div>'
				,anim: 'up'
				,shade :false
				,style: 'position:fixed; bottom:0; left:0; width: 100%; height: auto; padding:0; border:none;'
			});
			showKeybord()
		}else{
			$(".input_pro").click()
		}
	})
};
