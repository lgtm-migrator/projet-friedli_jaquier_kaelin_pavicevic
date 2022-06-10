package ch.heig.dil.commands;

import java.io.File;
import java.util.concurrent.Callable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import picocli.CommandLine;

@CommandLine.Command(name = "publish", description = "Publish the folder to Github")
public class Publish implements Callable<Integer> {
    @CommandLine.Parameters(
            index = "0",
            description = "Personal access token from Github's account")
    String token;

    @CommandLine.Parameters(index = "1", description = "Remote repository url")
    String remotePath;

    @CommandLine.Parameters(index = "2", description = "Build folder path")
    String buildPath;

    @CommandLine.Parameters(index = "3", description = ".git folder path")
    String gitPath;

    @Override
    public Integer call() {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            CredentialsProvider credentialsProvider =
                    new UsernamePasswordCredentialsProvider(token, "");
            Repository repository =
                    builder.setGitDir(new File(gitPath)).readEnvironment().findGitDir().build();

            Git git = new Git(repository);
            git.add().addFilepattern(buildPath + " .").call();
            git.commit().setSign(false).setMessage("Publish build folder").call();
            git.push().setRemote(remotePath).setCredentialsProvider(credentialsProvider).call();
        } catch (Exception e) {
            System.out.println("Error while adding files to the repository");
            return 1;
        }
        return 0;
    }
}
