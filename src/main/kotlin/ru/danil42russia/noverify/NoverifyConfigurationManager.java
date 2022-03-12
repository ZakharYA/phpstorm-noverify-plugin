package ru.danil42russia.noverify;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.tools.quality.QualityToolConfigurationManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoverifyConfigurationManager extends QualityToolConfigurationManager<NoverifyConfiguration> {
    protected NoverifyConfigurationManager(@Nullable Project project) {
        super(project);
        if (project != null) {
            this.myProjectManager = project.getService(NoverifyProjectConfigurationManager.class);
        }
        this.myApplicationManager = ApplicationManager.getApplication().getService(NoverifyAppConfigurationManager.class);
    }

    public static NoverifyConfigurationManager getInstance(@NotNull Project project) {
        return project.getService(NoverifyConfigurationManager.class);
    }

    @State(name = "Noverify", storages = {@Storage("php.xml")})
    static class NoverifyProjectConfigurationManager extends NoverifyConfigurationBaseManager {
    }

    @State(name = "Noverify", storages = {@Storage(value = "php.xml")})
    static class NoverifyAppConfigurationManager extends NoverifyConfigurationBaseManager {
    }
}