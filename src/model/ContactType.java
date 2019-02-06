package model;

public enum ContactType {
    PHONENUMBER("Номер телефона"),
    EMAIL("Эл.почта"),
    HOMEPAGE("Домашняя страница"),
    LINKEDIN("Линкедин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стековерфло"),
    SKYPE("Скайп");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
