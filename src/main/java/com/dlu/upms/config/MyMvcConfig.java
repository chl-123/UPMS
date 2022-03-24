package com.dlu.upms.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc 接管springmvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer{
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer(){
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/system/role").setViewName("admin/system/role");
                registry.addViewController("/system/user").setViewName("admin/system/user");
                registry.addViewController("/teacherInfo/teacher").setViewName("admin/teacherInfo/teacher");
                registry.addViewController("/studentInfo/student").setViewName("admin/studentInfo/student");
                registry.addViewController("/studentInfo/projectSelect").setViewName("admin/studentInfo/projectSelect");
                registry.addViewController("/projectInfo/trainbase").setViewName("admin/projectInfo/trainbase");
                registry.addViewController("/projectInfo/expense").setViewName("admin/projectInfo/expense");
                registry.addViewController("/projectInfo/project").setViewName("admin/projectInfo/project");
                registry.addViewController("/appraise/appraiseStudent").setViewName("admin/appraise/appraiseStudent");
                registry.addViewController("/appraise/appraiseTeacher").setViewName("admin/appraise/appraiseTeacher");
                registry.addViewController("/admin").setViewName("/admin/admin");
                registry.addViewController("/login").setViewName("/login");
                registry.addViewController("/register").setViewName("/register");
                registry.addViewController("/teacher/project").setViewName("teacher/project");
                registry.addViewController("/teacher/appraise").setViewName("teacher/appraise");
                registry.addViewController("/teacher/feedback").setViewName("teacher/feedback");
                registry.addViewController("/student/feedback").setViewName("student/feedback");
                registry.addViewController("/teacher/score").setViewName("teacher/score");
                registry.addViewController("/teacher/student").setViewName("teacher/student");
                registry.addViewController("/student/project").setViewName("student/project");
                registry.addViewController("/student/appraise").setViewName("student/appraise");
                registry.addViewController("/student/selected").setViewName("student/selected");
                registry.addViewController("/basicInfo/student").setViewName("basicInfo/student");
                registry.addViewController("/basicInfo/project").setViewName("basicInfo/project");
                registry.addViewController("/basicInfo/teacher").setViewName("basicInfo/teacher");
                registry.addViewController("/basicInfo/feedbackStudent").setViewName("basicInfo/feedbackStudent");
                registry.addViewController("/basicInfo/feedbackTeacher").setViewName("basicInfo/feedbackTeacher");
                registry.addViewController("/basicInfo/selectCount").setViewName("basicInfo/selectCount");
                registry.addViewController("/teacher").setViewName("teacher/teacher");
                registry.addViewController("/student").setViewName("student/student");

            }
        };
        return webMvcConfigurer;
    }

}
