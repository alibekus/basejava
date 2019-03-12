<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.akbar.basejava.model.SectionType" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
    <h3>Контакты:</h3>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<kz.akbar.basejava.model.ContactType, kz.akbar.basejava.model.Contact>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue().getValue())%><br/>
    </c:forEach>
    <p>
        <c:forEach var="section" items="${resume.sections}">
    <h3>${section.key.title}</h3>
    <c:choose>
        <c:when test="${section.key.name().equals(SectionType.EXPERIENCE.name())
                                            or section.key.name().equals(SectionType.EDUCATION.name())}">
            <c:forEach var="org" items="${section.value.organizations}">
                <jsp:useBean id="org" type="kz.akbar.basejava.model.Organization"/>
                <%=org.toString().replace("\n", "<br>")%>
                ==========================================================================<br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            ${section.value}
        </c:otherwise>
    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>