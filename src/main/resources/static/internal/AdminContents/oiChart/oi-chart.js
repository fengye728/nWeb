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
		ctrl.optionOIChange = {
				minOIChange : null,
				maxOIChange : null
		};
		ctrl.optionOIChangeResult = [];
		
// ------------------- Controller Functions -----------------------------------------------		
		ctrl.searchFake = function() {
			data = {};
			data.stockSymbol = 'SNAP';
			data.expiration = 190118;
			data.strike = 25;
			data.callPut = 'C';
			
			data.startEventDay = 180201;
			data.endEventDay = 180510;
			
			getStockQuoteBetween(data).success(function(response){
				console.log(response);
				ctrl.drawStockChart(data, response)
			});
			getOptionOIBetween(data).success(function(response){
				ctrl.drawOIChart(response);
				ctrl.drawOIChangeChart(response)
			});
		}
		

		ctrl.draw = function() {
			if(ctrl.checkOptionForm()) {
				// disable button
				$('#drawBtn')[0].disabled = true;
				$('#drawBtn')[0].textContent = 'Drawing';
				// search oi
				option = getFormatOptionData(ctrl.optionOIModel);
				getOptionOIBetween(option).success(function(response){
					if(response.length > 0) {
						// draw chart
						ctrl.drawOIChart(response);
						ctrl.drawOIChangeChart(response);
						// search stock
						// reset start and end date
						option = getFormatOptionData(ctrl.optionOIModel);
						option.startEventDay = response[0].eventDay;
						option.endEventDay = response[response.length - 1].eventDay;
						getStockQuoteBetween(option).success(function(response){
							ctrl.drawStockChart(getFormatOptionData(ctrl.optionOIModel), response);
							// enable button
							$('#drawBtn')[0].disabled = false;
							$('#drawBtn')[0].textContent = 'Draw';
						}).error(function(response){
							toastr.error(response, 'Server Error:');
							// enable button
							$('#drawBtn')[0].disabled = false;
							$('#drawBtn')[0].textContent = 'Draw';
						});
						
					} else {
						toastr.info('No Open Interest', 'Draw OI Chart');
						// enable button
						$('#drawBtn')[0].disabled = false;
						$('#drawBtn')[0].textContent = 'Draw';
					}

				}).error(function(response){
					toastr.error(response, 'Server Error:');
					
					// enable button
					$('#drawBtn')[0].disabled = false;
					$('#drawBtn')[0].textContent = 'Draw';
				});
				

			} else {
				// leak params
				toastr.error('Lack parameters!');
			}
		};
		
		ctrl.searchOIChange = function() {
			if(ctrl.checkOptionForm() && ctrl.checkOIChangeForm()) {
				// disable button
				$('#searchBtn')[0].disabled = true;
				$('#searchBtn')[0].textContent = 'Searching';
				// search oi
				getOptionOIBetween(getFormatOptionData(ctrl.optionOIModel)).success(function(response){
					if(response.length > 0) {
						// process oi change data
						var data = []
						for(var i = 1; i < response.length; i++) {
							x_date = new Date(response[i].eventDay / 10000 + 2000, response[i].eventDay % 10000 / 100 - 1, response[i].eventDay % 100);
							y_oi = response[i].openInterest - response[i - 1].openInterest;
							
							if(ctrl.optionOIChange.minOIChange != null && y_oi < ctrl.optionOIChange.minOIChange) {
								continue;
							}
							if(ctrl.optionOIChange.maxOIChange != null && y_oi > ctrl.optionOIChange.maxOIChange) {
								continue;
							}
							
							data.push({
								x : formatDate(x_date),
								y : y_oi
							});
						}
						ctrl.optionOIChangeResult = data;
						// show modal
						$('#oiChangeModal').modal('show');
						
					} else {
						toastr.info('No Open Interest', 'Search OI Change');
					}
					// enable button
					$('#searchBtn')[0].disabled = false;
					$('#searchBtn')[0].textContent = 'Search';
				}).error(function(response){
					toastr.error(response, 'Server Error:');
					// enable button
					$('#searchBtn')[0].disabled = false;
					$('#searchBtn')[0].textContent = 'Search';
				});				
				
			}
		};

		
