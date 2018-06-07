package application.service.impl;

import application.entity.Node;
import application.entity.Resource;
import application.entity.ResourceType;
import application.repository.NodeRepository;
import application.repository.ResourceRepository;
import application.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private NodeRepository nodeRepository;
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
        return null;
    }

    @Override
    public void addResource(long nodeId, String name, String location, ResourceType type) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setFileLocation(location);
        resource.setType(type);

        Node node = nodeRepository.findById(nodeId);
        resource.setFatherNode(node);
        resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public void updateResource(Resource resource) {
        resourceRepository.save(resource);
    }
}
