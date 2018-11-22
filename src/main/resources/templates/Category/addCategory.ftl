<!DOCTYPE html>
<html lang="zh" xmlns="http://www.w3.org/1999/html">
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
        <p>&nbsp;</p>

        <p>&nbsp;</p>
        <div class="container">
            <div class="row">
                <div class="span12">
                    <form class="form-horizontal" action="${basePath}/category/doAddCategory" method="post" >
                        <div class="control-group">
                            <label class="control-label" for="categoryName">类目名称</label>
                            <input id="categoryName" name="categoryName" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="categoryType">类目编号</label>
                            <input id="categoryType" name="categoryType" type="text" />
                        </div>
                        <br>
                        <center><button type="submit" class="btn" style="align-content: center">添加类目</button></center>
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