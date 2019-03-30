<%@ page import="kz.akbar.basejava.model.ContactType" %>
<%@ page import="kz.akbar.basejava.model.SectionType" %>
<%@ page import="kz.akbar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="kz.akbar.basejava.model.Resume" type="kz.akbar.basejava.model.Resume"
                 scope="request"/>
    <c:choose>
        <c:when test="${resume.uuid == null || resume.uuid.equals(\"\")}">
            <title>Новое резюме</title>
        </c:when>
        <c:otherwise>
            <title>Резюме ${resume.fullName}</title>
        </c:otherwise>
    </c:choose>
    <script language="javascript">

        var typeName;
        //Map of org numbers to section types
        var sectionOrgs = new Map();
        //Map of org numbers of section type to position numbers
        var secOrgPositions = new Map();

        function increaseOrgNum(sectionTypeName) {
            var orgNum = 0;
            if (sectionOrgs.has(sectionTypeName)) {
                orgNum = sectionOrgs.get(sectionTypeName);
            }
            orgNum++;
            sectionOrgs.set(sectionTypeName, orgNum);
            return orgNum;
        }

        function decreaseOrgNum(sectionTypeName) {
            if (sectionOrgs.has(sectionTypeName)) {
                var orgNum = sectionOrgs.get(sectionTypeName);
                sectionOrgs.put(sectionTypeName, --orgNum);
            }
        }

        function increasePositionNum(sectionTypeName, orgNumber) {
            var positionNums;
            var hasNum = false;
            var posNumber = 1;
            var sectionOrgNumber = sectionTypeName.concat(orgNumber);
            if (secOrgPositions.has(sectionOrgNumber)) {
                positionNums = secOrgPositions.get(sectionOrgNumber);
                for (var i = 0; i < positionNums.size; i++) {
                    if (positionNums.has(posNumber)) {
                        hasNum = true;
                        posNumber++;
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
            return posNumber;
        }

        function addOrgSection(typename) {
            typeName = typename;
            var orgSection = document.getElementById(typename + "_orgItems");
            var addOrgButton = document.getElementById(typename + "_addOrg");
            var orgNumber = window.increaseOrgNum(typeName);
            var orgItem = "<hr>";
            orgItem += "<dl><dt><strong>Организация: </strong></dt>";
            orgItem += "<dd><input type=\"text\" name = \"" + typeName + "_org_" + orgNumber + "_name\" " +
                "size=\"50\" onChange=\"isEmptyText(this.value, 'Организация')\" value=\"\"></dd>";
            orgItem += "<dt>Ссылка:</dt>";
            orgItem += "<dd><input type=\"text\" name=\"" + typeName + "_org_" + orgNumber + "_url\" " +
                " size=\"50\" onChange=\"isValidURL(this.value)\" value=\"http://\"></dd></dl>";
            orgItem += "<input type=\"button\" id= \"" + typeName + "_org_" + orgNumber +
                "_addPositionButton\" value=\"Добавить позицию\" onclick=\"addPosition(this.id);\"><br>";
            orgItem += "<p><input type=\"button\" id= \"" + typeName + "_org_" + orgNumber + "_removeButton\" " +
                "value=\"Удалить организацию\" onclick=\"removeOrg(this)\"</p>";
            var newOrgNode = document.createElement("div");
            newOrgNode.id = typeName + "_org_" + orgNumber;
            newOrgNode.innerHTML = orgItem;
            orgSection.insertBefore(newOrgNode, addOrgButton);
            sectionOrgs.set(typename, orgNumber);
        }

        function addPosition(divId) {
            var divIdSplit = divId.toString().split("_");
            addPositionFields(divIdSplit[0], divIdSplit[2]);
        }

        function addPositionFields(typename, orgNumber) {
            typeName = typename;
            var posNumber = window.increasePositionNum(typename, orgNumber);
            var orgDiv = document.getElementById(typeName + "_org_" + orgNumber);
            var addPosButton = document.getElementById(typeName + "_org_" + orgNumber + "_addPositionButton");
            var newPosItem = "<b>Позиция </b><br>";
            newPosItem += "<dt>Дата завершения: </dt>";
            newPosItem += "<dd><input type=\"month\" name=\"" + typeName + "_org_" + orgNumber + "_endDate_"
                + posNumber + "\" size=\"40\" onchange=\"isEmptyText(this.value, 'Дата завершения')\" value=\"\"></dd>";
            newPosItem += "<dl><dt>Дата начала: </dt>";
            newPosItem += "<dd><input type=\"month\" name=\"" + typeName + "_org_" + orgNumber + "_startDate_"
                + posNumber + "\" size=\"40\" onchange=\"isEmptyText(this.value, 'Дата начала')\" value=\"\"></dd>";
            newPosItem += "<dt>Должность: </dt>";
            newPosItem += "<dd><input type=\"text\" name=\"" + typeName + "_org_" + orgNumber + "_positionTitle_"
                + posNumber + "\" size=\"50\" onchange=\"isEmptyText(this.value, 'Должность')\" value=\"\"></dd>";
            newPosItem += "<dt>Обязанности: </dt>";
            newPosItem += "<dd><textarea name=\"" + typeName + "_org_" + orgNumber + "_duties_"
                + posNumber + "\" cols=\"50\" rows=\"5\" onchange=\"isEmptyText(this.value, 'Обязанности')\"></textarea></dd></dl>";
            newPosItem += "<p><input type=\"button\" id= \"" + typeName + "_org_" + orgNumber + "_pos_"
                + posNumber + "_removeButton\" value=\"Удалить позицию\" onclick=\"removePosition(this)\"></p>";
            var newPosNode = document.createElement("div");
            newPosNode.id = typeName + "_org_" + orgNumber + "_position_" + posNumber;
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
            if (window.secOrgPositions.has(sectionOrgNumber)) {
                var positionNums = secOrgPositions.get(sectionOrgNumber);
                positionNums.delete(posNum);
                secOrgPositions.set(sectionOrgNumber, positionNums);
                button.parentNode.parentNode.parentNode.removeChild(button.parentNode.parentNode);
            }
        }

        function checkAllFields() {
            return isEmptyText(document.getElementById('fullName'), 'Имя');
        }

        function isEmptyText(str, field) {
            if (str === " " || str === "") {
                alert("Введите сведения в поле '" + field + "'");
                return false
            }
            return true
        }

        function isValidEmailAddress(str) {
//  проверяет  неосталось ли поле пустым
            if (str === " ") {
                alert("Введите свой электронный адрес.");
                return false
            }
// проверяет наличи  '@' с 3 знака
            else if (str.indexOf("@", 2) === -1) {
                alert("Вы ввели  " + str + ". Вы уверенны, что это ваш электронный адрес.");
                return false
            }
            // проверяет наличие 'точки' с 4 знака
            else if (str.indexOf(".", 3) === -1) {
                alert("Вы ввели " + str + ". Вы уверенны, что это ваш электронный адрес.");
                return false
            }
            return true
        }

        function isValidURL(str) {
            if (str === " " || str === "") {
                if (!confirm("Отсутствует адрес сайта. Продолжить без адреса?")) {
                    // проверяет наличие 'http://' с 0 знака
                    if (str.indexOf("http://", 0) === -1) {
                        alert("Не указана протокол, например, http://");
                        return false;
                    }
                    // проверяет наличие 'точки' с 8 знака
                    if (str.indexOf(".", 7) === -1) {
                        alert("Не указана доменная зона");
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }

        function cancelModeBack() {
            document.getElementById("mode").value = "cancel";
            window.history.back();
        }
    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <c:choose>
            <c:when test="${resume.uuid == null || resume.equals(\"\")}">
                <h2>Новое резюме</h2>
                <input type="hidden" id="mode" name="mode" value="create"/>
            </c:when>
            <c:otherwise>
                <h2>Резюме ${resume.fullName}</h2>
                <input type="hidden" id="mode" name="mode" value="edit"/>
            </c:otherwise>
        </c:choose>
        <dl>
            <dt><h3>Имя:</h3></dt>
            <dd><label for="fullName"></label><input type="text" id="fullName" name="fullName" size="50" value="${resume.fullName}">
            </dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" id="${contactType.name()}" name="${contactType.name()}" size="30"
                           value="${resume.getContact(contactType).value}"></dd>
            </dl>
        </c:forEach>
        <h3>Разделы:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType.name().equals(\"EXPERIENCE\")
                                    or sectionType.name().equals(\"EDUCATION\")}">
                    <h4>${sectionType.title}</h4>
                    <c:if test="${resume.uuid.length() > 0}">
                        <c:forEach var="org" items="${resume.getSection(sectionType).getOrganizations()}"
                                   varStatus="orgCount">
                            <div id="${sectionType.name()}_org_${orgCount.index}">
                                <script language="JavaScript" type="text/javascript">
                                    this.increaseOrgNum('${sectionType.name()}');
                                </script>
                                <hr>
                                <dl>
                                    <dt><strong>Организация</strong></dt>
                                    <dd>
                                        <input type="text"
                                               id="${sectionType.name()}_org_${orgCount.index}_name"
                                               name="${sectionType.name()}_org_${orgCount.index}_name"
                                               value="${org.nameLink.title}" size="40">
                                    </dd>
                                    <dt>Адрес веб-сайта:</dt>
                                    <dd>
                                        <input type="text"
                                               id="${sectionType.name()}_org_${orgCount.index}_url"
                                               name="${sectionType.name()}_org_${orgCount.index}_url"
                                               value="${org.nameLink.value}" size="40">
                                    </dd>
                                </dl>
                                <c:forEach var="position" items="${org.getPositions()}" varStatus="positionCount">
                                    <div id="${sectionType.name()}_org_${orgCount.index}_position_${positionCount.index}">
                                        <script language="JavaScript" type="text/javascript">
                                            this.increasePositionNum('${sectionType.name()}', ${orgCount.index});
                                        </script>
                                        <dl>
                                            <b>Позиция</b>
                                            <dt>Дата завершения:</dt>
                                            <dd>
                                                <input type="month"
                                                       id="${sectionType.name()}_org_${orgCount.index}_endDate_${positionCount.index}"
                                                       name="${sectionType.name()}_org_${orgCount.index}_endDate_${positionCount.index}"
                                                       value="${DateUtil.format(position.getEndDate())}" size="40">
                                            </dd>
                                            <dt>Дата начала:</dt>
                                            <dd>
                                                <input type="month"
                                                       id="${sectionType.name()}_org_${orgCount.index}_startDate_${positionCount.index}"
                                                       name="${sectionType.name()}_org_${orgCount.index}_startDate_${positionCount.index}"
                                                       value="${DateUtil.format(position.getStartDate())}" size="40">
                                            </dd>
                                            <dt>Должность:</dt>
                                            <dd>
                                                <input type="text"
                                                       id="${sectionType.name()}_org_${orgCount.index}_positionTitle_${positionCount.index}"
                                                       name="${sectionType.name()}_org_${orgCount.index}_positionTitle_${positionCount.index}"
                                                       value="${position.getTitle()}" size="40">
                                            </dd>
                                            <dt>Обязанности:</dt>
                                            <dd>
                                                <textarea id="${sectionType.name()}_org_${orgCount.index}_duties_${positionCount.index}"
                                                          name="${sectionType.name()}_org_${orgCount.index}_duties_${positionCount.index}"
                                                          rows="5" cols="60">${position.getDescription()}
                                                </textarea>
                                            </dd>
                                        </dl>
                                        <p><input type="button"
                                                  id="${sectionType.name()}_org_${orgCount.index}_pos_${positionCount.index}_removeButton"
                                                  value="Удалить позицию" onclick="removePosition(this)"></p>
                                    </div>
                                </c:forEach>
                                <input type="button" id="${sectionType.name()}_org_${orgCount.index}_addPositionButton"
                                       value="Добавить позицию" onclick="addPosition(this.id);"><br>
                                <p><input type="button" id="${sectionType.name()}_org_${orgCount.index}_removeButton"
                                          value="Удалить организацию" onclick="removeOrg(this)"></p>
                            </div>
                        </c:forEach>
                    </c:if>
                    <div ID="${sectionType.name()}_orgItems">
                        <input type="button" ID="${sectionType.name()}_addOrg"
                               name="${sectionType.name()}_addOrg"
                               value="Добавить организацию"
                               onclick="addOrgSection('${sectionType.name()}')">
                    </div>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt><h4>${sectionType.title}</h4></dt>
                        <dd>
                            <c:if test="${sectionType.name().equals(\"OBJECTIVE\") || sectionType.name().equals(\"PERSONAL\")}">
                                <textarea name="${sectionType.name()}" cols="50"
                                          rows="5">${resume.getSection(sectionType)}</textarea>
                            </c:if>
                            <c:if test="${sectionType.name().equals(\"ACHIEVEMENTS\") || sectionType.name().equals(\"QUALIFICATIONS\")}">
                                <textarea name="${sectionType.name()}" cols="80"
                                          rows="10">${resume.getSection(sectionType)}</textarea>
                            </c:if>
                        </dd>
                    </dl>

                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="cancelModeBack()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>