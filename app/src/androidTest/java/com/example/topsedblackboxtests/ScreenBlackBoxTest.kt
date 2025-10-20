package com.example.topsedblackboxtests

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
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
    }

    fun getDivice(): UiDevice{
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
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