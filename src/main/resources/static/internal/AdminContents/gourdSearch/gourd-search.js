/**
 * Created by Maple on 2017/4/13.
 */
angular.module(window.tsc.constants.DASHBOARD_APP).component('gourdSearch', {
    templateUrl : '/internal/AdminContents/gourdSearch/gourd-search.html',
    controller : function($http, NgTableParams, $q, toastr) {
		var ctrl = this;
        // Init ctrl
        ctrl.DATE_FORMAT = window.tsc.constants.DATE_FORMAT;
		ctrl.editableMode = window.tsc.constants.USER_INFO_MODE.USER_MODE;	// Just modify course info, do not modify t-c-s relation.

		ctrl.originalData = [];	// The data used to reset
		ctrl.tableParams = getTableParams();
		
		getAllGourds().success(function(response){
			angular.copy(response, ctrl.originalData);
			ctrl.tableParams.settings({
				dataset : response
			});
		});

		
		function getTableParams() {
			var initialParams = {
				count: 25,
			};
			var initialSettings = {
				counts: [25, 50, 100],
				paginationMaxBlocks: 13,
				paginationMinBlocks: 2
			};
			return new NgTableParams(initialParams, initialSettings);
		}
// ------------------------------------------------------------------
		
		ctrl.clickDetail = function(row) {
			url = "https://www.marketwatch.com/investing/stock/" + row.stock_symbol;
			window.open(url);
		};
		
		ctrl.openTrade = function(row) {
			// TODO open trade info
		};

// ------------------------- Functions Interact with server -------------------------------

		function getAllGourds() {
			return $http.get('/gourd/getAllGourds');
		}
	}
});