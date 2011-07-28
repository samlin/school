<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>商品分类列表 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {


})
</script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>班级列表&nbsp;<span class="pageInfo">总记录数: ${lxClassList?size}</span></h1>
		</div>
		<form id="listForm" action="product_category!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='lx_class!add.action'" value="添加班级" />
			</div>
			<table class="listTable">
				<tr>
					
					<th>
						班级名&nbsp;
					</th>
					<th>
						描述
					</th>
					<th>
						老师Id
					</th>
					<th>
						操作
					</th>
					
				</tr>
				<#list lxClassList as list>
					<tr>
						<td >
								${list.name}
						</td>
						<td>
							${list.descs}
						</td>
						<td>
							${list.teacherId}
						</td>
						<td>
							<a href="lx_class!delete.action?id=${list.id}" class="deleteAction" title="删除" >[删除]</a>
							
							<a href="lx_class!edit.action?id=${list.id}" title="编辑">[编辑]</a>
							<a href="lx_class!dayLog.action?id=${list.name}" title="编辑">[创建日志用户]</a>
							<a href="lx_class!dayLogProject.action?id=${list.name}" title="编辑">[创建日志]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if lxClassList?size == 0>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>