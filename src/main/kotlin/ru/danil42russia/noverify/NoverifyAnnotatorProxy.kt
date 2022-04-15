package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.config.PhpRuntimeConfiguration
import com.jetbrains.php.tools.quality.*

open class NoverifyAnnotatorProxy : QualityToolAnnotator<NoverifyValidationInspection>() {
    override fun getOptions(
        filePath: String?, inspection: NoverifyValidationInspection, profile: InspectionProfile?, project: Project
    ): List<String> {
        if (filePath == null) {
            return emptyList()
        }

        val tool = qualityToolType.getGlobalTool(project, profile) as? NoverifyGlobalInspection ?: return emptyList()
        val projectPath = project.basePath ?: return emptyList()
        val config = getConfiguration(project, inspection) as? NoverifyConfiguration ?: return emptyList()

        val phpConfig = PhpRuntimeConfiguration.getInstance(project)
        val stubsPath = phpConfig.defaultStubsPath

        return tool.getCommandLineOptions(
            projectPath,
            filePath,
            config.myUseKphp,
            stubsPath,
            config.myCoresCount,
            config.myExcludeRegexp,
            config.myCachePath,
            config.myCustomParameters,
        )
    }

    override fun getTemporaryFilesFolder(): String {
        return TEMP_FOLDER
    }

    override fun createAnnotatorInfo(
        file: PsiFile?,
        tool: NoverifyValidationInspection,
        inspectionProfile: InspectionProfile,
        project: Project,
        configuration: QualityToolConfiguration,
        isOnTheFly: Boolean
    ): QualityToolAnnotatorInfo<NoverifyValidationInspection> {
        if (!isOnTheFly){
            LOG.warn("isOnTheFly is False")
        }

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

        // Точно надо?
        const val TEMP_FOLDER = "noverify_temp_folder"

        private val LOG: Logger = Logger.getInstance(NoverifyAnnotatorProxy::class.java)
    }
}
