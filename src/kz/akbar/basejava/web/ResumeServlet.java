package kz.akbar.basejava.web;

import kz.akbar.basejava.Config;
import kz.akbar.basejava.model.*;
import kz.akbar.basejava.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


public class ResumeServlet extends HttpServlet {

    SqlStorage sqlStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = (SqlStorage) Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mode = request.getParameter("mode");
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
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    Set<Map.Entry<String, String[]>> paramEntries = parameterMap.entrySet();
                    section = new OrganizationSection();
                    Organization organization = null;
                    Organization.Position position;
                    for (Map.Entry paramEntry : paramEntries) {
                        String paramKey = (String) paramEntry.getKey();
                        String[] paramKeys = paramKey.split("=");
                        if (paramKeys[0].equals(type.name())) {
                            switch (mode) {
                                case "create":
                                    String orgName = request.getParameter(type.name().concat("=orgName"));
                                    String url = request.getParameter(type.name().concat("=url"));
                                    LocalDate startDate = LocalDate.parse(request.getParameter(type.name().concat("=startDate")));
                                    LocalDate endDate = LocalDate.parse(request.getParameter(type.name().concat("=endDate")));
                                    String positionTitle = request.getParameter(type.name().concat("=positionTitle"));
                                    String duties = request.getParameter(type.name().concat("=duties"));
                                    position = new Organization.Position(startDate, endDate, positionTitle, duties);
                                    organization = new Organization(orgName, url, position);
                                    break;
                                case "edit":
                                    String[] paramValues = (String[]) paramEntry.getValue();
                                    organization = new Organization(paramKeys[1]);
                                    organization.addUrl(paramValues[1]);
                                    for (int i = 2; i < paramValues.length; i += 4) {
                                        position = new Organization.Position();
                                        position.setStartDate(LocalDate.parse(paramValues[i]));
                                        position.setEndDate(LocalDate.parse(paramValues[i + 1]));
                                        position.setPositionTitle(paramValues[i + 2]);
                                        position.setDuties(paramValues[i + 3]);
                                        organization.addPosition(position);
                                    }
                                    ((OrganizationSection) section).addOrganization(organization);
                            }
                        }
                    }
                    ((OrganizationSection) section).addOrganization(organization);
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
                request.getRequestDispatcher(("/WEB-INF/jsp/create.jsp"))
                        .forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }
}
