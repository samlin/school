<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>学生列表 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>成员列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="student!list.action" method="post">
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="className">班级名</span>
					</th>
					<th>
						<span class="sort" name="name">姓名</span>
					</th>
					<th>
						<span class="sort" name="sex">性别</span>
					</th>
					<th>
						<span class="sort" name="nativePlace">籍贯</span>
					</th>
					<th>
						<span class="sort" name="nation">民族</span>
					</th>
					<th>
						<span class="sort" name="education">学历</span>
					</th>
					<th>
						<span class="sort" name="birth">生日</span>
					</th>
					<th>
						<span class="sort" name="telephone">电话</span>
					</th>
					<th>
						<span class="sort" name="mobilePhone">家庭电话</span>
					</th>
					<th>
						<span class="sort" name="idCard">身份证</span>
					</th>
					<th>
						<span class="sort" name="homeAddress">家庭地址</span>
					</th>
					<th>
						<span class="sort" name="dateOfEnrollment">注册日期</span>
					</th>
				</tr>
				<#list studentList as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${(list.id)!}" />
						</td>
						<td>
							${list.className}
						</td>
						<td>
							${list.name}
						</td>
						<td>
							${list.sex}
						</td>
						<td>
							${list.nativePlace}
						</td>
						<td>
							${list.nation}
						</td>
						<td>
							${list.education}
						</td>
						<td>
							${list.birth}
						</td>
						<td>
							${list.telephone}
						</td>
						<td>
							${list.mobilePhone}
						</td>
						<td>
							${list.idCard}
						</td>
						<td>
							${list.homeAddress}
						</td>
						<td>
							${list.dateOfEnrollment}
						</td>
					</tr>
				</#list>
			</table>
				<div class="pagerBar">
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
				</div>
		</form>
	</div>
</body>
</html>
