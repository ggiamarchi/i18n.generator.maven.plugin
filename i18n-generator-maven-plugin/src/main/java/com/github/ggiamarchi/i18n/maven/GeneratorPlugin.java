package com.github.ggiamarchi.i18n.maven;

import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.impl.StaticLoggerBinder;

import com.github.ggiamarchi.i18n.GeneratorLauncher;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GeneratorPlugin extends AbstractMojo {

	@Parameter(required = true)
	private String bundles;

	@Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true)	
	private String srcDirectory;

	@Parameter(defaultValue = "${project.build.resources}", readonly = true)	
	private List<Resource> resourcesDirectories;
	
	@Parameter(defaultValue = "${project.build.directory}/generated-sources/i18n")
    private String outputDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {

	    // bind slf4j to maven log
	    StaticLoggerBinder.getSingleton().setLog(getLog());
	    
	    String [] res = new String[resourcesDirectories.size()];
	    for (int i = 0 ; i < res.length ; i++) {
	    	res[i] = resourcesDirectories.get(i).getDirectory();
	    }
	    
	    new GeneratorLauncher().execute(bundles.split(","), srcDirectory, res, outputDirectory);

    }

}
