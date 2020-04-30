package SpringBootMainClass;

import com.Boyce.crud.template.controller.CrudController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/9 10:46
 * @Version V1.0
 */
@SpringBootApplication(scanBasePackages = {"com.Boyce.crud.template"})
public class CrudTemplateApplication {
    public static void main(String[] args) {
        CrudController.addCrudInterface("/test/dispatch","test");
        SpringApplication.run(CrudTemplateApplication.class,args);
    }
}
