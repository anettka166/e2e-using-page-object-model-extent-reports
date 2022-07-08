package com.academy.techcenture.ecommerce.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.testng.Assert.*;

public class SummaryTabPage extends HomePage {

    public SummaryTabPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[contains(.,'Shopping-cart summary')][1]")
    private WebElement cartHeader;

    @FindBy(xpath = "//ul[@id='order_step']/li")
    private List<WebElement> shoppingToDo;

    @FindBy(xpath = "//li[contains(@class,'first')]//span[1]")
    private WebElement summaryTab;

    @FindBy(xpath = "//li[contains(@class,'four')]//span[1]")
    private WebElement shippingTab;

    @FindBy(xpath = "//table[@id='cart_summary']//tr//th")
    private List<WebElement> summaryOptions;

    @FindBy(css = "td[class='cart_description'] p[class='product-name'] a")
    private WebElement descriptionProductName;

    @FindBy(css = "td[class='cart_description'] small a")
    private WebElement sizeColor;

    @FindBy(xpath = "//span[@class='label label-success']")
    private WebElement availableInStock;

    @FindBy(xpath = "//span[contains(@class,'price special-price')]")
    private WebElement unitPrice;

    @FindBy(xpath = "//span[@class='price-percent-reduction small']")
    private WebElement discount;

    @FindBy(xpath = "//span[@class='old-price']")
    private WebElement oldPrice;

    @FindBy(xpath = "//td[@class='cart_quantity text-center']")
    private WebElement quantityInput;

    @FindBy(xpath = "//td[@class='cart_total']")
    private WebElement totalInput;

    @FindBy(xpath = "//i[@class='icon-trash']")
    private WebElement deleteIcon;

    @FindBy(xpath = "//td[@id='total_product']")
    protected WebElement totalProductPriceBeforeShipping;

    @FindBy(xpath = "//td[@id='total_shipping']")
    protected WebElement totalShippingPrice;

    @FindBy(xpath = "//td[@id='total_price_without_tax']")
    protected WebElement totalWithShipping;

    @FindBy(xpath = "//span[@id='total_price']")
    protected WebElement totalProductPriceAfterShipping;

    @FindBy(xpath = "(//span[contains(text(),'Proceed to checkout')])[2]")
    protected WebElement checkOutBtn;

    private String[] toDoOptions = {"summary", "sign in", "address", "shipping", "payment"};

    private String[] summaryOptionsExpected = {"product", "description", "avail.", "unit price", "qty", "total", null};

    //address tab
    @FindBy(xpath = "//li[@class='step_current third']//span[1]")
    protected WebElement addressTab;

    @FindBy(id = "id_address_delivery")
    protected WebElement addressField;
    @FindBy(id = "addressesAreEquals")
    protected WebElement addressCheckBox;

    @FindBy(name = "message")
    protected WebElement commentBox;

    @FindBy(xpath = "//div[@class='delivery_option_price']")
    private WebElement deliveryOptPrice;

    @FindBy(xpath = "//td[@class='delivery_option_radio']//input")
    private WebElement shippingRadioBtn;

    @FindBy(xpath = "//div[@class='fancybox-outer']")
    private WebElement warningTermsPopUp;

    @FindBy(xpath = "//a[@title='Close']")
    private WebElement warningTermsPopUpClose;

    @FindBy(linkText = "(Read the Terms of Service)")
    protected WebElement readTermLink;

    @FindBy(xpath = "//div[contains(@class,'content_only')]/p")
    protected List<WebElement> threeRules;

    @FindBy(xpath = "//a[@title='Close']")
    private WebElement closeMustAgreeBtns;

    @FindBy(id = "cgv")
    protected WebElement checkServiceBoxInput;

    @FindBy(tagName = "iframe")
    private WebElement childFrame;

    @FindBy(xpath = "//li[@id='step_end']/span")
    private WebElement paymentTab;

    @FindBy(xpath = "(//td)//img")
    private WebElement dressPaymentPic;

    @FindBy(xpath = "//td[contains(@class,'cart_quantity')]/span")
    private WebElement quantityPaymentTab;

    @FindBy(xpath = "//td[@class='cart_total']/span")
    private WebElement total;

    @FindBy(xpath = "//a[@class='bankwire']")
    private WebElement payByBankWire;

    @FindBy(xpath = "//a[@class='cheque']")
    private WebElement payByCheck;

    @FindBy(xpath = "//h3[@class='page-subheading']")
    private WebElement paymentOptionsHeader;

