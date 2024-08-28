package com.mitocode.service;

import com.mitocode.model.Category;
import com.mitocode.repo.ICategoryRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.impl.CRUDImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

    //@Autowired
    private final ICategoryRepo repo;// = new CategoryRepository();

    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Category> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Category> findByNameLike(String name) {
        return repo.findByNameLike("%" + name + "%");
    }

    @Override
    public List<Category> findByNameAndEnabled(String name, boolean enabled) {
        return repo.findByNameAndEnabled(name, enabled);
    }

    @Override
    public List<Category> getNameAndDescription1(String name, String description) {
        return repo.getNameAndDescription2(name, description); //Internamente manda a llamar al 2
    }

    @Override
    public List<Category> getNameSQL(String name) {
        return repo.getNameSql(name);

    }

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Category> findAllOrder(String param) {                //Devuelve un bloque ascendente //devuelve un bloque descendente
        Sort.Direction direction = param.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction, "name"/*,"description"*/));
    }

}

   /* @Override
    public List<Object[]> getNameSQL(String name) {
        return repo.getNameSql(name);
    }


       @Override
    public List<Category> findAllOrder(String param) {
        Sort.Direction direction = param.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction, "name"));
    }

    */






    /*public Category saveAndValid(Category category){
        if(category.getIdCategory() > 0){
            System.out.println("Category ID is the DB");
            return null;
        }else{
            return repository.save(category);
        }
    }*/
