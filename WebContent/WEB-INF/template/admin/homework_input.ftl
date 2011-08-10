<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑作业 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加作业<#else>编辑作业</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>homework!save.action<#else>homework!update.action</#if>" method="post">
			<input type="hidden" name="entity.id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						班级:
					</th>
					<td>
						<input type="text" name="entity.classId"  value="${(entity.classId)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
					<th>
						作业名称:
					</th>
					<td>
						<input type="text" name="entity.summary"  value="${(entity.summary)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						作业描述:
					</th>
					<td>
						<input type="text" name="entity.description" value="${(entity.description)!}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>