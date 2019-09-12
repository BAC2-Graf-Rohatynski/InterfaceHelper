package interfacehelper.interfaces

import java.net.InetAddress
import java.util.ArrayList

interface IGetAllIpAddresses {
    fun get(): ArrayList<InetAddress>
}