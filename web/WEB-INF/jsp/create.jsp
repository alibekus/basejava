<%@ page import="kz.akbar.basejava.model.ContactType" %>
<%@ page import="kz.akbar.basejava.model.SectionType" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Новое резюме</title>

    <script language="javascript">


        var typeName;
        //Map of org numbers to section types
        var sectionOrgs = new Map();
        //Map of org numbers of section type to position numbers
        var secOrgPositions = new Map();
        //Set variable for some org's positions; saved in orgPositions map as value;
        var positionNums;

        function addOrg(typename) {
            typeName = typename;
            var sectionDiv = document.getElementById(typename + "_orgItems");
            var addOrgButton = document.getElementById(typename + "_addOrg");
            var orgNumber = 0;
            if (sectionOrgs.has(typename)) {
                orgNumber = sectionOrgs.get(typename);
            }
            orgNumber++;
            var newOrgItem = "<hr>"
            newOrgItem += "<dl><dt><strong>Организация: </strong></dt>";
            newOrgItem += "<dd><input type=\"text\" name = \"" + typeName + "_orgName_" + orgNumber
                + "\" size=\"50\" onChange=\"isEmptyText(this.value, 'Организация')\" value=\""+randomText(10)+"\"></dd>";
            newOrgItem += "<dt>Ссылка:</dt>";
            newOrgItem += "<dd><input type=\"text\" name=\"" + typeName + "_url_" + orgNumber +
                "\" size=\"50\" onChange=\"isValidURL(this.value)\" value=\"http://" +randomText(10)+"\"></dd></dl>";
            newOrgItem += "<input type=\"button\" id= \"" + typeName + "_org_" + orgNumber +
                "_addPositionButton\" value=\"Добавить позицию\" onclick=\"addPosition(this.id);\"><br>";
            newOrgItem += "<p><input type=\"button\" id= \"" + typeName + "_org_" + orgNumber + "_removeButton\" " +
                "value=\"Удалить организацию\" onclick=\"removeOrg(this)\"</p></div>";
            var newOrgNode = document.createElement("div");
            newOrgNode.id = typeName + "_org_" + orgNumber + "_div";
            newOrgNode.innerHTML = newOrgItem;
            sectionDiv.insertBefore(newOrgNode, addOrgButton);
            sectionOrgs.set(typename, orgNumber);
        }

        function addPosition(divId) {
            var divIdSplit = divId.toString().split("_");
            addPositionFields(divIdSplit[0], divIdSplit[2]);
        }

        function addPositionFields(typename, orgNumber) {
            typeName = typename;
            var hasNum = false;
            var posNumber = 1;
            var sectionOrgNumber = typeName.concat(orgNumber);
            if (secOrgPositions.has(sectionOrgNumber)) {
                positionNums = secOrgPositions.get(sectionOrgNumber);
                for (var i = 0; i < positionNums.size; i++) {
                    if (positionNums.has(posNumber)) {
                        hasNum = true;
                        posNumber++;
                        continue;
                    } else {
                        hasNum = false;
                        break;
                    }
                }
                positionNums.add(posNumber);
            } else {
                positionNums = new Set();
                positionNums.add(posNumber);
            }
            secOrgPositions.set(sectionOrgNumber, positionNums);
            var orgDiv = document.getElementById(typeName + "_org_" + orgNumber + "_div");
            var addPosButton = document.getElementById(typeName + "_org_" + orgNumber + "_addPositionButton");
            var newPosItem = "<b>Позиция </b><br>";
            newPosItem += "<dl><dt>Дата начала: </dt>";
            newPosItem += "<dd><input type=\"month\" name=\"" + typeName + "_startDate_" + orgNumber + "_"
                + posNumber + "\" size=\"40\" onchange=\"isEmptyText(this.value, 'Дата начала')\" " +
                "value=\""+ randomDate(new Date(2012, 0, 1), new Date()) +"\"></dd>";
            newPosItem += "<dt>Дата завершения: </dt>";
            newPosItem += "<dd><input type=\"month\" name=\"" + typeName + "_endDate_" + orgNumber + "_"
                + posNumber + "\" size=\"40\" onchange=\"isEmptyText(this.value, 'Дата завершения')\" " +
                "value=\""+ randomDate(new Date(2012, 0, 1), new Date()) +"\"></dd>";
            newPosItem += "<dt>Должность: </dt>";
            newPosItem += "<dd><input type=\"text\" name=\"" + typeName + "_positionTitle_" + orgNumber + "_"
                + posNumber + "\" size=\"50\" onchange=\"isEmptyText(this.value, 'Должность')\" " +
                "value=\"" +randomText(10)+"\"></dd>";
            newPosItem += "<dt>Обязанности: </dt>";
            newPosItem += "<dd><textarea type=\"text\" name=\"" + typeName + "_duties_" + orgNumber + "_"
                + posNumber + "\" cols=\"50\" rows=\"5\" onchange=\"isEmptyText(this.value, 'Обязанности')\">"
                + randomText(50) + "</textarea></dd></dl>";
            newPosItem += "<p><input type=\"button\" id= \"" + typeName + "_org_" + orgNumber + "_pos_"
                + posNumber + "_removeButton\" value=\"Удалить позицию\" onclick=\"removePosition(this)\"></p>";
            var newPosNode = document.createElement("div");
            newPosNode.id = typeName + "_org_" + orgNumber + "_position_" + posNumber + "_div";
            newPosNode.innerHTML = newPosItem;
            orgDiv.insertBefore(newPosNode, addPosButton);
        }

        function removeOrg(button) {
            button.parentNode.parentNode.parentNode.removeChild(button.parentNode.parentNode);
        }

        function removePosition(button) {
            var buttonIdSplit = button.id.split("_");
            var typeSecName = buttonIdSplit[0];
            var orgNum = buttonIdSplit[2];
            var posNum = buttonIdSplit[4];
            var sectionOrgNumber = typeSecName.concat(orgNum);
            positionNums = secOrgPositions.get(sectionOrgNumber)
            positionNums.delete(posNum);
            secOrgPositions.set(sectionOrgNumber, positionNums);
            button.parentNode.parentNode.parentNode.removeChild(button.parentNode.parentNode);
        }

        function checkAllFields() {
            return isEmptyText(document.getElementById('fullName'), 'Имя');
        }

        // checkup for no empty text fields
        function isEmptyText(str, field) {
            if (str == " " || str == "") {
                alert("Введите сведения в поле '" + field + "'")
                return false
            }
            return true
        }

        // Function:  isValidEmailAddress
        //  Purpose:  to check for a valid email address
        function isValidEmailAddress(str) {
//  проверяет  неосталось ли поле пустым
            if (str == " ") {
                alert("Введите свой электронный адрес.")
                return false
            }
// проверяет наличи  '@' с 3 знака
            else if (str.indexOf("@", 2) == -1) {
                alert("Вы ввели  " + str + ". Вы уверенны, что это ваш электронный адрес.");
                return false
            }
            // проверяет наличие 'точки' с 4 знака
            else if (str.indexOf(".", 3) == -1) {
                alert("Вы ввели " + str + ". Вы уверенны, что это ваш электронный адрес.");
                return false
            }
            return true
        }

        function isValidURL(str) {
            if (str == " " || str == "") {
                if (!confirm("Отсутствует адрес сайта. Продолжить без адреса?")) {
                    // проверяет наличие 'http://' с 0 знака
                    if (str.indexOf("http://", 0) == -1) {
                        alert("Не указана протокол, например, http://");
                        return false;
                    }
                    // проверяет наличие 'точки' с 8 знака
                    if (str.indexOf(".", 7) == -1) {
                        alert("Не указана доменная зона");
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }

        function randomText(length) {
            var text = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            for (var i = 0; i < length; i++)
                text += possible.charAt(Math.floor(Math.random() * possible.length));
            return text;
        }

        function randomDate(start, end) {
            var date = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
            var month = date.getMonth() + 1;
            var year = date.getFullYear();
            var yearMonth = year + "-" + month;
            return yearMonth;
        }
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="mode" value="create"/>
        <h2>Новое резюме</h2>
        <dl>
            <dt><h3>Имя:</h3></dt>
            <dd><input type="text" id="fullName" name="fullName" size="50"
                       onchange="isEmptyText(document.getElementById('fullName'))" value="Name + <%=UUID.randomUUID().toString()%>"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <c:choose>
                    <c:when test="${contactType.name().equals(ContactType.PHONENUMBER)
                                    or contactType.name().equals(ContactType.SKYPE)}">
                        <dd><input type="text" id="${contactType.name()}" name="${contactType.name()}" size="30"
                                   onchange="isEmptyText(document.getElementById(${contactType.name()}).value,
                                       ${contactType.title})" value="${contactType.title} <%=UUID.randomUUID().toString()%>"></dd>
                    </c:when>
                    <c:when test="${contactType.name().equals(ContactType.EMAIL)}">
                        <dd><input type="text" id="${contactType.name()}" name="${contactType.name()}" size="30"
                                   onchange="isValidEmailAddress(document.getElementById(${contactType.name()}).value)"
                        value="email@email.com">
                        </dd>
                    </c:when>
                    <c:otherwise>
                        <dd><input type="text" id="${contactType.name()}" name="${contactType.name()}" size="30"
                                   onchange="isValidURL(document.getElementById(${contactType.name()}).value)"
                        value="http://homepage.com"></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <h3>Разделы:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <dl>
                <dt><h4>${sectionType.title}</h4></dt>
                <c:choose>
                    <c:when test="${sectionType.name().equals(\"EXPERIENCE\")
                                        or sectionType.name().equals(\"EDUCATION\")}">
                        <dd>
                            <div ID="${sectionType.name()}_orgItems">
                                <input type="button" ID="${sectionType.name()}_addOrg"
                                       name="${sectionType.name()}_addOrg"
                                       value="Добавить организацию"
                                       onClick="addOrg('${sectionType.name()}');">
                            </div>
                        </dd>
                    </c:when>
                    <c:otherwise>
                        <dd><textarea type="text" name="${sectionType.name()}" cols="60" rows="5"
                                      onchange="isEmptyText(document.getElementById(${sectionType.name()}).value)">
                            ${sectionType.name()} <%=UUID.randomUUID().toString()%>
                        </textarea></dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit" onclick="checkAllFields()">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>