//初始化富文本编辑器
	var ue = UE.getEditor('container',{
		initialFrameWidth:1000,  //初始化编辑器宽度,默认1000  
        initialFrameHeight:140  //初始化编辑器高度,默认320
	});
	
	
	
	//打开商品添加页面
	function openGoodsAdd() {
		$('#addGoodsForm').form('clear');
		$('#addDl').dialog({
			draggable : false,			
			closed : false,
			modal : true,
			title : "添加商品",
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : addGoods
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#addDl').dialog('close');
					$('#addGoodsForm').form('reset');
					ue.setContent('');
					document.getElementById("showGoodsPic").innerHTML = "";
				}
			}]
		});
	}
	
	function addGoods(){
		
		$('#addGoodsForm').form('submit', {
			url:'/goodsCheck/addGoods',
			type:'POST',
			success:function(data){
				if(data > 0){
					$('#addDl').dialog('close');
					$('#addGoodsForm').form('reset');
					UE.getEditor('container').setContent("");
					successShow();
					document.getElementById("showGoodsPic").innerHTML = "";
					$('#goodscheck').datagrid('reload');
				}
				else
				{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		});
	}
	
	
	//打开修改窗口
	function openEdit(){
		$('#goodsCheckUpdateForm').form('clear');
		var row = $("#goodscheck").datagrid('getSelected');
		if (row) {
			$('#goodsCheckUpdateDl').dialog('open').dialog({
				draggable : false,			
				closed : false,
				modal : true,
				title : "修改商品审核",
				buttons : [{
					text : '确定',
					iconCls : 'icon-ok',
					handler : edit
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#goodsCheckUpdateDl').dialog('close');
						$('#goodsCheckUpdateForm').form('reset');
						ue.setContent('');
						document.getElementById("showUGoodsPic").innerHTML = "";
					}
				}]
			});
			$('#goodsCheckUpdateForm').form('load',row);//加载数据
			$(document).ready(function(){  
				var ue3 = UE.getEditor('containerUpdate',{
					initialFrameWidth:1000,  //初始化编辑器宽度,默认1000  
			        initialFrameHeight:140  //初始化编辑器高度,默认320
				});
		
		     
		        ue3.ready(function() {//编辑器初始化完成再赋值  
		        ue3.setContent(row.content);  //赋值给UEditor 
		        });  
			   });    
		}else{
			$.messager.alert('信息提示','请选中要修改的数据');
		}
	}	
	
	/*
	*修改
	*/
	function edit(){
			$('#goodsCheckUpdateForm').form('submit', {
				url:'/goodsCheck/updateGoods',
				type:'POST',
				success:function(data){
					if(data > 0){
						$('#goodsCheckUpdateDl').dialog('close');
						$('#goodsCheckUpdateForm').form('reset');
						UE.getEditor('container').setContent("");
						document.getElementById("showGoodsPic").innerHTML = "";
						successShow();
						$('#goodscheck').datagrid('reload');
					}
					else
					{
						errorShow();
					}
				}
			});
	}
	
	
	
	//条件查询
	function doGoodsCheckSearch(){
//		var param = $.param({'pageNumber':1,'pageSize':10}) + '&' + $('#searchCheckForm').serialize();
//		var param1 = decodeURI(param); 
		
		$.ajax({ 
	          type: 'POST', 
	          url: '/goodsCheck/list', //用户请求数据的URL
	          data: $('#searchCheckForm').serialize(), 
	          error: function (XMLHttpRequest, textStatus, errorThrown) { 
	              alert("没有查询到数据"); 
	          }, 
	          success: function (data) { 
	        	  data =eval("("+data+")");
	        	  if(data.total == 0){
	        		  $.messager.alert('信息提示','</br>未检索到数据！请检查查询条件','info');
	        	  }
	              $('#goodscheck').datagrid('loadData', data.rows);
	               $('#goodsCheckPagination').pagination({ 
			    	  total:data.total
			    	  });
	          }
	       });
	}
	
	/*
	 * 删除
	 */
	function removeGoodsCheck(){
		
		var items = $('#goodscheck').datagrid('getSelections');
		var goodsids = [];
		if(items.length < 1){
			$.messager.alert('温馨提醒','请选中要删的数据');
			return ;
		}
		if(items[0].reviewStatues=='1'){
			$.messager.alert('温馨提醒','审核通过的数据不能删除,请重新选择！');
			return ;
		}
	
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				$(items).each(function(){
					goodsids.push(this.goodsids);	
				});
				$.ajax({
					url:'/goodsCheck/delete/' + goodsids,
					type:'POST',
					success:function(data){
						if(data){
							successShow();
							$('#goodscheck').datagrid('reload');
						}
						else
						{
							errorShow();		
						}
					}	
				});
			}	
		});
	}
	
	
	/*
	 * 提交审核申请/撤销申请流程
	 */
	function handleCheck(obj){
		
		var  reviewStatues;
		var ids ;
		var items = $('#goodscheck').datagrid('getSelections');
		if(items.length < 1){
			$.messager.alert('温馨提醒','请选中操作的数据');
			return ;
		}
		ids = items[0].ids;
		reviewStatues = items[0].reviewStatues;
		//提交审核
		if(obj.id == "pushCheck"){
			if(reviewStatues != 0){
				$.messager.alert('温馨提醒','您选中的不是一条待提交的数据，请重新选择其他待提交审核数据','question')
				return ;
			}
		}
		//撤销审核
		if(obj.id == "repealCheck"){
			if(reviewStatues != 3){
				$.messager.alert('温馨提醒','您选中的不是一条待审核的数据，请重新选择其他待审核数据','question')
				return ;
			}
		}
			$.ajax({
				url:'/goodsCheck/updateGoodsCheckStatus',
				type:'POST',
				data: {'ids':ids,'reviewStatues':reviewStatues},
				success:function(data){
					if(data){
						successShow();
						$('#goodscheck').datagrid('reload');
					}
					else
					{
						errorShow();		
					}
				}	
			});
			
	}
	
	/*
	 * 执行审核
	 */
	function doCheck(obj){
			
		var reviewStatues;
		var ids ;
		
		var items = $('#goodscheck').datagrid('getSelections');
		
		if(items.length < 1){
			$.messager.alert('温馨提醒','请选中操作的数据');
			return ;
		}
		ids = items[0].ids;
		reviewStatues = items[0].reviewStatues;
		
		//审核通过或拒绝
		if(obj.id == "doCheck"){
			if(reviewStatues != 3){
				$.messager.alert('温馨提醒','您选中的不是一条待申请审核的数据，请重新选择其他待审核数据','question')
				return ;
			}
			$('#checkDialog').dialog({
				draggable : false,			
				closed : false,
				modal : true,
				title : "商品审核",
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
					var param = $.param({'ids':ids}) + '&' + $('#checkForm').serialize();
						$.ajax({
							url:'/goodsCheck/updateGoodsCheckStatus',
							type:'POST',
							data: param,
							success:function(data){
								if(data){
									$('#checkDialog').dialog('close');
									$('#checkForm').form('reset');
									successShow();
									$('#goodscheck').datagrid('reload');
								}
								else
								{
									errorShow();		
								}
							}	
						});
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#checkDialog').dialog('close');
						$('#checkForm').form('reset');
					}
				} ]
			});
		}
	}
	
	
	
	/*
	 *数据导出
	 */ 
	 function goodsCheckExport(){
	     window.location.href='goodsCheck/export?model=jxls/goodsCheck.xls';      
	}
	
	/*
	 * 全局加载数据
	 */
	$(function() {

		//商品分类选择窗口
		$('#goodsClassTreeDlg').dialog({
			title: '菜单树',//窗口标题
			width: 400,//窗口宽度
			height: 300,//窗口高度
			closed: true,//窗口是是否为关闭状态, true：表示关闭
			modal: true,//模式窗口
			resizable:true
		});
	});

	/*
	 * 读取路径显示图片
	 */
	function imgFormatter(value, row) {
		var ids = row.goodsids
		var str = "";
		
		images = $.ajax({url:'/goodsCheck/reqGoodsGoodsImgPath/' + ids,type:'POST',async:false});
		str = "<img style=\"height: 75px;width: 110px;\" src=\""+ images.responseText +"\"/>";
		return str;
		
	}

	/*
	 * 分类状态
	 */
	function statesFormatter(value) {
		if (value == "0") {
			return '<span style="color:black">待申请审核</span>';
		} 
		if (value == "1"){
			return '<span style="color:green">审核通过</span>';
		} 
		if (value == '2'){
			return '<span style="color:red">审核拒绝</span>';
		}else{
			return '<span style="color:blue">待审核</span>';
		}
	}
	
	/*
	 * 商品类型
	 */
	function typeFormatter(value){
		if(value == "0"){
			return '<span>普通商品</span>';
		}else{
			return '<span style="color:#FF00FF">活动商品</span>';
		}
	}
	
	/*
	 *清除查询条件
	 */
	 function doGoodsCheckClear(){
		$('#searchCheckForm').form('reset');
	}
	
	/**
	 * 新增上传图片回显
	 */
	function readGoodsPicture(_obj) {
		//easyui-filebox封装input标签
		//var fileId = $("input[type='file']").attr('id');
		//console.info(fileId);
		//if(typeof(fileId) != "undefined"){
		
			// 检查是否为图像类型
			var simpleFile = document.getElementById("img").files[0];
			//console.info(simpleFile);
			if (!/image\/\w+/.test(simpleFile.type)) {
				$.messager.alert('信息提示', '请确保文件类型为图像类型', 'info')
				return false;
			}
			var reader = new FileReader();
			// 将文件以二进制文件读入页面中
			reader.readAsBinaryString(simpleFile);
			reader.onload = function(f) {
				var result = document.getElementById("showGoodsPic");
				var src = "data:" + simpleFile.type + ";base64,"
				+ window.btoa(this.result);
				result.innerHTML = '<img id="readGoodsPic" style="height: 130px;width: 180px;" src ="' + src + '"/>';
			}
			//document.getElementById("showpic").style.display="";
		//}
	}
	
	/**
	 * 修改上传图片回显
	 */
	function readUGoodsPicture(_obj) {
		//easyui-filebox封装input标签
		var fileId = $("#goodsCheckUpdateDl input[type='file']").attr('id');
		console.info(fileId);
		if(typeof(fileId) != "undefined"){
			
			// 检查是否为图像类型
			var simpleFile = document.getElementById(fileId).files[0];
			//console.info(simpleFile);
			if (!/image\/\w+/.test(simpleFile.type)) {
				$.messager.alert('信息提示', '请确保文件类型为图像类型', 'info')
				return false;
			}
			var reader = new FileReader();
			// 将文件以二进制文件读入页面中
			reader.readAsBinaryString(simpleFile);
			reader.onload = function(f) {
				var result = document.getElementById("showUGoodsPic");
				var src = "data:" + simpleFile.type + ";base64,"
				+ window.btoa(this.result);
				result.innerHTML = '<img id="readUGoodsPic" style="height: 130px;width: 180px;" src ="' + src + '"/>';
			}
			//document.getElementById("showpic").style.display="";
		}
	}
	
	
	// 商品审核详情查看
	function showGoodsCheck() {
	//debugger;
	var row = $('#goodscheck').datagrid('getSelected');
	var ids = row.goodsids;
	$(document).ready(function(){  
		var ue2 = UE.getEditor('containerQuery',{
			initialFrameWidth:1000,  //初始化编辑器宽度,默认1000  
	        initialFrameHeight:140  //初始化编辑器高度,默认320
		});
	    
	    ue2.ready(function() {//编辑器初始化完成再赋值  
	    ue2.setContent(row.content);  //赋值给UEditor 
	  	ue2.setDisabled('fullscreen');
	      
	  	images = $.ajax({url:'/goodsCheck/reqGoodsGoodsImgPath/' + ids,type:'POST',async:false});
	  	console.info(images);
	    var tp=$("#checkImgUrl").attr("src",images.responseText); 
	    });  
	    
	});	
	if (row <= 0) {
		$.messager.alert('提示', '请选择查看的条目!');
	} else {
		$('#goodsCheck-show').dialog('open').dialog('setTitle', '商品详情');
		$('#goodsCheck-show-data').form('load', row);
	}
}
	
	
	/**
	 * 打开商品分类窗口
	 */
	function ClassTree(obj_){
		//异步请求数据
		$('#classChooseBtns a').attr('name',obj_.id);
        $('#goodsClassTreeDlg').dialog('open');
		$('#goodsClassTree').tree({
			url:'/goods/findGoodsClass'
		});
	}
	
	/**
	 * 商品分类选择
	 */
	function classIdChoose(name){
		var row = $("#goodsClassTree").tree('getSelected');
		if(row){	
			if(name == "goodscheck_like"){
				$("#goodsclassname").textbox('setValue',row.text);
			}else if(name == "goodscheck_add"){
				$("#goodsClassIds").textbox('setValue',row.id);
			}else if(name == "goodscheck_upd"){
				$("#update-goodsClassIds").textbox('setValue',row.id);
			}
			$("#goodsClassTreeDlg").dialog("close");
			$('#goodsClassTree').tree('clearSelections');
		}else{
			$.messager.alert('系统消息','请选择一项进行操作!','info');
		}
	}
	
	/**
	 * 打开商户列表
	 */
	function venderFnmChoose(obj_){
		$('#vendersChooseBtns a').attr('name',obj_.id);
		$('#vendersChooseDialog').dialog('open');
	
	}
	
	/**
	 * 商户查询
	 */
	function venderDoSearch() {
		$('#venderDataTable').datagrid('load', {
			vendorId : $('#vender-vendorId').val(),
			vendorSnm : $('#vender-vendorSnm').val()
		});
	}

	/**
	 * 商户搜索条件清空
	 */
	function venderClearAll() {
		$("#venderSearchForm").form("reset");
		$('#venderDataTable').datagrid("load", {});
	}

	/**
	 * 商户名称选择
	 */
	function vendersChoose(name){
		var row = $("#venderDataTable").datagrid("getSelected");
		if(row){
			if(name == "like-choose"){
				$("#goodsCheck-vendorFnm").textbox('setValue',row.vendorFnm);
			}else if(name == "add-choose"){
				$("#add-vendorFnm").textbox('setValue',row.vendorId);
			}else if(name == "upd-choose"){
				$("#update-vendorFnm").textbox('setValue',row.vendorId);
			}
			$("#vendersChooseDialog").dialog("close");
			$('#venderDataTable').datagrid('clearSelections');
		}else{
			$.messager.alert('系统消息','请选择一项进行操作!','info');
		}
	}
	