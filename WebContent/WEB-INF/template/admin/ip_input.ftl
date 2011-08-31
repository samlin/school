<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑IP实名制- Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>IP实名制信息<#else>编辑IP实名制信息</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>ip!save.action<#else>ip!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="基本IP实名制信息" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						网络IP:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="ip.netID"  value="${(ip.netID)!}" />
							<label class="requireField">*</label>
						<#else>
							<input type="text" name="ip.netID" value="${(ip.netID)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						用户名:
					</th>
					<td>
						<input type="text" name="ip.userName" value="${(ip.userName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						班级:
					</th>
					<td>
						<input type="text" name="ip.classes"  value="" />
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