package me.parkinje.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDeveloperApplication  {

    // implements CommandLineRunner 삭송받을 시 스프링 컨테이너의 bean 을 주입받을수 있는 run 활성화

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class,args);
    }

//    @Autowired
//    DataSource dataSource;   // 데이터소스 주입
//
//    @Override
//    public void run(String... args) throws SQLException {
//        System.out.println("스프링부트가 관리하는 빈을 사용할 수 있다.");
//
//        Connection conn=dataSource.getConnection();
//        PreparedStatement ps=conn.prepareStatement("select * from dual");
//        ResultSet rs=ps.executeQuery();
//        while(rs.next()){
//            rs.getInt("");
//            rs.getString("");
//        }
//        rs.close();
//        ps.close();
//        conn.close();
//    }
}
