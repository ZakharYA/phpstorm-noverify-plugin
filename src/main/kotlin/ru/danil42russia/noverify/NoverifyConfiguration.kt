package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Transient
import com.jetbrains.php.tools.quality.QualityToolConfiguration

class NoverifyConfiguration : QualityToolConfiguration {
    private var myNoverifyPath = ""
    private var myTimeoutMs = 30000
    private var myMaxMessagesPerFile = 100

    override fun compareTo(other: QualityToolConfiguration?): Int {
        if (other !is NoverifyConfiguration) {
            return 1
        }
        if (this.getPresentableName(null) == "Local") {
            return -1
        }
        if (other.getPresentableName(null) == "Local") {
            return 1
        }

        return StringUtil.compare(getPresentableName(null), other.getPresentableName(null), false)
    }

    override fun getId(): String {
        return "Local"
    }

    override fun getPresentableName(project: Project?): String {
        return id
    }

    override fun getInterpreterId(): String? {
        return null
    }

    @Attribute("timeout")
    override fun getTimeout(): Int {
        return myTimeoutMs
    }

    override fun setTimeout(timeout: Int) {
        myTimeoutMs = timeout
    }

    @Transient
    override fun getToolPath(): String {
        return myNoverifyPath
    }

    override fun setToolPath(toolPath: String) {
        myNoverifyPath = toolPath
    }

    @Attribute("max_messages_per_file")
    override fun getMaxMessagesPerFile(): Int {
        return myMaxMessagesPerFile
    }

    override fun clone(): QualityToolConfiguration {
        return NoverifyConfiguration().also {
            it.myNoverifyPath = myNoverifyPath
            it.myMaxMessagesPerFile = myMaxMessagesPerFile
            it.myTimeoutMs = myTimeoutMs
        }
    }
}
