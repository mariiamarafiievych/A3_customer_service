package customer.controller;

import customer.entities.Customer;
import customer.service.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> show() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> showById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public void saveCustomer(@RequestBody Customer customer){
        //customer.setId(UUID.randomUUID());
        customerService.saveCustomer(customer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws NotFoundException {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
