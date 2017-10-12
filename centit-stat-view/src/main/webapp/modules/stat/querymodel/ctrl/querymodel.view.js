define(function(require) {
	var Core = require('core/core');
	var Page = require('core/page');
	var Config = require('config');
	var QueryModelAdd = require('./querymodel.add');

	// 编辑角色信息
	var QueryModelView = QueryModelAdd.extend(function() {
		var _self = this;
		var copypanel=null;
		this.submit = function(table, data) {
			if(null==data.databaseCode || ''==data.databaseCode)
			{$.messager.alert('提醒','请先配置数据源!','error');
			return;}
			else{
				window.open(Config.ContextPath + 'statpage.html?queryName='+data.modelName);
			}
		};

	});

	return QueryModelView;
});
