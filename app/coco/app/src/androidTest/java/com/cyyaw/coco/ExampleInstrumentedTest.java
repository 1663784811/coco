package com.cyyaw.coco;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.cyyaw.coco.activity.PrintPreviewActivity;
import com.cyyaw.coco.utils.print.PrintOrderDataMaker;
import com.cyyaw.coco.utils.print.PrintQueue;
import com.cyyaw.coco.utils.print.PrinterWriter;
import com.cyyaw.coco.utils.print.PrinterWriter58mm;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        printTest();
    }

    public static void printTest() {
        PrintOrderDataMaker printOrderDataMaker = new PrintOrderDataMaker(PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT);
        List<byte[]> printData = printOrderDataMaker.getPrintData(PrinterWriter58mm.TYPE_58);

        System.out.println("打印数据");
        PrintQueue.getQueue().add(printData);
    }

}