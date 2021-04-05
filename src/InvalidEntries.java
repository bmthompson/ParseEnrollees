import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class InvalidEntries {
    private static List<String> values = new ArrayList<>();
    private final String outputFileName = "../InvalidEntries.txt";

    public InvalidEntries() {
        removePreviousFile();
    }

    private void removePreviousFile() {
        File previousFile = new File( outputFileName );
        try {
             Files.deleteIfExists(previousFile.toPath());
         } catch (IOException e) {}
    }
 
    public void addInvalidEntry(String PinputString, int PlineNb) {
        if (!PinputString.isBlank()) {
            String invalidS = "Line#" + PlineNb + ":  " + PinputString;
            values.add( invalidS );
        }
    }

    public boolean outPut() 
    {
        if (values.size() <= 0)  return false;

        try  {
            FileOutputStream foStream = new FileOutputStream(outputFileName);
            OutputStreamWriter osWriter = new OutputStreamWriter(foStream,"utf-8");
            BufferedWriter outWriter = new BufferedWriter(osWriter,1024);

            for (String v : values) {
                outWriter.write(v + "\n");
            }
 
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
