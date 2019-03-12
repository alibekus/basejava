package kz.akbar.basejava.model;

public enum ContactType {
    PHONENUMBER("Номер телефона"),
    EMAIL("Эл.почта") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    LINKEDIN("Линкедин") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Гитхаб") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Стэковерфло") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
