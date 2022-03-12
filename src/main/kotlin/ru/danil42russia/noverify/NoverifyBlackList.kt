package ru.danil42russia.noverify

import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolBlackList

@State(name = "NoverifyBlackList", storages = [Storage("\$WORKSPACE_FILE$")])
class NoverifyBlackList : QualityToolBlackList() {
    companion object {
        fun getInstance(project: Project): NoverifyBlackList {
            return project.getService(NoverifyBlackList::class.java)
        }
    }
}