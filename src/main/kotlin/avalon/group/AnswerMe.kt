package avalon.group

import avalon.api.Flag.AT
import avalon.tool.XiaoIceResponder
import avalon.tool.pool.Constants
import avalon.util.GroupConfig
import avalon.util.GroupMessage
import org.apache.commons.lang3.StringUtils
import java.util.regex.Pattern

/**
 * Created by Eldath on 2017/10/5 0029.
 *
 * @author Eldath
 */
object AnswerMe : GroupMessageResponder() {

	override fun doPost(message: GroupMessage, groupConfig: GroupConfig) {
		if (!Constants.Setting.AnswerMe_Enabled) return
		var content = message.content.trim().toLowerCase().replace(Regex("[\\pP\\p{Punct}]"), "")
		var text = content
		text = text.replace(Regex(getKeyWordRegex().toString()), "")
		if ("" == text.replace(" ", "")) {
			message.response(AT(message) + " 消息不能为空哦~(*∩_∩*)")
			return
		}
		if (StringUtils.isAlpha(text)) if (text.length < 5) {
			message.response(AT(message) + " 您的消息过短~o(╯□╰)o！")
			return
		} else if (text.length < 3) {
			message.response(AT(message) + " 您的消息过短~o(╯□╰)o！")
			return
		}
		content = content.replace(Regex(getKeyWordRegex().toString()), "")
		val responseXiaoIce = XiaoIceResponder.responseXiaoIce(content) ?: return
		message.response(AT(message) + " " + responseXiaoIce)
	}

	override fun getHelpMessage() = "avalon answer me|阿瓦隆回答我：激活智能回复功能"

	override fun getKeyWordRegex(): Pattern = Pattern.compile("^avalon answer me |^阿瓦隆回答我 ")

	override fun instance() = this
}