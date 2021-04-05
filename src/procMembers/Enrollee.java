package procMembers;

public class Enrollee {
    private String userId;
    private String firstName;
    private String lastName;
    private int    versionNb;
    private String insuranceComp;

    public Enrollee(String PinputString) {
        String[] valuesList = PinputString.split("\\s*,\\s*");
        userId = valuesList[0];
        firstName = valuesList[1];
        lastName = valuesList[2];
        versionNb = Integer.parseInt(valuesList[3]);
        insuranceComp = valuesList[4];
    }

    public String getUserId()    { return userId; }
    public int    getVersionNb() { return versionNb; }
    public String getLastName()  { return lastName; }
    public String getFirstName() { return firstName; }
    public String getInsurance() { return insuranceComp; }

    public String createKey() { return createKey(userId, insuranceComp); }
    static public String createKey(String PuserId, String PinsComp) {
        String keyS = PuserId + "_" + PinsComp;
        return keyS;
    }

    public boolean isUpdateNeededChkVersion(Enrollee PotherOne) {
        if (!insuranceComp.equalsIgnoreCase(PotherOne.insuranceComp))
            return true;

        if (PotherOne.versionNb >= versionNb)
            return false;

        return true;
    }
    
    public static boolean checkInputString(String PinputString) {
        if (PinputString.length() <=0)  return false;

        long commaCount = PinputString.chars().filter(ch -> ch == ',').count();
        if (commaCount != 4)  return false;
        
        String chkString = new String( PinputString );
        String[] columnList = chkString.split("\\s*,\\s*");
        for (String chkColumn : columnList) {
            if (chkColumn.length() == 0)  return false;
        }

        try {
            Integer.parseInt( columnList[3] );
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public String convert2String() {
        String outPutS = userId + ", " + lastName + ", " + firstName + ", "
                         + versionNb + ", " + insuranceComp;
        return outPutS;
    }

    public void show() {
        System.out.println("userId=" + userId + "  Name=" + firstName + " " + lastName
                           + "   versionNb=" + versionNb + "   insur=" + insuranceComp);
    }
}
