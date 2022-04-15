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

    fun getCommandLineOptions(
        projectPath: String,
        filePath: String,
        useKphp: Boolean,
        stubsPath: String,
        coresCount: Int,
        excludeRegexp: String,
        cachePath: String,
        customParameters: String,
    ): List<String> {
        val options: MutableList<String> = ArrayList()
        options.add("check")

        options.add("--output-json")
        options.add("--full-analysis-files=$filePath")
        options.add("--cores=$coresCount")

        if (excludeRegexp.isNotBlank()) {
            options.add("--exclude=\"$excludeRegexp\"")
        }

        if (useKphp) {
            options.add("--kphp")
        }

        if (stubsPath.isNotBlank()) {
            options.add("--stubs-dir=$stubsPath")
        }

        if (cachePath.isNotBlank()) {
            options.add("--cache-dir=$cachePath")
        }

        if (customParameters.isNotBlank()) {
            options.add(customParameters)
        }

        options.add(projectPath)

        LOG.info(options.joinToString(separator = " "))
        return options
    }

    companion object {
        private val NOVERIFY_ANNOTATOR_INFO: Key<List<ProblemDescription>> = Key.create("ANNOTATOR_INFO_NOVERIFY")

        private val LOG: Logger = Logger.getInstance(NoverifyConfigurationProvider::class.java)
    }
}
