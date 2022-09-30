	$().ready(function() {
	$("#commentForm").validate();
	var a = "<i class='fa fa-times-circle'></i> ";
	$("#sysOperationAuthorityForm").validate({
		rules : {
			name : {
				required : true,
				minlength : 2,
				remote : {
					url :getRootPath()+ "/admin/sysOperation/ajaxgetRepletes",
					type : "POST",
					data : {
						roleName : function() {
								return  $("#name").val() ;
						}
					},
					dataType : "json",
					dataFilter : function(data, type) {
						
						var hidname = $("#hidname").val();
						var name = $("#name").val();
						if(name == hidname){
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
			name : {
				required : a + "请输入按钮名称",
				minlength : a + "按钮名称必须两个字符以上",
				remote   : a + "不能添加重复的按钮名称"
			},
		}
	});

	});	
	
	
	
