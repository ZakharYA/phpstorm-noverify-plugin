package ru.danil42russia.noverify

import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolType
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor

class NoverifyMessageProcessor(info: QualityToolAnnotatorInfo<*>) : QualityToolXmlMessageProcessor(info) {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getXmlMessageHandler(): XMLMessageHandler {
        TODO("Not yet implemented")
    }

    override fun getMessageStart(line: String): Int {
        TODO("Not yet implemented")
    }

    override fun getMessageEnd(line: String): Int {
        TODO("Not yet implemented")
    }
}