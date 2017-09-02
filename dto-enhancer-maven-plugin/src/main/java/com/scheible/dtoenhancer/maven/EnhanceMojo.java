package com.scheible.dtoenhancer.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 *
 * @author sj
 */
@Mojo(name = "enhance", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE, threadSafe = true)
public class EnhanceMojo extends AbstractDtoEnhancerMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        execute(false);
    }
}
