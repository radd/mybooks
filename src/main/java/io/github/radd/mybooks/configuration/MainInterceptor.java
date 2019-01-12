package io.github.radd.mybooks.configuration;

import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.service.impl.CategoryService;
import io.github.radd.mybooks.utils.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private BookTagRepository bookTagRepo;

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

            Pageable tagsPage = new PageRequest(0,100, Sort.Direction.ASC, "name");
            modelAndView.getModelMap().addAttribute("tagList", bookTagRepo.findAll(tagsPage).getContent());

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
