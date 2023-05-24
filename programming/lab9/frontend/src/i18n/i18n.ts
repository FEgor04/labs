import i18n from "i18next"
import {initReactI18next} from "react-i18next";
import I18nextBrowserLanguageDetector from "i18next-browser-languagedetector";
import I18NextHttpBackend from "i18next-http-backend";

i18n
    .use(I18nextBrowserLanguageDetector)
    .use(I18NextHttpBackend)
    .use(initReactI18next)
    .init({
        fallbackLng: "en-CA",
        interpolation: {
            escapeValue: false
        }
    });

export default i18n