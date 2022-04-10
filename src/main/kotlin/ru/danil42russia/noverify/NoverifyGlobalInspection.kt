package ru.danil42russia.noverify

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ex.ExternalAnnotatorBatchInspection
import com.intellij.openapi.util.Key
import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolValidationGlobalInspection
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor.ProblemDescription
import javax.swing.JComponent

class NoverifyGlobalInspection : QualityToolValidationGlobalInspection(), ExternalAnnotatorBatchInspection {
    override fun getAnnotator(): QualityToolAnnotator<NoverifyValidationInspection> {
        return NoverifyAnnotatorProxy.INSTANCE
    }

    override fun getKey(): Key<List<ProblemDescription>> {
        return NOVERIFY_ANNOTATOR_INFO
    }

    // TODO: А точно надо? Возможно пригодиться в будущем
    override fun createOptionsPanel(): JComponent? {
        val optionsPanel = NoverifyOptionsPanel(this)

        return optionsPanel.optionsPanel
    }

    override fun getSharedLocalInspectionTool(): LocalInspectionTool {
        return NoverifyValidationInspection()
    }

    // TODO: Переделать
    // Путь до stubs
    // Путь до кэша
    // KPHP флаг
    fun getCommandLineOptions(projectPath: String, filePath: String): List<String> {
        val options = ArrayList<String>()
        options.add("check")

        options.add("--output-json")
        options.add("--stubs-dir=D:\\Libs\\phpstorm-stubs")
        options.add("--full-analysis-files=$filePath")
        options.add("--exclude=\"vendor|tests\"")

        options.add(projectPath)

        return options
    }

    companion object {
        private val NOVERIFY_ANNOTATOR_INFO = Key.create<List<ProblemDescription>>("ANNOTATOR_INFO_NOVERIFY")
    }
}
