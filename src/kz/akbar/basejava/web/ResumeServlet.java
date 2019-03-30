package kz.akbar.basejava.web;

import kz.akbar.basejava.Config;
import kz.akbar.basejava.model.*;
import kz.akbar.basejava.storage.SqlStorage;
import kz.akbar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;


public class ResumeServlet extends HttpServlet {

    private SqlStorage sqlStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = (SqlStorage) Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        /*Map<String, String[]> parametersMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> parameterEntries = parametersMap.entrySet();
        for (Map.Entry entry : parameterEntries) {
            System.out.println("param key: " + entry.getKey());
            String[] paramValues = (String[]) entry.getValue();
            for (int i = 0; i < paramValues.length; i++) {
                System.out.println("param value" + i + ": " + paramValues[i]);
            }
        }*/
        String mode = request.getParameter("mode");
        if (mode.equals("cancel")) {
            response.sendRedirect("resume");
            return;
        }
        Resume resume = null;
        String fullName = request.getParameter("fullName");
        switch (mode) {
            case "create":
                resume = new Resume(fullName);
                break;
            case "edit":
                String uuid = request.getParameter("uuid");
                resume = sqlStorage.get(uuid);
                resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            Contact contact = new Contact(type.getTitle(), request.getParameter(type.name()));
            if (contact.getValue().trim().length() != 0) {
                resume.addContact(type, contact);
            } else {
                resume.getContacts().remove(type);
            }
        }
        Section section = null;
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String description = request.getParameter(type.name());
                    section = new TextSection(description);
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    String itemsString = request.getParameter(type.name());
                    ArrayList items = new ArrayList(Arrays.asList(itemsString.split("\n")));
                    section = new ListSection(items);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    Map<String, String[]> parametersMap = request.getParameterMap();
                    Set<Map.Entry<String, String[]>> paramEntries = parametersMap.entrySet();
                    String orgName, url, positionTitle, duties;
                    orgName = url = positionTitle = "";
                    LocalDate startDate, endDate;
                    startDate = endDate = null;
                    section = new OrganizationSection();
                    Organization organization = null;
                    Organization.Position position;
                    int orgNumber = -1;
                    int posNumber = -1;
                    for (Map.Entry paramEntry : paramEntries) {
                        String paramKey = (String) paramEntry.getKey();
                        if (!paramKey.contains("_")) {
                            continue;
                        }
                        String[] paramKeys = paramKey.split("_");
                        String[] paramValues;
                        String[] yearMonth;
                        if (paramKeys[0].equals(type.name())) {
                            if (mode.equals("create") || mode.equals("edit")) {
                                paramValues = (String[]) paramEntry.getValue();
                                switch (paramKeys[3]) {
                                    case "name":
                                        orgName = paramValues[0];
                                        break;
                                    case "url":
                                        url = paramValues[0];
                                        break;
                                    case "startDate":
                                        yearMonth = paramValues[0].split("-");
                                        if (!yearMonth[0].equals("")) {
                                            startDate = DateUtil.of(Integer.parseInt(yearMonth[0]), Month.of(Integer.parseInt(yearMonth[1])));
                                        }
                                        break;
                                    case "endDate":
                                        yearMonth = paramValues[0].split("-");
                                        if (!yearMonth[0].equals("")) {
                                            endDate = DateUtil.of(Integer.parseInt(yearMonth[0]), Month.of(Integer.parseInt(yearMonth[1])));
                                        }
                                        break;
                                    case "positionTitle":
                                        positionTitle = paramValues[0];
                                        break;
                                    case "duties":
                                        duties = paramValues[0];
                                        //if organization more than one position in org
                                        if (orgName.length() == 0) {
                                            continue;
                                        }
                                        position = new Organization.Position(startDate, endDate, positionTitle, duties);
                                        if (orgNumber != Integer.parseInt(paramKeys[2])) {
                                            organization = new Organization(orgName, url);
                                            ((OrganizationSection) section).addOrganization(organization);
                                            orgNumber = Integer.parseInt(paramKeys[2]);
                                        }
                                        if (posNumber != Integer.parseInt(paramKeys[4])) {
                                            List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                                            for (Organization org : organizations) {
                                                if (orgName.equals(org.getNameLink().getTitle())) {
                                                    organization = org;
                                                }
                                            }
                                            organization.addPosition(position);
                                            posNumber = Integer.parseInt(paramKeys[4]);
                                        }
                                }
                            }
                        }
                    }
            }
            resume.addSection(type, section);
        }
        switch (mode) {
            case "create":
                sqlStorage.save(resume);
                break;
            case "edit":
                sqlStorage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = sqlStorage.get(uuid);
                break;
            case "create":
                resume = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/createEdit.jsp"))
                .forward(request, response);
    }
}
