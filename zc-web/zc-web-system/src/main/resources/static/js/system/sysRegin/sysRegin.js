$().ready(function() {
	$("#commentForm").validate();
	var a = "<i class='fa fa-times-circle'></i> ";
	$("#sysReginForm").validate({
		rules : {
			roleName : {
				required : true,
				minlength : 2,
				remote : {
					url : "sysRegin/ajaxgetRepletes",
					type : "POST",
					data : {
						name : function() {
							return $("#name").val();
						}
					},
					dataType : "json",
					dataFilter : function(data, type) {

						var hidName = $("#hidName").val();
						var name = $("#name").val();
						if (name == hidname) {
							return true;
						}

						var jsondata = $.parseJSON(data);
						
						if (jsondata.status == 200) {
							return true;
						}
						return false;
					}
				}
			},

		},
		messages : {
			roleName : {
				required : a + "请输入区域名称",
				minlength : a + "区域名必须两个字符以上",
				remote : a + "不能添加重复的区域"
			},
		}
	});

});
