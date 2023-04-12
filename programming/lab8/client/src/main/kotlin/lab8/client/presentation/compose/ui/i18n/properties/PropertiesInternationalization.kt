package lab8.client.presentation.compose.ui.i18n.properties

import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.exceptions.ServerException
import java.util.*

class PropertiesInternationalization(val props: Properties) : Internationalization {
    private val locale = Locale(
        props.getProperty("language", Locale.CHINESE.language),
        props.getProperty("country", Locale.CHINESE.country)
    )

    override fun getLocale(): Locale = locale


    override val loginForm: Internationalization.LoginForm = object : Internationalization.LoginForm {
        override val login: String = props.getAutoDefault("loginScreen.login")
        override val password: String = props.getAutoDefault("loginScreen.password")
        override val signIn: String = props.getAutoDefault("loginScreen.signIn")
        override val signUp: String = props.getAutoDefault("loginScreen.signUp")

    }
    override val loginAppBar: Internationalization.LoginAppBar = object : AppBar(), Internationalization.LoginAppBar {

    }
    override val homeAppBar: Internationalization.HomeScreenAppBar =
        object : AppBar(), Internationalization.HomeScreenAppBar {
            override val addButton: String = props.getAutoDefault("appBar.home.add")
            override val deleteButton: String = props.getAutoDefault("appBar.home.delete")
            override val refreshButton: String = props.getAutoDefault("appBar.home.refresh")
            override val removeLowerButton: String = props.getAutoDefault("appBar.home.removeLower")
            override val removeGreaterButton: String = props.getAutoDefault("appBar.home.removeGreater")
            override val replaceIfLowerButton: String = props.getAutoDefault("appBar.home.replaceIfLower")
            override val visualizeButton: String = props.getAutoDefault("appBar.home.visualize")
        }
    override val dateFilterDialog: Internationalization.DateFilterDialog
        get() = object : PropertiesDialog(), Internationalization.DateFilterDialog {
            override val valueShouldBeGreaterThan: String =
                props.getAutoDefault("dialog.dateFilterDialog.valueShouldBeGreaterThan")
            override val valueShouldBeLessThan: String =
                props.getAutoDefault("dialog.dateFilterDialog.valueShouldBeLessThan")
        }
    override val numberFilterDialog: Internationalization.NumberFilterDialog
        get() = object: PropertiesDialog(), Internationalization.NumberFilterDialog {
            override val valueShouldBeGreaterThan: String = props.getAutoDefault("dialog.numberFilterDialog.valueShouldBeGreaterThan")
            override val valueShouldBeLessThan: String = props.getAutoDefault("dialog.numberFilterDialog.valueShouldBeLessThan")

        }
    override val dataTable: Internationalization.DataTable = object : Internationalization.DataTable {
        override val tableHeaders: List<String> = props
            .getProperty(
                "home.dataTable.headers",
                "id,name,coordinates,creationDate,enginePower,vehicleType,fuelType,author"
            )
            .split(",")

    }
    override val enumFilterDialog: Internationalization.EnumFilterDialog = object : PropertiesDialog(),
        Internationalization.EnumFilterDialog {

    }
    override val vehicleDialog: Internationalization.VehicleDialog =
        object : PropertiesDialog(), Internationalization.VehicleDialog {
            override val vehicleNameField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleNameField")
            override val vehicleEnginePowerField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleEnginePowerField")
            override val vehicleXCoordinateField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleXCoordinateField")
            override val vehicleYCoordinateField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleYCoordinateField")
            override val vehicleTypeField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleVehicleTypeField")
            override val fuelTypeField: String =
                props.getAutoDefault("dialog.addVehicleDialog.vehicleFuelTypeField")
        }
    override val stringDialog: Internationalization.StringRegexFilterDialog = object : PropertiesDialog(),
        Internationalization.StringRegexFilterDialog {
        override val stringShouldMatchRegex: String =
            props.getAutoDefault("dialog.stringFilterDialog.stringShouldMatchRegex")

    }

    override fun translateException(e: Exception): String {
        if(e is ServerException) {
            return when(e) {
                is ServerException.AcknowledgePseudoException -> props.getAutoDefault("exceptions.ackException")
                is ServerException.BadOwnerException -> props.getAutoDefault("exceptions.badOwnerException")
                is ServerException.UserAlreadyExistsException -> props.getAutoDefault("exceptions.userAlreadyExists")
            }
        }
        else {
            return e.toString()
        }
    }

    open inner class PropertiesDialog() : Internationalization.Dialog {
        override val confirm: String = props.getAutoDefault("dialog.confirm")
        override val cancel: String = props.getAutoDefault("dialog.cancel")
    }

    open inner class AppBar : Internationalization.AppBar {
        override val changeLanguage: String = props.getAutoDefault("appBar.changeLanguage")

    }
}

fun Properties.getAutoDefault(name: String): String = this.getProperty(name, name)