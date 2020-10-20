<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
  <title>Devops用户服务 -- 演示</title>
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
  <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css"/>
</head>
<body>
<div class="ui container" style="margin-top: 10px; margin-bottom: 20px">
  <h1>用户服务 -- 用于Devops流水线演示</h1>
  <div>
    <h4 class="ui horizontal divider header">
      <i class="bar chart icon"></i>
      用户信息列表
    </h4>
    <table class="ui celled unstackable table orange">
      <thead>
      <tr class="center aligned">
        <th class="six wide">用户ID</th>
        <th class="six wide">用户姓名</th>
        <th class="two wide">用户年龄</th>
      </tr>
      </thead>
      <tbody>
      <#list users as user>
        <tr class="center aligned">
          <td>${user.id}</td>
          <td>${user.name}</td>
          <td>${user.age}</td>
        </tr>
      </#list>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
