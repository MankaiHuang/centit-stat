define(function(require) {
    var Page = require('core/page');
    var CodeMirror = require('plugins/codemirror/lib/codemirror');
    require('plugins/codemirror/mode/sql/sql');
    var extraKeys = require('plugins/codemirror/addon/fullscreen');
    require('css!plugins/codemirror/lib/codemirror.css');
    return Page.extend(function() {
        var codeMirrorOptions = {
            mode: "text/x-sql",
            tabSize: 2,
            extraKeys: extraKeys
        };

        this.load = function(panel, data) {
            if (this.form) return;
            var vm = this;

            var form = this.form = panel.find('form');

            // 编辑模式下禁止修改编码
            if ('edit' == this.parent.mode) {
                this.frozeModelName();
            }

            // sql输入框
            var querySqlCM = this.querySqlCM = CodeMirror.fromTextArea(document.getElementById("querySqlText"), codeMirrorOptions);
            var columnSqlCM = this.columnSqlCM = CodeMirror.fromTextArea(document.getElementById("columnSqlText"), codeMirrorOptions);

            panel.find('#querySql .easyui-linkbutton').on('click', function() {
                querySqlCM.setOption("fullScreen", !querySqlCM.getOption("fullScreen"))
            });
            panel.find('#columnSql .easyui-linkbutton').on('click', function() {
                columnSqlCM.setOption("fullScreen", !columnSqlCM.getOption("fullScreen"))
            });

            // 当交叉制表时才显示 列语句输入框
            var columnSQLField = panel.find('#columnSql');
            panel.find('#databaseType').combobox({
                novalidate: true,
                onChange:function(value){
                    // 交叉表可以填写列sql
                    if ('5' == value) {
                        columnSQLField.show();
                    }
                    else {
                        columnSQLField.hide();
                        vm.columnSqlCM.setValue('');
                    }
                }
            });
            if (data.modelType != '5') {
                columnSQLField.hide();
            }
            form.form('disableValidation')
                .form('load', data);

        };

        /**
         * 禁止修改编码
         * @returns {*|Document.modelName}
         */
        this.frozeModelName = function() {
            var form = this.form,
                value = form.form('value'),
                modelName = value.modelName;
            if (modelName) {
                form.form('readonly', 'modelName');
            }
            return value.modelName;
        };

        this.getValue = function() {
            var form = this.form;

            var isValid = form.form('enableValidation').form('validate');

            // 因为使用codemirror，需要单独判断
            var querySql = this.querySqlCM.getValue(),
                columnSql = this.columnSqlCM.getValue();
            if (!querySql) {
                isValid = false;
            }

            if (isValid === false) {
                return false;
            }

            var value = form.form('value');
            // TODO 用特殊标记替换+和&到后台再替换回来。这个地方暂时这样，还要想办法解决+号被置空的问题。
            value.querySql = querySql.replace(/\+/g,"<plussign>").replace(/\&/g,"<andsign>");
            value.columnSql = columnSql;
            return value;
        };
    });

});
