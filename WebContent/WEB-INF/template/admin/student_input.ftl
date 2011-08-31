<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑学生- Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="template/admin/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	function lxit(){
		var className=document.all['student.className'].value;
		var group=$("#groupName");
		group.empty();
		$.ajax({
			type: "POST",
		    url: "student!getMsg.action",
		    data: "pager.property=className&pager.keyword="+className,
		    success: function(msg){
		      var list=eval(msg);
		      for(var j=0;j<list.length;j++){
		          $("<option value="+list[j].name+">"+list[j].name+"</option>").appendTo(group);
		      }
		    }
		}); 
	}
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>学生信息<#else>编辑学生信息</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>student!save.action<#else>student!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div class="blank"></div>
			<ul class="tab">
				<li>
					<input type="button" value="学生基本信息" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						学生名称:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="student.name"  value="${(student.name)!}" />
							<label class="requireField">*</label>
						<#else>
							<input type="text" name="student.name" value="${(student.name)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						所在班级:
					</th>
					<td>
						<select name="student.className" id="classes" class="{required: true}" onchange="lxit()">
							<option value="">请选择...</option>
							<#list lxClassList as list>
								<option value="${list.name}"<#if (list.name == className || list.name == student.className.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
						<lable class="requireField">*</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						所在组：&nbsp;
						<select name="student.groupName" id="groupName" class="{required: true}">
							<option value="">请选择...</option>
						</select>
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr>
					<th>
						学生性别:
					</th>
					<td>
						<input type="radio" name="student.sex"  value="男" ${nan!}/>男     <input type="radio" name="student.sex"  value="女" ${nv!}/>女
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						出生地:
					</th>
					<td>
						<input type="text" name="student.nativePlace"  value="${(student.nativePlace)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						学历:
					</th>
					<td>
						<input type="text" name="student.education"  value="${(student.education)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						民族:
					</th>
					<td>
						<input type="text" name="student.nation"  value="${(student.nation)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						生日:
					</th>
					<td>
						<input type="text" name="student.birth"  value="${(student.birth)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						电话:
					</th>
					<td>
						<input type="text" name="student.telephone"  value="${(student.telephone)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						 手机:
					</th>
					<td>
						<input type="text" name="student.mobilePhone"  value="${(student.mobilePhone)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr><tr>
					<th>
						身份证:
					</th>
					<td>
						<input type="text" name="student.idCard"  value="${(student.idCard)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr><tr>
					<th>
						家庭地址:
					</th>
					<td>
						<input type="text" name="student.homeAddress"  value="${(student.homeAddress)!}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						登记日期:
					</th>
					<td>
						<input type="text" name="student.dateOfEnrollment"  value="${(student.dateOfEnrollment)!}" />
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