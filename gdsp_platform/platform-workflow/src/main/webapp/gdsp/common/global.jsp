<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringUtils,org.apache.commons.lang3.ObjectUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 流程编码 -->
<input type="hidden" id ="processCode" value="" />
<!-- 默认单据 -->
<input type="hidden" id ="defaultForm" value="" />
<!-- 任务编码 -->
<input type="hidden" id ="taskFalg" value="true" />
<input type="hidden" id ="taskIds" value="" />
<!-- 任务名称 -->
<input type="hidden" id ="taskNameFalg" value="true" />
<input type="hidden" id ="taskNames" value="" />
<!-- 超时时长 -->
<input type="hidden" id ="timeout" value="true" />
<!-- 超时处理方式 -->
<input type="hidden" id ="dealMethod" value="true" />
