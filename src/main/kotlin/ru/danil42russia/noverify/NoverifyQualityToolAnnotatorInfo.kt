package ru.danil42russia.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolConfiguration

class NoverifyQualityToolAnnotatorInfo(
    psiFile: PsiFile?,
    inspection: NoverifyValidationInspection,
    profile: InspectionProfile,
    project: Project,
    configuration: QualityToolConfiguration,
    isOnTheFly: Boolean
) : QualityToolAnnotatorInfo<NoverifyValidationInspection>(
    psiFile,
    inspection,
    profile,
    project,
    configuration,
    isOnTheFly
)
