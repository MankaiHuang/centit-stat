requirejs.config({
    baseUrl: './ui',

    paths: {
        'jquery': 'js/plugins/jquery-1.8.3.min',
        'lodash': 'js/plugins/angularjs-1.2.6/lodash',
        'angular': 'js/plugins/angularjs-1.2.6/angular.min',
        'restangular': 'js/plugins/angularjs-1.2.6/restangular.min',
        'echarts': 'js/plugins/echarts/echarts.min',
        'stat.twodimen': 'stat/stat.twodimen.ctrl',
        'stat.chart': 'stat/stat.chart.ctrl'
    },

    shim: {
        angular: {
            exports: "angular"
        },

        'stat.twodimen': {
            deps: ['restangular', 'angular', 'echarts', 'css!stat/twodimenform.css']
        },

        'stat.chart': {
            deps: ['restangular', 'angular', 'echarts', 'css!stat/twodimenform.css']
        },

        'restangular': {
            deps: ['angular']
        }
    },

    map: {
        '*' : {
            'css' : 'js/css.min',
            'text' : 'js/text'
        }
    }
})

requirejs(['jquery', 'lodash', 'stat.twodimen'], function(angular) {
    angular.bootstrap(document, ["app"]);
    angular.module('statModel', ['restangular'])
})