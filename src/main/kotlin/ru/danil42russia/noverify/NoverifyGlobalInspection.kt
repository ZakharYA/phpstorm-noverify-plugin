package ru.danil42russia.noverify

import com.intellij.codeInspection.ex.ExternalAnnotatorBatchInspection
import com.intellij.openapi.util.Key
import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolValidationGlobalInspection
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor.ProblemDescription

class NoverifyGlobalInspection : QualityToolValidationGlobalInspection(),
    ExternalAnnotatorBatchInspection {
    override fun getAnnotator(): QualityToolAnnotator<NoverifyValidationInspection> {
        return NoverifyAnnotatorProxy.INSTANCE
    }

    override fun getKey(): Key<List<ProblemDescription>> {
        return NOVERIFY_ANNOTATOR_INFO
    }

    fun getCommandLineOptions(): List<String> {
        return emptyList()
    }

    companion object {
        private val NOVERIFY_ANNOTATOR_INFO = Key.create<List<ProblemDescription>>("ANNOTATOR_INFO_NOVERIFY")
    }
}
