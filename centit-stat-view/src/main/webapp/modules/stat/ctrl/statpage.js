define(function(require){
	var Page = require('core/page');
	var Config = require('config');
	var Stat=Page.extend(function() {
				this.injecte([
				              
		  	    ]);
		  		
		  		// @override
		  		this.load = function(panel,data) {
		  			panel.find('table').cdatagrid({
		  				// 必须要加此项!!
		  				controller: this,
		  				url:Config.ContextPath+'stat/stat/formdata/'+data.modelName+"/"
		  			}).datagrid({
		  				columns:[[
		  				        {field:'code',title:'Code',width:100},
		  				        {field:'name',title:'Name',width:100},
		  				        {field:'price',title:'Price',width:100,align:'right'}
		  				    ]]
		  			});
		  		};
	});
	return Stat;
});
