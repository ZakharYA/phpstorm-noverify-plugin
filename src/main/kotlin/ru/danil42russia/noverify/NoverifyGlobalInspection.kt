package ru.danil42russia.noverify

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ex.ExternalAnnotatorBatchInspection
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Key
import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolValidationGlobalInspection
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor.ProblemDescription

class NoverifyGlobalInspection : QualityToolValidationGlobalInspection(), ExternalAnnotatorBatchInspection {
    override fun getAnnotator(): QualityToolAnnotator<NoverifyValidationInspection> {
        return NoverifyAnnotatorProxy.INSTANCE
    }

    override fun getKey(): Key<List<ProblemDescription>> {
        return NOVERIFY_ANNOTATOR_INFO
    }

    override fun getSharedLocalInspectionTool(): LocalInspectionTool {
        return NoverifyValidationInspection()
    }

    // TODO: Переделать
    // Путь до stubs
    // Путь до кэша
    // Регулярка для игнора путей
    // KPHP флаг (Готово)
    fun getCommandLineOptions(projectPath: String, filePath: String, useKphp: Boolean): List<String> {
        val options = ArrayList<String>()
        options.add("check")

        options.add("--output-json")
        options.add("--stubs-dir=D:\\Libs\\phpstorm-stubs")
        options.add("--full-analysis-files=$filePath")
        options.add("--exclude=\"vendor|tests\"")

        if (useKphp) {
            options.add("--kphp")
        }

        options.add(projectPath)

        LOG.info(options.joinToString(separator = " "))
        return options
    }

    companion object {
        private val NOVERIFY_ANNOTATOR_INFO = Key.create<List<ProblemDescription>>("ANNOTATOR_INFO_NOVERIFY")


        private val LOG: Logger = Logger.getInstance(NoverifyConfigurationProvider::class.java)
    }
}
