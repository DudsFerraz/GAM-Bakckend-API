package br.org.gam.api.rbac.permission.domain;

import lombok.Getter;

@Getter
public enum PermissionEnum {
    MEMBER_GET(Code.MEMBER_GET, "View members", "Allows viewing active members"),
    MEMBER_SEARCH(Code.MEMBER_SEARCH, "Search members", "Allows searching members"),
    MEMBER_ACTIVATION(Code.MEMBER_ACTIVATION, "Activate members", "Allows activating and deactivating members"),
    MEMBER_GET_NON_ACTIVE(Code.MEMBER_GET_NON_ACTIVE, "View inactive members", "Allows viewing non-active members"),
    MEMBER_MANAGE(Code.MEMBER_MANAGE, "Manage members", "Allows managing members"),

    ACCOUNT_GET(Code.ACCOUNT_GET, "View accounts", "Allows viewing accounts"),
    ACCOUNT_SEARCH(Code.ACCOUNT_SEARCH, "Search accounts", "Allows searching accounts"),

    EVENT_CREATE(Code.EVENT_CREATE, "Create events", "Allows creating events"),
    EVENT_SEARCH(Code.EVENT_SEARCH, "Search events", "Allows searching events"),
    EVENT_GET_PRESENCES(Code.EVENT_GET_PRESENCES, "View event presences", "Allows viewing presences for an event"),
    EVENT_GET_S(Code.EVENT_GET_S, "View restricted events", "Allows viewing events that require special access"),
    EVENT_MANAGE(Code.EVENT_MANAGE, "Manage events", "Allows managing events"),

    PRESENCES_SEARCH(Code.PRESENCES_SEARCH, "Search presences", "Allows searching presences");

    private final String code;
    private final String label;
    private final String description;

    PermissionEnum(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    public static class Code {
        public static final String MEMBER_GET = "MEMBER_GET";
        public static final String MEMBER_SEARCH = "MEMBER_SEARCH";
        public static final String MEMBER_ACTIVATION = "MEMBER_ACTIVATION";
        public static final String MEMBER_GET_NON_ACTIVE = "MEMBER_GET_NON_ACTIVE";
        public static final String MEMBER_MANAGE = "MEMBER_MANAGE";

        public static final String ACCOUNT_GET = "ACCOUNT_GET";
        public static final String ACCOUNT_SEARCH = "ACCOUNT_SEARCH";

        public static final String EVENT_CREATE = "EVENT_CREATE";
        public static final String EVENT_SEARCH = "EVENT_SEARCH";
        public static final String EVENT_GET_PRESENCES = "EVENT_GET_PRESENCES";
        public static final String EVENT_GET_S = "EVENT_GET_S";
        public static final String EVENT_MANAGE = "EVENT_MANAGE";

        public static final String PRESENCES_SEARCH = "PRESENCES_SEARCH";
    }
}
