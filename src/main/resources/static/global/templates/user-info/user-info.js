angular.module('userInfo',['serverService'])
.directive('userInfo',function(){

    return {
        restrict : 'EA',
        templateUrl : '../../global/templates/user-info/user-info.html',
        scope : {
            mode : '=mode',
            userWithDetail : '=userWithDetail',
            isFormValid : '=isFormValid',	// For share the form status with outside
            userRoleCategory : '='			// For display user role
        },
        link : function(scope, ele, attrs) {
            // check the mode
            if(scope.mode == window.tsc.constants.USER_INFO_MODE.USER_MODE){
                // The user editable
            	scope.isBaseEditable = false;
            	scope.isDetailEditable = true;
                
            }
            else if(scope.mode == window.tsc.constants.USER_INFO_MODE.ADMIN_MODE){
                // The Admin editable
            	scope.isBaseEditable = true;
            	scope.isDetailEditable = true;
            }
            else if(scope.mode ==  window.tsc.constants.USER_INFO_MODE.VISTOR_MODE) {
                // The Vistor editable mode
            	scope.isBaseEditable = false;
            	scope.isDetailEditable = false;
            }
            
            // Watch the form status
            scope.$watch('userDetailForm.$invalid', function(value){
            	if(value == scope.isFormValid)
            		return ;
            	scope.isFormValid = value;
            });
        }
    };

});