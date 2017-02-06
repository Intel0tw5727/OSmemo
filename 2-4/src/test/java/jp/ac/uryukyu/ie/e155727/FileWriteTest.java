package jp.ac.uryukyu.ie.e155727;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by e155727 on 2016/11/08.
 **/
public class FileWriteTest {
    @Test
    public void getOpts() throws Exception {
        FileWrite fileWrite = new FileWrite();
        String[] args = { "-b", "1024", "-s", "8192"};
        fileWrite.getOpts(args);
        assertEquals(fileWrite.isBuffered(),true);
        assertEquals(fileWrite.getWriteSize(),8192);
        assertEquals(fileWrite.getBufferSize(),1024);
    }

}