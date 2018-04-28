angular.module(window.tsc.constants.HOMEPAGE_APP)
.component('topicCom', {
	bindings : {
		topicFromServer : '<'
	},
	templateUrl : '/internal/UserContents/topic/topic.html',
	controller : function(){
		var ctrl = this;
		ctrl.topic = ctrl.topicFromServer.data;
		ctrl.isDetail = true;
	}
});