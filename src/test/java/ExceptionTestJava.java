//--------------------------------------------------
// Class ExceptionTestJava
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.exception.BadRequestException;

public class ExceptionTestJava {
    public static void main(String[] args) throws Exception {
        throw (Exception) new BadRequestException();
    }
}
