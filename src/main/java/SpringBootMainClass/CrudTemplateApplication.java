package SpringBootMainClass;

import com.Boyce.CrudTemplate.controller.CrudController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author LiuY
 * @Date 2020/4/9 10:46
 * @Version V1.0
 */
@SpringBootApplication(scanBasePackages = {"com.Boyce.CrudTemplate.config"})
public class CrudTemplateApplication {
    public static void main(String[] args) {
        CrudController.addCRUDInterface("/test/test",null);
        SpringApplication.run(CrudTemplateApplication.class,args);
    }
}
