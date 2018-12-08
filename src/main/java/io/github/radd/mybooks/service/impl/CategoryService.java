package io.github.radd.mybooks.service.impl;

import io.github.radd.mybooks.domain.BookTag;
import io.github.radd.mybooks.domain.Category;
import io.github.radd.mybooks.domain.dto.BookTagDTO;
import io.github.radd.mybooks.domain.dto.CategoryDTO;
import io.github.radd.mybooks.domain.repository.BookTagRepository;
import io.github.radd.mybooks.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Transactional
    public Category addCategory(CategoryDTO categoryDTO)
            throws Exception {

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setBookCount(0L);
        category.setParent(categoryDTO.getParent());

        return categoryRepo.save(category);
    }

    public List<Category> getAllCats() {
        return categoryRepo.findAll();
    }

    public List<Category> getAllCatsForm() {
        List<Category> cats = categoryRepo.findFirstParents();

        List<Category> catsForm = new ArrayList<>();

        for( Category cat : cats) {
            catForm(cat, catsForm, "");
        }

        return catsForm;
    }

    private void catForm(Category cat, List<Category> cats, String prefix) {
        Category newCat = new Category();
        newCat.setId(cat.getId());
        newCat.setName(prefix + cat.getName());
        cats.add(newCat);

        prefix +="\u00A0\u00A0\u00A0\u00A0";

        for(Category c : cat.getChildren()) {
            catForm(c, cats, prefix);
        }
    }



}
