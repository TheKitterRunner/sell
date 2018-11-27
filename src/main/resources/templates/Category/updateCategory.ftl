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
                    <h4 class="page-title">类目管理</h4>
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
        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <div class="container">
            <div class="row">
                <div class="span12">
                    <form action="${basePath}/seller/category/updateCategory" cla ss="form-horizontal" method="post" >
                        <div class="controls">
                            <input id="categoryId" name="categoryId" value="${productCategory.categoryId}" type="hidden" />
                            <div class="control-group"><label class="control-label"><strong>类目名称</strong></label>
                                <div class="controls"><input id="categoryName" name="categoryName" type="text" value="${productCategory.categoryName}" /></div>
                            </div>
                        </div>
                        <br>
                        <div class="control-group"><label class="control-label"><strong>类目类型</strong></label>
                            <div class="controls">
                                <input id="categoryType" name="categoryType" type="text" value="${productCategory.categoryType}" readonly="true" style="background-color: lightgray;"/>
                            <p>此项不能更改</p>
                            </div>
                        </div>
                        <br>
                        <div class="controls"><button class="btn btn-primary" name="signup" type="submit" value="Sign up">保存提交</button></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../common/layout.ftl">
<script></script>
<#include "../common/js.ftl">
</body>
</html>