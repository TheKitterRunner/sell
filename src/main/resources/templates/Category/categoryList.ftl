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
                            <h4 class="card-title">类目类型列表</h4>
                            <div class="table-responsive">
                                <table id="zero_config" class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th class="text-center">类目ID</th>
                                        <th class="text-center">类目名称</th>
                                        <th class="text-center">类目编号</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list productCategoryPage.content as productCategory >
                                        <tr>
                                            <td>${productCategory.categoryId}</td>
                                            <td>${productCategory.categoryName}</td>
                                            <td>${productCategory.categoryType}</td>
                                            <td>
                                                <a class="btn btn-sm btn-outline-info" href="${basePath}/category/updateCategoryPage?categoryId=${productCategory.categoryId}">修改</a>
                                            </td>
                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>
                                <!-- 分页 -->
                                <ul class="pagination float-right">
                                        <#if productCategoryPage.first>
                                        <li class="page-item disabled">
                                            <a class="page-link" href="${basePath}/category/list?page=${productCategoryPage.number}">
                                                上一页
                                            </a>
                                        </li>
                                        <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/category/list?page=${productCategoryPage.number}" aria-label="Previous">
                                                上一页
                                            </a>
                                        </li>
                                        </#if>
                                        <#list 1..productCategoryPage.totalPages as index>
                                            <#if productCategoryPage.number == (index - 1)>
                                        <li class="page-item active">
                                            <a class="page-link" href="${basePath}/category/list?page=${index}">${index}</a>
                                        </li>
                                            <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/category/list?page=${index}">${index}</a>
                                        </li>
                                            </#if>
                                        </#list>
                                        <#if productCategoryPage.last>
                                        <li class="page-item disabled">
                                            <a class="page-link" href="${basePath}/category/list?page=${productCategoryPage.number+1}" aria-label="Next">
                                                下一页
                                            </a>
                                        </li>
                                        <#else>
                                        <li class="page-item">
                                            <a class="page-link" href="${basePath}/category/list?page=${productCategoryPage.number+2}" aria-label="Next">
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