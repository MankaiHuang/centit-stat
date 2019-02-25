
	// var model=angular.module('statModel', ['restangular']);


	//将带小数点的四舍五入只保留两位小数
	model.filter('rounding', function() {
	    return function(input) {
	      if(!isNaN(input)&&(input+"").indexOf(".")>-1)
	    	  return input.toFixed(2);
	      else
	    	  return input;
	    }
	});
	model.config(['$locationProvider',function($locationProvider) {
		  $locationProvider.html5Mode(true);
	}]);
	model.factory("TwodimenForm", ['Restangular', function(Restangular) {
		 var twodimen=Restangular.all("stat/stat/twodimenform/");
		 return twodimen;
	}]);
	model.directive('statChart', function() {
	    return {
	        restrict: 'E',
	        templateUrl: 'modules/stat/template/chart.html',
	        replace: true,
	        controller:function($scope,$element){
	        	$scope.removeChart=function(statchart){
	        		$element.empty();
	        		$element.remove();
	        		$scope.statcharts.splice($scope.statcharts.indexOf(statchart),1);
	        	}
	        	$scope.addSeries=function(){
	        		$scope.chartData.series.push({name:'',index:'0',data:[],type:'line'});
	        	}
	        	$scope.deleteSeries=function(index){
	        		$scope.chartData.series.splice(index,1);
	        	}
	        },
	        link: function (scope, element, attrs) {
	        	scope._chartcols=angular.copy(scope.chartcols);
	        	if(scope.statchart.charttype=='xy')
	        	{
	        		//scope._chartcols=angular.copy(scope.chartcols);
		        	scope.chartData={
		        		xaxis:{name:'未选择',data:['q','w','e','r'],index:'0'},
		        		series:[{name:'未选择',data:[1,1,0,2,1],type:'line',index:'0'}]
		        	};
		        	var setOption=function(xaxis,series){
		        		var legenddata=[];
		        		for(var i=0;i<series.length;i++){
		        			legenddata.push(series[i].name);
		        		}
		        	   // 指定图表的配置项和数据
		               option = {title: {text: '',x: 'center' },
		            		    tooltip:{
		            		    	show:true
		            		    },
		            		   	toolbox: {
		            		   		show : true,
		        		            feature : {
		        		                saveAsImage : {}
		        		             }
		            		   	},
		        		        legend: {data:legenddata,y:'top'},
		        		        xAxis: {
		        		        	name:xaxis.name,
		        		        	data:xaxis.data,
		        		        	axisLabel : {
		        		                show:true,
		        		                interval: 0,
		        		                rotate:-45,
		        		                height:'150px',
		        		                textStyle:{
		        		                }
		        		            }
		        		        },
		        		        yAxis: {},
		        		        series:series
		        		    };
		               return option;
		        	}
		        	var canvas=element.find('#canvas')[0];
		            //基于准备好的dom，初始化echarts实例
		        	var myChart = echarts.init(canvas);
		            myChart.setOption(setOption(scope.chartData.xaxis,scope.chartData.series));
		            scope.$watch('chartData', function(newValue, oldValue){
		            	 if(newValue==oldValue){return;}
		            	 if(newValue.xaxis==null){return;}
		            	 if(newValue.series.length==0)
		            		 newValue.series=[{name:'未选择',data:[1,1,0,2,1],type:'line'}];
		            	 var myChart = echarts.init(canvas);
		            	 myChart.setOption(setOption(newValue.xaxis,newValue.series));
		            },true);
		            scope.$watch('chartcols', function(newValue, oldValue){
		            	 if(newValue==oldValue){return;}
		            	 scope._chartcols=angular.copy(scope.chartcols);
		            	 var chartData=scope.chartData;
		            	 for(var i=0;i<chartData.series.length;i++){
		            		 chartData.series[i]=scope._chartcols[chartData.series[i].index];
		            	 }
		            	 chartData.xaxis=scope._chartcols[chartData.xaxis.index];

		            },false);
	           }else if(scope.statchart.charttype=='pie'){
	        	   scope.chartData={
	        			    legend:{name:'未选择',data:['q','w','w','w','e'],index:'0'},
			        		series:{name:'未选择',data:[1,2,1,4,8],index:'0'}
			        	};
			        	var setOption=function(legend,series){
			        		var seriesname=series.name;
			        		var seriesdata={};
			        		var piedata=[];
			        		var legenddata=[];
			        		$.each(legend.data,function(idx){

			        			var num=series.data[idx];
			        			if(num==null)
			        				num=0;
			        			var leg=legend.data[idx];
			        			if(null!=leg && '合计'!=leg && '--'!=leg)
			        				{
			        			   if(null==seriesdata[leg])
			        				   {seriesdata[leg]=0+num;}
			        			   else{
			        				   seriesdata[leg]+=num;
			        			   }
			        				}

			        		});
			        		$.each(seriesdata,function(idx){
			        			legenddata.push(idx);
			        			var obj={};
			        			obj['value']=seriesdata[idx];
			        			obj['name']=idx;
			        			piedata.push(obj);
			        		});
			        	    // 指定图表的配置项和数据
			        		option = {
			        			    title : {
			        			        text: '某站点用户访问来源',
			        			        subtext: '纯属虚构',
			        			        x:'center'
			        			    },
			        			    legend: {
			        			        orient: 'vertical',
			        			        left: 'left',
			        			        data: legenddata
			        			    },
			        			    series : [
			        			        {
			        			        	name:seriesname,
			        			            type: 'pie',
			        			            radius : '55%',
			        			            center: ['50%', '60%'],
			        			            data:piedata,
			        			            itemStyle: {
			        			                emphasis: {
			        			                    shadowBlur: 10,
			        			                    shadowOffsetX: 0,
			        			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			        			                }
			        			            }
			        			        }
			        			    ]
			        			};
			        		return option;
			        	}
			        	var canvas=element.find('#canvas')[0];
			            //基于准备好的dom，初始化echarts实例
			        	var myChart = echarts.init(canvas);
			            myChart.setOption(setOption(scope.chartData.legend,scope.chartData.series));
			            scope.$watch('chartData', function(newValue, oldValue){
			            	 if(newValue==oldValue){return;}
			            	 if(newValue.legend==null){return;}
			            	 if(newValue.series.length==0)
			            		 newValue.series=[{name:'未选择',data:[1,1,0,2,1],type:'line'}];
			            	 var myChart = echarts.init(canvas);
			            	 myChart.setOption(setOption(newValue.legend,newValue.series));
			            },true);
			            scope.$watch('chartcols', function(newValue, oldValue){
			            	 if(newValue==oldValue){return;}
			            	 scope._chartcols=angular.copy(scope.chartcols);
			            	 var chartData=scope.chartData;
			            	 chartData.series=scope._chartcols[chartData.series.index];
			            	 chartData.legend=scope._chartcols[chartData.legend.index];

			            },false);



	        	}
	        }
	    };
	});

	model.directive('chartPanel', function() {
	    return {
	        restrict: 'E',
	        templateUrl: 'modules/stat/template/chartspanel.html',
	        replace: true,
	        scope:{
	        	chartcols:'=chartcols'
	        },
	        controller:function($scope, $element){
	        	if(null==$scope.statcharts)
	        		{$scope.statcharts=[];}
	        	$scope.addXYChart=function(){
	        		$scope.statcharts.push({charttype:'xy'});
	        	}
	        	$scope.addPieChart=function(){
	        		$scope.statcharts.push({charttype:'pie'});
	        	}
	        },
	        compile:function(element,attr){
	        	////console.log(element);
	        }
	    };
	});

	model.controller('TwoDimenFormController',['$scope','TwodimenForm','$location',function($scope,TwodimenForm,$location){

			var initParam=function(){
				$scope.queryName=$location.search().queryName;
				$scope.queryformshow=false;
				$scope.queryformcreated=false;
				$scope.showchart=false;
				//默认分页
				$scope.page={totalRows:0,pageNo:1,pageSize:20,totalPage:0};
				$scope.queryParams={};
			}
			initParam();

			$scope.showChart=function(){
				$scope.showchart=!$scope.showchart;
			}

			//重置分页
			$scope.resetPage=function(){
				$scope.page.totablRows=0;
				$scope.page.pageNo=1;
				$scope.page.pageSize=$scope.page.pageSize;
				$scope.queryByParam();
			}
			//页面大小切换选项
		    $scope.pageSizeChoose=[{key:10,value:10},{key:15,value:15},{key:20,value:20},{key:30,value:30},{key:50,value:50},{key:100,value:100},{key:500,value:500}];

			//搜索查询
			$scope.queryByParam=function(){
				//console.log($scope.page);
				TwodimenForm.one($scope.queryName).customGET(null,$.extend({},$scope.queryParams,$scope.page)).then(function(result){
					if(!$scope.queryformcreated){
						$scope.conditions_create=angular.copy(result.data.conditions);
						$scope.queryformcreated=true;
					}
					$scope.data=result.data;
					$scope.tableHead=result.data.tablePanel.thead;
					$scope.tableBody=result.data.tablePanel.tbody;
					var columns=result.data.columns;
					var formData=result.data.formData;
					$scope.conditions=result.data.conditions;


					for(var i=0;i<$scope.conditions.length;i++){
						var cond=$scope.conditions[i];
						if(cond.condValue)
							$scope.queryParams[cond.condName]=cond.condValue+'';
						//console.log($scope.queryParams);
					}

					$scope.page.totalRows=result.data.totalRowsAll;
	                $scope.page.totalPage= Math.ceil($scope.page.totalRows/$scope.page.pageSize);

	                //交叉报表不显示搜索栏
	                if($scope.data.modelType!=5){
	                	$scope.queryformshow=true;
	                }
	                var columnDatas=[];
	                for(var i=0;i<columns.length;i++){
	                	if(columns[i].isShow=='T')
	                	{
		                	var data=[];
		                	for(var j=0;j<formData.length;j++){
		                		data[j]=formData[j][i];
		                	}
		                	columnDatas.push({name:columns[i].colName,data:data,type:'line',index:columnDatas.length+''});
	                	}

	                }
	                $scope.showchart=false;
	                $scope.columnDatas=columnDatas;
	                $scope.chartcols=columnDatas;
				})
			}
			$scope.queryByParam();

			//导入excel里
			$scope.exportToExcel=function(){
			   //TODO:这边不知道除了用form还有没有什么更好的办法实现后台推送一个下载到页面接受
			   var form = $("<form>");
			   form.attr('action', "/product-stat/stat/stat/twodimenform/excels");
			   form.attr('style', 'display:none');
			   form.attr('target', '');
			   form.attr('method', 'post');

			   /*------------------所有参数 --------------------------*/
			   var input=$("<input type=text />");
			   input.attr("name","modelName");
			   input.attr("value",$scope.queryName);
			   form.append(input);
			   //查询参数
			   var qms=$scope.queryName;
			   for(var key in qms){
				   var input=$("<input type=text />");
				   input.attr("name",key);
				   input.attr("value",qms[key]);
				   form.append(input);
			   }
			   //分页
			   for(var key in $scope.page){
				   if(undefined!=$scope.page[key]&&""!=$scope.page[key])
				   {var input=$("<input type=text />");
				   input.attr("name",key);
				   input.attr("value",$scope.page[key]);
				   form.append(input);}
			   }
			   /*---------------------- --------------------------*/

			   $('body').append(form);
			   form.submit();
			   form.empty();
			   form.remove();
			}


			//打印表格
			$scope.printTable=function(){
				var style="table{width:100%;font-family:verdana,arial,sans-serif;font-size:11px;color:#333;border-width:1px;border-color:#666;border-collapse:collapse;text-align:center}.tableHead{background-color:lightgrey}.tableTitle{text-align:center;font-size:x-large}table th{border-width:1px;padding:8px;border-style:solid;border-color:#666;background-color:#dedede}table td{border-width:1px;padding:8px;border-style:solid;border-color:#666;background-color:#fff}";
	            var head="<html><head><style>"+style+"</style></style></head>";
	            var tail="</body></html>"

	            var copyPrintObj=$("#normalTableBody").clone();
	            var newWindow=window.open("","_blank");
	            var images=copyPrintObj.find('img');

	            //打印对象中图表的隐藏图片设为显示用以打印
	            for(var i=0;i<images.length;i++){
	            	if("chartimg"==images[i].className&& images[i].hidden)
	            		images[i].hidden=false;
	            }

	            var docStr = head+copyPrintObj[0].innerHTML+tail;
	            newWindow.document.write(docStr);
	            newWindow.document.close();
	            newWindow.print();
	            newWindow.close();
	            return false;
			}

	  	}]);
