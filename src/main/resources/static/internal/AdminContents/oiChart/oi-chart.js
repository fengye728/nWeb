/**
 * Created by Maple on 2017/4/13.
 */
angular.module(window.tsc.constants.DASHBOARD_APP).component('oiChart', {
    templateUrl : '/internal/AdminContents/oiChart/oi-chart.html',
    controller : function($http, toastr) {
		var ctrl = this;
        // Init ctrl
        ctrl.DATE_FORMAT = window.tsc.constants.DATE_FORMAT;
        
		ctrl.optionTypeCategory = ['C', 'P'];
		ctrl.optionOIModel = {};
		
		ctrl.searchFake = function() {
			data = {};
			data.stockSymbol = 'SNAP';
			data.expiration = 190118;
			data.strike = 25;
			data.callPut = 'C';
			
			data.startEventDay = 180401;
			data.endEventDay = 180507;
			
			getOptionOIBetween(data).success(function(response){
				ctrl.drawChart(response);
			});
		}
		
// ------------------- Functions -----------------------------------------------
		ctrl.search = function() {
			console.log(ctrl.optionOIModel);
			if(ctrl.checkForm()) {
				// search oi
				getOptionOIBetween(getFormatData(ctrl.optionOIModel)).success(function(response){
					if(response.length > 0) {
						ctrl.drawChart(response);
					} else {
						toastr.info('No Open Interest');
					}
				});
			} else {
				// leak params
				toastr.error('Lack parameters!');
			}
		};
		
		
		// Check and format form 
		ctrl.checkForm = function() {
			count = 0;
			for(var i in ctrl.optionOIModel) {
				count++;
			}
			if(count < 6) {
				return false;
			} else {
				return true;
			}
		};
		
		function getFormatData(formData) {
			result = {};
			for(var col in formData) {
				result[col] = formData[col];
			}
			result.startEventDay = convertDate2Num(result.startEventDay);
			result.endEventDay = convertDate2Num(result.endEventDay);
			return result;
		}
		
		function convertDate2Num(date) {
			return date.getFullYear() % 100 * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
		}
// -------------------- Chart function ---------------------
		ctrl.drawChart = function(oiList) {
			option = oiList[0].stockSymbol + oiList[0].strike + oiList[0].callPut + oiList[0].expiration
			
			$('#oichart').highcharts({
				title: {                                                                
					text: '<b>'+ option + '</b>' + ' OI Change'                                            
				},                                                                      
				xAxis: {
					type: 'datetime',
					tickPixelInterval: 500,
					endOnTick: true,
					showLastLabel: true,
					startOnTick: true
				},                                                                      
				yAxis: {                                                                
					title: {                                                            
						text: 'Open Interest'                                                   
					}                                                            
				},                                                                      
				tooltip: {                                                              
					formatter: function() {                                             
						return Highcharts.dateFormat('%Y/%m/%d', this.x) +' : ' + '<b>'+ this.y + '</b>';
					}
				},
				legend: {                                                               
					enabled: false                                                      
				},                                                                                                                                       
				series: [{
					name: 'Open Interest',                                                
					data: (function() {
						var data = []
						for(var i = 0; i < oiList.length; i++) {
							data.push({
								x :  new Date(oiList[i].eventDay / 10000 + 2000, oiList[i].eventDay % 10000 / 100 - 1, oiList[i].eventDay % 100),
								y : oiList[i].openInterest
							});
						}
						return data;
					})()
				}]
			});                
			
		}
// ------------------------- Functions Interact with server -------------------------------

		function getOptionOIBetween(searchOption) {
			console.log(searchOption);
			return $http({
				url :'/oi/between',
				method : 'POST',
				data : searchOption
			});
		}
	}
});