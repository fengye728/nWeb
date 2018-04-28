/**
 * Created by Maple on 2017/4/13.
 */
angular.module(window.tsc.constants.DASHBOARD_APP).component('userManagement', {
    templateUrl : '/internal/AdminContents/userManagement/user-management.html',
    controller : function($http, NgTableParams, $q, toastr, serverService) {
		var ctrl = this;
        // Init ctrl
        ctrl.DATE_FORMAT = window.tsc.constants.DATE_FORMAT;
        ctrl.isFormValid;
        ctrl.selectedUserIdList = [];
        ctrl.clickedUser = null;
        
        ctrl.userRoleCategoryList = ["ROLE_ADMIN", "ROLE_USER"];
        
        getAllUsers();

		ctrl.tableParams = getTableParams();

		function getTableParams() {
			var initialParams = {
				count: 25,
				sorting: {createdDt: "desc"}
			};
			var initialSettings = {
				counts: [25, 50, 100],
				paginationMaxBlocks: 13,
				paginationMinBlocks: 2,
				dataset: ctrl.users
			};
			return new NgTableParams(initialParams, initialSettings);
		}
		
// -------------- Add function And Delete function ------------------------
		ctrl.clickDetail = function(row) {
			ctrl.editableMode = window.tsc.constants.USER_INFO_MODE.USER_MODE;
			ctrl.clickedUser = row;
			$('#userDetailModal').modal('show');
		}
		
		ctrl.checkSelectStatus = function(event,row) {
			if(event.currentTarget.checked) {
				// add its id to list
				var index = _.indexOf(ctrl.selectedUserIdList, row.id);
				if(index < 0) {
					ctrl.selectedUserIdList.push(row.id);
				}
			}
			else {
				// delete its id from list
				_.remove(ctrl.selectedUserIdList, function(rowId){
					return rowId == row.id;
				});

			}
		};
		
		/**
		 * Delete users in selectedUserIdList
		 */
		ctrl.deleteUser = function() {
			if(ctrl.selectedUserIdList.length > 0){
				// Delete users from server
                var promise = deleteUsersFromServer(ctrl.selectedUserIdList);

                promise.then(function(resolve){
                    // delete user success
                    
                    // remove these users in ng-table
                	ctrl.delRowsByIdList(ctrl.selectedUserIdList);
                    // initialize the list
        			ctrl.selectedUserIdList = [];
        			
        			toastr.success("Delete user success！！", "Server:");
                }, function(reject){
                    toastr.error("Delete user failed！", "Server Error:");
                });
			}
		};
		
		/**
		 * Check if checkbox of all rows is checked.
		 */
		ctrl.isSelected = function(row) {
			return _.indexOf(ctrl.selectedUserIdList, row.id) >= 0;
		}
		
// ------------------ ng-table functions ----------------------
		ctrl.delRowsByIdList = function (idList) {
            // remove these users in ng-table
            _.remove(ctrl.tableParams.settings().dataset, function (item) {
                return  _.indexOf(idList, item.id) >= 0;
            });
            
            // reload ng-table
			ctrl.tableParams.reload().then(function (data) {
				if (data.length === 0 && ctrl.tableParams.total() > 0) {
					ctrl.tableParams.page(ctrl.tableParams.page() - 1);
					ctrl.tableParams.reload();
				}
			});			
		};
		
// ------------------ Model functions  ----------------------------------
		ctrl.openAddUserWithDetail = function(){
			// init
            ctrl.clickedUser = {};
            ctrl.editableMode = window.tsc.constants.USER_INFO_MODE.ADMIN_MODE;
			// Open modal to display user detail
            $('#userDetailModal').modal('show');
		};
		
		/**
		 * Save the user information and send to server for modal
		 */ 
		ctrl.saveUserWithDetail = function(userWithDetail) {

            // Check if the action is adding user or update user
            if(userWithDetail.id == null){
                // Add user
                var promise = addUserToServer(userWithDetail);
                promise.then(function(resolve){
                    // success
                	
                    // Add the row into ngtable
                	ctrl.tableParams.settings().dataset.push(resolve);
                    ctrl.originalData.push(angular.copy(resolve));
                    // reload ng-table
        			ctrl.tableParams.reload().then(function (data) {
        				if (data.length === 0 && ctrl.tableParams.total() > 0) {
        					ctrl.tableParams.page(ctrl.tableParams.page() - 1);
        					ctrl.tableParams.reload();
        				}
        			});
        			
                    $('#userDetailModal').modal('hide');
                    ctrl.clickedUser = null;
                }, function(reject){
                   toastr.error("Add user failed！", "Server Error");
                });
            }
            else {
                // Get the original data
                var index = _.findIndex(ctrl.originalData, function(r){
                    return r.id === userWithDetail.id;
                });

                // Update the user in server
                $http.post('/user/updateUser', userWithDetail).success(function(response){
                    $('#userDetailModal').modal('hide');
                    // Update the row in ngtable
                    angular.extend(ctrl.tableParams.settings().dataset[index], userWithDetail)
                    angular.extend(ctrl.originalData[index], userWithDetail);
                    
                }).error(function(response){
                    toastr.error('Update failed！', 'Server Error:');
                });
            }
		};

// ------------------------- Functions Interact with server -------------------------------
		/**
		 * Add user to server
		 * 
		 * @param user
		 * @returns promise that include user with id if success, otherwise null.
		 */
		function addUserToServer(user) {
			var defered = $q.defer();
			
			$http.post('/user/add',user).success(function(response){
                defered.resolve(response);
			}).error(function(response){
				defered.reject(null);
			});
            return defered.promise;
		};

		/**
		 * Get all user base information without detail.
		 * 
		 * @returns
		 */
		function getAllUsers() {
			$http.get('/user/getAll').success(function (response) {
				ctrl.users = response;
				ctrl.tableParams.settings({
					dataset: ctrl.users
				});
				ctrl.originalData = angular.copy(ctrl.users);
			}).error(function (response) {
				toastr.error("Get users failed!");
			});
		}

        /**
         * Delete users in rowIdList.
         *
         * @param rowIdList
         * @returns {Promise} with true if success, otherwise false.
         */
		function deleteUsersFromServer(rowIdList){
			var defered = $q.defer();
			
			$http.post('/user/removeUserList', rowIdList).success(function(response){
               defered.resolve(true);
            }).error(function(response){
                defered.reject(false);
            });
            return defered.promise;
		}
	}
});