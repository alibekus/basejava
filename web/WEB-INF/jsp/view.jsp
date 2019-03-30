<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="kz.akbar.basejava.model.ListSection" %>
<%@ page import="kz.akbar.basejava.model.OrganizationSection" %>
<%@ page import="kz.akbar.basejava.util.HtmlUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a></h2>
    <p>
    <h3>Контакты:</h3>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<kz.akbar.basejava.model.ContactType, kz.akbar.basejava.model.Contact>"/>
        <%=contactEntry.getKey().toHtml(contactEntry.getValue().getValue())%><br/>
    </c:forEach>
    <p>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<kz.akbar.basejava.model.SectionType, kz.akbar.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="kz.akbar.basejava.model.Section"/>
            <tr>
                <td colspan="2"><h3><a name="type.name">${type.title}</a></h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <strong><%=section.toString()%></strong><br><br>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENTS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty org.nameLink.value}">
                                        <h3>${org.nameLink.title}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${org.nameLink.value}">${org.nameLink.title}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${org.positions}">
                            <jsp:useBean id="position" type="kz.akbar.basejava.model.Organization.Position"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDate(position)%>
                                </td>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>