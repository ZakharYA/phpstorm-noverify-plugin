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

class NoVerifyCustomOptionsForm(project: Project, private val configuration: NoVerifyConfiguration) :
    QualityToolCustomSettings() {
    private lateinit var myTopPanel: JPanel
    private lateinit var myUseKphpCheckBox: JBCheckBox
    private lateinit var myUseCores: JBIntSpinner
    private lateinit var myExcludeRegexp: JBTextField
    private lateinit var myCachePath: PhpTextFieldWithSdkBasedBrowse
    private lateinit var myCustomParameters: JBTextField

    init {
        myUseKphpCheckBox.addActionListener { configuration.myUseKphp = myUseKphpCheckBox.isSelected }
        myUseCores.addChangeListener { configuration.myCoresCount = myUseCores.number }
        myExcludeRegexp.addActionListener { configuration.myExcludeRegexp = myExcludeRegexp.text }

        myCachePath.init(project, null, "Cache Path", false, true)
        myCachePath.textField.document.addUndoableEditListener { configuration.myCachePath = myCachePath.text }

        myCustomParameters.addActionListener { configuration.myCustomParameters = myCustomParameters.text }
    }

    override fun createComponent(): JComponent {
        return myTopPanel
    }

    override fun isModified(): Boolean {
        val oldSetting = configuration.clone() as NoVerifyConfiguration

        return myUseKphpCheckBox.isSelected != oldSetting.myUseKphp ||
                myUseCores.number != oldSetting.myCoresCount ||
                myExcludeRegexp.text != oldSetting.myExcludeRegexp ||
                myCachePath.text != oldSetting.myCachePath ||
                myCustomParameters.text != oldSetting.myCustomParameters
    }

    override fun apply() {
        configuration.myUseKphp = myUseKphpCheckBox.isSelected
        configuration.myCoresCount = myUseCores.number
        configuration.myExcludeRegexp = myExcludeRegexp.text
        configuration.myCachePath = myCachePath.text
        configuration.myCustomParameters = myCustomParameters.text
    }

    override fun reset() {
        myUseKphpCheckBox.isSelected = configuration.myUseKphp
        myUseCores.number = configuration.myCoresCount
        myExcludeRegexp.text = configuration.myExcludeRegexp
        myCachePath.text = configuration.myCachePath
        myCustomParameters.text = configuration.myCustomParameters
    }

    override fun getDisplayName(): String? {
        return null
    }

    override fun validate(): Pair<Boolean, String> {
        return Pair.create(false, "")
    }

    fun createUIComponents() {
        val maxCores = Runtime.getRuntime().availableProcessors()

        myUseCores = JBIntSpinner(configuration.myCoresCount, 1, maxCores)
    }
}
