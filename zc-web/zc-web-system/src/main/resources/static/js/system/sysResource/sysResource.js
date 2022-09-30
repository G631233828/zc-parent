
// 根据选择的目录生成菜单
function changeType() {
	var type = $("#type").val();
	if (type == 1) {
		$("#parent").show();
		$("#button").show();
		$("#parentId").attr("disabled", false);
	}  else if (type == 0) {
		$("#parent").css("display", "none");
		$("#button").css("display", "none");
		$("#parentId").attr("disabled", "disabled");
		$("[name=operationAuthority]:checkbox").prop("checked", false);
	}
}








function selectIcon(){
	$("#iconModal").modal('show');
}

	/*
	$.validator.setDefaults({
		highlight : function(a) {
			$(a).closest(".form-group").removeClass("has-success")
					.addClass("has-error")
		},
		success : function(a) {
			a.closest(".form-group").removeClass("has-error").addClass(
					"has-success")
		},
		errorElement : "span",
		errorPlacement : function(a, b) {
			if (b.is(":radio") || b.is(":checkbox")) {
				a.appendTo(b.parent().parent().parent())
			} else {
				a.appendTo(b.parent())
			}
		},
		errorClass : "help-block m-b-none",
		validClass : "help-block m-b-none"
	});*/
	$().ready(function() {
		$("#commentForm").validate();
		var a = "<i class='fa fa-times-circle'></i> ";
		$("#sysResourceForm").validate({
			rules : {
				name : {
					required : true,
					minlength : 2
				},
				resKey : {
					required : true,
				},
				resUrl : {
					required : true,
				},

			},
			messages : {
				name : {
					required : a + "请输入资源名称",
					minlength : a + "资源名称必须两个字符以上"
				},
				resKey : {
					required : a + "请输入资源权限标识"
				},
				resUrl : {
					required : a + "请输入访问路径"
				}
			}
		});
	});











