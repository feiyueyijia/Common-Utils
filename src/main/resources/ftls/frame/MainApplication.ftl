package ${BasePackageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("${BasePackageName}.mapper")
@ComponentScan(basePackages = {"${BasePackageName}.**","com.yfny.utilscommon"})
public class ${ProjectName}Application {

    public static void main(String[] args) {
        SpringApplication.run(${ProjectName}Application.class, args);
    }

}
