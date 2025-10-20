package com.example.topsedblackboxtests
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import io.qameta.allure.android.allureScreenshot
import io.qameta.allure.kotlin.Allure

var packageName = "com.topsed.race"
val prefix = "$packageName:id"
fun waitApp(device:UiDevice){
    device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
}
class WelcomeScreen(private val device: UiDevice) {
    val skipLoginSelector = By.res("$prefix/skipLoginButton")
    fun welcomeSkip() {
        Allure.step("Нажимаем Continue на приветственном экране"){
            waitApp(device)
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
            device.apply {
                wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
                findObject(skipLoginSelector).click()
                wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
            }
        }
    }
}

class MainScreen(private val device: UiDevice){
    val startSelector = By.res("$prefix/startBtn")
    val stopSelector = By.res("$prefix/stopBtn")
    val pauseSelector = By.res("$prefix/pauseBtn")
    val allowSelector = By.res("$prefix/allowBtn")
    val cancelSelector = By.res("$prefix/cancelBtn")
    val arcProgressSelector = By.res("$prefix/arcProgress")

    fun clickStartButton() {
        Allure.step("Запускаем поездку нажатием кнопки старт") {
            waitStartButton()
            device.findObject(startSelector).click()

        }
    }

    fun clickStopButton(){
        Allure.step("Нажимаем стоп") {
            waitStopButton()
            device.findObject(stopSelector).click()
        }
    }

    fun clickPauseButton(){
        Allure.step("Нажимаем паузу") {
            waitPauseButton()
            device.findObject(pauseSelector).click()
        }
    }

    fun clickCancelButton(){
        Allure.step("В окне с запросом на разрешения использования геолокации от приложения нажимаем отмена") {
            waitCancelButton()
            device.findObject(cancelSelector).click()
        }
    }

    fun clickAllowButton() {
        Allure.step("В окне с запросом на разрешения использования геолокации от приложения нажимаем разрешить") {
            waitAllowButton()
            device.findObject(allowSelector).click()
        }
    }

    fun hasObjectStartButton():Boolean{
        waitStartButton()
        return device.hasObject(startSelector)
    }

    fun hasObjectStopButton():Boolean{
        waitStopButton()
        return device.hasObject(stopSelector)
    }

    fun hasObjectPauseButton():Boolean{
        waitPauseButton()
        return device.hasObject(pauseSelector)
    }
    fun hasObjectAcrProgress():Boolean{
        waitArcProgress()
        return device.hasObject(arcProgressSelector)
    }
    fun waitArcProgress(){
        device.wait(Until.hasObject(arcProgressSelector), 5000L)
    }
    fun waitAllowButton(){
        device.wait(Until.hasObject(allowSelector), 5000L)
    }
    fun waitStartButton(){
        device.wait(Until.hasObject(startSelector), 5000L)
    }
    fun waitStopButton(){
        device.wait(Until.hasObject(stopSelector), 5000L)
    }
    fun waitPauseButton(){
        device.wait(Until.hasObject(pauseSelector), 5000L)
    }
    fun waitCancelButton(){
        device.wait(Until.hasObject(cancelSelector), 5000L)
    }

}

class DriveReportScreen(private val device: UiDevice){
    val titleText = By.text("Ride Details")
    fun hasObjectTitle():Boolean = device.hasObject(titleText)
}

