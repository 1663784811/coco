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
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        printTest();
    }

    public static void printTest() {

        System.out.println("打印数据");
    }

}