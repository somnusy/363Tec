package somnous.y.templateservice.service;

import lombok.Data;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @version 1.0 created by yuanchangxin_fh on 2019/1/3 16:52
 */
@Data
@ConfigurationProperties("template.git")
@Configuration
public class TemplateConfiguration implements InitializingBean {
    private String uri;
    private String username;
    private String password;
    private String basedir;


    @Override
    public void afterPropertiesSet() throws Exception {
        /*CloneCommand clone = this.gitFactory.getCloneCommandByCloneRepository()
                .setURI(getUri()).setDirectory(getBasedir());
        configureCommand(clone);
        try {
            return clone.call();
        }
        catch (GitAPIException e) {
            deleteBaseDirIfExists();
            throw e;
        }*/
        File file = new File(basedir);
        file.mkdir();
        CloneCommand cmd = new CloneCommand().setURI(uri).setDirectory(file).
                setCredentialsProvider(new UsernamePasswordCredentialsProvider(getUsername(), getPassword()));
        cmd.call();
    }
}


