<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>新增/编辑题目- Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>新增题目<#else>编辑题目</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>subject!save.action<#else>subject!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="forward" value="${forward}"/>
			<input type="hidden" name="back" value="${back}"/>
			<div class="blank"></div>
			
			<table class="inputTable tabContent">
				<tr>
					<th>
						问题:
					</th>
					<td>
						<#if isAdd??>
							<textarea name="subject.issue" rows="1" cols="50" value="${(subject.issue)!}" ></textarea>
							<label class="requireField">*</label>
						<#else>
							<textarea name="subject.issue" rows="1" cols="50" value="${(subject.issue)!}" ></textarea>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						答案:
					</th>
					<td>
						<#if isAdd??>
						<textarea name="subject.solution" rows="20" cols="50" value="${(subject.solution)!}"></textarea>
						<label class="requireField">*</label>
						<#else>
							<textarea name="subject.solution" rows="20" cols="50" value="${(subject.solution)!}"></textarea>
						</#if>
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