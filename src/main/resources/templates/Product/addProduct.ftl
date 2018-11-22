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
                    <h4 class="page-title">添加商品</h4>
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

        <div class="container">
            <div class="row">
                <div class="span12">
                    <form class="form-horizontal" action="${basePath}/seller/product/doAdd" method="post" >
                        <div class="control-group">
                            <label class="control-label" for="productName">商品名称</label>
                                <input id="productName" name="productName" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="productPrice">商品价格</label>
                                <input id="productPrice" name="productPrice" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="productStock">商品库存</label>
                                <input id="productStock" name="productStock" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="productDescription">商品描述</label>
                                <input id="productDescription" name="productDescription" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="productIcon">商品图片</label>
                                <input id="productIcon" name="productIcon" type="text" />
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="productCategory">商品所属类目</label>
                                 <select name = "categoryType">
                                        <#list productCategoryPage.content as productCategory>
                                           <option value="${productCategory.categoryType}">${productCategory.categoryName }</option>
                                        </#list>
                                 </select>
                        </div>
                        <br>
                        <div class="control-group">
                            <div class="controls">
                                <label class="control-label" style="align-content: left">商品状态</label>
                                <label class="checkbox">
                                    <input checked name="productStatus" type="radio" value="0" /> 上架
                                    <input name="productStatus" type="radio" value="1" />下架
                                </label>
                                <br>
                            </div>
                        </div>
                        <br>
                        <div class="controls"><button class="btn btn-primary" name="signup" type="submit" value="Sign up">添加商品</button></div>
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