    @FindBy(xpath = "//div[contains(@class,'cheque-box')]/p[not(self::p/span)]")
    private List<WebElement> instructionCheckSteps;

    @FindBy(xpath = "//div[contains(@class,'cheque-box')]/p[not(self::p/span)]")
    private List<WebElement> instructionBankWireSteps;

    @FindBy(xpath = "//a[contains(@class,'button-exclusive btn btn-default')]")
    private WebElement otherPaymentMethodsLink;

    @FindBy(xpath = "//button[contains(.,'I confirm my order')]")
    private WebElement iConfirmMyOrderBtn;

    @FindBy(xpath = "//div[@class='box']")
    private WebElement orderCompleteInformation;

    @FindBy(linkText = "Back to orders")
    private WebElement backToOrdersLnk;

    private void proceedToCheckOut() {

        assertTrue(checkOutBtn.isEnabled(), "Checkout buttons is not Enabled!");
        checkOutBtn.click();

    }

    public void verifyShoppingCartSummary(Map<String, String> data) throws InterruptedException {
        verifyingProductSummaryTabs(data);
        proceedToCheckOut();
        verifyingAddressTab(data);
        proceedToCheckOut();
        verifyShippingTab(data);
        proceedToCheckOut();
        verifyPaymentTab(data);

    }

    private void verifyingProductSummaryTabs(Map<String, String> data) {
        assertEquals(cartHeader.getText().trim().toUpperCase(), "SHOPPING-CART SUMMARY" + "\n" + "YOUR SHOPPING CART CONTAINS: " + data.get("Quantity") + " PRODUCTS", "Header is not correct");
        for (int i = 0; i < shoppingToDo.size(); i++) {
            assertEquals(shoppingToDo.get(i).getText().toLowerCase().substring(4), toDoOptions[i], toDoOptions[i] + "isnt match");
        }

        verifyActiveTab(summaryTab);
        /*String rgbFormat = summaryTab.getCssValue("color");
        String hexcolor = Color.fromString(rgbFormat).asHex(); //converted Into HexFormat
        assertEquals(hexcolor, "#ffffff", "Summary button is selected");*/
        for (int i = 0; i < summaryOptions.size() - 1; i++) {
            assertEquals(summaryOptions.get(i).getText().toLowerCase(), summaryOptionsExpected[i], summaryOptionsExpected[i] + "isnt match");
        }
        assertEquals(descriptionProductName.getText().trim(), data.get("Name"), "Product name isn't correct");
        String[] colorAndSize = sizeColor.getText().split(",");
        String[] uiColor = colorAndSize[0].split(":");
        String[] uiSize = colorAndSize[1].split(":");
        assertEquals(uiColor[1].trim(), data.get("PickColor"), "Color isn't correct");
        assertEquals(uiSize[1].trim(), data.get("Size"), "Size isn't correct");
        assertTrue(availableInStock.isDisplayed(), "In Stoke isn't displayed");
        assertEquals(unitPrice.getText().replace("$", ""), data.get("PriceAfterDiscount"), "Price isn't correct");
        assertEquals(discount.getText().replaceAll("[-%]", "").trim(), data.get("Discount"), "Discount isn't correct");
        assertEquals(oldPrice.getText().replace("$", ""), data.get("PriceBeforeDiscount"), "Price before discount isn't correct");
        assertTrue(quantityInput.isDisplayed(), "Quantity isn't displayed");
        double total = Double.parseDouble(data.get("Quantity")) * Double.parseDouble(data.get("PriceAfterDiscount"));
        assertEquals(totalInput.getText().replace("$", ""), Double.toString(total), "Total Input isn't correct");
        assertTrue(deleteIcon.isEnabled(), "Delete Icon isn't enabled");
        assertEquals(totalProductPriceBeforeShipping.getText().replace("$", ""), Double.toString(total), "Total Input isn't correct");
        assertEquals(totalShippingPrice.getText().replace("$", "").substring(0, 3), data.get("ShippingCost"), "Shipping Cost isn't correct");
        double totalPlusShipping = Double.parseDouble(data.get("ShippingCost")) + total;
        assertEquals(totalWithShipping.getText().replace("$", ""), Double.toString(totalPlusShipping), "Total plus Shipping isn't correct");
        assertEquals(totalProductPriceAfterShipping.getText().replace("$", ""), Double.toString(totalPlusShipping), "Finished Total isn't correct");
        assertTrue(checkOutBtn.isEnabled(), "Check Out Button isn't enabled");
    }

