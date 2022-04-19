package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializer
import com.jetbrains.php.config.interpreters.PhpInterpreter
import com.jetbrains.php.remote.tools.quality.QualityToolByInterpreterConfigurableForm
import com.jetbrains.php.tools.quality.QualityToolConfigurableForm
import org.jdom.Element
import org.jetbrains.annotations.NonNls

class NoverifyRemoteConfigurationProvider : NoverifyConfigurationProvider() {
    override fun canLoad(tagName: String): Boolean {
        return tagName == NOVERIFY_BY_INTERPRETER
    }

    override fun load(element: Element): NoverifyConfiguration {
        return XmlSerializer.deserialize(element, NoverifyRemoteConfiguration::class.java)
    }

    override fun createConfigurationForm(
        project: Project,
        settings: NoverifyConfiguration
    ): QualityToolConfigurableForm<*>? {
        if (settings !is NoverifyRemoteConfiguration) {
            return null
        }

        val delegate = NoverifyConfigurableForm(project, settings)
        return QualityToolByInterpreterConfigurableForm(
            project,
            settings,
            delegate,
        )
    }

    override fun createNewInstance(
        project: Project?,
        existingSettings: List<NoverifyConfiguration>
    ): NoverifyConfiguration? {
        // TODO: Добавить, но позже

        return null
    }

    override fun createConfigurationByInterpreter(interpreter: PhpInterpreter): NoverifyConfiguration {
        val settings = NoverifyRemoteConfiguration()
        settings.setInterpreterId(interpreter.id)
        return settings
    }

    companion object {
        private const val NOVERIFY_BY_INTERPRETER: @NonNls String = "noverify_by_interpreter"
    }
}
