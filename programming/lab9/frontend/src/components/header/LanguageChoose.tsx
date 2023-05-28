import {Button, Dropdown, MenuProps} from "antd";
import {useTranslation} from "react-i18next";

const LanguageChoose = () => {
    const {t, i18n} = useTranslation()
    const options = [
        {
            language: "en-CA",
        },
        {
            language: "ru-RU",
        },
        {
            language: "tr-TR",
        },
        {
            language: "lv-LV"
        }
    ]

    const items: MenuProps['items'] = options.map((opt, ind) => {
        return {
            key: ind,
            label: (<a>{t(`languages.${opt.language}.localizedName`)}</a>),
            onClick: () => {
                i18n.changeLanguage(opt.language)
            }
        }
    })

    return (
        <div style={{marginRight: "20px", display: "inline"}}>
            <Dropdown menu={{items}}>
                <Button>{t('header.setLanguage')}</Button>
            </Dropdown>
        </div>
    )
}

export default LanguageChoose