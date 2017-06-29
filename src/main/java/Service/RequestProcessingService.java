package Service;

import java.io.IOException;

/**
 * Created by Павло on 29.06.2017.
 */
public interface RequestProcessingService {
    void add(String username, String repoName, String token) throws IOException;
    boolean addRepo(String repo, String token) throws IOException;
}
