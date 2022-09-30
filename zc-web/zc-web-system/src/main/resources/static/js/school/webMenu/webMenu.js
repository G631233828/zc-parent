// 根据选择的目录生成菜单
function changeType() {
	var type = $("#type").val();
	if (type == 0) {
		// 创建的是主题，隐藏所有的菜单
		$("#parent").css("display", "none");
		$("#first").css("display", "none");
		$("#parentId").attr("disabled", "disabled");
		$("#firstLevel").attr("disabled", "disabled");
		//隐藏是否创建尾部链接与是否创建微网站
		$("#showexternalLink").css("display", "none");
		$("#externalLink").attr("disabled", "disabled");
		$("#showweiWeb").css("display", "none");
		$("#weiWeb").attr("disabled", "disabled");
	} else if (type == 1) {
		// 创建一级菜单 显示主题选项下拉
		$("#parent").show();
		$("#first").css("display", "none");
		$("#firstLevel").attr("disabled", "disabled");
		$("#parentId").attr("disabled", false);
		$("#showexternalLink").show();
		$("#externalLink").attr("disabled", false);
		$("#showweiWeb").show();
		$("#weiWeb").attr("disabled", false);
	} else if (type == 2) {
		// 创建一级菜单 显示选择主题，选择主题之后根据选择的主题自动生成一级菜单选项
		$("#parent").show();
		$("#parentId").attr("disabled", false);
		$("#showexternalLink").show();
		$("#externalLink").attr("disabled", false);
		$("#firstLevel").attr("disabled", false);
		$("#first").show();
		$("#showweiWeb").show();
		$("#weiWeb").attr("disabled", false);

	}
	selectFirstLevel();
}

function setUrl() {
	var externalLink = $("#externalLink").val();
	if (externalLink == 'true') {
		$("#menuUrl").show();
		$("#resUrl").attr("disabled", false);
	} else if (externalLink == 'false') {
		$("#menuUrl").css("display", "none");
		$("#resUrl").attr("disabled", "disabled");
	}
}


function weiweb() {
	var weiWeb = $("#weiWeb").val();
	if (weiWeb == 'true') {
		$("#weiwebImg").show();
		$("#img").attr("disabled", false);
		$("#oldImg").attr("disabled", "false");
	} else if (weiWeb == 'false') {
		$("#weiwebImg").css("display", "none");
		$("#img").attr("disabled", "disabled");
		$("#oldImg").attr("disabled", "disabled");
	}
}

// 选择添加二级菜单 -- 选择
function selectFirstLevel() {
	var type = $("#type").val();
	var parentId = $("#parentId").val();
	if (type == 2 && parentId != "") {
		// 需要通过ajax加载对应的菜单列表
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/school/webMenu/getFirstLevel",
			data : "parentId=" + parentId,
			dataType : "json",
			success : function(data) {
				var sale = "<option value=''>----选择一级菜单----</option>";
				$.each(data.data, function(index, item) {
					sale += "<option value=" + item.id + ">" + item.name
							+ "</option>";
				});
				$("#firstLevel").html(sale)
			}
		});

	}
}

// if (type == 1) {
// $("#parent").show();
// $("#button").show();
// $("#parentId").attr("disabled", false);
// } else if (type == 0) {
// $("#parent").css("display", "none");
// $("#button").css("display", "none");
// $("#parentId").attr("disabled", "disabled");
// $("[name=operationAuthority]:checkbox").prop("checked", false);
// }

/*
 * $.validator.setDefaults({ highlight : function(a) {
 * $(a).closest(".form-group").removeClass("has-success") .addClass("has-error") },
 * success : function(a) {
 * a.closest(".form-group").removeClass("has-error").addClass( "has-success") },
 * errorElement : "span", errorPlacement : function(a, b) { if (b.is(":radio") ||
 * b.is(":checkbox")) { a.appendTo(b.parent().parent().parent()) } else {
 * a.appendTo(b.parent()) } }, errorClass : "help-block m-b-none", validClass :
 * "help-block m-b-none" });
 */
$().ready(function() {
	$("#commentForm").validate();
	var a = "<i class='fa fa-times-circle'></i> ";
	$("#webMenuForm").validate({
		rules : {
			name : {
				required : true,
				minlength : 2
			},
			type : {
				required : true,
			},
			parentId : {
				required : true,
			},
			resUrl : {
				required : true,
			},
			firstLevel : {
				required : true,
			},
			sort: {
				digits:true,
			}

		},
		messages : {
			name : {
				required : a + "请输入资源名称",
				minlength : a + "资源名称必须两个字符以上"
			},
			type : {
				required : a + "请选择菜单类别",
			},
			parentId : {
				required : a + "请选择主题",
			},
			resUrl : {
				required : a + "请输入访问路径",
			},
			firstLevel : {
				required : a + "请选择一级菜单",
			},
			sort: {
				digits:a+"请输入有效的数字"
			}
		}
	});
});
