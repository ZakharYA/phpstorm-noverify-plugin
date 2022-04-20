package ru.danil42russia.noverify

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.ExtensionPointName
import com.jetbrains.php.tools.quality.QualityToolConfigurationProvider

abstract class NoVerifyConfigurationProvider : QualityToolConfigurationProvider<NoVerifyConfiguration>() {
    companion object {
        private val LOG: Logger = Logger.getInstance(NoVerifyConfigurationProvider::class.java)
        private val EP_NAME: ExtensionPointName<NoVerifyConfigurationProvider> =
            ExtensionPointName.create("ru.danil42russia.noverify.noverifyConfigurationProvider")

        fun getInstances(): NoVerifyConfigurationProvider? {
            val extensions: Array<NoVerifyConfigurationProvider> = EP_NAME.extensions
            if (extensions.size > 1) {
                LOG.error("Several providers for remote NoVerify configuration was found")
            }
            return if (extensions.isEmpty()) null else extensions[0]
        }
    }
}
