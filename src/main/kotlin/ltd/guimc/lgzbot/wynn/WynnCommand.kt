package ltd.guimc.lgzbot.wynn

import dev.ohate.wynncraft4j.model.choices.player.PlayerSelection
import dev.ohate.wynncraft4j.model.player.PlayerRank
import ltd.guimc.lgzbot.wynn.PluginMain.wynncraftAPI
import ltd.guimc.lgzbot.wynn.utils.DateUtils
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.ForwardMessageBuilder
import net.mamoe.mirai.message.data.PlainText
import java.time.ZoneId
import java.time.format.DateTimeFormatter



object WynnCommand : CompositeCommand(
    owner = PluginMain, primaryName = "wynn", description = "WynnCraft"
) {
    @SubCommand("player")
    @Description("获取玩家信息(所有角色)")
    suspend fun CommandSender.player(playerName: String) {
        requireNotNull(bot) { "Must have bot to use it" }
        requireNotNull(user) { "Must have user to use it" }

        val playerSelection: PlayerSelection = wynncraftAPI.player().getPlayer(playerName)
        val rank = playerSelection.player.playerRank
        val online = playerSelection.player.isOnline
        val firstJoin = playerSelection.player.firstJoin
        val lastJoin = playerSelection.player.lastJoin
        val playtime = playerSelection.player.playtime // hour(s)
        val server = playerSelection.player.server

        val outputMessage = ForwardMessageBuilder(bot!!.asFriend)

        outputMessage.add(bot!!, PlainText("WynnCraft 玩家信息:\n" +
            "玩家: ${if (rank == PlayerRank.UNKNOWN) "" else "[${rank.name}]"} $playerName\n" +
            "首次上线: ${DateUtils.instantToString(firstJoin)}\n" +
            "上次上线: ${DateUtils.instantToString(lastJoin)}\n" +
            "总游玩时长: $playtime 小时\n" +
            "在线状态: ${if (online) "在线" else "离线"}\n" +
            "所在服务器: $server"))

        playerSelection.player.characters.values.forEach {character ->
            var dungeonsInfo = ""
            var questsInfo = ""
            character.dungeons.list.forEach { dungeon -> dungeonsInfo += "  * ${dungeon.key}: ${dungeon.value}\n" }
            character.quests.forEach { quest -> questsInfo += "  * $quest\n" }
            outputMessage.add(bot!!, PlainText("档案信息:\n" +
                "档案名称: ${character.nickname}\n" +
                "档案 UUID: ${
                    playerSelection.player.characters.keys.firstOrNull { playerSelection.player.characters[it] == character }
                        .toString()}\n" +
                "职业: ${character.type}\n" +
                "等级: ${character.level}\n" +
                "在线时长: ${character.playtime} 小时\n" +
                "登入次数: ${character.logins}\n" +
                "死亡次数: ${character.deaths}\n" +
                "击杀生物数: ${character.mobsKilled}\n" +
                "移动距离: ${character.blocksWalked}b\n" +
                "已探索数量: ${character.discoveries}\n" +
                "找到的箱子: ${character.chestsFound}\n" +
                "技能点信息:\n" +
                "  * Strength: ${character.skillPoints.strength}\n" +
                "  * Dexterity: ${character.skillPoints.dexterity}\n" +
                "  * Intelligence: ${character.skillPoints.intelligence}\n" +
                "  * Defense: ${character.skillPoints.defence}\n" +
                "  * Agility: ${character.skillPoints.agility}\n" +
                "PVP: 击杀: ${character.pvp.kills}, 死亡: ${character.pvp.deaths}\n" +
                "专业等级:\n" +
                "  * Fishing: ${character.professions.fishing.level}\n" +
                "  * Woodcutting: ${character.professions.woodcutting.level}\n" +
                "  * Mining: ${character.professions.mining.level}\n" +
                "  * Farming: ${character.professions.farming.level}\n" +
                "  * Scribing: ${character.professions.scribing.level}\n" +
                "  * Jeweling: ${character.professions.jeweling.level}\n" +
                "  * Alchemism: ${character.professions.alchemism.level}\n" +
                "  * Cooking: ${character.professions.cooking.level}\n" +
                "  * Weaponsmithing: ${character.professions.weaponsmithing.level}\n" +
                "  * Tailoring: ${character.professions.tailoring.level}\n" +
                "  * Woodworking: ${character.professions.woodworking.level}\n" +
                "  * Armouring: ${character.professions.armouring.level}\n" +
                "地下城探索总数: ${character.dungeons.total}\n" +
                dungeonsInfo +
                "已完成的任务 (总数: ${character.quests.size})\n" +
                questsInfo
                ))
        }

        sendMessage(outputMessage.build())
    }
}