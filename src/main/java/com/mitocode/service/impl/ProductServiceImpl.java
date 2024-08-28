package com.mitocode.service.impl;

import com.mitocode.model.Product;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IProductRepo;
import com.mitocode.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    private final IProductRepo repo;

    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return repo;
    }
}
