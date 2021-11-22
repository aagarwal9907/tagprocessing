var settings=angular.module('settings',[]);
settings.controller('Comsettingscontroller',function($scope,$rootScope,$http){
	$scope.comDevice={};
	var comFn=[
			{'value': 'PT1'},
			{'value': 'PT2'},
			{'value': 'PT3'},
			{'value': 'PT4'},
			{'value': 'PT5'},
			{'value': 'PT6'},
			{'value': 'PT7'},
			{'value': 'M11'},
			{'value': 'M21'},
			{'value': 'M31'},
			{'value': 'M12'},
			{'value': 'M22'},
			{'value': 'M32'},
			{'value': 'M13'},
			{'value': 'M23'},
			{'value': 'M33'},
			{'value': 'M14'},
			{'value': 'M24'},
			{'value': 'M34'},
			{'value': 'DP1'},
			{'value': 'DP2'},
			{'value': 'DP3'}
		];
	
	$scope.comDevice.comFn=comFn;
	$scope.comDevice.selFunction=comFn[0];

	$scope.setFunction=function(){
		if($scope.comDevice.fnValue!=undefined){
		//do service call for com port value set. 
		$scope.messageData={
			'showMessage' :true,
			'alert' :'default',
			'message' : 'Command Updated !!!'
		 }
		}else{
			$scope.messageData={
			'showMessage' :true,
			'alert' :'warning',
			'message' : 'Value must be provided !!!'
		 }
		 
		}
	}

	$scope.getFunction=function(){
		var settingRequest={
			"command" : $scope.comDevice.selFunction.value,
			"commandStatus" : "READ"
		};
		$http({
		  method: 'POST',
		  url: '/api/v1/tagging/settings/read',
		  data: settingRequest
		}).then(function successCallback(response) {
		  	$scope.comDevice.fnValue=response.data.commandData; 
		  }, function errorCallback(response) {
			 console.log(response.data);
		  });
		
	};
	$scope.setFunction=function(){
		var settingRequest={
			"command" : $scope.comDevice.selFunction.value,
			"commandData" : $scope.comDevice.fnValue,
			"commandStatus" : "WRITE"
		};
		$http({
		  method: 'POST',
		  url: '/api/v1/tagging/settings/write',
		  data: settingRequest
		}).then(function successCallback(response) {
		  	$scope.comDevice.fnValue=response.data.commandData; 
		  }, function errorCallback(response) {
			 console.log(response.data);
		  });
		
	};

});/*.factory('settingsSrv',['$http',function(){
	return{readSettings: function(settingRequest){
		// Simple GET request example:
		$http({
		  method: 'POST',
		  url: '/api/v1/tagging/read',
		  data: settingRequest
		}).then(function successCallback(response) {
		  	return response.data; 
		  }, function errorCallback(response) {
			  return response.data;
		  });
	
		}
	};
}]);*/