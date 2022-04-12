package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.ui.components.JBCheckBox
import com.jetbrains.php.tools.quality.QualityToolCustomSettings
import javax.swing.JComponent
import javax.swing.JPanel

class NoverifyCustomOptionsForm(project: Project, private val configuration: NoverifyConfiguration) :
    QualityToolCustomSettings() {
    private var myTopPanel: JPanel? = null
    private var myUseKphpCheckBox: JBCheckBox? = null

    init {
        myUseKphpCheckBox?.addActionListener { configuration.myUseKphp = myUseKphpCheckBox?.isSelected ?: false }
    }

    override fun createComponent(): JComponent? {
        return myTopPanel
    }

    override fun isModified(): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply() {
        configuration.myUseKphp = myUseKphpCheckBox?.isSelected ?: false
    }

    override fun reset() {
        myUseKphpCheckBox?.isSelected = configuration.myUseKphp
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

    override fun validate(): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }
}
