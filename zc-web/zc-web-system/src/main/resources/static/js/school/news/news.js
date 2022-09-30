
	$().ready(function() {
		$("#commentForm").validate();
		var a = "<i class='fa fa-times-circle'></i> ";
		$("#newsForm").validate({
			rules : {
				title : {
					required : true,
					minlength : 2
				},
				author : {
					required : true,
					minlength : 2
				},


			},
			messages : {
				title : {
					required : a + "请输入新闻标题",
					minlength : a + "新闻标题必须两个字符以上"
				},
				author : {
					required : a + "请输入作者",
				},
				
			}
		});
	});







