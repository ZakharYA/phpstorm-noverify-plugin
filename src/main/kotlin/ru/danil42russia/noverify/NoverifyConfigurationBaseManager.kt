package ru.danil42russia.noverify

import com.intellij.util.xmlb.XmlSerializer
import com.jetbrains.php.tools.quality.QualityToolConfigurationBaseManager
import com.jetbrains.php.tools.quality.QualityToolType
import org.jdom.Element

open class NoverifyConfigurationBaseManager : QualityToolConfigurationBaseManager<NoverifyConfiguration>() {
    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    override fun getOldStyleToolPathName() = "noverify"

    override fun getConfigurationRootName() = "noverify_settings"

    override fun loadLocal(element: Element?): NoverifyConfiguration? {
        if (element == null)
            return null

        return XmlSerializer.deserialize(element, NoverifyConfiguration::class.java)
    }
}