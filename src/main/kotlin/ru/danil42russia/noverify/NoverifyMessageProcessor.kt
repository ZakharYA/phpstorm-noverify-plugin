package ru.danil42russia.noverify

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.jetbrains.php.tools.quality.*
import org.jetbrains.annotations.NonNls

class NoverifyMessageProcessor(private val info: QualityToolAnnotatorInfo<*>) : QualityToolMessageProcessor(info) {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getMessagePrefix(): @NonNls String {
        return "noverify"
    }

    override fun parseLine(line: String) {
        val outputLine = line.trim()
        if (!outputLine.startsWith(MESSAGE_START) || !outputLine.endsWith(MESSAGE_END)) {
            return
        }

        val messageHandler = NoverifyJsonMessageHandler()
        messageHandler.parseJson(outputLine)

        val psiFile = info.psiFile ?: return
        val document = PsiDocumentManager.getInstance(psiFile.project).getDocument(psiFile) ?: return

        for (problem in messageHandler.problemList) {
            val startLine = document.getLineStartOffset(problem.lineNumber - 1)
            val textRange = TextRange.create(startLine + problem.startChar, startLine + problem.endChar)

            val message = QualityToolMessage(this, textRange, problem.severity, problem.message)
            addMessage(message)
        }
    }

    // 300iq мув конечно, но выглядит прикольно :)
    override fun severityToDisplayLevel(severity: QualityToolMessage.Severity): HighlightDisplayLevel? {
        return HighlightDisplayLevel.find(severity.name)
    }

    override fun addMessage(message: QualityToolMessage) {
        super.addMessage(message)
    }

    override fun done() {
    }

    private class NoverifyJsonMessageHandler {
        val problemList: MutableList<NoverifyProblemDescription> = mutableListOf()

        fun parseJson(line: String) {
            val parser = JsonParser.parseString(line)

            val reports = parser.asJsonObject.get("Reports").asJsonArray
            reports.forEach { report: JsonElement ->
                val jReport = report.asJsonObject

                val problem = parseReport(jReport)
                problemList.add(problem)
            }
        }

        fun parseReport(jReport: JsonObject): NoverifyProblemDescription {
            return NoverifyProblemDescription(
                levelToSeverity(jReport.get("level").asInt),
                jReport.get("line").asInt,
                jReport.get("start_char").asInt,
                jReport.get("end_char").asInt,
                jReport.get("message").asString,
                jReport.get("filename").asString,
            )
        }

        /**
         * @see https://github.com/VKCOM/noverify/blob/master/src/linter/report.go#L1153
         * @see https://github.com/VKCOM/noverify/blob/master/src/linter/lintapi/lintapi.go
         */
        fun levelToSeverity(level: Int?): QualityToolMessage.Severity? {
            return when (level) {
                1 -> QualityToolMessage.Severity.ERROR
                2 -> QualityToolMessage.Severity.WARNING
                3 -> null // TODO: Подумать
                4 -> QualityToolMessage.Severity.WARNING
                else -> null
            }
        }
    }

    companion object {
        private const val MESSAGE_START: @NonNls String = "{"
        private const val MESSAGE_END: @NonNls String = "}"

        private val LOG: Logger = Logger.getInstance(NoverifyMessageProcessor::class.java)
    }
}
