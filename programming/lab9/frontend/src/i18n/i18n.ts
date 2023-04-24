import i18n from "i18next"
import {initReactI18next} from "react-i18next";

i18n.use(initReactI18next)
    .init({
        resources: {
            en: {
                translation: {
                    "signIn": "Sign In",
                    "username": "User Name",
                    "password": "Password",
                    "signInDontHaveAnAccount": "Don't have an account yet?",
                    "signUp": "Sign Up",
                    "usernameMessage": "Please enter your username",
                    "passwordMessage": "Please enter your password",
                    "vehiclesPage": "Vehicles",
                    "usersPage": "Users",
                    "tableIdColumn": "ID",
                    "tableNameColumn": "Name",
                    "tableXColumn": "X",
                    "tableYColumn": "Y",
                    "tableCreationDateColumn": "Creation Date",
                    "tableEnginePowerColumn": "Engine Power",
                    "tableVehicleTypeColumn": "Vehicle Type",
                    "tableFuelTypeColumn": "Fuel Type",
                    "tableCreatorColumn": "Creator",
                    "tableActionColumn": "Action",
                }
            }
        },
        fallbackLng: "en",
        interpolation: {
            escapeValue: false
        }
    });

export default i18n