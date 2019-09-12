package interfacehelper.interfaces

interface IMyOperatingSystem {
    fun getOperatingSystem(): String = System.getProperty("os.name")
    fun isLinux(): Boolean = getOperatingSystem().startsWith("Unix") || getOperatingSystem().startsWith("Linux")
    fun isWindows(): Boolean = getOperatingSystem().startsWith("Windows")
    fun getShutdownCommand(): String? = if (isWindows()) "shutdown.exe -s -t 0" else if (isLinux()) "shutdown -h now" else null
}