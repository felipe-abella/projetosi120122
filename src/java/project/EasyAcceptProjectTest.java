package project;

import easyaccept.EasyAcceptFacade;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EasyAcceptProjectTest {
    public static final int TEST_NUMBER = -1;
    
    public static void main(String[] args) {
        EasyAcceptProjectTest eapTest = new EasyAcceptProjectTest();
        
        if (!eapTest.easyAcceptSuccess())
            System.exit(1);
    }
   
    public boolean easyAcceptSuccess() {
        List<String> files = new ArrayList<String>();
        
        /*
        if (TEST_NUMBER == -1) {
            String folderName = "easyaccept_tests";
            File folder = new File(folderName);
        
            for (File file: folder.listFiles())
                if (file.isFile() && file.getName().endsWith(".txt") && file.getName().startsWith("US"))
                    files.add("easyaccept_tests" + "/" + file.getName());
        } else {
            files.add(String.format("easyaccept_tests/US%02d.txt", TEST_NUMBER));
        }
        */
        
        
        files.add(String.format("easyaccept_tests/US%02d.txt", 1));
        files.add(String.format("easyaccept_tests/US%02d.txt", 2));
        files.add(String.format("easyaccept_tests/US%02d.txt", 3));
        /*files.add(String.format("easyaccept_tests/US%02d.txt", 4));
        files.add(String.format("easyaccept_tests/US%02d.txt", 5));*/
        
        ProjectFacade facade = new ProjectFacade();
        EasyAcceptFacade eaFacade = new EasyAcceptFacade(facade, files);
        eaFacade.executeTests();
        System.out.println(eaFacade.getCompleteResults());
        
        return eaFacade.getTotalNumberOfNotPassedTests() == 0;
    }
}
