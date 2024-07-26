package ltd.guimc.lgzbot.wynn.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {
    fun instantToString(date: Instant): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss Z")
        return date.atZone(ZoneId.of("GMT+8")).format(formatter)
    }
}