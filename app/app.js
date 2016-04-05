'use strict';

// Declare app level module which depends on views, and components
var labApp = angular.module('labApp', [
    'ngRoute',
    'productMainController',
    'languageControllers',
    'languageServices',
    'pascalprecht.translate',
    'shoppingCartControllers',
    'flow'
])
labApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/addProduct', {
            templateUrl: 'template/editProduct.html',
            controller: 'addProductController'
        }).when('/editProduct/:id', {
            templateUrl: 'template/editProduct.html',
            controller: 'editProductController'
        }).when('/listProduct', {
            templateUrl: 'template/productList.html',
            controller: 'listProductController'
        }).when('/shoppingCart/:id', {
            templateUrl: 'template/shoppingCart.html',
            controller: 'showShoppingCartController'
        }).otherwise({redirectTo: '/listProduct'});
    }]);

labApp.config(function ($translateProvider) {
    $translateProvider.useUrlLoader('http://localhost:8080/messageBundle');
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('en');
    $translateProvider.fallbackLanguage('en');
});
labApp.config( [
    '$compileProvider',
    function( $compileProvider )
    {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|chrome-extension):/);
        $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|local|data):/);
    }
]);


labApp.config(['flowFactoryProvider', function(flowFactoryProvider) {
    flowFactoryProvider.defaults = {
        target: '',
        permanentErrors: [500,501],
        maxChunkRetries: 1,
        simultaneousUploads: 4,
        singleFile: false
    };
    flowFactoryProvider.on('catchAll', function(event){
        console.log('catchAll', arguments);
    });

}])
