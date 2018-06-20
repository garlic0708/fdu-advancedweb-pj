package application.service.impl;

import application.entity.Node;
import application.entity.Resource;
import application.entity.ResourceType;
import application.repository.NodeRepository;
import application.repository.ResourceRepository;
import application.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final NodeRepository nodeRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository, NodeRepository nodeRepository) {
        this.resourceRepository = resourceRepository;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Resource getById(long id) {
        return resourceRepository.findById(id);
    }

    @Override
    public Resource getByName(String name) {
        return resourceRepository.findByName(name);
    }

    @Override
    public Set<Resource> getByNodeId(long nodeId) {
        Node node = nodeRepository.findById(nodeId);
        Set<Resource> resourceSet = new HashSet<>();
        for (Resource resource : node.getResources()) {
            resourceSet.add(resourceRepository.findById(resource.getId().longValue()));
        }
        return resourceSet;
    }

    @Override
    public Resource addResource(long nodeId, String name, String location, ResourceType type) {
        Resource resource = new Resource();
        resource.setName(name);
        if (type == ResourceType.FILE)
            resource.setFileLocation(location);
        else
            resource.setUrl(location);
        resource.setType(type);

        Node node = nodeRepository.findById(nodeId);
        resource.setFatherNode(node);
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        resourceRepository.deleteAll();
    }

    @Override
    public Resource updateResource(long id, Resource resource) {
        Resource updated = getById(id);
        updated.setUrl(resource.getUrl());
        updated.setName(resource.getName());
        return resourceRepository.save(updated);
    }

    @Override
    public String getFilePath(long id) {
        Resource resource = getById(id);
        return resource.getFileLocation() + resource.getName();
    }
}
