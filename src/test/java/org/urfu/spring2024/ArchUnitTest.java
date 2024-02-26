package org.urfu.spring2024;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ArchUnitTest {
    private final JavaClasses classes = new ClassFileImporter().importPackages("org.urfu.spring2024");

    @Test
    @DisplayName("Требований слоеной архитектуры соблюдены")
    void testProjectArchitecture() {
        ArchRule rule = Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("..domain..")
                .layer("app").definedBy("..app..")
                .layer("extern").definedBy("..extern..")
                .whereLayer("app").mayOnlyBeAccessedByLayers("app", "extern")
                .whereLayer("extern").mayOnlyBeAccessedByLayers("extern");

        rule.check(classes);
    }
}
