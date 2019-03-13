<%@ page import="kz.akbar.basejava.model.ContactType" %>
<%@ page import="kz.akbar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Новое резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="mode" value="create"/>
        <dl>
            <dt><h3>Имя:</h3></dt>
            <dd><input type="text" name="fullName" size="50"></dd>
        </dl>
        <h4>Контакты:</h4>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type.name().equals(SectionType.EXPERIENCE.name())
                                        or type.name().equals(SectionType.EDUCATION.name())}">
                    <dl>
                        <dt>Организация:</dt>
                        <dd><input type="text" name="${String.join('=',type.name(),"orgName")}" size="60"/> </dd>
                        <dt>Веб-ссылка:</dt>
                        <dd><input type="text" name="${String.join('=',type.name(),"url")}", size="60"/></dd>
                        <dl>
                            <dt>Должность</dt>
                            <dd>
                                <dt>Дата начала:</dt>
                                <dd><input type="date" name="${String.join('=',type.name(),"startDate")}", size="40"> </dd>
                                <dt>Дата завершения:</dt>
                                <dd><input type="date" name="${String.join('=',type.name(),"endDate")}", size="40"> </dd>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${String.join('=',type.name(),"positionTitle")}", size="60"> </dd>
                                <dt>Обязанности:</dt>
                                <dd><textarea type="text" name="${String.join('=',type.name(),"duties")}" cols="60" rows="10"></textarea></dd>
                                <a href="addPosition.jsp" type="text/html">Добавить должность</a>
                            </dd>
                        </dl>
                    </dl>
                    <a href="addOrganization.jsp" type="text/html">Добавить организацию</a>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt></dt>
                        <dd><textarea type="text" name="${type.name()}" cols="60" rows="5">
                                </textarea></dd>
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