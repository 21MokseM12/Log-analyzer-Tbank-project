package backend.academy.analyzer.enums;

public enum ReportTopic {

    GENERAL_INFO_LABEL("Общая информация"),

    METRIC("Метрика"),

    MEANING("Значение"),

    INPUT_RESOURCES_NAME("Файл(-ы)"),

    BEGIN_DATE("Начальная дата"),

    END_DATE("Конечная дата"),

    LOG_COUNT("Количество запросов"),

    AVG_SERVER_RESPONSE("Средний размер ответа"),

    PERCENT_SERVER_RESPONSE("95p размера ответа"),

    GENERAL_MOST_POPULAR_RESOURCES_LABEL("Запрашиваемые ресурсы"),

    RESOURCE_NAME("Ресурс"),

    GENERAL_MOST_POPULAR_STATUS_CODES_LABEL("Коды ответа"),

    STATUS_CODE_VALUE("Код"),

    STATUS_CODE_NAME("Имя"),

    GENERAL_ACTIVE_USER_IPS("Самые часто встречаемые IP адреса пользователей"),

    USER_IP_LABEL("IP адрес"),

    GENERAL_MOST_POPULAR_USER_AGENTS("Самые часто встречаемые user-agent"),

    USER_AGENT_LABEL("user-agent"),

    COUNT("Количество");

    private final String topic;

    ReportTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return this.topic;
    }
}
