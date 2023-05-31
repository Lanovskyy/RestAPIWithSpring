package br.com.tiago.restapiwithspringboot.service;


import br.com.tiago.restapiwithspringboot.entity.Category;
import br.com.tiago.restapiwithspringboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getInfoCategory() {
        return categoryRepository.findAll();
    } // traz uma lista de category

    public Category saveCategory(Category category) {
        if (validateCategory(category)) {      // se a validação for verdadeira, cadastre
            return categoryRepository.saveAndFlush(category); // saveandflush confirma o que ele fez, é mais rápido que o método só save
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O usuário é obrigatório e deve ser declarado!");
        }
    }

    public HashMap<String, Object> deleteCategory(Long idCategory) {
        Optional<Category> category = // criei uma variavel chamada category. O optional é uma classe do java que procura os objetos
                Optional.ofNullable(categoryRepository.findById(idCategory).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, //Optional.ofNullable ---> encontre o produto pelo id, se não for nulo ele pega o id, se for nulo, ele da a mensagem
                        "Usuário não encontrado!")));                           // o throw é o tratamento de exceções

        categoryRepository.delete(category.get());
        HashMap<String, Object> result = new  HashMap<String, Object> (); // HashMap --- > monto uma variável da forma que eu quiser
        result.put("result", "Categoria: " + category.get().getNameCategory() + " excluída com sucesso!");
        return result;
    }

    public Category findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada!"));
    }

    public Category updateCategory(Category category) {
        if (category.getIdCategory() == null || category.getIdCategory() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O ID da categoria é obrigatório na atualização!");
        }

        if (validateCategory(category)) {
            return categoryRepository.saveAndFlush(category);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Linha 57 de CategoryService.java, Mensagem de erro");
        }
    }


    public Boolean validateCategory (Category category) {

        if (category.getNameCategory() != null &&
                category.getDescriptionCategory() != null)
              {
            return true;
        } else {
            return false;
        }
    }

}

