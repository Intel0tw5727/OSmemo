package jp.ac.uryukyu.ie.e155727;

import java.io.*;

/**
 * Created by e155727 on 2016/11/08.
 **/
public class FileWrite {

    private long writeSize = 1024*1024;
    private int bufferSize = 1024;
    private boolean buffered = false;
    private String fileName = "output.txt";

    public static void main(String args[]) throws IOException {
        FileWrite fw = new FileWrite();
        fw.getOpts(args);
        long tic = System.nanoTime();
        fw.start();
        long tac = System.nanoTime();
        System.out.println("Time:" + (tac - tic) / 1000000f + "ms");
    }

    private void start() throws IOException {
        File output = new File(fileName);
        OutputStream os = new FileOutputStream(output);
        if (buffered) {
            os = new BufferedOutputStream(os,bufferSize);
        }
        for(long i=0; i<writeSize; i++) {
            os.write(0x55);
        }
        os.close();
    }

    public void getOpts(String args[]){
        for (int i = 0 ; i < args.length ; i++) {
            String arg = args[i];
            if (arg.equals("-h,--help")) { // show this
                System.out.println("[-h,--help]:for help");
                System.out.println("[-b]:buffered write");
                System.out.println("[-u]:unbuffered write");
                System.out.println("[-s]:write file size");
                System.out.println("[-f]:input filename");

            } else if (arg.equals("-b")) {
                buffered = true;
                if (i+1<args.length){
                    bufferSize = Integer.parseInt(args[i+1]);
                    i++;
                }

            } else if (arg.equals("-u")) {
                buffered = false;

            } else if (arg.equals("-s")) {
                if (i+1 < args.length) {
                    writeSize = Integer.parseInt(args[i+1]) ;
                    i++;
                }
            } else if (arg.equals("-f")) {
                if (i+1<args.length) {
                    fileName = args[i+1];
                    i++;
                }
            } else {
                System.out.println("Usage: -u,-b,-s,-f filename");
            }
        }
    }
    public boolean isBuffered() {
        return buffered;
    }
    public long getWriteSize() {
        return writeSize;
    }
    public int getBufferSize() {
        return bufferSize;
    }
    public String getFileName() {
        return fileName;
    }

}