class MyDrivesScreen(private val device: UiDevice){
    val navMyDrivesSelector = By.res("$prefix/nav_my_drives")
    val navRankSelector = By.res("$prefix/nav_rank")
    val totalDriveSelector = By.res("$prefix/totalDriveText")
    val topSpeedSelector = By.res("$prefix/topSpeedText")
    val recViewSelector =  By.res("$prefix/recyclerView")
    //UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))
    val recViewScrollable = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))

    val recViewAllSelector = By.res("$prefix/recyclerView")

    val recViewAllScrollable = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))
    val viewAllSelector = By.res("$prefix/viewAllBtn")
    val recViewRankScrollable = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))
    val recViewRankSelector =  By.res("$prefix/recyclerView")
    val deleteIcon = By.res("$prefix/deleteIcon")
    val tagName = UiSelector().resourceId("$prefix/tagText")
    val cardTopLayout = UiSelector().resourceId("$prefix/top_layout")
    val cmpIcon = By.res("$prefix/compareIcon")

    fun waitNavMyDrives(){
        device.wait(Until.hasObject(navMyDrivesSelector), 5000L)
    }
    fun waitNavRank(){
        device.wait(Until.hasObject(navRankSelector), 5000L)
    }
    fun clickNavRank(){
        Allure.step("Переходим на вкладку рейтинга поездки") {
            waitNavRank()
            device.findObject(navRankSelector).click()
            waitRecViewRank()
        }
    }

    fun clickNavMyDrives(){
        Allure.step("Переходим на основной экран со списком поездок") {
            waitNavMyDrives()
            device.findObject(navMyDrivesSelector).click()
            waitTotalDrives()
        }
    }

    fun recViewScrolltoEnd(){
        waitRecView()
        recViewScrollable.scrollToEnd(3)
    }
    fun recViewScrolltoBegining(){
        Allure.step("Скроллим список поездок в начало") {
            waitRecView()
            recViewScrollable.scrollToBeginning(3)
        }
    }
    fun waitTotalDrives(){
        device.wait(Until.hasObject(totalDriveSelector), 5000L)
    }
    fun waitRecView(){
        device.wait(Until.hasObject(recViewSelector), 1000L)
    }
    fun waitRecViewRank(){
        device.wait(Until.hasObject(recViewRankSelector), 1000L)
    }
    fun waitRecViewAll(){
        device.wait(Until.hasObject(recViewAllSelector), 1000L)
    }
    fun waitViewAllButton(){
        device.wait(Until.hasObject(viewAllSelector), 1000L)
    }
    fun clickViewButton() {
        waitViewAllButton()
        device.findObject(viewAllSelector).click()
    }

    fun clickDeleteIcon(){
        device.findObject(deleteIcon).click()
    }
    fun clickCmpIcon(){
        device.findObject(cmpIcon).click()
    }
    fun getRecViewChildCount():Int{
        waitRecView()
        return recViewScrollable.getChildCount()
    }
    fun getRecViewAllChildCount():Int{
        waitRecViewAll()
        return recViewAllScrollable.getChildCount()
    }
    fun getRecViewRankChildCount():Int{
        waitRecViewRank()
        return recViewRankScrollable.getChildCount()
    }
    fun getRecViewAllChildByIndex(index:Int) = recViewAllScrollable.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(index))

    fun getRecViewChildByIndex(index:Int) = recViewScrollable.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(index))
    fun getTagNamefromCardViewAll(index:Int)= getRecViewAllChildByIndex(index).getChild(tagName)

    fun getTagNamefromCardView(index:Int)= getRecViewChildByIndex(index).getChild(tagName)

    fun clickonCardViewByIndex (index: Int) = getRecViewChildByIndex(index).getChild(cardTopLayout).click()

    fun getTotalDrivesCount(): String = device.findObject(totalDriveSelector).text

    fun getTopSpeedValue(): String = device.findObject(topSpeedSelector).text
}
class CompareScreen(private val device: UiDevice){
    val bottomSheetSelector = By.res("$prefix/bottomSheet")
    val myTopSpeedTextSelector = By.res("$prefix/myTopSpeedText")
    val otherTopSpeedSelector = By.res("$prefix/otherTopSpeedText")
    val myAvgSpeedSelector = By.res("$prefix/myAvgSpeedText")
    val otherAvgSpeedSelector = By.res("$prefix/otherAvgSpeedText")
    val myTotalDistSelector =By.res("$prefix/myTotalDistanceText")
    val otherTotalDistSelector = By.res("$prefix/otherTotalDistanceText")

    fun waitBottomSheet(){
        device.wait(Until.hasObject(bottomSheetSelector), 1000L)
    }
    fun waitMyTotalDistance(){
        device.wait(Until.hasObject(myTotalDistSelector), 1000L)
    }

    fun getMyTopSpeed() = device.findObject(myTopSpeedTextSelector).text

    fun getOtherTopSpeed() = device.findObject(otherTopSpeedSelector).text

    fun getMyAvgSpeed() = device.findObject(myAvgSpeedSelector).text

    fun getOtherAvgSpeed() = device.findObject(otherAvgSpeedSelector).text

    fun getMyTotalDist() = device.findObject(myTotalDistSelector).text

    fun getOtherTotalDist() = device.findObject(otherTotalDistSelector).text
}