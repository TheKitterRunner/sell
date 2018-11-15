<!DOCTYPE html>
<html dir="ltr" lang="zh">
<!-- head -->
<#include "../common/header.ftl">
<body>

<div id="main-wrapper">
    <!-- Topbar header - style you can find in pages.scss -->
    <#include "../common/topbar.ftl">

    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <#include "../common/sidebar.ftl">

    <!-- Page wrapper  -->
    <div class="page-wrapper">

        <!-- 页面功能导航 -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-5 align-self-center">
                    <h4 class="page-title">订单管理</h4>
                    <div class="d-flex align-items-center"></div>
                </div>
                <div class="col-7 align-self-center">
                    <div class="d-flex no-block justify-content-end align-items-center">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item">首页</li>
                                <li class="breadcrumb-item active" aria-current="page">
                                    <a href="${basePath}/seller/order/list">订单</a>
                                </li>
                                <li class="breadcrumb-item active" aria-current="page">
                                    详情
                                </li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">订单详情</h4>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-6">
                                        <!-- 订单 -->
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>订单编号</th>
                                                <th>订单金额</th>
                                            </tr>
                                            <tr>
                                                <td>${orderDTO.orderId}</td>
                                                <td>${orderDTO.orderAmount}</td>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="col-md-12">
                                        <!-- 订单详情 -->
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>商品编号</th>
                                                <th>商品名称</th>
                                                <th>价格</th>
                                                <th>数量</th>
                                                <th>总金额</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <#list orderDTO.orderDetailList as orderDetail>
                                            <tr>
                                                <td>${orderDetail.productId}</td>
                                                <td>${orderDetail.productName}</td>
                                                <td>${orderDetail.productPrice}</td>
                                                <td>${orderDetail.productQuantity}</td>
                                                <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                                            </tr>
                                            </#list>
                                            </tbody>
                                        </table>
                                    </div>
                                    <#if orderDTO.orderStatus == 0>
                                    <div class="col-md-12">
                                        <div class="btn-group" role="group">
                                            <a href="${basePath}/seller/order/finish?orderId=${orderDTO.orderId}" class="btn btn-success">完结订单</a>
                                            <a href="${basePath}/seller/order/cancel?orderId=${orderDTO.orderId}" class="btn btn-danger">取消订单</a>
                                        </div>
                                    </div>
                                    </#if>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- customizer Panel -->
<#include "../common/layout.ftl">

<!-- All Jquery -->
<#include "../common/js.ftl">

</body>
</html>