package main.java.com.thoughtworks;

import main.java.com.thoughtworks.external.FasterMessageGateway;
import main.java.com.thoughtworks.requests.CustomerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static main.java.com.thoughtworks.handlers.Handlers.findHandler;


public class Bank {
    private FasterMessageGateway emailSender;

    private List<Customer> customerList = new ArrayList<>();

    public Bank(FasterMessageGateway emailSender) {
        this.emailSender = emailSender;
    }

    public boolean addCustomer(Customer customer) {
        if (!isExist(customer) && isValid(customer.getNickName())) {
            customerList.add(customer);
            emailSender.sendMessage(customer.getNickName() + "@thebank.com", "Dear " + customer.getNickName() + ", Welcome to the Bank");
            return true;
        }
        return false;
    }

    public void handleRequest(CustomerRequest request) {
        if (customerList.contains(request.getCustomer()))
            findHandler(request.getRequestType()).handle(request);
        if (shouldBePremiumCustomer(request.getCustomer())) {
            request.getCustomer().setPremiumCustomer(true);
            emailSender.sendMessage("manager@thebank.com", request.getCustomer().getNickName() + " is now a premium customer");
        }
    }

    private boolean isValid(String nickName) {
        Pattern pattern = Pattern.compile("^[a-z0-9]+$");
        return pattern.matcher(nickName).matches();
    }

    private boolean isExist(Customer customer) {
        for (Customer temp : customerList) {
            if (temp.getNickName().equals(customer.getNickName())) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldBePremiumCustomer(Customer customer) {
        return !customer.isPremiumCustomer() && customer.getBalance() >= 40000;
    }

}
