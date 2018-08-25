/**
 * Created by Maple on 2017/4/13.
 */
angular.module(window.tsc.constants.DASHBOARD_APP).component('downloadPage', {
    templateUrl : '/internal/AdminContents/downloadPage/download-page.html',
    controller : function($http, NgTableParams, $q, toastr) {
		var ctrl = this;
		//ctrl.server = '52.205.81.17'
		ctrl.server = 'localhost';
		ctrl.serverPort = 8080
		
		ctrl.url = ctrl.server + ":" + ctrl.serverPort
		
	}
});