    private void verifyingAddressTab(Map<String, String> data) {

        verifyActiveTab(addressTab);

        assertTrue(addressField.isEnabled(), "Address Field is enabled");
        if (!addressCheckBox.isSelected()) {
            addressCheckBox.click();
        }
        assertTrue(commentBox.isEnabled(), "Address Field is enabled");
        commentBox.sendKeys("There should be a random comment, but may be later");
    }

    private void verifyShippingTab(Map<String, String> data) throws InterruptedException {

        verifyActiveTab(shippingTab);

        assertEquals(Double.parseDouble(deliveryOptPrice.getText().trim().replace("$", "")), Double.parseDouble(data.get("ShippingCost")), "Shipping price doesn't match");
        assertTrue(shippingRadioBtn.isSelected(), "Radio button is not selected!");
        proceedToCheckOut();
        assertTrue(warningTermsPopUp.isDisplayed(), "Warning for terms is not Displayed!");
        warningTermsPopUpClose.click();

        assertTrue(readTermLink.isDisplayed(), "read term link is not displayed");
        readTermLink.click();
        Thread.sleep(2000);

        //Switching to child html (iFrame)
        switchToFrame(childFrame);

        assertEquals(threeRules.get(0).getText().trim(), data.get("Rule1").trim(), "Rule 1 doesnt match with data we have");
        assertEquals(threeRules.get(1).getText().trim(), data.get("Rule2").trim(), "Rule 2 doesnt match with data we have");
        assertEquals(threeRules.get(2).getText().trim(), data.get("Rule3").trim(), "Rule 3 doesnt match with data we have");

        //Switching back to main html
        switchToFrame(0);

        assertTrue(closeMustAgreeBtns.isDisplayed(), "Close button for tearms is not displayed");
        closeMustAgreeBtns.click();

        if (!checkServiceBoxInput.isSelected()) {
            checkServiceBoxInput.click();
        }

    }

    private void switchToFrame(WebElement frame) {
        driver.switchTo().frame(frame);
    }

    private void switchToFrame(int index) {
        if (index == 0) {
            driver.switchTo().defaultContent(); //switiching to parent window
        } else {
            driver.switchTo().frame(index);
        }
    }

    private void verifyActiveTab(WebElement tab) {

        String rgbFormat = tab.getCssValue("color"); // rgb(255,255,255)
        String hexcolor = Color.fromString(rgbFormat).asHex(); //converted Into HexFormat #fffff
        assertEquals(hexcolor, "#ffffff", "Tab button is selected");
    }

    private void verifyPaymentTab(Map<String, String> data) {
        verifyActiveTab(paymentTab);
        assertTrue(dressPaymentPic.isDisplayed(), "Dress pic is NOT displayed");
        assertEquals(descriptionProductName.getText().trim(), data.get("Name").trim(), "Name of the product is NOT displayed");
        String[] colorAndSize = sizeColor.getText().split(",");
        String[] uiColor = colorAndSize[0].split(":");
        String[] uiSize = colorAndSize[1].split(":");
        assertEquals(uiColor[1].trim(), data.get("PickColor"), "Color isn't correct");
        assertEquals(uiSize[1].trim(), data.get("Size"), "Size isn't correct");
        assertTrue(availableInStock.isDisplayed(), "In Stoke isn't displayed");
        assertEquals(unitPrice.getText().replace("$", ""), data.get("PriceAfterDiscount"), "Price isn't correct");
        assertEquals(discount.getText().replaceAll("[-%]", "").trim(), data.get("Discount"), "Discount isn't correct");
        assertEquals(oldPrice.getText().replace("$", ""), data.get("PriceBeforeDiscount"), "Price before discount isn't correct");
        assertEquals(quantityPaymentTab.getText().trim(), data.get("Quantity").trim(), "Quantity on payment tab is not matching");
        assertEquals(total.getText().trim().replace("$", ""), data.get("TotalCostBeforeShip"), "Total cost before ship is not matching");
        assertEquals(totalProductPriceBeforeShipping.getText().trim().replace("$", ""), data.get("TotalCostBeforeShip"), "Total products cost before ship is not matching");
        assertEquals(totalShippingPrice.getText().trim().replace("$", "").substring(0, 3), data.get("ShippingCost"), "Total shipping cost is not matching");
        assertEquals(totalProductPriceAfterShipping.getText().trim().replace("$", ""), data.get("TotalCost"), "Total product price after shipping  is not matching");
        assertTrue(payByBankWire.isEnabled(), "Pay by bank wire is not enabled");
        assertTrue(payByCheck.isEnabled(), "Pay by check is not enabled");
        payForProduct(data);
        verifyOrderConfirmation(data);

    }

