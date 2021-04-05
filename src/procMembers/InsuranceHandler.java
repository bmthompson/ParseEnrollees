package procMembers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class InsuranceHandler {
    public InsuranceHandler(ArrayList<Enrollee> PinputList) {
        // Sort the inputList by name
        Collections.sort(PinputList, new EnrolleeComparator());
    }

    public boolean writeToFile(ArrayList<Enrollee> PinputList) 
    {
        try  {
            String fileName = "../" + PinputList.get(0).getInsurance() + ".txt";
            FileOutputStream foStream = new FileOutputStream(fileName);
            OutputStreamWriter osWriter = new OutputStreamWriter(foStream,"utf-8");
            BufferedWriter outWriter = new BufferedWriter(osWriter,1024);

            for (Enrollee e : PinputList) {
                outWriter.write(e.convert2String() + "\n");
            }
 
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
