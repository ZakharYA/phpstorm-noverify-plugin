package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolConfiguration

class NoverifyConfiguration : QualityToolConfiguration {
    override fun compareTo(other: QualityToolConfiguration?): Int {
        TODO("Not yet implemented")
    }

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getPresentableName(p0: Project?): String {
        TODO("Not yet implemented")
    }

    override fun getInterpreterId(): String {
        TODO("Not yet implemented")
    }

    override fun getTimeout(): Int {
        TODO("Not yet implemented")
    }

    override fun setTimeout(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getToolPath(): String {
        TODO("Not yet implemented")
    }

    override fun setToolPath(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getMaxMessagesPerFile(): Int {
        TODO("Not yet implemented")
    }

    override fun clone(): QualityToolConfiguration {
        TODO("Not yet implemented")
    }
}