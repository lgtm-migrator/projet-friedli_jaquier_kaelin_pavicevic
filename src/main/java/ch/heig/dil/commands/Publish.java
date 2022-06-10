package ch.heig.dil.commands;

import java.io.File;
import java.util.concurrent.Callable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import picocli.CommandLine;

@CommandLine.Command(name = "publish", description = "Publish the folder to a github repository.")
public class Publish implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            description = "Personal access token from Github's account")
    String token;

    @CommandLine.Parameters(index = "1", description = "Remote repository URL")
    String remotePath;

    @CommandLine.Parameters(index = "2", description = "Cloned root folder from remote repository")
    String repoPath;

    @Override
    public Integer call() {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            CredentialsProvider credentialsProvider =
                    new UsernamePasswordCredentialsProvider(token, "");
            Repository repository =
                    builder.setGitDir(new File(repoPath + "/.git"))
                            .readEnvironment()
                            .findGitDir()
                            .build();
            Git git = new Git(repository);
            git.add().addFilepattern(Build.BUILD_FOLDER).setUpdate(true).call();
            git.commit().setSign(false).setMessage("Publish build folder").call();
            git.push().setRemote(remotePath).setCredentialsProvider(credentialsProvider).call();
        } catch (Exception e) {
            System.out.println("Error while adding files to the repository");
            return 1;
        }
        return 0;
    }
}
