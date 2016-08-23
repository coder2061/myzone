/**   
* @Title: main.js 
* @Package js 
* @Description: 主页调用js 
* @author jiangyf  
* @date 2016年1月9日 下午9:51:02 
* @version V1.0   
*/
var routepath = $("base").attr("routepath");
$(function () {
	$("#checkall").click(function(){
		if (this.checked) {
			$(".checkone").prop("checked", true);
		} else {
			$(".checkone").prop("checked", false);
		}
	});
	
	$("#editMemberinfo").click(function(){
		$.ajax({
            type: "POST",
            url: routepath + "member/memberInfo",
            data: "",
            dataType: "json",
            async:false,
            success: function(data){ 
            	var member = data.member;
               	 if (member != null) {
               		$("#NameID").val(strOper(member.Name, ""));
                	$("#EmailID").val(strOper(member.Email, ""));
        			$("#MobileID").val(strOper(member.Mobile, ""));
        			$("#QQID").val(strOper(member.QQ, ""));
        			$("#AddressID").val(strOper(member.Address, ""));
        			$("#avatarID").attr("src",strOper(member.HeadPic, "/img/avatar.jpg"));
     			 } else {
     				alert("卡死哥了");
				 }
            }
        });
	});
	
	$("#ajaxContactList").click(function(){
		$.ajax({
            type: "POST",
            url: routepath + "contact/ajaxContactList",
            data: "",
            dataType: "json",
            async:false,
            success: function(data){ 
            	var online = data.online;
               	 if (online!=null && online.length>0) {
               		var str = "";
	               	$.each(online, function (i, items) { 
	       				str += "<li><a href='#'><span><img alt='' class='img-chat img-online img-circle' src='"+strOper(items.HeadPic,'/img/avatar.jpg')+"'></span><b>"+strOper(items.Name,'')+"</b></a><br><i>Last seen : 08:00 PM</i></li>";
	     	  		})
	               	$("#Online").html(str);
               	 }
               	var Offline = data.Offline;
              	 if (Offline!=null && Offline.length>0) {
              		var str = "";
	               	$.each(Offline, function (i, items) { 
	       				str += "<li><a href='#'><span><img alt='' class='img-chat img-online img-circle' src='"+strOper(items.HeadPic,'/img/avatar.jpg')+"'></span><b>"+strOper(items.Name,'')+"</b></a><br><i>Last seen : 08:00 PM</i></li>";
	     	  		})
	               	$("#Offline").html(str);
              	 }
            }
        });
	});
	
});