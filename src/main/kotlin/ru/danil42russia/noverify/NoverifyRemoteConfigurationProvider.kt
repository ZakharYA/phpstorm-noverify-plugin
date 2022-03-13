package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.jetbrains.php.config.interpreters.PhpInterpreter
import com.jetbrains.php.tools.quality.QualityToolConfigurableForm
import org.jdom.Element

class NoverifyRemoteConfigurationProvider : NoverifyConfigurationProvider() {
    override fun canLoad(tagName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun load(element: Element): NoverifyConfiguration? {
        TODO("Not yet implemented")
    }

    override fun createConfigurationForm(
        project: Project,
        settings: NoverifyConfiguration
    ): QualityToolConfigurableForm<*> {
        TODO("Not yet implemented")
    }

    override fun createNewInstance(
        project: Project?,
        existingSettings: List<NoverifyConfiguration>
    ): NoverifyConfiguration {
        TODO("Not yet implemented")
    }

    override fun createConfigurationByInterpreter(interpreter: PhpInterpreter): NoverifyConfiguration {
        TODO("Not yet implemented")
    }
}
