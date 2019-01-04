package somnous.y.templateservice.configuration;

import groovy.lang.Binding;
import lombok.Data;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0 created by yuanchangxin_fh on 2019/1/3 16:52
 */
@Data
@ConfigurationProperties("template.git")
@Configuration
public class TemplateConfiguration implements InitializingBean {
    @Autowired
    private ApplicationContext context;

    public static Map<String,String> groovySource = new HashMap<>();
    private String uri;
    private String username;
    private String password;
    private String basedir;


    @Bean
    public Binding binding(){
        Binding binding = new Binding();
        Map<String, Object> allBeans = context.getBeansOfType(Object.class);
        for (String key:allBeans.keySet()) {
            binding.setVariable(key,allBeans.get(key));
        }
        return binding;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        File file = new File(basedir);
        delFile(file);
        file.mkdir();
        CloneCommand cmd = new CloneCommand().setURI(uri).setDirectory(file).
                setCredentialsProvider(new UsernamePasswordCredentialsProvider(getUsername(), getPassword()));
        cmd.call();
        File f = new File(basedir+File.separator+"groovy"+File.separator+"Template.groovy");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            ArrayList<Byte> list = new ArrayList<>();
            while (read!=-1){
                buffer.flip();
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    list.add(b);
                }
                buffer.clear();
                read = channel.read(buffer);
            }
            groovySource.put(f.getName(),new String(buffer.array()).trim());
        }finally {
            inputStream.close();
        }
    }

    private static void  delFile(File... files){
        for (File file:files) {
            File[] chf = file.listFiles();
            if(Objects.nonNull(chf)&&chf.length>0){
                delFile(chf);
            }
            file.delete();
        }
    }
}


