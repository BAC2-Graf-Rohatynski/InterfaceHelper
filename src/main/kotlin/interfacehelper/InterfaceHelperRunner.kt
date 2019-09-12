package interfacehelper

import enumstorage.update.ApplicationName
import org.json.JSONObject

object InterfaceHelperRunner {
    fun getUpdateInformation(): JSONObject = UpdateInformation.getAsJson(applicationName = ApplicationName.Interface.name)
}