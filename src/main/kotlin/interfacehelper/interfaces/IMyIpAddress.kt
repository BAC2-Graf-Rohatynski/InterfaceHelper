package interfacehelper.interfaces

import java.net.InetAddress

interface IMyIpAddress {
    fun getAsString(): String?
    fun getAsInetAddress(): InetAddress?
    fun getMyInterface(): String
}