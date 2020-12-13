package com.example.customer.controller.grpc;
import com.example.customer.entities.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.*;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
public class CustomerServiceImpl extends customerServiceGrpc.customerServiceImplBase{
    private final CustomerService customerService;

    @Autowired
    public CustomerServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseStreamObserver) {
        List<Customer> customers = customerService.getAllCustomers();

        List<ProtoCustomer> protoCustomers = new ArrayList<>();
        for (Customer customer: customers) {
            ProtoCustomer protoCustomer = ProtoCustomer.newBuilder()
                    .setFirstName(customer.getFirstName())
                    .setLastName(customer.getLastName())
                    .build();
            protoCustomers.add(protoCustomer);
        }
        GetResponse response = GetResponse.newBuilder().addAllCustomers(protoCustomers).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void create(CreateRequest request, StreamObserver<CreateResponse> responseStreamObserver){
        ProtoCustomer protoCustomer = request.getCustomer();
        Customer customer = new Customer(UUID.randomUUID(), protoCustomer.getFirstName(), protoCustomer.getLastName());
        customerService.saveCustomer(customer);
        CreateResponse response = CreateResponse.newBuilder()
                .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver){
        UUID customerId = UUID.fromString(request.getCustomerId());
        customerService.deleteCustomerById(customerId);

        DeleteResponse response = DeleteResponse.newBuilder()
                .setReport("Customer"  + customerId.toString() + "was deleted")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }
}
