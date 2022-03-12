package ru.danil42russia.noverify.services

import com.intellij.openapi.project.Project
import ru.danil42russia.noverify.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
