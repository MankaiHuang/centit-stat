define(function(require) {
    var ColumnsPage = require('../../querymodel-columns/ctrl/columns.ctrl');

    var ConditionAdd=require('./querymodel.condition.add');
    var ConditionRemove=require('./querymodel.condition.remove');



    return ColumnsPage.extend(function() {
        this.injecte([
            new ConditionAdd('querycondition_add'),
            new ConditionRemove('querycondition_remove')
        ]);

        this.object = {
            isShow: 'T',
            condDisplayStyle: 'N',
            paramType: 'S'
        };

        this.tableName = 'table_queryconditions';
    });
});