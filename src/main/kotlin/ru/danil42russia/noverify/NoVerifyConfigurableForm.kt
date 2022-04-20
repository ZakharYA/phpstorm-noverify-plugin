package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.jetbrains.php.tools.quality.*
import ru.danil42russia.noverify.NoVerifyConfigurationBaseManager.Companion.NOVERIFY

class NoVerifyConfigurableForm(project: Project, configuration: NoVerifyConfiguration) :
    QualityToolConfigurableForm<NoVerifyConfiguration>(project, configuration, NOVERIFY, "noverify") {
    override fun getQualityToolType(): QualityToolType<QualityToolConfiguration> {
        return NoVerifyQualityToolType.INSTANCE as QualityToolType<QualityToolConfiguration>
    }

    override fun getCustomConfigurable(
        project: Project, configuration: NoVerifyConfiguration
    ): QualityToolCustomSettings {
        return NoVerifyCustomOptionsForm(project, configuration)
    }

    override fun getHelpTopic(): String {
        return "reference.settings.php.NoVerify"
    }

    override fun validateMessage(message: String): Pair<Boolean, String> {
        // FIXME: Поправить когда сделают переопределение аргументов

        // Конфликтует с validate из NoVerifyCustomOptionsForm
        // но пофиг, главное хоть какая-та валидация

        return doValidation(message)
    }

    companion object {
        // На самом деле проверка фигня
        // всё из-за того, что в функции validateConfiguration захардкожены параметры получения версии линтера
        // 300iq :^)
        fun doValidation(message: String): Pair<Boolean, String> {
            val noverifyName = "NoVerify"
            if (!message.startsWith(noverifyName)) {
                return Pair.create(false, message)
            }

            val informationTest = "NoVerify - Pretty fast linter (static analysis tool) for PHP"
            val regex = Regex("^NoVerify, version (?<version>\\d.\\d.\\d)?")
            val matchResult = regex.find(message) ?: return Pair.create(true, "OK, $informationTest")

            val version = matchResult.groups["version"]
            if (version == null) {
                Pair.create(true, "OK, $informationTest")
            }

            return Pair.create(true, "OK, $message")
        }
    }
}
