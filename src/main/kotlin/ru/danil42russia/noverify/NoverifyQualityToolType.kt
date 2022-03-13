package ru.danil42russia.noverify

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.*

class NoverifyQualityToolType : QualityToolType<NoverifyConfiguration>() {
    override fun getDisplayName(): String {
        return "Noverify"
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
        project: Project,
        settings: NoverifyConfiguration?
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

    companion object {
        val INSTANCE = NoverifyQualityToolType()
    }
}
