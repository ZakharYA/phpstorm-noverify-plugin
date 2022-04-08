package ru.danil42russia.noverify

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.jetbrains.php.tools.quality.QualityToolProjectConfiguration
import com.jetbrains.php.tools.quality.QualityToolType

@State(name = "NoverifyProjectConfiguration", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class NoverifyProjectConfiguration : QualityToolProjectConfiguration<NoverifyConfiguration>(),
    PersistentStateComponent<NoverifyProjectConfiguration> {
    override fun getState(): NoverifyProjectConfiguration {
        return this
    }

    override fun loadState(state: NoverifyProjectConfiguration) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun getQualityToolType(): QualityToolType<NoverifyConfiguration> {
        return NoverifyQualityToolType.INSTANCE
    }

    companion object {
        fun getInstance(project: Project): NoverifyProjectConfiguration {
            return project.getService(NoverifyProjectConfiguration::class.java)
        }
    }
}
