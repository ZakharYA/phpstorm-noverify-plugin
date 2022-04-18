package ru.danil42russia.noverify

import ru.danil42russia.noverify.NoverifyConfigurationBaseManager.Companion.NOVERIFY

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.jetbrains.php.tools.quality.QualityToolConfigurableForm
import com.jetbrains.php.tools.quality.QualityToolConfiguration
import com.jetbrains.php.tools.quality.QualityToolCustomSettings
import com.jetbrains.php.tools.quality.QualityToolType

class NoverifyConfigurableForm(project: Project, configuration: NoverifyConfiguration) :
    QualityToolConfigurableForm<NoverifyConfiguration>(project, configuration, NOVERIFY, "noverify") {
    override fun getQualityToolType(): QualityToolType<QualityToolConfiguration> {
        return NoverifyQualityToolType.INSTANCE as QualityToolType<QualityToolConfiguration>
    }

    override fun getCustomConfigurable(
        project: Project, configuration: NoverifyConfiguration
    ): QualityToolCustomSettings {
        return NoverifyCustomOptionsForm(project, configuration)
    }

    override fun getHelpTopic(): String {
        return "reference.settings.php.NoVerify"
    }

    override fun validateMessage(message: String): Pair<Boolean, String> {
        // FIXME: Поправить когда сделают переопределение аргументов

        // На самом деле проверка фигня
        // всё из-за того, что в функции validateConfiguration захардкожены параметры получения версии линтера
        // 300iq :^)

        // Конфликтует с validate из NoverifyCustomOptionsForm
        // но пофиг, главное хоть какая-та валидация

        return doValidation(message)
    }

    companion object {
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
