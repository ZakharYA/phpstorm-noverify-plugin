package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.tools.quality.*

open class NoverifyAnnotatorProxy : QualityToolAnnotator<NoverifyValidationInspection>() {
    override fun getOptions(
        filePath: String?,
        inspection: NoverifyValidationInspection,
        profile: InspectionProfile?,
        project: Project
    ): List<String> {
        val tool = qualityToolType.getGlobalTool(project, profile) as? NoverifyGlobalInspection ?: return emptyList()

        return tool.getCommandLineOptions(project.basePath)
    }

    override fun createAnnotatorInfo(
        file: PsiFile?,
        tool: NoverifyValidationInspection,
        inspectionProfile: InspectionProfile,
        project: Project,
        configuration: QualityToolConfiguration,
        isOnTheFly: Boolean
    ): QualityToolAnnotatorInfo<NoverifyValidationInspection> {
        return NoverifyQualityToolAnnotatorInfo(file, tool, inspectionProfile, project, configuration, isOnTheFly)
    }

    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun createMessageProcessor(collectedInfo: QualityToolAnnotatorInfo<*>): QualityToolMessageProcessor {
        return NoverifyMessageProcessor(collectedInfo)
    }

    override fun getPairedBatchInspectionShortName(): String {
        return qualityToolType.inspectionId
    }

    companion object {
        val INSTANCE = NoverifyAnnotatorProxy()
    }
}
