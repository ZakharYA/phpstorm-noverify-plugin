package ru.danil42russia.noverify

import com.intellij.openapi.util.NlsSafe
import com.intellij.util.xmlb.XmlSerializer
import com.jetbrains.php.tools.quality.QualityToolConfigurationBaseManager
import com.jetbrains.php.tools.quality.QualityToolType
import org.jdom.Element

open class NoverifyConfigurationBaseManager : QualityToolConfigurationBaseManager<NoverifyConfiguration>() {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getOldStyleToolPathName() = NOVERIFY_PATH

    override fun getConfigurationRootName() = NOVERIFY_ROOT_NAME

    override fun loadLocal(element: Element?): NoverifyConfiguration? {
        if (element == null) {
            return null
        }

        return XmlSerializer.deserialize(element, NoverifyConfiguration::class.java)
    }

    companion object {
        const val NOVERIFY: @NlsSafe String = "Noverify"
        const val NOVERIFY_PATH: @NlsSafe String = "NoverifyPath"
        const val NOVERIFY_ROOT_NAME: @NlsSafe String = "Noverify_settings"
    }
}
