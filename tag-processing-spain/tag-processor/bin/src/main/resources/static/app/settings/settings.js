var settings=angular.module('settings',[]);
settings.controller('Comsettingscontroller',function($scope){
	$scope.comDevice={};
	comFn=[];
	var i=1;
	while(i<=30){
		 comFn[i-1]={'value': 'F'+i};
		 i++;
		}
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
		$scope.messageData={
			'showMessage' :true,
			'alert' :'default',
			'message' : 'Value Retrieved !!!'
		 }
	}

})
.directive('alertMessage',function(){
	return {
		templateUrl: 'app/settings/directives-view/alert-message.html'
	}
});