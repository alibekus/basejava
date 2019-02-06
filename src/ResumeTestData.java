import model.*;

import java.io.IOException;
import java.util.*;

class ResumeTestData {

    private static final String FULLNAME = "Григорий Кислин";
    private static final String PHONENUMBER = "+7(921) 855-0482";
    private static final String SKYPE = "grigory.kislin";
    private static final String EMAIL = "gkislin@yandex.ru";
    private static final String LINKEDIN = "https://www.linkedin.com/in/gkislin";
    private static final String GITHUB = "https://github.com/gkislin";
    private static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    private static final String HOMEPAGE = "http://gkislin.ru/";
    private static final String personalInfo = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
    private static final String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    private static final List<String> companyNames = new ArrayList<>();
    private static final List<String> jobPositions = new ArrayList<>();
    private static final List<Calendar> startJobDates = new ArrayList<>();
    private static final List<Calendar> endJobDates = new ArrayList<>();
    private static final List<String> jobDuties = new ArrayList<>();
    private static final List<Calendar> startEduDates = new ArrayList<>();
    private static final List<Calendar> endEduDates = new ArrayList<>();
    private static final List<String> eduDuties = new ArrayList<>();
    private static final List<String> contactItems = new ArrayList<>();
    private static final List<String> achievements = new ArrayList<>();
    private static final List<String> qualifications = new ArrayList<>();
    private static final List<String> eduNames = new ArrayList<>();
    private static final List<Organization> eduOrgs = new ArrayList<>();

    static {
        contactItems.add(PHONENUMBER);
        contactItems.add(EMAIL);
        contactItems.add(HOMEPAGE);
        contactItems.add(LINKEDIN);
        contactItems.add(GITHUB);
        contactItems.add(STACKOVERFLOW);
        contactItems.add(SKYPE);

        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)");
        qualifications.add("Python: Django");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");

        companyNames.add("Java Online Projects");
        companyNames.add("Wrike");
        companyNames.add("RIT Center");
        companyNames.add("Luxoft");
        companyNames.add("Yota");
        companyNames.add("Enkata");
        companyNames.add("Siemens AG");
        companyNames.add("Alcatel");

        jobPositions.add("Автор проекта");
        jobPositions.add("Старший разработчик (backend)");
        jobPositions.add("Java архитектор");
        jobPositions.add("Ведущий программист");
        jobPositions.add("Ведущий специалист");
        jobPositions.add("Разработчик ПО");
        jobPositions.add("Разработчик ПО");
        jobPositions.add("Инженер по аппаратному и программному тестированию");

        Calendar date;
        date = Calendar.getInstance();
        date.set(2013, Calendar.OCTOBER, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2014, Calendar.OCTOBER, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2012, Calendar.APRIL, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2010, Calendar.DECEMBER, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2008, Calendar.JUNE, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2007, Calendar.MARCH, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2005, Calendar.JANUARY, 1);
        startJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(1997, Calendar.SEPTEMBER, 1);
        startJobDates.add(date);

        date = (Calendar) date.clone();
        date.set(9999, Calendar.DECEMBER, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2016, Calendar.JANUARY, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2014, Calendar.OCTOBER, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2012, Calendar.APRIL, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2010, Calendar.DECEMBER, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2008, Calendar.JUNE, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2007, Calendar.FEBRUARY, 1);
        endJobDates.add(date);
        date = (Calendar) date.clone();
        date.set(2005, Calendar.JANUARY, 1);
        endJobDates.add(date);

