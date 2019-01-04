package somnous.y.templateservice.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import somnous.y.templateservice.configuration.TemplateConfiguration;

/**
 * @version 1.0 created by yuanchangxin_fh on 2019/1/4 16:06
 */
@RestController
public class TemplateService {

    @Autowired
    private Binding binding;

    private static final String suffix = ".groovy";

    @RequestMapping("/exeGroovy")
    public Object exeGroovy(String name){
        GroovyShell shell = new GroovyShell(binding);
        String content = TemplateConfiguration.groovySource.get(name+suffix);
        Script parse = shell.parse(content);
        Object run = parse.run();
        return run;
    }


    @RequestMapping("/reload")
    public Object reload(String name){

        return null;
    }
}
