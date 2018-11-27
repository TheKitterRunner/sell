<!DOCTYPE html>

<#include "header.ftl">

<body>

<div style="margin-top:100px"></div>
<div class="container-fluid">
    <div class="row">
        <div class="d-flex justify-content-center col-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">系统提示</h4>
                    <div class="alert alert-warning">
                        <h4 class="text-warning"><i class="fa fa-exclamation-triangle"></i> 警告</h4>
                        <p>${msg}</p>
                        <hr>
                        <a href="${basePath}/${url}" class="alert-link">3秒后页面自动跳转...没有跳转请点击</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    //页面自动跳转
    setTimeout(function(){
        window.location.href = "${basePath}/${url}";
    }, 3000);
</script>
</body>
</html>