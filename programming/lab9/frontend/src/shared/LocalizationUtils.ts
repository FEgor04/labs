import i18n from "../i18n/i18n.ts";

export const getCurrentLocale = (): Intl.Locale => {
    return new Intl.Locale(i18n.language)
}

export const getDateTimeFormatter = (): Intl.DateTimeFormat => {
    return new Intl.DateTimeFormat(i18n.language)
}

export const getNumberFormatter = (): Intl.NumberFormat => {
    return new Intl.NumberFormat(i18n.language)
}
