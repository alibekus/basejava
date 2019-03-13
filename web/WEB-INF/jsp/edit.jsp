<%@ page import="kz.akbar.basejava.model.ContactType" %>
<%@ page import="kz.akbar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="kz.akbar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="mode" value="edit"/>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><h3>Имя:</h3></dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h4>Контакты:</h4>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type).value}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type.name().equals(SectionType.EXPERIENCE.name())
                                        or type.name().equals(SectionType.EDUCATION.name())}">
                    <c:forEach var="organization" items="${resume.getSection(type).organizations}">
                        <dl>
                            <dt>Организация:</dt>
                            <dd><input type="text"
                                       name="${String.join('=', type.name(), organization.nameLink.title)}"
                                       size="30" value="${organization.nameLink.title}"/>
                            <dt>Веб-ссылка:</dt>
                            <dd><input type="text"
                                       name="${String.join('=',type.name(),organization.nameLink.title)}"
                                       size="40" value="${organization.nameLink.value}"/>
                            <c:forEach var="position" items="${organization.positions}">
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd><input type="date"
                                           name="${String.join('=', type.name(), organization.nameLink.title)}"
                                           size="20" value="${position.startDate}"/>
                                <dt>Дата окончания:</dt>
                                <dd><input type="date"
                                           name="${String.join('=', type.name(), organization.nameLink.title)}"
                                           size="20" value="${position.endDate}"/>
                                <dt>Должность:</dt>
                                <dd><input type="text"
                                           name="${String.join('=', type.name(), organization.nameLink.title)}"
                                           size="40" value="${position.title}"/>
                                <dt>Обязанности:</dt>
                                <dd><textarea type="text"
                                              name="${String.join('=',type.name(), organization.nameLink.title)}"
                                              cols="60" rows="10">${position.description}</textarea></dd>
                            </dl>
                            </c:forEach>
                        </dl>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt></dt>
                        <dd><textarea type="text" name="${type.name()}" cols="60" rows="5">
                                ${resume.getSection(type)}</textarea></dd>
                    </dl>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>