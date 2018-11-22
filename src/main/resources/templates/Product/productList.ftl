<!DOCTYPE html>
<html lang="zh">
<#include "../common/header.ftl">
<body>

<div id="main-wrapper">
    <#include "../common/sidebar.ftl">

    <#include "../common/topbar.ftl">

    <!-- 页面主体内容 -->
    <!-- Page wrapper  -->
    <div class="page-wrapper">

        <!-- 页面功能导航 -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-5 align-self-center">
                    <h4 class="page-title">商品管理</h4>
                    <div class="d-flex align-items-center"></div>
                </div>
                <div class="col-7 align-self-center">
                    <div class="d-flex no-block justify-content-end align-items-center">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item">首页</li>
                                <li class="breadcrumb-item active" aria-current="page">
                                    订单
                                </li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页面主体信息 -->
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">商品列表</h4>
                            <div class="table-responsive">
                                <table id="zero_config" class="table table-striped table-bordered" >
                                    <thead>
                                    <tr>
                                        <th class="text-center">商品ID</th>
                                        <th class="text-center">商品名</th>
                                        <th class="text-center">价格</th>
                                        <th class="text-center">库存</th>
                                        <th class="text-center">描述</th>
                                        <th class="text-center">状态</th>
                                        <th class="text-center">类目类型</th>
                                        <th class="text-center">创建时间</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list productInfoPage.content as productInfo >
                                        <tr>
                                            <td name="productId">${productInfo.productId}</td>
                                            <td name="productName">${productInfo.productName}</td>
                                            <td name="productPrice">${productInfo.productPrice}</td>
                                            <td name="productStock">${productInfo.productStock}</td>
                                            <td naem="productDescription">${productInfo.productDescription}</td>
                                            <#if productInfo.productStatus == 0 >
                                            <td name="productStatus">上架中</td>
                                            </#if>
                                            <#if productInfo.productStatus == 1 >
                                            <td name="productStatus">已下架</td>
                                            </#if>
                                            <#list productCategoryPage.content as productCategory>
                                            <#if productCategory.categoryType == productInfo.categoryType>
                                            <td name="categoryName">${productCategory.categoryName}</td>
                                            </#if>
                                            </#list>
                                            <td name="createTime">${productInfo.createTime}</td>
                                            <td>
                                                <#if productInfo.productStatus == 1>
                                                <a class="btn btn-sm btn-outline-info" href="${basePath}/sell/product/detail?productId=${productInfo.productId}">上架</a>
                                                </#if>
                                                <#if productInfo.productStatus == 0>
                                                <a class="btn btn-sm btn-outline-danger" href="${basePath}/seller/product/cancel?productId=${productInfo.productId}">下架</a>
                                                </#if>
                                                <a class="btn btn-sm btn-outline-info" href="${basePath}/sell/product/updateProductPage?productId=${productInfo.productId}">修改</a>
                                            </td>
                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>
                                <!-- 分页 -->
                                <ul class="pagination float-right">
                                        <#if productInfoPage.first>
                                        <li class="page-item disabled">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${productInfoPage.number}">
                                                上一页
                                            </a>
                                        </li>
                                        <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${productInfoPage.number}" aria-label="Previous">
                                                上一页
                                            </a>
                                        </li>
                                        </#if>
                                        <#list 1..productInfoPage.totalPages as index>
                                            <#if productInfoPage.number == (index - 1)>
                                        <li class="page-item active">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${index}">${index}</a>
                                        </li>
                                            <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${index}">${index}</a>
                                        </li>
                                            </#if>
                                        </#list>
                                        <#if productInfoPage.last>
                                        <li class="page-item disabled">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${productInfoPage.number+1}" aria-label="Next">
                                                下一页
                                            </a>
                                        </li>
                                        <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/seller/product/list?page=${productInfoPage.number+2}" aria-label="Next">
                                                下一页
                                            </a>
                                        </li>
                                        </#if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../common/layout.ftl">

<#include "../common/js.ftl">
</body>
</html>