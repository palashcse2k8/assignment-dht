import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Singleton {
//    private static final Singleton inst= new Singleton();
    static File file = new File("Output.txt");
    private Singleton() {
        super();
    }
    
    private static void writeUsingBufferedWriter(String data) {

    		try {
	    		FileWriter fw = new FileWriter("output.txt", true);
	    	    BufferedWriter bw = new BufferedWriter(fw);
	    	    PrintWriter out = new PrintWriter(bw);
    		    out.println(data);
    		    //more code
				out.close();
    		    //more code
    		} catch (IOException e) {
    		    //exception handling left as an exercise for the reader
    			e.printStackTrace();
    		} finally {

			}
    }

    public synchronized static void writeToFile(String str) {
    	writeUsingBufferedWriter(str);
    }
    
    public static void removeFile() {
        if (file.delete()) {
//            System.out.println("Deleted the file: " + file.getName());
        } else {
//            System.out.println("Failed to delete the file.");
        }
    }

//    public static Singleton getInstance() {
//        return inst;
//    }

}