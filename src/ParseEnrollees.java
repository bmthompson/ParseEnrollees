import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import procMembers.Enrollee;
import procMembers.InsuranceHandler;

class SortInsuranceComp implements Comparator<Enrollee>
{
    public int compare(Enrollee person1, Enrollee person2)
    {
        return person1.getInsurance().compareTo( person2.getInsurance() );
    }
}

public class ParseEnrollees {
    public static void main(String[] args) throws Exception 
    {
        Map<String, Enrollee> allEnrolleeMap = new HashMap<String, Enrollee>();
        InvalidEntries badEntries = new InvalidEntries();

        try {
            // Read in data file
            List<String> inputFile = Files.readAllLines(Paths.get("Names.txt"));

            // Add to all enrollees map and remove duplicate user ids 
            int lineCnt = 0;
            for (String person : inputFile) {
                lineCnt++;
                
                if (!Enrollee.checkInputString( person )) { 
                     badEntries.addInvalidEntry(person, lineCnt);
                     continue;
                }

                Enrollee enrollee = new Enrollee( person );
                //enrollee.show();

                Enrollee prevEnroll = allEnrolleeMap.get(enrollee.createKey());
                if (prevEnroll != null) {
                    // If new enrollee versionNb is greater, then remove from
                    // map so can be replaced with new enrollee
                    if (!enrollee.isUpdateNeededChkVersion(prevEnroll)) {
                        continue;
                    }
                }

                allEnrolleeMap.put(enrollee.createKey(), enrollee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort by insurance company
        Collection<Enrollee> enrolleeCollt = allEnrolleeMap.values();
        ArrayList<Enrollee> enrolleeList = new ArrayList<>(enrolleeCollt);
        Collections.sort(enrolleeList, new SortInsuranceComp());
    
        // Break up each insurance company and sort enrollees by names
        ArrayList<Enrollee> prevInsList = new ArrayList<>();
        for (Enrollee e : enrolleeList) {
            if (prevInsList.size() == 0) {
                prevInsList.add( e );
            } else if (prevInsList.get(0).getInsurance().equals( e.getInsurance() )) {
                prevInsList.add( e );
            } else {
                // Process previous list
                InsuranceHandler insHandler = new InsuranceHandler( prevInsList );
                insHandler.writeToFile( prevInsList );

                prevInsList.clear();
                prevInsList.add( e );
            }
        }

        if (prevInsList.size() > 0) {      // Process last insurance list
            InsuranceHandler insHandler = new InsuranceHandler( prevInsList );
            insHandler.writeToFile( prevInsList );
        }

        // Output any invalid entries
        badEntries.outPut();
    }
}
