var tag=angular.module('tag',["ngRoute","settings"]);
tag.config(function($locationProvider) {
$locationProvider.hashPrefix('');
});
tag.config(function($routeProvider) {
	$routeProvider
	.when("/", {templateUrl : "app/view/start.html"})
	.when("/Apple", {templateUrl : "Apple.htm"})
	.when("/Mango", {templateUrl : "Mango.htm"})
	.when("/settings",{
			controller : "Comsettingscontroller",
			templateUrl : "app/settings/view/settings.html"
		 }
		)
	});
tag.controller('TagOperationController',function($scope){
	$scope.greeting="Start with finding an orderId in the tag processing system";
	$scope.newOrder=false;
});