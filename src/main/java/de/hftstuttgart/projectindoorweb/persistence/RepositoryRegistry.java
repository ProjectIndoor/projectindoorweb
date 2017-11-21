package de.hftstuttgart.projectindoorweb.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryRegistry {

    private static Map<String,CrudRepository> repositoryMap;

    public static void initRepositoryMap(){
        repositoryMap = new HashMap<>();
    }

    public static void registerRepository(String name, CrudRepository repository){
        repositoryMap.putIfAbsent(name,repository);
    }

    public static void disposeRepositories(){
        repositoryMap=null;
    }

    public static CrudRepository getRepositoryForName(String repositoryName){
        return repositoryMap.get(repositoryName);
    }
}
