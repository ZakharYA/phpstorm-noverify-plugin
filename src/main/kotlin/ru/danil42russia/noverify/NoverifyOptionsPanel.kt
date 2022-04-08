package ru.danil42russia.noverify

import com.jetbrains.php.tools.quality.QualityToolsOptionsPanel
import javax.swing.JPanel

class NoverifyOptionsPanel(inspection: NoverifyGlobalInspection) : QualityToolsOptionsPanel() {
    private val myOptionsPanel: JPanel? = null

    init {
    }

    override fun getOptionsPanel(): JPanel? {
        return myOptionsPanel
    }
}
