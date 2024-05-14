package com.alparslanturk.bulbiletini.data.entities.enums

enum class DateFormatType(val format: String) {
    DATE_WITH_DOT("dd.MM.yyyy"),
    TIME_WITH_DOT("HH:mm"),
    DATE_TIME_WITH_DOT("dd.MM.yyyy HH:mm"),
    DATE_WITH_SLASH("dd/MM/yyyy"),
    DATE_DAY_SHORT_MONTH_YEAR_WITH_SPACES("dd MMM YYYY"),
    DATE_DAY_FULL_MONTH_YEAR_WITH_SPACES("dd MMMM yyyy"),
    DATE_MONTH_YEAR_WITH_SLASH("MM/yyyy"),
    DATE_ONLY_DAY("EEEE"),
    DATE_ONLY_MONTH("MM"),
    DATE_ONLY_YEAR("yyyy"),
    DATE_WITH_SPACES_SHORT("dd MMM yy"),
    DATE_TIME_WITH_SPACES_SHORT("dd MMM yyyy, HH:mm"),
    DATE_TIME_WITH_SLASH("dd/MM/yyyy HH:mm"),
    DATE_WITH_DASH("dd-MM-yyyy"),
    DATE_TIME_WITH_DASH("dd-MM-yyyy HH:mm"),
    DATE_TIME_YEAR_MONTH_DAY("yyyy:MM:dd HH:mm:SS"),
    DATE_TIME("yyyy-MM-dd'T'HH:mm:ss")
}