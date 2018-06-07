package application.service;

import application.entity.HomeWork;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
public interface HomeWorkService {
    HomeWork getById(long id);

    Set<HomeWork> getByNodeId(long nodeId);

    Set<HomeWork> getByMindMapId(long mindMapId);

    void add(long nodeId, HomeWork homeWork);

    void deleteById(long id);

    void deleteAll();

    void update(HomeWork homeWork);
}
