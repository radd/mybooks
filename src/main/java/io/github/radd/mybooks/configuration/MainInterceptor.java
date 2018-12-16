package io.github.radd.mybooks.configuration;

import io.github.radd.mybooks.domain.repository.UserRepository;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MainInterceptor implements HandlerInterceptor {

    @Autowired
    private AutoLogin autoLogin;

    @Autowired
    AuthUser auth;

    @Autowired
    CategoryService categoryService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!autoLogin.isAlreadySetup()){
            autoLogin.signUp(request);
        }

        return true;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.getModelMap().addAttribute("auth", auth);
            modelAndView.getModelMap().addAttribute("categoryList", categoryService.getAllCatsList());

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
