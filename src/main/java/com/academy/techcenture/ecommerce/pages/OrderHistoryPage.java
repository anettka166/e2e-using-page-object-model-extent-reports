package com.academy.techcenture.ecommerce.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.testng.Assert.assertTrue;

public class OrderHistoryPage extends HomePage{

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    private static final String INVOICES_FOLDER = "src/main/resources/invoices";

    @FindBy(xpath = "//h1[contains(@class,'page-heading')]")
    private WebElement pageHeading;

    @FindBy(xpath = "//table[@id='order-list']//tbody/tr/td[contains(@class,'history_link')]")
    private List<WebElement> orderReferences;

    public void verifyOrderHistory(Map<String, String> data) throws IOException, InterruptedException {

        boolean foundReference = false;
        for (int i = 0; i < orderReferences.size(); i++) {
            if (orderReferences.get(i).getText().equals( data.get("orderReference"))){
                foundReference = true;
                break;
            }
        }
        assertTrue(foundReference, "Order Reference was not found in the order history table");

        String invoicePdfLink = "//table[@id='order-list']//tbody/tr/td/a[contains(text(),'"+data.get("orderReference")+"')]/../following-sibling::td[@class='history_invoice']/a";
        WebElement pdfInvoiceLink = driver.findElement(By.xpath(invoicePdfLink));

        pdfInvoiceLink.click();

        Thread.sleep(4000);

        verifyPdfInvoiceFile(data);


//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//
//        FutureTask<String> downloadProcess = new FutureTask<>( new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    verifyPdfInvoiceFile(data);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "Download process is complete");
//
//        executorService.submit(downloadProcess);
//
//        while (true){
//            if (downloadProcess.isDone()){
//                System.out.println("-----Download, reading and verification process has finished!");
//                executorService.shutdown();
//                break;
//            }
//
//            if (!downloadProcess.isDone()){
//                System.out.println("-----Still waiting for download, reading and verification to finish");
//            }
//        }
    }

    private void verifyPdfInvoiceFile(Map<String,String> data) throws IOException {
        String invoiceFileName = commonUtils.getRecentFileName(INVOICES_FOLDER);
        String pdfContent = commonUtils.readPdfDocument(invoiceFileName);

        Assert.assertTrue(pdfContent.contains(data.get("orderReference")));
        Assert.assertTrue(pdfContent.contains(data.get("Name")));
        Assert.assertTrue(pdfContent.contains(data.get("TotalCost")));

    }

}
