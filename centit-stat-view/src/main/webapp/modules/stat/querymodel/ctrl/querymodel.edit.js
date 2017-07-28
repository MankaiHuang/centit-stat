define(function(require) {
	var Core = require('core/core');
	var Config = require('config');
	var QueryModelAdd = require('./querymodel.add');
	
	return QueryModelAdd.extend(function() {

		this.mode = 'edit';

		/**
		 * 加载数据
		 * @returns {*}
		 */
		this.loadData = function(model) {
			var vm = this;

			return Core.ajax(Config.ContextPath + 'service/stat/querymodel/' + model.modelName, {
				type: 'json',
				method: 'get'
			}).then(function(data) {
				vm.details = data;
				vm.columns = data.queryColumns;
				vm.conditions = data.queryConditions;
			});
		};
	});
});