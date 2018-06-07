package application.service;

import application.entity.Resource;
import application.entity.ResourceType;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface ResourceService {
    Resource getById(long id);

    Resource getByName(String name);

    Set<Resource> getByNodeId(long nodeId);

    void addResource(long nodeId, String name, String location, ResourceType type);

    void deleteResource(long id);

    void updateResource(Resource resource);
}