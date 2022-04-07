package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.jetbrains.php.tools.quality.QualityToolConfigurableForm
import com.jetbrains.php.tools.quality.QualityToolConfiguration
import com.jetbrains.php.tools.quality.QualityToolType
import ru.danil42russia.noverify.NoverifyConfigurationBaseManager.Companion.NOVERIFY

class NoverifyConfigurableForm(project: Project, configuration: NoverifyConfiguration) :
    QualityToolConfigurableForm<NoverifyConfiguration>(project, configuration, NOVERIFY, "noverify") {

    override fun getQualityToolType(): QualityToolType<QualityToolConfiguration> {
        return NoverifyQualityToolType.INSTANCE as QualityToolType<QualityToolConfiguration>
    }

    override fun validateMessage(message: String): Pair<Boolean, String> {
        return if (message.contains(NOVERIFY))
            Pair.create(true, "OK, $message")
        else
            Pair.create(false, message)
    }
}