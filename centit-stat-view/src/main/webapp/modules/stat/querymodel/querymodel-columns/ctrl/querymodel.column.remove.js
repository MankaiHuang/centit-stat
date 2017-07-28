define(function(require) {
	var Page = require('core/page');
	
	return Page.extend(function() {
		// @override
		this.submit = function(table, row) {
			var index = table.datagrid('getRowIndex', row);
			table.datagrid('deleteRow', index);
		};
	});
});