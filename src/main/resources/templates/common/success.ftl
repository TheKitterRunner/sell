<#assign basePath=request.contextPath />
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>微信点餐管理后台 - 消息提示</title>
    <link href="${basePath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
    <h1 class="display-4">系统消息</h1>
    <p class="lead"></p>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-dismissable alert-success">
                <h4>
                    <span class="glyphicon glyphicon-volume-up" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    成功!
                </h4>
                <strong>${msg}, </strong>
                <a href="${basePath}/${url}" class="alert-link">3秒后页面自动跳转...</a>
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