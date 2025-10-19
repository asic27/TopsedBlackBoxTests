package com.example.topsedblackboxtests

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import io.qameta.allure.android.allureScreenshot
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import io.qameta.allure.kotlin.Allure
import org.junit.Assert.assertTrue
import org.junit.Before

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

@RunWith(AllureAndroidJUnit4::class)
class ScreenBlackBoxTest {

    @After
    fun deleteApp(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        device.executeShellCommand("pm uninstall $packageName")
    }


    @Test
    fun `Start_Screen_Test`() {
        Allure.step("Установка приложения")
        val device = getDivice()
        WelcomeScreen(device).welcomeSkip()
        Allure.step("Проверяем, что на экране элемент спидометра с основного экране приложения") {
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
            assertTrue(MainScreen(device).hasObjectAcrProgress())
        }
    }

    @Test
    fun `HomeScreen_Buttons_Test_Main`(){
        Allure.step("Установка приложения")
        val device = getDivice()
        WelcomeScreen(device).welcomeSkip()
        val mainScr = MainScreen(device)
        mainScr.clickStartButton()
        mainScr.clickAllowButton()
        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)
        Allure.step("В окне с запросом на разрешения использования геолокации от ОС нажимаем разрешить")
        device.findObject(By.res("com.android.permissioncontroller:id/permission_allow_foreground_only_button")).click()
        mainScr.waitArcProgress()

        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
            allureScreenshot(name = "PauseStopButtonsOnly", quality = 90, scale = 1.0f)
            assertTrue(mainScr.hasObjectStopButton())
            assertTrue(mainScr.hasObjectPauseButton())
            assertTrue(!mainScr.hasObjectStartButton())
        }
        mainScr.clickPauseButton()
        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
            allureScreenshot(name = "PauseStopButtonsOnlyonPause", quality = 90, scale = 1.0f)
            assertTrue(mainScr.hasObjectStopButton())
            assertTrue(mainScr.hasObjectPauseButton())
            assertTrue(!mainScr.hasObjectStartButton())
        }

        Allure.step("Нажимаем стоп и отменяем остановку поездки в всплывающем окне") {
            mainScr.clickStopButton()
            device.apply {
                wait(Until.hasObject(By.res("android:id/message")), 5000L)
                Allure.step("Отменяем остановку поездки во всплывающем окне")
                allureScreenshot(name = "PauseStopButtonsOnlyonPause", quality = 90, scale = 1.0f)
                findObject(By.res("android:id/button2")).click()
                wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
            }
        }
        //device.dumpWindowHierarchy(System.out)
        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            allureScreenshot(name = "PauseStopButtonsOnlyAfterTryStop", quality = 90, scale = 1.0f)
            assertTrue(mainScr.hasObjectStopButton())
            assertTrue(mainScr.hasObjectPauseButton())
            assertTrue(!mainScr.hasObjectStartButton())
        }

        Allure.step("Снова нажимаем стоп и останавливаем поездку") {
            mainScr.clickStopButton()
            device.apply {
                //findObject(By.res("$prefix/stopBtn")).click()
                Allure.step("Останавливаем поездку во всплывающем окне")
                wait(Until.hasObject(By.res("android:id/message")), 5000L)
                findObject(By.res("android:id/button1")).click()
            }
        }

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Проверяем, что открылся отчет о поездке") {
            assertTrue(DriveReportScreen(device).hasObjectTitle())
            allureScreenshot(name = "DriveReport", quality = 90, scale = 1.0f)
        }

    }

    @Test
    fun `HomeScreen_Buttons_Test_Negative`(){
        Allure.step("Установка приложения")
        val device = getDivice()

        WelcomeScreen(device).welcomeSkip()

        val mainScr = MainScreen(device)

        mainScr.clickStartButton()

        mainScr.clickCancelButton()

        Allure.step("Проверяем, что на экране осталась кнопка старт, но отсуствуют кнопки паузы и стоп") {
            mainScr.waitArcProgress()
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
            assertTrue(!mainScr.hasObjectStopButton())
            assertTrue(!mainScr.hasObjectPauseButton())
            assertTrue(mainScr.hasObjectStartButton())
        }

        mainScr.clickStartButton()

        mainScr.clickAllowButton()

        Allure.step("В окне с запросом на разрешения использования геолокации от ОС нажимаем отмена") {
            device.apply {
                wait(Until.hasObject(By.res("android:id/message")), 5000L)
                findObject(By.res("com.android.permissioncontroller:id/permission_deny_button")).click()
            }
            waitObject(device, "arcProgress")
        }

        Allure.step("Проверяем, что на экране снова осталась кнопка старт, но отсуствуют кнопки паузы и стоп") {
            allureScreenshot(name = "StartButtonOnlyAgain", quality = 90, scale = 1.0f)
            assertTrue(!mainScr.hasObjectStopButton())
            assertTrue(!mainScr.hasObjectPauseButton())
            assertTrue(mainScr.hasObjectStartButton())
        }

    }

    @Test
    fun `MyDrives_Screen_Test_Base`(){
        Allure.step("Установка приложения и тестовой БД с 2 поездками")
        val device = getDivice()

        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1)

        waitApp(device)

        WelcomeScreen(device).welcomeSkip()

        val myDrvScr = MyDrivesScreen(device)

        myDrvScr.clickNavMyDrives()

        Allure.step("Проверяем отображаемые значения максимальной скорости и количества поездок") {
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
            assertEquals("2",myDrvScr.getTotalDrivesCount())
            assertEquals("23 mph",myDrvScr.getTopSpeedValue())//в БД скорость приведена в м/с
        }

    }

    @Test
    fun `MyDrivesScreen_ViewAll_Delete_Rename_Drive_Test`(){
        Allure.step("Установка приложения и тестовой БД с 4 поездками")
        val device = getDivice()
        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1allviews)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        WelcomeScreen(device).welcomeSkip()

        val myDrvScr = MyDrivesScreen(device)
        myDrvScr.clickNavMyDrives()

        Allure.step("Проверяем отображаемые значения максимальной скорости и количества поездок"){
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
            assertEquals("4",myDrvScr.getTotalDrivesCount())
            assertEquals("63 mph",myDrvScr.getTopSpeedValue())//в БД скорость приведена в м/с
        }

        Allure.step("Скроллим список поездок до конца и нажимаем ViewAllButton") {
            myDrvScr.recViewScrolltoEnd()
            allureScreenshot(name = "ViewAllButton", quality = 90, scale = 1.0f)
            myDrvScr.clickViewButton()
        }


        Allure.step("Проверяем, что в полном списке поездок отображено 4 элемента ") {
            assertEquals(myDrvScr.getRecViewChildCount(), 4)
            allureScreenshot(name = "AllDrivesList", quality = 90, scale = 1.0f)
        }

        Allure.step("Удаляем поездку из списка (поездка с самой высокой максимальной скоростью)") {
            myDrvScr.clickDeleteIcon()
            device.apply {
                wait(Until.hasObject(By.res("android:id/message")), 5000L)
                findObject(By.res("android:id/button1")).click()
            }
        }

        Allure.step("Проверяем, что в списке теперь отображено 3 поездки") {
            assertEquals(myDrvScr.getRecViewAllChildCount(), 3)
            allureScreenshot(name = "AllDrivesListAfterDelete", quality = 90, scale = 1.0f)
        }

        Allure.step("Меняем название первой поездки в списке и проверяем что изменения отобразились") {
            myDrvScr.getTagNamefromCardViewAll(0).click()
            waitApp(device)
            device.apply {
                findObject(UiSelector().className("android.widget.EditText")).setText("Test Ride")
                findObject(By.res("android:id/button1")).click()
            }
            myDrvScr.waitRecViewAll()
            //waitObject(device, "recyclerView")
            allureScreenshot(name = "RenamedDrive", quality = 90, scale = 1.0f)
            assertEquals("Test Ride",myDrvScr.getTagNamefromCardViewAll(0).text)

        }

        Allure.step("Возвращаемся на основной экран со списком поездок") {
            device.findObject(By.desc("Navigate up")).click()
            myDrvScr.waitRecView()
        }

        myDrvScr.recViewScrolltoBegining()
        Allure.step("Проверяем, что первая поездка в списке имеет изменённое название") {
            allureScreenshot(name = "RenamedDrive", quality = 90, scale = 1.0f)
            assertEquals("Test Ride",myDrvScr.getTagNamefromCardView(0).text)
        }


        Allure.step("Проверяем, что отображаемые значения максимальной скорости и количества поездок изменились"){
            assertEquals("3",myDrvScr.getTotalDrivesCount())
            assertEquals("62 mph",myDrvScr.getTopSpeedValue())//в БД скорость приведена в м/с
            allureScreenshot(name = "NewTops", quality = 90, scale = 1.0f)
        }
    }

    @Test
    fun `Compare_Screen_Test`(){
        Allure.step("Установка приложения и тестовой БД с 2 поездками для сравнения")
        val device = getDivice()
        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1compare)

        WelcomeScreen(device).welcomeSkip()

        val myDrvScr = MyDrivesScreen(device)

        myDrvScr.clickNavMyDrives()

        Allure.step("Проверяем отображаемое значение количества поездок") {
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
            assertEquals("2",myDrvScr.getTotalDrivesCount())
            assertEquals(myDrvScr.getRecViewChildCount(), 2)
        }

        Allure.step("Нажимаем на первую поездку в списке") {
            myDrvScr.clickonCardViewByIndex(0)
        }

        Allure.step("Переходим на вкладку рейтинга поездки") {
            myDrvScr.clickNavRank()
        }

        Allure.step("Проверяем, что в списке сравнения поездок две записи") {
            assertEquals(myDrvScr.getRecViewRankChildCount(), 2)
            allureScreenshot(name = "RankScreen", quality = 90, scale = 1.0f)
        }
        val cmpScr = CompareScreen(device)
        Allure.step("Нажимаем на поездку отображаемым значком сравнения") {
            myDrvScr.clickCmpIcon()
            cmpScr.waitBottomSheet()
            allureScreenshot(name = "CompareScreenBase", quality = 90, scale = 1.0f)
        }

        Allure.step("Вытягиваем информацию о поездках с помощью свайпа") {
            val displayHeight = device.displayHeight
            val displayWidth = device.displayWidth

            device.swipe(
                displayWidth / 2,
                displayHeight - 200,
                displayWidth / 2,
                (displayHeight * 0.2).toInt(),
                20
            )
        }

        Allure.step("Проверяем правильность отображаемой информации о поездках") {
            cmpScr.waitMyTotalDistance()
            allureScreenshot(name = "CompareScreenWithInfo", quality = 90, scale = 1.0f)
            assertEquals("316 mph", cmpScr.getMyTopSpeed())
            assertEquals("316 mph", cmpScr.getOtherTopSpeed())
            assertEquals("663 mph", cmpScr.getMyAvgSpeed())
            assertEquals("677 mph", cmpScr.getOtherAvgSpeed())
            assertEquals("2 mi", cmpScr.getMyTotalDist())
            assertEquals("2.1 mi", cmpScr.getOtherTotalDist())
        }
    }
    @Before
    fun installApp(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)

        device.wakeUp()

        println("Install package")
        val testContext = instrumentation.context
        val apk =
            testContext.resources.openRawResource(com.example.topsedblackboxtests.test.R.raw.topsed)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val temp = File(context.filesDir, "/topsed.apk")
        temp.writeBytes(apk.readBytes())
        device.executeShellCommand("pm install -t -r " + temp.absolutePath)

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        ApplicationProvider.getApplicationContext<Context>().startActivity(intent)
        //return device
    }

    fun getDivice(): UiDevice{
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        //val context = ApplicationProvider.getApplicationContext<Context>()
        //val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        //ApplicationProvider.getApplicationContext<Context>().startActivity(intent)
        return device
    }

    // Подменяю базу данных с поездками в эмуляторе
    fun downloadTestDatabase(id:Int){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        val testContext = instrumentation.context
        device.wakeUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = testContext.resources.openRawResource(id)
        val temp = File(context.filesDir, "/topsedv1.db")
        temp.writeBytes(db.readBytes())
        device.executeShellCommand("mkdir -p /data/data/com.topsed.race/databases")
        device.executeShellCommand("cp -f ${temp.absolutePath} /data/data/com.topsed.race/databases/")
        device.executeShellCommand("chmod 777 /data/data/com.topsed.race/databases/topsedv1.db")
    }
    fun waitObject(device:UiDevice, objectid: String){
        device.wait(Until.hasObject(By.res("$prefix/$objectid")), 5000L)
    }


}