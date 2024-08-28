package com.mitocode.repo;

import com.mitocode.model.User;


public interface IUserRepo extends IGenericRepo<User, Integer> {

    //puedo crear un Query derivado que me devuelva un solo usuario, a partir de su nombre de usuario
	//Verifica que el nombre de usuario existe
    User findOneByUsername(String username);
}
