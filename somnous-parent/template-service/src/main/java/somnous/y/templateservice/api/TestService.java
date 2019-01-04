package somnous.y.templateservice.api;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.springframework.stereotype.Component;

/**
 * @version 1.0 created by yuanchangxin_fh on 2019/1/4 14:56
 */
@Component
public class TestService {


    public String testQuery(long id){
        return "Test query success, id is " + id;
    }

    public static void main(String[] args) {
        Binding binding = new Binding();
        binding.setVariable("variable", "variable");
        binding.setProperty("property","property");
        binding.setVariable("testService",new TestService());
        GroovyShell shell = new GroovyShell(TestService.class.getClassLoader(),binding);
        /*Script parse = null;
        try {
            parse = shell.parse(new File("D:\\idea_workspace\\363Tec\\363Tec\\somnous-parent\\template-service\\src\\main\\java\\somnous\\y\\templateservice\\Template.groovy"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Script testQuery = shell.parse("def query = testService.testQuery(2L);\n" +
                "query");
        Object run = testQuery.run();
        System.out.println(run);
    }
}
