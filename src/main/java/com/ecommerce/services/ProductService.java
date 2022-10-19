package com.ecommerce.services;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.models.ProductEntity;
import com.ecommerce.models.UserEntity;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.security.JwtUtils;


@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    public ProductEntity createProduct(ProductEntity product, HttpServletRequest request){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
        UserEntity user = userRepo.findByUsername(username).get();
        product.setUser(user);
        product.setCreatedAt(new Date());
        product.setUpdateAt(new Date());
        return (ProductEntity) productRepo.save(product);
    }

    public ProductEntity updateProduct(ProductEntity product){
        UserEntity user = userRepo.findById(product.getUser().getId()).get();
        product.setUser(user);
        product.setUpdateAt(new Date());
        return (ProductEntity) productRepo.save(product);
    }

    public Iterable<ProductEntity> findAllProduct(){
        return productRepo.findAll();
    }

    public ProductEntity findProductById(long id){
        Optional<ProductEntity> product  = productRepo.findById(id);
        if(product.isPresent()){

            return product.get();
        }

        return null;
    }

   
    public void deleteProductById(long id){
        productRepo.deleteById(id);
    }
}
