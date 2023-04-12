package lab8.client.presentation.compose.ui.i18n

import java.util.*

interface Internationalization {
    fun getLocale(): Locale
    val loginForm: LoginForm
    val loginAppBar: LoginAppBar
    val homeAppBar: HomeScreenAppBar
    val dateFilterDialog: DateFilterDialog
    val numberFilterDialog: NumberFilterDialog
    val dataTable: DataTable
    val enumFilterDialog: EnumFilterDialog
    val vehicleDialog: VehicleDialog
    val stringDialog: StringRegexFilterDialog

    fun translateException(e: Exception): String

    interface LoginForm {
        val login: String
        val password: String
        val signIn: String
        val signUp: String
    }

    interface VehicleDialog: Dialog {
        val vehicleNameField: String
        val vehicleEnginePowerField: String
        val vehicleXCoordinateField: String
        val vehicleYCoordinateField: String
        val vehicleTypeField: String
        val fuelTypeField: String
    }

    interface EnumFilterDialog : Dialog

    interface Dialog {
        val confirm: String
        val cancel: String
    }

    interface NumberFilterDialog :
        Dialog {
        val valueShouldBeGreaterThan: String
        val valueShouldBeLessThan: String
    }

    interface StringRegexFilterDialog : Dialog {
        val stringShouldMatchRegex: String
    }


    interface DateFilterDialog :
        Dialog {
        val valueShouldBeGreaterThan: String
        val valueShouldBeLessThan: String
    }

    interface LoginAppBar: AppBar {
    }

    interface AppBar {
        val changeLanguage: String
    }

    interface HomeScreenAppBar: AppBar {
        val addButton: String
        val deleteButton: String
        val refreshButton: String
        val removeLowerButton: String
        val removeGreaterButton: String
        val replaceIfLowerButton: String
        val visualizeButton: String
    }

    interface DataTable {
        val tableHeaders: List<String>
    }
}
