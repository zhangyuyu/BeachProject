package main.java.com.thoughtworks.handlers;


import main.java.com.thoughtworks.exception.OverdrawException;
import main.java.com.thoughtworks.requests.CustomerRequest;

public interface Handler {
    double handle(CustomerRequest customerRequest) throws OverdrawException;
}