package interfacehelper

import interfacehelper.interfaces.IMyIpAddress
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import propertystorage.InterfaceProperties
import java.lang.Exception
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.ArrayList

object MyIpAddress: IMyIpAddress {
    private val logger: Logger = LoggerFactory.getLogger(MyIpAddress::class.java)
    private val myInterface = InterfaceProperties.getInterfaceName()
    private var myIpAddress = String()
    private lateinit var myInetAddress: InetAddress
    private const val defaultIpAddress = "127.0.0.1"

    init {
        requestIpAddress()
    }

    @Synchronized
    override fun getAsString(): String? = if (myIpAddress.isEmpty()) null else myIpAddress

    @Synchronized
    override fun getAsInetAddress(): InetAddress? = if (::myInetAddress.isInitialized) myInetAddress else null

    @Synchronized
    override fun getMyInterface(): String = myInterface

    private fun requestIpAddress() {
        if (myInterface == "lo" || myInterface == "local" || myInterface == "loop" || myInterface == "localhost") {
            logger.info("Local host detected. Assigning local IP interface '$defaultIpAddress' ...")
            myIpAddress = defaultIpAddress
            myInetAddress = InetAddress.getByName(defaultIpAddress)
        } else {
            getMyIpAddress()
        }
    }

    private fun getMyIpAddress() {
        try {
            val ipAddresses = ArrayList<InetAddress>()
            logger.info("Requesting my outgoing IP address for network '$myInterface' ...")

            for (client in NetworkInterface.getNetworkInterfaces()) {
                if (client.isUp && client.name.contains(myInterface)) {
                    logger.info("Interface '${client.name}' found")

                    for (address in client.inetAddresses) {
                        logger.debug("Available device interface: ${address.hostAddress}")

                        if (address.hostAddress.startsWith("10.") || address.hostAddress.startsWith("192.")) {
                            val newIpAddress = address.hostAddress.split("%").first()
                            myInetAddress = address
                            myIpAddress = newIpAddress
                            return logger.info("My outgoing interface IP address is $newIpAddress!")
                        }
                    }
                }
            }

            if (ipAddresses.isEmpty()) {
                logger.warn("No devices found in network '$myInterface'. Starting with default IP address '$defaultIpAddress' ...")
            }

            myIpAddress = defaultIpAddress
        } catch (ex: Exception) {
            logger.error("Error occurred!\n${ex.message}")
            myIpAddress = defaultIpAddress
        }
    }
}