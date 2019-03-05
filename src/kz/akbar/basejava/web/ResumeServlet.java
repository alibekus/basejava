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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ResumeServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            System.out.println("Postgres driver loading...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Postgres driver has been loaded successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Postgres driver class loading error: " + e.getCause());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        SqlStorage sqlStorage = (SqlStorage) Config.getInstance().getStorage();
        pw.print("<!DOCTYPE html>");
        pw.println("<html>");
        pw.print("<head>");
        pw.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        pw.print("<title>");
        pw.print("Print resumes");
        pw.print("</title>");
        pw.print("</head>");
        pw.println("<body>");
        List<Resume> resumes = sqlStorage.getAllSorted();
        for (Resume resume : resumes) {
            printResume(pw, resume);
        }
        pw.print("</body>");
        pw.print("</html>");
    }

    private void printResume(PrintWriter pw, Resume resume) {
        Map<ContactType, Contact> contacts = resume.getContacts();
        Map<SectionType, Section> sections = resume.getSections();
        pw.println("<H2>Resume: " + resume.getUuid() + "</H2>");
        pw.write("<table border = 2>");
        pw.write("<tr>");
        pw.println("<td><b>Имя: </b></td><td>" + resume.getFullName() + "</td>");
        pw.write("</tr>");
        for (Map.Entry<ContactType, Contact> contactEntry : contacts.entrySet()) {
            pw.write("<tr>");
            pw.print("<td><b>" + contactEntry.getKey().getTitle() + "</b></td>");
            pw.print("<td>" + contactEntry.getValue().getValue() + "</td>");
            pw.write("</tr>");
        }
        for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
            pw.write("<tr>");
            pw.print("<td><b>" + sectionEntry.getKey().getTitle() + "</b></td>");
            pw.print("<td>");
            Arrays.stream(sectionEntry.getValue().toString().split("\n"))
                    .forEach(item -> pw.println("- " + item + "<br>"));
            pw.print("</td>");
            pw.write("</tr>");
        }
        pw.write("</table>");
    }
}
