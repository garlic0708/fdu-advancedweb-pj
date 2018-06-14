package application.service.impl;

import application.entity.HomeWork;
import application.entity.Node;
import application.entity.view.TypeDescriptionView;
import application.repository.HomeWorkRepository;
import application.repository.NodeRepository;
import application.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    private HomeWorkRepository homeWorkRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Override
    public HomeWork getById(long id) {
        return homeWorkRepository.findById(id);
    }

    @Override
    public Set<TypeDescriptionView> getByNodeId(long nodeId) {
        return homeWorkRepository.findByFatherNode_Id(nodeId);
    }

    @Override
    public Set<HomeWork> getByMindMapId(long mindMapId) {
        return homeWorkRepository.findByMindMapId(mindMapId);
    }

    @Override
    public void add(long nodeId, HomeWork homeWork) {
        Node node = nodeRepository.findById(nodeId);
        node.addQuestion(homeWork);
        nodeRepository.save(node);
    }

    @Override
    public void deleteById(long id) {
        homeWorkRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        homeWorkRepository.deleteAll();
    }

    @Override
    public void update(HomeWork homeWork) {
        homeWorkRepository.save(homeWork);
    }
}
