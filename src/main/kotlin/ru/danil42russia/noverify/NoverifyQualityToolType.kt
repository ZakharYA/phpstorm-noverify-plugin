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
        TODO("Not yet implemented")
    }

    override fun createConfigurableForm(
        project: Project,
        settings: NoverifyConfiguration?
    ): QualityToolConfigurableForm<NoverifyConfiguration> {
        TODO("Not yet implemented")
    }

    override fun getToolConfigurable(project: Project): Configurable {
        TODO("Not yet implemented")
    }

    override fun getProjectConfiguration(project: Project): QualityToolProjectConfiguration<NoverifyConfiguration> {
        TODO("Not yet implemented")
    }

    override fun createConfiguration(): NoverifyConfiguration {
        TODO("Not yet implemented")
    }

    companion object {
        val INSTANCE = NoverifyQualityToolType()
    }
}