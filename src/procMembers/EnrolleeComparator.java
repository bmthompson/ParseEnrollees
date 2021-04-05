package procMembers;

import java.util.Comparator;

public class EnrolleeComparator implements Comparator<Enrollee> 
{
    @Override
    public int compare(Enrollee person1, Enrollee person2) {
        int lastNameResults = person1.getLastName().compareTo( person2.getLastName());
        if (lastNameResults != 0)
            return lastNameResults;

        int firstNameResults = person1.getFirstName().compareTo( person2.getFirstName());
        return firstNameResults;
    }
}
