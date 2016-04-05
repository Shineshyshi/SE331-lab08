'use strict';

var productMainController = angular.module('productMainController', ['productServices']);

productMainController.controller('addProductController', ['$scope', '$http', '$location', '$rootScope', 'productService',
    function ($scope, $http, $location, $rootScope, productService) {
        $scope.product = {};
        $scope.addPerson = true;
        $scope.editPerson = false;
        $scope.addProduct = function (flowFiles) {

            //$http.post("/product", $scope.product).success(function () {
            productService.save($scope.product, function (data) {
                var productid = data.id;
                flowFiles.opts.target = 'http://localhost:8080/productImage/add';
                flowFiles.opts.testChunks = false;
                flowFiles.opts.query = {productid: productid};
                flowFiles.upload();
                $scope.$apply();
            });
            $rootScope.addSuccess = true;
            $location.path("listProduct");
        };


    }]);

productMainController.controller('listProductController', ['$scope', '$http', '$rootScope', 'productService', '$route', 'totalCalService', 'queryProductService',
    function ($scope, $http, $rootScope, productService, $route, totalCalService, queryProductService) {
        //$http.get("/product/").success(function (data) {

        var data = productService.query(function () {
            // $scope.totalNetPrice= totalCalService.getTotalNetPrice(data);
            $scope.products = data;
        });


        $scope.$on('$locationChangeStart', function (event) {
            $rootScope.addSuccess = false;
            $rootScope.editSuccess = false;
            $rootScope.deleteSuccess = false;
        });

        $scope.deleteProduct = function (id) {
            var answer = confirm("Do you want to delete the product?");
            if (answer) {
                productService.delete({id: id}, function () {
                    $rootScope.deleteSuccess = true;
                    $route.reload();
                })
            }
        }

        $scope.searchProduct = function (name) {
            queryProductService.query({name: name}, function (data) {
                $scope.products = data;
            });
        }

    }]);

productMainController.controller('editProductController', ['$scope', '$http', '$routeParams', '$location', '$rootScope', 'productService',
    function ($scope, $http, $routeParams, $location, $rootScope, productService) {
        $scope.addPerson = false;
        $scope.editPerson = true;
        var id = $routeParams.id;
        $http.get("http://localhost:8080/product/" + id).success(function (data) {
            $scope.product = data;
        });

        $scope.editProduct = function (flowFiles) {
            //$http.put("/product", $scope.product).then(function () {
            var pd = angular.copy($scope.product);
            //pd.images = [];
            productService.update({
                id: $scope.product.id,
                name: $scope.product.name,
                description: $scope.product.description,
                totalPrice: $scope.product.totalPrice
            }, function (data) {
                var productid = data.id;
                flowFiles.opts.target = 'http://localhost:8080/productImage/add';
                flowFiles.opts.testChunks = false;
                flowFiles.opts.query = {productid: productid};
                flowFiles.upload();
                $rootScope.editSuccess = true;
                $location.path("listProduct");
            });
        }

        $scope.deleteImage = function (productId, imageId) {
            var answer = confirm("Do you want to delete the image product?");
            if (answer == true) {
                $http.delete("http://localhost:8080/productImage/delete?productid=" + productId + "&imageid=" + imageId).then(function () {
                    $http.get("http://localhost:8080/product/" + productId).success(function (data) {
                        $scope.product = data;
                    });
                }, function () {
                    console.log("FAILED");
                });
            }
        }

    }]);