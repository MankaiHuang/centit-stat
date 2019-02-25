define(function(require) {
	var Core = require('core/core');
	var Page = require('core/page');
	var Config = require('config');
	require('plugins/es6-promise.min');

	var QuerymodelDetails = require('../querymodel-detail/ctrl/detail.ctrl');
	var QuerymodelColumns = require('../querymodel-columns/ctrl/columns.ctrl');
	var QuerymodelConditions = require('../querymodel-conditions/ctrl/conditions.ctrl');
	// var QuerymodelCharts = require('../querymodel-charts/ctrl/charts.ctrl');

	// 新增统计模块信息
	return Page.extend(function() {

		// 1.模块详情控制器
		var detailInst = this.detailInst = new QuerymodelDetails('querymodel_details');

		// 2.展示字段
		var columnsInst = this.columnsInst = new QuerymodelColumns('querymodel_columns');


		// 3.查询参数
		var conditionsInst = this.conditionsInst = new QuerymodelConditions('querymodel_conditions');

		// 4.统计图表
		// var chartsInst = this.chartsInst = new QuerymodelCharts('querymodel_charts');

		this.mode = 'add';

		this.injecte([detailInst, columnsInst, conditionsInst]);

		/**
		 * 加载数据
		 * @returns {*}
		 */
		this.loadData = function() {
			this.details = {};
			this.columns = [];
			this.conditions = [];

			return new Promise(function(resolve) {
				resolve();
			});
		};

		// @override
		this.load = function(panel, data) {
			var vm = this;

			this.detailInst.form = null;
			this.columnsInst.table = null;
			this.conditionsInst.table = null;
			// this.chartsInst.table = null;

			$('#ajax-loader').show();

			this.loadData(data)
				.then(function() {
					vm.initTabs();
					vm.initToolbar();
					$('#ajax-loader').hide();
				})['catch'](function() {
					$('#ajax-loader').hide();
				});
		};

		/**
		 * 分析 SQL 添加columns和conditions
		 * @param sql
		 */
		this.analyzeSql = function(sql) {
			var vm = this;
			Core.ajax(Config.ContextPath + 'stat/stat/querymodel/colandcond', {
				method: 'post',
				data: {
					sql: sql
				}
			}).then(function(data) {
				vm.sql = true;

				var columns = data.columns,
					conditions = data.conditions;
				addColumns(columns);
				addConditions(conditions);
			});

			function addColumns(columns) {
				if (!columns || !columns.length) return;

				// 冻结modelName不能再改变
				var modelName = vm.detailInst.frozeModelName();

				// 生成待添加的column数据
				var datas = columns.map(function(data, index) {
					return $.extend({}, vm.columnsInst.object, {
						modelName: modelName,
						colName: data.colName,
						colOrder: index + 1
					});
				});

				// 如果页面已经初始化，通过easyui的方法添加数据
				if (vm.columnsInst.table) {
					vm.columnsInst.table.datagrid('loadData', datas)
				}
				else {
					vm.columns = datas;
				}
			}

			function addConditions(conditions) {
				if (!conditions || !conditions.length) return;

				// 冻结modelName不能再改变
				var modelName = vm.detailInst.frozeModelName();

				// 生成待添加的column数据
				var datas = conditions.map(function(data, index) {
					return $.extend({}, vm.conditionsInst.object, {
						modelName: modelName,
						condName: data.condName,
						condLabel: data.condName,
						condOrder: index + 1
					});
				});

				// 如果页面已经初始化，通过easyui的方法添加数据
				if (vm.conditionsInst.table) {
					vm.conditionsInst.table.datagrid('loadData', datas)
				}
				else {
					vm.conditions = datas;
				}
			}
		};

		/**
		 * 初始化底部工具栏
		 */
		this.initToolbar = function() {
			var vm = this,
				panel = vm.panel;

			// 可拖动
			panel.find('#bt').draggable({
				onBeforeDrag: function(e) {
					var target = $(e.target);
					target.css('bottom', 'auto')
						.css('right', 'auto')
				}
			});

			// 上一页
			panel.find('#bt_back').click(function(){
				var tabs = vm.tabset;
				var tab = tabs.tabs('getSelected');
				var index = tabs.tabs('getTabIndex',tab);
				tabs.tabs("select", index - 1);
			});

			// 下一页
			panel.find('#bt_next').click(function(){
				var tabs = vm.tabset;
				var tab=tabs.tabs('getSelected');
				var index = tabs.tabs('getTabIndex',tab);
				tabs.tabs("select", index + 1);
			});

			// 提交保存
			panel.find('#bt_sub').click(function(){

				vm.details = detailInst.getValue();

				// 在编辑页面直接点击提交按钮，table还没有初始化，直接引用数据
				var columnsData = columnsInst.getValue(),
					conditionsData = conditionsInst.getValue();

				if (columnsData !== undefined) {
					vm.columns = columnsData;
				}
				if (conditionsData !== undefined) {
					vm.conditions = conditionsData;
				}

				if(vm.details && vm.columns && vm.conditions) {
					$.messager.confirm("操作提示","您确定要保存吗？",function(data) {
						if(data) {
							var subobject = {};

							$.extend(subobject, vm.details, {
								queryColumns: vm.columns,
								queryConditions: vm.conditions,
								_method: vm.mode == 'edit' ? 'PUT' : undefined
							});

							var url = Config.ContextPath + 'stat/stat/querymodel';
							if ('edit' == vm.mode) {
								url += '/' + subobject.modelName;
							}

							Core.ajax(url, {
								type:'json',
								method:'post',
								data: subobject
							}).then(function(){
								$.messager.alert('提示', '已保存！', 'info');
								var parentTable = vm.parent.panel.find('#quermodelTable');
								parentTable.datagrid('reload');
							})
						}
					});
				}
			});
		};

		/**
		 * 初始化tabs
		 */
		this.initTabs = function () {
			var vm = this;

			this.tabset = this.panel.find('#qmtabs').tabs({

				// 当TAB页面加载完后，初始化子页面控制器
				onLoad: function() {
					var tabs = vm.tabs = $(this).tabs('tabs');
					// 1.模块详情
					if (tabs[0].is(':visible')) {
						vm.detailInst.init(tabs[0], vm.details);
					}

					// 2.展示字段
					if (tabs[1].is(':visible')) {
						vm.columnsInst.init(tabs[1], vm.columns);
					}

					// 3.查询参数
					if (tabs[2].is(':visible')) {
						vm.conditionsInst.init(tabs[2], vm.conditions);
					}
				},

				// 切换子页面时，校验当前输入页面是否有错误
				onUnselect: function(tab, index) {
					var result = false,
						tabset = $(this);
					switch(index) {
						// 1.模块详情
						case 0:
							result = vm.detailInst.getValue();
							break;
						// 2.展示字段
						case 1:
							result = vm.columnsInst.getValue();
							break;
						// 3.查询参数
						case 2:
							result = vm.conditionsInst.getValue();
							break;
						default:
					}

					if (false === result) {
						tabset.tabs('select', index);
						return false;
					}

					// 新增，第一次输入sql时有效
					// TODO 编辑或改变时根据输入名称自动匹配，现在字段不够无法做到
					if ('add' == vm.mode && !vm.sql && 0 == index) {
						// 当输入sql有改变时，提交到后台，根据sql返回columns和conditions
						vm.analyzeSql(result.querySql);
					}
				}
			});
		};
	});


});
