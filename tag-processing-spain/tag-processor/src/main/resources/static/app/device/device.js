var device=angular.module('device',[]);
device.controller('DeviceErrorController',function($scope,$rootScope,$http,$interval){
	var errorMsg=$interval(function(){
		$http.get('/api/v1/tagging/device/error')
		.then(function successCallback(response){
			$scope.errorData=response.data;
			if($rootScope.errorData!=""){
				$('#errorModal').modal('show');
				console.log($scope.errorData);
			}
			
	}, function errorCallback(response){
		console.log("Error in Calling error Handler");
	});
	},
	1000);
}).directive('errorHandling',function(){
	return {
		templateUrl: 'app/device/view/error-handling.html'
	}
});