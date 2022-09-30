
//选择添加二级菜单 -- 选择
function selectFirstLevel() {
	var parentId = $("#parentId").val();
	if (parentId != "") {
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
// 选择栏目 -- 选择
function selectSecondLevel() {
	var firstLevel = $("#firstLevel").val();
	if (firstLevel != "") {
		// 需要通过ajax加载对应的菜单列表
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/school/webMenu/getSecondLevel",
			data : "firstLevel=" + firstLevel,
			dataType : "json",
			success : function(data) {
			
				/*
				 * $.each(data.data, function(index, item) { sale += "<option
				 * value=" + item.id + ">" + item.name + "</option>"; });
				 */
				// ----------------
				var options = [], _options;
				$.each(data.data, function(index, item) {
					sale = "<option value=" + item.id + ">" + item.name
							+ "</option>";
					options.push(sale);
				});
				_options = options.join('');
				$('#number-multiple')[0].innerHTML = _options;
				$("#number-multiple").selectpicker('refresh');
				$('#number-multiple').selectpicker('render')
//				$("#secondLevel").html(sale)
			}
		});

	}
}

// function toSort(o){
// var sort = $("#"+o+"_sort").val();
//	
// var flag = $.isNumeric(sort);
// if(!flag){
// alert("请输入正确的数字！");
// return;
// }
//	
// $.ajax({
// type : 'POST',
// url : "indexSetting/edit",
// data : "id=" + o+"&sort="+sort,
// dataType : "json",
// success : function(data) {
// if(data.status == 200){
// window.location.href = "indexSettings";
// }else{
// alert("未能获取到数据，请刷新");
// }
//			
//			
// }
// });
//	
//	
// }
//
//

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
	$("#indexSettingForm").validate({
		rules : {

			'webMenu.id' : {
				required : true,
			},
			sort : {
				required : true,
				number : true
			},
			isArticle : {
				required : true,
			}

		},
		messages : {

			'webMenu.id' : {
				required : a + "请选择栏目",
			},
			sort : {
				required : a + "请输入排序",
				number : a + "请输入一个合法的数字！",
			},
			isArticle : {
				required : a + "请选择显示方式",
			},

		}
	});
});