// -------------------- Other function ---------------------
		// Check optionOIModel and toastr error message
		ctrl.checkOptionForm = function() {
			valueCount = 0;
			for(var col in ctrl.optionOIModel) {
				if(ctrl.optionOIModel[col] != null && ctrl.optionOIModel[col] !== '') {
					valueCount++;
				}
			}
			if(valueCount < 6) {
				toastr.error('Lack option parameters!');
				return false;
			} else {
				return true;
			}
		};
		
		// Check optionOIChange and toastr error message
		ctrl.checkOIChangeForm = function() {
			valueCount = 0;
			for(var col in ctrl.optionOIChange) {
				if(ctrl.optionOIChange[col] != null && ctrl.optionOIChange[col] !== '') {
					valueCount++;
				}
			}
			if(valueCount == 2) {
				if(ctrl.optionOIChange.minOIChange > ctrl.optionOIChange.maxOIChange) {
					toastr.error('Min OI change is bigger than Max OI change');
					return false;					
				} else {
					return true;
				}
			} else if(valueCount > 0) {
				return true;
			} else {
				toastr.error('Lack OI Change range parameters!');
				return false;
			}
		}
		
		ctrl.drawOIChart = function(oiList) {
			option = oiList[0].stockSymbol + ' ' + oiList[0].strike + ' ' + oiList[0].callPut + ' 20' + oiList[0].expiration;
			
			Highcharts.chart('oichart', {
				chart : {
					zoomType : 'x'
				},
				title: {                                                                
					text: '<b>'+ option + '</b>' + ' Open Interest'
				},                                                                      
				xAxis: {
					type : 'datetime',
					tickmarkPlacement: 'between'
				},                                                                      
				yAxis: {                                                                
					title: {                                                            
						text: 'Open Interest'                                                   
					}
				},
				legend: {
					enabled: false
				},
				tooltip: {                                                              
					formatter: function() {  
						return formatDate(this.x) +' : ' + '<b>'+ this.y + '</b>';
					}
				},                                                                                                                                   
				series: [{
					name: 'Open Interest',
					data: (function() {
						var data = []
						for(var i = 0; i < oiList.length; i++) {
							// add event day for satisfied Liu
							if(i == oiList.length - 1) {
								// last one
								// add date one
								record = oiList[i];
								x_date = new Date(record.eventDay / 10000 + 2000, record.eventDay % 10000 / 100 - 1, record.eventDay % 100);
								x_date.setDate( x_date.getDate() + 1);
							} else {
								// set date to next record date
								record = oiList[i + 1];
								x_date = new Date(record.eventDay / 10000 + 2000, record.eventDay % 10000 / 100 - 1, record.eventDay % 100);
							}
							data.push({
								x : x_date,
								y : oiList[i].openInterest
							});
						}
						return data;
					})()
				}]
			});                
			
		};
		
		ctrl.drawOIChangeChart = function(oiList) {
			option = oiList[0].stockSymbol + ' ' + oiList[0].strike + ' ' + oiList[0].callPut + ' 20' + oiList[0].expiration;
			
			Highcharts.chart('oiChangeChart', {
				chart : {
					zoomType : 'x',
					type: 'column'
				},
				title: {                                                                
					text: '<b>'+ option + '</b>' + ' Open Interest Change'
				},                                                                      
				xAxis: {
					type : 'datetime',
					tickmarkPlacement: 'between'
				},                                                                      
				yAxis: {
					title: {                                                            
						text: 'Open Interest Change'                                                   
					}                                                            
				},                                                                      
				tooltip: {                                                              
					formatter: function() {  
						return formatDate(this.x) +' : ' + '<b>'+ this.y + '</b>';
					}
				},
				legend: {
					enabled: false
				},                                                                                                                                       
				series: [{
					name: 'Open Interest Change',
					data: (function() {
						var data = []
						for(var i = 1; i < oiList.length; i++) {
							x_date = new Date(oiList[i].eventDay / 10000 + 2000, oiList[i].eventDay % 10000 / 100 - 1, oiList[i].eventDay % 100);
							data.push({
								x : x_date,
								y : oiList[i].openInterest - oiList[i - 1].openInterest
							});
						}
						return data;
					})(),
					zones: [{
						value: 0,
						color: '#F4606C'
					},{
						color: '#7cb5ec'
					}]
				}],
			});
		};
		
		ctrl.drawStockChart = function(option, quoteList) {
			stock = option.stockSymbol;
			
			Highcharts.chart('stockChart', {
				chart : {
					zoomType : 'x'
				},
				colors: ['#8bbc21'],
				title: {                                                                
					text: '<b>'+ stock + '</b>' + ' Stock Price'
				},                                                                      
				xAxis: {
					type : 'datetime',
					tickmarkPlacement: 'between'
				},                                                                      
				yAxis: {
					title: {                                                            
						text: 'Price'                                                   
					}                                                            
				},                                                                      
				tooltip: {                                                              
					formatter: function() {  
						return formatDate(this.x) +' : ' + '<b>'+ this.y + '</b>';
					}
				},
				legend: {
					enabled: false
				},                                                                                                                                       
				series: [{
					name: 'Stock Price',
					data: (function() {
						var data = []
						for(var i = 1; i < quoteList.length; i++) {
							x_date = new Date(quoteList[i].quoteDate / 10000, quoteList[i].quoteDate % 10000 / 100 - 1, quoteList[i].quoteDate % 100);
							data.push({
								x : x_date,
								y : quoteList[i].close
							});
						}
						return data;
					})()
				}]
			});  			
		};
// ------------------------- General Functions --------------------------------------------
		function getFormatOptionData(formData) {
			result = {};
			for(var col in formData) {
				result[col] = formData[col];
			}
			result.stockSymbol = result.stockSymbol.toUpperCase();
			result.startEventDay = convertDate2Num(result.startEventDay);
			result.endEventDay = convertDate2Num(result.endEventDay);
			return result;
		}
		
		function convertDate2Num(date) {
			return date.getFullYear() % 100 * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
		}
		
		function formatDate(date) {
			strDate = '20' + convertDate2Num(date);
			return strDate.substr(0, 4) + '/' + strDate.substr(4, 2) + '/' + strDate.substr(6);
		}
// ------------------------- Functions Interact with server -------------------------------

		function getOptionOIBetween(searchOption) {
			return $http({
				url :'/oi/between',
				method : 'POST',
				data : searchOption
			});
		}
		
		function getStockQuoteBetween(searchOption) {
			return $http({
				url: '/stock/betweenQuote',
				method : 'POST',
				data : searchOption
			});
		}
	}
});