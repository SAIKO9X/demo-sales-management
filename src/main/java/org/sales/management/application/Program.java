package org.sales.management.application;

import org.sales.management.model.entities.Department;
import org.sales.management.model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        // Criar uma instância de Department
        Department dept = new Department(1, "Computers");

        // Criar uma instância de Seller com o departamento associado
        Seller seller = new Seller(1, "Bob Brown", "bob@gmail.com", new Date(98, 4, 21), 1000.00, dept);

        // Exibir informações do Department
        System.out.println("Department:");
        System.out.println(dept);

        // Exibir informações do Seller
        System.out.println("\nSeller:");
        System.out.println(seller);

        // Testar equals e hashCode
        System.out.println("\nTesting equals and hashCode:");
        System.out.println("Department hashCode: " + dept.hashCode());
        System.out.println("Seller hashCode: " + seller.hashCode());
    }
}
