package ru.danil42russia.noverify

import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolType
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor
import org.jetbrains.annotations.NonNls
import org.xml.sax.Attributes

class NoverifyMessageProcessor(info: QualityToolAnnotatorInfo<*>) : QualityToolXmlMessageProcessor(info) {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getMessagePrefix(): @NonNls String {
        return "noverify"
    }

    override fun getXmlMessageHandler(): XMLMessageHandler {
        return NoverifyXmlMessageHandler()
    }

    override fun getMessageStart(line: String): Int {
        return line.indexOf(MESSAGE_START)
    }

    override fun getMessageEnd(line: String): Int {
        return line.indexOf(MESSAGE_END)
    }

    private class NoverifyXmlMessageHandler : XMLMessageHandler() {

        override fun parseTag(tagName: String, attributes: Attributes) {
            if (tagName != "report") {
                return
            }

            this.myLineNumber = parseLineNumber(attributes.getValue("line"))
        }
    }

    companion object {
        private const val MESSAGE_START: @NonNls String = "<report"
        private const val MESSAGE_END: @NonNls String = "</report>"
    }
}
