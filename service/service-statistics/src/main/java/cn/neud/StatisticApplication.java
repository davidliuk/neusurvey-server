package cn.neud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.neud.neusurvey")
@ComponentScan({"springfox.documentation.schema"})
@ComponentScan(basePackages = "cn.neud.neusurvey")
public class StatisticApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StatisticApplication.class);
    }
}