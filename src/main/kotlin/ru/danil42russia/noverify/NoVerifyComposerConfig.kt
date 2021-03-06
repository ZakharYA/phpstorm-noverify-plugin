package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.jetbrains.php.composer.actions.log.ComposerLogMessageBuilder
import com.jetbrains.php.tools.quality.QualityToolConfigurationManager
import com.jetbrains.php.tools.quality.QualityToolsComposerConfig
import org.jetbrains.annotations.NonNls
import ru.danil42russia.noverify.NoVerifyOpenSettingsProvider.Companion.NOVERIFY_OPEN_SETTINGS_PROVIDER

class NoVerifyComposerConfig :
    QualityToolsComposerConfig<NoVerifyConfiguration, NoVerifyValidationInspection>(PACKAGE, RELATIVE_PATH) {
    override fun getQualityToolsInspectionSettings(): ComposerLogMessageBuilder.Settings? {
        return null
    }

    override fun getQualityInspectionShortName(): String {
        return NoVerifyQualityToolType.INSTANCE.inspectionId
    }

    override fun getConfigurationManager(project: Project): QualityToolConfigurationManager<NoVerifyConfiguration> {
        return NoVerifyConfigurationManager.getInstance(project)
    }

    override fun getSettings(): ComposerLogMessageBuilder.Settings {
        return NOVERIFY_OPEN_SETTINGS_PROVIDER
    }

    companion object {
        private const val PACKAGE: @NonNls String = "vkcom/noverify"
        private val RELATIVE_PATH: @NonNls String = "bin/noverify${if (SystemInfo.isWindows) ".exe" else ""}"
    }
}
