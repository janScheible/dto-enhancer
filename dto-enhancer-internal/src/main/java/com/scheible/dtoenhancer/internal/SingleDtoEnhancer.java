package com.scheible.dtoenhancer.internal;

import com.scheible.dtoenhancer.api.Config;
import com.google.common.io.Files;
import com.scheible.dtoenhancer.internal.enhancement.MethodEnhancement;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
public class SingleDtoEnhancer {

    public static boolean enhance(File file, Config config) throws IOException {
        if (config.isVerbose()) {
            config.getOut().println("* Processing '" + file.getAbsolutePath() + "'...");
        }

        String javaSource = Files.asCharSource(file, Charset.defaultCharset()).read();
        JavaClassSource javaClassSource = Roaster.parse(JavaClassSource.class, javaSource);
        AnnotationSource<JavaClassSource> autoValueAnnotation = javaClassSource.getAnnotation(config.getAnnotationClassFullName());
        if (autoValueAnnotation == null) {
            if (config.isVerbose()) {
                config.getOut().println("  - No @" + config.getAnnotationClassFullName() + " was found. Skipping...");
            }
            return false;
        }

        boolean changed = false;
        for (FieldSource<JavaClassSource> field : javaClassSource.getFields()) {
            boolean setterChanged = MethodEnhancement.enhanceSetter(javaClassSource, field, config.isMandatoryFinal()).apply(javaClassSource);
            changed = changed || setterChanged;

            if (config.isVerbose() && setterChanged) {
                config.getOut().println("  - setter for field '" + field.getName() + "' method needed update");
            }
        }

        for (FieldSource<JavaClassSource> field : javaClassSource.getFields()) {
            boolean getterChanged = MethodEnhancement.enhanceGetter(javaClassSource, field, config.isMandatoryFinal(), config.isMandatoryThis()).apply(javaClassSource);
            changed = changed || getterChanged;

            if (config.isVerbose() && getterChanged) {
                config.getOut().println("  - getter for field '" + field.getName() + "' method needed update");
            }
        }

        boolean equalsChanged = MethodEnhancement.enhanceEquals(javaClassSource, config.isMandatoryFinal(), config.isMandatoryThis()).apply(javaClassSource);
        changed = changed || equalsChanged;
        if (config.isVerbose() && equalsChanged) {
            config.getOut().println("  - equals() method needed update");
        }
        
        boolean hashCodeChanged = MethodEnhancement.enhanceHashcode(javaClassSource, config.isMandatoryFinal(), config.isMandatoryThis()).apply(javaClassSource);
        changed = changed || hashCodeChanged;
        if (config.isVerbose() && hashCodeChanged) {
            config.getOut().println("  - hashCode() method needed update");
        }

        if (changed) {
            if(!config.isVerificationOnly()) {
                if(config.isVerbose()) {
                    config.getOut().println("  --> updating");
                } else {
                    config.getOut().println("* Updating '" + file.getAbsolutePath() + "'...");
                }
                
                Files.asCharSink(file, Charset.defaultCharset()).write(javaClassSource.toString());
            } else {
                if(config.isVerbose()) {
                    config.getOut().println("  --> varification failed, updated needed");
                }
            }
        } else {
            if(config.isVerbose()) {
                if(!config.isVerificationOnly()) {
                    config.getOut().println("  --> no update needed");
                } else {
                    config.getOut().println("  --> verification succeeded");
                }
            }
        }
        
        return changed;
    }
}
