package ru.danil42russia.noverify

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolConfigurationManager

class NoverifyConfigurationManager(project: Project?) :
    QualityToolConfigurationManager<NoverifyConfiguration>(project) {
    init {
        if (project != null) {
            myProjectManager = project.getService(NoverifyProjectConfigurationManager::class.java)
        }
        myApplicationManager =
            ApplicationManager.getApplication().getService(NoverifyAppConfigurationManager::class.java)
    }

    @State(name = "Noverify", storages = [Storage("php.xml")])
    internal class NoverifyProjectConfigurationManager : NoverifyConfigurationBaseManager()

    @State(name = "Noverify", storages = [Storage("php.xml")])
    internal class NoverifyAppConfigurationManager : NoverifyConfigurationBaseManager()

    companion object {
        fun getInstance(project: Project): NoverifyConfigurationManager {
            return project.getService(NoverifyConfigurationManager::class.java)
        }
    }
}
