package ru.danil42russia.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.jetbrains.php.config.interpreters.PhpTextFieldWithSdkBasedBrowse
import com.jetbrains.php.tools.quality.QualityToolCustomSettings
import javax.swing.JComponent
import javax.swing.JPanel

class NoverifyCustomOptionsForm(project: Project, private val configuration: NoverifyConfiguration) :
    QualityToolCustomSettings() {
    private lateinit var myTopPanel: JPanel
    private lateinit var myUseKphpCheckBox: JBCheckBox
    private lateinit var myUseCores: JBIntSpinner
    private lateinit var myExcludeRegexp: JBTextField
    private lateinit var myCachePath: PhpTextFieldWithSdkBasedBrowse

    init {
        myUseKphpCheckBox.addActionListener { configuration.myUseKphp = myUseKphpCheckBox.isSelected }
        myUseCores.addChangeListener { configuration.myCoresCount = myUseCores.number }
        myExcludeRegexp.addActionListener { configuration.myExcludeRegexp = myExcludeRegexp.text }

        myCachePath.init(project, null, "Cache Path", false, true)
        myCachePath.textField.document.addUndoableEditListener { configuration.myCachePath = myCachePath.text }
    }

    override fun createComponent(): JComponent {
        return myTopPanel
    }

    override fun isModified(): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply() {
        configuration.myUseKphp = myUseKphpCheckBox.isSelected
        configuration.myCoresCount = myUseCores.number
        configuration.myExcludeRegexp = myExcludeRegexp.text
        configuration.myCachePath = myCachePath.text
    }

    override fun reset() {
        myUseKphpCheckBox.isSelected = configuration.myUseKphp
        myUseCores.number = configuration.myCoresCount
        myExcludeRegexp.text = configuration.myExcludeRegexp
        myCachePath.text = configuration.myCachePath
    }

    override fun getDisplayName(): String? {
        return null
    }

    override fun validate(): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    fun createUIComponents() {
        val maxCores = Runtime.getRuntime().availableProcessors()

        myUseCores = JBIntSpinner(configuration.myCoresCount, 1, maxCores)
    }
}
