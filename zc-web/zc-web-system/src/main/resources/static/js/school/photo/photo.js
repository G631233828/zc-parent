
$().ready(function() {
	$("#commentForm").validate();
	var a = "<i class='fa fa-times-circle'></i> ";
	$("#activityForm").validate({
		rules : {

			
			name : {
				required : true,
			},
			author : {
				required : true,
			}

		},
		messages : {

			name : {
				required : a + "请输入图册名称",
			},
			author : {
				required : a + "请输入创建人",
			},

		}
	});
});





function createActivity(){
	$("#name").val('');
	$("#author").val('');
	$("#setid").html('');
	$("#createActivity").modal('show');
}


function toUploadImg(){
	$("#uploadImgModel").modal('show');

}






function toeditActivity() {
	
		//编辑为表格上方的按钮
		var a = $("input[name='ids']:checked").length;
		if (a == 1) {
			var id = $("input[name='ids']:checked").val();
		
			$.ajax({
				type : 'POST',
				url : "photo/edit",
				data : "id=" + id,
				dataType : "json",
				success : function(data) {
					if(data.status == 200){
						$("#name").val(data.data.name);
						$("#author").val(data.data.author);
						var showInindex = data.data.showInIndex;
						$("select option[value='"+showInindex+"']").attr("selected","selected"); 
						$("#setid").html('<input type="hidden" id ="id" name="id" value='+data.data.id+ '>')
						$("#createActivity").modal('show');
					}else{
						$("#myMessageModal").modal('show');
						$("#modalbody").text(data.msg)
					}
				}
			});
			
			
			
		} else if (a == 0) {
			swal({
				type : "warning",
				title : "",
				text : "请选择一条记录进行编辑!!",
			});
		} else {
			swal({
				type : "warning",
				title : "",
				text : "请不要同时选择多个进行编辑",
			});
		}
	

}







