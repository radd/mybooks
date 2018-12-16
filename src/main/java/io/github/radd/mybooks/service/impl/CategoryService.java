package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.dto.CategoryDTO;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.domain.repository.CategoryRepository;
import io.github.radd.mybooks.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Transactional
    public Category addCategory(CategoryDTO categoryDTO) {

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setBookCount(0L);
        category.setParent(categoryDTO.getParent()); //TODO if parent exists
        category.setSlug(getUniqueSlug(categoryDTO.getName()));

        return categoryRepo.save(category);
    }

    private String getUniqueSlug(String text) {
        String slug = WebUtils.Slug.makeSlug(text);

        slug = !slug.equals("") ? slug : "cat"; //TODO duplicate default slug name - all entity with slug

        Category book = categoryRepo.findBySlug(slug);

        String tempSlug = slug;
        int suffix = 2;
        while(book != null) {
            slug = tempSlug + "-" + suffix;
            book = categoryRepo.findBySlug(slug);
            suffix++;
        }

        return slug;
    }

    public List<Category> getAllCats() {
        return categoryRepo.findAll();
    }


    //Category Form - category list with parent-children order and intend

    public List<Category> getAllCatsForm() {
        //List<Category> allCats = categoryRepo.findAll(new Sort(Sort.Direction.ASC, "name"));
        List<Category> allCats = categoryRepo.findAllByOrderByNameAsc();
        List<Category> cats = allCats.stream()
                .filter(x -> null == x.getParent()).collect(Collectors.toList());
        allCats.removeAll(cats);

        List<Category> catsForm = new ArrayList<>();

        for( Category cat : cats) {
            catForm(cat,"",  catsForm, allCats);
        }

        return catsForm;
    }

    private void catForm(Category cat, String prefix, List<Category> cats, List<Category> allCats) {
        Category newCat = new Category();
        newCat.setId(cat.getId());
        newCat.setName(prefix + cat.getName());
        cats.add(newCat);

        prefix +="\u00A0\u00A0\u00A0\u00A0";

        List<Category> children = allCats.stream()
                .filter(x -> cat.getId() == x.getParent().getId()).collect(Collectors.toList());
        allCats.removeAll(children);

        for(Category c : children) {
            catForm(c, prefix, cats, allCats);
        }
    }

    //TODO move to javascript
    public String getAllCatsList() {
        List<Category> allCats = categoryRepo.findAllByOrderByNameAsc();
        List<Category> cats = allCats.stream()
                .filter(x -> null == x.getParent()).collect(Collectors.toList());
        allCats.removeAll(cats);

        StringBuilder catsForm = new StringBuilder();


        for( Category cat : cats) {
            catsForm.append("<li>");
            catList(cat,  catsForm, allCats);
            catsForm.append("</li>");
        }

        return catsForm.toString();
    }

    private void catList(Category cat, StringBuilder output, List<Category> allCats) {

        output.append("<a href='/mybooks/cat/" + cat.getSlug() + "'>" + cat.getName()+ "</a>");


        List<Category> children = allCats.stream()
                .filter(x -> cat.getId() == x.getParent().getId()).collect(Collectors.toList());
        allCats.removeAll(children);

        if(children.size() >0)
            output.append("<ul>");
        for(Category c : children) {
            output.append("<li>");
            catList(c, output, allCats);
            output.append("</li>");
        }
        if(children.size() >0)
            output.append("</ul>");
    }



}
