package br.com.tiago.restapiwithspringboot.controller;


import br.com.tiago.restapiwithspringboot.entity.Customer;
import br.com.tiago.restapiwithspringboot.entity.Product;
import br.com.tiago.restapiwithspringboot.exception.ResponseGenericException;
import br.com.tiago.restapiwithspringboot.service.CustomerService;
import br.com.tiago.restapiwithspringboot.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customer")         // no postman eu acho os métodos por esse caminho declarado aqui
@CrossOrigin(value = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/list")
    @Operation(summary = "Lista os clientes")
    public ResponseEntity<Object> getInfoCustomers() { // aqui devolve a informação pro front // a solicitação é feita para o endpoint, o endpoint transfere pra camada service e a camada service faz as coisas acontecer e devolve a resposta pra controller
        List<Customer> result = customerService.getInfoCustomers();
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
    }
    @PostMapping(value = "/create")
    @Operation(summary = "Cria os clientes")
    public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
        Customer result = customerService.saveCustomer(customer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
    }

    @DeleteMapping(value = "/delete/{idCustomer}") // Aqui o idProduct é o id do meu item, no postman, se eu colocar <http://localhost:8080/api/v1/customer/delete/1> ele vai deletar o item 1
    @Operation(summary = "Deleta os clientes")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long idCustomer) {
        HashMap<String, Object> result = customerService.deleteCustomer(idCustomer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
    }

    @GetMapping(value = "/findCustomer/{idCustomer}")
    @Operation(summary = "Acha os clientes por id")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long idCustomer){
        Customer result = customerService.findCustomerById(idCustomer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
    }


    @PutMapping(value = "/update")//metodo put altera toodo o objeto
    @Operation(summary = "Altera os clientes")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer) {
        Customer result = customerService.updateCustomer(customer);
        return ResponseEntity.ok().body(ResponseGenericException.response(result));
    }

    @SpringBootApplication
    @OpenAPIDefinition(info = @Info(title = "Rest API Fatec", description = "REST API Fatec SpringBoot") )
    public class RestApiWithSpringBootApplication{

        public static void main(String[] args){
            SpringApplication.run(RestApiWithSpringBootApplication.class, args);}
    }

}
