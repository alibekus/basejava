package kz.akbar.basejava.storage;

import kz.akbar.basejava.model.*;
import kz.akbar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ResumeTestData {

    private static ResumeTestData instance;

    private final String phoneNumber = "+7(921) 855-0482";
    private final String skype = "grigory.kislin";
    private final String email = "gkislin@yandex.ru";
    private final String linkedIn = "https://www.linkedin.com/in/gkislin";
    private final String gitHub = "https://gitHub.com/gkislin";
    private final String stackOverflow = "https://stackoverflow.com/users/548473";
    private final String homePage = "http://gkislin.ru/";
    private String personalInfo;
    private String objective;
    private List<String> companyNames = new ArrayList<>();
    private List<String> jobPositions = new ArrayList<>();
    private List<String> jobDuties = new ArrayList<>();
    private List<LocalDate> startJobDates = new ArrayList<>();
    private List<LocalDate> endJobDates = new ArrayList<>();
    private List<String> eduNames = new ArrayList<>();
    private List<String> eduPositions = new ArrayList<>();
    private List<String> eduDuties = new ArrayList<>();
    private List<LocalDate> startEduDates = new ArrayList<>();
    private List<LocalDate> endEduDates = new ArrayList<>();
    private List<String> contactItems = new ArrayList<>();
    private List<String> achievements = new ArrayList<>();
    private List<String> qualifications = new ArrayList<>();

    static Resume getResumeInstance(String uuid, String fullName) {
        if (instance == null) {
            instance = new ResumeTestData();
        }
        return instance.fillResume(uuid, fullName);
    }

    private void fillContacts() {
        contactItems.add(phoneNumber);
        contactItems.add(email);
        contactItems.add(homePage);
        contactItems.add(linkedIn);
        contactItems.add(gitHub);
        contactItems.add(stackOverflow);
        contactItems.add(skype);
    }


    private void fillObjective() {
        this.objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    }

    private void fillPersonalInfo() {
        this.personalInfo = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.";
    }

    private void fillAchievements() {
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное " +
                "взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы " +
                "по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, " +
                "Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
    }

    private void fillQualifications() {
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2.");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce.");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle.");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB.");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy.");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts.");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js.");
    }

    private void fillCompanyNames() {
        companyNames.add("Java Online Projects");
        companyNames.add("Wrike");
        companyNames.add("RIT Center");
        companyNames.add("Luxoft");
        companyNames.add("Yota");
        companyNames.add("Enkata");
        companyNames.add("Siemens AG");
        companyNames.add("Alcatel");
    }

    private void fillJobPositions() {
        jobPositions.add("Автор проекта");
        jobPositions.add("Старший разработчик (backend)");
        jobPositions.add("Java архитектор");
        jobPositions.add("Ведущий программист");
        jobPositions.add("Ведущий специалист");
        jobPositions.add("Разработчик ПО");
        jobPositions.add("Разработчик ПО");
        jobPositions.add("Инженер по аппаратному и программному тестированию");
    }

    private void fillJobStartDates() {
        startJobDates.add(DateUtil.of(2013, Month.OCTOBER));
        startJobDates.add(DateUtil.of(2014, Month.OCTOBER));
        startJobDates.add(DateUtil.of(2012, Month.APRIL));
        startJobDates.add(DateUtil.of(2010, Month.DECEMBER));
        startJobDates.add(DateUtil.of(2008, Month.JUNE));
        startJobDates.add(DateUtil.of(2007, Month.MARCH));
        startJobDates.add(DateUtil.of(2005, Month.JANUARY));
        startJobDates.add(DateUtil.of(1997, Month.SEPTEMBER));
    }

    private void fillJobEndDates() {
        endJobDates.add(DateUtil.of(9999, Month.DECEMBER));
        endJobDates.add(DateUtil.of(2016, Month.JANUARY));
        endJobDates.add(DateUtil.of(2014, Month.OCTOBER));
        endJobDates.add(DateUtil.of(2012, Month.APRIL));
        endJobDates.add(DateUtil.of(2010, Month.DECEMBER));
        endJobDates.add(DateUtil.of(2008, Month.JUNE));
        endJobDates.add(DateUtil.of(2007, Month.FEBRUARY));
        endJobDates.add(DateUtil.of(2005, Month.JANUARY));
    }

    private void fillJobDuties() {
        jobDuties.add("Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobDuties.add("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
                "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                "OAuth2, JWT SSO.");
        jobDuties.add("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                "ведение CI (Jenkins миграция базы (кастомизация Flyway конфигурирование системы (pgBoucer, " +
                "Nginx AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, " +
                "BPMN2, 1C (WebServices сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + " +
                "plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python.");
        jobDuties.add("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, " +
                "Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT Highstock, Commet, HTML5.");
        jobDuties.add("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, " +
                "OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS).");
        jobDuties.add("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей " +
                "кластерного J2EE приложения (OLAP, Data mining).");
        jobDuties.add("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                "IN платформе Siemens @vantage (Java, Unix).");
        jobDuties.add("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
    }

    private void fillEduOrgs() {
        eduNames.add("Coursera");
        eduNames.add("Luxoft");
        eduNames.add("Siemens AG");
        eduNames.add("Alcatel");
        eduNames.add("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                "механики и оптики");
        eduNames.add("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                "механики и оптики");
        eduNames.add("Заочная физико-техническая школа при МФТИ");
    }

    private void fillEduPositions() {
        eduPositions.add("Слушатель");
        eduPositions.add("Слушатель");
        eduPositions.add("Практикант");
        eduPositions.add("Практикант");
        eduPositions.add("Аспирант");
        eduPositions.add("Студент");
        eduPositions.add("Школьник");
    }


    private void fillEduStartDates() {
        startEduDates.add(DateUtil.of(2013, Month.MARCH));
        startEduDates.add(DateUtil.of(2011, Month.MARCH));
        startEduDates.add(DateUtil.of(2005, Month.JANUARY));
        startEduDates.add(DateUtil.of(1997, Month.SEPTEMBER));
        startEduDates.add(DateUtil.of(1993, Month.SEPTEMBER));
        startEduDates.add(DateUtil.of(1987, Month.SEPTEMBER));
        startEduDates.add(DateUtil.of(1984, Month.SEPTEMBER));
    }

    private void fillEduEndDates() {
        endEduDates.add(DateUtil.of(2013, Month.MAY));
        endEduDates.add(DateUtil.of(2011, Month.APRIL));
        endEduDates.add(DateUtil.of(2005, Month.APRIL));
        endEduDates.add(DateUtil.of(1998, Month.MARCH));
        endEduDates.add(DateUtil.of(1996, Month.JULY));
        endEduDates.add(DateUtil.of(1993, Month.JULY));
        endEduDates.add(DateUtil.of(1987, Month.JUNE));
    }

    private void fillEduDuties() {
        eduDuties.add("Курс \"Functional Programming Principles in Scala\" by Martin Odersky\"");
        eduDuties.add("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML\"");
        eduDuties.add("3 месяца обучения мобильным IN сетям (Берлин)");
        eduDuties.add("6 месяцев обучения цифровым телефонным сетям (Москва)");
        eduDuties.add("Аспирантура (программист С, С++)");
        eduDuties.add("Инженер (программист Fortran, C)");
        eduDuties.add("Закончил с отличием");
    }


    private Resume fillResume(String uuid, String fullName) {
        SimpleSection simpleSection;
        ListSection listSection;
        Resume resume = new Resume(uuid, fullName);
        fillContacts();
        fillObjective();
        fillPersonalInfo();
        fillAchievements();
        fillQualifications();
        fillCompanyNames();
        fillJobPositions();
        fillJobDuties();
        fillJobStartDates();
        fillJobEndDates();
        fillEduOrgs();
        fillEduPositions();
        fillEduDuties();
        fillEduStartDates();
        fillEduEndDates();
        System.out.println("=================Entering sections===================");
        for (SectionType sectionType : SectionType.values()) {
            System.out.println("Section: " + sectionType.getTitle());
            switch (sectionType) {
                case OBJECTIVE:
                    simpleSection = new SimpleSection(objective);
                    resume.addSection(sectionType, simpleSection);
                    break;
                case PERSONAL:
                    simpleSection = new SimpleSection(personalInfo);
                    resume.addSection(sectionType, simpleSection);
                    break;
                case ACHIEVEMENT:
                    listSection = new ListSection(achievements);
                    resume.addSection(sectionType, listSection);
                    break;
                case QUALIFICATION:
                    listSection = new ListSection(qualifications);
                    resume.addSection(sectionType, listSection);
                    break;
                case EXPERIENCE:
                    fillOrganizations(companyNames.size(), sectionType, companyNames, jobPositions, jobDuties,
                            startJobDates, endJobDates, resume);
                    break;
                case EDUCATION:
                    fillOrganizations(eduNames.size(), sectionType, eduNames, eduPositions, eduDuties,
                            startEduDates, endEduDates, resume);
                    break;
            }
        }
        //----------Contacts entering---------------------
        System.out.println("============================Entering contacts============================");
        Iterator contactIterator = contactItems.listIterator();
        for (ContactType type : ContactType.values()) {
            if (contactIterator.hasNext()) {
                System.out.println("Contact " + type.getTitle());
                Contact contact = new Contact(type.getTitle(), (String) contactIterator.next());
                resume.addContact(type, contact);
            }
        }
        return resume;
    }

    private void fillOrganizations(int count, SectionType sectionType, List<String> companyNames,
                                   List<String> positions, List<String> duties, List<LocalDate> startDates,
                                   List<LocalDate> endDates, Resume resume) {
        OrganizationSection organizationSection = new OrganizationSection();
        Organization organization = null;
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                organization = new Organization(companyNames.get(i), "",
                        new Organization.Position(startDates.get(i), endDates.get(i), positions.get(i), duties.get(i)));
                organizationSection.addOrganization(organization);
            } else {
                if (companyNames.get(i).equals(companyNames.get(i - 1))) {
                    Organization.Position position = new Organization.Position(startDates.get(i), endDates.get(i),
                            positions.get(i), duties.get(i));
                    organization.addPosition(position);
                } else {
                    organization = new Organization(companyNames.get(i), "",
                            new Organization.Position(startDates.get(i), endDates.get(i), positions.get(i), duties.get(i)));
                    organizationSection.addOrganization(organization);
                }
            }

        }
        resume.addSection(sectionType, organizationSection);
    }

    private void printContacts(Resume resume) {
        System.out.println("- - - - - - - - - - - - - Контакты- - - - - - - - - - - - - ");
        for (ContactType type : ContactType.values()) {
            System.out.println(resume.getContacts().get(type));
        }
    }

    private void printSections(Resume resume) {
        for (SectionType type : SectionType.values()) {
            System.out.println("- - - - - - - - - - - - - " + type.getTitle() + "- - - - - - - - - - - - -");
            System.out.println(resume.getSections().get(type));
        }
    }


    public static void main(String[] args) {
        ResumeTestData dataTest = new ResumeTestData();
        Resume resume = dataTest.getResumeInstance("uuid1", "Григорий Кислин");
        //------------Resume's info printing--------------
        System.out.println("==============================Print resume==============================");
        System.out.println(resume.toString());
        dataTest.printContacts(resume);
        dataTest.printSections(resume);
    }

}

