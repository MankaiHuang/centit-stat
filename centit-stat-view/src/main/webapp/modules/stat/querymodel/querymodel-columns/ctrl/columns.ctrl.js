define(function(require) {
    var Page = require('core/page');

    var ColumnAdd=require('./querymodel.column.add');
    var ColumnRemove=require('./querymodel.column.remove');

    return Page.extend(function() {
        this.tableName = 'table_querycolumns';

        this.injecte([
            new ColumnAdd('querycolumn_add'),
            new ColumnRemove('querycolumn_remove')
        ]);

        this.object = {
            isShow: 'T',
            showType: 'D',
            optType: '0',
            colType: 'S',
            drawChart: 'F'
        };

        this.load = function(panel, data) {
            if (this.table) return;

            var _self = this;
            var table = this.table = panel.find('#' + this.tableName);
            if (!table.length) return;

            table.cdatagrid({
                controller:_self
            }).datagrid('loadData', data);
        };

        this.getValue = function() {
            var table = this.table;
            if (!table || !table.length) return;

            var isValid = table.cdatagrid('endEdit');

            if (isValid === false) {
                return false;
            }

            return table.datagrid('getRows');
        }
    });
});