    public void payForProduct(Map<String, String> data) {
        if (data.get("PaymentMethod").equals("Wire")) {
            payByBankWire.click();

            //save the price inter span if needed
//            WebElement amount = driver.findElement(By.id("amount"));
//            String jsCode = "let p = document.evaluate(\"(//p[@class='cheque-indent']/following-sibling::p)[1]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
//                    "let child = document.evaluate(\"//span[@id='amount']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
//                    "p.removeChild(child);";
//            String jsCode = "document.getElementById('amount').textContent = ''; ";
//            ((JavascriptExecutor) driver).executeScript(jsCode);
//
//
//            WebElement allDiv = driver.findElement(By.xpath("//div[contains(@class,'cheque-box')]"));
//
//            System.out.println(allDiv.getText());


            assertEquals(paymentOptionsHeader.getText().trim().toLowerCase(), data.get("BankWireHeader"), "Payment header is not matching!");

            String[] bankWireStepsArray = data.get("BankWireSteps").trim().split("\\+");

            for (int i = 0; i < instructionBankWireSteps.size(); i++) {
                assertEquals(instructionBankWireSteps.get(i).getText().trim(), bankWireStepsArray[i], "Steps is not matching");
            }
            assertTrue(otherPaymentMethodsLink.isEnabled(), "Other payment methods button is not enabled!");
            assertTrue(iConfirmMyOrderBtn.isEnabled(), "I confirm my order button is not enabled!");
            iConfirmMyOrderBtn.click();

        } else if (data.get("PaymentMethod").equals("Check")) {
            payByCheck.click();
            assertEquals(paymentOptionsHeader.getText().trim().toLowerCase(), data.get("CheckHeader"), "Payment header is not matching!");

            String[] checkStepsArray = data.get("CheckSteps").trim().split("\\+");

            for (int i = 0; i < instructionCheckSteps.size(); i++) {
                assertEquals(instructionCheckSteps.get(i).getText().trim(), checkStepsArray[i], "Steps is not matching");
            }
            assertTrue(otherPaymentMethodsLink.isEnabled(), "Other payment methods button is not enabled!");
            assertTrue(iConfirmMyOrderBtn.isEnabled(), "I confirm my order button is not enabled!");
            iConfirmMyOrderBtn.click();

        }
    }

//    public void verifyOrderConfirmation(Map<String, String> data) {
//
//        List<String> confirmationLines = orderCompleteInformation.getText().trim().lines().collect(Collectors.toList());
//        StringBuilder temp = new StringBuilder();
//        for (int i = 0; i < confirmationLines.get(6).length(); i++) {
//            if (Character.isUpperCase(confirmationLines.get(6).charAt(i))) {
//                temp.append(confirmationLines.get(6).charAt(i));
//            }
//        }
//        //Line is start with 'D'o not in this case just getting order reference substring.
//        String orderReference = temp.substring(1);
//        data.put("orderReference", orderReference);
//
//        String orderInfoTextUi = orderCompleteInformation.getText().trim();
//        //System.out.println(orderInfoTextUi);
//        orderInfoTextUi = orderInfoTextUi.replace(" " + orderReference, "");
//        assertEquals(orderInfoTextUi, data.get("OrderCompleteInfo"));
//
//        //goes back to orders page
//        goBackToOrders();
//    }

    public void verifyOrderConfirmation(Map<String, String> data) {
        String orderConfirmationTxt = orderCompleteInformation.getText();

        Pattern pattern = Pattern.compile("([A-Z]){9}");
        Matcher matcher = pattern.matcher(orderConfirmationTxt);
        String orderReference = "";
        while (matcher.find()) {
            orderReference = matcher.group();
        }
        data.put("orderReference", orderReference);
        String orderInfoTextUi = orderCompleteInformation.getText().trim();
        orderInfoTextUi = orderInfoTextUi.replace(" " + orderReference, "");
        assertEquals(orderInfoTextUi, data.get("OrderCompleteInfo"));
        goBackToOrders();
    }

    private void goBackToOrders(){
        wait.until(ExpectedConditions.elementToBeClickable(backToOrdersLnk));
        assertEquals(backToOrdersLnk.getText().trim(),"Back to orders", "back to orders is not correct");
        backToOrdersLnk.click();
    }

}





