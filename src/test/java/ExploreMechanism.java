import org.junit.Test;

import java.util.Properties;

public class ExploreMechanism {
    @Test
    public void testJGit(){
        Properties p=System.getProperties();
        p.list(System.out);
    }
}
