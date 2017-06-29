package Service.Impl;

import org.kohsuke.github.*;
import Service.RequestProcessingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Created by Павло on 29.06.2017.
 */
public class RequestProcessingServiceImpl implements RequestProcessingService {

    private final String script = "script";

    public void add(String username, String reponame, String token) throws IOException {
        //Obtaining access to github
        GitHub git = new GitHubBuilder().withOAuthToken(token).build();

        //Obtain access to repository
        GHRepository repository = git.getRepository(username + "/" + reponame);

        //Obtain list of content in repository(folders, files)
        List<GHContent> contents = repository.getDirectoryContent("");

        //Obtain original content from original content
        List<GHContent> contentInOriginalScript = repository.getDirectoryContent("script/template");

        contentInOriginalScript.forEach((c) -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(c.read()));
                String originalText = "", s = "";
                while((s = reader.readLine()) != null) {
                    originalText.concat(s + "/n");
                }
                createContentInRepo(repository, c.getName(), getMaxFolderNumber(contentInOriginalScript), originalText,c.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public boolean addRepo(String repo, String token) throws IOException {
        GitHub gitHub = new GitHubBuilder().withOAuthToken(token).build();
        GHRepository repository = gitHub.createRepository(repo).create();
        return repository != null ;
    }

    private int getMaxFolderNumber(List<GHContent> contents) {
        int maxNumber = 1;

        for (GHContent c : contents) {
            if (c.getName().startsWith(script) &&  !c.getName().equals("script")) {
                String numberString = c.getName().substring(script.length(), c.getName().length());
                if (maxNumber < Integer.valueOf(numberString)) {
                    maxNumber = Integer.valueOf(numberString);
                }
            }
        }

        return maxNumber +1 ;
    }

    private void createContentInRepo(GHRepository repository, String fileName, int number, String content, String fileExtension) throws IOException {
        repository.createContent(fileName+ number + "." + fileExtension,
                "addent file " + fileName + number+ "." + fileExtension,
                "script" + number + "/" + fileName + number+ "." + fileExtension );
    }

}
