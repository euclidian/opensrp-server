package org.opensrp.connector.openmrs.service;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.opensrp.form.domain.FormSubmission;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;


public class TestResourceLoader {
	protected String openmrsOpenmrsUrl;
	protected String openmrsUsername;
	protected String openmrsPassword;
	protected String formDirPath;

	public TestResourceLoader() throws IOException {
		Resource resource = new ClassPathResource("/opensrp.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		openmrsOpenmrsUrl = props.getProperty("openmrs.url");
		openmrsUsername = props.getProperty("openmrs.username");
		openmrsPassword = props.getProperty("openmrs.password");
		formDirPath = props.getProperty("form.directory.name");
	}
	
	protected FormSubmission getFormSubmissionFor(String formName, Integer number) throws JsonIOException, IOException{
		ResourceLoader loader=new DefaultResourceLoader();
		String path = loader.getResource(formDirPath).getURI().getPath();
		File fsfile = new File(path+"/"+formName+"/form_submission"+(number==null?"":number)+".json");
		return new Gson().fromJson(new FileReader(fsfile), FormSubmission.class);		
	}
	
	protected FormSubmission getFormSubmissionFor(String formName) throws JsonIOException, IOException{
		return getFormSubmissionFor(formName, null);		
	}
}
