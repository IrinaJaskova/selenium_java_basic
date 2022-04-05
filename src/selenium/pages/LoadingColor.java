package selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.junit.Assert.*;


public class LoadingColor extends GenericSamplePage {
    @FindBy(how = How.ID, using = "start_green")
    private WebElement startGreen;
    @FindBy(how = How.ID, using = "Loading_green")
    private WebElement loadingGreen;
    @FindBy(how = How.ID, using = "finish_green")
    private WebElement finishGreen;

    public void clickStartGreen() {
        startGreen.click();
    }
    public boolean displayStartGreen() {
        return   startGreen.isDisplayed();
    }
    public boolean displayLoadGreen() {
        return   loadingGreen.isDisplayed();
    }
    public boolean displayFinishGreen() {
        return   finishGreen.isDisplayed();
    }
    public String getTextLoadGreen() {
        return   loadingGreen.getText();
    }
    public String getTextFinishGreen() {
        return   finishGreen.getText();
    }
}