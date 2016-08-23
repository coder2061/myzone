/**
* @Description: 常用js
* @author jiangyf
* @date 2015年10月26日
*/
//-------------------------------------------------------- table --------------------------------------------------------
//table设置
function setTable(){
	$('table th input:checkbox').on('click' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
			
	});

	$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
}
function tooltip_placement(context, source) {
	var $source = $(source);
	var $parent = $source.closest('table')
	var off1 = $parent.offset();
	var w1 = $parent.width();

	var off2 = $source.offset();
	var w2 = $source.width();

	if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) {
		return 'right';
	} else {
		return 'left';
	}
}
//-------------------------------------------------------- tips --------------------------------------------------------
//操作确认提示
function opercfm(msg) { 
	if (!confirm(msg)) { 
		window.event.returnValue = false; 
	} 
}
//删除操作确认提示
function delcfm() { 
	if (!confirm("确认要删除？")) { 
		window.event.returnValue = false; 
	} 
}
//置顶操作确认提示
function topcfm() { 
	if (!confirm("确认要置顶？")) { 
		window.event.returnValue = false; 
	} 
}
//取消置顶操作确认提示
function untopcfm() { 
	if (!confirm("确认要取消置顶？")) { 
		window.event.returnValue = false; 
	} 
}
//操作结果提示
function reltips(flag){
	if(flag =='true'){
		alert("操作成功!");
	}
}
//-------------------------------------------------------- province,city --------------------------------------------------------
//加载省
function setProvince(province,city,adressjson){
	if(province != null || province !=""){
		var myobj=eval(adressjson); 
		for(var i=0;i<myobj.length;i++){ 
			if(myobj[i][province]!=null){
				for(var j=0;j<myobj[i][province].length;j++){
					$("#city").append("<option value='"+myobj[i][province][j]+"'>" + myobj[i][province][j] + "</option>");
				}
			}
		}
		$("#city  option[value='"+city+"'] ").attr("selected",true);
	}
}
//省市联动
function changeProvince(adressjson){
	$('#province').change(function(){
		$("#city").html("");
		$("#city").append("<option value='' >&nbsp;</option>");
		var key = $(this).val();
		var myobj=eval(adressjson); 
		for(var i=0;i<myobj.length;i++){ 
			if(myobj[i][key]!=null){
				for(var j=0;j<myobj[i][key].length;j++){
					$("#city").append("<option value='"+myobj[i][key][j]+"'>" + myobj[i][key][j] + "</option>");
				}
			}
		} 
	});
}
//-------------------------------------------------------- elements --------------------------------------------------------
//设置日期样式
function setdate(elmname){
	$('input[name='+elmname+']').daterangepicker(
			{
				format: 'YYYY-MM-DD', 
				separator:'至',
				locale:{
					applyLabel: '确定',
					cancelLabel: '取消',
					fromLabel: '从',
					toLabel: '到',
					daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
		            monthNames: ['一月', '二月', '三月', '四月', '五月', 
		              '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
					}
			},function(start, end, label) {
			  }).prev().on(ace.click_event, function(){
		$(this).next().focus();
		
	});
}
//-------------------------------------------------------- ajax --------------------------------------------------------
//修改状态
function statusopr(tips,link){
	if (confirm(tips)) {
		getAjaxUrl(link,function(data){
			if(data.status == 0){
				alert("操作成功！");
				window.onload = true;
			}else{
				alert("操作失败！");
			}
		});
	}else{
		window.event.returnValue = false;
		return false;
	}
}
//权限判断
function checkPower(statusText, url){
	if(confirm('是否确认进行'+statusText+'操作？')){
		$.ajax({
	         type: "GET",
	         url: url+"&type=1",
	         dataType: "json",
	         success: function(data){
	        	if(data.power == false){
	        		alert("没有权限");
	        	} else {
					window.location.href = url+"&type=2";
				}
	         }
	     });
	}
}
//通用ajax函数
function getAjaxUrl(links,functions){
	var fetchingData = $.get(links);
	fetchingData.done(functions);
}
//-------------------------------------------------------- upload --------------------------------------------------------
//上传图片
function uploadpics(index){
	document.getElementById("idest").value = index;
	document.getElementById("pic").click();  
}
//上传图片成功
function uploadSuccess(msg) {
    if (msg.split('|').length > 1) {
        $('input[name=pic'+msg.split('|')[2]+']').val(msg.split('|')[1]);
    }
}
function uploadSuccess2(msg) {
	if (msg.split('|').length > 1) {
		$('input[name=pic1]').val(msg.split('|')[1]);
	}
}
//-------------------------------------------------------- check --------------------------------------------------------
//校验是否为正整数
function checkIsInt(vals){
	var re = /^[1-9]+[0-9]*]*$/;
	return re.test(vals);
}
//特殊字符校验
function containSpecial(vals)      
{      
   var containSpecial = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/);      
   return ( containSpecial.test(vals) );      
}
//是否为空
function isBlank(val){
	if (val==null || val=="" || val==undefined) {
		return true;
	} else {
		return false;
	}
}
//是否不为空
function notBlank(val){
	if (val!=null && val!="" && val!=undefined) {
		return true;
	} else {
		return false;
	}
}
function strOper(str, str2){
	if (isBlank(str)) {
		return str2;
	} else {
		return str;
	}
}
//-------------------------------------------------------- tools --------------------------------------------------------
//获得字符串实际长度
function getLength(str) {
     var realLength = 0, len = str.length, charCode = -1;
     for (var i = 0; i < len; i++) {
         charCode = str.charCodeAt(i);
         if (charCode >= 0 && charCode <= 128) realLength += 1;
         else realLength += 2;
     }
     return realLength;
 }



function test(){
	$.ajax({
        type: "POST",
        url: "${routepath}commodity/setCardPrices",
        data: "cmdID="+cmdID,
        dataType: "json",
        async:false,
        success: function(data){ 
       	 var cardList = data.cardList;
       	 var str = "该商品未设置储值会员卡价格!";
       	 if (cardList.length > 0) {
	          		str = "<table style='width: 230px;text-align: left;'>";
	               	$.each(cardList, function (i, items) { 
	       				str += "<tr><td width='70%'>"+items.Name+"</td>"+"<td width='30%'>"+items.Price+"&nbsp;元</td></tr>";
	     	  		})
	     	  		str += "</table>";
			 }
	  		 $("#cardPrices").html(str);
        }
    });
}