package com.alphathur.curatordemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class WatcherApp {

  private static CuratorFramework curatorFramework = CuratorFactory.getCuratorFramework();

  public static void main(String[] args) throws Exception {
    watchChildNode();
  }

  private static void watchNodeChange() throws Exception{
    NodeCache nodeCache = new NodeCache(curatorFramework, "/app1/node2");
    nodeCache.start(true);
    nodeCache.getListenable().addListener(new NodeCacheListener() {
      @Override
      public void nodeChanged() throws Exception {
        System.out.println("node has changed, current node is " + new String(nodeCache.getCurrentData().getData()));
      }
    });

    System.in.read();
  }

  private static void watchChildNode() throws Exception{
    PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/app1", true);
    pathChildrenCache.start(StartMode.POST_INITIALIZED_EVENT);
    pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
      @Override
      public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
          throws Exception {
        System.out.println(event+ ", "+new String(event.getData().getData()));
      }
    });

    System.in.read();
  }
}
