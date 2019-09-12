package interfacehelper

import interfacehelper.interfaces.IGetAllIpAddresses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.ArrayList

object GetAllIpAddresses: IGetAllIpAddresses {
    private val logger: Logger = LoggerFactory.getLogger(GetAllIpAddresses::class.java)
    private val outgoingInterface = MyIpAddress.getMyInterface()

    @Synchronized
    override fun get(): ArrayList<InetAddress> {
        val ipAddresses = ArrayList<InetAddress>()
        logger.info("Requesting all IP addresses in network '$outgoingInterface' ...")

        for (client in NetworkInterface.getNetworkInterfaces()) {
            if (client.isUp && client.name == outgoingInterface) {
                for (address in client.inetAddresses) {
                    if (!address.hostName.contains("localhost")) {
                        logger.info("Network device '${address.hostName}' found")
                        ipAddresses.add(InetAddress.getByName(address.hostName))
                    }
                }
            }
        }

        if (ipAddresses.isEmpty()) {
            logger.warn("No devices found in network '$outgoingInterface'")
        }

        return ipAddresses
    }
}