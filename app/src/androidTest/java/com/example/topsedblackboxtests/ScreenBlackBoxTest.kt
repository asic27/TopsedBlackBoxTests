package com.example.topsedblackboxtests

import android.content.Context
import android.graphics.Point
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
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


@RunWith(AllureAndroidJUnit4::class)
class ScreenBlackBoxTest {
    var packageName = "com.topsed.race"
    val prefix = "$packageName:id"
    @After
    fun deleteApp(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        device.executeShellCommand("pm uninstall $packageName")
    }

    @Test
    fun useAppContex(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.topsedblackboxtests", appContext.packageName)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)

        device.wakeUp()
        device.pressHome()
    }
    @Test
    fun `Start_Screen_Test`() {
        Allure.step("Установка приложения")
        val device = installApp()
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
        //device.dumpWindowHierarchy(System.out)
        Allure.step("Нажимаем Continue на приветственном экране") {
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
        }
        device.findObject(By.res("$prefix/skipLoginButton")).click()
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
        //device.dumpWindowHierarchy(System.out)
        Allure.step("Проверяем, что на экране элемент спидометра с основного экране приложения") {
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
        }
        device.findObject(By.res("$prefix/arcProgress"))
    }

    @Test
    fun `HomeScreen_Buttons_Test_Main`(){
        Allure.step("Установка приложения")
        val device = installApp()
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
        Allure.step("Нажимаем Continue на приветственном экране")
        device.findObject(By.res("$prefix/skipLoginButton")).click()

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Запускаем поездку нажатием кнопки старт")
        device.findObject(By.res("$prefix/startBtn")).click()

        waitObject(device,"allowBtn")

        Allure.step("В окне с запросом на разрешения использования геолокации от приложения нажимаем разрешить")
        device.findObject(By.res("$prefix/allowBtn")).click()

        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)

        Allure.step("В окне с запросом на разрешения использования геолокации от ОС нажимаем разрешить")
        device.findObject(By.res("com.android.permissioncontroller:id/permission_allow_foreground_only_button")).click()

