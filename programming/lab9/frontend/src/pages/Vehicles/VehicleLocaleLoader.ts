import {TableLocale} from "antd/es/table/interface";
import i18n from "../../i18n/i18n.ts";

export class VehicleLocaleLoader {
    private t = i18n.t
    loadVehicleTableLocale(): TableLocale {
        return {
            triggerAsc: this.t("table.triggerAsc"),
            triggerDesc: this.t("table.triggerDesc"),
            cancelSort: this.t('table.cancelSort')
        }
    }
}