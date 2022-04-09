package ru.danil42russia.noverify

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.util.PathUtil
import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolMessage
import com.jetbrains.php.tools.quality.QualityToolType
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor
import org.jetbrains.annotations.NonNls
import org.xml.sax.Attributes
import org.xml.sax.InputSource

class NoverifyMessageProcessor(private val info: QualityToolAnnotatorInfo<*>) : QualityToolXmlMessageProcessor(info) {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getMessagePrefix(): @NonNls String {
        return "noverify"
    }

    override fun getXmlMessageHandler(): XMLMessageHandler {
        return NoverifyXmlMessageHandler()
    }

    // TODO: Переделать
    override fun processMessage(source: InputSource?) {
        val messageHandler = xmlMessageHandler as NoverifyXmlMessageHandler
        mySAXParser.parse(source, messageHandler)

        val psiFile = info.psiFile
        val document = PsiDocumentManager.getInstance(psiFile.project).getDocument(psiFile)!!

        for (problem in messageHandler.problemList) {
            val startLine = document.getLineStartOffset(problem.lineNumber - 1)
            val tr = TextRange.create(startLine + problem.startChar, startLine + problem.endChar)

            val message = QualityToolMessage(this, tr, problem.severity, problem.message)
            addMessage(message)
        }
    }

    override fun getMessageStart(line: String): Int {
        return line.indexOf(MESSAGE_START)
    }

    override fun getMessageEnd(line: String): Int {
        return line.indexOf(MESSAGE_END)
    }

    private class NoverifyXmlMessageHandler : XMLMessageHandler() {
        val problemList = mutableListOf<NoverifyProblemDescription>()

        override fun parseTag(tagName: String, attributes: Attributes) {
            if (tagName != "report") {
                return
            }

            myLineNumber = parseLineNumber(attributes.getValue("line"))
            val message = attributes.getValue("message")
            val filePath = PathUtil.toSystemIndependentName(attributes.getValue("filename"))
            val startChar = parseCharNumber(attributes.getValue("start_char"))
            val endChar = parseCharNumber(attributes.getValue("end_char"))

            val problem = NoverifyProblemDescription(
                QualityToolMessage.Severity.ERROR,
                myLineNumber,
                startChar,
                endChar,
                message,
                filePath,
            )

            problemList.add(problem)
        }

        fun parseCharNumber(charNumStr: String?): Int {
            if (charNumStr != null) {
                try {
                    return charNumStr.toInt()
                } catch (_: NumberFormatException) {
                    LOG.error("Invalid char number: $charNumStr")
                }
            } else {
                LOG.error("Missing char number")
            }

            return -1
        }
    }

    companion object {
        private const val MESSAGE_START: @NonNls String = "<report"
        private const val MESSAGE_END: @NonNls String = "</report>"

        private val LOG: Logger = Logger.getInstance(NoverifyMessageProcessor::class.java)
    }
}
