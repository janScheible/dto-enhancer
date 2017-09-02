package com.scheible.dtoenhancer.maven;

import com.scheible.dtoenhancer.api.Config;
import com.scheible.dtoenhancer.api.Launcher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Inspiration: https://github.com/JodaOrg/joda-beans-maven-plugin
 * 
 * @author sj
 */
public abstract class AbstractDtoEnhancerMojo extends AbstractMojo {

    @Parameter(property = "dto-enhancer.skip", defaultValue = "false", required = true)
    protected boolean skip;

    @Parameter(property = "dto-enhancer.mandatory-this", defaultValue = "false", required = true)
    protected boolean mandatoryThis;

    @Parameter(property = "dto-enhancer.manadatory-final", defaultValue = "false", required = true)
    protected boolean mandatoryFinal;

    @Parameter(property = "dto-enhancer.verbose", defaultValue = "false", required = true)
    protected boolean verbose;

    @Parameter(property = "dto-enhancer.baseDir", defaultValue = "${project.basedir}/src/main/java", required = true)
    protected File baseDir;

    public void execute(boolean verificationOnly) throws MojoExecutionException, MojoFailureException {
        if (this.skip) {
            getLog().warn("Skipping enhance");
        } else {
            getLog().info("baseDir: " + baseDir);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                Config config = new Config();
                config.setMandatoryFinal(mandatoryFinal);
                config.setOut(new PrintStream(byteArrayOutputStream, false));
                config.setVerificationOnly(verificationOnly);
                config.setMandatoryThis(mandatoryThis);
                config.setVerbose(verbose);

                boolean changed = Launcher.enhance(baseDir, config);
                config.getOut().flush();
                getLog().info(new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8));

                if (verificationOnly && changed) {
                    throw new MojoFailureException ("Some DTOs need an update...");
                }
            } catch (Exception ex) {
                throw new MojoExecutionException("Unable to enhance DTOs", ex);
            }
        }
    }

}
