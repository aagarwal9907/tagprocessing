var reports=angular.module('report',[]);
reports.controller('ReportController',function($scope,$rootScope,$http){
	
	 var service = {},  socket = {
      client: null,
      stomp: null
    };
    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/reportSocket";
    service.REPORT_TOPIC = "/topic/feed";
    service.REPORT_BROKER = "/api/v1/tagging/report";

	$scope.initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({},  function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        socket.stomp.subscribe(service.REPORT_TOPIC, function (tagdata) {
            renderReport(JSON.parse(tagdata.body).content);
        });
    });
    };

	$scope.generate=function(tagReport){
		 tagProcessingInput={};
		 tagProcessingInput["orderId"]=tagReport.orderid;
		 tagProcessingInput["barcode"]=tagReport.barcode;
		 console.log(JSON.stringify(tagProcessingInput));
		  $http.post("/api/v1/tagging/report/generate",JSON.stringify(tagProcessingInput)).then(function successCallback(response){
			 console.log(response.data);
			 message=response.data.errorCode;
			 if(!message.startsWith("SUCCESS")){
				 $scope.messageData={
							'showMessage' :true,
							'alert' :'error',
							'message' : response.data.message
						 }
			 }else{
				 $scope.messageData={
							'showMessage' :true,
							'alert' :'default',
							'message' : response.data.message
						 }
				 $scope.hideForm=true;
			 }
			console.log($scope.messageData);
			$scope.apply();
			 
		 },function errorCallback(response){
			 $scope.messageData={
						'showMessage' :true,
						'alert' :'error',
						'message' : response.data.message
					 }
			console.log($scope.messageData);
			$scope.apply();
		 });
	// socket.stomp.send(service.REPORT_BROKER , {}, JSON.stringify(tagProcessingInput));
	 };
	
});