        waitObject(device,"arcProgress")

        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            allureScreenshot(name = "PauseStopButtonsOnly", quality = 90, scale = 1.0f)
        }
        assertTrue(device.hasObject(By.res("$prefix/stopBtn")))
        assertTrue(device.hasObject(By.res("$prefix/pauseBtn")))
        assertTrue(!device.hasObject(By.res("$prefix/startBtn")))

        Allure.step("Нажимаем паузу")
        device.findObject(By.res("$prefix/pauseBtn")).click()
        //device.dumpWindowHierarchy(System.out)
        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            allureScreenshot(name = "PauseStopButtonsOnlyonPause", quality = 90, scale = 1.0f)
        }
        assertTrue(device.hasObject(By.res("$prefix/stopBtn")))
        assertTrue(device.hasObject(By.res("$prefix/pauseBtn")))
        assertTrue(!device.hasObject(By.res("$prefix/startBtn")))

        Allure.step("Нажимаем стоп")
        device.findObject(By.res("$prefix/stopBtn")).click()

        Allure.step("Отменяем остановку поездки в всплывающем окне") {
            allureScreenshot(name = "PauseStopButtonsOnlyonPause", quality = 90, scale = 1.0f)
        }
        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)
        device.findObject(By.res("android:id/button2")).click()
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
        //device.dumpWindowHierarchy(System.out)
        Allure.step("Проверяем, что на экране отображаются кнопки пауза и стоп, но не отображается кнопка старт") {
            allureScreenshot(name = "PauseStopButtonsOnlyAfterTryStop", quality = 90, scale = 1.0f)
        }
        assertTrue(device.hasObject(By.res("$prefix/stopBtn")))
        assertTrue(device.hasObject(By.res("$prefix/pauseBtn")))
        assertTrue(!device.hasObject(By.res("$prefix/startBtn")))

        Allure.step("Снова нажимаем стоп и останавливаем поездку")
        device.findObject(By.res("$prefix/stopBtn")).click()
        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)
        device.findObject(By.res("android:id/button1")).click()

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Проверяем, что открылся отчет о поездке") {
            assertTrue(device.hasObject(By.text("Ride Details")))
            allureScreenshot(name = "DriveReport", quality = 90, scale = 1.0f)
        }

    }

    @Test
    fun `HomeScreen_Buttons_Test_Negative`(){
        Allure.step("Установка приложения")
        val device = installApp()

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Нажимаем Continue на приветственном экране")
        device.findObject(By.res("$prefix/skipLoginButton")).click()

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Запускаем поездку нажатием кнопки старт")
        device.findObject(By.res("$prefix/startBtn")).click()
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
        //device.dumpWindowHierarchy(System.out)
        Allure.step("В окне с запросом на разрешения использования геолокации от приложения нажимаем отмена")
        device.findObject(By.res("$prefix/cancelBtn")).click()

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Проверяем, что на экране осталась кнопка старт, но отсуствуют кнопки паузы и стоп") {
            allureScreenshot(name = "StartButtonOnly", quality = 90, scale = 1.0f)
        }
        assertTrue(!device.hasObject(By.res("$prefix/stopBtn")))
        assertTrue(!device.hasObject(By.res("$prefix/pauseBtn")))
        assertTrue(device.hasObject(By.res("$prefix/startBtn")))

        Allure.step("Снова нажимаем кнопку старт")
        device.findObject(By.res("$prefix/startBtn")).click()
        waitObject(device,"allowBtn")

        Allure.step("В окне с запросом на разрешения использования геолокации от приложения нажимаем разрешить")
        device.findObject(By.res("$prefix/allowBtn")).click()

        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)
        //device.dumpWindowHierarchy(System.out)

        Allure.step("В окне с запросом на разрешения использования геолокации от ОС нажимаем отмена")
        device.findObject(By.res("com.android.permissioncontroller:id/permission_deny_button")).click()

        waitObject(device,"arcProgress")

        Allure.step("Проверяем, что на экране снова осталась кнопка старт, но отсуствуют кнопки паузы и стоп") {
            allureScreenshot(name = "StartButtonOnlyAgain", quality = 90, scale = 1.0f)
        }
        assertTrue(!device.hasObject(By.res("$prefix/stopBtn")))
        assertTrue(!device.hasObject(By.res("$prefix/pauseBtn")))
        assertTrue(device.hasObject(By.res("$prefix/startBtn")))
    }

    @Test
    fun `MyDrives_Screen_Test_Base`(){
        Allure.step("Установка приложения и тестовой БД с 2 поездками")
        val device = installApp()
        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Нажимаем Continue на приветственном экране")
        device.findObject(By.res("$prefix/skipLoginButton")).click()

        waitObject(device,"nav_my_drives")
        //device.wait(Until.hasObject(By.res("$prefix/nav_my_drives").depth(0)), 5000L)
        Allure.step("Переходим на основной экран со списком поездок")
        device.findObject(By.res("$prefix/nav_my_drives")).click()

        Allure.step("Проверяем отображаемые значения максимальной скорости и количества поездок") {
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
        }
        assertEquals("2",device.findObject(By.res("$prefix/totalDriveText")).text)
        assertEquals("23 mph",device.findObject(By.res("$prefix/topSpeedText")).text)//в БД скорость приведена в м/с
    }

    @Test
    fun `MyDrivesScreen_ViewAll_Delete_Rename_Drive_Test`(){
        Allure.step("Установка приложения и тестовой БД с 4 поездками")
        val device = installApp()
        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1allviews)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Нажимаем Continue на приветственном экране")
        device.findObject(By.res("$prefix/skipLoginButton")).click()


        waitObject(device,"nav_my_drives")

        Allure.step("Переходим на основной экран со списком поездок")
        device.findObject(By.res("$prefix/nav_my_drives")).click()
        waitObject(device, "totalDriveText")

        Allure.step("Проверяем отображаемые значения максимальной скорости и количества поездок"){
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
        }
        assertEquals("4",device.findObject(By.res("$prefix/totalDriveText")).text)
        assertEquals("63 mph",device.findObject(By.res("$prefix/topSpeedText")).text)//в БД скорость приведена в м/с

        waitObject(device,"recyclerView")

        val recview = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))

        Allure.step("Скроллим список поездок до конца и нажимаем ViewAllButton") {
            recview.scrollToEnd(3)
            allureScreenshot(name = "ViewAllButton", quality = 90, scale = 1.0f)
            device.findObject(By.res("$prefix/viewAllBtn")).click()
        }

        waitObject(device,"recyclerView")

        Allure.step("Проверяем, что в полном списке поездок отображено 4 элемента ") {
            assertEquals(recview.getChildCount(), 4)
            allureScreenshot(name = "AllDrivesList", quality = 90, scale = 1.0f)
        }

        Allure.step("Удаляем поездку из списка (поездка с самой высокой максимальной скоростью)")
        device.findObject(By.res("$prefix/deleteIcon")).click()
        device.wait(Until.hasObject(By.res("android:id/message")), 5000L)
        device.findObject(By.res("android:id/button1")).click()

        waitObject(device,"recyclerView")

        Allure.step("Проверяем, что в списке теперь отображено 3 поездки") {
            assertEquals(recview.getChildCount(), 3)
            allureScreenshot(name = "AllDrivesListAfterDelete", quality = 90, scale = 1.0f)
        }

        Allure.step("Меняем название первой поездки в списке и проверяем что изменения отобразились") {
            recview.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(0))
                .getChild(UiSelector().resourceId("$prefix/tagText")).click()
            device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
            //device.dumpWindowHierarchy(System.out)
            device.findObject(UiSelector().className("android.widget.EditText"))
                .setText("Test Ride")
            device.findObject(By.res("android:id/button1")).click()

            waitObject(device, "recyclerView")

            allureScreenshot(name = "RenamedDrive", quality = 90, scale = 1.0f)
        }
        assertEquals("Test Ride",recview.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(0)).getChild(UiSelector().resourceId("$prefix/tagText")).text)

        Allure.step("Возвращаемся на основной экран со списком поездок") {
            device.findObject(By.desc("Navigate up")).click()
            waitObject(device, "recyclerView")
        }

        Allure.step("Скроллим список поездок в начало")
        recview.scrollToBeginning(3)

        Allure.step("Проверяем, что первая поездка в списке имеет изменённое название") {
            allureScreenshot(name = "RenamedDrive", quality = 90, scale = 1.0f)
        }
        assertEquals("Test Ride",recview.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(0)).getChild(UiSelector().resourceId("$prefix/tagText")).text)

        Allure.step("Проверяем, что отображаемые значения максимальной скорости и количества поездок изменились"){
            allureScreenshot(name = "NewTops", quality = 90, scale = 1.0f)
        }
        assertEquals("3",device.findObject(By.res("$prefix/totalDriveText")).text)
        assertEquals("62 mph",device.findObject(By.res("$prefix/topSpeedText")).text)//в БД скорость приведена в м/с
    }

    @Test
    fun `Compare_Screen_Test`(){
        Allure.step("Установка приложения и тестовой БД с 2 поездками для сравнения")
        val device = installApp()
        downloadTestDatabase(com.example.topsedblackboxtests.test.R.raw.topsedv1compare)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)

        Allure.step("Нажимаем Continue на приветственном экране")
        device.findObject(By.res("$prefix/skipLoginButton")).click()

        waitObject(device,"nav_my_drives")

        Allure.step("Переходим на основной экран со списком поездок")
        device.findObject(By.res("$prefix/nav_my_drives")).click()


        waitObject(device,"totalDriveText")
        var recview = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))

        Allure.step("Проверяем отображаемое значение количества поездок") {
            allureScreenshot(name = "DriveList", quality = 90, scale = 1.0f)
            assertEquals("2", device.findObject(By.res("$prefix/totalDriveText")).text)
            assertEquals(recview.getChildCount(), 2)
        }

        Allure.step("Нажимаем на первую поездку в списке")
        recview.getChild(UiSelector().className("androidx.cardview.widget.CardView").index(0)).getChild(UiSelector().resourceId("$prefix/top_layout")).click()

        Allure.step("Переходим на вкладку рейтинга поездки")
        device.findObject(By.res("$prefix/nav_rank")).click()


        waitObject(device,"recyclerView")

        Allure.step("Проверяем, что в списке сравнения поездок две записи") {
            allureScreenshot(name = "RankScreen", quality = 90, scale = 1.0f)
            recview = UiScrollable(UiSelector().resourceId("$prefix/recyclerView"))
            assertEquals(recview.getChildCount(), 2)
        }

        Allure.step("Нажимаем на поездку отображаемым значком сравнения") {
            device.findObject(By.res("$prefix/compareIcon")).click()
            waitObject(device, "bottomSheet")
            allureScreenshot(name = "CompareScreenBase", quality = 90, scale = 1.0f)
        }

        Allure.step("Вытягиваем информацию о поездках с помощью свайпа")
        val displayHeight = device.displayHeight
        val displayWidth = device.displayWidth

        device.swipe(
            displayWidth / 2,
            displayHeight - 200,
            displayWidth / 2,
            (displayHeight * 0.2).toInt(),
            20
        )

        waitObject(device,"myTotalDistanceText")


        Allure.step("Проверяем правильность отображаемой информации о поездках") {
            allureScreenshot(name = "CompareScreenWithInfo", quality = 90, scale = 1.0f)
            val myMaxSpeed = device.findObject(By.res("$prefix/myTopSpeedText")).text
            val otherMaxSpeed = device.findObject(By.res("$prefix/otherTopSpeedText")).text
            assertEquals("316 mph", myMaxSpeed)
            assertEquals("316 mph", otherMaxSpeed)
            //assertTrue(myMaxSpeed.substringBefore(" mph").toInt()==otherMaxSpeed.substringBefore(" mph").toInt())

            val myAvgSpeed = device.findObject(By.res("$prefix/myAvgSpeedText")).text
            val otherAvgSpeed = device.findObject(By.res("$prefix/otherAvgSpeedText")).text
            assertEquals("663 mph", myAvgSpeed)
            assertEquals("677 mph", otherAvgSpeed)
            //assertTrue(myAvgSpeed.substringBefore(" mph").toInt()<otherAvgSpeed.substringBefore(" mph").toInt())

            val myTotalDist = device.findObject(By.res("$prefix/myTotalDistanceText")).text
            val otherTotalDist = device.findObject(By.res("$prefix/otherTotalDistanceText")).text
            assertEquals("2 mi", myTotalDist)
            assertEquals("2.1 mi", otherTotalDist)
            //assertTrue(myTotalDist.substringBefore(" mi").toDouble()<otherTotalDist.substringBefore(" mi").toDouble())
        }
    }
    fun installApp(): UiDevice{
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