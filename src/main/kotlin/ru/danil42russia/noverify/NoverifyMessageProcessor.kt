package ru.danil42russia.noverify

import com.intellij.codeHighlighting.HighlightDisplayLevel
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

        val psiFile = info.psiFile ?: return
        val document = PsiDocumentManager.getInstance(psiFile.project).getDocument(psiFile) ?: return

        for (problem in messageHandler.problemList) {
            val startLine = document.getLineStartOffset(problem.lineNumber - 1)
            val textRange = TextRange.create(startLine + problem.startChar, startLine + problem.endChar)

            val message = QualityToolMessage(this, textRange, problem.severity, problem.message)
            addMessage(message)
        }
    }

    // 300iq мув конечно :)
    override fun severityToDisplayLevel(severity: QualityToolMessage.Severity): HighlightDisplayLevel? {
        return HighlightDisplayLevel.find(severity.name)
    }

    override fun getMessageStart(line: String): Int {
        return line.indexOf(MESSAGE_START)
    }

    override fun getMessageEnd(line: String): Int {
        return line.indexOf(MESSAGE_END)
    }

    private class NoverifyXmlMessageHandler : XMLMessageHandler() {
        val problemList: MutableList<NoverifyProblemDescription> = mutableListOf()

        override fun parseTag(tagName: String, attributes: Attributes) {
            if (tagName != "report") {
                return
            }

            myLineNumber = parseLineNumber(attributes.getValue("line"))
            val message = attributes.getValue("message")
            val filePath = PathUtil.toSystemIndependentName(attributes.getValue("filename"))
            val startChar = parseCharNumber(attributes.getValue("start_char"))
            val endChar = parseCharNumber(attributes.getValue("end_char"))
            val severity = levelToSeverity(attributes.getValue("level"))

            val problem = NoverifyProblemDescription(
                severity,
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

        /**
         * @see https://github.com/VKCOM/noverify/blob/master/src/linter/report.go#L1153
         * @see https://github.com/VKCOM/noverify/blob/master/src/linter/lintapi/lintapi.go
         */
        fun levelToSeverity(level: String?): QualityToolMessage.Severity? {
            return when (level) {
                "1" -> QualityToolMessage.Severity.ERROR
                "2" -> QualityToolMessage.Severity.WARNING
                "3" -> null // TODO: Подумать
                "4" -> QualityToolMessage.Severity.WARNING
                else -> null
            }
        }
    }

    companion object {
        private const val MESSAGE_START: @NonNls String = "<report"
        private const val MESSAGE_END: @NonNls String = "</report>"

        private val LOG: Logger = Logger.getInstance(NoverifyMessageProcessor::class.java)
    }
}
