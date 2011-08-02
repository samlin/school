<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>班级管理</span>
			</dt>
			<dd>
				<a href="lx_class!list.action" target="mainFrame">班级列表</a>
			</dd>
			<dd>
				<a href="lx_class!add.action" target="mainFrame">新增班级</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>学生管理</span>
			</dt>
			<dd>
				<a href="student!list.action" target="mainFrame">学生列表</a>
			</dd>
			<dd>
				<a href="student!add.action" target="mainFrame">新建学生</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>作业管理</span>
			</dt>
			<dd>
				<a href="delivery_type!list.action" target="mainFrame">新建作业</a>
			</dd>
			<dd>
				<a href="area!list.action" target="mainFrame">作业列表</a>
			</dd>
		</dl>
	</div>
</body>
</html>