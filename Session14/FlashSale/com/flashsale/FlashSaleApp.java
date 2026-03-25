package Session14.FlashSale.com.flashsale;


import Session14.FlashSale.com.flashsale.dao.ProductDAO;
import Session14.FlashSale.com.flashsale.dao.UserDAO;
import Session14.FlashSale.com.flashsale.service.OrderService;
import Session14.FlashSale.com.flashsale.service.ReportService;
import Session14.FlashSale.com.flashsale.util.DatabaseInitializer;

import java.sql.SQLException;

public class FlashSaleApp {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderService orderService = new OrderService();
        ReportService reportService = new ReportService();

        try {
            try {
                userDAO.addUser("john_doe", "john@example.com");
                System.out.println("User added.");
            } catch (SQLException e) {
                System.out.println("User already exists or error adding user: " + e.getMessage());
            }
            
            try {
                productDAO.addProduct("Flash Sale Item", 100.0, 10);
                System.out.println("Product added.");
            } catch (SQLException e) {
                 System.out.println("Product already exists or error adding product: " + e.getMessage());
            }

            System.out.println("\nPlacing order...");
            orderService.placeOrder(1, 1, 1); 
            

            System.out.println("\nGenerating Reports...");
            reportService.getTopBuyers();
            reportService.getProductRevenue();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
