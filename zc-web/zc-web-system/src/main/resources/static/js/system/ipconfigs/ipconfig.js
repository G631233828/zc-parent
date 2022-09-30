
$().ready(function() {
		$("#commentForm").validate();
		var a = "<i class='fa fa-times-circle'></i> ";
		$("#ipconfigForm").validate({
			rules : {
				ip : {
					required : true,
					remote : {
						url : getRootPath() + "/admin/ipconfig/ajaxgetRepletes",
						type : "POST",
						data : {
							ip : function() {
								return $("#ip").val();
							}
						},
						dataType : "json",
						dataFilter : function(data, type) {
							var oldip = $("#oldip").val();
							var ip_ = $("#ip").val();
							if(oldip == ip_){
								return true;
							}
							var jsondata = $.parseJSON(data);
							if (jsondata.status == 200) {
								return true;
							}
							return false;
						}
					},
					ipaddress : true,
				},
				
			},
			messages : {
				ip : {
					required : a + "请输入ip地址",
					remote : a + "当前ip地址已经存在！",
					ipaddress: a+ "请输入一个合法的ip地址"
				},
				
				
			}
		});
	});

jQuery.validator.addMethod("ipaddress", function(value, element) {
	return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256));
	}, "请输入合法的IP信息");
