define(function(require) {
	var Core = require('core/core');
	var Page = require('core/page');
	var Config = require('config');
	
	var QueryModelAdd = require('./querymodel.add');
	var QueryModelEdit = require('./querymodel.edit');
	var QueryModelRemove = require('./querymodel.remove');
	var QueryModelView = require('./querymodel.view');
    
	// 角色信息列表
	var QueryModel = Page.extend(function() {
		this.injecte([
	        new QueryModelAdd('querymodel_add'), 
	        new QueryModelEdit('querymodel_edit'), 
	        new QueryModelRemove('querymodel_remove'),
	        new QueryModelView('querymodel_view')
	    ]);
		
		// @override
		this.load = function(panel) {
			panel.find('table').cdatagrid({
				// 必须要加此项!!
				controller: this
				
			});
		};
	});
	
	return QueryModel;
});