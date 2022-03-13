package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.tools.quality.*

open class NoverifyAnnotatorProxy : QualityToolAnnotator<NoverifyValidationInspection>() {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getOptions(
        filePath: String?,
        inspection: NoverifyValidationInspection,
        profile: InspectionProfile?,
        project: Project
    ): List<String> {
        return emptyList()
    }

    override fun getOptions(
        filePath: String?,
        inspection: NoverifyValidationInspection,
        profile: InspectionProfile?,
        project: Project,
        isOnTheFly: Boolean
    ): List<String> {
        return emptyList()

//        getQualityToolType
    }

    override fun createAnnotatorInfo(
        file: PsiFile?,
        tool: NoverifyValidationInspection,
        inspectionProfile: InspectionProfile,
        project: Project,
        configuration: QualityToolConfiguration,
        isOnTheFly: Boolean
    ): QualityToolAnnotatorInfo<NoverifyValidationInspection> {
        return NoverifyQualityToolAnnotatorInfo(file, tool, inspectionProfile, project, configuration, isOnTheFly);
    }

    override fun createMessageProcessor(collectedInfo: QualityToolAnnotatorInfo<*>): QualityToolMessageProcessor {
        return NoverifyMessageProcessor(collectedInfo)
    }

    companion object {
        val INSTANCE = NoverifyAnnotatorProxy()
    }
}
