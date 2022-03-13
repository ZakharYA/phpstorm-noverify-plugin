package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.jetbrains.php.composer.actions.log.ComposerLogMessageBuilder
import com.jetbrains.php.tools.quality.QualityToolConfigurableList
import com.jetbrains.php.tools.quality.QualityToolType
import com.jetbrains.php.ui.PhpUiUtil


class NoverifyOpenSettingsProvider : ComposerLogMessageBuilder.Settings("\u200C") {
    override fun show(project: Project) {
        PhpUiUtil.editConfigurable(
            project,
            object :
                QualityToolConfigurableList<NoverifyConfiguration>(project, NoverifyQualityToolType.INSTANCE, null) {
                override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
                    return NoverifyQualityToolType.INSTANCE
                }
            })
    }

    companion object {
        val NOVERIFY_OPEN_SETTINGS_PROVIDER = NoverifyOpenSettingsProvider()
    }
}