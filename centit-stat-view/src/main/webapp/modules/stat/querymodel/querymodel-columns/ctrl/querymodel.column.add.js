define(function(require) {
    var Page = require('core/page');

    return Page.extend(function() {
        this.submit = function(table) {

            if (!table.cdatagrid('endEdit')) {
                return;
            }

            // 获取modelName，一旦添加数据，modelName不可改变
            this.modelName = this.modelName || this.parent.parent.detailInst.frozeModelName();

            if (!this.modelName) {
                $.messager.error('错误', '请在详情页面填写“模板编码”！');
                return;
            }

            var index = table.datagrid('getRows').length;

            table.datagrid('appendRow', $.extend({}, this.parent.object, {
                colOrder: index + 1,
                modelName: this.modelName
            }));

            table.datagrid('selectRow', index);
            table.cdatagrid('beginEdit', index, 'dataCode');
        };
    });
});