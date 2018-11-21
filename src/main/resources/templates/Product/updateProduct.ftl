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
        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <form action="${basePath}/product/updateProduct" class="form-horizontal" method="post" >
            <input type="hidden" value="${productInfo.productId}" name="productId"/>
            <div class="control-group"><label class="control-label">商品名称</label>
                <div class="controls"><input id="productName" name="productName" value="${productInfo.productName}" type="text" /></div>
                <div class="control-group"><label class="control-label">商品价格</label>
                    <div class="controls"><input id="productPrice" name="productPrice" type="text" value="${productInfo.productPrice}" /></div>
                </div>
            </div>
            <div class="control-group"><label class="control-label">商品库存</label>
                <div class="controls"><input id="productStock" name="productStock" type="text" value="${productInfo.productStock}" />
                    <div class="control-group"><label class="control-label">商品描述</label>
                        <div class="controls"><input id="productDescription" name="productDescription" type="text" value="${productInfo.productDescription}" /></div>
                    </div>
                </div>
            </div>
            <div class="control-group"><label class="control-label">商品图片(输入地址)</label>
                <div class="controls"><input id="productIcon" name="productIcon" type="text" value="${productInfo.productIcon}" />
                </div>
            </div>
            <div class="control-group"><label class="control-label">商品所属类目</label>
                <#--<div class="controls"><input id="categoryType" name="categoryType" type="text" value="${productInfo.categoryType}" />-->
                <br>
                <select name = "categoryType">
                <#list productCategoryPage.content as productCategory>
                    <option value="${productCategory.categoryType}" >${productCategory.categoryName }</option>
                </#list>
                </select>
            </div>
            <div class="control-group"><label class="control-label">商品状态</label>
                 <div class="controls">
                       <div class="radio">
                            <label><input <#if productInfo.productStatus == 0 >checked</#if> name="productStatus" type="radio" value="0" />上架中</label>
                            <label><input <#if productInfo.productStatus == 1 >checked</#if> name="productStatus" type="radio" value="1" />已下架</label>
                       </div>
                 </div>
            </div>
            <div class="controls"><button class="btn btn-primary" name="signup" type="submit" value="Sign up">保存提交</button></div>
        </form>
    </div>
</div>

<#include "../common/layout.ftl">
<script></script>
<#include "../common/js.ftl">
</body>
</html>