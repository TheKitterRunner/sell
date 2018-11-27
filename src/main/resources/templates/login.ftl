<!DOCTYPE html>
<html dir="ltr" lang="zh">

<!-- head -->
<#include "common/header.ftl">

<body>
<div class="main-wrapper">
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <!-- Login box.scss -->
    <div class="auth-wrapper d-flex no-block justify-content-center align-items-center" style="background:url(${basePath}/assets/images/big/auth-bg.jpg) no-repeat center center;">
        <div class="auth-box">
            <div id="loginform">
                <div class="logo">
                <#--<span class="db"><img src="${basePath}/assets/images/logo-icon.png" alt="logo" /></span>-->
                    <h4 class="font-medium m-b-20">商家登录</h4>
                </div>
                <!-- Form -->
                <div class="row">
                    <div class="col-12">
                        <form class="form-horizontal m-t-20" id="loginform" action="${basePath}/seller/accountLogin" method="post">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1"><i class="ti-user"></i></span>
                                </div>
                                <input type="text" name="account" class="form-control form-control-lg" placeholder="账号" aria-label="Account" aria-describedby="basic-addon1">
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon2"><i class="ti-pencil"></i></span>
                                </div>
                                <input type="password" name="password" class="form-control form-control-lg" placeholder="密码" aria-label="Password" aria-describedby="basic-addon1">
                            </div>
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="customCheck1">
                                        <label class="custom-control-label" for="customCheck1">请记住我</label>
                                        <a href="javascript:void(0)" id="to-recover" class="text-dark float-right"><i class="fa fa-lock m-r-5"></i> 忘记密码？</a>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group text-center">
                                <div class="col-xs-12 p-b-20">
                                    <button class="btn btn-block btn-lg btn-info" type="submit">登 录</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 m-t-10 text-center">
                                    <div class="social">
                                        <a onclick="wxLogin()" href="javascript:void(0)" class="btn btn-success" data-toggle="tooltip" title="" data-original-title="微信登录">
                                            <i aria-hidden="true" class="fab fa-weixin fa-2x"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group m-b-0 m-t-10">
                                <div class="col-sm-12 text-center">
                                    没有账号吗？ <a href="javascript:void(0)" class="text-info m-l-5"><b>注册</b></a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ============================================================== -->
<!-- All Required js -->
<!-- ============================================================== -->
<script src="${basePath}/assets/libs/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap tether Core JavaScript -->
<script src="${basePath}/assets/libs/popper.js/dist/umd/popper.min.js"></script>
<script src="${basePath}/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- ============================================================== -->
<!-- This page plugin js -->
<!-- ============================================================== -->
<script>
    $('[data-toggle="tooltip"]').tooltip();
    $(".preloader").fadeOut();

    //微信扫码登录
    function wxLogin() {
        var iWidth = 600; //弹出窗口的宽度;
        var iHeight = 500; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置
        var iLeft = (window.screen.availWidth - iWidth) / 2; //获得窗口的水平位置
        window.open("${basePath}/wechat/qrAuthorize?returnUrl=http://http://227aq28234.imwork.net:36320/sell/seller/login",
                "微信登录", "width="+iWidth+",height="+iHeight+",left="+iLeft+",top="+iTop);
    }
</script>
</body>

</html>