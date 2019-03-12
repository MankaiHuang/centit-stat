define(function(require) {
	var Core = require('core/core');
	var Page = require('core/page');
	var Config = require('config');
	
	// 删除数据字典
	var QueryModelRemove = Page.extend(function() {

		// @override
		this.submit = function(table, data) {
			Core.ajax(Config.ContextPath+'stat/stat/querymodel/'+data.modelName, {
            	type: 'json',
                method:'delete'
			}).then(function(){
				table.datagrid('reload');
			});
		};
	});
	
	return QueryModelRemove;
});
