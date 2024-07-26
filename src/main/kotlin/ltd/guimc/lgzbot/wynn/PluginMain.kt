package ltd.guimc.lgzbot.wynn

import dev.ohate.wynncraft4j.WynncraftAPI
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        "ltd.guimc.lgzbot.wynn",
        "0.1.0",
        "WynnPlugin",
    ){
        author("BakaBotTeam")
    }
) {
    lateinit var wynncraftAPI: WynncraftAPI

    override fun onEnable() {
        logger.info("WynnPlugin 正在加载喵")
        CommandManager.registerCommand(WynnCommand)
        wynncraftAPI = WynncraftAPI()
    }
}