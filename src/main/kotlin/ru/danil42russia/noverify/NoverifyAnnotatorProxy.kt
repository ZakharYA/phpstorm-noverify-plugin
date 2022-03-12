package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolMessageProcessor
import com.jetbrains.php.tools.quality.QualityToolType

class NoverifyAnnotatorProxy : QualityToolAnnotator<NoverifyValidationInspection>() {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getOptions(
        filePath: String?,
        inspection: NoverifyValidationInspection,
        profile: InspectionProfile?,
        project: Project
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun createMessageProcessor(collectedInfo: QualityToolAnnotatorInfo<*>): QualityToolMessageProcessor {
        return NoverifyMessageProcessor(collectedInfo)
    }

    companion object {
        val INSTANCE = NoverifyAnnotatorProxy()
    }
}