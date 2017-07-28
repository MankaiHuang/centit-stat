define(function(require) {
    var ColumnsPage = require('../../querymodel-columns/ctrl/columns.ctrl');

    var ChartAdd=require('./querymodel.chart.add');
    var ChartRemove=require('./querymodel.chart.remove');



    return ColumnsPage.extend(function() {
        this.injecte([
            new ChartAdd('chart_add'),
            new ChartRemove('chart_remove')
        ]);

        this.tableName = 'table_charts';
    });
});