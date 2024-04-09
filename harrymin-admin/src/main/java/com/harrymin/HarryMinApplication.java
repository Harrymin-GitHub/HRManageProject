package com.harrymin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author harrymin
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HarryMinApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HarryMinApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  HR管理系统启动成功   ლ(´ڡ`ლ)ﾞ   \n" +
                " .                .    .————————————————.     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " |————————————————|    |  |   |  |   |  |     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " | (   )   (   )  |    |  |   |  |   |  |     \n" +
                " .                .    |  |   |  |   |  |");
    }
}