        jobDuties.add("Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobDuties.add("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        jobDuties.add("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins миграция базы (кастомизация Flyway конфигурирование системы (pgBoucer, Nginx AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobDuties.add("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT Highstock, Commet, HTML5.");
        jobDuties.add("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobDuties.add("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        jobDuties.add("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        jobDuties.add("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        eduNames.add("Coursera");
        eduNames.add("Luxoft");
        eduNames.add("Siemens AG");
        eduNames.add("Alcatel");
        eduNames.add("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        eduNames.add("Заочная физико-техническая школа при МФТИ");

        date = (Calendar) date.clone();
        date.set(2013, Calendar.MARCH, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(2011, Calendar.MARCH, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(2005, Calendar.JANUARY, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1997, Calendar.SEPTEMBER, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1993, Calendar.SEPTEMBER, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1987, Calendar.SEPTEMBER, 1);
        startEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1984, Calendar.SEPTEMBER, 1);
        startEduDates.add(date);

        date = (Calendar) date.clone();
        date.set(2013, Calendar.MAY, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(2011, Calendar.APRIL, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(2005, Calendar.APRIL, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1998, Calendar.MARCH, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1996, Calendar.JULY, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1993, Calendar.JULY, 1);
        endEduDates.add(date);
        date = (Calendar) date.clone();
        date.set(1987, Calendar.JUNE, 1);
        endEduDates.add(date);

        eduDuties.add("\"Functional Programming Principles in Scala\" by Martin Odersky");
        eduDuties.add("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML\"");
        eduDuties.add("3 месяца обучения мобильным IN сетям (Берлин)");
        eduDuties.add("6 месяцев обучения цифровым телефонным сетям (Москва)");
        eduDuties.add("Аспирантура (программист С, С++)");
        eduDuties.add("Инженер (программист Fortran, C)");
        eduDuties.add("Закончил с отличием");

        Organization edu1 = new Organization(eduNames.get(0));
        edu1.setDatesPositionsDuties(startEduDates.get(0), endEduDates.get(0), "", eduDuties.get(0));
        eduOrgs.add(edu1);
        Organization edu2 = new Organization(eduNames.get(1));
        edu2.setDatesPositionsDuties(startEduDates.get(1), endEduDates.get(1), "", eduDuties.get(1));
        eduOrgs.add(edu2);
        Organization edu3 = new Organization(eduNames.get(2));
        edu3.setDatesPositionsDuties(startEduDates.get(2), endEduDates.get(2), "", eduDuties.get(2));
        eduOrgs.add(edu3);
        Organization edu4 = new Organization(eduNames.get(3));
        edu4.setDatesPositionsDuties(startEduDates.get(3), endEduDates.get(3), "", eduDuties.get(3));
        eduOrgs.add(edu4);
        Organization edu5 = new Organization(eduNames.get(4));
        edu5.setDatesPositionsDuties(startEduDates.get(4), endEduDates.get(4), "", eduDuties.get(4));
        edu5.setDatesPositionsDuties(startEduDates.get(5), endEduDates.get(5), "", eduDuties.get(5));
        eduOrgs.add(edu5);
        Organization edu6 = new Organization(eduNames.get(5));
        edu6.setDatesPositionsDuties(startEduDates.get(6), endEduDates.get(6), "", eduDuties.get(6));
        eduOrgs.add(edu6);
    }

    private static void fillOrganizations(int count, SectionType sectionType, List<String> companyNames,
                                          List<String> positions, List<String> duties, Resume resume) {
        OrganizationSection organizationSection = new OrganizationSection();
        organizationSection.setType(sectionType);
        for (int i = 0; i < count; i++) {
            if (sectionType.equals(SectionType.EXPERIENCE)) {
                Organization organization = new Organization(companyNames.get(i));
                organization.setDatesPositionsDuties(startJobDates.get(i), endJobDates.get(i), positions.get(i),
                        duties.get(i));
                organizationSection.addOrganization(organization);
            } else {
                organizationSection.addOrganization(eduOrgs.get(i));
            }
        }
        resume.addSection(organizationSection);
    }

    public static void main(String[] args) {
        SimpleSection simpleSection;
        ListSection listSection;
        Resume resume = new Resume(FULLNAME);
        System.out.println("=================Entering sections===================");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println("Section: " + sectionType.getTitle());
            switch (sectionType) {
                case OBJECTIVE:
                    simpleSection = new SimpleSection(sectionType, objective);
                    resume.addSection(simpleSection);
                    break;
                case PERSONAL:
                    simpleSection = new SimpleSection(sectionType, personalInfo);
                    resume.addSection(simpleSection);
                    break;
                case ACHIEVEMENT:
                    listSection = new ListSection(sectionType, achievements);
                    resume.addSection(listSection);
                    break;
                case QUALIFICATION:
                    listSection = new ListSection(sectionType, qualifications);
                    resume.addSection(listSection);
                    break;
                case EXPERIENCE:
                    fillOrganizations(companyNames.size(), sectionType, companyNames, jobPositions, jobDuties, resume);
                    break;
                case EDUCATION:
                    fillOrganizations(eduNames.size(), sectionType, eduNames, jobPositions, eduDuties, resume);
                    break;
            }
        }
        //----------Contacts entering---------------------
        System.out.println("============================Entering contacts============================");
        Iterator contactIterator = contactItems.listIterator();
        for (ContactType type : ContactType.values()) {
            if (contactIterator.hasNext()) {
                System.out.println("Contact " + type.getTitle());
                Contact contact = new Contact(type, (String) contactIterator.next());
                resume.addContact(contact);
            }
        }
        //------------Resume's info printing--------------
        System.out.println("==============================Print resume==============================");
        System.out.println(resume.toString());
        resume.printContacts();
        resume.printSections();
    }
}

