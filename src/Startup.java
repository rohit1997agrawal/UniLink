
import java.io.IOException;

public class Startup {

 //Main Method , in which object of Type Unilink is Created
        public static void main (String[] args) throws Exception
        {

            UniLink obj = new UniLink();
            //method "hardCodeData" to hard code a few data for testing purposes
            obj.hardCodeData();
            //Method "startUpApplication" called which displays the Menu and enables user to perform all function and operations
            obj.startUpApplication();
        }


}
