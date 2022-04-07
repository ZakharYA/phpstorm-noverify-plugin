package ru.danil42russia.noverify

import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolValidationInspection
import ru.danil42russia.noverify.NoverifyConfigurationBaseManager.Companion.NOVERIFY

class NoverifyValidationInspection : QualityToolValidationInspection() {
    override fun getAnnotator(): QualityToolAnnotator<NoverifyValidationInspection> {
        return NoverifyAnnotatorProxy.INSTANCE
    }

    override fun getToolName(): String {
        return NOVERIFY
    }
}
