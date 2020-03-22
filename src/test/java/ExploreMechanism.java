import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class ExploreMechanism {
    @Test
    public void testJGit() throws GitAPIException, IOException {
        Collection<Ref> refs=Git.lsRemoteRepository().setRemote(".\\test\\repo").call();
        for (Ref ref:refs){
            System.out.println(ref);
        }
        Git git=Git.init().setDirectory(new File(".\\test\\repo")).call();
        System.out.println(git.status().call().getUntracked());
    }
}
