import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class ServerInfo(
    @SerialName("api_version")
    val apiVersion: List<Int> = listOf(),

    @SerialName("api_version_string")
    val apiVersionString: String = "", // 1.2.0

    @SerialName("components")
    val components: List<String> = listOf(),

    @SerialName("failed_components")
    val failedComponents: List<String> = listOf(),

    @SerialName("klippy_connected")
    val klippyConnected: Boolean = false, // true

    @SerialName("klippy_state")
    val klippyState: String = "", // ready

    @SerialName("missing_klippy_requirements")
    val missingKlippyRequirements: List<String> = listOf(),

    @SerialName("moonraker_version")
    val moonrakerVersion: String = "", // v0.7.1-876-g87dba2f

    @SerialName("registered_directories")
    val registeredDirectories: List<String> = listOf(),

    @SerialName("warnings")
    val warnings: List<String> = listOf(),

    @SerialName("websocket_count")
    val websocketCount: Int = 0 // 1
)