package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.profile.codeInspection.InspectionProjectProfileManager
import com.intellij.util.ObjectUtils.tryCast
import com.jetbrains.php.tools.quality.*
import ru.danil42russia.noverify.NoverifyConfigurationBaseManager.Companion.NOVERIFY

class NoverifyQualityToolType : QualityToolType<NoverifyConfiguration>() {
    override fun getDisplayName(): String {
        return NOVERIFY
    }

    override fun getQualityToolBlackList(project: Project): QualityToolBlackList {
        return NoverifyBlackList.getInstance(project)
    }

    override fun getConfigurationManager(project: Project): QualityToolConfigurationManager<NoverifyConfiguration> {
        return NoverifyConfigurationManager.getInstance(project)
    }

    override fun getInspection(): QualityToolValidationInspection {
        return NoverifyValidationInspection()
    }

    override fun getConfigurationProvider(): QualityToolConfigurationProvider<NoverifyConfiguration>? {
        return NoverifyConfigurationProvider.getInstances()
    }

    override fun createConfigurableForm(
        project: Project, settings: NoverifyConfiguration
    ): QualityToolConfigurableForm<NoverifyConfiguration> {
        return NoverifyConfigurableForm(project, settings)
    }

    override fun getToolConfigurable(project: Project): Configurable {
        return NoverifyConfigurable(project)
    }

    override fun getProjectConfiguration(project: Project): QualityToolProjectConfiguration<NoverifyConfiguration> {
        return NoverifyProjectConfiguration.getInstance(project)
    }

    override fun createConfiguration(): NoverifyConfiguration {
        return NoverifyConfiguration()
    }

    override fun getInspectionId(): String {
        return "NoVerifyGlobal"
    }

    override fun getHelpTopic(): String {
        return "reference.settings.php.NoVerify"
    }

    override fun getGlobalTool(project: Project, profile: InspectionProfile?): QualityToolValidationGlobalInspection? {
        val newProfile = profile ?: InspectionProjectProfileManager.getInstance(project).currentProfile

        val inspectionTool = newProfile.getInspectionTool(inspectionId, project) ?: return null

        return tryCast(inspectionTool.tool, NoverifyGlobalInspection::class.java)
    }

    override fun getInspectionShortName(project: Project): String {
        val tool = getGlobalTool(project, null)

        return tool?.shortName ?: inspection.shortName
    }

    companion object {
        val INSTANCE = NoverifyQualityToolType()
    }
}
