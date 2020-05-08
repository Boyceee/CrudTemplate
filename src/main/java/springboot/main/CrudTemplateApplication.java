package springboot.main;

import com.boyce.crud.template.controller.CrudController;
import com.boyce.crud.template.model.TestTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description
 * @author Boyce
 * @date 2020/4/9 10:46
 * @version V1.0
 */
@SpringBootApplication(scanBasePackages = {"com.boyce.crud.template"})
public class CrudTemplateApplication {
    public static void main(String[] args) {
        CrudController.addCrudInterface("/test/dispatch",new TestTable());
        SpringApplication.run(CrudTemplateApplication.class,args);
    }
}
