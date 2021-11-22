var service = {},  socket = {
      client: null,
      stomp: null
    };

var tag=angular.module('tag',["ngRoute","settings","report"]);
tag.config(function($locationProvider) {
	$locationProvider.hashPrefix('');
});

tag.config(function($routeProvider,$qProvider) {
	$routeProvider
	.when("/", {templateUrl : "app/view/start.html"})
	.when("/settings",{
			controller : "Comsettingscontroller",
			templateUrl : "app/settings/view/settings.html"
		 }
		)
	.when("/report",{
			controller : "ReportController",
			templateUrl : "app/report/view/report.html"
		 }
		)
		$qProvider.errorOnUnhandledRejections(false);
	});
tag.run(function($rootScope){
	$rootScope.allowStart=true;
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/reportSocket";
    service.REPORT_TOPIC = "/topic/feed";
    service.REPORT_BROKER = "/api/v1/tagging/report";
    socket.client = new SockJS(service.SOCKET_URL);
    socket.stomp = Stomp.over(socket.client);
    socket.stomp.connect({},  function (frame) {
        //setConnected(true);
        //console.log('Connected: ' + frame);
        socket.stomp.subscribe(service.REPORT_TOPIC, function (tagdata) {
       	tdata=JSON.parse(tagdata.body);
	    if(tdata!=undefined && tdata[0]!=undefined){
		epc=tdata[0].epc;
		userMemory=tdata[0].user_Memory;
		acessPassword=tdata[0].access_Password;
		killPass=tdata[0].kill_Password;
		status=tdata[0].status;
		rssi=tdata[0].rssi;
		count=tdata[0].count;
		orderId=tdata[0].orderId;
		tidData=tdata[0].tidData;
		date=tdata[0].date;
		memory_rw_error=tdata[0].memory_rw_error;
		table="<tr>";
		html="<td style='font-size:12px;padding-right:0px;'>"+epc+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+userMemory+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+acessPassword+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+killPass+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+status+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+rssi+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+count+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+tidData+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+date+"</td>";
		html+="<td style='font-size:12px;padding-right:0px;'>"+memory_rw_error+"</td>";
		table+=html+"</tr>";
		$("#tag").append(table);
		}
	    });
    });
  
});
tag.controller('TagOperationController',function($scope,$rootScope,$http,$interval){
	
	
	$scope.greeting="Start with finding an orderId in the tag processing system";
	$scope.newOrder=false;
	$scope.errorData={};
	$scope.errorAction={};
	$scope.errorAction["ERROR_0033"]={"instruction": "Press action button to proceed.","action": "AAAA240390003030"};
	$scope.errorAction["ERROR_0133"]={"instruction": "Press action button to proceed.","action": "AAAA240390003030"};
	$scope.errorAction["ERROR_0233"]={"instruction": "Press action button to proceed.","action": "AAAA240390003030"};
	$scope.errorAction["ERROR_0333"]={"instruction": "Check the conver belt.","action": ""};
	$scope.errorAction["ERROR_0433"]={"instruction": "No Action to be taken","action": ""};
	$scope.errorAction["ERROR_0533"]={"instruction": "Press send action button to proceed","action": "AAAA240390003030"};
	$scope.errorAction["ERROR_F533"]={"instruction": "No Action to be taken","action": ""};
	$scope.errorAction["ERROR_020A"]={"instruction": "No Action to be taken","action": ""};
	$scope.errorAction["ERROR_030A"]={"instruction": "No Action to be taken","action": ""};
	$scope.hideForm=false;
	$scope.messageData={
			'showMessage' :false,
			'alert' :'default',
			'message' : ''
		 }
	 //$rootScope.allowStart=true;
	 if (typeof(EventSource) !== "undefined") {
        
         var source = new EventSource('/api/v1/tagging/status');

         source.onmessage = function (event) {
             $rootScope.allowStart=JSON.parse(event.data);
             $rootScope.$apply();
         };

     }
	 $scope.takeAction=function(){
		$http.post("/api/v1/tagging/device/error/resume",JSON.stringify($scope.errorData)).then(function successCallback(response){
			 received=response.data;
			if(received.message==='Done'){
				$('#errorModal').modal('hide');
			}
		 },function errorCallback(response){
			console.log(response.data);
		 });
	 }; 

	 $scope.viewOnlineReport=function(){
		 tagProcessingInput={};
		 tagProcessingInput["orderId"]=$scope.orderForm.orderid.$viewValue;
		 tagProcessingInput["barcode"]=$scope.orderForm.barcode.$viewValue;
		 $scope.title="Job Status for order id:"+tagProcessingInput["orderId"]+", barcode :"+tagProcessingInput["barcode"];
		 socket.stomp.send(service.REPORT_BROKER , {}, JSON.stringify(tagProcessingInput)); 
	};
	 $scope.startJob=function(){
		 tagProcessingInput={};
		 tagProcessingInput["orderId"]=$scope.orderForm.orderid.$viewValue;
		 tagProcessingInput["barcode"]=$scope.orderForm.barcode.$viewValue;
		 $scope.title="Job Status for order id:"+tagProcessingInput["orderId"]+", barcode :"+tagProcessingInput["barcode"];
		 socket.stomp.send(service.REPORT_BROKER , {}, JSON.stringify(tagProcessingInput)); 
		 console.log(JSON.stringify(tagProcessingInput));
		 $http.post("/api/v1/tagging/start",JSON.stringify(tagProcessingInput)).then(function successCallback(response){
			 console.log(response.data);
			 message=response.data.message;
			 if(!message.startsWith("Started")){
				 $scope.messageData={
							'showMessage' :true,
							'alert' :'warning',
							'message' : response.data.message
						 }
			 }else{
				 $scope.messageData={
							'showMessage' :true,
							'alert' :'default',
							'message' : response.data.message
						 }
				 $scope.hideForm=true;
				 $scope.greeting="Started tagging for orderId:("+$scope.orderForm.orderid.$viewValue+") barcode:("+$scope.orderForm.barcode.$viewValue+")";
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
	 };
	var errorMsg=$interval(function(){
		$http.get('/api/v1/tagging/device/error')
		.then(function successCallback(response){
			datavalue=response.data;
			if(datavalue!=""){
			$scope.errorData=datavalue;
			$('#errorModal').modal('show');
			console.log($scope.errorData);
			}
	}, function errorCallback(response){
		console.log("Error in Calling error Handler");
	});
	},
	1000);
}).directive('alertMessage',function(){
	return {
		templateUrl: 'app/directives-view/alert-message.html'
	}
});