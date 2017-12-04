/*
 * 自定义easyui-validatebox验证规则
 * value是validatebox默认传入的参数,param为validType[]内方法指定参数
 * {0}代表字符串中参数的位置
 */
$.extend($.fn.validatebox.defaults.rules, {    
	    maxLength: {    
	        validator: function(value, param){    
	            return value.length <= param[0];    
	        },    
	        message: '最大只能输入{0}位！'   
	    },
	    checkPwd: {    
	        validator: function(value,param){    
	            return value == $(param[0]).val();    
	        },    
	        message: '两次输入密码不一致！'   
	    },
	    betweenLength: {    
	        validator: function(value,param){
	        	var len = $.trim(value).length;
	            return len >= param[0] && len <= param[1];    
	        },    
	        message: '输入长度必须在{0}和{1}之间！'   
	    },
	    checkUserNames: {    
	        validator: function(value){    
	            return /^[A-Za-z\u0391-\uFFE5]+$/.test(value);    
	        },    
	        message: '请输入中文或字母！'
	    },
	    isNumber:{
	    	validator: function(value){    
	            return /^[0-9]+$/.test(value)    
	        },    
	        message: '请输入纯数字！'
	    },
	    depName:{
	    	validator: function(value){    
	            return /^[A-Za-z0-9\u0391-\uFFE5]+$/.test(value);    
	        },    
	        message: '请输入字母、中文、数字！'
	    }
	});