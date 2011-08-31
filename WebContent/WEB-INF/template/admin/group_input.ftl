<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑组- Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>组信息<#else>编辑组信息</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>group!save.action<#else>group!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="基本公司信息" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						组名称:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="group.name"  value="${(group.name)!}" />
							<label class="requireField">*</label>
						<#else>
							<input type="text" name="group.name" value="${(group.name)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						所在班级:
					</th>
					<td>
						<select name="group.className" id="className" class="{required: true}">
							<option value="">请选择...</option>
							<#list lxClassList as list>
								<option value="${list.name}"<#if (list.name == className || list.name == student.className.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						组描述:
					</th>
					<td>
						<input type="text" name="group.depict"  value="${(group.depict)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>